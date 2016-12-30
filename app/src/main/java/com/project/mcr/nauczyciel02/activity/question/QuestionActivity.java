package com.project.mcr.nauczyciel02.activity.question;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.project.mcr.nauczyciel02.R;
import com.project.mcr.nauczyciel02.network.RetrofitAPI;
import com.project.mcr.nauczyciel02.model.Answer;
import com.project.mcr.nauczyciel02.model.Question;
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
 * Created by MCR on 24.11.2016.
 */
public class QuestionActivity extends Activity {
    int chosenQuestionId;
    ListView test_listview;
    String categoryName, questionName;

    static final String API_URL = "http://192.168.1.100/android_login_api2";
    RestAdapter restAdapter, restAdapter2;
    int chosenTestId;
    List<HashMap<String, Object>> testMapList;
    HashMap<String, Object> testMap;
    private List<Question> questionsFinalList;
    private Question question;
    private List<Answer> answerFinalList;
    private Answer answer;


    private ArrayList<String> answerNameList;
    private ArrayList<Integer> answerIsCorrectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        chosenQuestionId = getIntent().getIntExtra("position", 0);
        categoryName = getIntent().getStringExtra("categoryName");
        questionName = getIntent().getStringExtra("questionName");


        test_listview = (ListView) findViewById(R.id.test_listview);


        OkHttpClient mOkHttpClient2 = new OkHttpClient();
        mOkHttpClient2.setConnectTimeout(15000, TimeUnit.MILLISECONDS);
        mOkHttpClient2.setReadTimeout(15000, TimeUnit.MILLISECONDS);

        restAdapter2 = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .setClient(new OkClient(mOkHttpClient2))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        RetrofitAPI methods2 = restAdapter2.create(RetrofitAPI.class);

        Callback<List<Answer>> cb2 = new Callback<List<Answer>>() {

            @Override
            public void success(List<Answer> questions, retrofit.client.Response response) {

                answerNameList = new ArrayList<>();
                answerIsCorrectList = new ArrayList<>();
                for (Answer q : questions) {

                    answerNameList.add(q.getName());
                    answerIsCorrectList.add(q.getIs_correct());


                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_list_item_1, answerNameList) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView textView = (TextView) super.getView(position, convertView, parent);
                        if (answerIsCorrectList.get(position) == 1) {
                            textView.setTextColor(Color.GREEN);
                        } else {
                            //textView.setTextColor(Color.WHITE);
                        }

                        return textView;
                    }
                };
                test_listview.setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("QuestionListActivity", error.getMessage() + "\n" + error.getStackTrace());
                error.printStackTrace();
            }
        };
        methods2.getAnswerListQuestionById(chosenQuestionId, cb2);


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            TextView questionNameTxt = (TextView) findViewById(R.id.questionName);
            questionNameTxt.setText("Tresc: " + questionName);

        }
    }


}
