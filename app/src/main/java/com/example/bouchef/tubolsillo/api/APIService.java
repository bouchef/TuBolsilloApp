package com.example.bouchef.tubolsillo.api;


import com.example.bouchef.tubolsillo.api.model.ComercioViewModelResponse;
import com.example.bouchef.tubolsillo.api.model.CompraViewModelPOST;
import com.example.bouchef.tubolsillo.api.model.CompraViewModelResponse;
import com.example.bouchef.tubolsillo.api.model.IdResponse;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelPOST;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelResponse;
import com.example.bouchef.tubolsillo.api.model.PCDViewModelPOST;
import com.example.bouchef.tubolsillo.api.model.PCDViewModelResponse;
import com.example.bouchef.tubolsillo.api.model.UsuarioViewModelPOST;
import com.example.bouchef.tubolsillo.api.model.UsuarioViewModelResponse;
import com.example.bouchef.tubolsillo.api.model.ComercioViewModelPOST;
import com.example.bouchef.tubolsillo.api.model.ComercioViewModelResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {
    @GET("ultimoMensaje/{idCompra}/{idUsuario}/{idTipoEvento}")
    Call<MensajeViewModelResponse> getUltimoMensaje(@Path("idCompra") int idCompra, @Path("idUsuario") int idUsuario, @Path("idTipoEvento") int idTipoEvento);

    @POST("pcd")
    Call<PCDViewModelResponse> getPCD(@Body PCDViewModelPOST pcdViewModel);

    @POST("usuarios")
    Call<UsuarioViewModelResponse> getUsuario(@Body UsuarioViewModelPOST usuarioViewModel);


    @POST("nuevaCompra")
    Call<IdResponse> nuevaCompra(@Body CompraViewModelPOST compra);

    @POST("nuevoMensaje")
    Call<IdResponse> nuevoMensaje(@Body MensajeViewModelPOST mensajeViewModelPOST);

    @POST("comercios")
    Call<List<ComercioViewModelResponse>> getComercios(@Body ComercioViewModelPOST comercioViewModelPost);

    @POST("getCompras")
    Call<List<CompraViewModelResponse>> getCompras(@Body CompraViewModelPOST compraViewModelPOST);

    @GET("actualizarCompra/{idCompra}/{idEstado}/{monto}")
    Call<Boolean> actualizarCompra(@Path("idCompra") int idCompra, @Path("idEstado") int idEstado, @Path("monto") double monto);

    @GET("marcarMensajeVisto/{id}")
    Call<Boolean> marcarMensajeVisto(@Path("id") int id);

    @GET("compraVigente/{idUsuario}")
    Call<CompraViewModelResponse> getCompraVigente(@Path("idUsuario") int idUsuario);

    @GET("usuario/{id}")
    Call<UsuarioViewModelResponse> getUsuarioPCD(@Path("id") int id);
}










