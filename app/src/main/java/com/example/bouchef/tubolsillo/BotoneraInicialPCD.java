package com.example.bouchef.tubolsillo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bouchef.tubolsillo.api.APIService;
import com.example.bouchef.tubolsillo.api.Api;
import com.example.bouchef.tubolsillo.api.model.CompraViewModelResponse;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelPOST;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelResponse;
import com.example.bouchef.tubolsillo.api.model.UsuarioViewModelPOST;
import com.example.bouchef.tubolsillo.api.model.UsuarioViewModelResponse;
import com.example.bouchef.tubolsillo.generics.ApplicationGlobal;
import com.example.bouchef.tubolsillo.generics.GlobalClass;
import com.example.bouchef.tubolsillo.generics.Utils;
import com.example.bouchef.tubolsillo.tests.ListaUnoActivity;
import com.example.bouchef.tubolsillo.utiles.Alerts;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by World of UI/UX on 01/04/2019.
 */

public class BotoneraInicialPCD extends AppCompatActivity {
    private Context mContext= BotoneraInicialPCD.this;
    private APIService api;

    @BindView(R.id.descripcion)
    TextView descripcion;
    @BindView(R.id.fechaAlta)
    TextView fechaAlta;
    @BindView(R.id.accion)
    ImageView btn_accion;

    @BindView(R.id.tit_barra) TextView titulo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_botonera_inicial_pcd);

        titulo =  findViewById(R.id.tit_barra);
        titulo.setText(R.string.tit_inicio_pcd);

        ButterKnife.bind(this);
        ApplicationGlobal applicationGlobal = ApplicationGlobal.getInstance();



        api = Api.getAPIService(getApplicationContext());

        UsuarioViewModelPOST usuarioViewModelPOST = new UsuarioViewModelPOST();
        usuarioViewModelPOST.setId(2);
        usuarioViewModelPOST.setNombre("");

        //try {
        //    Response<UsuarioViewModelResponse> usuarioResult = api.getUsuario(usuarioViewModelPOST).execute();
        //    cargarUsuarioGlobal(applicationGlobal, usuarioResult.body());
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}

        //NO FUNCIONA
        api.getUsuario(usuarioViewModelPOST).enqueue(new Callback<UsuarioViewModelResponse>() {
            @Override
            public void onResponse(Call<UsuarioViewModelResponse> call, Response<UsuarioViewModelResponse> response) {
                if(response.isSuccessful()){
                    cargarUsuarioGlobal(applicationGlobal, response.body());

                    //PARCHE
                    api.getCompraVigente(applicationGlobal.getUsuario().getId()).enqueue(new Callback<CompraViewModelResponse>() {
                        //api.getCompraVigente(2).enqueue(new Callback<CompraViewModelResponse>() {
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

        Button btn1 = (Button) findViewById(R.id.button1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), CredencialPCD.class);
                startActivityForResult(intent, 0);
            }
        });

    /*Button btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent (v.getContext(), LocationActivity.class);
            startActivityForResult(intent, 0);
        }
    });*/
        /*Button btn3 = (Button) findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), InicioPCD.class);
                startActivityForResult(intent, 0);
            }
        });*/
        /*Button btn4 = (Button) findViewById(R.id.button4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), ListaCompraPCD.class);
                startActivityForResult(intent, 0);
            }
        });*/

        Button btn5 = (Button) findViewById(R.id.button5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), ListaControlGastos.class);
                startActivityForResult(intent, 0);
            }
        });

        Button btn7 = (Button) findViewById(R.id.button7);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationGlobal global = ApplicationGlobal.getInstance();
                Intent intent;

                api.getCompraVigente(2).enqueue(new Callback<CompraViewModelResponse>() {
                    @Override
                    public void onResponse(Call<CompraViewModelResponse> call, Response<CompraViewModelResponse> response) {
                        if(response.isSuccessful()){
                            applicationGlobal.setCompra(response.body());
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

                if (global.getCompra() == null) {
                    intent = new Intent(v.getContext(), ListaNegociosFavoritos.class);

                }
                else if (global.getCompra().getEstado().getId() != 4 && global.getCompra().getEstado().getId() != 3) {
                    intent = new Intent(v.getContext(),NotificadorPCD.class);
                }
                else
                {
                    intent = new Intent(v.getContext(),PagarPCD.class);
                }

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

        Button btn9 = (Button) findViewById(R.id.button9);
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), BotoneraInicialAyudante.class);
                startActivityForResult(intent, 0);
                finish();
            }
        });

        Button btn10 = (Button) findViewById(R.id.button10);
        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), NotificadorPCD.class);
                startActivityForResult(intent, 0);
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
                        Alerts.newToastLarge(getApplicationContext(), "Marcar Mensaje como leido");
                    }
                }

            }
        });
        // FIN ACCION DEL BOTON MENSAJE
    }


    private void cargarUsuarioGlobal(ApplicationGlobal global, UsuarioViewModelResponse usuario) {
        global.setUsuario(usuario);
    }

    private void cargarUltimoMensaje(MensajeViewModelResponse mensaje){
        descripcion.setText(mensaje.getDescripcion());
        fechaAlta.setText(mensaje.getFechaAlta());

    }
}