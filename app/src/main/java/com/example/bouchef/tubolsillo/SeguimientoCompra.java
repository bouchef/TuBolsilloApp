package com.example.bouchef.tubolsillo;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

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

    MensajesRecyclerViewAdapter adapter;
    List<ItemBasico> items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mensajes_compra);
        ButterKnife.bind(this);
        //titulo.setText("SEGUIMIENTO DE COMPRA");



        //En AppCompatCustomActivity
        //applicationGlobal = ApplicationGlobal.getInstance();
        //api = Api.getAPIService(getApplicationContext());
        cargarLista();
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
