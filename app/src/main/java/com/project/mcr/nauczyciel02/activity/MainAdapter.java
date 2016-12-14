package com.project.mcr.nauczyciel02.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.LinkedList;

/**
 * Created by mikolaj.mocarski on 2016-12-14.
 */
public abstract class MainAdapter extends BaseAdapter {

    private LinkedList<?>list;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public abstract View getView(int i, View view, ViewGroup viewGroup);
}
