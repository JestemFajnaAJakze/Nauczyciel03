package com.project.mcr.nauczyciel02.activity.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mcr.nauczyciel02.R;
import com.project.mcr.nauczyciel02.activity.MainActivity;
import com.project.mcr.nauczyciel02.activity.question.QuestionActivity;
import com.project.mcr.nauczyciel02.endpoint.RetrofitAPI;
import com.project.mcr.nauczyciel02.model.Category;
import com.project.mcr.nauczyciel02.model.Question;
import com.project.mcr.nauczyciel02.model.Test;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;

/**
 * Created by MCR on 24.11.2016.
 */
public class TestActivity extends Activity implements AdapterView.OnItemClickListener {

    static final String API_URL = "http://192.168.1.100/android_login_api2";
    ListView test_listview;
    RestAdapter restAdapter;
    int chosenTestId;
    List<HashMap<String,Object>> testMapList;
    HashMap<String, Object> testMap;
    private List<Test> testsFinalList;
    private int testCategoryId;
    //public static  final String message = "position";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        chosenTestId = 0;
        testCategoryId = 0;
        chosenTestId = getIntent().getIntExtra("position", 0);




        test_listview = (ListView) findViewById(R.id.test_listview);
        test_listview.setOnItemClickListener(this);

        OkHttpClient mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setConnectTimeout(15000, TimeUnit.MILLISECONDS);
        mOkHttpClient.setReadTimeout(15000, TimeUnit.MILLISECONDS);

        restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .setClient(new OkClient(mOkHttpClient))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        RetrofitAPI methods = restAdapter.create(RetrofitAPI.class);

        testsFinalList = new ArrayList<>();
        Callback<List<Test>> cb = new Callback<List<Test>>() {

            @Override
            public void success(List<Test> tests, retrofit.client.Response response) {

                testMapList = new ArrayList<>();
                Test test = new Test();

                for(Test t: tests){
                    testMap = new HashMap<>();

                    try {

                        testMap.put(t.getClass().getField("test_id").getName(),t.getTest_id());
                        testMap.put(t.getClass().getField("test_name").getName(),t.getTest_name());
                        testMap.put(t.getClass().getField("category_name").getName(),t.getCategory_name());
                        testMap.put(t.getClass().getField("category_id").getName(),t.getCategory_id());
                        test.setTest_id(t.getTest_id());
                        test.setTest_name(t.getTest_name());
                        test.setCategory_name(t.getCategory_name());
                        test.setCategory_id(t.getCategory_id());
                        testMapList.add(testMap);
                        testsFinalList.add(test);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                }
                /*SimpleAdapter adapter = new SimpleAdapter(getApplication(), testMapList, R.layout.list_item_test,
                        new String [] {"test_name"},new int [] { R.id.testName});

                test_listview.setAdapter(adapter);*/
            }



            @Override
            public void failure(RetrofitError error) {
                Log.e("TestListActivity", error.getMessage() +"\n"+ error.getStackTrace());
                error.printStackTrace();
            }
        };
        methods.getTestMainInfo(chosenTestId,cb);







    }

    @Override
    protected void onStart() {
        super.onStart();

    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            TextView testName = (TextView)findViewById(R.id.testName) ;
            testName.setText("Wybrany test: "+testsFinalList.get(0).getTest_name());

        TextView categoryName = (TextView)findViewById(R.id.categoryName) ;
        categoryName.setText("Wybrana kategoria: "+testsFinalList.get(0).getCategory_name());

            testCategoryId = testsFinalList.get(0).getCategory_id();


            if(testsFinalList.isEmpty()){
                Toast.makeText(getApplicationContext(),
                        "Ten test nie ma podpietych zadnych pytan", Toast.LENGTH_LONG)
                        .show();
            } else {
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

                    @Override
                    public void success(List<Question> questions, retrofit.client.Response response) {
                        //Log.v("BookListActivity", booksString);
                        //TypeToken<List<Book>> token = new TypeToken<List<Book>>() {};
                        //List<Book> books = new Gson().fromJson(booksString, token.getType());

                        List<HashMap<String,Object>> questionListMap = new ArrayList<>();
                        for(Question q: questions){
                            HashMap<String, Object> questionMap = new HashMap<>();

                            try {

                                questionMap.put(q.getClass().getField("question_id").getName(),q.getQuestion_id());
                                questionMap.put(q.getClass().getField("name").getName(),q.getName());

                                questionListMap.add(questionMap);
                            } catch (NoSuchFieldException e) {
                                e.printStackTrace();
                               /* Toast.makeText(getApplicationContext(),
                                        "Ten test nie ma podpietych zadnych pytan", Toast.LENGTH_LONG)
                                        .show();*/
                            }
                        }
                        SimpleAdapter adapter = new SimpleAdapter(getApplication(), questionListMap, R.layout.list_item_question,
                                new String [] {"name"},new int [] {R.id.questionName});

                        test_listview.setAdapter(adapter);
                    }



                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("QuestionListActivity", error.getMessage() +"\n"+ error.getStackTrace());
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(),
                                "Ten test nie ma podpietych zadnych pytan", Toast.LENGTH_LONG)
                                .show();
                    }
                };
                methods.getTestDetailsInfo(testCategoryId, chosenTestId, cb);

            }

        }
    }

    public void onClickBackButton(View v){
        Intent intent = new Intent(getApplicationContext(), TestListActivity.class);
        startActivity(intent);
    }

}
