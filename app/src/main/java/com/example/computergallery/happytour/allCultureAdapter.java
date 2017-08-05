package com.example.computergallery.happytour;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.example.computergallery.happytour.helper.SQLiteHandler;
import com.example.computergallery.happytour.helper.SQLiteHandlerForOnePlace;
import com.example.computergallery.happytour.helper.SessionManager;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Akhlak Uzzaman Ashik on 7/31/2017.
 */

public class allCultureAdapter extends RecyclerView.Adapter<allCultureAdapter.MyViewHolder> {

    private ArrayList<allCultureModel> cultureModels;
    private Context context;
    private SQLiteHandler db;
    private SQLiteHandlerForOnePlace dbb;
    private SessionManager session;
    String place_id;
    allCultureModel newsModel;
    SliderLayout sliderShow;
    private static final String TAGG = AllPlaceAdapter.class.getSimpleName();

    public allCultureAdapter(Context context, List<allCultureModel> postLists) {
        this.cultureModels = (ArrayList<allCultureModel>) postLists;
        this.context = context;
    }


    @Override
    public allCultureAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.all_place_card_view, parent, false);
        allCultureAdapter.MyViewHolder holder = new allCultureAdapter.MyViewHolder(view, context, cultureModels);
        return holder;
    }

    @Override
    public void onBindViewHolder(final allCultureAdapter.MyViewHolder holder, int position) {
        final allCultureModel newsModel = cultureModels.get(position);
        holder.name.setText(newsModel.getTitle());

//        Double value= Double.valueOf(newsModel.getRating());
//        DecimalFormat twoDecimals = new DecimalFormat("#.##");
//        Double d=Double.valueOf(twoDecimals.format(value));



//    TODO    holder.rating.setText("Rating :"+newsModel.getRating());
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
        return cultureModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name,rating,fact;
        ImageView imageView;
        ArrayList<allCultureModel> postLists = new ArrayList<>();
        ProgressWheel progressWheel;
        Context context;

        public MyViewHolder(View itemView, Context context, ArrayList<allCultureModel> postLists) {
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

            int position=getAdapterPosition();
            newsModel=this.postLists.get(position);
            Intent intent=new Intent(this.context,NextActivity.class);
            intent.putExtra("id",newsModel.getId());
            intent.putExtra("name",newsModel.getTitle());
            intent.putExtra("description",newsModel.getDetails());
            intent.putExtra("rating",newsModel.getRating());
            intent.putExtra("image1",newsModel.getImage1());
            intent.putExtra("image2",newsModel.getImage2());
            intent.putExtra("image3",newsModel.getImage3());
            intent.putExtra("loc",newsModel.getLocation());
            intent.putExtra("lat","24.9045");
            intent.putExtra("lon","91.8611");
            intent.putExtra("division","Sylhet");
            intent.putExtra("district","Sylhet");




            //new task
            // SQLite database handler
            dbb = new SQLiteHandlerForOnePlace(context.getApplicationContext());
            // Inserting row in users table
            dbb.deleteUsers();
            dbb.addUser(newsModel.getId(),newsModel.getTitle(),newsModel.getDetails(),newsModel.getRating(),newsModel.getImage1(),newsModel.getImage2(),newsModel.getImage3(),newsModel.getLocation(),"24.9045","91.8611","Sylhet","Sylhet");
            //new task end

            //new task
            // SQLite database handler
            db = new SQLiteHandler(context.getApplicationContext());
            place_id=newsModel.getId();
            // Inserting row in users table
            db.deleteUsers();
            db.addUser(place_id);
            //new task end



            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);


        }
    }


}
