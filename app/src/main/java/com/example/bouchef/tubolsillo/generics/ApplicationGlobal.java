package com.example.bouchef.tubolsillo.generics;

import com.example.bouchef.tubolsillo.api.model.UsuarioViewModelResponse;

public class ApplicationGlobal {
    private UsuarioViewModelResponse usuario;

    public UsuarioViewModelResponse getUsuario(){
        return usuario;
    }
    public void setUsuario(UsuarioViewModelResponse usuario){
        this.usuario = usuario;
    }

    private static ApplicationGlobal _applicationGlobal;

    public ApplicationGlobal(){

    }

    public static ApplicationGlobal getInstance(){
        if(_applicationGlobal == null){
            _applicationGlobal = new ApplicationGlobal();

        }
        return  _applicationGlobal;
    }
}
