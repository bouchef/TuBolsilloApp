package com.example.bouchef.tubolsillo.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsuarioViewModelResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("idTipoUsuario")
    @Expose
    private Integer idTipoUsuario;
    @SerializedName("tipoUsuario")
    @Expose
    private TipoUsuarioViewModelResponse tipoUsuario;
    @SerializedName("idNivel")
    @Expose
    private Integer idNivel;
    @SerializedName("nivel")
    @Expose
    private NivelViewModelResponse nivel;
    @SerializedName("idUsuarioPadre")
    @Expose
    private Integer idUsuarioPadre;
    @SerializedName("usuarioPadre")
    @Expose
    private UsuarioViewModelResponse usuarioPadre;
    @SerializedName("fechaAlta")
    @Expose
    private String fechaAlta;
    @SerializedName("fechaModificacion")
    @Expose
    private String fechaModificacion;
    @SerializedName("habilitado")
    @Expose
    private Boolean habilitado;
    @SerializedName("movil")
    @Expose
    private String movil;
    @SerializedName("email")
    @Expose
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(Integer idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public TipoUsuarioViewModelResponse getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuarioViewModelResponse tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public Integer getIdNivel() {
        return idNivel;
    }

    public void setIdNivel(Integer idNivel) {
        this.idNivel = idNivel;
    }

    public NivelViewModelResponse getNivel() {
        return nivel;
    }

    public void setNivel(NivelViewModelResponse nivel) {
        this.nivel = nivel;
    }

    public Integer getIdUsuarioPadre() {
        return idUsuarioPadre;
    }

    public void setIdUsuarioPadre(Integer idUsuarioPadre) {
        this.idUsuarioPadre = idUsuarioPadre;
    }

    public UsuarioViewModelResponse getUsuarioPadre() {
        return usuarioPadre;
    }

    public void setUsuarioPadre(UsuarioViewModelResponse usuarioPadre) {
        this.usuarioPadre = usuarioPadre;
    }

    public String getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}
}
