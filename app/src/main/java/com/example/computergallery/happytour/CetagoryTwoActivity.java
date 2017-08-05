package com.example.computergallery.happytour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.computergallery.happytour.Adapters.CetagoryAll2Adapter;
import com.example.computergallery.happytour.ModelClass.CetagoryAll2Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CetagoryTwoActivity extends AppCompatActivity {
    public static List<CetagoryAll2Model> cetagoryAll2ModelsList = new ArrayList<>();
    RecyclerView recyclerView;
    CetagoryAll2Adapter cetagoryAll2Adapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cetagory_two);



        recyclerView = (RecyclerView) findViewById(R.id.recycleViewAll2Cetagory);
        toolbar = (Toolbar) findViewById(R.id.appbarsecond);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Beautifull Bangladesh");


        int position = getIntent().getIntExtra("POSITION",0);

        cetagoryAll2Adapter = new CetagoryAll2Adapter(getApplicationContext(),cetagoryAll2ModelsList);




        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(cetagoryAll2Adapter);

        if(position == 0){
            fetchingHistoricData();

        }else if(position == 1){
            fetchingArtistryData();

        }else if(position == 2){
            fetchingRenownData();

        }else if(position ==3){
            Toast.makeText(getApplicationContext(),"This is article man, and you didn't do anythig for this section!!!",Toast.LENGTH_LONG).show();


        }
    }




    void fetchingHistoricData(){

        recyclerView.removeAllViews();
        cetagoryAll2ModelsList.clear();


        String myURL = "https://multipurpus-drone.000webhostapp.com/historicalplaceapi.php";

        JsonArrayRequest mainArrayReq= new JsonArrayRequest(myURL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if (response.isNull(0)){

                    Toast.makeText(CetagoryTwoActivity.this,"There is no event",Toast.LENGTH_LONG).show();
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        CetagoryAll2Model cetagory2Model = new CetagoryAll2Model();

                        cetagory2Model.setTitle(jsonObject.getString("hp_title"));
                        cetagory2Model.setDetails(jsonObject.getString("hp_details"));
                        cetagory2Model.setRating(jsonObject.getString("hp_rating"));
                        cetagory2Model.setImage1(jsonObject.getString("hp_imag1"));
                        cetagory2Model.setImage2(jsonObject.getString("hp_imag2"));
                        cetagory2Model.setImage3(jsonObject.getString("hp_image3"));
                        cetagory2Model.setLocation(jsonObject.getString("hp_location"));
                        cetagory2Model.setId(jsonObject.getString("hp_id"));

                        cetagoryAll2ModelsList.add(cetagory2Model);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        cetagoryAll2Adapter.notifyItemChanged(i);


                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(CetagoryTwoActivity.this,"Something went wrong !",Toast.LENGTH_LONG).show();

            }
        });

        //AppController.getInstance().addToRequestQueue(mainArrayReq);

        mainArrayReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(mainArrayReq);

    }


    void fetchingArtistryData(){

        recyclerView.removeAllViews();
        cetagoryAll2ModelsList.clear();


        String myURL = "https://multipurpus-drone.000webhostapp.com/artistryapi.php";

        JsonArrayRequest mainArrayReq= new JsonArrayRequest(myURL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if (response.isNull(0)){

                    Toast.makeText(CetagoryTwoActivity.this,"There is no event",Toast.LENGTH_LONG).show();
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        CetagoryAll2Model cetagory2Model = new CetagoryAll2Model();

                        cetagory2Model.setTitle(jsonObject.getString("a_title"));
                        cetagory2Model.setDetails(jsonObject.getString("a_details"));
                        cetagory2Model.setRating(jsonObject.getString("a_rating"));
                        cetagory2Model.setImage1(jsonObject.getString("a_image1"));
                        cetagory2Model.setImage2(jsonObject.getString("a_image2"));
                        cetagory2Model.setImage3(jsonObject.getString("a_image3"));
                        cetagory2Model.setLocation(jsonObject.getString("a_location"));
                        cetagory2Model.setId(jsonObject.getString("a_id"));

                        cetagoryAll2ModelsList.add(cetagory2Model);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        cetagoryAll2Adapter.notifyItemChanged(i);


                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(CetagoryTwoActivity.this,"Something went wrong !",Toast.LENGTH_LONG).show();

            }
        });

        //AppController.getInstance().addToRequestQueue(mainArrayReq);

        mainArrayReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(mainArrayReq);

    }


    void fetchingRenownData(){

        recyclerView.removeAllViews();
        cetagoryAll2ModelsList.clear();


        String myURL = "https://multipurpus-drone.000webhostapp.com/renownapi.php";

        JsonArrayRequest mainArrayReq= new JsonArrayRequest(myURL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if (response.isNull(0)){

                    Toast.makeText(CetagoryTwoActivity.this,"There is no event",Toast.LENGTH_LONG).show();
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        CetagoryAll2Model cetagory2Model = new CetagoryAll2Model();

                        cetagory2Model.setTitle(jsonObject.getString("rt_title"));
                        cetagory2Model.setDetails(jsonObject.getString("rt_details"));
                        cetagory2Model.setRating(jsonObject.getString("rt_rating"));
                        cetagory2Model.setImage1(jsonObject.getString("rt_image1"));
                        cetagory2Model.setImage2(jsonObject.getString("rt_image2"));
                        cetagory2Model.setImage3(jsonObject.getString("rt_image3"));
                        cetagory2Model.setLocation(jsonObject.getString("rt_loction"));
                        cetagory2Model.setId(jsonObject.getString("rt_id"));

                        cetagoryAll2ModelsList.add(cetagory2Model);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        cetagoryAll2Adapter.notifyItemChanged(i);


                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(CetagoryTwoActivity.this,"Something went wrong !",Toast.LENGTH_LONG).show();

            }
        });

        //AppController.getInstance().addToRequestQueue(mainArrayReq);

        mainArrayReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(mainArrayReq);

    }


    void fetchingArticleData(){

        recyclerView.removeAllViews();
        cetagoryAll2ModelsList.clear();


        String myURL = "https://multipurpus-drone.000webhostapp.com/articleapi.php";

        JsonArrayRequest mainArrayReq= new JsonArrayRequest(myURL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if (response.isNull(0)){

                    Toast.makeText(CetagoryTwoActivity.this,"There is no event",Toast.LENGTH_LONG).show();
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        CetagoryAll2Model cetagory2Model = new CetagoryAll2Model();

                        cetagory2Model.setTitle(jsonObject.getString("ct_title"));
                        cetagory2Model.setDetails(jsonObject.getString("ct_details"));
                        cetagory2Model.setImage1(jsonObject.getString("ct_image1"));
                        cetagory2Model.setImage2(jsonObject.getString("ct_image2"));
                        cetagory2Model.setImage3(jsonObject.getString("ct_image3"));

                        cetagory2Model.setId(jsonObject.getString("ct_id"));

                        cetagoryAll2ModelsList.add(cetagory2Model);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        cetagoryAll2Adapter.notifyItemChanged(i);


                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(CetagoryTwoActivity.this,"Something went wrong !",Toast.LENGTH_LONG).show();

            }
        });

        //AppController.getInstance().addToRequestQueue(mainArrayReq);

        mainArrayReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(mainArrayReq);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(CetagoryTwoActivity.this,MainActivity.class));
    }






}


