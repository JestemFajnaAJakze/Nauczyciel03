package com.project.mcr.nauczyciel02.activity.user;

import android.os.Bundle;

import com.project.mcr.nauczyciel02.R;

/**
 * Created by MCR on 24.10.2016.
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


import com.project.mcr.nauczyciel02.activity.MainActivity;
import com.project.mcr.nauczyciel02.activity.category.CategoryListActivity;
import com.project.mcr.nauczyciel02.app.AppConfig;
import com.project.mcr.nauczyciel02.app.AppController;
import com.project.mcr.nauczyciel02.endpoint.RetrofitAPI;
import com.project.mcr.nauczyciel02.helper.SQLiteHandler;
import com.project.mcr.nauczyciel02.helper.SessionManager;
import com.project.mcr.nauczyciel02.model.Category;
import com.project.mcr.nauczyciel02.model.Teacher;
import com.squareup.okhttp.OkHttpClient;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;

public class RegisterActivity extends Activity {

    private EditText inputFullName;
    private EditText inputEmail;
    private EditText inputPassword;
    String name, email, password;
    private Teacher teacher;
    private Boolean isTeacherExist;

    static final String API_URL = "http://192.168.1.100/android_login_api2";
    RestAdapter restAdapter, restAdapter2;


    public void onClickLogin(View v) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }


    public void onClickRegister(View v) {
        name = inputFullName.getText().toString().trim();
        email = inputEmail.getText().toString().trim();
        password = inputPassword.getText().toString().trim();


        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {

            OkHttpClient mOkHttpClient = new OkHttpClient();
            mOkHttpClient.setConnectTimeout(15000, TimeUnit.MILLISECONDS);
            mOkHttpClient.setReadTimeout(15000, TimeUnit.MILLISECONDS);

            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(API_URL)
                    .setClient(new OkClient(mOkHttpClient))
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();
            RetrofitAPI methods = restAdapter.create(RetrofitAPI.class);

            Callback<List<Teacher>> cb = new Callback<List<Teacher>>() {

                @Override
                public void success(List<Teacher> teachers, retrofit.client.Response response) {


                  //  try {
                        for (Teacher t : teachers) {


                            isTeacherExist = true;


                        }

                        if(isTeacherExist){
                            Toast.makeText(getApplicationContext(),
                                    "Ten adres email jest juz zajety", Toast.LENGTH_SHORT)
                                    .show();

                        }else {
                        }





                    //}
                }

                @Override
                public void failure(RetrofitError error) {

                    OkHttpClient mOkHttpClient2 = new OkHttpClient();
                    mOkHttpClient2.setConnectTimeout(15000, TimeUnit.MILLISECONDS);
                    mOkHttpClient2.setReadTimeout(15000, TimeUnit.MILLISECONDS);

                    restAdapter2 = new RestAdapter.Builder()
                            .setEndpoint(API_URL)
                            .setClient(new OkClient(mOkHttpClient2))
                            .setLogLevel(RestAdapter.LogLevel.FULL)
                            .build();
                    RetrofitAPI methods2 = restAdapter2.create(RetrofitAPI.class);

                    Callback<List<Teacher>> cb2 = new Callback<List<Teacher>>() {

                        @Override
                        public void success(List<Teacher> teachers2, retrofit.client.Response response) {
                            //try {


                            //}
                        }

                        @Override
                        public void failure(RetrofitError error) {

                            Toast.makeText(getApplicationContext(),
                                    "Formularz zamkniety poprawnie", Toast.LENGTH_SHORT)
                                    .show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            // } catch (Exception e) {
/*
                            Toast.makeText(getApplicationContext(),
                                    "Nie udalo sie dodac uzytkownika", Toast.LENGTH_SHORT)
                                    .show();*/

                        }
                    };

                    methods2.addTeacher(email, password, name, cb2);

                }
            };

            methods.isTeacherExist(email, cb);


        } else {
            Toast.makeText(getApplicationContext(),
                    "Uzupelnij dane formularza", Toast.LENGTH_LONG)
                    .show();
        }
    }

    ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputFullName = (EditText) findViewById(R.id.name);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        teacher = new Teacher();
        isTeacherExist = false;

    }

}