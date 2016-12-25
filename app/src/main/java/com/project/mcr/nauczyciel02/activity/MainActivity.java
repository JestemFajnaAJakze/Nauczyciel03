package com.project.mcr.nauczyciel02.activity;

import android.os.Bundle;

import com.project.mcr.nauczyciel02.R;

import com.project.mcr.nauczyciel02.activity.category.CategoryListActivity;
import com.project.mcr.nauczyciel02.activity.question.QuestionListActivity;
import com.project.mcr.nauczyciel02.activity.test.TestListActivity;
import com.project.mcr.nauczyciel02.activity.user.LoginActivity;
import com.project.mcr.nauczyciel02.endpoint.RetrofitAPI;
import com.project.mcr.nauczyciel02.helper.SQLiteHandler;
import com.project.mcr.nauczyciel02.helper.SessionManager;
import com.project.mcr.nauczyciel02.model.Category;
import com.project.mcr.nauczyciel02.model.Teacher;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;

public class MainActivity extends Activity {

    private TextView txtName;
    private TextView txtEmail;
    private Button btnLogout;
    private Button categoryButton;
    private Button questionButton;
    private Button testButton;

    private SQLiteHandler db;
    private SessionManager session;
    static final String API_URL = "http://192.168.1.100/android_login_api2";
    RestAdapter restAdapter;
    public Teacher loggedTeacher;
    private List<Teacher> loggedTeachers;



    LinkedList<Category> categories1;




    public void onClickCategory(View v) {
        Intent intent = new Intent(getApplicationContext(), CategoryListActivity.class);
        intent.putExtra("CategoryList", categories1);
        startActivity(intent);
    }

    ;


    public void onClickQuestion(View v) {
        Intent intent = new Intent(getApplicationContext(), QuestionListActivity.class);
        startActivity(intent);
    }

    ;


    public void onClickTest(View v) {
        Intent intent = new Intent(getApplicationContext(), TestListActivity.class);
        startActivity(intent);
    }

    ;

    public void onClicklogoutUser(View v) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        //logoutUser();
    }

    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtName = (TextView) findViewById(R.id.name);
        txtEmail = (TextView) findViewById(R.id.email);
        btnLogout = (Button) findViewById(R.id.btnLogout);

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
            public void success(List<Teacher> loggedTeachers2, retrofit.client.Response response) {

                List<HashMap<String,Object>> bookMapList = new ArrayList<>();
                loggedTeachers = new ArrayList<>();
                for(Teacher t: loggedTeachers2){
                    HashMap<String, Object> bookmap = new HashMap<>();
                    try {

                        bookmap.put(t.getClass().getField("teacher_id").getName(),t.getTeacher_id());
                        bookmap.put(t.getClass().getField("name").getName(),t.getName());
                        bookmap.put(t.getClass().getField("email").getName(),t.getEmail());
                        bookmap.put(t.getClass().getField("password").getName(),t.getPassword());
                        loggedTeacher=t;
                        loggedTeachers.add(t);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }



            @Override
            public void failure(RetrofitError error) {
                Log.e("CategoryListActivity", error.getMessage() +"\n"+ error.getStackTrace());
                error.printStackTrace();
                Toast.makeText(getApplicationContext(),
                        "Bledne dane do logowania!", Toast.LENGTH_LONG)
                        .show();
            }
        };
        methods.checkTeacher("aa","aaa",cb);

        //txtName.setText(t.getName());

    }

}