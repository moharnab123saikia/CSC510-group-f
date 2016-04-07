package com.example.csc510f.restoscrapper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;



public class listItem extends Activity {
    static int clickcount =0;
    String file = "myClickFile.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);
       Intent intent =  getIntent();
       String key = (String)intent.getSerializableExtra("restaurant_key");
        Log.i("ListItem: ", MainActivity.myDataHolder.getRestName(key));

        TextView resName = (TextView) findViewById(R.id.resName);
        resName.setText(MainActivity.myDataHolder.getRestName(key));

        RatingBar aggrRateBar = (RatingBar)findViewById(R.id.ratingBarAggr);
        aggrRateBar.setRating(Float.parseFloat(MainActivity.myDataHolder.getAgrRating(key)));
        //aggrRateBar.setStepSize(Float.parseFloat(MainActivity.myDataHolder.getAgrRating(key)));

        TextView yelpRating = (TextView) findViewById(R.id.yelp);
        yelpRating.setText(" "+MainActivity.myDataHolder.getyelpRating(key)+"/5");

        TextView tripRating = (TextView) findViewById(R.id.tripAdvisor);
        tripRating.setText(" "+MainActivity.myDataHolder.getTripARating(key)+"/5");

        TextView fsRating = (TextView) findViewById(R.id.fourSquare);
        fsRating.setText(" "+MainActivity.myDataHolder.getFourSqrRating(key)+"/10");

        TextView agrRating = (TextView) findViewById(R.id.average_rating);
        agrRating.setText(" "+MainActivity.myDataHolder.getAgrRating(key)+"/5");

        TextView review_1 = (TextView) findViewById(R.id.review1);
        review_1.setText("#1: "+ MainActivity.myDataHolder.getReviews(key).get(0));

        TextView review_2 = (TextView) findViewById(R.id.review2);
        review_2.setText("#2: "+MainActivity.myDataHolder.getReviews(key).get(1));

        TextView review_3 = (TextView) findViewById(R.id.review3);
        review_3.setText("#3: "+MainActivity.myDataHolder.getReviews(key).get(2));

       // Log.i("negreviewLog", MainActivity.myDataHolder.getNegReviews(key).get(0));

        TextView negreview_1 = (TextView) findViewById(R.id.negreview1);
        negreview_1.setText("#1: "+ MainActivity.myDataHolder.getNegReviews(key).get(0));

       // Log.i("negreviewlog", MainActivity.myDataHolder.getNegReviews(key).get(1));
        TextView negreview_2 = (TextView) findViewById(R.id.negreview2);
        negreview_2.setText("#2: "+MainActivity.myDataHolder.getNegReviews(key).get(1));

        //Log.i("negreviewlog", MainActivity.myDataHolder.getNegReviews(key).get(2));

        TextView negreview_3 = (TextView) findViewById(R.id.negreview3);
        negreview_3.setText("#3: "+MainActivity.myDataHolder.getNegReviews(key).get(2));

        final String url = MainActivity.myDataHolder.getResUrl(key);

        Button button = (Button)findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse(url));
                clickcount=clickcount+1;
                Log.i("clickcount Inside", clickcount+"");
                try {
                    FileOutputStream fileout=openFileOutput(file, MODE_APPEND);
                    OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
                    outputWriter.write(clickcount + "");
                    outputWriter.close();

                    //disp0lay file saved message
                    Log.i("test_toast", "file created");
                    Toast.makeText(getBaseContext(), "File saved successfully!",
                            Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                startActivity(viewIntent);
            }
        });
       // Log.i("clickcount Outside", clickcount+"");

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
