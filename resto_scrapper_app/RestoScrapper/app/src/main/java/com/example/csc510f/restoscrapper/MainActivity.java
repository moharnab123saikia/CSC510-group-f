package com.example.csc510f.restoscrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.csc510f.restoscrapper.FetchData.fetch_comp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;


public class MainActivity extends Activity implements fetch_comp {

    public ListView list;
    public ArrayList<Restaurants> restaurants = new ArrayList<Restaurants>();
    public ListAdapter adapter;
    public HashMap<String,String>name_res = new HashMap<String,String>();
    public HashMap<String,String>url_res = new HashMap<String,String>();
    public HashMap<String,String>rating_foursq = new HashMap<String,String>();
    public HashMap<String,String>rating_tripA = new HashMap<String,String>();
    public HashMap<String,String>rating_yelp = new HashMap<String,String>();
    public HashMap<String,String>rating_aggr = new HashMap<String,String>();
    //public HashMap<String,String>res_url;
    public HashMap<String,ArrayList<String>>reviews = new HashMap<String,ArrayList<String>>();
    public HashMap<String,ArrayList<String>>negreviews = new HashMap<String,ArrayList<String>>();
    public static dataHolder myDataHolder = new dataHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.list);
        adapter = new ListAdapter(this);
        list.setAdapter(adapter);

        EditText searchBox = (EditText)findViewById(R.id.inputSearch);

        FetchData json_data = new FetchData((fetch_comp) this);
        json_data.fetch_urldata("https://www.dropbox.com/s/qm7odahzxf06ghx/restaurants.json?dl=1");

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
               // Restaurants item = (Restaurants) adapter.getItem(position);
                Restaurants item =(Restaurants)list.getItemAtPosition(position);
                Intent intent = new Intent(getBaseContext(),listItem.class);
               if(item != null) {
                    String res_key = item.getKey();
                    intent.putExtra("restaurant_key", res_key);
                }
                startActivity(intent);

            }
        });

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
                    String res_url = res_id.getString("res_url");
                    JSONObject res_data = res_id.getJSONObject("data");

                    JSONObject foursqr_data = res_data.getJSONObject("foursquare");
                    String foursqr_count = foursqr_data.getString("count");
                    String foursqr_rating = foursqr_data.getString("rating");
                    JSONArray foursqr_review = new JSONArray(foursqr_data.getString("reviews"));
                    //negreview
                    JSONArray foursqr_neg_review = new JSONArray(foursqr_data.getString("negativeReviews"));
                    String foursqr_url = foursqr_data.getString("url");

                    JSONObject tripadvisor_data = res_data.getJSONObject("tripadvisor");
                    String tripadvisor_count = tripadvisor_data.getString("count");
                    String tripadvisor_rating = tripadvisor_data.getString("rating");
                    JSONArray tripadvisor_review = new JSONArray(tripadvisor_data.getString("reviews"));
                    //negreview
                    JSONArray tripadvisor_neg_review = new JSONArray(tripadvisor_data.getString("negativeReviews"));
                    String tripadvisor_url = tripadvisor_data.getString("url");

                    JSONObject yelp_data = res_data.getJSONObject("yelp");
                    String yelp_data_count = yelp_data.getString("count");
                    String yelp_data_rating = yelp_data.getString("rating");
                    JSONArray yelp_data_review = new JSONArray(yelp_data.getString("reviews"));
                    //negreview
                    JSONArray yelp_data_neg_review = new JSONArray(yelp_data.getString("negativeReviews"));
                    String yelp_data_url = yelp_data.getString("url");

                    Double  rating = ((Double.parseDouble(foursqr_rating)/2) + Double.parseDouble(tripadvisor_rating)+Double.parseDouble(yelp_data_rating))/3;
                    Double round_rating = Math.round(rating*10.0)/10.0;
                    String aggr_rating = round_rating.toString();
                    // Storing the data for future use

                    name_res.put(key,res_name);
                    rating_foursq.put(key,foursqr_rating);
                    rating_tripA.put(key,tripadvisor_rating);
                    rating_yelp.put(key, yelp_data_rating);
                    rating_aggr.put(key, aggr_rating);
                    url_res.put(key, res_url);
                    ArrayList<String> myString = new ArrayList<String>();

                    JSONObject reviewObj1 = new JSONObject(foursqr_review.get(0).toString());
                    myString.add(reviewObj1.getString("review"));

                    JSONObject reviewObj2 = new JSONObject(tripadvisor_review.get(0).toString());
                    myString.add(reviewObj2.getString("review"));

                    JSONObject reviewObj3 = new JSONObject(yelp_data_review.get(0).toString());
                    myString.add(reviewObj3.getString("review"));

                    reviews.put(key, myString);
                    //negreview
                    ArrayList<String> myString1 = new ArrayList<String>();

                    //Log.i("MainActivity",foursqr_neg_review.get(0).toString());
                    JSONObject negreviewObj1 = new JSONObject(foursqr_neg_review.get(0).toString());
                    myString1.add(negreviewObj1.getString("review"));

                    //Log.i("MainActivity",tripadvisor_neg_review.get(0).toString());
                    JSONObject negreviewObj2 = new JSONObject(tripadvisor_neg_review.get(0).toString());
                    myString1.add(negreviewObj2.getString("review"));

                    //Log.i("MainActivity",yelp_data_neg_review.get(0).toString());
                    JSONObject negreviewObj3 = new JSONObject(yelp_data_neg_review.get(0).toString());
                    myString1.add(negreviewObj3.getString("review"));

                    negreviews.put(key, myString1);

                    myDataHolder.setNameRes(name_res);
                    myDataHolder.setFourSqRating(rating_foursq);
                    myDataHolder.setTripARating(rating_tripA);
                    myDataHolder.setYelpRating(rating_yelp);
                    myDataHolder.setAggrRating(rating_aggr);
                    myDataHolder.setReviews(reviews);
                    myDataHolder.setNegReviews(negreviews);
                    myDataHolder.setResUrl(url_res);
                    // Storing end
                    Restaurants add=new Restaurants();
                    add.name = res_name;
                    add.rating = aggr_rating;
                    add.key = key;
                    add.setKey(key);
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
