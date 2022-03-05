package com.example.flickpickbeta;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.solver.state.State;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private Context mContext;
    private List<MovieModelClass> mData;

    public MovieAdapter(Context mContext, List<MovieModelClass> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater= LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.movie_item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.title.setText(mData.get(position).getTitle());
        holder.rating.setText(mData.get(position).getRating());
        //holder.info.setText(mData.get(position).getInfo());

        Glide.with(mContext).load("https://image.tmdb.org/t/p/w500"+mData.get(position).getImg()).into(holder.img);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext , ActivityDetails.class);

                Bundle bundle = new Bundle();
                bundle.putString("title" , mData.get(position).getTitle());
                bundle.putString("overview" , mData.get(position).getInfo());
                bundle.putString("poster" , mData.get(position).getImg());
                bundle.putString("rating" , mData.get(position).getRating());

                intent.putExtras(bundle);

                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {

        return mData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{


        ImageView img;
        TextView title;
        TextView rating;
        TextView info;
        ConstraintLayout constraintLayout;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            img= itemView.findViewById(R.id.id_image);
            title= itemView.findViewById(R.id.id_title);
            rating= itemView.findViewById(R.id.id_rating);
            //info= itemView.findViewById(R.id.id_info);
            constraintLayout= itemView.findViewById(R.id.main_layout);


        }
    }
}

