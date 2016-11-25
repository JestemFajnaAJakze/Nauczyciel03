package com.project.mcr.nauczyciel02.activity;

import android.os.Bundle;

import com.project.mcr.nauczyciel02.R;

import com.project.mcr.nauczyciel02.helper.SQLiteHandler;
import com.project.mcr.nauczyciel02.helper.SQLiteHandlerOLD;
import com.project.mcr.nauczyciel02.helper.SessionManager;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;


import com.project.mcr.nauczyciel02.R;

/**
 * Created by MCR on 24.11.2016.
 */
public class CategoryListActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

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
}