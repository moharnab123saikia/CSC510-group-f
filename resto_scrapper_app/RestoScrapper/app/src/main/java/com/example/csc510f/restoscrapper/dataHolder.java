package com.example.csc510f.restoscrapper;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Rashmi on 2/28/2016.
 */
public class dataHolder {

    private HashMap<String,String>name_res;
    private HashMap<String,String>url_yelp;
    private HashMap<String,String>url_tripA;
    private HashMap<String,String>url_foursq;
    private HashMap<String,String>url_res;
    private HashMap<String,String>rating_foursq;
    private HashMap<String,String>rating_tripA;
    private HashMap<String,String>rating_yelp;
    private HashMap<String,String>rating_aggr;
    //public HashMap<String,String>res_url;
    private HashMap<String,ArrayList<String>>reviews;
    private HashMap<String,ArrayList<String>>negreviews;

    public String getRestName(String key)
    {
        return name_res.get(key);
    }

    public String getFourSqrRating(String key)
    {
        return rating_foursq.get(key);
    }
    public String getTripARating(String key)
    {
        return rating_tripA.get(key);
    }
    public String getyelpRating(String key)
    {
        return rating_yelp.get(key);
    }
    public String getAgrRating(String key)
    {
        return rating_aggr.get(key);
    }
    public String getResUrl(String key)
    {
        return url_res.get(key);
    }
    public String getYelpURL(String key)
    {
        return url_yelp.get(key);
    }
    public String getTripAURL(String key)
    {
        return url_tripA.get(key);
    }
    public String getFourSqrURL(String key)
    {
        return url_foursq.get(key);
    }
    public ArrayList<String> getReviews(String key)
    {
        return reviews.get(key);
    }
    public ArrayList<String> getNegReviews(String key)
    {
        //Log.i("dataholder", negreviews.get(key).get(0));
        return negreviews.get(key);
    }
    public void setNameRes(HashMap<String,String>name_res)
    {
        this.name_res = name_res;
    }
    public void setResUrl(HashMap<String,String>url_res)
    {
        this.url_res = url_res;
    }
    public void setYelpURL(HashMap<String,String>url_yelp)
    {
        this.url_yelp = url_yelp;
    }
    public void setTripAURL(HashMap<String,String>url_tripA)
    {
        this.url_tripA = url_tripA;
    }
    public void setFourSqrURL(HashMap<String,String>url_foursq)
    {
        this.url_foursq = url_foursq;
    }
    public void setFourSqRating(HashMap<String,String>rating_foursq)
    {
        this.rating_foursq = rating_foursq;
    }
    public void setTripARating(HashMap<String,String>rating_tripA)
    {
        this.rating_tripA = rating_tripA;
    }
    public void setYelpRating(HashMap<String,String>rating_yelp)
    {
        this.rating_yelp = rating_yelp;
    }

    public void setAggrRating(HashMap<String,String>rating_aggr)
    {
        this.rating_aggr = rating_aggr;
    }

    public void setReviews(HashMap<String,ArrayList<String>>reviews)
    {
        this.reviews = reviews;
    }
    public void setNegReviews(HashMap<String,ArrayList<String>>negreviews)
    {
        this.negreviews = negreviews;
    }

//    private static final DataHolder holder = new DataHolder();
//    public static DataHolder getInstance() {return holder;}
}
