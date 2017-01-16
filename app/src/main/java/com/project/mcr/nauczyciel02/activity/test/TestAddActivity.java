package com.project.mcr.nauczyciel02.activity.test;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mcr.nauczyciel02.R;
import com.project.mcr.nauczyciel02.network.RetrofitAPI;
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
import retrofit.client.Response;

/**
 * Created by mikolaj.mocarski on 2016-11-29.
 */
public class TestAddActivity extends Activity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

/*    private EditText questionNameInput;
    private EditText asnwerAInput;
    private EditText asnwerBInput;
    private EditText asnwerCInput;
    private EditText asnwerDInput;*/
    private Spinner spinner;
    private int choosenCategoryId;

    private ListView choosenQuestionList;
    private int currentTestId;
    static final String API_URL = "http://192.168.1.100/android_login_api2";
    RestAdapter restAdapter;
    RestAdapter restAdapter2;
    private ArrayList<String> questionNameList, categoryNameList;
    private ArrayList<Integer> questionIdList, categoryIdList, selectedQuestions;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_test);

        choosenQuestionList = (ListView) findViewById(R.id.choosenQuestionList);

        spinner = (Spinner) findViewById(R.id.spinner);
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


                for (Test q : questions) {


                    currentTestId = q.getTest_id() + 1;
                }


            }


            @Override
            public void failure(RetrofitError error) {
                Log.e("AddQuestionListActivity", error.getMessage() + "\n" + error.getStackTrace());
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

                categoryIdList = new ArrayList<>();
                categoryNameList = new ArrayList<>();

                for (Category c : categories) {

                    categoryIdList.add(c.getCategory_id());
                    categoryNameList.add(c.getName());
                }
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_list_item_1, categoryNameList);
                spinner.setAdapter(adapter2);
            }


            @Override
            public void failure(RetrofitError error) {
                Log.e("TestAddActivity", error.getMessage() + "\n" + error.getStackTrace());
                error.printStackTrace();
            }
        };
        methods.getCategoryList(cb);


    }


    public void onClickAddTest(View v) {

        EditText testNameInput = (EditText) findViewById(R.id.testName);
        String testNameValue = testNameInput.getText().toString().trim();

        if (testNameValue.isEmpty() || (choosenCategoryId == 0)) {
            Toast.makeText(getApplicationContext(),
                    "Prosze uzupelnic dane formularza", Toast.LENGTH_LONG)
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


            Callback<List<Test>> cb = new Callback<List<Test>>() {

                @Override
                public void success(List<Test> tests, retrofit.client.Response response) {

                }


                @Override
                public void failure(RetrofitError error) {
                    Log.e("TestAddActivity", error.getMessage() + "\n" + error.getStackTrace());
                    error.printStackTrace();
                }
            };
            methods.addTest(choosenCategoryId, testNameValue, cb);


            selectedQuestions = new ArrayList<>();
            SparseBooleanArray checked = choosenQuestionList.getCheckedItemPositions();
            ArrayList<String> selectedItems = new ArrayList<String>();
            for (int i = 0; i < checked.size(); i++) {
                // Item position in adapter
                int position = checked.keyAt(i);
                // Add sport if it is checked i.e.) == TRUE!
                if (checked.valueAt(i)) {
                    selectedItems.add(adapter.getItem(position));
                    selectedQuestions.add(questionIdList.get(position));
                }

            }


            for (int x = 0; x < selectedQuestions.size(); x++) {


                OkHttpClient mOkHttpClient2 = new OkHttpClient();
                mOkHttpClient2.setConnectTimeout(15000, TimeUnit.MILLISECONDS);
                mOkHttpClient2.setReadTimeout(15000, TimeUnit.MILLISECONDS);

                restAdapter = new RestAdapter.Builder()
                        .setEndpoint(API_URL)
                        .setClient(new OkClient(mOkHttpClient2))
                        .setLogLevel(RestAdapter.LogLevel.FULL)
                        .build();
                RetrofitAPI methods2 = restAdapter.create(RetrofitAPI.class);


                Callback<List<Test>> cb2 = new Callback<List<Test>>() {

                    @Override
                    public void success(List<Test> tests, retrofit.client.Response response) {

                    }


                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("TestAddActivity", error.getMessage() + "\n" + error.getStackTrace());
                        error.printStackTrace();
                    }
                };
                methods2.addTestQuestion(currentTestId, selectedQuestions.get(x), cb2);


                Intent intent = new Intent(getApplicationContext(), TestAssignedToClassActivity.class);
                intent.putExtra("currentTestId", currentTestId);
                startActivity(intent);
            }
        }


    }

    public void onClickBackButton(View v) {
        Intent intent = new Intent(getApplicationContext(), TestListActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


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


        Callback<List<Question>> cb = new Callback<List<Question>>() {

            @Override
            public void success(List<Question> questions, retrofit.client.Response response) {

                questionIdList = new ArrayList<>();
                questionNameList = new ArrayList<>();

                for (Question q : questions) {

                    questionIdList.add(q.getQuestion_id());
                    questionNameList.add(q.getName());
                }
                adapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_list_item_multiple_choice, questionNameList);

                choosenQuestionList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                choosenQuestionList.setAdapter(adapter);


            }


            @Override
            public void failure(RetrofitError error) {
                Log.e("TestAddActivity", error.getMessage() + "\n" + error.getStackTrace());
                error.printStackTrace();

                questionNameList.add("Brak pytan dla wybranej kategorii");
                adapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_list_item_1, questionNameList);


                choosenQuestionList.setAdapter(adapter);
            }
        };
        methods.getQuestionListByCategory(choosenCategoryId, cb);


    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {

    }
}