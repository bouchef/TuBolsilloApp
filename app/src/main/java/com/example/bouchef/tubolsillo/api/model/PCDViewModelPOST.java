package com.example.bouchef.tubolsillo.api.model;

public class PCDViewModelPOST {
      //"nroCertificado": "01145456783", "idUsuario": 2

    public String getNroCertificado() {
        return nroCertificado;
    }

    public void setNroCertificado(String nroCertificado) {
        this.nroCertificado = nroCertificado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    private String nroCertificado;
    private int idUsuario;
}
