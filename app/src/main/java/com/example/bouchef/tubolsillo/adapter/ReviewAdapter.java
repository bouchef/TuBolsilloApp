package com.example.bouchef.tubolsillo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bouchef.tubolsillo.R;
import com.example.bouchef.tubolsillo.model.Review;

import java.util.List;

/**
 * Created by World of UI/UX on 01/04/2019.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {

    Context context;
    private List<Review> OfferList;
    int myPos = 0;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView image,imageProfile;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            image = (ImageView)view.findViewById(R.id.like);
            imageProfile = (ImageView)view.findViewById(R.id.profile_image);
        }
    }

    public ReviewAdapter(Context context, List<Review> offerList) {
        this.OfferList = offerList;
        this.context = context;
    }

    @Override
    public ReviewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_review, parent, false);
        return new ReviewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        Review lists = OfferList.get(position);
        holder.title.setText(lists.getTitle());
        holder.image.setImageResource(lists.getImage());
        Glide.with(context)
                .load(R.drawable.jack_sparrow)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.imageProfile);

        if (position % 2 == 0  ){
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        }else
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#f8f8f8"));
        }
    }

    @Override
    public int getItemCount() {
        return OfferList.size();
    }

}


