package com.project.mcr.nauczyciel02.activity.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.project.mcr.nauczyciel02.R;
import com.project.mcr.nauczyciel02.endpoint.RetrofitAPI;
import com.project.mcr.nauczyciel02.model.Category;
import com.project.mcr.nauczyciel02.model.Question;
import com.project.mcr.nauczyciel02.model.SchoolClass;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.HashMap;
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
public class TestAssignedToClassActivity extends Activity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private EditText questionNameInput;
    private EditText asnwerAInput;
    private EditText asnwerBInput;
    private EditText asnwerCInput;
    private EditText asnwerDInput;
    private Spinner spinner;
    private int choosenCategoryId;
    private List<HashMap<String, Object>> categoryDropList;
    private HashMap<String, Object> categoryMap;
    private List<Category> categoriesFinalList;
    private ListView choosenQuestionList;
    private List<Question> finalQuestionList;
    private int currentTestId;
    static final String API_URL = "http://192.168.1.100/android_login_api2";
    RestAdapter restAdapter;
    RestAdapter restAdapter2;
    private Question currentQuestion;
    private ArrayList<String> classesNameList, categoryNameList;
    private ArrayList<Integer> classesIdList, categoryIdList, selectedClasses;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigned_to_class);
        currentTestId = getIntent().getIntExtra("currentTestId", 0);

        Toast.makeText(getApplicationContext(),
                "TestId: "+currentTestId, Toast.LENGTH_SHORT)
                .show();

        choosenQuestionList = (ListView) findViewById(R.id.choosenQuestionList);

        OkHttpClient mOkHttpClient3 = new OkHttpClient();
        mOkHttpClient3.setConnectTimeout(15000, TimeUnit.MILLISECONDS);
        mOkHttpClient3.setReadTimeout(15000, TimeUnit.MILLISECONDS);

        restAdapter2 = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .setClient(new OkClient(mOkHttpClient3))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        RetrofitAPI methods3 = restAdapter2.create(RetrofitAPI.class);


        classesIdList = new ArrayList<>();
        classesNameList = new ArrayList<>();
        Callback<List<SchoolClass>> cb3 = new Callback<List<SchoolClass>>() {

            @Override
            public void success(List<SchoolClass> classes, Response response) {


                for (SchoolClass sc : classes) {

                    classesIdList.add(sc.getSchoolclass_id());
                    classesNameList.add(sc.getName());
                }

                adapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_list_item_multiple_choice, classesNameList);

                choosenQuestionList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                choosenQuestionList.setAdapter(adapter);

            }


            @Override
            public void failure(RetrofitError error) {
                Log.e("AddQuestionListActivity", error.getMessage() + "\n" + error.getStackTrace());
                error.printStackTrace();
            }
        };
        methods3.getClassesAll(cb3);


    }


    public void onClickAddTest(View v) {


        selectedClasses = new ArrayList<>();
        SparseBooleanArray checked = choosenQuestionList.getCheckedItemPositions();
        ArrayList<String> selectedItems = new ArrayList<String>();
        for (int i = 0; i < checked.size(); i++) {
            // Item position in adapter
            int position = checked.keyAt(i);
            // Add sport if it is checked i.e.) == TRUE!
            if (checked.valueAt(i)) {
                selectedItems.add(adapter.getItem(position));
                selectedClasses.add(classesIdList.get(position));
            }
            Toast.makeText(getApplicationContext(),
                    "Ile wybralem: "+selectedClasses.size()+", testId: "+currentTestId, Toast.LENGTH_SHORT)
                    .show();

        }

        for (int x = 0; x < selectedClasses.size(); x++) {


            OkHttpClient mOkHttpClient2 = new OkHttpClient();
            mOkHttpClient2.setConnectTimeout(15000, TimeUnit.MILLISECONDS);
            mOkHttpClient2.setReadTimeout(15000, TimeUnit.MILLISECONDS);

            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(API_URL)
                    .setClient(new OkClient(mOkHttpClient2))
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();
            RetrofitAPI methods2 = restAdapter.create(RetrofitAPI.class);


            Callback<List<SchoolClass>> cb2 = new Callback<List<SchoolClass>>() {

                @Override
                public void success(List<SchoolClass> tests, Response response) {

                }


                @Override
                public void failure(RetrofitError error) {
                    Log.e("TestAddActivity", error.getMessage() + "\n" + error.getStackTrace());
                    error.printStackTrace();
                }
            };
            methods2.addTestClass(currentTestId, selectedClasses.get(x), cb2);

        }


        /*Intent intent = new Intent(getApplicationContext(), TestListActivity.class);
        startActivity(intent);*/


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {

    }
}