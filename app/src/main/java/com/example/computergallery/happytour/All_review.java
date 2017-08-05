package com.example.computergallery.happytour;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.computergallery.happytour.Adapters.AllReviewAdapter;
import com.example.computergallery.happytour.ModelClass.AllReviewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class All_review extends AppCompatActivity{


    Button name;
    private EditText comment;
    private Button btn_submit;
    ImageButton imageView;

    private ProgressDialog progressDialog;

    String placeID;
    int place_id;
    String user_comment;

    private Context context;
    public static List<AllReviewModel> postLists = new ArrayList<>();
    RecyclerView recyclerView;
    AllReviewAdapter postAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_review);

        postAdapter=new AllReviewAdapter(getApplicationContext(), postLists);
        recyclerView=(RecyclerView)findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(postAdapter);


        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Loading");






        placeID=getIntent().getStringExtra("id");
        place_id= Integer.parseInt(placeID);

        gettingAllReview();



    }





    void gettingAllReview(){

        recyclerView.removeAllViews();
        postLists.clear();

        progressDialog.show();
        String myURL = "https://thelostclan.xyz/TravelTour/all_review.php";

        progressDialog.show();


        StringRequest sq = new StringRequest(Request.Method.POST, myURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    progressDialog.cancel();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                            AllReviewModel postModel = new AllReviewModel();

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
                Toast.makeText(All_review.this,"Network connection failed!!!",Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("place_id", placeID);

                return params;
            }
        };
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
        startActivity(new Intent(All_review.this,NextActivity.class));
    }
}
