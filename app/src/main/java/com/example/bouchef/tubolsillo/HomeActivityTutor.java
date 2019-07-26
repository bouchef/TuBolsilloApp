package com.example.bouchef.tubolsillo;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toolbar;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bouchef.tubolsillo.adapter.DashboardAdapter;
import com.example.bouchef.tubolsillo.model.dashboard;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeActivityTutor extends Activity {
    private Context mContext= HomeActivityTutor.this;

    private RecyclerView recyclerView;
    private DashboardAdapter adapter;
    private List<dashboard> dashboardList;

    RelativeLayout rlTop;
    AppBarLayout Appbar;
    CollapsingToolbarLayout CoolToolbar;
    Toolbar toolbar;

    boolean ExpandedActionBar = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_tutor);

        //rlTop=(RelativeLayout)findViewById(R.id.rltop);
        //Appbar = (AppBarLayout)findViewById(R.id.appbar);
        //CoolToolbar = (CollapsingToolbarLayout)findViewById(R.id.ctolbar);
        //toolbar = (Toolbar) findViewById(R.id.toolbar);

        //setSupportActionBar(toolbar);
        CoolToolbar.setTitle("");

        Appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (Math.abs(verticalOffset) > 200){
                    ExpandedActionBar = false;
                    CoolToolbar.setTitle("My Dashboard");
                    rlTop.setVisibility(View.GONE);
                    invalidateOptionsMenu();
                } else {
                    ExpandedActionBar = true;
                    CoolToolbar.setTitle("");
                    rlTop.setVisibility(View.VISIBLE);
                    invalidateOptionsMenu();
                }
            }
        });

        ImageView imgProfile=(ImageView)findViewById(R.id.profile_image);
        Glide.with(mContext)
                .load(R.drawable.jack_sparrow)
                .apply(RequestOptions.circleCropTransform())
                .into(imgProfile);

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

        dashboardList.add(new dashboard(R.drawable.ic1,"General"));
        dashboardList.add(new dashboard(R.drawable.ic2,"Transport"));
        dashboardList.add(new dashboard(R.drawable.ic3,"Shooping"));
        dashboardList.add(new dashboard(R.drawable.ic4,"Bills"));
        dashboardList.add(new dashboard(R.drawable.ic5,"Entertainment"));
        dashboardList.add(new dashboard(R.drawable.ic6,"Grocery"));
        dashboardList.add(new dashboard(R.drawable.ic1,"General"));
        dashboardList.add(new dashboard(R.drawable.ic2,"Transport"));
        dashboardList.add(new dashboard(R.drawable.ic3,"Shooping"));
        dashboardList.add(new dashboard(R.drawable.ic4,"Bills"));
        dashboardList.add(new dashboard(R.drawable.ic5,"Entertainment"));
        dashboardList.add(new dashboard(R.drawable.ic6,"Grocery"));
        dashboardList.add(new dashboard(R.drawable.ic1,"General"));
        dashboardList.add(new dashboard(R.drawable.ic2,"Transport"));
        dashboardList.add(new dashboard(R.drawable.ic3,"Shooping"));
        dashboardList.add(new dashboard(R.drawable.ic4,"Bills"));
        dashboardList.add(new dashboard(R.drawable.ic5,"Entertainment"));
        dashboardList.add(new dashboard(R.drawable.ic6,"Grocery"));

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
