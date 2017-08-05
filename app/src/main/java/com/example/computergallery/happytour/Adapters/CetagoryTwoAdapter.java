package com.example.computergallery.happytour.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.example.computergallery.happytour.BitmapTransform;
import com.example.computergallery.happytour.CetagoryTwoActivity;
import com.example.computergallery.happytour.ModelClass.CetagoryTwoModel;
import com.example.computergallery.happytour.R;
import com.example.computergallery.happytour.helper.SQLiteHandler;
import com.example.computergallery.happytour.helper.SQLiteHandlerForOnePlace;
import com.example.computergallery.happytour.helper.SessionManager;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;



public class CetagoryTwoAdapter extends RecyclerView.Adapter<CetagoryTwoAdapter.MyViewHolder> {

    private ArrayList<CetagoryTwoModel> cetagoryTwoModels;
    private Context context;
    private SQLiteHandler db;
    private SQLiteHandlerForOnePlace dbb;
    private SessionManager session;
    private static final int MAX_WIDTH = 500;
    private static final int MAX_HEIGHT = 300;

    int size = (int) Math.ceil(Math.sqrt(MAX_WIDTH * MAX_HEIGHT));
    String place_id;
    CetagoryTwoModel newsModel;
    SliderLayout sliderShow;
    private static final String TAGG = AllPlaceAdapter.class.getSimpleName();

    public CetagoryTwoAdapter(Context context, List<CetagoryTwoModel> postLists) {
        this.cetagoryTwoModels = (ArrayList<CetagoryTwoModel>) postLists;
        this.context = context;
    }


    @Override
    public CetagoryTwoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.cetagory_card_view, parent, false);
        CetagoryTwoAdapter.MyViewHolder holder = new CetagoryTwoAdapter.MyViewHolder(view, context, cetagoryTwoModels);
        return holder;
    }

    @Override
    public void onBindViewHolder(final CetagoryTwoAdapter.MyViewHolder holder, int position) {
        final CetagoryTwoModel newsModel = cetagoryTwoModels.get(position);
        holder.name.setText(newsModel.getName());

        Picasso.with(holder.imageView.getContext())
                .load(newsModel.getImage())
                .transform(new BitmapTransform(MAX_WIDTH, MAX_HEIGHT))
                .skipMemoryCache()
                .resize(size, size)
                .placeholder(R.drawable.placeholder)
                .centerInside()
                .into(holder.imageView);




    }

    @Override
    public int getItemCount() {
        return cetagoryTwoModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name,rating,fact;
        ImageView imageView;
        ArrayList<CetagoryTwoModel> postLists = new ArrayList<>();
        ProgressWheel progressWheel;
        Context context;

        public MyViewHolder(View itemView, Context context, ArrayList<CetagoryTwoModel> postLists) {
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
//            newsModel=this.postLists.get(position);
            Intent intent=new Intent(this.context,CetagoryTwoActivity.class);
//
            intent.putExtra("POSITION",position);
//            intent.putExtra("lat","24.9045");
//            intent.putExtra("lon","91.8611");
//            intent.putExtra("division","Sylhet");
//            intent.putExtra("district","Sylhet");
//
//
//
//
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);


        }
    }


}
