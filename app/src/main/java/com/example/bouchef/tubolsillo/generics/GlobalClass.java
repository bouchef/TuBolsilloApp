package com.example.bouchef.tubolsillo.generics;

import android.app.Application;

public class GlobalClass extends Application {
    private static GlobalClass instance;
    private String name;
    private Integer idTipoUsuario;
    private String btn_accion_tag;

    public String getName() {
        return name;
    }

    public void setName(String aName) {
        name = aName;
    }

    public String getBtn_accion_tag() {
        return btn_accion_tag;
    }

    public void setBtn_accion_tag(String _btn_accion_tag) {
        btn_accion_tag = _btn_accion_tag;
    }

    public Integer getTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(Integer _idTipoUsuario) {
        idTipoUsuario = _idTipoUsuario;
    }

    public static synchronized GlobalClass getInstance(){
        if(instance==null){
            instance=new GlobalClass();
        }
        return instance;
    }
}
