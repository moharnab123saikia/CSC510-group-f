package com.example.csc510f.restoscrapper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Rashmi on 2/28/2016.
 */
public class dataHolder {

    private HashMap<String,String>name_res;
    private HashMap<String,String>rating_foursq;
    private HashMap<String,String>rating_tripA;
    private HashMap<String,String>rating_yelp;
    private HashMap<String,String>rating_aggr;
    //public HashMap<String,String>res_url;
    private HashMap<String,ArrayList<String>>reviews;

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
    public ArrayList<String> getReviews(String key)
    {
        return reviews.get(key);
    }
    public void setNameRes(HashMap<String,String>name_res)
    {
        this.name_res = name_res;
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

//    private static final DataHolder holder = new DataHolder();
//    public static DataHolder getInstance() {return holder;}
}
