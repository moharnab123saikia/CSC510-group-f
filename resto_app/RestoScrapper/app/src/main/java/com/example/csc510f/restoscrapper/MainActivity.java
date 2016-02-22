package com.example.csc510f.restoscrapper;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.csc510f.restoscrapper.FetchData.fetch_comp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;


public class MainActivity extends Activity implements fetch_comp {

    public ListView list;
    public ArrayList<Restaurants> restaurants = new ArrayList<Restaurants>();
    public ListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.list);
        adapter = new ListAdapter(this);
        list.setAdapter(adapter);

        FetchData json_data = new FetchData((fetch_comp) this);
        json_data.fetch_urldata("https://api.myjson.com/bins/2zcn3");

    }



    public void get_data(String data)
    {

        try {
            JSONArray data_array=new JSONArray(data);

            for (int i = 0 ; i < data_array.length() ; i++)
            {
                JSONObject obj=new JSONObject(data_array.get(i).toString());

                Restaurants add=new Restaurants();
                add.name = obj.getString("name");
                add.rating = obj.getString("rating");

                restaurants.add(add);

            }

            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
