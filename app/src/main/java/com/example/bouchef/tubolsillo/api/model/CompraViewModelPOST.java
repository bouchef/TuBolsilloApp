package com.example.bouchef.tubolsillo.api.model;

public class CompraViewModelPOST {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }
    public int getIdEstado() {
        return idEstado;
    }

    public int getIdComercio() {
        return idComercio;
    }

    public void setIdComercio(int idComercio) {
        this.idComercio = idComercio;
    }

    public Boolean getCompraReal() { return compraReal;}

    public void setCompraReal(Boolean compraReal) {
        this.compraReal = compraReal;
    }

    public Double getPrecioTotal() {return precioTotal;}

    public void setPrecioTotal(Double precioTotal) {this.precioTotal = precioTotal;}

    public Double getLatitudInicio() {return latitudInicio;}

    public void setLatitudInicio(Double latitudInicio) {
        this.latitudInicio = latitudInicio;
    }

    public Double getLongitudInicio() {return longitudInicio;}

    public void setLongitudInicio(Double longitudInicio) {
        this.longitudInicio = longitudInicio;
    }

    public String getTiempoEstimadoDestino() {return tiempoEstimadoDestino;}

    public void setTiempoEstimadoDestino(String tiempoEstimadoDestino) {
        this.tiempoEstimadoDestino = tiempoEstimadoDestino;
    }

    public String getTiempoEstimadoRegreso() {return tiempoEstimadoRegreso;}

    public void setTiempoEstimadoRegreso(String tiempoEstimadoRegreso) {
        this.tiempoEstimadoRegreso = tiempoEstimadoRegreso;
    }

    public String getEmail() {return email;}

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMercadoPago_TokenId() {return mercadoPago_TokenId;}

    public void setMercadoPago_TokenId(String mercadoPago_TokenId) {this.mercadoPago_TokenId = mercadoPago_TokenId;}

    public String getMercadoPago_PublicId() {return  mercadoPago_PublicId;}

    public void  setMercadoPago_PublicId(String mercadoPago_PublicId) {this.mercadoPago_PublicId = mercadoPago_PublicId;}

    public String getMercadoPago_PreferenceId() {return  mercadoPago_PreferenceId;}

    public  void setMercadoPago_PreferenceId(String mercadoPago_PreferenceId) {this.mercadoPago_PreferenceId = mercadoPago_PreferenceId;}

    private int id;
    private int idUsuario;
    private int idComercio;
    private Boolean compraReal = null;
    private Double precioTotal = null;
    private Double latitudInicio = null;
    private Double longitudInicio = null;
    private String tiempoEstimadoDestino = null;
    private String tiempoEstimadoRegreso = null;
    private int idEstado = 0;   //facu
    private String email = null;
    private String mercadoPago_TokenId = null;
    private String mercadoPago_PublicId = null;
    private String mercadoPago_PreferenceId = null;
}
