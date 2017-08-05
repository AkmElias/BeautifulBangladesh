package com.example.computergallery.happytour;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.computergallery.happytour.helper.SQLiteHandlerForOnePlace;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText uemail,upass;
    Button login,signUP;
    SharedPreferences sharedPreferences;
    private ProgressDialog progressDialog;
    String author;



    private static final String TAG = LoginActivity.class.getSimpleName();
    private SQLiteHandlerForOnePlace dbb;
    public String place_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
      //  getSupportActionBar().hide();

        uemail=(EditText)findViewById(R.id.user);
        upass=(EditText)findViewById(R.id.pass);
        login=(Button)findViewById(R.id.btn_login);
        signUP=(Button)findViewById(R.id.btn_sign_up);

        sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);

        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Login..");

        // SQLite database handler
      //  db = new SQLiteHandler(getApplicationContext());


        //new task
        // SqLite database handler
        dbb = new SQLiteHandlerForOnePlace(getApplicationContext());
        // Fetching user details from SQLite
        final HashMap<String, String> user = dbb.getUserDetails();
        place_id=user.get("place_id");




        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signIn();


            }
        });


        signUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
                finish();
            }
        });
    }

    void signIn(){
        progressDialog.show();
        final String userEmail = uemail.getText().toString();
        final String userPass = upass.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://thelostclan.xyz/TravelTour/login.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.cancel();

                        if(response.equals("login")){

                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putBoolean("login",true);
                            editor.putString("email",userEmail);
                            editor.commit();
                            //gettingLoginUserRatingInfo(userEmail);
                            //Toast.makeText(getApplicationContext(),"Login Success",Toast.LENGTH_LONG).show();
                            //startActivity(new Intent(LoginActivity.this,NextActivity.class));
                            //finish();
                            Intent intent = new Intent(LoginActivity.this, NextActivity.class);
                            intent.putExtra("view_review", "view_review");
                            finish();
                            startActivity(intent);

                        }

                        else if (response.equals("error")){
                            Toast.makeText(getApplicationContext(),"Email / Password Has Been Wrong !",Toast.LENGTH_LONG).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.cancel();
                        Toast.makeText(getApplicationContext(),"Something Went Wrong !",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("user_email",userEmail);
                params.put("user_pass",userPass);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void gettingLoginUserRatingInfo(final String userEmail) {

        String url = "https://thelostclan.xyz/TravelTour/specific_user_review.php";

        if (userEmail != null) {

            progressDialog.show();


            StringRequest sq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {


                @Override
                public void onResponse(String response) {
                    if (response.length()!=1) {
                        progressDialog.cancel();
                        finish();
                        startActivity(new Intent(LoginActivity.this, Give_review.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

                    }
                    try {
                        JSONArray jsonArray=new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {

                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                String rating_user_name=jsonObject.getString("name");
                                String rating_user_img=jsonObject.getString("image");
                                String rating_user_rating=jsonObject.getString("rating");
                                String rating_user_comment=jsonObject.getString("comment");
                                String rating_id=jsonObject.getString("rating_id");

                                Intent intent=new Intent(LoginActivity.this,ShowUserReview.class);

                                intent.putExtra("name",rating_user_name);
                                intent.putExtra("comment",rating_user_comment);
                                intent.putExtra("rating",rating_user_rating);
                                intent.putExtra("image",rating_user_img);
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
                    Toast.makeText(LoginActivity.this, "SomeThing went wrong !", Toast.LENGTH_LONG).show();
                    progressDialog.cancel();

                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("place_id", place_id);
                    params.put("user_email", userEmail);

                    return params;
                }

            };

            sq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(sq);

        }
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
        startActivity(new Intent(LoginActivity.this,NextActivity.class));
    }

}
