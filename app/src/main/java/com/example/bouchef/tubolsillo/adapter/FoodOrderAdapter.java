package com.example.bouchef.tubolsillo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bouchef.tubolsillo.R;
import com.example.bouchef.tubolsillo.SplashScreen;
import com.example.bouchef.tubolsillo.BookOrderActivity;
import com.example.bouchef.tubolsillo.FilterActivity;
import com.example.bouchef.tubolsillo.FoodFilterActivity;
import com.example.bouchef.tubolsillo.HomeActivity;
import com.example.bouchef.tubolsillo.LocationActivity;
import com.example.bouchef.tubolsillo.ReviewActivity;
import com.example.bouchef.tubolsillo.model.Food;

import java.util.List;

/**
 * Created by World of UI/UX on 01/04/2019.
 */

public class FoodOrderAdapter extends RecyclerView.Adapter<FoodOrderAdapter.MyViewHolder> {

    Context context;
    private List<Food> OfferList;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
        }
    }

    public FoodOrderAdapter(Context context, List<Food> offerList) {
        this.OfferList = offerList;
        this.context = context;
    }

    @Override
    public FoodOrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_food_order, parent, false);
        return new FoodOrderAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        Food lists = OfferList.get(position);
        holder.title.setText(lists.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (position == 0) {
                    Intent i = new Intent(context, SplashScreen.class);
                    context.startActivity(i);
                    ((Activity)context).finish();
                }else if (position == 1) {
                    Intent i = new Intent(context, HomeActivity.class);
                    context.startActivity(i);
                }else if (position ==2){
                    Intent i = new Intent(context, BookOrderActivity.class);
                    context.startActivity(i);
                }else if (position ==3){
                    Intent i = new Intent(context, FilterActivity.class);
                    context.startActivity(i);
                }else if (position ==4){
                    Intent i = new Intent(context, FoodFilterActivity.class);
                    context.startActivity(i);
                }else if (position ==5){
                    Intent i = new Intent(context, ReviewActivity.class);
                    context.startActivity(i);
                }else if (position ==6){
                    Intent i = new Intent(context, LocationActivity.class);
                    context.startActivity(i);
                }
            }

        });

    }

    @Override
    public int getItemCount() {
        return OfferList.size();
    }

}


