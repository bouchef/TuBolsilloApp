package com.example.bouchef.tubolsillo.generics;

import com.example.bouchef.tubolsillo.api.model.ComercioViewModelResponse;
import com.example.bouchef.tubolsillo.api.model.CompraViewModelPOST;
import com.example.bouchef.tubolsillo.api.model.UsuarioViewModelResponse;
import com.example.bouchef.tubolsillo.api.model.CompraViewModelResponse;

public class ApplicationGlobal {
    private UsuarioViewModelResponse usuario;
    private ComercioViewModelResponse comercio;
    private CompraViewModelResponse compra;

    public UsuarioViewModelResponse getUsuario(){
        return usuario;
    }
    public void setUsuario(UsuarioViewModelResponse usuario){
        this.usuario = usuario;
    }

    public ComercioViewModelResponse getComercio() {return comercio;}
    public void setComercio(ComercioViewModelResponse comercio) {this.comercio = comercio;}

    public CompraViewModelResponse getCompra(){return compra;}
    public void setCompra(CompraViewModelResponse compra) {this.compra = compra;}

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
