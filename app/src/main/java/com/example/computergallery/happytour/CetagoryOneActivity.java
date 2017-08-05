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
import com.example.computergallery.happytour.Adapters.CetagoryAllAdapter;
import com.example.computergallery.happytour.ModelClass.CetagoryAllModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CetagoryOneActivity extends AppCompatActivity {

    public static List<CetagoryAllModel> cetagoryAllModelsList = new ArrayList<>();
    RecyclerView recyclerView;
    CetagoryAllAdapter cetagoryAllAdapter;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cetagory_one);






        recyclerView = (RecyclerView) findViewById(R.id.recycleViewAllCetagory);
        toolbar = (Toolbar) findViewById(R.id.appbarsecond);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Beautifull Bangladesh");


        int position = getIntent().getIntExtra("POSITION",0);

        cetagoryAllAdapter = new CetagoryAllAdapter(getApplicationContext(),cetagoryAllModelsList);




        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(cetagoryAllAdapter);

        if(position == 0){
            fetchingSeaBeachData();

        }else if(position == 1){
            fetchingWoodLandData();

        }else if(position == 2){
            fetchingHighLandData();

        }else if(position ==3){
            fetchingCultureData();


        }










    }

    void fetchingSeaBeachData(){

        recyclerView.removeAllViews();
        cetagoryAllModelsList.clear();


        String myURL = "http://multipurpus-drone.000webhostapp.com/seabeachapi.php";

        JsonArrayRequest mainArrayReq= new JsonArrayRequest(myURL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if (response.isNull(0)){

                    Toast.makeText(CetagoryOneActivity.this,"There is no event",Toast.LENGTH_LONG).show();
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        CetagoryAllModel cetagoryModel = new CetagoryAllModel();

                        cetagoryModel.setTitle(jsonObject.getString("sb_title"));
                        cetagoryModel.setDetails(jsonObject.getString("sb_details"));
                        cetagoryModel.setRating(jsonObject.getString("sb_rating"));
                        cetagoryModel.setImage1(jsonObject.getString("sb_image1"));
                        cetagoryModel.setImage2(jsonObject.getString("sb_image2"));
                        cetagoryModel.setImage3(jsonObject.getString("sb_image3"));
                        cetagoryModel.setLocation(jsonObject.getString("sb_location"));
                        cetagoryModel.setId(jsonObject.getString("sb_id"));

                        cetagoryAllModelsList.add(cetagoryModel);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        cetagoryAllAdapter.notifyItemChanged(i);


                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(CetagoryOneActivity.this,"Something went wrong !",Toast.LENGTH_LONG).show();

            }
        });

        //AppController.getInstance().addToRequestQueue(mainArrayReq);

        mainArrayReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(mainArrayReq);

    }


    void fetchingWoodLandData(){

        recyclerView.removeAllViews();
        cetagoryAllModelsList.clear();


        String myURL = "http://multipurpus-drone.000webhostapp.com/woodlandapi.php";

        JsonArrayRequest mainArrayReq= new JsonArrayRequest(myURL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if (response.isNull(0)){

                    Toast.makeText(CetagoryOneActivity.this,"There is no event",Toast.LENGTH_LONG).show();
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        CetagoryAllModel cetagoryModel = new CetagoryAllModel();

                        cetagoryModel.setTitle(jsonObject.getString("wl_title"));
                        cetagoryModel.setDetails(jsonObject.getString("wl_details"));
                        cetagoryModel.setRating(jsonObject.getString("wl_rating"));
                        cetagoryModel.setImage1(jsonObject.getString("wl_image1"));
                        cetagoryModel.setImage2(jsonObject.getString("wl_image2"));
                        cetagoryModel.setImage3(jsonObject.getString("wl_image3"));
                        cetagoryModel.setLocation(jsonObject.getString("wl_location"));
                        cetagoryModel.setId(jsonObject.getString("wl_id"));

                        cetagoryAllModelsList.add(cetagoryModel);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        cetagoryAllAdapter.notifyItemChanged(i);


                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(CetagoryOneActivity.this,"Something went wrong !",Toast.LENGTH_LONG).show();

            }
        });

        //AppController.getInstance().addToRequestQueue(mainArrayReq);

        mainArrayReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(mainArrayReq);

    }


    void fetchingHighLandData(){

        recyclerView.removeAllViews();
        cetagoryAllModelsList.clear();


        String myURL = "http://multipurpus-drone.000webhostapp.com/highlandapi.php";

        JsonArrayRequest mainArrayReq= new JsonArrayRequest(myURL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if (response.isNull(0)){

                    Toast.makeText(CetagoryOneActivity.this,"There is no event",Toast.LENGTH_LONG).show();
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        CetagoryAllModel cetagoryModel = new CetagoryAllModel();

                        cetagoryModel.setTitle(jsonObject.getString("hl_title"));
                        cetagoryModel.setDetails(jsonObject.getString("hl_details"));
                        cetagoryModel.setRating(jsonObject.getString("hl_rating"));
                        cetagoryModel.setImage1(jsonObject.getString("hl_image1"));
                        cetagoryModel.setImage2(jsonObject.getString("hl_image2"));
                        cetagoryModel.setImage3(jsonObject.getString("hl_image3"));
                        cetagoryModel.setLocation(jsonObject.getString("hl_loction"));
                        cetagoryModel.setId(jsonObject.getString("hl_id"));

                        cetagoryAllModelsList.add(cetagoryModel);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        cetagoryAllAdapter.notifyItemChanged(i);


                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(CetagoryOneActivity.this,"Something went wrong !",Toast.LENGTH_LONG).show();

            }
        });

        //AppController.getInstance().addToRequestQueue(mainArrayReq);

        mainArrayReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(mainArrayReq);

    }


    void fetchingCultureData(){

        recyclerView.removeAllViews();
        cetagoryAllModelsList.clear();


        String myURL = "http://multipurpus-drone.000webhostapp.com/cultureandtraditionapi.php";

        JsonArrayRequest mainArrayReq= new JsonArrayRequest(myURL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if (response.isNull(0)){

                    Toast.makeText(CetagoryOneActivity.this,"There is no event",Toast.LENGTH_LONG).show();
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        CetagoryAllModel cetagoryModel = new CetagoryAllModel();

                        cetagoryModel.setTitle(jsonObject.getString("ct_title"));
                        cetagoryModel.setDetails(jsonObject.getString("ct_details"));
                        cetagoryModel.setImage1(jsonObject.getString("ct_image1"));
                        cetagoryModel.setImage2(jsonObject.getString("ct_image2"));
                        cetagoryModel.setImage3(jsonObject.getString("ct_image3"));

                        cetagoryModel.setId(jsonObject.getString("ct_id"));

                        cetagoryAllModelsList.add(cetagoryModel);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        cetagoryAllAdapter.notifyItemChanged(i);


                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(CetagoryOneActivity.this,"Something went wrong !",Toast.LENGTH_LONG).show();

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
        startActivity(new Intent(CetagoryOneActivity.this,MainActivity.class));
    }






}
