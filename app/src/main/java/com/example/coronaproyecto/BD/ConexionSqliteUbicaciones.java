package com.example.coronaproyecto.BD;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.coronaproyecto.Variables.Constantes;

public class ConexionSqliteUbicaciones extends SQLiteOpenHelper {
    public ConexionSqliteUbicaciones(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }





    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Constantes.insubicacion);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS Ubicaciones");
        onCreate(db);

    }
}
