package com.example.bouchef.tubolsillo;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.bouchef.tubolsillo.api.APIService;
import com.example.bouchef.tubolsillo.generics.ApplicationGlobal;
import com.google.android.material.textfield.TextInputEditText;
import com.mercadopago.android.px.core.MercadoPagoCheckout;

public class Fragment_configuracion_pago extends Fragment {
    private Context mContext= getContext();


    private APIService api;

    ApplicationGlobal applicationGlobal = ApplicationGlobal.getInstance();

    protected static final int REQUEST_CODE = 0x01;

    String keyPublic;
    String preferenceId;
    String token;


    public Fragment_configuracion_pago() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.activity_custom_initialization, container, false);

        final TextInputEditText publicKeyInput = vista.findViewById(R.id.publicKeyInput);
        final TextInputEditText preferenceIdInput = vista.findViewById(R.id.preferenceIdInput);
        final TextInputEditText accessTokenInput = vista.findViewById(R.id.accessTokenInput);

        vista.findViewById(R.id.clearButton).setOnClickListener(v -> {
            publicKeyInput.setText("");
            preferenceIdInput.setText("");
            accessTokenInput.setText("");
        });

        if(publicKeyInput.getText().toString().isEmpty()){
            String keyPublic = "TEST-2a313672-b2b4-4ca2-a578-48b749251105";
        }

        if(preferenceIdInput.getText().toString().isEmpty()){
            String preferenceId = "";
        }

        if(accessTokenInput.getText().toString().isEmpty()){
            String token = "TEST-2015804469455591-082604-568a3a638e3ebba71a663ee2cc520ffa-80817756";
        }



        vista.findViewById(R.id.startButton)
                .setOnClickListener(v -> new MercadoPagoCheckout.Builder(publicKeyInput.getText().toString(),
                        preferenceIdInput.getText().toString())
                        .setPrivateKey(accessTokenInput.getText().toString()).build()
                        .startPayment(getContext(), REQUEST_CODE));

        return vista;
    }
}