package com.project.mcr.nauczyciel02.activity.user;

import android.os.Bundle;

import com.project.mcr.nauczyciel02.R;

import com.project.mcr.nauczyciel02.activity.category.CategoryListActivity;
import com.project.mcr.nauczyciel02.activity.question.QuestionListActivity;
import com.project.mcr.nauczyciel02.activity.test.TestListActivity;
import com.project.mcr.nauczyciel02.helper.SQLiteHandler;
import com.project.mcr.nauczyciel02.helper.SessionManager;
import com.project.mcr.nauczyciel02.model.Category;
import com.project.mcr.nauczyciel02.model.Teacher;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import retrofit.RestAdapter;

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
    private String teacherName, teacherEmail;


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

        teacherName = getIntent().getStringExtra("teacherName");
        teacherEmail = getIntent().getStringExtra("teacherEmail");

        txtName.setText("Nazwa uzytkownika: "+teacherName);
        txtEmail.setText("Email: "+teacherEmail);


    }

}