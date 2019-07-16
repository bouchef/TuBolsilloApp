package com.example.bouchef.tubolsillo;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.bouchef.tubolsillo.api.model.CompraViewModelPOST;
import com.example.bouchef.tubolsillo.api.model.IdResponse;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelPOST;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelResponse;
import com.example.bouchef.tubolsillo.api.model.UsuarioViewModelResponse;
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

import static com.example.bouchef.tubolsillo.R.layout.activity_mensajes;

public class NotificadorPCD extends AppCompatActivity {
    private Context mContext= NotificadorPCD.this;

    private APIService api;

    @BindView(R.id.descripcion)
    TextView descripcion;
    @BindView(R.id.fechaAlta) TextView fechaAlta;

    @BindView(R.id.autorizarButton)
    ImageButton autorizarButton;

    @BindView(R.id.info)
    ImageView imageInfo;
    private Integer idTipoEvento;

    //private RecyclerView recyclerView;
    private DashboardAdapter adapter;
    private ArrayList<dashboard> dashboardList;
    private ArrayList<String> cars = new ArrayList<String>();
    private dashboard das;
    private String lenguajeProgramacion[]=new String[]{"Necesito Ayuda","Salgo a Comprar","Esperando el trasporte","Llegando al Comercio","Ya estoy volviendo","Cancelando Compra"};
    private Integer[] imgid={
            R.drawable.conversation,
            R.drawable.conversation,
            R.drawable.conversation,
            R.drawable.conversation,
            R.drawable.conversation,
            R.drawable.conversation
    };

    private ListView lista;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_mensajes);

        ButterKnife.bind(this);

        api = Api.getAPIService(getApplicationContext());
        ApplicationGlobal applicationGlobal = ApplicationGlobal.getInstance();

        MensajeViewModelPOST mensajeViewModelPOST = new MensajeViewModelPOST();
        mensajeViewModelPOST.setIdUsuario(2);
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

                MensajeViewModelPOST  m = new MensajeViewModelPOST();
                if (applicationGlobal.getCompra() != null) {
                    m.setIdCompra(applicationGlobal.getCompra().getId());
                }
                m.setIdTipoEvento(4);
                m.setDescripcion(Slecteditem);
                Integer idUsuarioMensaje = obtenerUsuarioMensaje(applicationGlobal);
                m.setIdUsuario(idUsuarioMensaje);
                m.setOrdenImportancia(2);
                api.nuevoMensaje(m).enqueue(new Callback<IdResponse>() {
                    @Override
                    public void onResponse(Call<IdResponse> call, Response<IdResponse> response) {
                        if(response.isSuccessful()){
                            procesarMensaje(Slecteditem);
                        }else{
                            Alerts.newToastLarge(getApplicationContext(), "Err");
                        }

                    }

                    @Override
                    public void onFailure(Call<IdResponse> call, Throwable t) {
                        Alerts.newToastLarge(getApplicationContext(), "Err");

                    }
                });



            }
        });

        autorizarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), AutorizarTutor.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    private void procesarMensaje(String Slecteditem){
        ApplicationGlobal applicationGlobal = ApplicationGlobal.getInstance();

        if(Slecteditem=="Necesito Ayuda"){
            //enviar mensaje("Necesito Ayuda")
            Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();
        }
        if(Slecteditem=="Salgo a Comprar"){
            //enviar mensaje("Salgo a Comprar")
            Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();
        }
        if(Slecteditem=="Esperando el trasporte"){
            //enviar mensaje("Esperando el trasporte")
            Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();
        }
        if(Slecteditem=="Llegando al Comercio"){
            //enviar mensaje("Llegando al Comercio")
            Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();

            api.actualizarCompra(applicationGlobal.getCompra().getId(),3,0).enqueue(new Callback<Boolean>() {
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

            Intent intent = new Intent (getApplicationContext(), PagarPCD.class);
            startActivityForResult(intent, 0);
        }
        if(Slecteditem=="Ya estoy volviendo"){
            //enviar mensaje("Ya estoy volviendo")
            Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent (getApplicationContext(), BotoneraInicialPCD.class);
            startActivityForResult(intent, 0);
        }
        if(Slecteditem=="Cancelando Compra"){
            //enviar mensaje("Cancelando Compra")
            Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();

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

            Intent intent = new Intent (getApplicationContext(), BotoneraInicialPCD.class);
            startActivityForResult(intent, 0);
        }
    }

    private void cargarUltimoMensaje(MensajeViewModelResponse mensaje){

        String t = FechaUtils.fromStringToVerbose(mensaje.getFechaAlta());

        descripcion.setText(mensaje.getDescripcion());
        //fechaAlta.setText(mensaje.getFechaAlta());
        fechaAlta.setText(t);

        idTipoEvento = mensaje.getOrdenImportancia();
        if(idTipoEvento.equals(3)){
            autorizarButton.setVisibility(View.VISIBLE);
        }else {
            imageInfo.setVisibility(View.VISIBLE);
        }
    }

    private Integer obtenerUsuarioMensaje(ApplicationGlobal global)
    {
        UsuarioViewModelResponse usuario = global.getUsuario();
        UsuarioViewModelResponse usuarioTemp = new UsuarioViewModelResponse();
        api = Api.getAPIService(getApplicationContext());

        if (usuario.getTipoUsuario().getDescripcion().equals("Usuario"))
        {
            return usuario.getUsuarioPadre().getId();
        }
        else
        {
            //Obtener usuario a cargo del ayudante para enviarle el mensaje
            //NO FUNCIONA, POR ESO HARDCODEO POR EL MOMENTO
            //api.getUsuarioPCD(usuario.getId()).enqueue(new Callback<UsuarioViewModelResponse>() {
             //   @Override
            //    public void onResponse(Call<UsuarioViewModelResponse> call, Response<UsuarioViewModelResponse> response) {
            //        if(response.isSuccessful()){
            //            usuarioTemp.setId(response.body().getId());
            //        }else{
            //            Alerts.newToastLarge(getApplicationContext(), "Err");
            //        }

            //    }

            //    @Override
            //    public void onFailure(Call<UsuarioViewModelResponse> call, Throwable t) {
            //        Alerts.newToastLarge(getApplicationContext(), "Err");

            //    }
            //});

            return 2;
            //return usuarioTemp.getId();
        }
    }

}
