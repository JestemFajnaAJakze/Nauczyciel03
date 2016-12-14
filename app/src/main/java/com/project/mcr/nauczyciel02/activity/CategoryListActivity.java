package com.project.mcr.nauczyciel02.activity;

import android.content.Context;
import android.os.Bundle;

import com.project.mcr.nauczyciel02.R;

import com.project.mcr.nauczyciel02.helper.SQLiteHandler;
import com.project.mcr.nauczyciel02.helper.SQLiteHandlerOLD;
import com.project.mcr.nauczyciel02.helper.SessionManager;

import java.util.HashMap;
import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.project.mcr.nauczyciel02.R;

/**
 * Created by MCR on 24.11.2016.
 */
public class CategoryListActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);


        ListView categoryList = (ListView) findViewById(R.id.categoryList);


        final LinkedList<Category> categories1 = new LinkedList<Category>();
        categories1.add(new Category(1, "Matematyka"));
        categories1.add(new Category(2, "Przedmioty przyrodnicze"));
        categories1.add(new Category(3, "J. Polski"));

        CategoryAdapter adapter = new CategoryAdapter(categories1);
        categoryList.setAdapter(adapter);  //setAdapter(adapter);

        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Object object = this.getItem(position);

                Toast.makeText(getApplicationContext(), "Wybrano kategorie: " + categories1.get(position).getName(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class Category{

        private int category_id;
        private String name;

        public Category(int category_id, String name) {
            this.category_id = category_id;
            this.name = name;
        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }



    }

    private class CategoryAdapter extends BaseAdapter {



        private LinkedList<Category> categories1;
        public CategoryAdapter(LinkedList<Category> categories1) {
            this.categories1 = categories1;
        }

        @Override
        public int getCount() {
            return categories1.size();
        }

        @Override
        public Category getItem(int position) {
            return categories1.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list_item_category, parent, false);
            }

            TextView categoryName = (TextView) convertView.findViewById(R.id.categoryName);


            categoryName.setText(getItem(position).getName());

            return convertView;
        }
    }

    public void onClickAddCategoryActivity(View v){
        Intent intent = new Intent(getApplicationContext(), AddCategoryActivity.class);
        startActivity(intent);
    }
}