package com.example.coronaproyecto.Actividades;

import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.coronaproyecto.Adaptador.Adaptador;
import com.example.coronaproyecto.BD.ConexionSqliteHelper;
import com.example.coronaproyecto.Modelos.Ciudades;
import com.example.coronaproyecto.Modelos.Paises;
import com.example.coronaproyecto.Modelos.Usuarios;
import com.example.coronaproyecto.R;
import com.example.coronaproyecto.Variables.Constantes;
import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Recycler extends AppCompatActivity {

    private ArrayList<Paises> listapaises;
    private RecyclerView recicler;
    private Adaptador adapter;
    private ConexionSqliteHelper conn;
    private Ciudades ciudades;
    private ArrayList<Ciudades> listaciudades;
    private Paises paises;
    private String nombrepais;
    private int resultado = 0;
    private Usuarios user;
    private int idinfectado;
    private Presentacion pres;
    private MainActivity main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        getSupportActionBar().hide();
        conn = new ConexionSqliteHelper(this, "BD_Corona.db", null, Constantes.versionbd);
        recicler = findViewById(R.id.recyclerdatos);
        pres = new Presentacion();
        main = new MainActivity();
        main.muestraDatosFirebaseCiudades();
        pres.muestraDatosFirebaseUsuarios();
        recicler.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new Adaptador(ActividadUsuario.listapaises, MainActivity.listaciudades, this);
        adapter.setOnclicklistener(new View.OnClickListener() {
            @Override
            // TE MUESTRA AL PULSAR EL NOMBRE DEL PAIS
            public void onClick(View v) {
                nombrepais = ActividadUsuario.listapaises.get(recicler.getChildAdapterPosition(v)).getNombrepais();
                Snackbar.make(v, ActividadUsuario.listapaises.get(recicler.getChildAdapterPosition(v)).getNombrepais() + " tiene: "
                        + " " + muestraDatos(v) + " infectado/s ", Snackbar.LENGTH_LONG).
                        setTextColor(getResources().getColor(R.color.colorblanco))
                        .show();


            }
        });
        recicler.setAdapter(adapter);

        for (Usuarios usuarios : Presentacion.listadeusuarios) {

            Log.v("usuarios ", "user " + usuarios.getPk_idciudad());

        }
        for (Ciudades ciudades : MainActivity.listaciudades) {

            Log.v("usuarios ", "ciudad " + ciudades.getIdciudad());
        }

    }

    // METODO QUE TE DICE LOS USUARIOS QUE HAY INFECTADOS
    public int muestraDatos(View v) {
        int contador = 1;
        idinfectado = Presentacion.listadeusuarios.get(recicler.getChildAdapterPosition(v)).getInfectado();

        if (idinfectado == 1) {

            contador = contador + 1;

        }

        return idinfectado;

    }

/*
    public void rellenarecycler() {
        try {
            SQLiteDatabase db = conn.getReadableDatabase();
            /*
            Cursor cursorciudad = db.rawQuery("SELECT * FROM CIUDADES", null);
            if (cursorciudad != null) {
                while (cursorciudad.moveToNext()) {
                    ciudades=new Ciudades();
                    ciudades.setCodpostal(cursorciudad.getInt(0));
                    ciudades.setNombre(cursorciudad.getString(1));
                    ciudades.setIdciudad(cursorciudad.getString(2));
                    ciudades.setPk_id_pais(cursorciudad.getString(3));
                }
                cursorciudad.close();


            }


            sentencia sql : SELECT  count (infectado) from Usuarios
             inner join Ciudades on Usuarios.pk_id_ciudad=Ciudades.idciudad
             inner JOIN Paises on Ciudades.pk_id_pais = Paises.idpais where infectado = 1



            Cursor cursorpais = db.rawQuery("SELECT * FROM Paises INNER JOIN Ciudades INNER JOIN Usuarios WHERE idpais = pk_id_pais and idciudad = pk_id_ciudad and infectado =1;", null);
            if (cursorpais != null) {
                while (cursorpais.moveToNext()) {
                    paises = new Paises();
                    paises.setNombrepais(cursorpais.getString(0));
                    paises.setIdpais(cursorpais.getString(1));
                    listapaises.add(paises);
                }
                cursorpais.close();
            }
            Cursor cursorciudad = db.rawQuery("SELECT * FROM CIUDADES INNER JOIN Usuarios WHERE idciudad = pk_id_ciudad and infectado = 1;", null);
            if (cursorciudad != null) {
                while (cursorciudad.moveToNext()) {
                    ciudades = new Ciudades();
                    ciudades.setCodpostal(cursorciudad.getInt(0));
                    ciudades.setNombreciudad(cursorciudad.getString(1));
                    ciudades.setIdciudad(cursorciudad.getString(2));
                    ciudades.setPk_id_pais(cursorciudad.getString(3));
                    listaciudades.add(ciudades);
                }
                cursorciudad.close();
            }


            conn.close();


        } catch (SQLException o) {
            Toast.makeText(this, "Error en la base de datos ", Toast.LENGTH_SHORT).show();
        }
    }







    "SELECT * FROM CIUDADES +
                    "inner join Ciudades on Usuarios.pk_id_ciudad=Ciudades.idciudad\n" +
                    "inner JOIN Paises on Ciudades.pk_id_pais = Paises.idpais where infectado = 1






    public int consultainfectados() {

        try {
            SQLiteDatabase bd = conn.getReadableDatabase();
            String sentencia = "SELECT  count (infectado) from Usuarios\n" +
                    "inner join Ciudades on Usuarios.pk_id_ciudad=Ciudades.idciudad\n" +
                    "inner JOIN Paises on Ciudades.pk_id_pais = Paises.idpais where infectado = 1 and nombrepais = " + "'" + nombrepais + "'";
            Cursor infectado = bd.rawQuery(sentencia, null);


            if (infectado.moveToFirst()) {

                resultado = infectado.getInt(0);

            }
            infectado.close();
            bd.close();
            conn.close();


        } catch (SQLException o) {


            Toast.makeText(this, "Error con la base de datos", Toast.LENGTH_SHORT).show();

        }

        return resultado;
    }
}


 */

}
