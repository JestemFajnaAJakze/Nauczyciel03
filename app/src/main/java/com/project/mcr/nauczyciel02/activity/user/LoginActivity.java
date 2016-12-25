package com.project.mcr.nauczyciel02.activity.user;

import android.os.Bundle;

import com.project.mcr.nauczyciel02.R;

/**
 * Created by MCR on 24.10.2016.
 */import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;


import com.project.mcr.nauczyciel02.activity.MainActivity;
import com.project.mcr.nauczyciel02.endpoint.RetrofitAPI;
import com.project.mcr.nauczyciel02.helper.SQLiteHandler;
import com.project.mcr.nauczyciel02.helper.SessionManager;
import com.project.mcr.nauczyciel02.model.Teacher;
import com.squareup.okhttp.OkHttpClient;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;

public class LoginActivity extends Activity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnLogin;
    private Button btnLinkToRegister;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    private List<Teacher> loggedTeachers;
    Boolean isUserCorrect = false;
    static final String API_URL = "http://192.168.1.100/android_login_api2";
    RestAdapter restAdapter;
    private Teacher loggedTeacher;

    public void onClickLogin(View v) {

        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();


        // Check for empty data in the form
        if (!email.isEmpty() && !password.isEmpty()) {
            // login user

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

                    try{
                        for (Teacher t : teachers) {


                            loggedTeacher.setName(t.getName());
                            loggedTeacher.setPassword(t.getPassword());
                            loggedTeacher.setEmail(t.getEmail());

                            if (loggedTeacher.getEmail().equals(null)) {
                                Toast.makeText(getApplicationContext(),
                                        "Bledne dane do logowania!", Toast.LENGTH_SHORT)
                                        .show();
                            } else {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("teacherName", loggedTeacher.getName());
                                intent.putExtra("teacherEmail", loggedTeacher.getEmail());
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(),
                                        "Witaj " + loggedTeacher.getName() + "!", Toast.LENGTH_LONG)
                                        .show();


                            }


                        }
                    }
                    catch (Exception e){

                    }




                }


                @Override
                public void failure(RetrofitError error) {
                    Log.e("CategoryListActivity", error.getMessage() + "\n" + error.getStackTrace());
                    error.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Bledne dane do logowania!", Toast.LENGTH_SHORT)
                            .show();
                }
            };
            methods.checkTeacher(email, password, cb);

        } else {
            Toast.makeText(getApplicationContext(),
                    "Prosze uzupelnic dane do logowania!", Toast.LENGTH_LONG)
                    .show();
        }

    }


    public void onClickRegister(View v) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }

    ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        loggedTeacher = new Teacher();



    }


}