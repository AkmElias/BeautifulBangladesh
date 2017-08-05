package com.example.computergallery.happytour;
import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Elias on 13-07-2017.
 */

public class GetNearbyPlaces extends AsyncTask<Object,String,String>{
    String googlePlace;
    String url;
    GoogleMap mMap;

    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap)objects[0];
        String url = (String) objects[1];
        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            googlePlace = downloadUrl.readUrll(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return googlePlace;
    }

    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String,String>> nearByPlaceList = null;
        DataParser parser = new DataParser();
        nearByPlaceList = parser.parse(s);
        ShowNearByPlaces(nearByPlaceList);
    }

    private  void ShowNearByPlaces(List<HashMap<String,String>> nearByPlacesList){

        for(int i = 0;i<nearByPlacesList.size();i++){

            MarkerOptions mo = new MarkerOptions();
            HashMap<String,String>place = nearByPlacesList.get(i);
            String placeNmae = place.get("place_name");
            String vacinity = place.get("vacinity");
            double lat = Double.parseDouble(place.get("lat"));
            double lon = Double.parseDouble(place.get("lng"));

            LatLng latLng = new LatLng(lat,lon);
            mo.position(latLng);
            mo.title(placeNmae+" "+vacinity);
            mo.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

            mMap.addMarker(mo);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));




        }
    }
}
