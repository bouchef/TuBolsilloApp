package com.example.bouchef.tubolsillo;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bouchef.tubolsillo.api.APIService;
import com.example.bouchef.tubolsillo.api.Api;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelPOST;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelResponse;
import com.example.bouchef.tubolsillo.generics.ApplicationGlobal;
import com.example.bouchef.tubolsillo.generics.Progress;
import com.example.bouchef.tubolsillo.tests.HistorialMensajesRecyclerViewAdapter;
import com.example.bouchef.tubolsillo.tests.ItemBasico;
import com.example.bouchef.tubolsillo.tests.MensajesRecyclerViewAdapter;
import com.example.bouchef.tubolsillo.utiles.Alerts;
import com.example.bouchef.tubolsillo.utiles.FechaUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Response;


public class FragmentHistorialMensajes extends Fragment  implements HistorialMensajesRecyclerViewAdapter.DatoBasicoItemClickListener{
    private Context mContext = getContext();
    FragmentActivity activity = (FragmentActivity) mContext;


    private APIService api;

    @BindView(R.id.list_mensajes) RecyclerView list;
    @BindView(R.id.empty_state_container) LinearLayout lista_vacia;
    RecyclerView recyclerListaMensajes;
    HistorialMensajesRecyclerViewAdapter adapter;
    List<ItemBasico> items;

    ApplicationGlobal applicationGlobal = ApplicationGlobal.getInstance();

    boolean fragmentTransaction = false;
    Fragment fragment = null;

    public FragmentHistorialMensajes() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        api = Api.getAPIService(getContext());
        //cargarLista();
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.activity_historial_tutor, container, false);

        lista_vacia = (LinearLayout) vista.findViewById(R.id.empty_state_container);

        items=new ArrayList<>();
        recyclerListaMensajes = (RecyclerView) vista.findViewById(R.id.list_mensajes);
        recyclerListaMensajes.setLayoutManager(new LinearLayoutManager(getContext()));


        cargarLista();

        HistorialMensajesRecyclerViewAdapter adapter = new HistorialMensajesRecyclerViewAdapter(getContext(), items);

        recyclerListaMensajes.setAdapter(adapter);

        return  vista;
    }

    private void cargarLista(){

        MensajeViewModelPOST mensajeViewModelPOST = new MensajeViewModelPOST();
        mensajeViewModelPOST.setIdUsuario(applicationGlobal.getUsuario().getId());
        mensajeViewModelPOST.setIdCompra(0);
        mensajeViewModelPOST.setIdTipoEvento(0);
        mensajeViewModelPOST.setDescripcion("");
        mensajeViewModelPOST.setOrdenImportancia(0);

        new AsyncTask<Void, Long, List<ItemBasico>>(){
            AlertDialog progressDialog;

            @Override
            protected void onPreExecute() {
                progressDialog = Progress.getProgressBar(getContext(), "Cargando" );
                progressDialog.show();
                //super.onPreExecute();
            }
            @Override
            protected List<ItemBasico> doInBackground(Void... voids) {
                try {
                    Response<List<MensajeViewModelResponse>> response ;
                    //response = api.getMensajesNuevos(58, 1, 4).execute();
                    response = api.getHistorialMensajes(mensajeViewModelPOST).execute();
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
                    populateList(getContext());
                }
            }
        }.execute();
    }

    private void populateList(Context context){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerListaMensajes.setLayoutManager(new LinearLayoutManager(getContext()));
        HistorialMensajesRecyclerViewAdapter adapter = new HistorialMensajesRecyclerViewAdapter(context, items);

        // oculto segun resultado de la lista
        if(items.isEmpty()){
            lista_vacia.setVisibility(View.VISIBLE);
            recyclerListaMensajes.setVisibility((View.GONE));
        }else{
            lista_vacia.setVisibility((View.GONE));
            recyclerListaMensajes.setVisibility(View.VISIBLE);
        }
        adapter.setClickListener(this);
        recyclerListaMensajes.setAdapter(adapter);

    }

    @Override
    public void onClickEstrella(View view, int position) {
        ItemBasico i = items.get(position);
        Alerts.newToastLarge(mContext, i.getDescripcion());

        i.setClickeado( !i.isClickeado());

        adapter.notifyItemChanged(position);
    }
}

