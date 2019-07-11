package com.example.bouchef.tubolsillo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bouchef.tubolsillo.R;
import com.example.bouchef.tubolsillo.model.BookOrder;

import java.util.ArrayList;

/**
 * Created by World of UI/UX on 01/04/2019.
 */

public class BookOrderAdapter extends RecyclerView.Adapter<BookOrderAdapter.MyViewHolder> {

    Context context;
    private ArrayList<com.example.bouchef.tubolsillo.model.BookOrder> OfferList;
    int myPos = 0;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title;
        LinearLayout linear;

        public MyViewHolder(View view) {
            super(view);

            image = (ImageView) view.findViewById(R.id.image);
            title = (TextView) view.findViewById(R.id.title);
            linear = (LinearLayout) view.findViewById(R.id.linear);
        }
    }

    public BookOrderAdapter(Context context, ArrayList<com.example.bouchef.tubolsillo.model.BookOrder> offerList) {
        this.OfferList = offerList;
        this.context = context;
    }

    @Override
    public BookOrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_book_order, parent, false);
        return new BookOrderAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final BookOrder lists = OfferList.get(position);
        holder.image.setImageResource(lists.getImage());
        holder.title.setText(lists.getTitle());

        if (myPos == position){
            holder.title.setTextColor(Color.parseColor("#000000"));
            holder.linear.setBackgroundResource(R.drawable.ic_selector_1);
        }else {
            holder.title.setTextColor(Color.parseColor("#484646"));
            holder.linear.setBackgroundResource(R.drawable.ic_selector_2);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myPos = position;
                notifyDataSetChanged();
            }


        });

    }

    @Override
    public int getItemCount() {
        return OfferList.size();
    }

}


