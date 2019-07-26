package com.example.bouchef.tubolsillo.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MensajeViewModelResponse {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("ordenImportancia")
    @Expose
    private Integer ordenImportancia;
    @SerializedName("fechaAlta")
    @Expose
    private String fechaAlta;
    @SerializedName("fechaVisto")
    @Expose
    private String fechaVisto;
    @SerializedName("idTipoEvento")
    @Expose
    private Integer idTipoEvento;
    @SerializedName("tipoEvento")
    @Expose
    private TipoEventoViewModelResponse tipoEvento;
    @SerializedName("idCompra")
    @Expose
    private Integer idCompra;
    @SerializedName("compra")
    @Expose
    private CompraViewModelResponse compra;
    @SerializedName("idUsuario")
    @Expose
    private Integer idUsuario;
    @SerializedName("usuario")
    @Expose
    private UsuarioViewModelResponse usuario;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getOrdenImportancia() {
        return ordenImportancia;
    }

    public void setOrdenImportancia(Integer ordenImportancia) {
        this.ordenImportancia = ordenImportancia;
    }

    public String getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getFechaVisto() {
        return fechaVisto;
    }

    public void setFechaVisto(String fechaVisto) {
        this.fechaVisto = fechaVisto;
    }

    public Integer getIdTipoEvento() {
        return idTipoEvento;
    }

    public void setIdTipoEvento(Integer idTipoEvento) {
        this.idTipoEvento = idTipoEvento;
    }

    public TipoEventoViewModelResponse getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(TipoEventoViewModelResponse tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public Integer getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Integer idCompra) {
        this.idCompra = idCompra;
    }

    public CompraViewModelResponse getCompra() {
        return compra;
    }

    public void setCompra(CompraViewModelResponse compra) {
        this.compra = compra;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public UsuarioViewModelResponse getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioViewModelResponse usuario) {
        this.usuario = usuario;
    }
}
