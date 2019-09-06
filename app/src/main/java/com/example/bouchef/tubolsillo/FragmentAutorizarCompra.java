package com.example.bouchef.tubolsillo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.bouchef.tubolsillo.adapter.DashboardAdapter;
import com.example.bouchef.tubolsillo.adapter.LenguajeListAdapter;
import com.example.bouchef.tubolsillo.api.APIService;
import com.example.bouchef.tubolsillo.api.Api;
import com.example.bouchef.tubolsillo.api.model.CompraViewModelPOST;
import com.example.bouchef.tubolsillo.api.model.CompraViewModelResponse;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelPOST;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelResponse;
import com.example.bouchef.tubolsillo.generics.ApplicationGlobal;
import com.example.bouchef.tubolsillo.generics.GlobalClass;
import com.example.bouchef.tubolsillo.model.dashboard;
import com.example.bouchef.tubolsillo.utiles.Alerts;
import com.example.bouchef.tubolsillo.utiles.FechaUtils;
import com.mercadopago.core.MercadoPago;
//import com.mercadopago.model.DecorationPreference;
import com.mercadopago.core.MercadoPagoCheckout;
import com.mercadopago.model.Payment;
import com.mercadopago.util.JsonUtil;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bouchef.tubolsillo.R.drawable.bocadillo;
import static com.example.bouchef.tubolsillo.R.drawable.information;


public class FragmentAutorizarCompra extends Fragment {

    private Context mContext= getContext();


    private APIService api;


    /*@BindView(R.id.autorizarButton)
    ImageButton autorizarButton;*/
    @BindView(R.id.irHome)
    ImageView btn_home;
    @BindView(R.id.accion) ImageView btn_accion;
    @BindView(R.id.tit_barra)
    TextView titulo;

    @BindView(R.id.mp_results) TextView mp_results;
    @BindView(R.id.submit) Button submitButton;


    private DashboardAdapter adapter;
    private ArrayList<dashboard> dashboardList;
    private ArrayList<String> cars = new ArrayList<String>();
    private dashboard das;
    private String lenguajeProgramacion[]=new String[]{"Compra 1 $10"};
    private Integer[] imgid={
            R.drawable.bocadillo
    };

    private ListView lista;
    @BindView(R.id.empty_state_container) LinearLayout lista_vacia;

    private Integer idTipoEvento;

    private MensajeViewModelResponse ultimoMensaje;


    boolean fragmentTransaction = false;
    Fragment fragment = null;

    ApplicationGlobal applicationGlobal = ApplicationGlobal.getInstance();

    public FragmentAutorizarCompra() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.activity_autorizar_tutor, container, false);
        lista_vacia = (LinearLayout) vista.findViewById(R.id.empty_state_container);

        submitButton = (Button) vista.findViewById(R.id.submit);

        api = Api.getAPIService(getContext());

        ApplicationGlobal applicationGlobal = ApplicationGlobal.getInstance();
// inicio compra vigente
        api.getCompraVigente(applicationGlobal.getUsuario().getId()).enqueue(new Callback<CompraViewModelResponse>() {
            @Override
            public void onResponse(Call<CompraViewModelResponse> call, Response<CompraViewModelResponse> response) {
                if(response.isSuccessful()){
                    applicationGlobal.setCompra(response.body());
                    // inicio
                    if(applicationGlobal.getCompra() != null) {
                        LenguajeListAdapter adapter = new LenguajeListAdapter((Activity) getContext(), lenguajeProgramacion, imgid);
                        lista = (ListView) vista.findViewById(R.id.mi_lista);
                        lista.setAdapter(adapter);
                        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String Slecteditem = lenguajeProgramacion[+position];
                                Toast.makeText(getContext(), "ATENCION: PAGO AUTORIZADO", Toast.LENGTH_SHORT).show();


                                if (applicationGlobal.getCompra() != null) {
                                    CompraViewModelPOST compraViewModelPOST = new CompraViewModelPOST();
                                    compraViewModelPOST.setId(applicationGlobal.getCompra().getId());
                                    compraViewModelPOST.setIdEstado(5);
                                    compraViewModelPOST.setPrecioTotal(Double.parseDouble("10"));

                                    api.actualizarCompra(compraViewModelPOST).enqueue(new Callback<Boolean>() {
                                        @Override
                                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                            if (response.isSuccessful()) {
                                                applicationGlobal.getCompra().setIdEstado(5);
                                                if (applicationGlobal.getUsuario() != null) {
                                                    MensajeViewModelPOST mensajeViewModelPOST = new MensajeViewModelPOST();
                                                    mensajeViewModelPOST.setIdUsuario(applicationGlobal.getUsuario().getId());
                                                    mensajeViewModelPOST.setIdCompra(applicationGlobal.getCompra().getId());
                                                    mensajeViewModelPOST.setIdTipoEvento(4);    // tipoEvento = Pedir Autorizacion

                                                    api.getUltimoMensaje(mensajeViewModelPOST.getIdUsuario(), mensajeViewModelPOST.getIdCompra(), mensajeViewModelPOST.getIdTipoEvento()).enqueue(new Callback<MensajeViewModelResponse>() {
                                                        @Override
                                                        public void onResponse(Call<MensajeViewModelResponse> call, Response<MensajeViewModelResponse> response) {
                                                            if (response.isSuccessful()) {
                                                                MensajeViewModelResponse ultimoMensaje = response.body();

                                                                api.marcarMensajeVisto(ultimoMensaje.getId()).enqueue(new Callback<Boolean>() {
                                                                    @Override
                                                                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                                                        if (response.isSuccessful()) {
                                                                            //UltimoMensaje marcado como visto OK;
                                                                            if (response.body().equals(true)) {
                                                                                Alerts.newToastLarge(getContext(), "Ultimo Mensaje marcado como visto");
                                                                            } else {
                                                                                Alerts.newToastLarge(getContext(), "Error: no se pudo marcar UltimoMensaje como visto");
                                                                            }
                                                                        } else {
                                                                            Alerts.newToastLarge(getContext(), "Error: no se pudo marcar UltimoMensaje como visto");
                                                                        }

                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<Boolean> call, Throwable t) {
                                                                        Alerts.newToastLarge(getContext(), "Error: llamada a servicio");

                                                                    }
                                                                });


                                                            } else {
                                                                if (response.code() != 404) {
                                                                    Alerts.newToastLarge(getContext(), "ERROR 404");
                                                                } else {
                                                                    //no hay mensaje
                                                                }
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<MensajeViewModelResponse> call, Throwable t) {
                                                            Alerts.newToastLarge(getContext(), "Error: Al consultar ultimo mensaje");
                                                        }
                                                    });
                                                }


                                            } else {
                                                Alerts.newToastLarge(getContext(), "Error: Al actualizar Compra!!");
                                            }

                                        }

                                        @Override
                                        public void onFailure(Call<Boolean> call, Throwable t) {
                                            Alerts.newToastLarge(getContext(), "Error: Al actualizar compra");

                                        }
                                    });
                                }

                                fragment = new FragmentBotoneraInicioAyudante();
                                ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.content_frame, fragment).addToBackStack(null).commit();
                            }
                        });
                    }else{
                        Alerts.newToastLarge(vista.getContext(), "NO HAY COMPRAS PARA AUTORIZAR");
                        lista_vacia.setVisibility(View.VISIBLE);
                    }
                    //fin
                }else{
                    if (response.code() != 404) {
                        Alerts.newToastLarge(vista.getContext(), "ERR");
                    }
                    else
                    {
                        applicationGlobal.setCompra(null);
                    }
                }

            }

            @Override
            public void onFailure(Call<CompraViewModelResponse> call, Throwable t) {
                Alerts.newToastLarge(vista.getContext(), "Err");

            }
        });
// fin compra vigente
        //boton Mercadopago
        /*submitButton.setOnClickListener(v -> new MercadoPagoCheckout.Builder().setPublicKey("TEST-2a313672-b2b4-4ca2-a578-48b749251105")
                .setActivity((Activity) v.getContext()));
*/

        return vista;
    }
    // Método ejecutado al hacer clic en el botón
/*    public void submit(View view) {
        // Iniciar el checkout de Mercado Pago
        new MercadoPago.StartActivityBuilder()
                .setActivity((Activity) view.getContext())
                .setPublicKey("TEST-ad365c37-8012-4014-84f5-6c895b3f8e0a")
                .setCheckoutPreferenceId("150216849-ceed1ee4-8ab9-4449-869f-f4a8565d386f")
                .startCheckoutActivity();

    }*/



    // Espera los resultados del checkout
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {

        if (requestCode == MercadoPago.CHECKOUT_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {

                // Listo! El pago ya fue procesado por MP.
                Payment payment = JsonUtil.getInstance()
                        .fromJson(data.getStringExtra("payment"), Payment.class);
                TextView results = (TextView) view.findViewById(R.id.mp_results);

                if (payment != null) {
                    results.setText("PaymentID: " + payment.getId() +
                            " - PaymentStatus: " + payment.getStatus());
                } else {
                    results.setText("El usuario no concretó el pago.");
                }

            } else {
                if ((data != null) && (data.hasExtra("mpException"))) {
                    MPException mpException = JsonUtil.getInstance()
                            .fromJson(data.getStringExtra("mpException"), MPException.class);
                    // Manejá tus errores.
                }
            }
        }
    }*/




}
