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
import com.project.mcr.nauczyciel02.activity.user.MainActivity;
import com.project.mcr.nauczyciel02.network.RetrofitAPI;
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
public class TestListActivity extends Activity implements AdapterView.OnItemClickListener {

    static final String API_URL = "http://192.168.1.100/android_login_api2";
    ListView test_listview;
    RestAdapter restAdapter;
    List<HashMap<String,Object>> testMapList;
    HashMap<String, Object> testMap;
    private ArrayList<Test> testsFinalList;
    private ArrayList<String>testsFinalListNames;
    private ArrayList<Integer>testsFinalListIds;
    private Test test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);

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


        Callback<List<Test>> cb = new Callback<List<Test>>() {

            @Override
            public void success(List<Test> tests, retrofit.client.Response response) {

                testsFinalList = new ArrayList<Test>();
                testsFinalListNames = new ArrayList<>();
                testsFinalListIds = new ArrayList<>();

                for(Test t: tests){

                    try {
                        testsFinalListNames.add(t.getName());
                        testsFinalListIds.add(t.getTest_id());
                        testsFinalList.add(t);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_list_item_1, testsFinalListNames);

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


        Callback<List<Test>> cb = new Callback<List<Test>>() {

            @Override
            public void success(List<Test> tests, retrofit.client.Response response) {

                testsFinalList = new ArrayList<Test>();
                testsFinalListNames = new ArrayList<>();
                testsFinalListIds = new ArrayList<>();

                for(Test t: tests){

                    try {

                        testsFinalListNames.add(t.getName());
                        testsFinalListIds.add(t.getTest_id());
                        testsFinalList.add(t);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_list_item_1, testsFinalListNames);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        int choosenTestId = 0;
        choosenTestId = testsFinalListIds.get(position);
        //choosenTestId = position+1;
        Toast.makeText(getApplicationContext(),
                "Name: "+testsFinalListNames.get(position), Toast.LENGTH_SHORT)
                .show();
        Intent intent = new Intent(getApplicationContext(), TestActivity.class);
        intent.putExtra("position", testsFinalListIds.get(position));
        intent.putExtra("testName", testsFinalListNames.get(position));

        startActivity(intent);
    }
}