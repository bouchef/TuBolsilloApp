package com.example.bouchef.tubolsillo.tests;

public class ItemBasico {

    private int id;
    private String descripcion;
    private String tipo;

    private boolean clickeado;

    public boolean isClickeado() {
        return clickeado;
    }

    public void setClickeado(boolean clickeado) {
        this.clickeado = clickeado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


}
