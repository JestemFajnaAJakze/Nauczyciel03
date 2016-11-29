package com.project.mcr.nauczyciel02.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import com.project.mcr.nauczyciel02.R;

import java.util.ArrayList;

/**
 * Created by MCR on 24.11.2016.
 */
public class QuestionListActivity extends Activity implements CompoundButton.OnCheckedChangeListener {


    ListView lv;
    ArrayList<Question2> question2List;
    QuestionAdapter questionAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);
        lv = (ListView)findViewById(R.id.listView);
        displayPlanetList();





        /*AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnClickListener(){
            @Override
            public void onClick(View v) {

            }

            public void onItemClick(AdapterView<?> listView, View v, int position, long id){
                if (position == 0 ){
                    Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                    startActivity(intent);
                }
            };
        ;};*/

    }

    private void displayPlanetList(){
        question2List= new ArrayList<Question2>();
        question2List.add(new Question2(1,"Pytanie 1"));
        question2List.add(new Question2(2,"Pytanie 2"));
        question2List.add(new Question2(3,"Pytanie 3"));
        question2List.add(new Question2(4,"Pytanie 4"));

        questionAdapter = new QuestionAdapter(question2List, this);
        lv.setAdapter(questionAdapter);
    }


    public void onClickAddQuestionActivity(View v){
        Intent intent = new Intent(getApplicationContext(), AddQuestionActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

        int pos = lv.getPositionForView(compoundButton);
        if(pos!=ListView.INVALID_POSITION){
            Question2 q = question2List.get(pos);
            q.setSelected(isChecked);

            Toast.makeText(getApplicationContext(),
                    "Wybrano pytanie: "+q.getName(), Toast.LENGTH_LONG)
                    .show();
        }
    }
}