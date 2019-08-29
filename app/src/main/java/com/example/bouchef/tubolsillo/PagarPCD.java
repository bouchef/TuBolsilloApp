package com.example.bouchef.tubolsillo;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bouchef.tubolsillo.adapter.DashboardAdapter;
import com.example.bouchef.tubolsillo.api.APIService;
import com.example.bouchef.tubolsillo.api.Api;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelPOST;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelResponse;
import com.example.bouchef.tubolsillo.generics.ApplicationGlobal;
import com.example.bouchef.tubolsillo.model.dashboard;
import com.example.bouchef.tubolsillo.utiles.Alerts;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bouchef.tubolsillo.R.layout.activity_pagar_pcd;

public class PagarPCD extends AppCompatActivity {
    private Context mContext= PagarPCD.this;


    private APIService api;
    @BindView(R.id.accion)
    ImageView btn_accion;
    @BindView(R.id.irHome) ImageView btn_home;
    @BindView(R.id.tit_barra) TextView titulo;

    //private RecyclerView recyclerView;
    private DashboardAdapter adapter;
    private ArrayList<dashboard> dashboardList;
    private ArrayList<String> cars = new ArrayList<String>();
    private dashboard das;

    private String lenguajeProgramacion[]=new String[]{};
    private Integer[] imgid={};
    private ListView lista;

    private EditText importe;
    private EditText mail;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_pagar_pcd);

        titulo =  findViewById(R.id.tit_barra);
        titulo.setText(R.string.tit_pagar_pcd);

        ApplicationGlobal applicationGlobal = ApplicationGlobal.getInstance();

        ButterKnife.bind(this);

        api = Api.getAPIService(getApplicationContext());

        MensajeViewModelPOST mensajeViewModelPOST = new MensajeViewModelPOST();
        mensajeViewModelPOST.setIdUsuario(applicationGlobal.getUsuario().getId());
        mensajeViewModelPOST.setIdCompra(0);
        mensajeViewModelPOST.setIdTipoEvento(4);

        api.getUltimoMensaje(mensajeViewModelPOST.getIdUsuario(),mensajeViewModelPOST.getIdCompra(),mensajeViewModelPOST.getIdTipoEvento()).enqueue(new Callback<MensajeViewModelResponse>() {
            @Override
            public void onResponse(Call<MensajeViewModelResponse> call, Response<MensajeViewModelResponse> response) {
                if(response.isSuccessful()){
                    //*Alerts.newToastLarge(mContext, "OK");*/
                    //cargarUltimoMensaje(response.body());
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


        mail = (EditText) findViewById(R.id.mailTxt);
        importe = (EditText) findViewById(R.id.importeTxt);
        String item = "PAGAR COMPRA 1 ($"+importe.getText().toString()+")";

        Button btnPCD = (Button) findViewById(R.id.button);
        btnPCD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //enviar mensaje("Cancelando Compra y Volviendo")
                Toast.makeText(getApplicationContext(), "Cancelando Compra y Volviendo", Toast.LENGTH_SHORT).show();

                api.actualizarCompra(applicationGlobal.getCompra().getId(),8,0).enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if(response.isSuccessful()){
                            applicationGlobal.getCompra().setIdEstado(4);
                        }else{
                            Alerts.newToastLarge(getApplicationContext(), "Err");
                        }

                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Alerts.newToastLarge(getApplicationContext(), "Err");

                    }
                });

                api.actualizarCompra(applicationGlobal.getCompra().getId(),9,0).enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if(response.isSuccessful()){
                            applicationGlobal.getCompra().setIdEstado(4);
                        }else{
                            Alerts.newToastLarge(getApplicationContext(), "Err");
                        }

                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Alerts.newToastLarge(getApplicationContext(), "Err");

                    }
                });

                Intent intent = new Intent (v.getContext(), BotoneraInicialPCD.class);
                startActivityForResult(intent, 0);
              finish();
            }
        });

        Button btnPagar = (Button) findViewById(R.id.button1);
        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(importe.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Debe Colocar un importe", Toast.LENGTH_SHORT).show();
                }else {
                    if(mail.getText().toString().isEmpty()){
                        Toast.makeText(getApplicationContext(), "Debe colocar un mail de vendendor", Toast.LENGTH_SHORT).show();
                    }else{
                        ApplicationGlobal applicationGlobal = ApplicationGlobal.getInstance();

                        if(applicationGlobal.getUsuario().getIdTipoUsuario().equals(2)) {
                            // TIENE PERFIL DE AYUDANTE => ES QUIEN PAGA
                            // enviar updateCompra()
                            Toast.makeText(getApplicationContext(), "ATENCION: PAGANDO COMPRA 1 ($" + importe.getText().toString() + ")", Toast.LENGTH_SHORT).show();

                            api.actualizarCompra(applicationGlobal.getCompra().getId(), 4, Double.parseDouble(importe.getText().toString())).enqueue(new Callback<Boolean>() {
                                @Override
                                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                    if (response.isSuccessful()) {
                                        applicationGlobal.getCompra().setIdEstado(4);
                                    } else {
                                        Alerts.newToastLarge(getApplicationContext(), "Err");
                                    }

                                }

                                @Override
                                public void onFailure(Call<Boolean> call, Throwable t) {
                                    Alerts.newToastLarge(getApplicationContext(), "Err");

                                }

                            });
                        }else{
                            //enviar mensaje("Autorizar pago Importe")
                            Alerts.newToastLarge(getApplicationContext(), "OJO!!! NO ESTA ENVIANDO EL MENSAJE PARA AUTORIZAR");
                        }

                    }
                }
            }
        });

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
                        Alerts.newToastLarge(mContext, "Marcar Mensaje como leido");
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



}
