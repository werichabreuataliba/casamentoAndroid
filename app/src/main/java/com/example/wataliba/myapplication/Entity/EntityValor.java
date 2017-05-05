package com.example.wataliba.myapplication.Entity;

/**
 * Created by wataliba on 29/03/2017.
 */
public class EntityValor {

    private double _valor;

    public void set_valor(double _valor) {
        this._valor = _valor;
    }

    public double get_valor() {

        return _valor;
    }

    public EntityValor(double valor){
        this._valor = valor;
    }
    public EntityValor(){}
}
