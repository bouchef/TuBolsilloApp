package com.example.bouchef.tubolsillo;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.bouchef.tubolsillo.api.APIService;
import com.example.bouchef.tubolsillo.api.Api;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelPOST;
import com.example.bouchef.tubolsillo.api.model.PCDViewModelPOST;
import com.example.bouchef.tubolsillo.api.model.PCDViewModelResponse;
import com.example.bouchef.tubolsillo.generics.ApplicationGlobal;
import com.example.bouchef.tubolsillo.utiles.Alerts;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bouchef.tubolsillo.R.layout.activity_credencial_pcd;

public class FragmentCredencialPCD extends Fragment {
    private Context mContext = getContext();
    FragmentActivity activity = (FragmentActivity) mContext;


    private APIService api;

    ApplicationGlobal applicationGlobal = ApplicationGlobal.getInstance();

    boolean fragmentTransaction = false;
    Fragment fragment = null;

    @BindView(R.id.nombre)
    TextView nombre;
    @BindView(R.id.nroCertificado) TextView nroCertificado;
    @BindView(R.id.documento) TextView documento;
    @BindView(R.id.fechaNacimiento) TextView fechaNacimiento;
    @BindView(R.id.diagnosticoFuncional) TextView diagnosticoFuncional;
    @BindView(R.id.orientacionPrestacional) TextView orientacionPrestacional;
    @BindView(R.id.fechaVencimiento) TextView fechaVencimiento;
    @BindView(R.id.telefono) TextView telefono;
    @BindView(R.id.ayudante) TextView ayudante;
    //@BindView(R.id.ayudante) TextView ayudante;
    @BindView(R.id.descripcion) TextView descripcion;
    @BindView(R.id.fechaAlta) TextView fechaAlta;

    @BindView(R.id.irHome)
    ImageView btn_home;

    @BindView(R.id.tit_barra) TextView titulo;



    public FragmentCredencialPCD() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_fragment1, container, false);
        View vista = inflater.inflate(R.layout.activity_credencial_pcd, container, false);

        nroCertificado = vista.findViewById(R.id.nroCertificado);
        nombre = vista.findViewById(R.id.nombre);
        documento = vista.findViewById(R.id.documento);
        orientacionPrestacional = vista.findViewById(R.id.orientacionPrestacional);
        fechaNacimiento = vista.findViewById(R.id.fechaNacimiento);
        fechaVencimiento = vista.findViewById(R.id.fechaVencimiento);
        diagnosticoFuncional = vista.findViewById(R.id.diagnosticoFuncional);

        telefono = vista.findViewById(R.id.telefono);
        ayudante = vista.findViewById(R.id.ayudante);


        //titulo =  vista.findViewById(R.id.tit_barra);
        //titulo.setText(R.string.tit_credencial);

        ButterKnife.bind(vista);

        ApplicationGlobal applicationGlobal = ApplicationGlobal.getInstance();

        api = Api.getAPIService(vista.getContext());

        MensajeViewModelPOST mensajeViewModelPOST = new MensajeViewModelPOST();
        mensajeViewModelPOST.setIdUsuario(applicationGlobal.getUsuario().getId());
        mensajeViewModelPOST.setIdCompra(0);
        mensajeViewModelPOST.setIdTipoEvento(4);


        PCDViewModelPOST pcdViewModelPOST = new PCDViewModelPOST();
        pcdViewModelPOST.setNroCertificado("");
        pcdViewModelPOST.setIdUsuario(applicationGlobal.getUsuario().getId());
        api.getPCD(pcdViewModelPOST).enqueue(new Callback<PCDViewModelResponse>() {
            @Override
            public void onResponse(Call<PCDViewModelResponse> call, Response<PCDViewModelResponse> response) {
                if(response.isSuccessful()){
                    cargarPCD(response.body());
                }else{
                    Alerts.newToastLarge(mContext, "ERR");
                }
            }

            @Override
            public void onFailure(Call<PCDViewModelResponse> call, Throwable t) {
                Alerts.newToastLarge(mContext, "ErrErr");
            }
        });

        cargarUsuario(applicationGlobal);
 //       telefono.setText(applicationGlobal.getUsuario().getMovil());
 //       ayudante.setText(applicationGlobal.getUsuario().getUsuarioPadre().getNombre());


        return vista;
    }

    private void cargarPCD(PCDViewModelResponse pcd){
        nombre.setText(pcd.getNombre() + " " + pcd.getApellido());
        nroCertificado.setText(pcd.getNroCertificado());
        documento.setText(pcd.getTipoDocumento() + " "+ pcd.getNroDocumento());
        orientacionPrestacional.setText(pcd.getOrientacionPrestacional());
        fechaNacimiento.setText(pcd.getFechaNacimiento());
        fechaVencimiento.setText(pcd.getFechaVencimiento());
        diagnosticoFuncional.setText(pcd.getDiagnosticoFuncional());
        //ayudante.setText(pcd.getUsuario());
    }

    private void cargarUsuario(ApplicationGlobal global) {
        telefono.setText(global.getUsuario().getMovil());
        ayudante.setText(global.getUsuario().getUsuarioPadre().getNombre());
    }

}
