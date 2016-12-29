package com.project.mcr.nauczyciel02.activity.test;

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
import android.widget.TextView;
import android.widget.Toast;

import com.project.mcr.nauczyciel02.R;
import com.project.mcr.nauczyciel02.activity.question.QuestionActivity;
import com.project.mcr.nauczyciel02.network.RetrofitAPI;
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
    private ArrayList<String> questionsNameLists;
    private ArrayList<Integer> questionsIdList;
    private Test test;

    List<HashMap<String, Object>> testMapList;
    HashMap<String, Object> testMap;
    private List<Test> testsFinalList;
    private int testCategoryId;
    private String testName, categoryName;

    //public static  final String message = "position";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        chosenTestId = 0;
        testCategoryId = 0;
        chosenTestId = getIntent().getIntExtra("position", 0);
        testName = getIntent().getStringExtra("testName");
        //categoryName = getIntent().getStringExtra("categoryName");


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

                test = new Test();

                for (Test t : tests) {

                    test.setTest_id(t.getTest_id());
                    test.setTest_name(t.getTest_name());
                    categoryName = t.getCategory_name();
                    testCategoryId = t.getCategory_id();

                }

            }


            @Override
            public void failure(RetrofitError error) {
                Log.e("TestListActivity", error.getMessage() + "\n" + error.getStackTrace());
                error.printStackTrace();
            }
        };
        methods.getTestMainInfo(chosenTestId, cb);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
        intent.putExtra("position", questionsIdList.get(position));
        //intent.putExtra("categoryName", test.getCategory_name());
        intent.putExtra("questionName", questionsNameLists.get(position));
        startActivity(intent);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            TextView testNametxt = (TextView) findViewById(R.id.testName);
            testNametxt.setText("Wybrany test: " + testName);

            TextView categoryNametxt = (TextView) findViewById(R.id.categoryName);
            categoryNametxt.setText("Wybrana kategoria: " + categoryName);


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

                    questionsIdList = new ArrayList<>();
                    questionsNameLists = new ArrayList<>();
                    for (Question q : questions) {


                        questionsIdList.add(q.getQuestion_id());
                        questionsNameLists.add(q.getName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_list_item_1, questionsNameLists) {
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            TextView textView = (TextView) super.getView(position, convertView, parent);
                            textView.setTextColor(Color.BLACK);
                            return textView;
                        }
                    };
                    ;

                    test_listview.setAdapter(adapter);
                }


                @Override
                public void failure(RetrofitError error) {
                    Log.e("QuestionListActivity", error.getMessage() + "\n" + error.getStackTrace());
                    error.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Ten test nie ma podpietych zadnych pytan", Toast.LENGTH_LONG)
                            .show();
                }
            };
            methods.getTestDetailsInfo(testCategoryId, chosenTestId, cb);

        }


    }

    public void onClickBackButton(View v) {
        Intent intent = new Intent(getApplicationContext(), TestListActivity.class);
        startActivity(intent);
    }

}
