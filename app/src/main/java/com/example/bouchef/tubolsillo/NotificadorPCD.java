package com.example.bouchef.tubolsillo;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import com.example.bouchef.tubolsillo.api.model.IdResponse;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelPOST;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelResponse;
import com.example.bouchef.tubolsillo.api.model.UsuarioViewModelResponse;
import com.example.bouchef.tubolsillo.generics.ApplicationGlobal;
import com.example.bouchef.tubolsillo.generics.GlobalClass;
import com.example.bouchef.tubolsillo.model.dashboard;
import com.example.bouchef.tubolsillo.utiles.Alerts;
import com.example.bouchef.tubolsillo.utiles.FechaUtils;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bouchef.tubolsillo.R.layout.activity_mensajes;
import static java.security.AccessController.getContext;

public class NotificadorPCD extends AppCompatActivity {

    private Context mContext= NotificadorPCD.this;

    private APIService api;

    @BindView(R.id.accion)
    ImageView btn_accion;
    @BindView(R.id.irHome) ImageView btn_home;

    @BindView(R.id.tit_barra) TextView titulo;


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

        titulo =  findViewById(R.id.tit_barra);
        titulo.setText(R.string.tit_notificador);


        api = Api.getAPIService(getApplicationContext());
        ApplicationGlobal applicationGlobal = ApplicationGlobal.getInstance();

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


        LenguajeListAdapter adapter=new LenguajeListAdapter(this,lenguajeProgramacion,imgid);
        lista=(ListView)findViewById(R.id.mi_lista);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Slecteditem= lenguajeProgramacion[+position];
                obtenerUsuarioMensaje(applicationGlobal,Slecteditem);
            }
        });

        //btn_accion =  findViewById(R.id.accion);

        //final String name  = globalVariable.getBtn_accion_tag();
        GlobalClass g = GlobalClass.getInstance();
        String imageId =  g.getBtn_accion_tag();

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
                    if(imageId.equals("")) {
                        // Marcar mensaje como leido y actualizar
                        Alerts.newToastLarge(mContext, "no cargo");
                    }
                }

            }
        });

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

            api.actualizarCompra(applicationGlobal.getCompra().getId(),3,0, "A").enqueue(new Callback<Boolean>() {
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
          finish();
        }
        if(Slecteditem=="Ya estoy volviendo"){
            //enviar mensaje("Ya estoy volviendo")
            Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent (getApplicationContext(), BotoneraInicialPCD.class);
            startActivityForResult(intent, 0);
          finish();
        }
        if(Slecteditem=="Cancelando Compra"){
            //enviar mensaje("Cancelando Compra")
            Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();

            api.actualizarCompra(applicationGlobal.getCompra().getId(),8,0, "A").enqueue(new Callback<Boolean>() {
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

            api.actualizarCompra(applicationGlobal.getCompra().getId(),9,0, "A").enqueue(new Callback<Boolean>() {
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
          finish();
        }
    }


    private void obtenerUsuarioMensaje(ApplicationGlobal global,String Slecteditem)
    {
        api = Api.getAPIService(getApplicationContext());

        MensajeViewModelPOST  m = new MensajeViewModelPOST();
        if (global.getCompra() != null) {
            m.setIdCompra(global.getCompra().getId());
        }
        m.setIdTipoEvento(4);
        m.setDescripcion(Slecteditem);
        m.setOrdenImportancia(2);

        UsuarioViewModelResponse usuario = global.getUsuario();

        if (usuario.getTipoUsuario().getDescripcion().equals("Usuario"))
        {
            m.setIdUsuario(usuario.getUsuarioPadre().getId());
            enviarNuevoMensaje(m,Slecteditem);
        }
        else
        {
            //Obtener usuario a cargo del ayudante para enviarle el mensaje
            api.getUsuarioPCD(usuario.getId()).enqueue(new Callback<UsuarioViewModelResponse>() {
                @Override
                public void onResponse(Call<UsuarioViewModelResponse> call, Response<UsuarioViewModelResponse> response) {
                    if(response.isSuccessful()){
                        usuario.setId(response.body().getId());
                        m.setIdUsuario(usuario.getId());
                        enviarNuevoMensaje(m,Slecteditem);
                    }else{
                        Alerts.newToastLarge(getApplicationContext(), "Err");
                    }

                }

                @Override
                public void onFailure(Call<UsuarioViewModelResponse> call, Throwable t) {
                    Alerts.newToastLarge(getApplicationContext(), "Err");

                }
            });

        }
    }

    private void enviarNuevoMensaje(MensajeViewModelPOST m, String Slecteditem) {

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

}
