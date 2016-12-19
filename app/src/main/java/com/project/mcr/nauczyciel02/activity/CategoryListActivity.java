package com.project.mcr.nauczyciel02.activity;

import android.os.Bundle;

import com.project.mcr.nauczyciel02.R;

import com.project.mcr.nauczyciel02.endpoint.RetrofitAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;


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
                //Log.v("BookListActivity", booksString);
                //TypeToken<List<Book>> token = new TypeToken<List<Book>>() {};
                //List<Book> books = new Gson().fromJson(booksString, token.getType());

                List<HashMap<String,Object>> bookMapList = new ArrayList<>();
                for(Category c: categories){
                    HashMap<String, Object> bookmap = new HashMap<>();

                    try {

                        bookmap.put(c.getClass().getField("category_id").getName(),c.getCategory_id());
                        bookmap.put(c.getClass().getField("name").getName(),c.getName());

                        bookMapList.add(bookmap);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                }
                SimpleAdapter adapter = new SimpleAdapter(getApplication(), bookMapList, R.layout.list_item_category,
                        new String [] {"category_id", "name"},new int [] {R.id.categoryId, R.id.categoryName});

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

                List<HashMap<String,Object>> categoryMapList = new ArrayList<>();
                for(Category c: categories){ 
                    HashMap<String, Object> categoryMap = new HashMap<>();

                    try {

                        categoryMap.put(c.getClass().getField("category_id").getName(),c.getCategory_id());
                        categoryMap.put(c.getClass().getField("name").getName(),c.getName());

                        categoryMapList.add(categoryMap);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                }
                SimpleAdapter adapter = new SimpleAdapter(getApplication(), categoryMapList, R.layout.list_item_category,
                        new String [] {"category_id", "name"},new int [] {R.id.categoryId, R.id.categoryName});

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
        Intent intent = new Intent(getApplicationContext(), AddCategoryActivity.class);
        startActivity(intent);
    }
}