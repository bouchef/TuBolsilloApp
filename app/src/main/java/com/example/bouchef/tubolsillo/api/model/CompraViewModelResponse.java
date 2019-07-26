package com.example.bouchef.tubolsillo.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompraViewModelResponse {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("compraReal")
    @Expose
    private Boolean compraReal;
    @SerializedName("precioTotal")
    @Expose
    private Double precioTotal;
    @SerializedName("latitudInicio")
    @Expose
    private Double latitudInicio;
    @SerializedName("longitudInicio")
    @Expose
    private Double longitudInicio;
    @SerializedName("fechaInicio")
    @Expose
    private String fechaInicio;
    @SerializedName("fechaDestino")
    @Expose
    private String fechaDestino;
    @SerializedName("fechaFin")
    @Expose
    private String fechafin;
    @SerializedName("tiempoEstimadoDestino")
    @Expose
    private String fechaEstimadoDestino;
    @SerializedName("tiempoEstimadoRegreso")
    @Expose
    private String tiempoEstimadoRegreso;
    @SerializedName("idUsuario")
    @Expose
    private Integer idUsuario;
    @SerializedName("usuario")
    @Expose
    private UsuarioViewModelResponse usuario;
    @SerializedName("idEstado")
    @Expose
    private Integer idEstado;
    @SerializedName("estado")
    @Expose
    private EstadoViewModelResponse estado;
    @SerializedName("idComercio")
    @Expose
    private Integer idComercio;
    @SerializedName("comercio")
    @Expose
    private ComercioViewModelResponse comercio;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getCompraReal() {
        return compraReal;
    }

    public void setCompraReal(Boolean compraReal) {
        this.compraReal = compraReal;
    }

    public Double getPrecioTotal() {return precioTotal;}

    public void setPrecioTotal(Double precioTotal) {this.precioTotal = precioTotal;}

    public Double getLatitudInicio() {return latitudInicio;}

    public void setLatitudInicio(Double latitudInicio) {this.latitudInicio = latitudInicio;}

    public Double getLongitudInicio() {return longitudInicio;}

    public void setLongitudInicio(Double longitudInicio) {this.longitudInicio = longitudInicio;}

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaDestino() {
        return fechaDestino;
    }

    public void setFechaDestino(String fechaDestino) {
        this.fechaDestino = fechaDestino;
    }

    public String getFechafin() {
        return fechafin;
    }

    public void setFechafin(String fechafin) {
        this.fechafin = fechafin;
    }

    public String getFechaEstimadoDestino() {
        return fechaEstimadoDestino;
    }

    public void setFechaEstimadoDestino(String fechaEstimadoDestino) {
        this.fechaEstimadoDestino = fechaEstimadoDestino;
    }

    public String getTiempoEstimadoRegreso() {
        return tiempoEstimadoRegreso;
    }

    public void setTiempoEstimadoRegreso(String tiempoEstimadoRegreso) {
        this.tiempoEstimadoRegreso = tiempoEstimadoRegreso;
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

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public EstadoViewModelResponse getEstado() {
        return estado;
    }

    public void setEstado(EstadoViewModelResponse estado) {
        this.estado = estado;
    }

    public Integer getIdComercio() {
        return idComercio;
    }

    public void setIdComercio(Integer idComercio) {
        this.idComercio = idComercio;
    }

    public ComercioViewModelResponse getComercio() {
        return comercio;
    }

    public void setComercio(ComercioViewModelResponse comercio) {
        this.comercio = comercio;
    }
}
