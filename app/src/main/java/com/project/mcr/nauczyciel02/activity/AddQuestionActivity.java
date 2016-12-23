package com.project.mcr.nauczyciel02.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.project.mcr.nauczyciel02.R;
import com.project.mcr.nauczyciel02.endpoint.RetrofitAPI;
import com.project.mcr.nauczyciel02.model.Answer;
import com.project.mcr.nauczyciel02.model.Category;
import com.project.mcr.nauczyciel02.model.Question;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Created by mikolaj.mocarski on 2016-11-29.
 */
public class AddQuestionActivity extends Activity implements AdapterView.OnItemSelectedListener {

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
    private int currentQuestionId;
    private RadioButton radioButton;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioButton radioButton4;
    private RadioGroup radioGroup;
    private int correctAnswer;

    static final String API_URL = "http://192.168.1.100/android_login_api2";
    RestAdapter restAdapter;
    RestAdapter restAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        questionNameInput = (EditText)findViewById(R.id.question);
        asnwerAInput = (EditText)findViewById(R.id.answerA);
        asnwerBInput = (EditText)findViewById(R.id.answerB);
        asnwerCInput = (EditText)findViewById(R.id.answerC);
        asnwerDInput = (EditText)findViewById(R.id.answerD);
        spinner = (Spinner)findViewById(R.id.spinner);
        radioButton = (RadioButton) findViewById(R.id.radioButton);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        radioButton4 = (RadioButton) findViewById(R.id.radioButton4);
        spinner.setOnItemSelectedListener(this);
        correctAnswer = 0; //jesli uzytkownik nie wybierze poprawnej odpowiedzi
        currentQuestionId = 0; //jesli nie uda sie pobrac nowododanego id pytania


        OkHttpClient mOkHttpClient3 = new OkHttpClient();
        mOkHttpClient3.setConnectTimeout(15000, TimeUnit.MILLISECONDS);
        mOkHttpClient3.setReadTimeout(15000, TimeUnit.MILLISECONDS);

        restAdapter2 = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .setClient(new OkClient(mOkHttpClient3))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        RetrofitAPI methods3 = restAdapter2.create(RetrofitAPI.class);


        Callback<List<Question>> cb3 = new Callback<List<Question>>() {

            @Override
            public void success(List<Question> questions, retrofit.client.Response response) {


                List<HashMap<String,Object>> questionListMap = new ArrayList<>();
                for(Question q: questions){
                    HashMap<String, Object> questionMap = new HashMap<>();

                    try {

                        questionMap.put(q.getClass().getField("question_id").getName(),q.getQuestion_id());
                        //questionMap.put(q.getClass().getField("name").getName(),q.getName());

                        questionListMap.add(questionMap);
                        currentQuestionId = q.getQuestion_id()+1;
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
        methods3.getInsertedQuestionId(cb3);



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
                SimpleAdapter adapter = new SimpleAdapter(getApplication(), categoryDropList, R.layout.list_item_category,
                        new String [] {"name"},new int [] {R.id.categoryName});

                spinner.setAdapter(adapter);
            }



            @Override
            public void failure(RetrofitError error) {
                Log.e("AddQuestionListActivity", error.getMessage() +"\n"+ error.getStackTrace());
                error.printStackTrace();
            }
        };
        methods.getCategoryList(cb);



    }

    public void onClickAddQuestion(View v){
        //
        final String question = questionNameInput.getText().toString().trim();
        String answerA = asnwerAInput.getText().toString().trim();
        String answerB = asnwerBInput.getText().toString().trim();
        String answerC = asnwerCInput.getText().toString().trim();
        String answerD = asnwerDInput.getText().toString().trim();
        ArrayList<String> answerList = new ArrayList();
        answerList.add(answerA);
        answerList.add(answerB);
        answerList.add(answerC);
        answerList.add(answerD);

        if(question.isEmpty() ||(correctAnswer==0) || answerA.isEmpty() || answerB.isEmpty() || answerC.isEmpty() || answerD.isEmpty() ){

             Toast.makeText(getApplicationContext(),
                "Prosze uzupelnic dane formularza", Toast.LENGTH_LONG)
                .show();
        }else {

            //DODAJE DO BAZY nową kategorię
       /* Toast.makeText(getApplicationContext(),
                "Dodano pytanie:\n Treść: "+question+"\nA: "+answerA+"\nB: "+answerB+"\nC: "+answerC+"\nD: "+answerD, Toast.LENGTH_LONG)
                .show();*/

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

                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getApplicationContext(),
                            "Nie udalo sie dodac pytania", Toast.LENGTH_LONG)
                            .show();
                }
            };

            methods.addQuestion(choosenCategoryId,question , cb);



            int iterator = 1;
            for(String a : answerList){
                OkHttpClient mOkHttpClient2 = new OkHttpClient();
                mOkHttpClient2.setConnectTimeout(15000, TimeUnit.MILLISECONDS);
                mOkHttpClient2.setReadTimeout(15000, TimeUnit.MILLISECONDS);

                restAdapter = new RestAdapter.Builder()
                        .setEndpoint(API_URL)
                        .setClient(new OkClient(mOkHttpClient2))
                        .setLogLevel(RestAdapter.LogLevel.FULL)
                        .build();
                RetrofitAPI methods2 = restAdapter.create(RetrofitAPI.class);

                Callback<List<Answer>> cb2 = new Callback<List<Answer>>() {

                    @Override
                    public void success(List<Answer> questions, Response response) {




                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getApplicationContext(),
                                "Nie udalo sie dodac pytania", Toast.LENGTH_LONG)
                                .show();
                    }
                };

                if (correctAnswer==iterator){
                    methods.addAnswer(currentQuestionId,a,1 , cb2);
                }else {
                    methods.addAnswer(currentQuestionId,a,0 , cb2);
                }

                iterator++;
            }
            //poprawne dodanie pytania i wyjscie z formularza
            Toast.makeText(getApplicationContext(),
                    "Formularz poprawnie zamkniety", Toast.LENGTH_LONG)
                    .show();
            Intent intent = new Intent(getApplicationContext(), QuestionListActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //choosenCategoryId =  categ
        //HashMap<String, Object> item = (HashMap<String, Object>) parent.getItemAtPosition(position);
       // questionNameInput.setText(Integer.toString(categoriesFinalList.get(parent.getSelectedItemPosition()).getCategory_id()));
        //questionNameInput.setText((Integer) item.get("category_id"));
        //categoryDropList.get(position).containsValue(item);
        //questionNameInput.setText(categoriesFinalList.get(position).getName());

        //zapisujemy id kategorii pytania
        choosenCategoryId = categoriesFinalList.get(position).getCategory_id();
        //questionNameInput.setText(Integer.toString(choosenCategoryId));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onClickSelectCorrectAnswer (View view){
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButton:
                if (checked){
                    correctAnswer = 1;
                }

                    break;
            case R.id.radioButton2:
                if (checked){
                    correctAnswer = 2;
                }
                    break;
            case R.id.radioButton3:
                if (checked){
                    correctAnswer = 3;
                }
                    break;
            case R.id.radioButton4:
                if (checked){
                    correctAnswer = 4;
                }
                    break;
        }
    };
}