package com.project.mcr.nauczyciel02.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mcr.nauczyciel02.R;
import com.project.mcr.nauczyciel02.endpoint.CategoryGET;
import com.project.mcr.nauczyciel02.model.Question;
import com.squareup.okhttp.OkHttpClient;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;

/**
 * Created by MCR on 24.11.2016.
 */
public class QuestionListActivity extends Activity  {

    static final String API_URL = "http://192.168.1.100/android_login_api2";
    ListView question_listview;
    RestAdapter restAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        question_listview = (ListView) findViewById(R.id.questionList) ;


        OkHttpClient mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setConnectTimeout(15000, TimeUnit.MILLISECONDS);
        mOkHttpClient.setReadTimeout(15000, TimeUnit.MILLISECONDS);

        restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .setClient(new OkClient(mOkHttpClient))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        CategoryGET methods = restAdapter.create(CategoryGET.class);


        Callback<List<Question>> cb = new Callback<List<Question>>() {

            @Override
            public void success(List<Question> questions, retrofit.client.Response response) {
                //Log.v("BookListActivity", booksString);
                //TypeToken<List<Book>> token = new TypeToken<List<Book>>() {};
                //List<Book> books = new Gson().fromJson(booksString, token.getType());

                List<HashMap<String,Object>> questionListMap = new ArrayList<>();
                for(Question q: questions){
                    HashMap<String, Object> questionMap = new HashMap<>();

                    try {

                        questionMap.put(q.getClass().getField("question_id").getName(),q.getQuestion_id());
                        questionMap.put(q.getClass().getField("name").getName(),q.getName());

                        questionListMap.add(questionMap);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                }
                SimpleAdapter adapter = new SimpleAdapter(getApplication(), questionListMap, R.layout.list_item_question,
                        new String [] {"question_id", "name"},new int [] {R.id.questionId, R.id.questionName});

                question_listview.setAdapter(adapter);
            }



            @Override
            public void failure(RetrofitError error) {
                Log.e("BookListActivity", error.getMessage() +"\n"+ error.getStackTrace());
                error.printStackTrace();
            }
        };
        methods.getQuestionListByCategory(cb);

    }

    public void onClickAddQuestionActivity(View v){
        Intent intent = new Intent(getApplicationContext(), AddQuestionActivity.class);
        startActivity(intent);
    }


}