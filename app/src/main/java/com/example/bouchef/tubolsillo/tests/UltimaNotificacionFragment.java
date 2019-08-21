package com.example.bouchef.tubolsillo.tests;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.bouchef.tubolsillo.R;
import com.example.bouchef.tubolsillo.api.APIService;
import com.example.bouchef.tubolsillo.api.Api;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelPOST;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelResponse;
import com.example.bouchef.tubolsillo.generics.ApplicationGlobal;
import com.example.bouchef.tubolsillo.generics.GlobalClass;
import com.example.bouchef.tubolsillo.utiles.Alerts;
import com.example.bouchef.tubolsillo.utiles.FechaUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.internal.Internal;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bouchef.tubolsillo.R.drawable.bocadillo;
import static com.example.bouchef.tubolsillo.R.drawable.information;

/**
 * A simple {@link Fragment} subclass.
 */
public class UltimaNotificacionFragment extends Fragment {
    private APIService api;

    Timer timer = new Timer();

    ApplicationGlobal applicationGlobal = ApplicationGlobal.getInstance();

    public UltimaNotificacionFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.descripcion)
    TextView descripcion;

    @BindView(R.id.fechaAlta)
    TextView fechaAlta;

    @BindView(R.id.accion)
    ImageView btn_accion;

    @BindView(R.id.fragment_main)
    LinearLayout fragment_main;

    Context mContext;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            //info = (ContratistaObservadoChart)getArguments().getSerializable(ARG_INFO);
            //color = getArguments().getInt(ARG_COLOR);
        }
    }



    @Override
    public void onStop() {
        super.onStop();
        //timer.cancel();
        //timer.purge();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ultima_notificacion, container, false);
        ButterKnife.bind(this, view);

        api = Api.getAPIService(getContext());
        mContext = getContext();

        loadMensaje();

        //comenzar_check();
        return view;
    }

    private void loadMensaje(){


        if (applicationGlobal.getUsuario() != null) {
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
        }
    }

//    public void comenzar_check(){
//        final int DELAY_SECS = 0;
//        final int DELAY_BETWEEN_INTENT_SECS = 5;
//        timer = new Timer();
//
//        timer.schedule(new TimerTask()
//        {
//            @Override
//            public void run()
//            {
//                loadMensaje();
//                //Alerts.newToastLarge(getContext(), "NINI");
//            }
//        }, DELAY_SECS*1000, DELAY_BETWEEN_INTENT_SECS*1000);
//
//    }

    private void cargarUltimoMensaje(MensajeViewModelResponse mensaje){
        try{
            if(mensaje != null){
                String t = FechaUtils.fromStringToVerbose(mensaje.getFechaAlta());
                descripcion.setText(mensaje.getDescripcion());
                fechaAlta.setText(t);

                GlobalClass g = GlobalClass.getInstance();
                Integer id = applicationGlobal.getUsuario().getIdTipoUsuario();
                if(id.equals(1)) {
                    if (mensaje.getOrdenImportancia().equals(3)) {
                        btn_accion.setImageDrawable(this.getResources().getDrawable(bocadillo));
                        btn_accion.setTag("Autorizacion");
                        g.setBtn_accion_tag("Autorizacion");
                    } else {
                        g.setBtn_accion_tag("Informacion");
                    }
                }else{
                    btn_accion.setImageDrawable(this.getResources().getDrawable(information));
                }

                Alerts.newToastLarge(getContext(), "Check msg OK");
                fragment_main.setVisibility(View.VISIBLE);
            }else{
                Alerts.newToastLarge(getContext(), "Check msg NO OK");
                fragment_main.setVisibility(View.GONE);
            }

        }catch (Exception e){
            Alerts.newToastLarge(mContext, "Check msg ERRRR");
        }

    }


}
