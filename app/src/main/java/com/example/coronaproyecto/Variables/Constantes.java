package com.example.coronaproyecto.Variables;

import androidx.annotation.IntegerRes;

public class Constantes {

    // VERSION

     public static int versionbd = 8;

    //TABLAS
    public static final String tablausu = "Usuarios";
    public static final String tablaciudades = "Ciudades";
    public static final String tablapaises = "Paises";
    public static final String tablaubicaciones = "Ubicaciones";
    //CAMPOS USUARIOS
    public static final String campofec = "fecnac";
    public static final String nombreusu = "nombreuser";
    public static final String infectadousu = "infectado";
    public static final String iduser = "iduser";
    public static final String passuser = "pass";
    public static final String pkidciudad = "pk_id_ciudad";
    //CAMPOS CIUDADES
    public static final String codigopostal = "codigopostal";
    public static final String nombreciudad = "nombreciudad";
    public static final String idciudad = "idciudad";
    public static final String pk_id_pais ="pk_id_pais";
    //CAMPOS PAISES
    public static final String nombrepais = "nombrepais";
    public static final String idpais = "idpais";


    public static final String insusu = "CREATE TABLE Usuarios ( fecnac VARCHAR(100)," +
            "nombreuser VARCHAR(100) ,  infectado INTEGER,  iduser  VARCHAR(100) PRIMARY KEY ," +
            "pass VARCHAR(100) , pk_id_ciudad  VARCHAR(100) , FOREIGN KEY (pk_id_ciudad) REFERENCES Ciudades (idciudad));";

    public static final String insciudad = "CREATE TABLE  Ciudades  (codigopostal  INT," +
            "nombreciudad VARCHAR(100) ,  idciudad   VARCHAR(100) PRIMARY KEY , pk_id_pais VARCHAR(100) , FOREIGN KEY (pk_id_pais) REFERENCES Paises (idpais));";

    public static final String inspais = "CREATE TABLE Paises ( nombrepais VARCHAR(100) , idpais VARCHAR(100) PRIMARY KEY);";


    // BASE DE DATOS UBICACIONES

    //SENTENCIAS

    public static int versionubicacion = 28;

    // CAMPOS

    public static final String latitud = "latitud";
    public static final String longitud = "longitud";
    public static final String idubicacion = "idubicacion";
    public static final String insubicacion = "CREATE TABLE Ubicaciones " +
            "( latitud VARCHAR(100) , longitud VARCHAR(100) , idubicacion VARCHAR(100));";





}
