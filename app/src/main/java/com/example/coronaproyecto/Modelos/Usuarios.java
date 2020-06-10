package com.example.coronaproyecto.Modelos;

import androidx.core.graphics.drawable.IconCompat;

import java.io.Serializable;

public class Usuarios   {
      private String usuario;
       private String fecnac;
      private int infectado;
      private String iduser;
    private String pass;
    private String pk_idciudad;


    public int getInfectado() {
        return infectado;
    }

    public void setInfectado(int infectado) {
        this.infectado = infectado;
    }

    public Usuarios() {
    }

    public String getPk_idciudad() {
        return pk_idciudad;
    }

    public void setPk_idciudad(String pk_idciudad) {
        this.pk_idciudad = pk_idciudad;
    }


    public Usuarios(String usuario, String fecnac, int infectado, String iduser, String pass , String pk_idciudad ) {
        this.usuario = usuario;
        this.fecnac = fecnac;
        this.infectado = infectado;
        this.iduser = iduser;
        this.pass = pass;
        this.pk_idciudad =pk_idciudad;

    }



    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFecnac() {
        return fecnac;
    }

    public void setFecnac(String fecnac) {
        this.fecnac = fecnac;
    }




    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int cuentaInfectados(){
       int cont = 0 ;
        if (infectado ==1){
            infectado = infectado +1;
             cont = infectado = infectado + 1;
        }

      return cont;
    }


}


