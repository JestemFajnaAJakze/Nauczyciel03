package com.project.mcr.nauczyciel02.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.project.mcr.nauczyciel02.R;

/**
 * Created by MCR on 24.11.2016.
 */
public class QuestionListActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

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


    public void onClickAddQuestionActivity(View v){
        Intent intent = new Intent(getApplicationContext(), AddQuestionActivity.class);
        startActivity(intent);
    }
}