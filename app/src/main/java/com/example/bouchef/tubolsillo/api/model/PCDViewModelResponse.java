package com.example.bouchef.tubolsillo.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PCDViewModelResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nroCertificado")
    @Expose
    private String nroCertificado;
    @SerializedName("apellido")
    @Expose
    private String apellido;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("tipoDocumento")
    @Expose
    private String tipoDocumento;
    @SerializedName("nroDocumento")
    @Expose
    private Integer nroDocumento;
    @SerializedName("fechaNacimiento")
    @Expose
    private String fechaNacimiento;
    @SerializedName("diagnosticoFuncional")
    @Expose
    private String diagnosticoFuncional;
    @SerializedName("orientacionPrestacional")
    @Expose
    private String orientacionPrestacional;
    @SerializedName("fechaVencimiento")
    @Expose
    private String fechaVencimiento;
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

    public String getNroCertificado() {
        return nroCertificado;
    }

    public void setNroCertificado(String nroCertificado) {
        this.nroCertificado = nroCertificado;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Integer getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(Integer nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDiagnosticoFuncional() {
        return diagnosticoFuncional;
    }

    public void setDiagnosticoFuncional(String diagnosticoFuncional) {
        this.diagnosticoFuncional = diagnosticoFuncional;
    }

    public String getOrientacionPrestacional() {
        return orientacionPrestacional;
    }

    public void setOrientacionPrestacional(String orientacionPrestacional) {
        this.orientacionPrestacional = orientacionPrestacional;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
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
