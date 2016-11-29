package com.project.mcr.nauczyciel02.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;

import com.project.mcr.nauczyciel02.R;

import java.util.List;

/**
 * Created by mikolaj.mocarski on 2016-11-29.
 */

 class Question2 {

     int _id;
     String name;
     boolean isSelected = false;

    public Question2(int i, String s) {
        this._id=i;
        this.name=s;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }



}

public class QuestionAdapter extends ArrayAdapter<Question2> {

    private Context context;
    private List<Question2> questionList;


    public QuestionAdapter(List<Question2> questionList, Context context) {
        super(context, R.layout.activity_single_question);
        this.questionList=questionList;
        this.context=context;
    }

    private static class QuestionHolder{
        public CheckBox checkBox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        QuestionHolder holder = new QuestionHolder();
        if(convertView==null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.activity_single_question, null);
            holder.checkBox = (CheckBox)v.findViewById(R.id.questionCheckBox);

            holder.checkBox.setOnCheckedChangeListener((QuestionListActivity)context);

        }else {
            holder=(QuestionHolder)v.getTag();
        }
        Question2 q = questionList.get(position);
        holder.checkBox.setText(q.getName());
        holder.checkBox.setChecked(q.isSelected());
        holder.checkBox.setTag(q);


        return v;
    }
}
