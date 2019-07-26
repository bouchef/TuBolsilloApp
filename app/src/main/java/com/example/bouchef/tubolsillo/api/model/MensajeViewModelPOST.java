package com.example.bouchef.tubolsillo.api.model;

public class MensajeViewModelPOST {

    public Integer getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Integer idCompra) {
        this.idCompra = idCompra;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIdTipoEvento() {
        return idTipoEvento;
    }

    public void setIdTipoEvento(Integer idTipoEvento) {
        this.idTipoEvento = idTipoEvento;
    }

    public Integer getOrdenImportancia() {
        return ordenImportancia;
    }

    public void setOrdenImportancia(Integer ordenImportancia) {
        this.ordenImportancia = ordenImportancia;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    private Integer idCompra;
    private String descripcion;
    private Integer idTipoEvento;
    private Integer ordenImportancia;
    private Integer idUsuario;
}
