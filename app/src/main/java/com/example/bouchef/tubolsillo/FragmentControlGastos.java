package com.example.bouchef.tubolsillo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.bouchef.tubolsillo.adapter.DashboardAdapter;
import com.example.bouchef.tubolsillo.adapter.LenguajeListAdapter;
import com.example.bouchef.tubolsillo.api.APIService;
import com.example.bouchef.tubolsillo.api.Api;
import com.example.bouchef.tubolsillo.generics.ApplicationGlobal;
import com.example.bouchef.tubolsillo.model.dashboard;
import com.example.bouchef.tubolsillo.utiles.Alerts;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.bouchef.tubolsillo.R.layout.activity_lista_control_gastos;


public class FragmentControlGastos extends Fragment {

    private Context mContext= getContext();

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

    public FragmentControlGastos() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_fragment1, container, false);

        View vista = inflater.inflate(R.layout.activity_lista_control_gastos, container, false);

        //titulo = vista.findViewById(R.id.tit_barra);
        //titulo.setText(R.string.tit_control_gastos);

        api = Api.getAPIService(getContext());

        ApplicationGlobal applicationGlobal = ApplicationGlobal.getInstance();

        LenguajeListAdapter adapter = new LenguajeListAdapter((Activity) getContext(), lenguajeProgramacion, imgid);
        lista = (ListView) vista.findViewById(R.id.mi_lista);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Slecteditem = lenguajeProgramacion[+position];
                Toast.makeText(getContext(), Slecteditem, Toast.LENGTH_SHORT).show();
            }
        });

        return vista;
    }
}

