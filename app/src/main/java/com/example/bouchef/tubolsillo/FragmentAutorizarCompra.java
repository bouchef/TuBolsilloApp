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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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

    private Integer idTipoEvento;

    private MensajeViewModelResponse ultimoMensaje;


    boolean fragmentTransaction = false;
    Fragment fragment = null;

    public FragmentAutorizarCompra() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.activity_autorizar_tutor, container, false);


        api = Api.getAPIService(getContext());

        ApplicationGlobal applicationGlobal = ApplicationGlobal.getInstance();

        MensajeViewModelPOST mensajeViewModelPOST = new MensajeViewModelPOST();
        mensajeViewModelPOST.setIdUsuario(2);
        mensajeViewModelPOST.setIdCompra(0);
        mensajeViewModelPOST.setIdTipoEvento(4);

        LenguajeListAdapter adapter=new LenguajeListAdapter((Activity) getContext(),lenguajeProgramacion,imgid);
        lista=(ListView) vista.findViewById(R.id.mi_lista);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Slecteditem= lenguajeProgramacion[+position];
                Toast.makeText(getContext(), "ATENCION: PAGO AUTORIZADO", Toast.LENGTH_SHORT).show();

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
                                        Alerts.newToastLarge(getContext(), "Err");
                                    }

                                }

                                @Override
                                public void onFailure(Call<Boolean> call, Throwable t) {
                                    Alerts.newToastLarge(getContext(), "Err");

                                }
                            });

                        }else{
                            Alerts.newToastLarge(getContext(), "Err");
                        }

                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Alerts.newToastLarge(getContext(), "Err");

                    }
                });


                fragment = new FragmentBotoneraInicioAyudante();
                ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, fragment).addToBackStack(null).commit();
            }
        });

        return vista;
    }
}
