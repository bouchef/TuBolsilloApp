package com.example.bouchef.tubolsillo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bouchef.tubolsillo.R;
import com.example.bouchef.tubolsillo.model.Popular;

import java.util.List;

/**
 * Created by World of UI/UX on 01/04/2019.
 */

public class HomePopularAdapter extends RecyclerView.Adapter<HomePopularAdapter.MyViewHolder> {

    Context context;
    private List<Popular> OfferList;



    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title;
        public MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.image);
            title = (TextView) view.findViewById(R.id.title);
        }

    }

    public HomePopularAdapter(Context context, List<Popular> offerList) {
        this.OfferList = offerList;
        this.context = context;
    }

    @Override
    public HomePopularAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_home_popular, parent, false);
        return new HomePopularAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final Popular lists = OfferList.get(position);
        holder.image.setImageResource(lists.getImage());
        holder.title.setText(lists.getTitle());
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(context, All_Services_Activity.class);
//                context.startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return OfferList.size();
    }

}


