package com.project.mcr.nauczyciel02.activity;

import android.os.Bundle;

import com.project.mcr.nauczyciel02.R;

import com.project.mcr.nauczyciel02.helper.SQLiteHandler;
import com.project.mcr.nauczyciel02.helper.SQLiteHandlerOLD;
import com.project.mcr.nauczyciel02.helper.SessionManager;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView txtName;
    private TextView txtEmail;
    private Button btnLogout;
    private Button categoryButton;
    private Button questionButton;
    private Button testButton;

    private SQLiteHandler db;
    private SessionManager session;


    public void onClickCategory(View v) {
        Intent intent = new Intent(getApplicationContext(), CategoryListActivity.class);
        startActivity(intent);
    }

    ;


    public void onClickQuestion(View v) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    ;


    public void onClickTest(View v) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }

    ;

    public void onClicklogoutUser(View v) {
        logoutUser();
    }

    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtName = (TextView) findViewById(R.id.name);
        txtEmail = (TextView) findViewById(R.id.email);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        /*categoryButton = (Button) findViewById(R.id.categoryButton);
        questionButton = (Button) findViewById(R.id.questionButton);
        testButton = (Button) findViewById(R.id.testButton);*/


        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        // Displaying the user details on the screen
        txtName.setText("Nazwa: " + name);
        txtEmail.setText("email: " + email);

        // Logout button click event
        /*btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });*/
        AdapterView.OnItemClickListener itemClickListener2 = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        };
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> listView, View v, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(getApplicationContext(), CategoryListActivity.class);
                    startActivity(intent);


                }
            }
        };
        ListView listView = (ListView) findViewById(R.id.list_options);
        listView.setOnClickListener((View.OnClickListener) itemClickListener2);

        /**
         * Logging out the user. Will set isLoggedIn flag to false in shared
         * preferences Clears the user data from sqlite users table
         * */

    }

    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}