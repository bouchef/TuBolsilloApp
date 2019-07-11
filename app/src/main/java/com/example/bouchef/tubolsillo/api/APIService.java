package com.example.bouchef.tubolsillo.api;


import com.example.bouchef.tubolsillo.api.model.CompraViewModelPOST;
import com.example.bouchef.tubolsillo.api.model.CompraViewModelResponse;
import com.example.bouchef.tubolsillo.api.model.PCDViewModelPOST;
import com.example.bouchef.tubolsillo.api.model.PCDViewModelResponse;
import com.example.bouchef.tubolsillo.api.model.UsuarioViewModelPOST;
import com.example.bouchef.tubolsillo.api.model.UsuarioViewModelResponse;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelResponse;

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
    Call<CompraViewModelResponse> nuevaCompra(@Body CompraViewModelPOST compra);
}










