package com.project.mcr.nauczyciel02.activity.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.project.mcr.nauczyciel02.R;
import com.project.mcr.nauczyciel02.activity.MainActivity;
import com.project.mcr.nauczyciel02.endpoint.RetrofitAPI;
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
public class TestListActivity extends Activity {

    static final String API_URL = "http://192.168.1.100/android_login_api2";
    ListView test_listview;
    RestAdapter restAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);

        test_listview = (ListView) findViewById(R.id.test_listview);

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

                List<HashMap<String,Object>> testMapList = new ArrayList<>();
                for(Test t: tests){
                    HashMap<String, Object> testMap = new HashMap<>();

                    try {

                        testMap.put(t.getClass().getField("test_id").getName(),t.getTest_id());
                        testMap.put(t.getClass().getField("name").getName(),t.getName());

                        testMapList.add(testMap);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                }
                SimpleAdapter adapter = new SimpleAdapter(getApplication(), testMapList, R.layout.list_item_test,
                        new String [] {"name"},new int [] { R.id.testName});

                test_listview.setAdapter(adapter);
            }



            @Override
            public void failure(RetrofitError error) {
                Log.e("TestListActivity", error.getMessage() +"\n"+ error.getStackTrace());
                error.printStackTrace();
            }
        };
        methods.getTestsAll(cb);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setContentView(R.layout.activity_test_list);

        test_listview = (ListView) findViewById(R.id.test_listview);

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
                //Log.v("BookListActivity", booksString);
                //TypeToken<List<Book>> token = new TypeToken<List<Book>>() {};
                //List<Book> books = new Gson().fromJson(booksString, token.getType());

                List<HashMap<String,Object>> testMapList = new ArrayList<>();
                for(Test t: tests){
                    HashMap<String, Object> testMap = new HashMap<>();

                    try {

                        testMap.put(t.getClass().getField("test_id").getName(),t.getTest_id());
                        testMap.put(t.getClass().getField("name").getName(),t.getName());

                        testMapList.add(testMap);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                }
                SimpleAdapter adapter = new SimpleAdapter(getApplication(), testMapList, R.layout.list_item_test,
                        new String [] {"name"},new int [] { R.id.testName});

                test_listview.setAdapter(adapter);
            }



            @Override
            public void failure(RetrofitError error) {
                Log.e("TestListActivity", error.getMessage() +"\n"+ error.getStackTrace());
                error.printStackTrace();
            }
        };
        methods.getTestsAll(cb);

    }


    public void onClickAddTestActivity(View v){
        Intent intent = new Intent(getApplicationContext(), TestAddActivity.class);
        startActivity(intent);
    }

    public void onClickBackButton(View v){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}