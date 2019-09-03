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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.bouchef.tubolsillo.adapter.DashboardAdapter;
import com.example.bouchef.tubolsillo.adapter.LenguajeListAdapter;
import com.example.bouchef.tubolsillo.api.APIService;
import com.example.bouchef.tubolsillo.api.Api;
import com.example.bouchef.tubolsillo.api.model.IdResponse;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelPOST;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelResponse;
import com.example.bouchef.tubolsillo.api.model.UsuarioViewModelResponse;
import com.example.bouchef.tubolsillo.generics.ApplicationGlobal;
import com.example.bouchef.tubolsillo.generics.GlobalClass;
import com.example.bouchef.tubolsillo.model.dashboard;
import com.example.bouchef.tubolsillo.utiles.Alerts;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bouchef.tubolsillo.R.layout.activity_mensajes;

public class FragmentNotificador extends Fragment {

    private Context mContext= getContext();
    @BindView(R.id.accion)
    ImageView btn_accion;

    private APIService api;

    boolean fragmentTransaction = false;
    Fragment fragment = null;

    private DashboardAdapter adapter;
    private ArrayList<dashboard> dashboardList;
    private ArrayList<String> cars = new ArrayList<String>();
    private dashboard das;
    private String lenguajeProgramacion[]=new String[]{"Necesito Ayuda","Salgo a Comprar","Esperando el trasporte","Llegando al Comercio","Ya estoy volviendo","Cancelando Compra"};
    private Integer[] imgid={
            R.drawable.conversation,
            R.drawable.conversation,
            R.drawable.conversation,
            R.drawable.conversation,
            R.drawable.conversation,
            R.drawable.conversation
    };

    private ListView lista;

    public FragmentNotificador() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);

        //return inflater.inflate(R.layout.fragment_fragment1, container, false);
        View vista = inflater.inflate(R.layout.activity_mensajes, container, false);

        api = Api.getAPIService(getContext());
        ApplicationGlobal applicationGlobal = ApplicationGlobal.getInstance();

        MensajeViewModelPOST mensajeViewModelPOST = new MensajeViewModelPOST();
        mensajeViewModelPOST.setIdUsuario(applicationGlobal.getUsuario().getId());
        mensajeViewModelPOST.setIdCompra(0);
        mensajeViewModelPOST.setIdTipoEvento(4);

        api.getUltimoMensaje(mensajeViewModelPOST.getIdUsuario(),mensajeViewModelPOST.getIdCompra(),mensajeViewModelPOST.getIdTipoEvento()).enqueue(new Callback<MensajeViewModelResponse>() {
            @Override
            public void onResponse(Call<MensajeViewModelResponse> call, Response<MensajeViewModelResponse> response) {
                if(response.isSuccessful()){
                    //*Alerts.newToastLarge(mContext, "OK");*/
                    //cargarUltimoMensaje(response.body());
                }else{
                    if (response.code() != 404) {
                        Alerts.newToastLarge(mContext, "ERR");
                    }
                    else
                    {
                        //cargarUltimoMensaje(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<MensajeViewModelResponse> call, Throwable t) {
                Alerts.newToastLarge(getContext(), "ErrErr");
            }
        });

        LenguajeListAdapter adapter=new LenguajeListAdapter((Activity) getContext(),lenguajeProgramacion,imgid);
        lista=(ListView) vista.findViewById(R.id.mi_lista);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Slecteditem= lenguajeProgramacion[+position];
                obtenerUsuarioMensaje(applicationGlobal,Slecteditem);
            }
        });

        //GlobalClass g = GlobalClass.getInstance();
        //String imageId =  g.getBtn_accion_tag();





        return vista;
    }

    private void obtenerUsuarioMensaje(ApplicationGlobal global,String Slecteditem)
    {
        api = Api.getAPIService(getContext());

        MensajeViewModelPOST  m = new MensajeViewModelPOST();
        if (global.getCompra() != null) {
            m.setIdCompra(global.getCompra().getId());
        }
        m.setIdTipoEvento(4);
        m.setDescripcion(Slecteditem);
        m.setOrdenImportancia(2);

        UsuarioViewModelResponse usuario = global.getUsuario();

        if (usuario.getTipoUsuario().getDescripcion().equals("Usuario"))
        {
            m.setIdUsuario(usuario.getUsuarioPadre().getId());
            enviarNuevoMensaje(m,Slecteditem);
        }
        else
        {
            //Obtener usuario a cargo del ayudante para enviarle el mensaje
            api.getUsuarioPCD(usuario.getId()).enqueue(new Callback<UsuarioViewModelResponse>() {
                @Override
                public void onResponse(Call<UsuarioViewModelResponse> call, Response<UsuarioViewModelResponse> response) {
                    if(response.isSuccessful()){
                        usuario.setId(response.body().getId());
                        m.setIdUsuario(usuario.getId());
                        enviarNuevoMensaje(m,Slecteditem);
                    }else{
                        Alerts.newToastLarge(getContext(), "Err");
                    }

                }

                @Override
                public void onFailure(Call<UsuarioViewModelResponse> call, Throwable t) {
                    Alerts.newToastLarge(getContext(), "Err");

                }
            });

        }
    }

    private void enviarNuevoMensaje(MensajeViewModelPOST m, String Slecteditem) {

        api.nuevoMensaje(m).enqueue(new Callback<IdResponse>() {
            @Override
            public void onResponse(Call<IdResponse> call, Response<IdResponse> response) {
                if(response.isSuccessful()){
                    procesarMensaje(Slecteditem);
                }else{
                    Alerts.newToastLarge(getContext(), "Err");
                }

            }

            @Override
            public void onFailure(Call<IdResponse> call, Throwable t) {
                Alerts.newToastLarge(getContext(), "Err");

            }
        });

    }

    private void procesarMensaje(String Slecteditem){
        ApplicationGlobal applicationGlobal = ApplicationGlobal.getInstance();

        if(Slecteditem=="Necesito Ayuda"){
            //enviar mensaje("Necesito Ayuda")
            Toast.makeText(getContext(), Slecteditem, Toast.LENGTH_SHORT).show();
        }
        if(Slecteditem=="Salgo a Comprar"){
            //enviar mensaje("Salgo a Comprar")
            Toast.makeText(getContext(), Slecteditem, Toast.LENGTH_SHORT).show();
        }
        if(Slecteditem=="Esperando el trasporte"){
            //enviar mensaje("Esperando el trasporte")
            Toast.makeText(getContext(), Slecteditem, Toast.LENGTH_SHORT).show();
        }
        if(Slecteditem=="Llegando al Comercio"){
            //enviar mensaje("Llegando al Comercio")
            Toast.makeText(getContext(), Slecteditem, Toast.LENGTH_SHORT).show();

            api.actualizarCompra(applicationGlobal.getCompra().getId(),3,0, "A").enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if(response.isSuccessful()){
                        applicationGlobal.getCompra().setIdEstado(4);
                    }else{
                        Alerts.newToastLarge(getContext(), "Err");
                    }

                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Alerts.newToastLarge(getContext(), "Err");

                }
            });

            fragment = new FragmentPagarCompra();
            ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, fragment).addToBackStack(null).commit();
        }
        if(Slecteditem=="Ya estoy volviendo"){
            //enviar mensaje("Ya estoy volviendo")
            Toast.makeText(getContext(), Slecteditem, Toast.LENGTH_SHORT).show();
            fragment = new FragmentBotoneraInicioPCD();
            ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, fragment).addToBackStack(null).commit();
        }
        if(Slecteditem=="Cancelando Compra"){
            //enviar mensaje("Cancelando Compra")
            Toast.makeText(getContext(), Slecteditem, Toast.LENGTH_SHORT).show();

            api.actualizarCompra(applicationGlobal.getCompra().getId(),8,0, "A").enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if(response.isSuccessful()){
                        applicationGlobal.getCompra().setIdEstado(4);
                    }else{
                        Alerts.newToastLarge(getContext(), "Err");
                    }

                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Alerts.newToastLarge(getContext(), "Err");

                }
            });

            api.actualizarCompra(applicationGlobal.getCompra().getId(),9,0, "A").enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if(response.isSuccessful()){
                        applicationGlobal.getCompra().setIdEstado(4);
                    }else{
                        Alerts.newToastLarge(getContext(), "Err");
                    }

                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Alerts.newToastLarge(getContext(), "Err");

                }
            });

            fragment = new FragmentBotoneraInicioPCD();
            ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, fragment).addToBackStack(null).commit();

        }
    }

}
