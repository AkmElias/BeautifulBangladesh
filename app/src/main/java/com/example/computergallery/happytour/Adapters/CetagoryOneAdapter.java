package com.example.computergallery.happytour.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.example.computergallery.happytour.BitmapTransform;
import com.example.computergallery.happytour.CetagoryOneActivity;
import com.example.computergallery.happytour.ModelClass.CetagoryOneModel;
import com.example.computergallery.happytour.R;
import com.example.computergallery.happytour.helper.SQLiteHandler;
import com.example.computergallery.happytour.helper.SQLiteHandlerForOnePlace;
import com.example.computergallery.happytour.helper.SessionManager;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class CetagoryOneAdapter extends RecyclerView.Adapter<CetagoryOneAdapter.MyViewHolder> {

    private ArrayList<CetagoryOneModel> cetagoryOneModels;
    private Context context;
    private SQLiteHandler db;
    private SQLiteHandlerForOnePlace dbb;
    private SessionManager session;
    String place_id;
    CetagoryOneModel newsModel;
    private static final int MAX_WIDTH = 500;
    private static final int MAX_HEIGHT = 300;

    int size = (int) Math.ceil(Math.sqrt(MAX_WIDTH * MAX_HEIGHT));

// Loads given image

    SliderLayout sliderShow;
    private static final String TAGG = AllPlaceAdapter.class.getSimpleName();

    public CetagoryOneAdapter(Context context, List<CetagoryOneModel> postLists) {
        this.cetagoryOneModels = (ArrayList<CetagoryOneModel>) postLists;
        this.context = context;
    }


    @Override
    public CetagoryOneAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.cetagory_card_view, parent, false);
        CetagoryOneAdapter.MyViewHolder holder = new CetagoryOneAdapter.MyViewHolder(view, context, cetagoryOneModels);
        return holder;
    }

    @Override
    public void onBindViewHolder(final CetagoryOneAdapter.MyViewHolder holder, int position) {
        final CetagoryOneModel newsModel = cetagoryOneModels.get(position);
        holder.name.setText(newsModel.getName());

//        Picasso.with(context).load(newsModel.getImage()).placeholder(R.drawable.placeholder).into(holder.imageView);
        Picasso.with(holder.imageView.getContext())
                .load(newsModel.getImage())
                .transform(new BitmapTransform(MAX_WIDTH, MAX_HEIGHT))
                .skipMemoryCache()
                .resize(size, size)
                .centerInside()
                .placeholder(R.drawable.placeholder)
                .into(holder.imageView);




    }

    @Override
    public int getItemCount() {
        return cetagoryOneModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name,rating,fact;
        ImageView imageView;
        ArrayList<CetagoryOneModel> postLists = new ArrayList<>();
        ProgressWheel progressWheel;
        Context context;

        public MyViewHolder(View itemView, Context context, ArrayList<CetagoryOneModel> postLists) {
            super(itemView);
            this.context = context;
            this.postLists = postLists;
            name=(TextView)itemView.findViewById(R.id.card_title);
            imageView = (ImageView) itemView.findViewById(R.id.card_image);



            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            int position=getAdapterPosition();
            Log.d("This is the hahahahaha",String.valueOf(position));

            Intent intent=new Intent(context,CetagoryOneActivity.class);

            intent.putExtra("POSITION",position);
            Log.d("This is the hahahahaha",String.valueOf(position));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

//
//            intent.putExtra("lat","24.9045");
//            intent.putExtra("lon","91.8611");
//            intent.putExtra("division","Sylhet");
//            intent.putExtra("district","Sylhet");
//
//
//
//
            context.startActivity(intent);


        }
    }


}
