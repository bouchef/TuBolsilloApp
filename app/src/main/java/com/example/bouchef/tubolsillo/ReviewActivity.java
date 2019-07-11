package com.example.bouchef.tubolsillo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bouchef.tubolsillo.adapter.ReviewAdapter;
import com.example.bouchef.tubolsillo.model.Review;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {

    TextView title;
    private ArrayList<Review> reviewModelClas;
    private RecyclerView recyclerView;
    private ReviewAdapter bAdapter;
    private String title1[] = {"Jack Sparrow","Jack Sparrow","Jack Sparrow","Jack Sparrow"};
    private Integer image[] = {R.drawable.ic_favorite_black_24dp, R.drawable.ic_favorite_border_black_24dp, R.drawable.ic_favorite_black_24dp, R.drawable.ic_favorite_border_black_24dp,};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        title = findViewById(R.id.title);
        title.setText("Restaurant Review");

        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ReviewActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        reviewModelClas = new ArrayList<>();

        for (int i = 0; i < title1.length; i++) {
            Review beanClassForRecyclerView_contacts = new Review(title1[i],image[i]);
            reviewModelClas.add(beanClassForRecyclerView_contacts);
        }
        bAdapter = new ReviewAdapter(ReviewActivity.this,reviewModelClas);
        recyclerView.setAdapter(bAdapter);
    }
}
