package com.example.csc510f.restoscrapper;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.csc510f.restoscrapper.FetchData.fetch_comp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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
        json_data.fetch_urldata("https://s3.amazonaws.com/restoscrapper/restaurants.json");

    }



    public void get_data(String data)
    {

        try {
            Log.i("get_data", "In get data");
            //JSONArray data_array=new JSONArray(data);
            JSONObject  data_array = new JSONObject(data);
            JSONArray  test_array = new JSONArray(data_array.getString("restaurants"));
            Log.i("get_data", "In get data"+data_array.length());
            for(int i=0; i<test_array.length(); i++)
            {
                JSONObject obj=new JSONObject(test_array.get(i).toString());
                Iterator iterator = obj.keys();
                while(iterator.hasNext()){
                    String key = (String)iterator.next();
                    JSONObject res_id = obj.getJSONObject(key);
                    String res_name = res_id.getString("name");
                    JSONObject res_data = res_id.getJSONObject("data");

                    JSONObject foursqr_data = res_data.getJSONObject("foursquare");
                    String foursqr_count = foursqr_data.getString("count");
                    String foursqr_rating = foursqr_data.getString("rating");
                    JSONArray foursqr_review = new JSONArray(foursqr_data.getString("reviews"));
                    String foursqr_url = foursqr_data.getString("url");

                    JSONObject tripadvisor_data = res_data.getJSONObject("tripadvisor");
                    String tripadvisor_count = tripadvisor_data.getString("count");
                    String tripadvisor_rating = tripadvisor_data.getString("rating");
                    JSONArray tripadvisor_review = new JSONArray(tripadvisor_data.getString("reviews"));
                    String tripadvisor_url = tripadvisor_data.getString("url");

                    JSONObject yelp_data = res_data.getJSONObject("yelp");
                    String yelp_data_count = yelp_data.getString("count");
                    String yelp_data_rating = yelp_data.getString("rating");
                    JSONArray yelp_data_review = new JSONArray(yelp_data.getString("reviews"));
                    String yelp_data_url = yelp_data.getString("url");

                    Double  rating = ((Double.parseDouble(foursqr_rating)/2) + Double.parseDouble(tripadvisor_rating)+Double.parseDouble(yelp_data_rating))/3;
                    Double round_rating = Math.round(rating*10.0)/10.0;
                    String aggr_rating = round_rating.toString();
                    Restaurants add=new Restaurants();
                    add.name = res_name;
                    add.rating = aggr_rating;
                    restaurants.add(add);
                    Log.i("get_data", "In get data"+data_array.length());
                }
            }


            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
