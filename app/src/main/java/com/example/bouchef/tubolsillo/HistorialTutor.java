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

import com.example.bouchef.tubolsillo.api.model.MensajeViewModelPOST;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelResponse;
import com.example.bouchef.tubolsillo.generics.AppCompatCustomActivity;
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
import butterknife.ButterKnife;
import retrofit2.Response;

public class HistorialTutor extends AppCompatCustomActivity implements HistorialMensajesRecyclerViewAdapter.DatoBasicoItemClickListener {
    @BindView(R.id.list_mensajes)
    RecyclerView list;
    HistorialMensajesRecyclerViewAdapter adapter;
    List<ItemBasico> items;
    //@BindView(R.id.titulo) TextView titulo;
    @BindView(R.id.empty_state_container) LinearLayout lista_vacia;


//    private Context mContext= HistorialTutor.this;
//
//    private APIService api;
//
//    @BindView(R.id.descripcion) TextView descripcion;
//    @BindView(R.id.fechaAlta) TextView fechaAlta;
//    @BindView(R.id.autorizarButton) ImageButton autorizarButton;
//
//    @BindView(R.id.info)
//    ImageView imageInfo;
//    private Integer idTipoEvento;

    //private RecyclerView recyclerView;
    /*private DashboardAdapter adapter;
    private ArrayList<dashboard> dashboardList;
    private ArrayList<String> cars = new ArrayList<String>();
    private dashboard das;
    private String lenguajeProgramacion[]=new String[]{"Mensaje Usuario PCD","Mensaje Usuario PCD","Mensaje Usuario PCD","Mensaje Usuario PCD","Mensaje Usuario PCD"};
    private Integer[] imgid={
            R.drawable.user,
            R.drawable.user,
            R.drawable.user,
            R.drawable.user,
            R.drawable.user
    };*/

    //private ListView lista;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_tutor);
        //setContentView(R.layout.mensajes_compra);
        //titulo.setText("HISTORIAL DE MENSAJES");
        ButterKnife.bind(this);


//        api = Api.getAPIService(getApplicationContext());
//
//        MensajeViewModelPOST mensajeViewModelPOST = new MensajeViewModelPOST();
//        mensajeViewModelPOST.setIdUsuario(1);
//        mensajeViewModelPOST.setIdCompra(0);
//        mensajeViewModelPOST.setIdTipoEvento(4);
//
//        api.getUltimoMensaje(mensajeViewModelPOST.getIdCompra(),mensajeViewModelPOST.getIdUsuario(),mensajeViewModelPOST.getIdTipoEvento()).enqueue(new Callback<MensajeViewModelResponse>() {
//            @Override
//            public void onResponse(Call<MensajeViewModelResponse> call, Response<MensajeViewModelResponse> response) {
//                if(response.isSuccessful()){
//                    //*Alerts.newToastLarge(mContext, "OK");*/
//                    cargarUltimoMensaje(response.body());
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

        /*LenguajeListAdapter adapter=new LenguajeListAdapter(this,lenguajeProgramacion,imgid);
        lista=(ListView)findViewById(R.id.mi_lista);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Slecteditem= lenguajeProgramacion[+position];
                Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();
            }
        });

        autorizarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), AutorizarTutor.class);
                startActivityForResult(intent, 0);
            }
        });*/

        cargarLista();
    }


//    private void cargarUltimoMensaje(MensajeViewModelResponse mensaje){
//        descripcion.setText(mensaje.getDescripcion());
//        fechaAlta.setText(mensaje.getFechaAlta());
//
//        idTipoEvento = mensaje.getOrdenImportancia();
//        if(idTipoEvento.equals(3)){
//            autorizarButton.setVisibility(View.VISIBLE);
//        }else {
//            imageInfo.setVisibility(View.VISIBLE);
//        }
//    }

    private void cargarLista(){

        final Context _this = this;

        MensajeViewModelPOST mensajeViewModelPOST = new MensajeViewModelPOST();
        mensajeViewModelPOST.setIdUsuario(1);
        mensajeViewModelPOST.setIdCompra(0);
        mensajeViewModelPOST.setIdTipoEvento(0);
        mensajeViewModelPOST.setDescripcion("");
        mensajeViewModelPOST.setOrdenImportancia(0);

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
                    populateList(_this);
                }
            }
        }.execute();
    }

    private void populateList(Context context){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        list.setLayoutManager(linearLayoutManager);
        adapter = new HistorialMensajesRecyclerViewAdapter(context, items);

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
