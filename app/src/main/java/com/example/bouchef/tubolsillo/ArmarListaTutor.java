package com.example.bouchef.tubolsillo;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bouchef.tubolsillo.adapter.DashboardAdapter;
import com.example.bouchef.tubolsillo.adapter.LenguajeListAdapter;
import com.example.bouchef.tubolsillo.api.APIService;
import com.example.bouchef.tubolsillo.model.dashboard;

import java.util.ArrayList;

import butterknife.BindView;

import static com.example.bouchef.tubolsillo.R.layout.activity_armar_lista_tutor;

public class ArmarListaTutor extends AppCompatActivity {
    private Context mContext= ArmarListaTutor.this;


    private APIService api;

    @BindView(R.id.descripcion)
    TextView descripcion;
    @BindView(R.id.fechaAlta) TextView fechaAlta;

    //private RecyclerView recyclerView;
    private DashboardAdapter adapter;
    private ArrayList<dashboard> dashboardList;
    private ArrayList<String> cars = new ArrayList<String>();
    private dashboard das;
    private String lenguajeProgramacion[]=new String[]{"Categoria 1","Categoria 2","Categoria 3","Categoria 4","Categoria 5"};
    private Integer[] imgid={
            R.drawable.chocolate,
            R.drawable.canned_food,
            R.drawable.chicken,
            R.drawable.milk,
            R.drawable.watermelon
    };

    private ListView lista;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_armar_lista_tutor);

        LenguajeListAdapter adapter=new LenguajeListAdapter(this,lenguajeProgramacion,imgid);
        lista=(ListView)findViewById(R.id.mi_lista);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Slecteditem= lenguajeProgramacion[+position];
                Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void getMenu() {

        das = new dashboard(R.drawable.ic1,"General");
        cars.add("Volvo");
        cars.add("BMW");
        cars.add("Ford");
        cars.add("Mazda");
        dashboardList.add(das);
        /*dashboardList.add(new dashboard(R.drawable.ic2,"Transport"));
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
        dashboardList.add(new dashboard(R.drawable.ic6,"Grocery"));*/

        Toast.makeText(getApplicationContext(), "pasa", Toast.LENGTH_LONG).show();
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
