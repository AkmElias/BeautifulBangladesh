package com.example.computergallery.happytour;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.computergallery.happytour.Adapters.AllPlaceAdapter;
import com.example.computergallery.happytour.Adapters.CetagoryOneAdapter;
import com.example.computergallery.happytour.Adapters.CetagoryTwoAdapter;
import com.example.computergallery.happytour.Adapters.EventAdapter;
import com.example.computergallery.happytour.Adapters.allCultureAdapter;
import com.example.computergallery.happytour.Adapters.allHotelAdapter;
import com.example.computergallery.happytour.ModelClass.AllPlaceModel;
import com.example.computergallery.happytour.ModelClass.CetagoryOneModel;
import com.example.computergallery.happytour.ModelClass.CetagoryTwoModel;
import com.example.computergallery.happytour.ModelClass.EventModel;
import com.example.computergallery.happytour.ModelClass.allCultureModel;
import com.example.computergallery.happytour.ModelClass.allHotelModel;

import android.support.v7.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity  {//TODO  implements AdapterView.OnItemSelectedListener
//    public static List<AllPlaceModel> postLists = new ArrayList<>();
public static List<EventModel> eventModelsList = new ArrayList<>();

    public static List<CetagoryOneModel> cetagoryOneModelsList = new ArrayList<>();
    public static List<CetagoryTwoModel> cetagoryTwoModelsList = new ArrayList<>();

    private Button filter;
    RecyclerView recyclerView, recyclerViewHotel, recyclerViewCulture,recyclerViewCetagoryOne,recyclerViewGetagoryTwo;
    CetagoryOneAdapter cetagoryOneAdapter;
    CetagoryTwoAdapter cetagoryTwoAdapter;
    EventAdapter postAdapter;
    private ProgressDialog progressDialog;
    private Spinner state,city;
    List<String> citis=new ArrayList<String>();
    ArrayAdapter adapter,unit_adapter;
    String str_division,str_district;

    SliderLayout slider;
    Toolbar toolbar;




    String[] name1 ={"Sea Beach","wood Land","High Land","Culture and Tradition"};
    String[] name2 ={" Historic monuments","Artistry","Noted thing's","Article"};
    int[] image1 ={R.drawable.saintmartin,R.drawable.woodland,R.drawable.highland,R.drawable.culture};
    int[]image2 ={R.drawable.historical,R.drawable.artistry,R.drawable.notedthing,R.drawable.article};



    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        filter = (Button) findViewById(R.id.filter);
        //postAdapter = new EventAdapter(getApplicationContext(), eventModelsList);

        cetagoryOneAdapter = new CetagoryOneAdapter(getApplicationContext(), cetagoryOneModelsList);
        cetagoryTwoAdapter = new CetagoryTwoAdapter(getApplicationContext(), cetagoryTwoModelsList);

      //  recyclerView = (RecyclerView) findViewById(R.id.recycleViewEvent);
        recyclerViewCetagoryOne = (RecyclerView) findViewById(R.id.recycleViewCetagoryOne);
        recyclerViewGetagoryTwo = (RecyclerView) findViewById(R.id.recycleViewCetagoryTwo);
        TextView seeMap = (TextView) findViewById(R.id.seeMap);


        slider = (SliderLayout) findViewById(R.id.sliderMain);


        //recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCetagoryOne.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewGetagoryTwo.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));


        //recyclerView.setAdapter(postAdapter);

        recyclerViewCetagoryOne.setAdapter(cetagoryOneAdapter);
        recyclerViewGetagoryTwo.setAdapter(cetagoryTwoAdapter);


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Loading");



        cetagoryData();
        //fetchingCultureData();

        seeMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });


        AHBottomNavigation bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);


        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Explore", R.drawable.explore, R.color.colorPrimary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Search", R.drawable.searchic, R.color.colorPrimaryDark);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Profile", R.drawable.profileic, R.color.colorPrimaryLight);


        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);

        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#27ae60"));


        bottomNavigation.setBehaviorTranslationEnabled(false);


        bottomNavigation.setAccentColor(Color.parseColor("#27ae60"));
        bottomNavigation.setInactiveColor(Color.parseColor("#2ecc71"));


        bottomNavigation.setForceTint(true);


        bottomNavigation.setTranslucentNavigationEnabled(true);


        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);

        bottomNavigation.setColored(true);


        bottomNavigation.setCurrentItem(1);


        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("The Longest Sea Beach On EARTH", R.drawable.coxbazare);
        file_maps.put("largest Mangrove Forest On EARTH", R.drawable.shundarban);
        file_maps.put("Largest Sanctuary in WORLD for the Royal Bnagal Tiger", R.drawable.tiger);
        file_maps.put("One of the few Freshwater Swamp Forest in the WORLD", R.drawable.ratargul);

        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            //.setOnSliderClickListener();

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            slider.addSlider(textSliderView);
        }


    }









    void cetagoryData(){

        recyclerViewCetagoryOne.removeAllViews();
        cetagoryOneModelsList.clear();
        recyclerViewGetagoryTwo.removeAllViews();
        cetagoryTwoModelsList.clear();



        for (int i = 0 ; i < name1.length ; i++){

            try {

                CetagoryOneModel model = new CetagoryOneModel();

                model.setName(name1[i]);
                model.setImage(image1[i]);

                cetagoryOneModelsList.add(model);

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                cetagoryOneAdapter.notifyDataSetChanged();

            }


//



        }


        for (int j = 0; j < name2.length; j++){
            try {
                CetagoryTwoModel model1 = new CetagoryTwoModel();

                model1.setName(name2[j]);
                model1.setImage(image2[j]);

                cetagoryTwoModelsList.add(model1);

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                cetagoryTwoAdapter.notifyDataSetChanged();

            }


//




        }


    }
























    void fetchingCultureData(){

        recyclerView.removeAllViews();
        eventModelsList.clear();

        progressDialog.show();
        String myURL = "https://multipurpus-drone.000webhostapp.com/eventapi.php";

        JsonArrayRequest mainArrayReq= new JsonArrayRequest(myURL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if (response.isNull(0)){
                    progressDialog.cancel();
                    Toast.makeText(MainActivity.this,"There is no event",Toast.LENGTH_LONG).show();
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        EventModel eventModel = new EventModel();

                        eventModel.setTitle(jsonObject.getString("ep_title"));
                        eventModel.setAbout(jsonObject.getString("ep_about"));
                        eventModel.setWeWillDo("ep_wewilldo");
                        eventModel.setWhatprovide(jsonObject.getString("ep_whatprovide"));
                        eventModel.setNote(jsonObject.getString("ep_note"));
                        eventModel.setWherebe(jsonObject.getString("ep_wherebe"));
                        eventModel.setContact("ep_contact");
                        eventModel.setAbout(jsonObject.getString("ep_abouthost"));
                        eventModel.setImage1(jsonObject.getString("ep_image1"));
                        eventModel.setImage1(jsonObject.getString("ep_image2"));
                        eventModel.setImage1(jsonObject.getString("ep_image3"));
                        eventModelsList.add(eventModel);


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
                Toast.makeText(MainActivity.this,"Something went wrong !",Toast.LENGTH_LONG).show();

            }
        });

        //AppController.getInstance().addToRequestQueue(mainArrayReq);

        mainArrayReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(mainArrayReq);

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
    }




}
