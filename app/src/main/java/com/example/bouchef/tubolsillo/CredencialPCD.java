package com.example.bouchef.tubolsillo;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bouchef.tubolsillo.adapter.DashboardAdapter;
import com.example.bouchef.tubolsillo.api.APIService;
import com.example.bouchef.tubolsillo.api.Api;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelPOST;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelResponse;
import com.example.bouchef.tubolsillo.api.model.PCDViewModelPOST;
import com.example.bouchef.tubolsillo.api.model.PCDViewModelResponse;
import com.example.bouchef.tubolsillo.generics.ApplicationGlobal;
import com.example.bouchef.tubolsillo.model.dashboard;
import com.example.bouchef.tubolsillo.utiles.Alerts;
import com.example.bouchef.tubolsillo.utiles.FechaUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bouchef.tubolsillo.R.layout.activity_credencial_pcd;

public class CredencialPCD extends AppCompatActivity {
    private Context mContext= CredencialPCD.this;
    private APIService api;

    @BindView(R.id.accion) ImageView btn_accion;

    private DashboardAdapter adapter;
    private ArrayList<dashboard> dashboardList;
    private ArrayList<String> cars = new ArrayList<String>();
    private dashboard das;

    @BindView(R.id.nombre) TextView nombre;
    @BindView(R.id.nroCertificado) TextView nroCertificado;
    @BindView(R.id.documento) TextView documento;
    @BindView(R.id.fechaNacimiento) TextView fechaNacimiento;
    @BindView(R.id.diagnosticoFuncional) TextView diagnosticoFuncional;
    @BindView(R.id.orientacionPrestacional) TextView orientacionPrestacional;
    @BindView(R.id.fechaVencimiento) TextView fechaVencimiento;
    @BindView(R.id.telefono) TextView telefono;
    @BindView(R.id.ayudante) TextView ayudante;
    //@BindView(R.id.ayudante) TextView ayudante;
    @BindView(R.id.descripcion) TextView descripcion;
    @BindView(R.id.fechaAlta) TextView fechaAlta;

    @BindView(R.id.irHome) ImageView btn_home;

    @BindView(R.id.tit_barra) TextView titulo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_credencial_pcd);

        titulo =  findViewById(R.id.tit_barra);
        titulo.setText(R.string.tit_credencial);

        ButterKnife.bind(this);

        ApplicationGlobal applicationGlobal = ApplicationGlobal.getInstance();

        api = Api.getAPIService(getApplicationContext());

        MensajeViewModelPOST mensajeViewModelPOST = new MensajeViewModelPOST();
        mensajeViewModelPOST.setIdUsuario(2);
        mensajeViewModelPOST.setIdCompra(0);
        mensajeViewModelPOST.setIdTipoEvento(4);


        PCDViewModelPOST pcdViewModelPOST = new PCDViewModelPOST();
        pcdViewModelPOST.setNroCertificado("01145456783");
        pcdViewModelPOST.setIdUsuario(2);
        api.getPCD(pcdViewModelPOST).enqueue(new Callback<PCDViewModelResponse>() {
            @Override
            public void onResponse(Call<PCDViewModelResponse> call, Response<PCDViewModelResponse> response) {
                if(response.isSuccessful()){
                    cargarPCD(response.body());
                }else{
                    Alerts.newToastLarge(mContext, "ERR");
                }
            }

            @Override
            public void onFailure(Call<PCDViewModelResponse> call, Throwable t) {
                Alerts.newToastLarge(mContext, "ErrErr");
            }
        });

        cargarUsuario(applicationGlobal);

        // ACCION DEL BOTON DE MENSAJE
        btn_accion =  findViewById(R.id.accion);
        String imageId = (String) btn_accion.getTag();

        btn_accion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(applicationGlobal.getUsuario().getIdTipoUsuario().equals(1)) {
                    if(imageId.equals("Autorizacion")) {
                        Intent intent = new Intent(v.getContext(), AutorizarTutor.class);
                        startActivityForResult(intent, 0);
                      finish();
                    }
                    if(imageId.equals("Informacion")) {
                        // Marcar mensaje como leido y actualizar
                        Alerts.newToastLarge(getApplicationContext(), "Marcar Mensaje como leido");
                    }
                }

            }
        });
        // FIN ACCION DEL BOTON MENSAJE
        // ACCION DEL BOTON DE IR A HOME
        btn_home =  findViewById(R.id.irHome);

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(applicationGlobal.getUsuario().getIdTipoUsuario().equals(1)) {
                    Intent intent = new Intent(v.getContext(), BotoneraInicialAyudante.class);
                    startActivityForResult(intent, 0);
                  finish();
                }else {
                    Intent intent = new Intent(v.getContext(), BotoneraInicialPCD.class);
                    startActivityForResult(intent, 0);
                  finish();
                }

            }

        });
        // FIN ACCION DEL BOTON IR A HOME

    }



    private void cargarPCD(PCDViewModelResponse pcd){
        nombre.setText(pcd.getNombre() + " " + pcd.getApellido());
        nroCertificado.setText(pcd.getNroCertificado());
        documento.setText(pcd.getTipoDocumento() + " "+ pcd.getNroDocumento());
        orientacionPrestacional.setText(pcd.getOrientacionPrestacional());
        fechaNacimiento.setText(pcd.getFechaNacimiento());
        fechaVencimiento.setText(pcd.getFechaVencimiento());
        diagnosticoFuncional.setText(pcd.getDiagnosticoFuncional());
        //ayudante.setText(pcd.getUsuario());
    }

    private void cargarUsuario(ApplicationGlobal global) {
        telefono.setText(global.getUsuario().getMovil());
        ayudante.setText(global.getUsuario().getUsuarioPadre().getNombre());
    }

    private void getMenu() {

        das = new dashboard(R.drawable.ic1,"General");
        cars.add("Volvo");
        cars.add("BMW");
        cars.add("Ford");
        cars.add("Mazda");
        dashboardList.add(das);

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

    private void cargarUltimoMensaje(MensajeViewModelResponse mensaje){
        if(mensaje != null) {
            String t = FechaUtils.fromStringToVerbose(mensaje.getFechaAlta());

            descripcion.setText(mensaje.getDescripcion());
            fechaAlta.setText(t);
        }
    }
}
