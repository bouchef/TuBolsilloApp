package com.example.bouchef.tubolsillo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.bouchef.tubolsillo.adapter.DashboardAdapter;
import com.example.bouchef.tubolsillo.adapter.LenguajeListAdapter;
import com.example.bouchef.tubolsillo.api.APIService;
import com.example.bouchef.tubolsillo.api.Api;
import com.example.bouchef.tubolsillo.api.model.CompraViewModelResponse;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelPOST;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelResponse;
import com.example.bouchef.tubolsillo.generics.ApplicationGlobal;
import com.example.bouchef.tubolsillo.generics.GlobalClass;
import com.example.bouchef.tubolsillo.model.dashboard;
import com.example.bouchef.tubolsillo.utiles.Alerts;
import com.example.bouchef.tubolsillo.utiles.FechaUtils;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bouchef.tubolsillo.R.drawable.bocadillo;
import static com.example.bouchef.tubolsillo.R.drawable.information;


public class FragmentAutorizarCompra extends Fragment {

    private Context mContext= getContext();


    private APIService api;


    /*@BindView(R.id.autorizarButton)
    ImageButton autorizarButton;*/
    @BindView(R.id.irHome)
    ImageView btn_home;
    @BindView(R.id.accion) ImageView btn_accion;
    @BindView(R.id.tit_barra)
    TextView titulo;


    private DashboardAdapter adapter;
    private ArrayList<dashboard> dashboardList;
    private ArrayList<String> cars = new ArrayList<String>();
    private dashboard das;
    private String lenguajeProgramacion[]=new String[]{"Compra 1 $10"};
    private Integer[] imgid={
            R.drawable.bocadillo
    };

    private ListView lista;
    @BindView(R.id.empty_state_container) LinearLayout lista_vacia;

    private Integer idTipoEvento;

    private MensajeViewModelResponse ultimoMensaje;


    boolean fragmentTransaction = false;
    Fragment fragment = null;

    ApplicationGlobal applicationGlobal = ApplicationGlobal.getInstance();

    public FragmentAutorizarCompra() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.activity_autorizar_tutor, container, false);
        lista_vacia = (LinearLayout) vista.findViewById(R.id.empty_state_container);

        api = Api.getAPIService(getContext());



        MensajeViewModelPOST mensajeViewModelPOST = new MensajeViewModelPOST();
        mensajeViewModelPOST.setIdUsuario(2);
        mensajeViewModelPOST.setIdCompra(0);
        mensajeViewModelPOST.setIdTipoEvento(4);

        ApplicationGlobal applicationGlobal = ApplicationGlobal.getInstance();
// inicio compra vigente
        api.getCompraVigente(applicationGlobal.getUsuario().getId()).enqueue(new Callback<CompraViewModelResponse>() {
            @Override
            public void onResponse(Call<CompraViewModelResponse> call, Response<CompraViewModelResponse> response) {
                if(response.isSuccessful()){
                    applicationGlobal.setCompra(response.body());
                    // inicio
                    if(applicationGlobal.getCompra() != null) {
                        LenguajeListAdapter adapter = new LenguajeListAdapter((Activity) getContext(), lenguajeProgramacion, imgid);
                        lista = (ListView) vista.findViewById(R.id.mi_lista);
                        lista.setAdapter(adapter);
                        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String Slecteditem = lenguajeProgramacion[+position];
                                Toast.makeText(getContext(), "ATENCION: PAGO AUTORIZADO", Toast.LENGTH_SHORT).show();


                                if (applicationGlobal.getCompra() != null) {
                                    api.actualizarCompra(applicationGlobal.getCompra().getId(), 5, Double.parseDouble("10")).enqueue(new Callback<Boolean>() {
                                        @Override
                                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                            if (response.isSuccessful()) {
                                                applicationGlobal.getCompra().setIdEstado(5);
                                                if (applicationGlobal.getUsuario() != null) {
                                                    MensajeViewModelPOST mensajeViewModelPOST = new MensajeViewModelPOST();
                                                    mensajeViewModelPOST.setIdUsuario(applicationGlobal.getUsuario().getId());
                                                    mensajeViewModelPOST.setIdCompra(applicationGlobal.getCompra().getId());
                                                    mensajeViewModelPOST.setIdTipoEvento(4);    // tipoEvento = Pedir Autorizacion

                                                    api.getUltimoMensaje(mensajeViewModelPOST.getIdUsuario(), mensajeViewModelPOST.getIdCompra(), mensajeViewModelPOST.getIdTipoEvento()).enqueue(new Callback<MensajeViewModelResponse>() {
                                                        @Override
                                                        public void onResponse(Call<MensajeViewModelResponse> call, Response<MensajeViewModelResponse> response) {
                                                            if (response.isSuccessful()) {
                                                                MensajeViewModelResponse ultimoMensaje = response.body();

                                                                api.marcarMensajeVisto(ultimoMensaje.getId()).enqueue(new Callback<Boolean>() {
                                                                    @Override
                                                                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                                                        if (response.isSuccessful()) {
                                                                            //UltimoMensaje marcado como visto OK;
                                                                            if (response.body().equals(true)) {
                                                                                Alerts.newToastLarge(getContext(), "Ultimo Mensaje marcado como visto");
                                                                            } else {
                                                                                Alerts.newToastLarge(getContext(), "Error: no se pudo marcar UltimoMensaje como visto");
                                                                            }
                                                                        } else {
                                                                            Alerts.newToastLarge(getContext(), "Error: no se pudo marcar UltimoMensaje como visto");
                                                                        }

                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<Boolean> call, Throwable t) {
                                                                        Alerts.newToastLarge(getContext(), "Error: llamada a servicio");

                                                                    }
                                                                });


                                                            } else {
                                                                if (response.code() != 404) {
                                                                    Alerts.newToastLarge(getContext(), "ERROR 404");
                                                                } else {
                                                                    //no hay mensaje
                                                                }
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<MensajeViewModelResponse> call, Throwable t) {
                                                            Alerts.newToastLarge(getContext(), "Error: Al consultar ultimo mensaje");
                                                        }
                                                    });
                                                }


                                            } else {
                                                Alerts.newToastLarge(getContext(), "Error: Al actualizar Compra!!");
                                            }

                                        }

                                        @Override
                                        public void onFailure(Call<Boolean> call, Throwable t) {
                                            Alerts.newToastLarge(getContext(), "Error: Al actualizar compra");

                                        }
                                    });
                                }

                                fragment = new FragmentBotoneraInicioAyudante();
                                ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.content_frame, fragment).addToBackStack(null).commit();
                            }
                        });
                    }else{
                        Alerts.newToastLarge(vista.getContext(), "NO HAY COMPRAS PARA AUTORIZAR");
                        lista_vacia.setVisibility(View.VISIBLE);
                    }
                    //fin
                }else{
                    if (response.code() != 404) {
                        Alerts.newToastLarge(vista.getContext(), "ERR");
                    }
                    else
                    {
                        applicationGlobal.setCompra(null);
                    }
                }

            }

            @Override
            public void onFailure(Call<CompraViewModelResponse> call, Throwable t) {
                Alerts.newToastLarge(vista.getContext(), "Err");

            }
        });
// fin compra vigente


        return vista;
    }





}
