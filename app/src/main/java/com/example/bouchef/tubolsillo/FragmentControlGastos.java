package com.example.bouchef.tubolsillo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bouchef.tubolsillo.adapter.DashboardAdapter;
import com.example.bouchef.tubolsillo.adapter.LenguajeListAdapter;
import com.example.bouchef.tubolsillo.api.APIService;
import com.example.bouchef.tubolsillo.api.Api;
import com.example.bouchef.tubolsillo.api.model.CompraViewModelPOST;
import com.example.bouchef.tubolsillo.api.model.CompraViewModelResponse;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelResponse;
import com.example.bouchef.tubolsillo.generics.ApplicationGlobal;
import com.example.bouchef.tubolsillo.generics.Progress;
import com.example.bouchef.tubolsillo.model.dashboard;
import com.example.bouchef.tubolsillo.tests.ControlGastosRecyclerViewAdapter;
import com.example.bouchef.tubolsillo.tests.ItemBasico;
import com.example.bouchef.tubolsillo.tests.MensajesRecyclerViewAdapter;
import com.example.bouchef.tubolsillo.utiles.Alerts;
import com.example.bouchef.tubolsillo.utiles.FechaUtils;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

import static com.example.bouchef.tubolsillo.R.layout.activity_lista_control_gastos;


public class FragmentControlGastos extends Fragment implements ControlGastosRecyclerViewAdapter.DatoBasicoItemClickListener{

    private Context mContext= getContext();
    FragmentActivity activity = (FragmentActivity) mContext;

    private APIService api;

    @BindView(R.id.list_gastos_compras)
    RecyclerView list;
    //@BindView(R.id.titulo) TextView titulo;
    @BindView(R.id.empty_state_container)
    LinearLayout lista_vacia;
    @BindView(R.id.accion)
    ImageView btn_accion;
    @BindView(R.id.irHome) ImageView btn_home;
    @BindView(R.id.tit_barra) TextView titulo;
    @BindView(R.id.text_total_gastos) TextView total_gastos;

    RecyclerView recyclerListaControlGastos;
    ControlGastosRecyclerViewAdapter adapter;
    List<ItemBasico> items;

    ApplicationGlobal applicationGlobal;

    boolean fragmentTransaction = false;
    Fragment fragment = null;

    double total = 0;
    NumberFormat nf = new DecimalFormat("##.##");

    public FragmentControlGastos() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        api = Api.getAPIService(getContext());
        View vista = inflater.inflate(R.layout.activity_lista_control_gastos, container, false);

        applicationGlobal = ApplicationGlobal.getInstance();

        lista_vacia = (LinearLayout) vista.findViewById(R.id.empty_state_container);

        items=new ArrayList<>();
        recyclerListaControlGastos = (RecyclerView) vista.findViewById(R.id.list_gastos_compras);
        recyclerListaControlGastos.setLayoutManager(new LinearLayoutManager(getContext()));
        total_gastos = (TextView) vista.findViewById(R.id.text_total_gastos);

        cargarLista();

        ControlGastosRecyclerViewAdapter adapter = new ControlGastosRecyclerViewAdapter(getContext(), items);

        recyclerListaControlGastos.setAdapter(adapter);

        return vista;
    }

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
                    CompraViewModelPOST nuevaCompra = new CompraViewModelPOST();
                    nuevaCompra.setId(0);
                    nuevaCompra.setIdUsuario(applicationGlobal.getUsuario().getId());
                    nuevaCompra.setIdEstado(5);

                    Response<List<CompraViewModelResponse>> response ;
                    response = api.getCompras(nuevaCompra).execute();
                    if(response.isSuccessful()){
                        //convertir la lista a otra lista
                        items = new ArrayList<>();
                        for (CompraViewModelResponse m: response.body() ) {
                            ItemBasico i = new ItemBasico();
                            i.setDescripcion("COMPRA " + m.getId());
                            i.setFecha(FechaUtils.fromStringToVerbose(m.getFechafin()));
                            i.setPrecio(m.getPrecioTotal());
                            items.add(i);
                            total += m.getPrecioTotal();
                        }
                    }
                    else
                        items = null;
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
        recyclerListaControlGastos.setLayoutManager(new LinearLayoutManager(getContext()));
        ControlGastosRecyclerViewAdapter adapter = new ControlGastosRecyclerViewAdapter(context, items);

        total_gastos.setText("TOTAL      $" + nf.format(total));

        // oculto segun resultado de la lista
        if(items.isEmpty()){
            lista_vacia.setVisibility(View.VISIBLE);
            recyclerListaControlGastos.setVisibility((View.GONE));
        }else{
            lista_vacia.setVisibility((View.GONE));
            recyclerListaControlGastos.setVisibility(View.VISIBLE);
        }

        adapter.setClickListener(this);
        recyclerListaControlGastos.setAdapter(adapter);
    }

    @Override
    public void onClickEstrella(View view, int position) {
        ItemBasico i = items.get(position);
        Alerts.newToastLarge(mContext, i.getDescripcion());

        i.setClickeado( !i.isClickeado());

        adapter.notifyItemChanged(position);


    }
}

