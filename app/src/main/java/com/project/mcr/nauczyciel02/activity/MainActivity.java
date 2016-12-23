package com.project.mcr.nauczyciel02.activity;

import android.os.Bundle;

import com.project.mcr.nauczyciel02.R;

import com.project.mcr.nauczyciel02.activity.category.CategoryListActivity;
import com.project.mcr.nauczyciel02.activity.question.QuestionListActivity;
import com.project.mcr.nauczyciel02.activity.test.TestListActivity;
import com.project.mcr.nauczyciel02.helper.SQLiteHandler;
import com.project.mcr.nauczyciel02.helper.SessionManager;
import com.project.mcr.nauczyciel02.model.Category;

import java.util.HashMap;
import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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



        final LinkedList<Category> categories1 = new LinkedList<Category>();
        categories1.add(new Category(1, "Matematyka"));
        categories1.add(new Category(2, "Przedmioty przyrodnicze"));
        categories1.add(new Category(3, "J. Polskiiii"));






        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            //logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        // Displaying the user details on the screen
        txtName.setText("Nazwa: " + name);
        txtEmail.setText("Email: " + email);

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
       /* ListView listView = (ListView) findViewById(R.id.list_options);
        listView.setOnClickListener((View.OnClickListener) itemClickListener2);*/

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