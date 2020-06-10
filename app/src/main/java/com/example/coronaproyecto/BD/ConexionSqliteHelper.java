package com.example.coronaproyecto.BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.coronaproyecto.Modelos.Ciudades;
import com.example.coronaproyecto.Modelos.Paises;
import com.example.coronaproyecto.Modelos.Usuarios;
import com.example.coronaproyecto.Variables.Constantes;


public class ConexionSqliteHelper extends SQLiteOpenHelper {




    public ConexionSqliteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // CREAMOS LA BASE DE DATOS CON LAS TABLAS CORRESPONDIENTES
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Constantes.insusu);
        db.execSQL(Constantes.insciudad);
        db.execSQL(Constantes.inspais);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS Usuarios");
        db.execSQL("DROP TABLE IF EXISTS Ciudades");
        db.execSQL("DROP TABLE IF EXISTS Paises");
        onCreate(db);


    }
}
