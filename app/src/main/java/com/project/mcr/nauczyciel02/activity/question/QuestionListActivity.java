package com.project.mcr.nauczyciel02.activity.question;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.project.mcr.nauczyciel02.R;
import com.project.mcr.nauczyciel02.activity.user.MainActivity;
import com.project.mcr.nauczyciel02.network.RetrofitAPI;
import com.project.mcr.nauczyciel02.model.Category;
import com.project.mcr.nauczyciel02.model.Question;
import com.squareup.okhttp.OkHttpClient;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;

/**
 * Created by MCR on 24.11.2016.
 */
public class QuestionListActivity extends Activity implements AdapterView.OnItemSelectedListener {

    static final String API_URL = "http://192.168.1.100/android_login_api2";
    ListView question_listview;
    RestAdapter restAdapter;
    private Spinner spinner;
    private ArrayList<String> questionsNameList, categoryNameList;
    private ArrayList<Integer> questionsIdsList, categoryIdList;
    private int choosenCategoryId;
    private ListView choosenQuestionList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        question_listview = (ListView) findViewById(R.id.choosenQuestionList);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);


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

                categoryIdList = new ArrayList<>();
                categoryNameList = new ArrayList<>();
                for (Category c : categories) {

                    categoryIdList.add(c.getCategory_id());
                    categoryNameList.add(c.getName());

                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_list_item_1, categoryNameList);

                spinner.setAdapter(adapter);
            }


            @Override
            public void failure(RetrofitError error) {
                Log.e("AddQuestionListActivity", error.getMessage() + "\n" + error.getStackTrace());
                error.printStackTrace();

                questionsNameList.add("Dla wybranej kategorii nie ma zadnych pytan.");
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_list_item_1, questionsNameList);
                choosenQuestionList.setAdapter(adapter2);

            }
        };
        methods.getCategoryList(cb);


    }

    public void onClickAddQuestionActivity(View v) {
        Intent intent = new Intent(getApplicationContext(), QuestionAddActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setContentView(R.layout.activity_question_list);

        question_listview = (ListView) findViewById(R.id.choosenQuestionList);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);


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

                categoryIdList = new ArrayList<>();
                categoryNameList = new ArrayList<>();
                for (Category c : categories) {

                    categoryIdList.add(c.getCategory_id());
                    categoryNameList.add(c.getName());

                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_list_item_1, categoryNameList);

                spinner.setAdapter(adapter);
            }


            @Override
            public void failure(RetrofitError error) {
                Log.e("AddQuestionListActivity", error.getMessage() + "\n" + error.getStackTrace());
                error.printStackTrace();


            }
        };
        methods.getCategoryList(cb);


    }

    public void onClickBackButton(View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        //zapisujemy id kategorii pytania
        choosenCategoryId = categoryIdList.get(position);

        choosenQuestionList = (ListView) findViewById(R.id.choosenQuestionList);


        OkHttpClient mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setConnectTimeout(15000, TimeUnit.MILLISECONDS);
        mOkHttpClient.setReadTimeout(15000, TimeUnit.MILLISECONDS);

        restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .setClient(new OkClient(mOkHttpClient))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        RetrofitAPI methods = restAdapter.create(RetrofitAPI.class);


        questionsNameList = new ArrayList<>();
        questionsIdsList = new ArrayList<>();
        Callback<List<Question>> cb = new Callback<List<Question>>() {


            @Override
            public void success(List<Question> questions, retrofit.client.Response response) {

                for (Question q : questions) {

                    questionsIdsList.add(q.getQuestion_id());
                    questionsNameList.add(q.getName());

                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_list_item_1, questionsNameList);
                    choosenQuestionList.setAdapter(adapter2);


                }

            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("TestAddActivity", error.getMessage() + "\n" + error.getStackTrace());
                error.printStackTrace();
                questionsNameList.add("Dla wybranej kategorii nie ma zadnych pytan.");
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_list_item_1, questionsNameList);
                choosenQuestionList.setAdapter(adapter2);
            }
        };
        methods.getQuestionListByCategory(choosenCategoryId, cb);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}