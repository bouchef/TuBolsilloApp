package com.example.bouchef.tubolsillo;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bouchef.tubolsillo.R.layout.activity_pagar_pcd;

public class PagarPCD extends AppCompatActivity {
    private Context mContext= PagarPCD.this;


    private APIService api;

    @BindView(R.id.descripcion)
    TextView descripcion;
    @BindView(R.id.fechaAlta) TextView fechaAlta;

    //private RecyclerView recyclerView;
    private DashboardAdapter adapter;
    private ArrayList<dashboard> dashboardList;
    private ArrayList<String> cars = new ArrayList<String>();
    private dashboard das;
    /*private String lenguajeProgramacion[]=new String[]{"Compra 1","Producto 2","Producto 3","Producto 4","Producto 5"};
    private Integer[] imgid={
            R.drawable.eggs,
            R.drawable.bottle,
            R.drawable.milk,
            R.drawable.cheese,
            R.drawable.cereals
    };*/
    /*private String lenguajeProgramacion[]=new String[]{"PAGAR COMPRA 1"};
    private Integer[] imgid={
            R.drawable.list
    };*/
    private String lenguajeProgramacion[]=new String[]{};
    private Integer[] imgid={};
    private ListView lista;

    private EditText importe;
    private EditText mail;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_pagar_pcd);



        ButterKnife.bind(this);

        api = Api.getAPIService(getApplicationContext());

        MensajeViewModelPOST mensajeViewModelPOST = new MensajeViewModelPOST();
        mensajeViewModelPOST.setIdUsuario(1);
        mensajeViewModelPOST.setIdCompra(0);
        mensajeViewModelPOST.setIdTipoEvento(4);

        api.getUltimoMensaje(mensajeViewModelPOST.getIdCompra(),mensajeViewModelPOST.getIdUsuario(),mensajeViewModelPOST.getIdTipoEvento()).enqueue(new Callback<MensajeViewModelResponse>() {
            @Override
            public void onResponse(Call<MensajeViewModelResponse> call, Response<MensajeViewModelResponse> response) {
                if(response.isSuccessful()){
                    //*Alerts.newToastLarge(mContext, "OK");*/
                    cargarUltimoMensaje(response.body());
                }else{
                    Alerts.newToastLarge(mContext, "ERR");
                }
            }

            @Override
            public void onFailure(Call<MensajeViewModelResponse> call, Throwable t) {
                Alerts.newToastLarge(mContext, "ErrErr");
            }
        });


        mail = (EditText) findViewById(R.id.mailTxt);
        importe = (EditText) findViewById(R.id.importeTxt);
        String item = "PAGAR COMPRA 1 ($"+importe.getText().toString()+")";

        lenguajeProgramacion= new String[]{item};
        imgid= new Integer[]{R.drawable.list};

        LenguajeListAdapter adapter=new LenguajeListAdapter(this,lenguajeProgramacion,imgid);


        lista=(ListView)findViewById(R.id.mi_lista);
        lista.setAdapter(adapter);

        lista.setVisibility(View.INVISIBLE);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ApplicationGlobal applicationGlobal = ApplicationGlobal.getInstance();
                String Slecteditem= lenguajeProgramacion[+position];
                //enviar mensaje("Autorizar pago Importe")
                Toast.makeText(getApplicationContext(), "ATENCION: PAGANDO COMPRA 1 ($"+importe.getText().toString()+")", Toast.LENGTH_SHORT).show();

                api.actualizarCompra(applicationGlobal.getCompra().getId(),4,Double.parseDouble(importe.getText().toString())).enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if(response.isSuccessful()){
                            applicationGlobal.getCompra().setIdEstado(4);
                        }else{
                            Alerts.newToastLarge(getApplicationContext(), "Err");
                        }

                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Alerts.newToastLarge(getApplicationContext(), "Err");

                    }
                });

                Intent intent = new Intent (view.getContext(), BotoneraInicialPCD.class);
                startActivityForResult(intent, 0);
            }
        });

        Button btnPCD = (Button) findViewById(R.id.button);
        btnPCD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationGlobal applicationGlobal = ApplicationGlobal.getInstance();

                //enviar mensaje("Cancelando Compra y Volviendo")
                Toast.makeText(getApplicationContext(), "Cancelando Compra y Volviendo", Toast.LENGTH_SHORT).show();

                api.actualizarCompra(applicationGlobal.getCompra().getId(),8,0).enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if(response.isSuccessful()){
                            applicationGlobal.getCompra().setIdEstado(4);
                        }else{
                            Alerts.newToastLarge(getApplicationContext(), "Err");
                        }

                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Alerts.newToastLarge(getApplicationContext(), "Err");

                    }
                });

                api.actualizarCompra(applicationGlobal.getCompra().getId(),9,0).enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if(response.isSuccessful()){
                            applicationGlobal.getCompra().setIdEstado(4);
                        }else{
                            Alerts.newToastLarge(getApplicationContext(), "Err");
                        }

                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Alerts.newToastLarge(getApplicationContext(), "Err");

                    }
                });

                Intent intent = new Intent (v.getContext(), BotoneraInicialPCD.class);
                startActivityForResult(intent, 0);
            }
        });

        Button btnPagar = (Button) findViewById(R.id.button1);
        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(importe.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Debe Colocar un importe", Toast.LENGTH_SHORT).show();
                }else {
                    if(mail.getText().toString().isEmpty()){
                        Toast.makeText(getApplicationContext(), "Debe colocar un mail de vendendor", Toast.LENGTH_SHORT).show();
                    }else{
                        lista.setVisibility(View.VISIBLE);

                    }
                }
            }
        });

    }


    private void cargarUltimoMensaje(MensajeViewModelResponse mensaje){
        descripcion.setText(mensaje.getDescripcion());
        fechaAlta.setText(mensaje.getFechaAlta());
    }

}
