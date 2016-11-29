package com.project.mcr.nauczyciel02.activity;

import android.app.Activity;
import android.os.Bundle;

import com.project.mcr.nauczyciel02.R;

/**
 * Created by MCR on 24.11.2016.
 */
public class TestListActivity extends Activity {
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