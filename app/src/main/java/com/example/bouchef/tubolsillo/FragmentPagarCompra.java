package com.example.bouchef.tubolsillo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.bouchef.tubolsillo.api.APIService;
import com.example.bouchef.tubolsillo.api.Api;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelPOST;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelResponse;
import com.example.bouchef.tubolsillo.generics.ApplicationGlobal;
import com.example.bouchef.tubolsillo.utiles.Alerts;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentPagarCompra extends Fragment {

    private Context mContext= getContext();

    private APIService api;
    private EditText importe;
    private EditText mail;
    boolean fragmentTransaction = false;
    Fragment fragment = null;

    public FragmentPagarCompra() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View vista = inflater.inflate(R.layout.activity_pagar_pcd, container, false);


        api = Api.getAPIService(getContext());

        /*MensajeViewModelPOST mensajeViewModelPOST = new MensajeViewModelPOST();
        mensajeViewModelPOST.setIdUsuario(2);
        mensajeViewModelPOST.setIdCompra(0);
        mensajeViewModelPOST.setIdTipoEvento(4);*/

//        api.getUltimoMensaje(mensajeViewModelPOST.getIdUsuario(),mensajeViewModelPOST.getIdCompra(),mensajeViewModelPOST.getIdTipoEvento()).enqueue(new Callback<MensajeViewModelResponse>() {
//            @Override
//            public void onResponse(Call<MensajeViewModelResponse> call, Response<MensajeViewModelResponse> response) {
//                if(response.isSuccessful()){
//                    //*Alerts.newToastLarge(mContext, "OK");*/
//                    //cargarUltimoMensaje(response.body());
//                }else{
//                    if (response.code() != 404) {
//                        Alerts.newToastLarge(mContext, "ERR");
//                    }
//                    else
//                    {
//                        //cargarUltimoMensaje(null);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MensajeViewModelResponse> call, Throwable t) {
//                Alerts.newToastLarge(mContext, "ErrErr");
//            }
//        });

        mail = (EditText) vista.findViewById(R.id.mailTxt);
        importe = (EditText) vista.findViewById(R.id.importeTxt);
        String item = "PAGAR COMPRA 1 ($"+importe.getText().toString()+")";

        ApplicationGlobal applicationGlobal = ApplicationGlobal.getInstance();

        Button btnPCD = (Button) vista.findViewById(R.id.button);
        btnPCD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //enviar mensaje("Cancelando Compra y Volviendo")
                Toast.makeText(getContext(), "Cancelando Compra y Volviendo", Toast.LENGTH_SHORT).show();

                api.actualizarCompra(applicationGlobal.getCompra().getId(),8,0, "A").enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if(response.isSuccessful()){
                            applicationGlobal.getCompra().setIdEstado(4);
                        }else{
                            Alerts.newToastLarge(getContext(), "Err");
                        }

                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Alerts.newToastLarge(getContext(), "Err");

                    }
                });

                api.actualizarCompra(applicationGlobal.getCompra().getId(),9,0, "A").enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if(response.isSuccessful()){
                            applicationGlobal.getCompra().setIdEstado(4);
                        }else{
                            Alerts.newToastLarge(getContext(), "Err");
                        }

                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Alerts.newToastLarge(getContext(), "Err");

                    }
                });

                //Intent intent = new Intent (v.getContext(), BotoneraInicialPCD.class);
                //startActivityForResult(intent, 0);
                fragment = new FragmentBotoneraInicioPCD();
                ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, fragment).addToBackStack(null).commit();

            }
        });

        Button btnPagar = (Button) vista.findViewById(R.id.button1);
        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(importe.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Debe Colocar un importe", Toast.LENGTH_SHORT).show();
                }else {
                    if(mail.getText().toString().isEmpty()){
                        Toast.makeText(getContext(), "Debe colocar un mail de vendendor", Toast.LENGTH_SHORT).show();
                    }else{
                        ApplicationGlobal applicationGlobal = ApplicationGlobal.getInstance();

                        if(applicationGlobal.getUsuario().getIdTipoUsuario().equals(2)) {
                            // TIENE PERFIL DE AYUDANTE => ES QUIEN PAGA
                            // enviar updateCompra()
                            Toast.makeText(getContext(), "ATENCION: PAGANDO COMPRA 1 ($" + importe.getText().toString() + ")", Toast.LENGTH_SHORT).show();
                            //api.actualizarCompra(67, 4, 14).enqueue(new Callback<Boolean>() {
                            api.actualizarCompra(applicationGlobal.getCompra().getId(), 4, Double.parseDouble(importe.getText().toString()), applicationGlobal.getUsuario().getEmail()).enqueue(new Callback<Boolean>() {
                                @Override
                                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                    if (response.isSuccessful()) {
                                        applicationGlobal.getCompra().setIdEstado(4);
                                    } else {
                                        Alerts.newToastLarge(getContext(), "Err");
                                    }

                                }

                                @Override
                                public void onFailure(Call<Boolean> call, Throwable t) {
                                    Alerts.newToastLarge(getContext(), "Err");

                                }

                            });
                        }else{
                            //enviar mensaje("Autorizar pago Importe")
                            Alerts.newToastLarge(getContext(), "OJO!!! NO ESTA ENVIANDO EL MENSAJE PARA AUTORIZAR");
                        }

                    }
                }
            }
        });


        return vista;
    }

}
