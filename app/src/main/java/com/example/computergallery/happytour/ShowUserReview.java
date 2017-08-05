package com.example.computergallery.happytour;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.computergallery.happytour.helper.SQLiteHandlerForOnePlace;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShowUserReview extends AppCompatActivity {
    TextView comment,title;
    Button user_name;
    ImageButton r1,r2,r3,r4,r5;
    Button btn_edit,btn_delete,btn_rating;
    ImageButton imageView;
    int user_review;
    String rating_id,place_id;
    private SQLiteHandlerForOnePlace dbb;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_review);


        // SQLite database handler
        dbb = new SQLiteHandlerForOnePlace(getApplicationContext());


        user_name=(Button)findViewById(R.id.card_name);
        comment=(TextView)findViewById(R.id.card_title);
        imageView=(ImageButton)findViewById(R.id.card_image);
        title=(TextView)findViewById(R.id.title);

        btn_delete=(Button)findViewById(R.id.btn_delete);
        r1=(ImageButton)findViewById(R.id.r1);
        r2=(ImageButton)findViewById(R.id.r2);
        r3=(ImageButton)findViewById(R.id.r3);
        r4=(ImageButton)findViewById(R.id.r4);
        r5=(ImageButton)findViewById(R.id.r5);

        r1.setVisibility(View.GONE);
        r2.setVisibility(View.GONE);
        r3.setVisibility(View.GONE);
        r4.setVisibility(View.GONE);
        r5.setVisibility(View.GONE);
        title.setText(getIntent().getStringExtra("name")+" Rated This Place");
        user_name.setText(getIntent().getStringExtra("name"));
        comment.setText(getIntent().getStringExtra("comment"));
        Picasso.with(ShowUserReview.this).load(getIntent().getStringExtra("image")).placeholder(R.mipmap.ic_launcher).into(imageView);
        user_review= Integer.parseInt(getIntent().getStringExtra("rating"));
        rating_id=getIntent().getStringExtra("rating_id");
        place_id=getIntent().getStringExtra("place_id");

        if (user_review==1){
            r1.setVisibility(View.VISIBLE);
        }else if (user_review==2){
            r1.setVisibility(View.VISIBLE);
            r2.setVisibility(View.VISIBLE);
        }else if (user_review==3){
            r1.setVisibility(View.VISIBLE);
            r2.setVisibility(View.VISIBLE);
            r3.setVisibility(View.VISIBLE);
        }else if (user_review==4){
            r1.setVisibility(View.VISIBLE);
            r2.setVisibility(View.VISIBLE);
            r3.setVisibility(View.VISIBLE);
            r4.setVisibility(View.VISIBLE);
        }else if (user_review==5){
            r1.setVisibility(View.VISIBLE);
            r2.setVisibility(View.VISIBLE);
            r3.setVisibility(View.VISIBLE);
            r4.setVisibility(View.VISIBLE);
            r5.setVisibility(View.VISIBLE);
        }

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_review();
            }
        });

    }


    void delete_review(){


        StringRequest sq = new StringRequest(Request.Method.POST, "https://thelostclan.xyz/TravelTour/delete_review.php",
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(ShowUserReview.this,"Your review deleted successfully!!!",Toast.LENGTH_LONG).show();
               // startActivity(new Intent(ShowUserReview.this,Give_review.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
               // finish();
                UpdatePlaceTable1();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ShowUserReview.this,"Network connection failed!!!",Toast.LENGTH_LONG).show();
                startActivity(new Intent(ShowUserReview.this,NextActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        }) {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("rating_id",rating_id);
                params.put("place_id",place_id);
                params.put("rating", String.valueOf(user_review));

                return params;
            }

        };

        sq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(ShowUserReview.this);
        requestQueue.add(sq);

    }


    private void UpdatePlaceTable1() {

        String url = "https://thelostclan.xyz/TravelTour/getPlaceForDatabase.php";
        StringRequest sq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                            String newId=jsonObject.getString("id");
                            String newName=jsonObject.getString("name");
                            String newDetails=jsonObject.getString("details");
                            String newRating=jsonObject.getString("rating");
                            String newImg1=jsonObject.getString("image1");
                            String newImg2=jsonObject.getString("image2");
                            String newImg3=jsonObject.getString("image3");
                            String newLoc=jsonObject.getString("location");
                            String newLat=jsonObject.getString("latitude");
                            String newLon=jsonObject.getString("longitude");
                            String newDis=jsonObject.getString("district");
                            String newDiv=jsonObject.getString("division");

                            //new task
                            // Inserting row in users table
                            dbb.deleteUsers();
                            dbb.addUser(newId,newName,newDetails,newRating,newImg1,newImg2,newImg3,newLoc,newLat,newLon,newDiv,newDis);
                            //new task end

                            Intent intent = new Intent(ShowUserReview.this,Give_review.class);
                            finish();
                            startActivity(intent);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("place_id", place_id);
                return params;
            }

        };
        sq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(sq);

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
        startActivity(new Intent(ShowUserReview.this,NextActivity.class));
    }
}


