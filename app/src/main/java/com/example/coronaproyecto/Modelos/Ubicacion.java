package com.example.coronaproyecto.Modelos;

import android.location.Location;

public class Ubicacion {

    private String latitud ;
    private String longitud;
    private  String id;

    public String getId() {
        return id;
    }

    public Ubicacion() {
    }

    public Ubicacion(String latitud, String longitud, String id) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.id = id;
    }






    public void setId(String id) {
        this.id = id;
    }

    public Ubicacion(String latitud, String longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}
