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

import com.example.bouchef.tubolsillo.R;
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

public class FragmentBotoneraInicioPCD extends Fragment {
    private Context mContext = getContext();
    FragmentActivity activity = (FragmentActivity) mContext;

    ApplicationGlobal applicationGlobal = ApplicationGlobal.getInstance();

    /* botonera inicio PCD */
    private APIService api;

    @BindView(R.id.descripcion)
    TextView descripcion;
    @BindView(R.id.fechaAlta)
    TextView fechaAlta;
   // @BindView(R.id.accion)
   // ImageView btn_accion;

    //@BindView(R.id.tit_barra) TextView titulo;
    /* fin botonera inicio PCD */
    boolean fragmentTransaction = false;
    Fragment fragment = null;

    public FragmentBotoneraInicioPCD() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.activity_botonera_inicial_pcd, container, false);
        ButterKnife.bind(this, view);



        /* BOTONERA INICIO PCD */
            /*titulo =  view.findViewById(R.id.tit_barra);
            titulo.setText(R.string.tit_inicio_pcd);
*/
            ApplicationGlobal applicationGlobal = ApplicationGlobal.getInstance();



            api = Api.getAPIService(mContext);

            UsuarioViewModelPOST usuarioViewModelPOST = new UsuarioViewModelPOST();
            usuarioViewModelPOST.setId(2);
            usuarioViewModelPOST.setNombre("");

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

                                    api.getUltimoMensaje(mensajeViewModelPOST.getIdUsuario(), mensajeViewModelPOST.getIdCompra(), mensajeViewModelPOST.getIdTipoEvento()).enqueue(new Callback<MensajeViewModelResponse>() {
                                        @Override
                                        public void onResponse(Call<MensajeViewModelResponse> call, Response<MensajeViewModelResponse> response) {
                                            if (response.isSuccessful()) {
                                                if (response.body() != null) {
                                                    cargarUltimoMensaje(response.body());
                                                }
                                            } else {
                                                if (response.code() != 404) {
                                                    Alerts.newToastLarge(view.getContext(), "ERR");
                                                } else {
                                                    cargarUltimoMensaje(null);
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<MensajeViewModelResponse> call, Throwable t) {
                                            Alerts.newToastLarge(view.getContext(), "ErrErr");
                                        }
                                    });

                                }else{
                                    if (response.code() != 404) {
                                        Alerts.newToastLarge(view.getContext(), "ERR");
                                    }
                                    else
                                    {
                                        //applicationGlobal.setCompra(null);
                                    }
                                }

                            }

                            @Override
                            public void onFailure(Call<CompraViewModelResponse> call, Throwable t) {
                                Alerts.newToastLarge(view.getContext(), "Err");

                            }
                        });
                    }else{
                        Alerts.newToastLarge(view.getContext(), "ERR");
                    }
                }

                @Override
                public void onFailure(Call<UsuarioViewModelResponse> call, Throwable t) {
                    Alerts.newToastLarge(view.getContext(), "ErrErr");
                }
            });

            Button btn1 = (Button) view.findViewById(R.id.credencial);
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragment = new FragmentCredencialPCD();
                    ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, fragment).addToBackStack(null).commit();
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
        /*Button btn4 = (Button) findViewById(R.id.button4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), ListaCompraPCD.class);
                startActivityForResult(intent, 0);
            }
        });*/

            Button btn5 = (Button) view.findViewById(R.id.control_gastos);
            btn5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragment = new FragmentControlGastos();
                    ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, fragment).addToBackStack(null).commit();
                }
            });

            Button btn7 = (Button) view.findViewById(R.id.comenzar_compra);
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
                                    Alerts.newToastLarge(view.getContext(), "ERR");
                                }
                                else
                                {
                                    //applicationGlobal.setCompra(null);
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<CompraViewModelResponse> call, Throwable t) {
                            Alerts.newToastLarge(view.getContext(), "Err");

                        }
                    });

                    if (global.getCompra() == null) {
                        //intent = new Intent(v.getContext(), ListaNegociosFavoritos.class);
                        fragment = new FragmentComenzarCompra();

                    }
                    else if (global.getCompra().getEstado().getId() != 4 && global.getCompra().getEstado().getId() != 3) {
                        //intent = new Intent(v.getContext(),NotificadorPCD.class);
                        fragment = new FragmentNotificador();

                    }
                    else
                    {
                        //intent = new Intent(v.getContext(),PagarPCD.class);
                        fragment = new FragmentPagarCompra();
                    }

                    //startActivityForResult(intent, 0);
                    ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, fragment).addToBackStack(null).commit();
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



            Button btn10 = (Button) view.findViewById(R.id.notificador);
            btn10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Intent intent = new Intent (v.getContext(), NotificadorPCD.class);
                    //startActivityForResult(intent, 0);
                    fragment = new FragmentNotificador();
                    ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, fragment).addToBackStack(null).commit();
                }
            });
        /* FIN BOTONERA PCD */

        return view;
    }

    /* BOTONERA INICIO PCD */
    private void cargarUsuarioGlobal(ApplicationGlobal global, UsuarioViewModelResponse usuario) {
        global.setUsuario(usuario);
    }

    private void cargarUltimoMensaje(MensajeViewModelResponse mensaje){
        if(mensaje != null) {
            descripcion.setText(mensaje.getDescripcion());
            fechaAlta.setText(mensaje.getFechaAlta());
        }
    }
    /* FIN BOTONERA INICIO PCD */
}
