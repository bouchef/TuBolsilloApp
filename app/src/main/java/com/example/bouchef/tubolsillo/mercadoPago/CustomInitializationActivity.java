package com.example.bouchef.tubolsillo.mercadoPago;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bouchef.tubolsillo.R;
import com.google.android.material.textfield.TextInputEditText;
import com.mercadopago.core.MercadoPagoCheckout;

public class CustomInitializationActivity extends AppCompatActivity {

    protected static final int REQUEST_CODE = 0x01;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_initialization);

        final TextInputEditText publicKeyInput = findViewById(R.id.publicKeyInput);
        final TextInputEditText preferenceIdInput = findViewById(R.id.preferenceIdInput);
        final TextInputEditText accessTokenInput = findViewById(R.id.accessTokenInput);

        findViewById(R.id.clearButton).setOnClickListener(v -> {
            publicKeyInput.setText("");
            preferenceIdInput.setText("");
            accessTokenInput.setText("");
        });

        findViewById(R.id.startButton)
                .setOnClickListener(v -> {
                    MercadoPagoCheckout.Builder mpc = new MercadoPagoCheckout.Builder();
                            mpc.setPublicKey(publicKeyInput.getText().toString());
                            mpc.startForPayment();
                });
    }
}

