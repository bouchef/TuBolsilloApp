package com.example.bouchef.tubolsillo.api.model;

public class UsuarioViewModelPOST {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    private int id;
    private String nombre;
}