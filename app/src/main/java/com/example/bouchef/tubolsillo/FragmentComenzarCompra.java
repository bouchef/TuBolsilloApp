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
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bouchef.tubolsillo.adapter.DashboardAdapter;
import com.example.bouchef.tubolsillo.adapter.LenguajeListAdapter;
import com.example.bouchef.tubolsillo.api.APIService;
import com.example.bouchef.tubolsillo.api.Api;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelResponse;
import com.example.bouchef.tubolsillo.generics.Progress;
import com.example.bouchef.tubolsillo.model.dashboard;
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

import static com.example.bouchef.tubolsillo.R.layout.activity_negocios_favoritos;

public class FragmentComenzarCompra extends Fragment {

    private Context mContext= getContext();

    private APIService api;

    @BindView(R.id.empty_state_container)
    LinearLayout lista_vacia;

    //private RecyclerView recyclerView;
    private DashboardAdapter adapter;
    private ArrayList<dashboard> dashboardList;
    private ArrayList<String> cars = new ArrayList<String>();
    private dashboard das;
    private String lenguajeProgramacion[]=new String[]{"FAVORITO 1","FAVORITO 2","FAVORITO 3","FAVORITO 4","FAVORITO 5"};
    private Integer[] imgid={
            R.drawable.restaurant,
            R.drawable.restaurant,
            R.drawable.restaurant,
            R.drawable.restaurant,
            R.drawable.restaurant
    };
    //private Integer image[] = {R.drawable.ic_favorite_black_24dp, R.drawable.ic_favorite_border_black_24dp, R.drawable.ic_favorite_black_24dp, R.drawable.ic_favorite_border_black_24dp,};


    private ListView lista;

    boolean fragmentTransaction = false;
    Fragment fragment = null;

    public FragmentComenzarCompra() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View vista = inflater.inflate(R.layout.activity_seguimiento_compra, container, false);

        lista_vacia = (LinearLayout) vista.findViewById(R.id.empty_state_container);

        return vista;
    }

}
