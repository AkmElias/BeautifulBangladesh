package com.example.computergallery.happytour;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.computergallery.happytour.helper.SQLiteHandler;
import com.example.computergallery.happytour.helper.SQLiteHandlerForOnePlace;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Give_review extends AppCompatActivity{


    private EditText comment;
    private Button btn_submit,btn_comment;
    ImageButton r1,r2,r3,r4,r5,r6,r7,r8,r9,r10;

    private ProgressDialog progressDialog;
    private String user_email,user_name,writter_name,writter_email,writter_image;
    String status_details,status_total_comment,status_time;
    String placeId;

    String user_comment;

    private Context context;
    private SQLiteHandler db;
    SharedPreferences sharedPreferences;
    int user_rating;
    LinearLayout layout_submit,layout_comment,layout_rating;
    private SQLiteHandlerForOnePlace dbb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_review);

        // SQLite database handler
        dbb = new SQLiteHandlerForOnePlace(getApplicationContext());


        comment=(EditText)findViewById(R.id.comment);
        btn_comment=(Button)findViewById(R.id.btn_comment);
        btn_submit=(Button)findViewById(R.id.btn_submit);
        r1=(ImageButton)findViewById(R.id.r1);
        r2=(ImageButton)findViewById(R.id.r2);
        r3=(ImageButton)findViewById(R.id.r3);
        r4=(ImageButton)findViewById(R.id.r4);
        r5=(ImageButton)findViewById(R.id.r5);
        r6=(ImageButton)findViewById(R.id.r6);
        r7=(ImageButton)findViewById(R.id.r7);
        r8=(ImageButton)findViewById(R.id.r8);
        r9=(ImageButton)findViewById(R.id.r9);
        r10=(ImageButton)findViewById(R.id.r10);
        layout_submit=(LinearLayout) findViewById(R.id.layout_submit);
        layout_comment=(LinearLayout) findViewById(R.id.layout_comment);
        layout_rating=(LinearLayout) findViewById(R.id.layout_rating);



        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Loading...");

        sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
        user_email=sharedPreferences.getString("email","null");

        //new task
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());
        // Fetching user details from SQLite
        HashMap<String, String> user = db.getUserDetails();
        placeId= user.get("place_id");



        layout_submit.setVisibility(View.GONE);
        layout_comment.setVisibility(View.GONE);
       // btn_submit.setVisibility(View.GONE);
        //btn_comment.setVisibility(View.GONE);
        //comment.setVisibility(View.GONE);
        r2.setVisibility(View.GONE);
        r4.setVisibility(View.GONE);
        r6.setVisibility(View.GONE);
        r8.setVisibility(View.GONE);
        r10.setVisibility(View.GONE);




        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r1.setVisibility(View.GONE);
                r2.setVisibility(View.VISIBLE);
                //btn_submit.setVisibility(View.VISIBLE);
                layout_submit.setVisibility(View.VISIBLE);
                user_rating=1;
            }


        });
        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r1.setVisibility(View.GONE);
                r3.setVisibility(View.GONE);
                r2.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                //btn_submit.setVisibility(View.VISIBLE);
                layout_submit.setVisibility(View.VISIBLE);
                user_rating=2;
            }


        });
        r5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r1.setVisibility(View.GONE);
                r3.setVisibility(View.GONE);
                r5.setVisibility(View.GONE);
                r2.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r6.setVisibility(View.VISIBLE);
                //btn_submit.setVisibility(View.VISIBLE);
                layout_submit.setVisibility(View.VISIBLE);
                user_rating=3;
            }


        });
        r7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r1.setVisibility(View.GONE);
                r3.setVisibility(View.GONE);
                r5.setVisibility(View.GONE);
                r7.setVisibility(View.GONE);
                r2.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r6.setVisibility(View.VISIBLE);
                r8.setVisibility(View.VISIBLE);
                //btn_submit.setVisibility(View.VISIBLE);
                layout_submit.setVisibility(View.VISIBLE);
                user_rating=4;
            }


        });
        r9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r1.setVisibility(View.GONE);
                r3.setVisibility(View.GONE);
                r5.setVisibility(View.GONE);
                r7.setVisibility(View.GONE);
                r9.setVisibility(View.GONE);
                r2.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r6.setVisibility(View.VISIBLE);
                r8.setVisibility(View.VISIBLE);
                r10.setVisibility(View.VISIBLE);
                //btn_submit.setVisibility(View.VISIBLE);
                layout_submit.setVisibility(View.VISIBLE);
                user_rating=5;
            }

        });



        r8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r1.setVisibility(View.GONE);
                r3.setVisibility(View.GONE);
                r5.setVisibility(View.GONE);
                r7.setVisibility(View.GONE);
                r2.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r6.setVisibility(View.VISIBLE);
                r8.setVisibility(View.VISIBLE);
                r9.setVisibility(View.VISIBLE);
                r10.setVisibility(View.GONE);
                //btn_submit.setVisibility(View.VISIBLE);
                layout_submit.setVisibility(View.VISIBLE);
                user_rating=4;
            }

        });

        r6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r1.setVisibility(View.GONE);
                r3.setVisibility(View.GONE);
                r5.setVisibility(View.GONE);
                r7.setVisibility(View.VISIBLE);
                r9.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r6.setVisibility(View.VISIBLE);
                r8.setVisibility(View.GONE);
                r10.setVisibility(View.GONE);
                //btn_submit.setVisibility(View.VISIBLE);
                layout_submit.setVisibility(View.VISIBLE);
                user_rating=3;
            }

        });


        r4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r1.setVisibility(View.GONE);
                r3.setVisibility(View.GONE);
                r5.setVisibility(View.VISIBLE);
                r7.setVisibility(View.VISIBLE);
                r9.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r6.setVisibility(View.GONE);
                r8.setVisibility(View.GONE);
                r10.setVisibility(View.GONE);
                //btn_submit.setVisibility(View.VISIBLE);
                layout_submit.setVisibility(View.VISIBLE);
                user_rating=2;
            }

        });
        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r1.setVisibility(View.GONE);
                r3.setVisibility(View.VISIBLE);
                r5.setVisibility(View.VISIBLE);
                r7.setVisibility(View.VISIBLE);
                r9.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r4.setVisibility(View.GONE);
                r6.setVisibility(View.GONE);
                r8.setVisibility(View.GONE);
                r10.setVisibility(View.GONE);
                //btn_submit.setVisibility(View.VISIBLE);
                layout_submit.setVisibility(View.VISIBLE);
                user_rating=1;
            }

        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submit_rating();
                layout_rating.setVisibility(View.GONE);
                layout_submit.setVisibility(View.GONE);
                layout_comment.setVisibility(View.VISIBLE);


            }


        });

        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                comment();
            }


        });



    }


    private void submit_rating(){

        String url = "https://thelostclan.xyz/TravelTour/give_review.php";


        if (user_rating!=0){

            progressDialog.setTitle("Please wait");
            progressDialog.setMessage("Your rating is uploading...");
            progressDialog.show();


            StringRequest sq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Toast.makeText(Give_review.this, "Your rating uploaded", Toast.LENGTH_LONG).show();
                    progressDialog.cancel();
                  //  finish();
                  //  startActivity(new Intent(Give_review.this,Give_review.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    finish();
                    Toast.makeText(Give_review.this,"Network connection failed!!!",Toast.LENGTH_LONG).show();
                    progressDialog.cancel();

                }
            }) {
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("place_id",placeId);
                    params.put("user_email",user_email);
                    params.put("user_rating", String.valueOf(user_rating));

                    return params;
                }

            };

            sq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(sq);
        }

        else {
            Log.e("ERROR  ::::   ", "  RATING :::" + user_rating);

            Toast.makeText(Give_review.this,"Please give rating",Toast.LENGTH_SHORT).show();

        }

    }
    private void comment(){

        String url = "https://thelostclan.xyz/TravelTour/give_comment.php";
        user_comment=comment.getText().toString().trim();


        if (user_comment.length()!=0){

            progressDialog.setTitle("Please wait");
            progressDialog.setMessage("Your comment is uploading...");
            progressDialog.show();


            StringRequest sq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Toast.makeText(Give_review.this, "Your comment uploaded", Toast.LENGTH_LONG).show();
                    progressDialog.cancel();
                    UpdatePlaceTable();
                   /* Intent intent = new Intent(Give_review.this, NextActivity.class);
                    intent.putExtra("view_review", "view_review");
                    finish();
                    startActivity(intent);*/

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    finish();
                    Toast.makeText(Give_review.this,"Network connection failed!!!",Toast.LENGTH_LONG).show();
                    progressDialog.cancel();

                }
            }) {
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("place_id",placeId);
                    params.put("user_email",user_email);
                    params.put("user_comment",user_comment);

                    return params;
                }

            };

            sq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(sq);
        }

        else {
            Log.e("ERROR  ::::   ", "  COMMENTS :::" + user_comment);

            Toast.makeText(Give_review.this,"Please enter your comment",Toast.LENGTH_SHORT).show();

        }

    }

    private void UpdatePlaceTable() {

        String url = "https://thelostclan.xyz/TravelTour/getPlaceForDatabase.php";
            progressDialog.show();
            StringRequest sq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                        progressDialog.cancel();
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

                                Intent intent = new Intent(Give_review.this, NextActivity.class);
                                intent.putExtra("view_review", "view_review");
                                finish();
                                startActivity(intent);

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
                    progressDialog.cancel();

                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("place_id", placeId);
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
        startActivity(new Intent(Give_review.this,NextActivity.class));
    }
}
