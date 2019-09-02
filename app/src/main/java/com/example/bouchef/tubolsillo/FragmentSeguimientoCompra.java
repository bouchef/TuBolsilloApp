package com.example.bouchef.tubolsillo;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bouchef.tubolsillo.adapter.MensajesCompraAdapter;
import com.example.bouchef.tubolsillo.api.APIService;
import com.example.bouchef.tubolsillo.api.Api;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelResponse;
import com.example.bouchef.tubolsillo.generics.ApplicationGlobal;
import com.example.bouchef.tubolsillo.generics.GlobalClass;
import com.example.bouchef.tubolsillo.generics.Progress;
import com.example.bouchef.tubolsillo.tests.ItemBasico;
import com.example.bouchef.tubolsillo.tests.MensajesRecyclerViewAdapter;
import com.example.bouchef.tubolsillo.utiles.Alerts;
import com.example.bouchef.tubolsillo.utiles.FechaUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bouchef.tubolsillo.R.drawable.bocadillo;
import static com.example.bouchef.tubolsillo.R.drawable.information;

public class FragmentSeguimientoCompra extends Fragment implements MensajesRecyclerViewAdapter.DatoBasicoItemClickListener{

    private Context mContext = getContext();
    FragmentActivity activity = (FragmentActivity) mContext;

    private APIService api;

    ApplicationGlobal applicationGlobal = ApplicationGlobal.getInstance();

    @BindView(R.id.list_mensajes_compra)
    RecyclerView list;
    //@BindView(R.id.titulo) TextView titulo;
    @BindView(R.id.empty_state_container)
    LinearLayout lista_vacia;
    @BindView(R.id.accion)
    ImageView btn_accion;
    @BindView(R.id.irHome)
    ImageView btn_home;
    @BindView(R.id.tit_barra)
    TextView titulo;

    RecyclerView recyclerListaMensajesCompra;
    MensajesRecyclerViewAdapter adapter;
    List<ItemBasico> items;

    boolean fragmentTransaction = false;
    Fragment fragment = null;


    public FragmentSeguimientoCompra() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        api = Api.getAPIService(getContext());
        //cargarLista();
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.activity_seguimiento_compra, container, false);

        lista_vacia = (LinearLayout) vista.findViewById(R.id.empty_state_container);

        items=new ArrayList<>();
        recyclerListaMensajesCompra = (RecyclerView) vista.findViewById(R.id.list_mensajes_compra);
        recyclerListaMensajesCompra.setLayoutManager(new LinearLayoutManager(getContext()));

        //cargarListaMensajesCompra();
        cargarLista();

        MensajesRecyclerViewAdapter adapter = new MensajesRecyclerViewAdapter(getContext(), items);

        recyclerListaMensajesCompra.setAdapter(adapter);

        return vista;
    }

/*    private void cargarListaMensajesCompra(){

        ItemBasico i = new ItemBasico();
        i.setDescripcion("prueba");
        i.setFecha("fecha");
        items.add(i);


    }*/

    /*private void cargarLista(){

        api.getUltimoMensaje(1, 0, 0).enqueue(new Callback<MensajeViewModelResponse>() {
            @Override
            public void onResponse(Call<MensajeViewModelResponse> call, Response<MensajeViewModelResponse> response) {
                if (response.isSuccessful()) {
                    cargarMensajes(response.body());
                } else {
                    if (response.code() != 404) {
                        Alerts.newToastLarge(getContext(), "ERR");
                    } else {
                        cargarMensajes(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<MensajeViewModelResponse> call, Throwable t) {
                Alerts.newToastLarge(getContext(), "ErrErr");
            }
        });

    }

    private void cargarMensajes(MensajeViewModelResponse mensaje){
        try{
            if(mensaje != null){
                ItemBasico i = new ItemBasico();
                i.setDescripcion(mensaje.getDescripcion());
                i.setFecha(mensaje.getFechaAlta());
                items.add(i);

                GlobalClass g = GlobalClass.getInstance();
                Integer id = 1;
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
            }else{
                Alerts.newToastLarge(getContext(), "Check msg NO OK");
            }

        }catch (Exception e){
            Alerts.newToastLarge(getContext(), "Check msg ERRRR");
        }

    }*/

private void cargarLista(){
    final Context _this = getContext();
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
                if (applicationGlobal.getCompra() == null)
                {
                    items = null;
                }
                else {
                    Response<List<MensajeViewModelResponse>> response;
                    response = api.getMensajesNuevos(applicationGlobal.getCompra().getId(), applicationGlobal.getUsuario().getId(), 4).execute();
                    if (response.isSuccessful()) {
                        //convertir la lista a otra lista
                        items = new ArrayList<>();
                        for (MensajeViewModelResponse m : response.body()) {
                            ItemBasico i = new ItemBasico();
                            i.setDescripcion(m.getDescripcion());
                            i.setFecha(FechaUtils.fromStringToVerbose(m.getFechaAlta()));
                            items.add(i);
                        }
                    } else
                        items = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                items = null;
            }
            /*ItemBasico i = new ItemBasico();
            i.setDescripcion("prueba");
            i.setFecha("fecha");
            items.add(i);*/
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
        recyclerListaMensajesCompra.setLayoutManager(new LinearLayoutManager(getContext()));
        MensajesRecyclerViewAdapter adapter = new MensajesRecyclerViewAdapter(context, items);

        // oculto segun resultado de la lista
        if(items.isEmpty()){
            lista_vacia.setVisibility(View.VISIBLE);
            recyclerListaMensajesCompra.setVisibility((View.GONE));
        }else{
            lista_vacia.setVisibility((View.GONE));
            recyclerListaMensajesCompra.setVisibility(View.VISIBLE);
        }

        adapter.setClickListener(this);
        recyclerListaMensajesCompra.setAdapter(adapter);
    }

    @Override
    public void onClickEstrella(View view, int position) {
        ItemBasico i = items.get(position);
        Alerts.newToastLarge(mContext, i.getDescripcion());

        i.setClickeado( !i.isClickeado());

        adapter.notifyItemChanged(position);


    }

}