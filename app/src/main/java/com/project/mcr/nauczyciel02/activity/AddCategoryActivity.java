package com.project.mcr.nauczyciel02.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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
public class AddCategoryActivity extends Activity {


    static final String API_URL = "http://192.168.1.100/android_login_api2";
    //ListView category_listview;
    RestAdapter restAdapter;
    private EditText categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        categoryName = (EditText)findViewById(R.id.categoryTxt);
       // ImageButton backButton = (ImageButton) findViewById(R.id.backButton);

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
                /*Toast.makeText(getApplicationContext(),
                        "Dodano kategorie", Toast.LENGTH_LONG)
                        .show();*/
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getApplicationContext(),
                            "Nie udalo sie dodac kategorii", Toast.LENGTH_LONG)
                            .show();
                }
            };

            methods.addCategory(category, cb);


            //DODAJE DO BAZY nową kategorię
            Toast.makeText(getApplicationContext(),
                    "Formularz zamkniety poprawnie", Toast.LENGTH_LONG)
                    .show();
            Intent intent = new Intent(getApplicationContext(), CategoryListActivity.class);
            startActivity(intent);
        }
        }

}