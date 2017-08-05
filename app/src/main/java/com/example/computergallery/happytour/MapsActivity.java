package com.example.computergallery.happytour;



import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener,
        LocationListener {


    Object dataTrnsfer[] = new Object[2];

    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location location;
    private Location LastLocation;
    private Marker currentLocationMarker;
    public static final int REQUEST_LOCATION_CODE = 99;
    int PROXIMITY_RADIUS = 10000;
    double latitude, longitude;
    AlertDialog levelDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void vc(View v) {

        if (v.getId() == R.id.B_search) {

            final CharSequence[] items = {" HOSPITALS "," RESTAURANTS "," SCHOOLS "};

            // Creating and Building the Dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            //builder.setTitle("Select The ");
            builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {



                    if(item==0) {

                        mMap.clear();
                        String hospital = "hospital";
                        String url = getUrl(latitude, longitude, hospital);
                        String la = "" + latitude;
                        dataTrnsfer[0] = mMap;
                        dataTrnsfer[1] = url;
                        GetNearbyPlaces getNearbyPlaces = new GetNearbyPlaces();
                        getNearbyPlaces.execute(dataTrnsfer);
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.

                            return;
                        }
                        mMap.setMyLocationEnabled(true);
                        LatLng latlang = new LatLng(latitude, longitude);

                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latlang);
                        markerOptions.title("current Location");
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                        currentLocationMarker = mMap.addMarker(markerOptions);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlang));
                        // mMap.animateCamera(CameraUpdateFactory.zoomBy(10));
                        Toast.makeText(MapsActivity.this, "Showing nearByospitals", Toast.LENGTH_LONG).show();

                    }
                    else if(item==1) {

                        mMap.clear();
                        String restaurant = "restaurant";
                        String url = getUrl(latitude, longitude, restaurant);

                        dataTrnsfer[0] = mMap;
                        dataTrnsfer[1] = url;

                        GetNearbyPlaces getNearbyPlaces = new GetNearbyPlaces();
                        getNearbyPlaces.execute(dataTrnsfer);
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            mMap.setMyLocationEnabled(true);
                            return;
                        }
                        LatLng latlang = new LatLng(latitude, longitude);

                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latlang);
                        markerOptions.title("current Location");
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                        currentLocationMarker = mMap.addMarker(markerOptions);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlang));
                        //mMap.animateCamera(CameraUpdateFactory.zoomBy(10));

                        Toast.makeText(MapsActivity.this, "Showing nearByRestaurunts", Toast.LENGTH_LONG).show();

                    }
                    else if(item==2) {

                        Toast.makeText(MapsActivity.this, "Showing nearBySchools", Toast.LENGTH_LONG).show();
                        mMap.clear();
                        String school = "school";
                        String url = getUrl(latitude, longitude, school);

                        dataTrnsfer[0] = mMap;
                        dataTrnsfer[1] = url;

                        GetNearbyPlaces getNearbyPlaces = new GetNearbyPlaces();
                        getNearbyPlaces.execute(dataTrnsfer);
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            mMap.setMyLocationEnabled(true);

                            return;
                        }
                        LatLng latlang = new LatLng(latitude, longitude);

                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latlang);
                        markerOptions.title("current Location");
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                        currentLocationMarker = mMap.addMarker(markerOptions);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlang));
                        //mMap.animateCamera(CameraUpdateFactory.zoomBy(10));


                        // Your code when 3rd option seletced
                    }



                    levelDialog.dismiss();
                }
            });
            levelDialog = builder.create();
            levelDialog.show();

           /* String location = "";
            EditText et = (EditText) findViewById(R.id.TF_location);
            location = et.getText().toString();
            List<Address> addresses = null;
            MarkerOptions mo = new MarkerOptions();

            if (!location.equals("")) {

                Geocoder geocoder = new Geocoder(this);
                try {
                    addresses = geocoder.getFromLocationName(location, 5);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < addresses.size(); i++) {


                    Address addres = addresses.get(i);
                    LatLng ll = new LatLng(addres.getLatitude(), addres.getLongitude());
                    mo.position(ll);
                    mo.title("searchResult");
                    mMap.addMarker(mo);
                    // mo.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(ll));


                }
            }


            Toast.makeText(this, "invalid", Toast.LENGTH_LONG).show();*/

        }

    }
    private  String getUrl(Double latitude,Double longitude,String nearbyPlaces){

        StringBuilder googlrPlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlrPlaceUrl.append("location="+latitude+","+longitude);
        googlrPlaceUrl.append("&radius="+PROXIMITY_RADIUS);
        googlrPlaceUrl.append("&type="+nearbyPlaces);
        googlrPlaceUrl.append("&sensor=true");
        googlrPlaceUrl.append("&key="+"AIzaSyByQDwGMj0RkK8xqybI4ejN9NVxRVx2HVs");


        return googlrPlaceUrl.toString();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        switch(requestCode){

            case REQUEST_LOCATION_CODE:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){

                        if(client==null){
                            buildGoogleApi();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else{

                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED) {
            buildGoogleApi();
            mMap.setMyLocationEnabled(true);
        }
        mMap.setOnMarkerClickListener(this);


    }
    protected synchronized void buildGoogleApi(){

        client = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        client.connect();
    }


    @Override
    public void onLocationChanged(Location location) {

        LastLocation = location;

        if(currentLocationMarker!=null){

            currentLocationMarker.remove();
        }
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        LatLng latlang = new LatLng(location.getLatitude(),location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latlang);
        markerOptions.title("current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        currentLocationMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlang));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(10));

        if(client!=null){

            LocationServices.FusedLocationApi.removeLocationUpdates(client,this);
        }

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED) {


            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest,this);
        }


    }
    public boolean checkLocationPermission(){

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){

                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION_CODE);
            }
            else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION_CODE);

            }
            return false;
        }
        return true;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Geocoder geocoder  = new Geocoder(getApplicationContext(), Locale.getDefault());
        String lavel = new Date().toString();

        List<Address>listAddress = null;
        try {
            listAddress = geocoder.getFromLocation(latitude,longitude,1);
            if(listAddress!=null&&listAddress.size()>0){

                lavel = listAddress.get(0).getAddressLine(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(this,lavel,Toast.LENGTH_LONG).show();
        return false;
    }
}
