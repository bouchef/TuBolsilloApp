package com.example.bouchef.tubolsillo;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
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

import static com.example.bouchef.tubolsillo.R.layout.activity_lista_control_gastos;

public class ListaControlGastos extends AppCompatActivity {
    private Context mContext= ListaControlGastos.this;

    private APIService api;

    @BindView(R.id.accion)
    ImageView btn_accion;
    @BindView(R.id.irHome) ImageView btn_home;
    @BindView(R.id.tit_barra) TextView titulo;

    //private RecyclerView recyclerView;
    private DashboardAdapter adapter;
    private ArrayList<dashboard> dashboardList;
    private ArrayList<String> cars = new ArrayList<String>();
    private dashboard das;
    private String lenguajeProgramacion[]=new String[]{"COMPRA 1 $10","COMPRA 2 $56","COMPRA 3 $38","COMPRA 4 $13","COMPRA 5 $25"};
    private Integer[] imgid={
            R.drawable.list,
            R.drawable.list,
            R.drawable.list,
            R.drawable.list,
            R.drawable.list
    };

    private ListView lista;
    private Integer idTipoEvento;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_lista_control_gastos);


        titulo =  findViewById(R.id.tit_barra);
        titulo.setText(R.string.tit_control_gastos);

        ButterKnife.bind(this);

        api = Api.getAPIService(getApplicationContext());

        ApplicationGlobal applicationGlobal = ApplicationGlobal.getInstance();

        LenguajeListAdapter adapter=new LenguajeListAdapter(this,lenguajeProgramacion,imgid);
        lista=(ListView)findViewById(R.id.mi_lista);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Slecteditem= lenguajeProgramacion[+position];
                Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();
            }
        });

        // ACCION DEL BOTON DE MENSAJE
        btn_accion =  findViewById(R.id.accion);
        String imageId = (String) btn_accion.getTag();

        btn_accion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(applicationGlobal.getUsuario().getIdTipoUsuario().equals(1)) {
                    if(imageId.equals("Autorizacion")) {
                        Intent intent = new Intent(v.getContext(), AutorizarTutor.class);
                        startActivityForResult(intent, 0);
                      finish();
                    }
                    if(imageId.equals("Informacion")) {
                        // Marcar mensaje como leido y actualizar
                        Alerts.newToastLarge(mContext, "Marcar Mensaje como leido");
                    }
                }

            }
        });
        // FIN ACCION DEL BOTON MENSAJE

        // ACCION DEL BOTON DE IR A HOME
        btn_home =  findViewById(R.id.irHome);

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(applicationGlobal.getUsuario().getIdTipoUsuario().equals(1)) {
                    Intent intent = new Intent(v.getContext(), BotoneraInicialAyudante.class);
                    startActivityForResult(intent, 0);
                  finish();
                }else {
                    Intent intent = new Intent(v.getContext(), BotoneraInicialPCD.class);
                    startActivityForResult(intent, 0);
                  finish();
                }

            }

        });
        // FIN ACCION DEL BOTON IR A HOME

    }


/*    private void cargarUltimoMensaje(MensajeViewModelResponse mensaje){
        descripcion.setText(mensaje.getDescripcion());
        fechaAlta.setText(mensaje.getFechaAlta());

        idTipoEvento = mensaje.getOrdenImportancia();
        if(idTipoEvento.equals(3)){
            autorizarButton.setVisibility(View.VISIBLE);
        }else {
            imageInfo.setVisibility(View.VISIBLE);
        }
    }*/

}
