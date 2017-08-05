package com.example.computergallery.happytour;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by joy on 4/21/17.
 */

public class AllReviewAdapter extends RecyclerView.Adapter<AllReviewAdapter.MyViewHolder> {

    private ArrayList<AllReviewModel> postLists;
    private Context context;

    public AllReviewAdapter(Context context, List<AllReviewModel> postLists) {
        this.postLists = (ArrayList<AllReviewModel>)postLists;
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       // View view= LayoutInflater.from(context).inflate(R.layout.card_view,parent,false);
        View view= LayoutInflater.from(context).inflate(R.layout.all_review_card_view,parent,false);
        MyViewHolder holder=new MyViewHolder(view,context,postLists);
        return holder;

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final AllReviewModel newsModel=postLists.get(position);
        holder.name.setText(Html.fromHtml(newsModel.getReviewUserName()+" Rating = "+newsModel.getReviewRating()));
        holder.comment.setText(Html.fromHtml(newsModel.getComment()));

        holder.r1.setVisibility(View.GONE);
        holder.r2.setVisibility(View.GONE);
        holder.r3.setVisibility(View.GONE);
        holder.r4.setVisibility(View.GONE);
        holder.r5.setVisibility(View.GONE);

        int user_review= Integer.parseInt(newsModel.getReviewRating());

       if (user_review==1){
           holder.r1.setVisibility(View.VISIBLE);
       }else if (user_review==2){
           holder.r1.setVisibility(View.VISIBLE);
           holder.r2.setVisibility(View.VISIBLE);
       }else if (user_review==3){
           holder.r1.setVisibility(View.VISIBLE);
           holder.r2.setVisibility(View.VISIBLE);
           holder.r3.setVisibility(View.VISIBLE);
       }else if (user_review==4){
           holder.r1.setVisibility(View.VISIBLE);
           holder.r2.setVisibility(View.VISIBLE);
           holder.r3.setVisibility(View.VISIBLE);
           holder.r4.setVisibility(View.VISIBLE);
       }else if (user_review==5){
           holder.r1.setVisibility(View.VISIBLE);
           holder.r2.setVisibility(View.VISIBLE);
           holder.r3.setVisibility(View.VISIBLE);
           holder.r4.setVisibility(View.VISIBLE);
           holder.r5.setVisibility(View.VISIBLE);
        }





        Picasso.with(context).load(newsModel.getReviewUserImage()).placeholder(R.mipmap.ic_launcher).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return postLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView comment;
        TextView name;
        ImageButton r1,r2,r3,r4,r5;
        ArrayList<AllReviewModel> postLists=new ArrayList<>();
        Context context;
        ImageView imageView;

        public MyViewHolder(View itemView, Context context, ArrayList<AllReviewModel> postLists) {
            super(itemView);
            this.context=context;
            this.postLists=postLists;
            name=(TextView) itemView.findViewById(R.id.card_name);
            comment=(TextView)itemView.findViewById(R.id.card_title);
            imageView=(ImageView)itemView.findViewById(R.id.card_image);


            r1=(ImageButton)itemView.findViewById(R.id.r1);
            r2=(ImageButton)itemView.findViewById(R.id.r2);
            r3=(ImageButton)itemView.findViewById(R.id.r3);
            r4=(ImageButton)itemView.findViewById(R.id.r4);
            r5=(ImageButton)itemView.findViewById(R.id.r5);


        }


    }

}
