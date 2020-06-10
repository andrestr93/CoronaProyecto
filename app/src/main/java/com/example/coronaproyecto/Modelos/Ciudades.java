package com.example.coronaproyecto.Modelos;

import java.util.ArrayList;

public class Ciudades {
     private int codpostal;
    private String nombreciudad;
    private String idciudad;
    private String pk_id_pais;

    public Ciudades() {
    }




    public String getPk_id_pais() {
        return pk_id_pais;
    }

    public void setPk_id_pais(String pk_id_pais) {
        this.pk_id_pais = pk_id_pais;
    }





    public Ciudades(int codpostal, String nombreciudad, String idciudad , String pk_id_pais) {
        this.codpostal = codpostal;
        this.nombreciudad = nombreciudad;
        this.idciudad = idciudad;
        this.pk_id_pais = pk_id_pais;
    }

    public int getCodpostal() {
        return codpostal;
    }

    public void setCodpostal(int codpostal) {
        this.codpostal = codpostal;
    }

    public String getNombreciudad() {
        return nombreciudad;
    }

    public void setNombreciudad(String nombre) {
        this.nombreciudad = nombre;
    }

    public String getIdciudad() {
        return idciudad;
    }

    public void setIdciudad(String idciudad) {
        this.idciudad = idciudad;
    }
}
