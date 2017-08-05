package com.example.computergallery.happytour.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.example.computergallery.happytour.ModelClass.AllPlaceModel;
import com.example.computergallery.happytour.R;
import com.example.computergallery.happytour.helper.SQLiteHandler;
import com.example.computergallery.happytour.helper.SQLiteHandlerForOnePlace;
import com.example.computergallery.happytour.helper.SessionManager;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class AllPlaceAdapter extends RecyclerView.Adapter<AllPlaceAdapter.MyViewHolder> {

    private ArrayList<AllPlaceModel> postLists;
    private Context context;
    private SQLiteHandler db;
    private SQLiteHandlerForOnePlace dbb;
    private SessionManager session;
    String place_id;
    AllPlaceModel newsModel;
    SliderLayout sliderShow;
    private static final String TAGG = AllPlaceAdapter.class.getSimpleName();

    public AllPlaceAdapter(Context context, List<AllPlaceModel> postLists) {
        this.postLists = (ArrayList<AllPlaceModel>) postLists;
        this.context = context;
    }


    @Override
    public AllPlaceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.all_place_card_view, parent, false);
        MyViewHolder holder = new MyViewHolder(view, context, postLists);
        return holder;
    }

    @Override
    public void onBindViewHolder(final AllPlaceAdapter.MyViewHolder holder, int position) {
        final AllPlaceModel newsModel = postLists.get(position);


         holder.name.setText(Html.fromHtml(newsModel.getName()));

//    TODO    Double value= Double.valueOf(newsModel.getRating());
//        DecimalFormat twoDecimals = new DecimalFormat("#.##");
//        Double d=Double.valueOf(twoDecimals.format(value));
//        holder.rating.setText(Html.fromHtml(String.valueOf("Rating:"+d)));
//        holder.fact.setText(newsModel.getDetails());









        // holder.rating.setText(Html.fromHtml(newsModel.getRating().substring(0,3)));
//        if (newsModel.getImage1().isEmpty()) {
//            holder.imageView.setImageResource(R.mipmap.ic_launcher);
//        } else{
//            Picasso.with(context).load(newsModel.getImage1()).placeholder(R.mipmap.ic_launcher).into(holder.imageView);
//        }



         Picasso.with(context).load(newsModel.getImage1()).into(holder.imageView, new Callback() {
             @Override
             public void onSuccess() {
                 holder.progressWheel.setVisibility(View.INVISIBLE);

             }

             @Override
             public void onError() {

             }
         });

//        Picasso.with(context)
//                .load(" http://xxx.xxx.com/images/New%20folder/Desert.jpg.")
//                .into(holder.imageView);



    }

    @Override
    public int getItemCount() {
        return postLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name,rating,fact;
        ImageView imageView;
        ArrayList<AllPlaceModel> postLists = new ArrayList<>();
        ProgressWheel progressWheel;
        Context context;

        public MyViewHolder(View itemView, Context context, ArrayList<AllPlaceModel> postLists) {
            super(itemView);
            this.context = context;
            this.postLists = postLists;
            name=(TextView)itemView.findViewById(R.id.card_title);
//            fact = (TextView) itemView.findViewById(R.id.fact);
//            rating=(TextView)itemView.findViewById(R.id.card_rating);
            imageView = (ImageView) itemView.findViewById(R.id.card_image);
            progressWheel = (ProgressWheel) itemView.findViewById(R.id.progress_wheel) ;


            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

//            int position=getAdapterPosition();
//             newsModel=this.postLists.get(position);
//            Intent intent=new Intent(this.context,NextActivity.class);
//            intent.putExtra("id",newsModel.getId());
//            intent.putExtra("name",newsModel.getName());
//            intent.putExtra("description",newsModel.getDetails());
//            intent.putExtra("rating",newsModel.getRating());
//            intent.putExtra("image1",newsModel.getImage1());
//            intent.putExtra("image2",newsModel.getImage2());
//            intent.putExtra("image3",newsModel.getImage3());
//            intent.putExtra("loc",newsModel.getLocation());
//            intent.putExtra("lat",newsModel.getLatitude());
//            intent.putExtra("lon",newsModel.getLongitude());
//            intent.putExtra("division",newsModel.getDivision());
//            intent.putExtra("district",newsModel.getDivision());
//
//
//
//            //new task
//            // SQLite database handler
//            dbb = new SQLiteHandlerForOnePlace(context.getApplicationContext());
//            // Inserting row in users table
//            dbb.deleteUsers();
//            dbb.addUser(newsModel.getId(),newsModel.getName(),newsModel.getDetails(),newsModel.getRating(),newsModel.getImage1(),newsModel.getImage2(),newsModel.getImage3(),newsModel.getLocation(),newsModel.getLatitude(),newsModel.getLongitude(),newsModel.getDivision(),newsModel.getDistrict());
//            //new task end
//
//            //new task
//            // SQLite database handler
//            db = new SQLiteHandler(context.getApplicationContext());
//            place_id=newsModel.getId();
//            // Inserting row in users table
//            db.deleteUsers();
//            db.addUser(place_id);
//            //new task end
//
//
//
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent);


        }
    }

}