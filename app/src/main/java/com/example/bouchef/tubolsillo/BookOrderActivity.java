package com.example.bouchef.tubolsillo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bouchef.tubolsillo.adapter.BookOrderAdapter;
import com.example.bouchef.tubolsillo.model.BookOrder;

import java.util.ArrayList;

/**
 * Created by World of UI/UX on 01/04/2019.
 */

public class BookOrderActivity extends AppCompatActivity {

    TextView title;
    private ArrayList<BookOrder> bookOrderModelClasses;
    private RecyclerView recyclerView;
    private BookOrderAdapter bAdapter;

    private Integer image[] = {R.drawable.coffee_cup, R.drawable.doughnut, R.drawable.cake, R.drawable.egg,
    R.drawable.burgerrrr, R.drawable.fries, R.drawable.pizza, R.drawable.noodles, R.drawable.fish, R.drawable.buffet};
    private String txt[]={"Coffee","Donut","Cake","Omelette","Burger","French Fries","Pizza","Noodles","Fish","More"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_order);

        title = findViewById(R.id.title);
        title.setText("Most Popular");

        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(BookOrderActivity.this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        bookOrderModelClasses = new ArrayList<>();

        for (int i = 0; i < image.length; i++) {
            BookOrder beanClassForRecyclerView_contacts = new BookOrder(image[i],txt[i]);
            bookOrderModelClasses.add(beanClassForRecyclerView_contacts);
        }
        bAdapter = new BookOrderAdapter(BookOrderActivity.this,bookOrderModelClasses);
        recyclerView.setAdapter(bAdapter);
    }
}
