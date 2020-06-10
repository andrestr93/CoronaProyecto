package com.example.coronaproyecto.Modelos;

import java.util.ArrayList;

public class Paises {
    private String nombrepais;
    private  String idpais;



    public Paises() {
    }

    public Paises(String nombrepais, String idpais) {
        this.nombrepais = nombrepais;
        this.idpais = idpais;
    }

    public String getNombrepais() {
        return nombrepais;
    }

    public void setNombrepais(String nombre) {
        this.nombrepais = nombre;
    }

    public String getIdpais() {
        return idpais;
    }

    public void setIdpais(String idpais) {
        this.idpais = idpais;
    }
}
