package com.project.mcr.nauczyciel02.activity.category;

import android.graphics.Color;
import android.os.Bundle;

import com.project.mcr.nauczyciel02.R;

import com.project.mcr.nauczyciel02.activity.user.MainActivity;
import com.project.mcr.nauczyciel02.network.RetrofitAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.project.mcr.nauczyciel02.model.Category;
import com.squareup.okhttp.OkHttpClient;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;

/**
 * Created by MCR on 24.11.2016.
 */
public class CategoryListActivity extends Activity {

    static final String API_URL = "http://192.168.1.100/android_login_api2";
    ListView category_listview;
    RestAdapter restAdapter;
    ArrayList<String> categoryNameList;
    ArrayList<Integer> categoryIdsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        category_listview = (ListView) findViewById(R.id.categoryList) ;


        OkHttpClient mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setConnectTimeout(15000,TimeUnit.MILLISECONDS);
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

                categoryNameList = new ArrayList<>();
                categoryIdsList = new ArrayList<>();
                for(Category c: categories){

                    categoryNameList.add(c.getName());
                    categoryIdsList.add(c.getCategory_id());

                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_list_item_1, categoryNameList){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView textView = (TextView) super.getView(position, convertView, parent);
                        textView.setTextColor(Color.BLACK);
                        return textView;
                    }
                };
                category_listview.setAdapter(adapter);


            }



            @Override
            public void failure(RetrofitError error) {
                Log.e("CategoryListActivity", error.getMessage() +"\n"+ error.getStackTrace());
                error.printStackTrace();
            }
        };
        methods.getCategoryList(cb);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setContentView(R.layout.activity_category_list);

        category_listview = (ListView) findViewById(R.id.categoryList) ;


        OkHttpClient mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setConnectTimeout(15000,TimeUnit.MILLISECONDS);
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

                for(Category c: categories){

                    categoryNameList.add(c.getName());
                    categoryIdsList.add(c.getCategory_id());

                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_list_item_1, categoryNameList){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView textView = (TextView) super.getView(position, convertView, parent);
                        textView.setTextColor(Color.BLACK);
                        return textView;
                    }
                };;

                category_listview.setAdapter(adapter);
            }



            @Override
            public void failure(RetrofitError error) {
                Log.e("CategoryListActivity", error.getMessage() +"\n"+ error.getStackTrace());
                error.printStackTrace();
            }
        };
        methods.getCategoryList(cb);


    }

    public void onClickAddCategoryActivity(View v){
        Intent intent = new Intent(getApplicationContext(), CategoryAddActivity.class);
        startActivity(intent);
    }
    public void onClickBackButton(View v){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}