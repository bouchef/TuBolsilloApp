package com.example.bouchef.tubolsillo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.bouchef.tubolsillo.api.APIService;
import com.example.bouchef.tubolsillo.api.Api;
import com.example.bouchef.tubolsillo.api.model.CompraViewModelResponse;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelPOST;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelResponse;
import com.example.bouchef.tubolsillo.api.model.UsuarioViewModelPOST;
import com.example.bouchef.tubolsillo.api.model.UsuarioViewModelResponse;
import com.example.bouchef.tubolsillo.generics.ApplicationGlobal;
import com.example.bouchef.tubolsillo.utiles.Alerts;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentBotoneraInicioAyudante extends Fragment {

    private Context mContext = getContext();
    FragmentActivity activity = (FragmentActivity) mContext;

    private APIService api;

    @BindView(R.id.descripcion)
    TextView descripcion;
    @BindView(R.id.fechaAlta)
    TextView fechaAlta;

    @BindView(R.id.accion)
    ImageView btn_accion;

    //@BindView(R.id.irHome) ImageView btn_home;

    //@BindView(R.id.tit_barra) TextView titulo;

    boolean fragmentTransaction = false;
    Fragment fragment = null;

    public FragmentBotoneraInicioAyudante() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_botonera_inicial_ayudante, container, false);
        ButterKnife.bind(this, view);

        /* BOTONERA INICIO PCD */

        ApplicationGlobal applicationGlobal = ApplicationGlobal.getInstance();
        api = Api.getAPIService(mContext);

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

                                api.getUltimoMensaje(mensajeViewModelPOST.getIdUsuario(), mensajeViewModelPOST.getIdCompra(), mensajeViewModelPOST.getIdTipoEvento()).enqueue(new Callback<MensajeViewModelResponse>() {
                                    @Override
                                    public void onResponse(Call<MensajeViewModelResponse> call, Response<MensajeViewModelResponse> response) {
                                        if (response.isSuccessful()) {
                                            cargarUltimoMensaje(response.body());
                                        } else {
                                            if (response.code() != 404) {
                                                Alerts.newToastLarge(getContext(), "ERR");
                                            } else {
                                                cargarUltimoMensaje(null);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<MensajeViewModelResponse> call, Throwable t) {
                                        Alerts.newToastLarge(getContext(), "ErrErr");
                                    }
                                });
                            }else{
                                if (response.code() != 404) {
                                    Alerts.newToastLarge(getContext(), "ERR");
                                }
                                else
                                {
                                    //applicationGlobal.setCompra(null);
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<CompraViewModelResponse> call, Throwable t) {
                            Alerts.newToastLarge(getContext(), "Err");

                        }
                    });
                }else{
                    Alerts.newToastLarge(getContext(), "ERR");
                }
            }

            @Override
            public void onFailure(Call<UsuarioViewModelResponse> call, Throwable t) {
                Alerts.newToastLarge(mContext, "ErrErr");
            }
        });

        Button btn1 = (Button) view.findViewById(R.id.button_autorizar);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new FragmentAutorizarCompra();
                ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, fragment).commit();
            }
        });

        Button btn2 = (Button) view.findViewById(R.id.button_seguir_compra);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new FragmentSeguimientoCompra();
                ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, fragment).commit();
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
        Button btn6 = (Button) view.findViewById(R.id.button_bandeja_mensajes);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new FragmentHistorialMensajes();
                ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, fragment).commit();
            }
        });

        Button btn5 = (Button) view.findViewById(R.id.control_gastos);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new FragmentControlGastos();
                ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, fragment).commit();
            }
        });

        Button btn7 = (Button) view.findViewById(R.id.notificador);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new FragmentNotificador();
                ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, fragment).commit();
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


        // ACCION DEL BOTON DE MENSAJE
        btn_accion =  view.findViewById(R.id.accion);
        String imageId = (String) btn_accion.getTag();

        btn_accion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(applicationGlobal.getUsuario().getIdTipoUsuario().equals(1)) {
                    if(imageId.equals("Autorizacion")) {
                        //Intent intent = new Intent(v.getContext(), AutorizarTutor.class);
                        //startActivityForResult(intent, 0);
                        fragment = new FragmentAutorizarCompra();
                        ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content_frame, fragment).commit();
                    }
                    if(imageId.equals("Informacion")) {
                        // Marcar mensaje como leido y actualizar
                        Alerts.newToastLarge(getContext(), "Marcar Mensaje como leido");
                    }
                }

            }
        });

        /* FIN BOTONERA INICIO AYUDANTE */

        return view;
    }

    private void cargarUsuarioGlobal(ApplicationGlobal global, UsuarioViewModelResponse usuario) {
        global.setUsuario(usuario);
    }

    private void cargarUltimoMensaje(MensajeViewModelResponse mensaje){
        descripcion.setText(mensaje.getDescripcion());
        fechaAlta.setText(mensaje.getFechaAlta());

    }
}
