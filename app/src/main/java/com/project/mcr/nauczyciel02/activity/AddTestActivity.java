package com.project.mcr.nauczyciel02.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.project.mcr.nauczyciel02.R;

/**
 * Created by mikolaj.mocarski on 2016-11-29.
 */
public class AddTestActivity extends Activity {

    private EditText questionNameInput;
    private EditText asnwerAInput;
    private EditText asnwerBInput;
    private EditText asnwerCInput;
    private EditText asnwerDInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        questionNameInput = (EditText)findViewById(R.id.question);
        asnwerAInput = (EditText)findViewById(R.id.answerA);
        asnwerBInput = (EditText)findViewById(R.id.answerB);
        asnwerCInput = (EditText)findViewById(R.id.answerC);
        asnwerDInput = (EditText)findViewById(R.id.answerD);

    }

    public void onClickAddTest(View v){
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

    public void onClickBackButton(View v){
        Intent intent = new Intent(getApplicationContext(), TestListActivity.class);
        startActivity(intent);
    }

}