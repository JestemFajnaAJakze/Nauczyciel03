package com.project.mcr.nauczyciel02.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mcr.nauczyciel02.R;


import java.util.ArrayList;

/**
 * Created by MCR on 24.11.2016.
 */
public class QuestionListActivity extends Activity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        ListView questionList = (ListView) findViewById(R.id.questionList);
        final Question[] questions = {
                new Question(1, "Pytanie 1"),
                new Question(2, "Pytanie 2"),
                new Question(3, "Pytanie 3")
        };

        QuestionAdapter adapter = new QuestionAdapter(questions);
        questionList.setAdapter(adapter);  //setAdapter(adapter);

        questionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Wybrano pytanie " + position,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

private class Question{

    private int question_id;
    private String name;

    public Question(int question_id, String name) {
        this.question_id = question_id;
        this.name = name;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}

private class QuestionAdapter extends BaseAdapter {

    private Question[] Questions;

    public QuestionAdapter(Question[] Questions) {
        this.Questions = Questions;
    }

    @Override
    public int getCount() {
        return Questions.length;
    }

    @Override
    public Question getItem(int position) {
        return Questions[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_question, parent, false);
        }

        TextView questionName = (TextView) convertView.findViewById(R.id.questionName);


        questionName.setText(getItem(position).getName());

        return convertView;
    }
}


}