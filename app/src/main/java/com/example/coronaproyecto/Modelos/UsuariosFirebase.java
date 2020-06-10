package com.example.coronaproyecto.Modelos;

public class UsuariosFirebase {
    private String usuario;
      private String fecnac;
      private int infectado;
     private String iduser;
    private String pass;
    private String pk_idciudad;
    private String latitud;
     private  String longitud;

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

    public void setInfectado(int infectado) {
        this.infectado = infectado;
    }

    public UsuariosFirebase() {
    }

    public String getPk_idciudad() {
        return pk_idciudad;
    }

    public void setPk_idciudad(String pk_idciudad) {
        this.pk_idciudad = pk_idciudad;
    }

    public UsuariosFirebase(String usuario, String fecnac, int infectado, String iduser, String pass , String pk_idciudad ) {
        this.usuario = usuario;
        this.fecnac = fecnac;
        this.infectado = infectado;
        this.iduser = iduser;
        this.pass = pass;
        this.pk_idciudad =pk_idciudad;
    }

    public UsuariosFirebase(String usuario, String fecnac, int infectado, String iduser, String pass , String pk_idciudad , String longitud , String latitud ) {
        this.usuario = usuario;
        this.fecnac = fecnac;
        this.infectado = infectado;
        this.iduser = iduser;
        this.pass = pass;
        this.pk_idciudad =pk_idciudad;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public UsuariosFirebase(String string, String cursoruserString, String columnName, String iduser, String pass, String pk_idciudad) {
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

    public int isInfectado() {
        return infectado;
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


}


