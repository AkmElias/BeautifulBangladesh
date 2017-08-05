package com.example.computergallery.happytour.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.example.computergallery.happytour.BitmapTransform;
import com.example.computergallery.happytour.ModelClass.CetagoryAllModel;
import com.example.computergallery.happytour.R;
import com.example.computergallery.happytour.helper.SQLiteHandler;
import com.example.computergallery.happytour.helper.SQLiteHandlerForOnePlace;
import com.example.computergallery.happytour.helper.SessionManager;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class CetagoryAllAdapter extends RecyclerView.Adapter<CetagoryAllAdapter.MyViewHolder> {

    private ArrayList<CetagoryAllModel> cetagoryAllModels;
    private Context context;
    private SQLiteHandler db;
    private SQLiteHandlerForOnePlace dbb;
    private SessionManager session;
    String place_id;
    CetagoryAllModel newsModel;
    SliderLayout sliderShow;
    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 500;

    int size = (int) Math.ceil(Math.sqrt(MAX_WIDTH * MAX_HEIGHT));
    private static final String TAGG = AllPlaceAdapter.class.getSimpleName();

    public CetagoryAllAdapter(Context context, List<CetagoryAllModel> postLists) {
        this.cetagoryAllModels = (ArrayList<CetagoryAllModel>) postLists;
        this.context = context;
    }


    @Override
    public CetagoryAllAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_view_for_cetagory, parent, false);
        CetagoryAllAdapter.MyViewHolder holder = new CetagoryAllAdapter.MyViewHolder(view, context, cetagoryAllModels);
        return holder;
    }

    @Override
    public void onBindViewHolder(final CetagoryAllAdapter.MyViewHolder holder, int position) {
        final CetagoryAllModel newsModel = cetagoryAllModels.get(position);
        holder.name.setText(newsModel.getTitle());



        Picasso.with(holder.imageView.getContext())
                .load(newsModel.getImage1())
                .transform(new BitmapTransform(MAX_WIDTH, MAX_HEIGHT))
                .skipMemoryCache()
                .resize(size, size)
                .centerInside()
                .into(holder.imageView, new Callback() {
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
        return cetagoryAllModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name,rating,fact;
        ImageView imageView;
        ArrayList<CetagoryAllModel> postLists = new ArrayList<>();
        ProgressWheel progressWheel;
        Context context;

        public MyViewHolder(View itemView, Context context, ArrayList<CetagoryAllModel> postLists) {
            super(itemView);
            this.context = context;
            this.postLists = postLists;
            name=(TextView)itemView.findViewById(R.id.Name);
            imageView = (ImageView) itemView.findViewById(R.id.Image);
            progressWheel = (ProgressWheel) itemView.findViewById(R.id.progress_wheel) ;


            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

//            int position=getAdapterPosition();
//            newsModel=this.postLists.get(position);
//            Intent intent=new Intent(this.context,NextActivity.class);
//            intent.putExtra("id",newsModel.getId());
//            intent.putExtra("name",newsModel.getTitle());
//            intent.putExtra("description",newsModel.getDetails());
//            intent.putExtra("rating",newsModel.getRating());
//            intent.putExtra("image1",newsModel.getImage1());
//            intent.putExtra("image2",newsModel.getImage2());
//            intent.putExtra("image3",newsModel.getImage3());
//
//
//                intent.putExtra("loc",newsModel.getLocation());
//
//
//            intent.putExtra("lat","24.9045");
//            intent.putExtra("lon","91.8611");
//            intent.putExtra("division","Sylhet");
//            intent.putExtra("district","Sylhet");
//
//
//
//
//            //new task
//            // SQLite database handler
//            dbb = new SQLiteHandlerForOnePlace(context.getApplicationContext());
//            // Inserting row in users table
//            dbb.deleteUsers();
//
//            dbb.addUser(newsModel.getId(),newsModel.getTitle(),newsModel.getDetails(),newsModel.getRating(),newsModel.getImage1(),newsModel.getImage2(),newsModel.getImage3(),newsModel.getLocation(),"24.9045","91.8611","Sylhet","Sylhet");
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
