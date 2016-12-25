package com.project.mcr.nauczyciel02.activity.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.project.mcr.nauczyciel02.R;
import com.project.mcr.nauczyciel02.endpoint.RetrofitAPI;
import com.project.mcr.nauczyciel02.model.Category;
import com.project.mcr.nauczyciel02.model.Question;
import com.project.mcr.nauczyciel02.model.Test;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

import static com.project.mcr.nauczyciel02.R.layout.choosen_list_item_test;

/**
 * Created by mikolaj.mocarski on 2016-11-29.
 */
public class TestAddActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private EditText questionNameInput;
    private EditText asnwerAInput;
    private EditText asnwerBInput;
    private EditText asnwerCInput;
    private EditText asnwerDInput;
    private Spinner spinner;
    private int choosenCategoryId;
    private List<HashMap<String,Object>> categoryDropList;
    private HashMap<String, Object> categoryMap;
    private List<Category> categoriesFinalList;
    private ListView choosenQuestionList;
    private List<Question> finalQuestionList;
    private SimpleAdapter adapter;
    private int currentTestId;
    static final String API_URL = "http://192.168.1.100/android_login_api2";
    RestAdapter restAdapter;
    RestAdapter restAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_test);

         choosenQuestionList = (ListView) findViewById(R.id.choosenQuestionList);

        spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        currentTestId = 0;

        OkHttpClient mOkHttpClient3 = new OkHttpClient();
        mOkHttpClient3.setConnectTimeout(15000, TimeUnit.MILLISECONDS);
        mOkHttpClient3.setReadTimeout(15000, TimeUnit.MILLISECONDS);

        restAdapter2 = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .setClient(new OkClient(mOkHttpClient3))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        RetrofitAPI methods3 = restAdapter2.create(RetrofitAPI.class);


        Callback<List<Test>> cb3 = new Callback<List<Test>>() {

            @Override
            public void success(List<Test> questions, Response response) {


                List<HashMap<String,Object>> questionListMap = new ArrayList<>();

                for(Test q: questions){
                    HashMap<String, Object> questionMap = new HashMap<>();

                    try {

                        questionMap.put(q.getClass().getField("test_id").getName(),q.getTest_id());
                        //questionMap.put(q.getClass().getField("name").getName(),q.getName());

                        questionListMap.add(questionMap);
                        currentTestId = q.getTest_id()+1;
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                }





            }



            @Override
            public void failure(RetrofitError error) {
                Log.e("AddQuestionListActivity", error.getMessage() +"\n"+ error.getStackTrace());
                error.printStackTrace();
            }
        };
        methods3.getInsertedTestId(cb3);

        OkHttpClient mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setConnectTimeout(15000, TimeUnit.MILLISECONDS);
        mOkHttpClient.setReadTimeout(15000, TimeUnit.MILLISECONDS);

        restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .setClient(new OkClient(mOkHttpClient))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        RetrofitAPI methods = restAdapter.create(RetrofitAPI.class);


        Callback<List<Category>> cb = new Callback<List<Category>>() {

            @Override
            public void success(List<Category> categories, retrofit.client.Response response) {
                //Log.v("BookListActivity", booksString);
                //TypeToken<List<Book>> token = new TypeToken<List<Book>>() {};
                //List<Book> books = new Gson().fromJson(booksString, token.getType());

                categoryDropList = new ArrayList<>();
                //currentCategory = new Category(1,"");
                categoriesFinalList = new ArrayList<>();
                for(Category c: categories){
                    categoryMap = new HashMap<>();

                    try {

                        categoryMap.put(c.getClass().getField("category_id").getName(),c.getCategory_id());
                        categoryMap.put(c.getClass().getField("name").getName(),c.getName());

                        categoryDropList.add(categoryMap);
                        //currentCategory.setCategory_id(c.getCategory_id());
                        categoriesFinalList.add(c);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                }
                SimpleAdapter adapter2 = new SimpleAdapter(getApplication(), categoryDropList, R.layout.list_item_category,
                        new String [] {"name"},new int [] {R.id.categoryName});

                spinner.setAdapter(adapter2);
            }



            @Override
            public void failure(RetrofitError error) {
                Log.e("TestAddActivity", error.getMessage() +"\n"+ error.getStackTrace());
                error.printStackTrace();
            }
        };
        methods.getCategoryList(cb);



    }


    public void onClickAddTest(View v){

        EditText testNameInput = (EditText) findViewById(R.id.testName);
        String testNameValue = testNameInput.getText().toString().trim();

        if(testNameValue.isEmpty() || (choosenCategoryId == 0)){
            Toast.makeText(getApplicationContext(),
                    "Prosze uzupelnic dane formularza", Toast.LENGTH_LONG)
                    .show();
        }else{
            OkHttpClient mOkHttpClient = new OkHttpClient();
            mOkHttpClient.setConnectTimeout(15000, TimeUnit.MILLISECONDS);
            mOkHttpClient.setReadTimeout(15000, TimeUnit.MILLISECONDS);

            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(API_URL)
                    .setClient(new OkClient(mOkHttpClient))
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();
            RetrofitAPI methods = restAdapter.create(RetrofitAPI.class);


            Callback<List<Test>> cb = new Callback<List<Test>>() {

                @Override
                public void success(List<Test> tests, retrofit.client.Response response) {
                    /*List<HashMap<String,Object>> questionListMap = new ArrayList<>();
                    for(Test q: tests){
                        HashMap<String, Object> questionMap = new HashMap<>();

                        try {

                            questionMap.put(q.getClass().getField("question_id").getName(),q.get());
                            //questionMap.put(q.getClass().getField("name").getName(),q.getName());

                            questionListMap.add(questionMap);
                            choosenCategoryId = q.getQuestion_id()+1;
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        }
                    }*/





                }




                @Override
                public void failure(RetrofitError error) {
                    Log.e("TestAddActivity", error.getMessage() +"\n"+ error.getStackTrace());
                    error.printStackTrace();
                }
            };
            methods.addTest(choosenCategoryId,testNameValue,cb);

        /*Boolean isSelected;
            int liczba = 0;
            int amountOfQuestion = choosenQuestionList.getCount();
            testNameInput.setText(": "+amountOfQuestion);
            //CheckBox choosenQuestion = (CheckBox) findViewById(R.id.choosenQuestion);
            for (int i = 1; i <= amountOfQuestion; i++){
                isSelected = false;
                adapter.getItem(i);
                //CheckBox choosenQuestion = (CheckBox) choosenQuestionList.getChildAt(2).findViewById(R.id.linearLayout);
                CheckBox choosenQuestion = (CheckBox) choosenQuestionList.getChildAt(2).findViewById(choosen_list_item_test).;//.findViewById(R.id.choosenQuestion);
                //LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);
                layout.
                isSelected = choosenQuestion.isSelected();
                if(isSelected){
                    //finalQuestionList
                    liczba++;
                     Toast.makeText(getApplicationContext(),
                    "TERAZ DODAJE LIST VIEW DLA WYBRANEJ KATEGORII", Toast.LENGTH_LONG)
                    .show();
                }
        }*/
            //testNameInput.setText("= "+liczba);
        }



    }

    public void onClickBackButton(View v){
        Intent intent = new Intent(getApplicationContext(), TestListActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            choosenCategoryId = categoriesFinalList.get(position).getCategory_id();
            /*Toast.makeText(getApplicationContext(),
                    "TERAZ DODAJE LIST VIEW DLA WYBRANEJ KATEGORII", Toast.LENGTH_LONG)
                    .show();*/

        choosenQuestionList = (ListView) findViewById(R.id.choosenQuestionList) ;


        OkHttpClient mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setConnectTimeout(15000, TimeUnit.MILLISECONDS);
        mOkHttpClient.setReadTimeout(15000, TimeUnit.MILLISECONDS);

        restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .setClient(new OkClient(mOkHttpClient))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        RetrofitAPI methods = restAdapter.create(RetrofitAPI.class);


        Callback<List<Question>> cb = new Callback<List<Question>>() {

            List<HashMap<String,Object>> questionListMap = new ArrayList<>();
            @Override
            public void success(List<Question> questions, retrofit.client.Response response) {

                finalQuestionList = new ArrayList<>();

                for(Question q: questions){
                    HashMap<String, Object> questionMap = new HashMap<>();

                    try {

                        questionMap.put(q.getClass().getField("question_id").getName(),q.getQuestion_id());
                        questionMap.put(q.getClass().getField("name").getName(),q.getName());
                        questionListMap.add(questionMap);
                         adapter = new SimpleAdapter(getApplication(), questionListMap, choosen_list_item_test,
                                new String [] {"name"},new int [] {R.id.choosenQuestion});

                        choosenQuestionList.setAdapter(adapter);
                        finalQuestionList.add(q);

                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }

                }




            }



            @Override
            public void failure(RetrofitError error) {
                Log.e("TestAddActivity", error.getMessage() +"\n"+ error.getStackTrace());
                error.printStackTrace();
                HashMap<String, Object> questionMap = new HashMap<>();

                questionMap.put("name","Brak pytan dla wybranej kategorii");
                adapter = new SimpleAdapter(getApplication(), questionListMap, R.layout.list_item_question,
                        new String [] {"name"},new int [] {R.id.questionName});

                choosenQuestionList.setAdapter(adapter);
            }
        };
        methods.getQuestionListByCategory(choosenCategoryId,cb);

    }





    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}