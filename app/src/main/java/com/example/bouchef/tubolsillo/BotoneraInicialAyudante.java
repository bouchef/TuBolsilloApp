package com.example.bouchef.tubolsillo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bouchef.tubolsillo.api.APIService;
import com.example.bouchef.tubolsillo.api.Api;
import com.example.bouchef.tubolsillo.api.model.CompraViewModelResponse;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelPOST;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelResponse;
import com.example.bouchef.tubolsillo.api.model.UsuarioViewModelPOST;
import com.example.bouchef.tubolsillo.api.model.UsuarioViewModelResponse;
import com.example.bouchef.tubolsillo.generics.ApplicationGlobal;
import com.example.bouchef.tubolsillo.utiles.Alerts;
import com.example.bouchef.tubolsillo.utiles.FechaUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by World of UI/UX on 01/04/2019.
 */

public class BotoneraInicialAyudante extends AppCompatActivity {
    private Context mContext= BotoneraInicialAyudante.this;
    private APIService api;

    @BindView(R.id.descripcion)
    TextView descripcion;
    @BindView(R.id.fechaAlta)
    TextView fechaAlta;

    @BindView(R.id.accion) ImageView btn_accion;

    //@BindView(R.id.irHome) ImageView btn_home;

    @BindView(R.id.tit_barra) TextView titulo;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_botonera_inicial_ayudante);

        titulo =  findViewById(R.id.tit_barra);
        titulo.setText(R.string.tit_inicio_ayudante);

        ButterKnife.bind(this);
        ApplicationGlobal applicationGlobal = ApplicationGlobal.getInstance();

        api = Api.getAPIService(getApplicationContext());

        UsuarioViewModelPOST usuarioViewModelPOST = new UsuarioViewModelPOST();
        usuarioViewModelPOST.setId(1);
        usuarioViewModelPOST.setNombre("");
        api.getUsuario(usuarioViewModelPOST).enqueue(new Callback<UsuarioViewModelResponse>() {
            @Override
            public void onResponse(Call<UsuarioViewModelResponse> call, Response<UsuarioViewModelResponse> response) {
                if(response.isSuccessful()){
                    cargarUsuarioGlobal(applicationGlobal, response.body());


                    api.getCompraVigente(applicationGlobal.getUsuario().getId()).enqueue(new Callback<CompraViewModelResponse>() {
                        @Override
                        public void onResponse(Call<CompraViewModelResponse> call, Response<CompraViewModelResponse> response) {
                            if(response.isSuccessful()){
                                applicationGlobal.setCompra(response.body());


                                MensajeViewModelPOST mensajeViewModelPOST = new MensajeViewModelPOST();
                                mensajeViewModelPOST.setIdUsuario(applicationGlobal.getUsuario().getId());
                                mensajeViewModelPOST.setIdCompra(0);
                                mensajeViewModelPOST.setIdTipoEvento(0);

                                api.getUltimoMensaje(mensajeViewModelPOST.getIdCompra(), mensajeViewModelPOST.getIdUsuario(), mensajeViewModelPOST.getIdTipoEvento()).enqueue(new Callback<MensajeViewModelResponse>() {
                                    @Override
                                    public void onResponse(Call<MensajeViewModelResponse> call, Response<MensajeViewModelResponse> response) {
                                        if (response.isSuccessful()) {
                                            cargarUltimoMensaje(response.body());
                                        } else {
                                            if (response.code() != 404) {
                                                Alerts.newToastLarge(mContext, "ERR");
                                            } else {
                                                cargarUltimoMensaje(null);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<MensajeViewModelResponse> call, Throwable t) {
                                        Alerts.newToastLarge(mContext, "ErrErr");
                                    }
                                });
                            }else{
                                if (response.code() != 404) {
                                    Alerts.newToastLarge(mContext, "ERR");
                                }
                                else
                                {
                                    //applicationGlobal.setCompra(null);
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<CompraViewModelResponse> call, Throwable t) {
                            Alerts.newToastLarge(getApplicationContext(), "Err");

                        }
                    });
                }else{
                    Alerts.newToastLarge(mContext, "ERR");
                }
            }

            @Override
            public void onFailure(Call<UsuarioViewModelResponse> call, Throwable t) {
                Alerts.newToastLarge(mContext, "ErrErr");
            }
        });

        Button btn1 = (Button) findViewById(R.id.button_autorizar);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), AutorizarTutor.class);

                startActivityForResult(intent, 0);
            }
        });

        Button btn2 = (Button) findViewById(R.id.button_seguir_compra);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), SeguimientoCompra.class);
                startActivityForResult(intent, 0);
            }
        });
        /*Button btn3 = (Button) findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), InicioPCD.class);
                startActivityForResult(intent, 0);
            }
        });*/
        Button btn6 = (Button) findViewById(R.id.button_bandeja_mensajes);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), HistorialTutor.class);
                startActivityForResult(intent, 0);
            }
        });

        Button btn5 = (Button) findViewById(R.id.button_control_de_gastos);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), ListaControlGastos.class);
                startActivityForResult(intent, 0);
            }
        });

        Button btn7 = (Button) findViewById(R.id.button_enviar_mensajes);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), NotificadorPCD.class);
                startActivityForResult(intent, 0);
            }
        });

        /*Button btn7 = (Button) findViewById(R.id.button7);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), LocationActivityAmigable.class);
                startActivityForResult(intent, 0);
            }
        });*/

        /*Button btn8 = (Button) findViewById(R.id.button8);
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), SplashScreen.class);
                startActivityForResult(intent, 0);
            }
        });*/

        Button btn9 = (Button) findViewById(R.id.button_perfil_usuario);
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), BotoneraInicialPCD.class);
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
        //FloatingActionButton my_fab = (FloatingActionButton) findViewById(R.id.my_fab);
        /*my_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Tocaste el FAB", Toast.LENGTH_SHORT).show();
            }
        });*/
/*        btn_home =  findViewById(R.id.irHome);

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(applicationGlobal.getUsuario().getIdTipoUsuario().equals(1)) {
                    Intent intent = new Intent(v.getContext(), BotoneraInicialAyudante.class);
                    startActivityForResult(intent, 0);
                    }else {
                    Intent intent = new Intent(v.getContext(), BotoneraInicialPCD.class);
                    startActivityForResult(intent, 0);
                    }

                }

        });*/
        // FIN ACCION DEL BOTON IR A HOME


    }
//    private void cargarUltimoMensaje(MensajeViewModelResponse mensaje){
//        String t = FechaUtils.fromStringToVerbose(mensaje.getFechaAlta());
//
//        descripcion.setText(mensaje.getDescripcion());
//        fechaAlta.setText(t);
//
//        idTipoEvento = mensaje.getOrdenImportancia();
//        if(idTipoEvento.equals(3)){
//            autorizarButton.setVisibility(View.VISIBLE);
//        }else {
//            imageInfo.setVisibility(View.VISIBLE);
//        }
//    }

    private void cargarUsuarioGlobal(ApplicationGlobal global, UsuarioViewModelResponse usuario) {
        global.setUsuario(usuario);
    }

    private void cargarUltimoMensaje(MensajeViewModelResponse mensaje){
        descripcion.setText(mensaje.getDescripcion());
        fechaAlta.setText(mensaje.getFechaAlta());

    }
}