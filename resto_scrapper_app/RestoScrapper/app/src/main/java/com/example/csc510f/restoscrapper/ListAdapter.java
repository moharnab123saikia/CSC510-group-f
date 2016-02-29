package com.example.csc510f.restoscrapper;

/**
 * Created by Rashmi on 2/21/2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ListAdapter extends BaseAdapter {

    MainActivity main;

    ListAdapter(MainActivity main)
    {
        this.main = main;
    }

    @Override
    public int getCount() {
        return  main.restaurants.size();
    }

    @Override
    public Object getItem(int position) {

        return main.restaurants.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

   static class ViewHolderItem {
        TextView name;
        TextView code;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolderItem holder = new ViewHolderItem();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) main.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.rowview, null);

            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.code = (TextView) convertView.findViewById(R.id.rating);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolderItem) convertView.getTag();
        }


        holder.name.setText(this.main.restaurants.get(position).name);
        holder.code.setText(this.main.restaurants.get(position).rating);

        return convertView;
    }

}

