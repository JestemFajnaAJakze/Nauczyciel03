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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mcr.nauczyciel02.R;

import java.util.LinkedList;

/**
 * Created by MCR on 24.11.2016.
 */
public class TestListActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);

        ListView testList = (ListView) findViewById(R.id.testList);


        final LinkedList<Test> tests1 = new LinkedList<Test>();
        tests1.add(new Test(1,1,"Matematyka 001"));
        tests1.add(new Test(2,1,"Matematyka 002"));
        tests1.add(new Test(3,1,"Matematyka 003"));

        TestAdapter adapter = new TestAdapter(tests1);
        testList.setAdapter(adapter);

        testList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Wybrano test " + tests1.get(position).getName(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class Test{
        private int test_Id;
        private int category_Id;
        private String name;


        public Test(int test_Id, int category_Id, String name) {
            this.test_Id = test_Id;
            this.category_Id = category_Id;
            this.name = name;
        }

        public int getTest_Id() {
            return test_Id;
        }

        public void setTest_Id(int test_Id) {
            this.test_Id = test_Id;
        }

        public int getCategory_Id() {
            return category_Id;
        }

        public void setCategory_Id(int category_Id) {
            this.category_Id = category_Id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    private class TestAdapter extends BaseAdapter {



        private LinkedList<Test> tests1;

        public TestAdapter(LinkedList<Test> tests1) {
            this.tests1 = tests1;
        }

        @Override
        public int getCount() {
            return tests1.size();
        }

        @Override
        public Test getItem(int position) {
            return tests1.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list_item_test, parent, false);
            }

            //ImageView TestImage = (ImageView) convertView.findViewById(R.id.Test_image);
            TextView TestName = (TextView) convertView.findViewById(R.id.testName);


            TestName.setText(getItem(position).getName());

            return convertView;
        }
    }


    public void onClickAddTestActivity(View v){
        Intent intent = new Intent(getApplicationContext(), AddTestActivity.class);
        startActivity(intent);
    }

    public void onClickBackButton(View v){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}