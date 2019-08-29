package com.example.bouchef.tubolsillo;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bouchef.tubolsillo.adapter.DashboardAdapter;
import com.example.bouchef.tubolsillo.api.APIService;
import com.example.bouchef.tubolsillo.api.Api;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelPOST;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelResponse;
import com.example.bouchef.tubolsillo.model.dashboard;
import com.example.bouchef.tubolsillo.utiles.Alerts;
import com.example.bouchef.tubolsillo.generics.ApplicationGlobal;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bouchef.tubolsillo.R.layout.activity_evaluar_tutor;

public class EvaluadorTutor extends AppCompatActivity {
    private Context mContext= EvaluadorTutor.this;


    private APIService api;

    @BindView(R.id.descripcion) TextView descripcion;
    @BindView(R.id.fechaAlta) TextView fechaAlta;
    @BindView(R.id.autorizarButton)
    ImageButton autorizarButton;

    @BindView(R.id.info)
    ImageView imageInfo;

    //private RecyclerView recyclerView;
    private DashboardAdapter adapter;
    private ArrayList<dashboard> dashboardList;
    private ArrayList<String> cars = new ArrayList<String>();
    private dashboard das;

    private Integer idTipoEvento;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_evaluar_tutor);

        ButterKnife.bind(this);

        api = Api.getAPIService(getApplicationContext());
        ApplicationGlobal applicationGlobal = ApplicationGlobal.getInstance();

        MensajeViewModelPOST mensajeViewModelPOST = new MensajeViewModelPOST();
        mensajeViewModelPOST.setIdUsuario(applicationGlobal.getUsuario().getId());
        mensajeViewModelPOST.setIdCompra(0);
        mensajeViewModelPOST.setIdTipoEvento(4);

        autorizarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), AutorizarTutor.class);
                startActivityForResult(intent, 0);
              finish();
            }
        });


    }


    private void cargarUltimoMensaje(MensajeViewModelResponse mensaje){
        if(mensaje != null) {
            descripcion.setText(mensaje.getDescripcion());
            fechaAlta.setText(mensaje.getFechaAlta());

            idTipoEvento = mensaje.getOrdenImportancia();
            if (idTipoEvento.equals(3)) {
                autorizarButton.setVisibility(View.VISIBLE);
            } else {
                imageInfo.setVisibility(View.VISIBLE);
            }
        }
    }


}
