package com.project.mcr.nauczyciel02.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.project.mcr.nauczyciel02.R;
import com.project.mcr.nauczyciel02.endpoint.RetrofitAPI;
import com.project.mcr.nauczyciel02.model.Category;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;

/**
 * Created by mikolaj.mocarski on 2016-11-29.
 */
public class AddQuestionActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private EditText questionNameInput;
    private EditText asnwerAInput;
    private EditText asnwerBInput;
    private EditText asnwerCInput;
    private EditText asnwerDInput;
    private Spinner spinner;
    private int choosenCategoryId;
    private List<HashMap<String,Object>> categoryDropList;
    private HashMap<String, Object> categoryMap;
    private List<Category> categoriesFinalList;
    private Category currentCategory;
    private RadioButton radioButton;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioButton radioButton4;
    private RadioGroup radioGroup;
    private int correctAnswer;

    static final String API_URL = "http://192.168.1.100/android_login_api2";
    RestAdapter restAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        questionNameInput = (EditText)findViewById(R.id.question);
        asnwerAInput = (EditText)findViewById(R.id.answerA);
        asnwerBInput = (EditText)findViewById(R.id.answerB);
        asnwerCInput = (EditText)findViewById(R.id.answerC);
        asnwerDInput = (EditText)findViewById(R.id.answerD);
        spinner = (Spinner)findViewById(R.id.spinner);
        radioButton = (RadioButton) findViewById(R.id.radioButton);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        radioButton4 = (RadioButton) findViewById(R.id.radioButton4);
        spinner.setOnItemSelectedListener(this);



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
            public void success(List<Category> categories, retrofit.client.Response response) {
                //Log.v("BookListActivity", booksString);
                //TypeToken<List<Book>> token = new TypeToken<List<Book>>() {};
                //List<Book> books = new Gson().fromJson(booksString, token.getType());

                categoryDropList = new ArrayList<>();
                //currentCategory = new Category(1,"");
                categoriesFinalList = new ArrayList<>();
                for(Category c: categories){
                    categoryMap = new HashMap<>();

                    try {

                        categoryMap.put(c.getClass().getField("category_id").getName(),c.getCategory_id());
                        categoryMap.put(c.getClass().getField("name").getName(),c.getName());

                        categoryDropList.add(categoryMap);
                        //currentCategory.setCategory_id(c.getCategory_id());
                        categoriesFinalList.add(c);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                }
                SimpleAdapter adapter = new SimpleAdapter(getApplication(), categoryDropList, R.layout.list_item_category,
                        new String [] {"category_id", "name"},new int [] {R.id.categoryId, R.id.categoryName});

                spinner.setAdapter(adapter);
            }



            @Override
            public void failure(RetrofitError error) {
                Log.e("CategoryListActivity", error.getMessage() +"\n"+ error.getStackTrace());
                error.printStackTrace();
            }
        };
        methods.getCategoryList(cb);



    }

    public void onClickAddQuestion(View v){
        //
        String question = questionNameInput.getText().toString().trim();
        String answerA = asnwerAInput.getText().toString().trim();
        String answerB = asnwerBInput.getText().toString().trim();
        String answerC = asnwerCInput.getText().toString().trim();
        String answerD = asnwerDInput.getText().toString().trim();
        //DODAJE DO BAZY nową kategorię
        Toast.makeText(getApplicationContext(),
                "Dodano pytanie:\n Treść: "+question+"\nA: "+answerA+"\nB: "+answerB+"\nC: "+answerC+"\nD: "+answerD, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //choosenCategoryId =  categ
        //HashMap<String, Object> item = (HashMap<String, Object>) parent.getItemAtPosition(position);
       // questionNameInput.setText(Integer.toString(categoriesFinalList.get(parent.getSelectedItemPosition()).getCategory_id()));
        //questionNameInput.setText((Integer) item.get("category_id"));
        //categoryDropList.get(position).containsValue(item);
        //questionNameInput.setText(categoriesFinalList.get(position).getName());

        //zapisujemy id kategorii pytania
        choosenCategoryId = categoriesFinalList.get(position).getCategory_id();
        //questionNameInput.setText(Integer.toString(choosenCategoryId));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onClickSelectCorrectAnswer (View view){
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButton:
                if (checked){
                    correctAnswer = 1;
                }

                    break;
            case R.id.radioButton2:
                if (checked){
                    correctAnswer = 2;
                }
                    break;
            case R.id.radioButton3:
                if (checked){
                    correctAnswer = 3;
                }
                    break;
            case R.id.radioButton4:
                if (checked){
                    correctAnswer = 4;
                }
                    break;
        }
    };
}