package com.project.mcr.nauczyciel02.activity.question;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mcr.nauczyciel02.R;
import com.project.mcr.nauczyciel02.endpoint.RetrofitAPI;
import com.project.mcr.nauczyciel02.model.Answer;
import com.project.mcr.nauczyciel02.model.Question;
import com.project.mcr.nauczyciel02.model.Test;
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

    static final String API_URL = "http://192.168.1.100/android_login_api2";
    RestAdapter restAdapter, restAdapter2;
    int chosenTestId;
    List<HashMap<String,Object>> testMapList;
    HashMap<String, Object> testMap;
    private List<Question> questionsFinalList;
    private Question question;
    private List<Answer> answerFinalList;
    private Answer answer;
    private SimpleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        chosenQuestionId = 0;
        chosenQuestionId = getIntent().getIntExtra("position", 0)+1;
        test_listview = (ListView) findViewById(R.id.test_listview);

        OkHttpClient mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setConnectTimeout(15000, TimeUnit.MILLISECONDS);
        mOkHttpClient.setReadTimeout(15000, TimeUnit.MILLISECONDS);

        restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .setClient(new OkClient(mOkHttpClient))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        RetrofitAPI methods = restAdapter.create(RetrofitAPI.class);


        Callback<List<Question>> cb = new Callback<List<Question>>() {

            @Override
            public void success(List<Question> questions, retrofit.client.Response response) {
                //Log.v("BookListActivity", booksString);
                //TypeToken<List<Book>> token = new TypeToken<List<Book>>() {};
                //List<Book> books = new Gson().fromJson(booksString, token.getType());

                question = new Question();
                List<HashMap<String,Object>> questionListMap = new ArrayList<>();
                for(Question q: questions){
                    HashMap<String, Object> questionMap = new HashMap<>();

                    try {

                        questionMap.put(q.getClass().getField("question_id").getName(),q.getQuestion_id());
                        questionMap.put(q.getClass().getField("name").getName(),q.getName());
                        question.setName(q.getName());
                        question.setQuestion_id(q.getQuestion_id());
                        questionListMap.add(questionMap);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                }

            }



            @Override
            public void failure(RetrofitError error) {
                Log.e("QuestionListActivity", error.getMessage() +"\n"+ error.getStackTrace());
                error.printStackTrace();
            }
        };
        methods.getQuestionById(chosenQuestionId,cb);



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
                //Log.v("BookListActivity", booksString);
                //TypeToken<List<Book>> token = new TypeToken<List<Book>>() {};
                //List<Book> books = new Gson().fromJson(booksString, token.getType());

                answer = new Answer();
                answerFinalList = new ArrayList<>();
                List<HashMap<String,Object>> answerListMap = new ArrayList<>();
                for(Answer q: questions){
                    HashMap<String, Object> answerMap = new HashMap<>();

                    try {

                        answerMap.put(q.getClass().getField("answer_id").getName(),q.getAnswer_id());
                        answerMap.put(q.getClass().getField("is_correct").getName(),q.getIs_correct());
                        answerMap.put(q.getClass().getField("name").getName(),q.getName());
                        answer.setName(q.getName());
                        answer.setAnswer_id(q.getAnswer_id());
                        answer.setIs_correct(q.getIs_correct());
                        question.setQuestion_id(q.getQuestion_id());
                        answerListMap.add(answerMap);
                        answerFinalList.add(answer);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                }

                adapter = new SimpleAdapter(getApplication(), answerListMap, R.layout.list_item_test,
                        new String [] {"name"},new int [] { R.id.testName});

                test_listview.setAdapter(adapter);
            }



            @Override
            public void failure(RetrofitError error) {
                Log.e("QuestionListActivity", error.getMessage() +"\n"+ error.getStackTrace());
                error.printStackTrace();
            }
        };
        methods2.getAnswerListQuestionById(chosenQuestionId,cb2);


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            TextView questionName = (TextView) findViewById(R.id.questionName);
            questionName.setText("Tresc: "+question.getName());

            //adapter.getItem(0).



        }
    }


}
