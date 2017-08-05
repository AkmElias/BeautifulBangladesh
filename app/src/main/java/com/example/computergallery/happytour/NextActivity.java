package com.example.computergallery.happytour;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.computergallery.happytour.Adapters.OneReviewAdapter;
import com.example.computergallery.happytour.Adapters.SimilarPlaceAdapter;
import com.example.computergallery.happytour.ModelClass.OneReviewModel;
import com.example.computergallery.happytour.ModelClass.SimilarPlaceModel;
import com.example.computergallery.happytour.helper.SQLiteHandlerForOnePlace;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NextActivity extends AppCompatActivity implements OnMapReadyCallback {

    private TextView name,details,location,division,district,user_name,user_comment;
    private ImageView imageView1,imageView2,imageView3;
    Button rating,all_rating,logout;
    double lat,lon;
    SharedPreferences sharedPreferences;

    public String user_email,place_id;

    boolean login;
    private SQLiteHandlerForOnePlace dbb;
    private ProgressDialog progressDialog;

    String one_user_name,one_user_image;


    public static List<OneReviewModel> postLists = new ArrayList<>();
    RecyclerView recyclerView,recyclerViewSimilarPlace, recyclerViewRecomendedGuide;
    OneReviewAdapter postAdapter;
    LinearLayout one_review,more_review;
    int i=0;
    private GoogleMap mMap;

    SliderLayout sliderShow;



    public static List<SimilarPlaceModel> similarPlaceModelsList = new ArrayList<>();
    SimilarPlaceAdapter similarPlaceAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);



        postAdapter=new OneReviewAdapter(getApplicationContext(), postLists);
        recyclerView=(RecyclerView)findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(postAdapter);

        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Loading...");

        sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
        user_email=sharedPreferences.getString("email","null");
        login=sharedPreferences.getBoolean("login",false);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        name=(TextView)findViewById(R.id.place_name);
        details=(TextView)findViewById(R.id.place_description);
        rating=(Button)findViewById(R.id.place_rating);


        //imageView1=(ImageView)findViewById(R.id.imageView1);
        //imageView2=(ImageView)findViewById(R.id.imageView2);
        //imageView3=(ImageView)findViewById(R.id.imageView3);


        location=(TextView)findViewById(R.id.place_location);
        all_rating=(Button)findViewById(R.id.all_rating);
        logout=(Button)findViewById(R.id.logout);
        one_review=(LinearLayout) findViewById(R.id.one_review);
        more_review=(LinearLayout) findViewById(R.id.more_review);



        similarPlaceAdapter = new SimilarPlaceAdapter(getApplicationContext(),similarPlaceModelsList);

        recyclerViewSimilarPlace = (RecyclerView) findViewById(R.id.recycleViewSimilarPlace) ;
        recyclerViewRecomendedGuide = (RecyclerView) findViewById(R.id.recycleViewRecomendedGuide);





        recyclerViewSimilarPlace.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        recyclerViewSimilarPlace.setAdapter(similarPlaceAdapter);


        sliderShow = (SliderLayout) findViewById(R.id.slider);

        fetchingSimilarPlaceData();










        //new task
        // SqLite database handler
        dbb = new SQLiteHandlerForOnePlace(getApplicationContext());
        // Fetching user details from SQLite
        final HashMap<String, String> user = dbb.getUserDetails();


        name.setText(Html.fromHtml(user.get("name")));
        details.setText(Html.fromHtml(user.get("des")));
        location.setText(Html.fromHtml(user.get("loc")));
        place_id=user.get("place_id");


        gettingUserInfo();

        //rating.setText(Html.fromHtml("Rating("+user.get("rating"))+")");
        Double valuee= Double.valueOf(user.get("rating"));
        DecimalFormat twoDecimals = new DecimalFormat("#.##");
        Double d=Double.valueOf(twoDecimals.format(valuee));
        rating.setText(Html.fromHtml("Rating("+String.valueOf(d))+")");



      /*  name.setText(Html.fromHtml(getIntent().getStringExtra("name")));
        details.setText(Html.fromHtml(getIntent().getStringExtra("description")));
        rating.setText(Html.fromHtml("Rating("+getIntent().getStringExtra("rating"))+")");
        location.setText(Html.fromHtml(getIntent().getStringExtra("loc")));*/
       // user_name.setText(Html.fromHtml(getIntent().getStringExtra("user_name")));
       // user_comment.setText(Html.fromHtml(getIntent().getStringExtra("user_comment")));


        lat= Double.parseDouble(user.get("lat"));
        lon= Double.parseDouble(user.get("lon"));

        /*SharedPreferences sharedPreferences=getSharedPreferences("map",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putFloat("lat", (float) lat);
        editor.putFloat("lon", (float) lon);
        editor.commit();

        Intent intent=new Intent(AllPostDetailsActivity.this,MapsActivity.class);
        intent.putExtra("lat",lat);
        intent.putExtra("lon",lon);
        //startActivity(intent);
        */

//    TODO    Picasso.with(NextActivity.this).load(user.get("img1")).into(imageView1);
//        Picasso.with(NextActivity.this).load(user.get("img2")).into(imageView2);
//        Picasso.with(NextActivity.this).load(user.get("img3")).into(imageView3);



//
//            TextSliderView textSliderView = new TextSliderView(this);
//
//            textSliderView
//                    .description(user.get("name"))
//                    .image(user.get("img3"));
//
//
//
//
//            sliderShow.addSlider(textSliderView);



        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("Wow", user.get("img1"));
        url_maps.put("Awsome", user.get("img2"));
        url_maps.put("Beautiful", user.get("img3"));
        //url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            //.setOnSliderClickListener();

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            sliderShow.addSlider(textSliderView);
        }















        if (login==true) {
            String value = getIntent().getStringExtra("view_review");
            if (value != null) {
                if (value.equals("view_review")) {
                    gettingUserRatingInfo();
                }
            } else {
            }
        }else{
            logout.setVisibility(View.GONE);
        }




        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (login==true){
                        gettingUserRatingInfo();

                   /* Intent intent=new Intent(NextActivity.this,Give_review.class);
                    intent.putExtra("id",user.get("place_id"));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);*/
                }
                else {
                    Intent intent=new Intent(NextActivity.this,LoginActivity.class);
                    intent.putExtra("id",user.get("place_id"));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }

            }
        });


        all_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(NextActivity.this,All_review.class);
                intent.putExtra("id",user.get("place_id"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putBoolean("login",false);
                editor.commit();
                startActivity(new Intent(NextActivity.this,MainActivity.class));
                finish();

            }
        });


        gettingOneUserReview();


    }

    private void gettingUserInfo() {

        String url = "https://thelostclan.xyz/TravelTour/user_info.php";

        if (user_email != null) {

            StringRequest sq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {


                @Override
                public void onResponse(String response) {

                    try {
                        JSONArray jsonArray=new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {

                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                one_user_name=jsonObject.getString("name");
                                one_user_image=jsonObject.getString("image");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } finally {

                                progressDialog.cancel();

                            }
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                   //Toast.makeText(NextActivity.this, "SomeThing went wrong !", Toast.LENGTH_LONG).show();

                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_email", user_email);

                    return params;
                }

            };

            sq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(sq);

        }
    }








    void fetchingSimilarPlaceData(){

        recyclerViewSimilarPlace.removeAllViews();
        similarPlaceModelsList.clear();

        progressDialog.show();
        String myURL = "http://multipurpus-drone.000webhostapp.com/hotelandresortapi.php";

        JsonArrayRequest mainArrayReq= new JsonArrayRequest(myURL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if (response.isNull(0)){
                    progressDialog.cancel();
                    Toast.makeText(getApplicationContext(),"There is no event",Toast.LENGTH_LONG).show();
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        SimilarPlaceModel similarModel = new SimilarPlaceModel();


                        similarModel.setTitle(jsonObject.getString("rh_title"));
                        similarModel.setDetails(jsonObject.getString("rh_details"));
                        similarModel.setRating(jsonObject.getString("rh_rating"));
                        similarModel.setImage1(jsonObject.getString("rh_image1"));
                        similarModel.setImage2(jsonObject.getString("rh_image2"));
                        similarModel.setImage3(jsonObject.getString("rh_image3"));
                        similarModel.setLocation(jsonObject.getString("rh_location"));
                        similarModel.setId(jsonObject.getString("rh_id"));

                        similarPlaceModelsList.add(similarModel);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        postAdapter.notifyItemChanged(i);
                        progressDialog.cancel();

                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.cancel();
                Toast.makeText(getApplicationContext(),"Something went wrong !",Toast.LENGTH_LONG).show();

            }
        });

        //AppController.getInstance().addToRequestQueue(mainArrayReq);

        mainArrayReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(mainArrayReq);

    }











    private void gettingUserRatingInfo() {

        String url = "https://thelostclan.xyz/TravelTour/specific_user_review.php";

        if (user_email != null) {
            gettingUserInfo();
            progressDialog.show();


            StringRequest sq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {


                @Override
                public void onResponse(String response) {

                if(response.length()!=1){
                      progressDialog.cancel();
                      finish();
                      startActivity(new Intent(NextActivity.this, Give_review.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

                }
                    try {
                        JSONArray jsonArray=new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                        try {

                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        String rating_user_rating=jsonObject.getString("rating");
                        String rating_user_comment=jsonObject.getString("comment");
                        String rating_id=jsonObject.getString("rating_id");

                            Intent intent=new Intent(NextActivity.this,ShowUserReview.class);
                            intent.putExtra("name",one_user_name);
                            intent.putExtra("image",one_user_image);
                            intent.putExtra("rating",rating_user_rating);
                            intent.putExtra("comment",rating_user_comment);
                            intent.putExtra("rating_id",rating_id);
                            intent.putExtra("place_id",place_id);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();

                        } catch (JSONException e) {
                                e.printStackTrace();
                            } finally {

                                progressDialog.cancel();

                            }
                        }





                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(NextActivity.this, "SomeThing went wrong !", Toast.LENGTH_LONG).show();
                    progressDialog.cancel();

                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("place_id", place_id);
                    params.put("user_email", user_email);

                    return params;
                }

            };

            sq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(sq);

        }
    }
    void gettingOneUserReview(){

        recyclerView.removeAllViews();
        postLists.clear();

        progressDialog.show();
        String myURL = "https://thelostclan.xyz/TravelTour/all_review.php";

        progressDialog.show();


        StringRequest sq = new StringRequest(Request.Method.POST, myURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.length()<4){
                 all_rating.setVisibility(View.GONE);
                 recyclerView.setVisibility(View.GONE);
                 more_review.setVisibility(View.GONE);
                }

                try {
                    JSONArray jsonArray=new JSONArray(response);
                    progressDialog.cancel();
                    for (int i = 0; i < 4; i++) {
                        try {
                            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                            OneReviewModel postModel = new OneReviewModel();

                            postModel.setId(jsonObject.getString("id"));
                            postModel.setComment(jsonObject.getString("comment"));
                            postModel.setPlaceID(jsonObject.getString("place_id"));
                            postModel.setReviewDate(jsonObject.getString("review_date"));
                            postModel.setReviewRating(jsonObject.getString("rating"));
                            postModel.setReviewUserEmail(jsonObject.getString("email"));
                            postModel.setReviewUserName(jsonObject.getString("name"));
                            postModel.setReviewUserImage(jsonObject.getString("image"));


                            postLists.add(postModel);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {
                            postAdapter.notifyItemChanged(i);
                            progressDialog.cancel();

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.cancel();
                finish();
                Toast.makeText(NextActivity.this,"Network connection failed!!!",Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("place_id", place_id);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(sq);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //double lat=getIntent().getDoubleExtra("lat",0.0);
        //double lon=getIntent().getDoubleExtra("lon",0.0);
        SharedPreferences sharedPreferences=getSharedPreferences("map",MODE_PRIVATE);

        //double lat= (double) sharedPreferences.getFloat("lat", (float) 0.0);
        //double lon=(double) sharedPreferences.getFloat("lon", (float) 0.0);

        // Add a marker in Sydney and move the camera
        LatLng latLng = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(latLng).title("Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(NextActivity.this,MainActivity.class));
    }
/*
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //double lat=getIntent().getDoubleExtra("lat",0.0);
        //double lon=getIntent().getDoubleExtra("lon",0.0);
        SharedPreferences sharedPreferences=getSharedPreferences("map",MODE_PRIVATE);

        //double lat= (double) sharedPreferences.getFloat("lat", (float) 0.0);
        //double lon=(double) sharedPreferences.getFloat("lon", (float) 0.0);

        // Add a marker in Sydney and move the camera
        LatLng latLng = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(latLng).title("Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
    }*/

    @Override
    protected void onStop() {
        sliderShow.stopAutoCycle();
        super.onStop();
    }

}
