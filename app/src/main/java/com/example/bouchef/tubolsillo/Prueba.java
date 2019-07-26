package com.example.bouchef.tubolsillo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bouchef.tubolsillo.adapter.DashboardAdapter;
import com.example.bouchef.tubolsillo.model.dashboard;

import java.util.ArrayList;
import java.util.List;

import static com.example.bouchef.tubolsillo.R.layout.activity_prueba;

public class Prueba extends AppCompatActivity {
    private Context mContext = Prueba.this;

    private RecyclerView recyclerView;
    private DashboardAdapter adapter;
    private List<dashboard> dashboardList;

    //private ArrayList<dashboard> dashboardList;
//    private ArrayList<String> cars = new ArrayList<String>();
//    private dashboard das;
//    private Integer image[] = {R.drawable.coffee_cup, R.drawable.doughnut, R.drawable.cake, R.drawable.egg,
//            R.drawable.burgerrrr, R.drawable.fries, R.drawable.pizza, R.drawable.noodles, R.drawable.fish, R.drawable.buffet};
//    private String txt[] = {"Coffee", "Donut", "Cake", "Omelette", "Burger", "French Fries", "Pizza", "Noodles", "Fish", "More"};
//
//    private ListView lista;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_prueba);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        dashboardList = new ArrayList<>();
        adapter = new DashboardAdapter(mContext,dashboardList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContext, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        getMenu();


    }

    private void getMenu() {

        dashboardList.add(new dashboard(R.drawable.burger,"General"));
//        dashboardList.add(new dashboard(R.drawable.ic2,"Transport"));
//        dashboardList.add(new dashboard(R.drawable.ic3,"Shooping"));
//        dashboardList.add(new dashboard(R.drawable.ic4,"Bills"));
//        dashboardList.add(new dashboard(R.drawable.ic5,"Entertainment"));
//        dashboardList.add(new dashboard(R.drawable.ic6,"Grocery"));
//        dashboardList.add(new dashboard(R.drawable.ic1,"General"));
//        dashboardList.add(new dashboard(R.drawable.ic2,"Transport"));
//        dashboardList.add(new dashboard(R.drawable.ic3,"Shooping"));
//        dashboardList.add(new dashboard(R.drawable.ic4,"Bills"));
//        dashboardList.add(new dashboard(R.drawable.ic5,"Entertainment"));
//        dashboardList.add(new dashboard(R.drawable.ic6,"Grocery"));
//        dashboardList.add(new dashboard(R.drawable.ic1,"General"));
//        dashboardList.add(new dashboard(R.drawable.ic2,"Transport"));
//        dashboardList.add(new dashboard(R.drawable.ic3,"Shooping"));
//        dashboardList.add(new dashboard(R.drawable.ic4,"Bills"));
//        dashboardList.add(new dashboard(R.drawable.ic5,"Entertainment"));
//        dashboardList.add(new dashboard(R.drawable.ic6,"Grocery"));
//
        adapter.notifyDataSetChanged();
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    public int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}





