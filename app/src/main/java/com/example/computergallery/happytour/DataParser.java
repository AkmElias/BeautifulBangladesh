package com.example.computergallery.happytour;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Elias on 13-07-2017.
 */

public class DataParser {

    private HashMap<String,String> getPlace(JSONObject googlePlaceJson){

        HashMap<String,String>googlePlaceMap = new HashMap<>();

        String placeName = "-NA-";
        String vacinity = "-NA-";
        String latitude = "";
        String longnitude = "";
        String refference = "";

        if(!googlePlaceJson.isNull("name")){
            try {
                placeName = googlePlaceJson.getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(!googlePlaceJson.isNull("vacinity")){

            try {
                vacinity = googlePlaceJson.getString("vacinity");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            latitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longnitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");
            refference = googlePlaceJson.getString("reference");
            googlePlaceMap.put("place_name",placeName);
            googlePlaceMap.put("vacinity",vacinity);
            googlePlaceMap.put("lat",latitude);
            googlePlaceMap.put("lng",longnitude);
            googlePlaceMap.put("reference",refference);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  googlePlaceMap;
    }
    private List<HashMap<String,String>> getPlaces(JSONArray jsonArray){
        int count = jsonArray.length();
        List<HashMap<String,String>>placeslist = new ArrayList<>();
        HashMap<String,String>placeMap = null;
        for(int i = 0;i<count;i++){

            try {
                placeMap = getPlace((JSONObject)jsonArray.get(i));
                placeslist.add(placeMap);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return placeslist;
    }

    public List<HashMap<String,String>> parse(String jsondata){

        JSONArray jsonArray  = null;
        JSONObject jsonObject ;
        try {
            jsonObject = new JSONObject(jsondata);
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getPlaces(jsonArray);
    }
}

