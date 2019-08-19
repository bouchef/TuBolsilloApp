package com.example.bouchef.tubolsillo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bouchef.tubolsillo.api.model.MensajeViewModelResponse;
import com.example.bouchef.tubolsillo.generics.AppCompatCustomActivity;
import com.example.bouchef.tubolsillo.generics.Progress;
import com.example.bouchef.tubolsillo.tests.ItemBasico;
import com.example.bouchef.tubolsillo.tests.MensajesRecyclerViewAdapter;
import com.example.bouchef.tubolsillo.utiles.Alerts;
import com.example.bouchef.tubolsillo.utiles.FechaUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

public class SeguimientoCompra extends AppCompatCustomActivity implements MensajesRecyclerViewAdapter.DatoBasicoItemClickListener {

    @BindView(R.id.list_mensajes_compra)  RecyclerView list;
    //@BindView(R.id.titulo) TextView titulo;
    @BindView(R.id.empty_state_container) LinearLayout lista_vacia;
    @BindView(R.id.accion)
    ImageView btn_accion;
    @BindView(R.id.irHome)
    ImageView btn_home;
    @BindView(R.id.tit_barra) TextView titulo;

    MensajesRecyclerViewAdapter adapter;
    List<ItemBasico> items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mensajes_compra);
        ButterKnife.bind(this);

        titulo =  findViewById(R.id.tit_barra);
        titulo.setText(R.string.tit_seguimiento);



        //En AppCompatCustomActivity
        //applicationGlobal = ApplicationGlobal.getInstance();
        //api = Api.getAPIService(getApplicationContext());
        cargarLista();

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
                        Alerts.newToastLarge(v.getContext(), "Marcar Mensaje como leido");
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
    private void cargarLista(){

        final Context _this = this;
        new AsyncTask<Void, Long, List<ItemBasico>>(){
            AlertDialog progressDialog;

            @Override
            protected void onPreExecute() {
                progressDialog = Progress.getProgressBar(_this, "Cargando" );
                progressDialog.show();
                //super.onPreExecute();
            }
            @Override
            protected List<ItemBasico> doInBackground(Void... voids) {
                try {
                    Response<List<MensajeViewModelResponse>> response ;
                    response = api.getMensajesNuevos(58, 1, 4).execute();
                    if(response.isSuccessful()){
                        //convertir la lista a otra lista
                        items = new ArrayList<>();
                        for (MensajeViewModelResponse m: response.body() ) {
                            ItemBasico i = new ItemBasico();
                            i.setDescripcion(m.getDescripcion());
                            i.setFecha(FechaUtils.fromStringToVerbose(m.getFechaAlta()));
                            items.add(i);
                        }
                    }
                    else
                        items = null;
                } catch (IOException e) {
                    e.printStackTrace();
                    items = null;
                }
                return items;
            }



            @Override
            protected void onPostExecute(List<ItemBasico> proyectos){
                progressDialog.dismiss();
                super.onPostExecute(proyectos);
                if(proyectos!=null) {
                    populateList(_this);
                }
            }
        }.execute();
    }

    private void populateList(Context context){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        list.setLayoutManager(linearLayoutManager);
        adapter = new MensajesRecyclerViewAdapter(context, items);

        // oculto segun resultado de la lista
        if(items.isEmpty()){
            lista_vacia.setVisibility(View.VISIBLE);
            list.setVisibility(View.INVISIBLE);
        }else{
            lista_vacia.setVisibility(View.INVISIBLE);
            list.setVisibility(View.VISIBLE);
        }

        adapter.setClickListener(this);
        list.setAdapter(adapter);
        //DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),
                linearLayoutManager.getOrientation());
        list.addItemDecoration(dividerItemDecoration);

        list.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onClickEstrella(View view, int position) {
        ItemBasico i = items.get(position);
        Alerts.newToastLarge(getApplicationContext(), i.getDescripcion());

        i.setClickeado( !i.isClickeado());

        adapter.notifyItemChanged(position);


    }
}
