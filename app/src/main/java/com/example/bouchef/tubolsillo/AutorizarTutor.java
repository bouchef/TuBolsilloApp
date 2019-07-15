package com.example.bouchef.tubolsillo;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bouchef.tubolsillo.adapter.DashboardAdapter;
import com.example.bouchef.tubolsillo.adapter.LenguajeListAdapter;
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

import static com.example.bouchef.tubolsillo.R.layout.activity_autorizar_tutor;

public class AutorizarTutor extends AppCompatActivity {
    private Context mContext= AutorizarTutor.this;


    private APIService api;

    @BindView(R.id.descripcion)
    TextView descripcion;
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
    /*private String lenguajeProgramacion[]=new String[]{"Compra 1","Producto 2","Producto 3","Producto 4","Producto 5"};
    private Integer[] imgid={
            R.drawable.eggs,
            R.drawable.bottle,
            R.drawable.milk,
            R.drawable.cheese,
            R.drawable.cereals
    };*/
    private String lenguajeProgramacion[]=new String[]{"Compra 1 $10"};
    private Integer[] imgid={
            R.drawable.list
    };

    private ListView lista;

    private Integer idTipoEvento;

    private MensajeViewModelResponse ultimoMensaje;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_autorizar_tutor);



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
                    ultimoMensaje = response.body();
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
                Toast.makeText(getApplicationContext(), "ATENCION: PAGO AUTORIZADO", Toast.LENGTH_SHORT).show();

                ApplicationGlobal applicationGlobal = ApplicationGlobal.getInstance();

                api.actualizarCompra(applicationGlobal.getCompra().getId(),5,0).enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if(response.isSuccessful()){
                            applicationGlobal.getCompra().setIdEstado(5);
                            api.marcarMensajeVisto(ultimoMensaje.getId()).enqueue(new Callback<Boolean>() {
                                @Override
                                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                    if(response.isSuccessful()){

                                    }else{
                                        Alerts.newToastLarge(getApplicationContext(), "Err");
                                    }

                                }

                                @Override
                                public void onFailure(Call<Boolean> call, Throwable t) {
                                    Alerts.newToastLarge(getApplicationContext(), "Err");

                                }
                            });

                        }else{
                            Alerts.newToastLarge(getApplicationContext(), "Err");
                        }

                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Alerts.newToastLarge(getApplicationContext(), "Err");

                    }
                });

                Intent intent = new Intent (view.getContext(), BotoneraInicialAyudante.class);
                startActivityForResult(intent, 0);
            }
        });

        Button btnPCD = (Button) findViewById(R.id.button);
        btnPCD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), BotoneraInicialAyudante.class);
                startActivityForResult(intent, 0);
            }
        });

        autorizarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "ATENCION: PAGO AUTORIZADO", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent (v.getContext(), BotoneraInicialAyudante.class);
                startActivityForResult(intent, 0);
            }
        });

    }

    private void cargarUltimoMensaje(MensajeViewModelResponse mensaje){
        descripcion.setText(mensaje.getDescripcion());
        fechaAlta.setText(mensaje.getFechaAlta());

        idTipoEvento = mensaje.getOrdenImportancia();
        if(idTipoEvento.equals(3)){
            autorizarButton.setVisibility(View.VISIBLE);
        }else {
            imageInfo.setVisibility(View.VISIBLE);
        }
    }

}
