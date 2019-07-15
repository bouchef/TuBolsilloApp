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
import com.example.bouchef.tubolsillo.api.Api;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelPOST;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelResponse;
import com.example.bouchef.tubolsillo.model.dashboard;
import com.example.bouchef.tubolsillo.utiles.Alerts;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bouchef.tubolsillo.R.layout.activity_estoy_aqui_pcd;

public class AquiEstoyPCD extends AppCompatActivity {
    private Context mContext= AquiEstoyPCD.this;

    private APIService api;

    @BindView(R.id.descripcion) TextView descripcion;
    @BindView(R.id.fechaAlta) TextView fechaAlta;



    //private RecyclerView recyclerView;
    private DashboardAdapter adapter;
    private ArrayList<dashboard> dashboardList;
    private ArrayList<String> cars = new ArrayList<String>();
    private dashboard das;
    private String lenguajeProgramacion[]=new String[]{"Alertar","Comprar","Notificar","Estoy Aqui","Credencial"};
    private Integer[] imgid={
            R.drawable.ic1,
            R.drawable.ic2,
            R.drawable.ic3,
            R.drawable.ic4,
            R.drawable.ic5
    };

    private ListView lista;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_estoy_aqui_pcd);

        ButterKnife.bind(this);

        api = Api.getAPIService(getApplicationContext());

        MensajeViewModelPOST mensajeViewModelPOST = new MensajeViewModelPOST();
        mensajeViewModelPOST.setIdUsuario(1);
        mensajeViewModelPOST.setIdCompra(0);
        mensajeViewModelPOST.setIdTipoEvento(4);

        api.getUltimoMensaje(mensajeViewModelPOST.getIdCompra(),mensajeViewModelPOST.getIdUsuario(),mensajeViewModelPOST.getIdTipoEvento()).enqueue(new Callback<MensajeViewModelResponse>() {
            @Override
            public void onResponse(Call<MensajeViewModelResponse> call, Response<MensajeViewModelResponse> response) {
                if(response.isSuccessful()){
                    //*Alerts.newToastLarge(mContext, "OK");*/
                    cargarUltimoMensaje(response.body());
                }else{
                    if (response.code() != 404) {
                        Alerts.newToastLarge(mContext, "ERR");
                    }
                    else
                    {
                        //cargarUltimoMensaje(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<MensajeViewModelResponse> call, Throwable t) {
                Alerts.newToastLarge(mContext, "ErrErr");
            }
        });


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

    private void cargarUltimoMensaje(MensajeViewModelResponse mensaje){
        descripcion.setText(mensaje.getDescripcion());
        fechaAlta.setText(mensaje.getFechaAlta());
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
