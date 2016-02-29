package com.example.csc510f.restoscrapper;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.HashMap;


public class listItem extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);
       Intent intent =  getIntent();
       String key = (String)intent.getSerializableExtra("restaurant_key");
        Log.i("ListItem: ", MainActivity.myDataHolder.getRestName(key));

        TextView resName = (TextView) findViewById(R.id.resName);
        resName.setText(MainActivity.myDataHolder.getRestName(key));

        RatingBar yelpRateBar = (RatingBar)findViewById(R.id.ratingBarYelp);
        yelpRateBar.setRating(Float.parseFloat(MainActivity.myDataHolder.getyelpRating(key)));

        TextView yelpRating = (TextView) findViewById(R.id.yelp);
        yelpRating.setText("Yelp: "+MainActivity.myDataHolder.getyelpRating(key));

        TextView tripRating = (TextView) findViewById(R.id.tripAdvisor);
        tripRating.setText("TripAdvisor: "+MainActivity.myDataHolder.getTripARating(key));

        TextView fsRating = (TextView) findViewById(R.id.fourSquare);
        fsRating.setText("FourSquare: "+MainActivity.myDataHolder.getFourSqrRating(key));

        TextView agrRating = (TextView) findViewById(R.id.average_rating);
        agrRating.setText("Average Rating: "+MainActivity.myDataHolder.getAgrRating(key));

        TextView review_1 = (TextView) findViewById(R.id.review1);
        review_1.setText("customer1: "+ MainActivity.myDataHolder.getReviews(key).get(0));

        TextView review_2 = (TextView) findViewById(R.id.review2);
        review_2.setText("customer2: "+MainActivity.myDataHolder.getReviews(key).get(1));

        TextView review_3 = (TextView) findViewById(R.id.review3);
        review_3.setText("customer3: "+MainActivity.myDataHolder.getReviews(key).get(2));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
