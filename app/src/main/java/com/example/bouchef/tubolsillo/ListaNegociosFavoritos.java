package com.example.bouchef.tubolsillo;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bouchef.tubolsillo.adapter.DashboardAdapter;
import com.example.bouchef.tubolsillo.adapter.LenguajeListAdapter;
import com.example.bouchef.tubolsillo.api.APIService;
import com.example.bouchef.tubolsillo.api.Api;
import com.example.bouchef.tubolsillo.api.model.ComercioViewModelPOST;
import com.example.bouchef.tubolsillo.api.model.ComercioViewModelResponse;
import com.example.bouchef.tubolsillo.api.model.IdResponse;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelPOST;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelResponse;
import com.example.bouchef.tubolsillo.api.model.CompraViewModelPOST;
import com.example.bouchef.tubolsillo.api.model.CompraViewModelResponse;
import com.example.bouchef.tubolsillo.generics.ApplicationGlobal;
import com.example.bouchef.tubolsillo.model.dashboard;
import com.example.bouchef.tubolsillo.utiles.Alerts;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bouchef.tubolsillo.R.layout.activity_negocios_favoritos;

public class ListaNegociosFavoritos extends AppCompatActivity {
    private Context mContext= ListaNegociosFavoritos.this;


    private APIService api;

    @BindView(R.id.accion) ImageView btn_accion;
    @BindView(R.id.irHome) ImageView btn_home;
    @BindView(R.id.tit_barra) TextView titulo;

    //private RecyclerView recyclerView;
    private DashboardAdapter adapter;
    private ArrayList<dashboard> dashboardList;
    private ArrayList<String> cars = new ArrayList<String>();
    private dashboard das;
    private String lenguajeProgramacion[]=new String[]{"FAVORITO 1","FAVORITO 2","FAVORITO 3","FAVORITO 4","FAVORITO 5"};
    private Integer[] imgid={
            R.drawable.restaurant,
            R.drawable.restaurant,
            R.drawable.restaurant,
            R.drawable.restaurant,
            R.drawable.restaurant
    };
    //private Integer image[] = {R.drawable.ic_favorite_black_24dp, R.drawable.ic_favorite_border_black_24dp, R.drawable.ic_favorite_black_24dp, R.drawable.ic_favorite_border_black_24dp,};


    private ListView lista;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_negocios_favoritos);

        titulo =  findViewById(R.id.tit_barra);
        titulo.setText(R.string.tit_control_gastos);

        ButterKnife.bind(this);

        api = Api.getAPIService(getApplicationContext());
        ApplicationGlobal applicationGlobal = ApplicationGlobal.getInstance();

        MensajeViewModelPOST mensajeViewModelPOST = new MensajeViewModelPOST();
        mensajeViewModelPOST.setIdUsuario(applicationGlobal.getUsuario().getId());
        mensajeViewModelPOST.setIdCompra(0);
        mensajeViewModelPOST.setIdTipoEvento(4);

        api.getUltimoMensaje(mensajeViewModelPOST.getIdUsuario(), mensajeViewModelPOST.getIdCompra(),mensajeViewModelPOST.getIdTipoEvento()).enqueue(new Callback<MensajeViewModelResponse>() {
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


        //LenguajeListAdapterFavoritos adapter=new LenguajeListAdapterFavoritos(this,lenguajeProgramacion,imgid,image);
        LenguajeListAdapter adapter=new LenguajeListAdapter(this,lenguajeProgramacion,imgid);
        lista=(ListView)findViewById(R.id.mi_lista);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Slecteditem= lenguajeProgramacion[+position];
                //enviar mensaje ("inicio de compra")
                //enviar mensaje ("Me difirijo al comercio XXXXXX")
                //Me guardo el Comercio para las etapas siguientes
                ComercioViewModelPOST comercioViewModelPOST = new ComercioViewModelPOST();
                comercioViewModelPOST.setId(position + 1);
                comercioViewModelPOST.setIdUsuario(0);
                comercioViewModelPOST.setNombre("");
                api.getComercios(comercioViewModelPOST).enqueue(new Callback<List<ComercioViewModelResponse>>() {
                    @Override
                    public void onResponse(Call<List<ComercioViewModelResponse>> call, Response<List<ComercioViewModelResponse>> response) {
                        if(response.isSuccessful()){
                            cargarComercioGlobal(response.body().get(0),applicationGlobal);
                        }else{
                            Alerts.newToastLarge(mContext, "ERR");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ComercioViewModelResponse>> call, Throwable t) {
                        Alerts.newToastLarge(mContext, "ErrErr");
                    }
                });

                CompraViewModelPOST compraViewModelPOST = new CompraViewModelPOST();
                compraViewModelPOST.setIdUsuario(applicationGlobal.getUsuario().getId());
                compraViewModelPOST.setIdComercio(position + 1);
                compraViewModelPOST.setCompraReal(false);

                api.nuevaCompra(compraViewModelPOST).enqueue(new Callback<IdResponse>() {
                    @Override
                    public void onResponse(Call<IdResponse> call, Response<IdResponse> response) {
                        if(response.isSuccessful()){
                            cargarCompra((response.body().getId()),applicationGlobal);
                        }else{
                            Alerts.newToastLarge(getApplicationContext(), "Err");
                        }

                    }

                    @Override
                    public void onFailure(Call<IdResponse> call, Throwable t) {
                        Alerts.newToastLarge(getApplicationContext(), "Err");

                    }
                });

                Toast.makeText(getApplicationContext(), "ME DIRIJO A " + Slecteditem, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent (view.getContext(), NotificadorPCD.class);
                startActivityForResult(intent, 0);
              finish();
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


/*    private void cargarUltimoMensaje(MensajeViewModelResponse mensaje){
        descripcion.setText(mensaje.getDescripcion());
        fechaAlta.setText(mensaje.getFechaAlta());
    }*/

    private void cargarComercioGlobal(ComercioViewModelResponse comercio, ApplicationGlobal applicationGlobal) {
        applicationGlobal.setComercio(comercio);
    }
    private void cargarCompraGlobal(CompraViewModelResponse compra, ApplicationGlobal applicationGlobal) {
        applicationGlobal.setCompra(compra);
    }

    private void cargarCompra(Integer idCompra, ApplicationGlobal global) {
        //Consultar nueva compra
        CompraViewModelPOST nuevaCompra = new CompraViewModelPOST();
        nuevaCompra.setId(idCompra);
        nuevaCompra.setIdUsuario(global.getUsuario().getId());

        //Cargarla en Global
        api.getCompras(nuevaCompra).enqueue(new Callback<List<CompraViewModelResponse>>() {
            @Override
            public void onResponse(Call<List<CompraViewModelResponse>> call, Response<List<CompraViewModelResponse>> response) {
                if(response.isSuccessful()){
                    cargarCompraGlobal(response.body().get(0),global);
                }else{
                    Alerts.newToastLarge(mContext, "ERR");
                }
            }

            @Override
            public void onFailure(Call<List<CompraViewModelResponse>> call, Throwable t) {
                Alerts.newToastLarge(mContext, "ErrErr");
            }
        });
    }

}
