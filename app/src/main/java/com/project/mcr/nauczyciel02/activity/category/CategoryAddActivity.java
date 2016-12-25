package com.project.mcr.nauczyciel02.activity.category;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.project.mcr.nauczyciel02.R;
import com.project.mcr.nauczyciel02.endpoint.RetrofitAPI;
import com.project.mcr.nauczyciel02.model.Category;
import com.squareup.okhttp.OkHttpClient;

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
public class CategoryAddActivity extends Activity {

    static final String API_URL = "http://192.168.1.100/android_login_api2";
    RestAdapter restAdapter;
    private EditText categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        categoryName = (EditText)findViewById(R.id.categoryTxt);

    }
    public void onClickBackButton(View v){
        Intent intent = new Intent(getApplicationContext(), CategoryListActivity.class);
        startActivity(intent);
    }

    public void onClickAddCategory(View v){
        //


        String category = categoryName.getText().toString().trim();
        if(category.isEmpty()){
            Toast.makeText(getApplicationContext(),
                    "Prosze uzupelnic formularz", Toast.LENGTH_LONG)
                    .show();
        }else {
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
                public void success(List<Category> categories, Response response) {

                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getApplicationContext(),
                            "Nie udalo sie dodac kategorii", Toast.LENGTH_SHORT)
                            .show();
                }
            };

            methods.addCategory(category, cb);

            Toast.makeText(getApplicationContext(),
                    "Formularz zamkniety poprawnie", Toast.LENGTH_SHORT)
                    .show();
            Intent intent = new Intent(getApplicationContext(), CategoryListActivity.class);
            startActivity(intent);
        }
        }

}