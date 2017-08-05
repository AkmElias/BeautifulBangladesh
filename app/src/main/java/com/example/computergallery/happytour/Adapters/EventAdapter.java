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
import com.example.computergallery.happytour.EventActivity;
import com.example.computergallery.happytour.ModelClass.EventModel;
import com.example.computergallery.happytour.ModelClass.allHotelModel;
import com.example.computergallery.happytour.NextActivity;
import com.example.computergallery.happytour.R;
import com.example.computergallery.happytour.helper.SQLiteHandler;
import com.example.computergallery.happytour.helper.SQLiteHandlerForOnePlace;
import com.example.computergallery.happytour.helper.SessionManager;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 8/5/2017.
 */

public class EventAdapter  extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

    private ArrayList<EventModel> eventModelsList;
    private Context context;
    private SQLiteHandler db;
    private SQLiteHandlerForOnePlace dbb;
    private SessionManager session;
    String place_id;
    EventModel newsModel;
    SliderLayout sliderShow;
    private static final String TAGG = AllPlaceAdapter.class.getSimpleName();

    public EventAdapter(Context context, List<EventModel> postLists) {
        this.eventModelsList = (ArrayList<EventModel>) postLists;
        this.context = context;
    }


    @Override
    public EventAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_view_for_cetagory, parent, false);
        EventAdapter.MyViewHolder holder = new EventAdapter.MyViewHolder(view, context, eventModelsList);
        return holder;
    }

    @Override
    public void onBindViewHolder(final EventAdapter.MyViewHolder holder, int position) {
        final EventModel newsModel = eventModelsList.get(position);
        holder.name.setText(newsModel.getTitle());


        Picasso.with(context).load(newsModel.getImage1()).into(holder.imageView, new Callback() {
            @Override
            public void onSuccess() {
                holder.progressWheel.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onError() {

            }
        });


    }

    @Override
    public int getItemCount() {
        return eventModelsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name,rating,fact;
        ImageView imageView;
        ArrayList<EventModel> postLists = new ArrayList<>();
        ProgressWheel progressWheel;
        Context context;

        public MyViewHolder(View itemView, Context context, ArrayList<EventModel> postLists) {
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
            Intent intent=new Intent(this.context,EventActivity.class);
            intent.putExtra("id",newsModel.getId());
            intent.putExtra("name",newsModel.getTitle());
            intent.putExtra("wewilldo",newsModel.getWeWillDo());
            intent.putExtra("whatprovide",newsModel.getWhatprovide());
            intent.putExtra("wherebe",newsModel.getWherebe());
            intent.putExtra("note",newsModel.getNote());
            intent.putExtra("about",newsModel.getAbout());
            intent.putExtra("contact",newsModel.getContact());
            intent.putExtra("host",newsModel.getHost());
            intent.putExtra("image1",newsModel.getImage1());
            intent.putExtra("image2",newsModel.getImage2());
            intent.putExtra("image3",newsModel.getImage3());




            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);


        }
    }


}
