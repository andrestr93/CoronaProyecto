package com.example.coronaproyecto.Actividades;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coronaproyecto.BD.ConexionSqliteUbicaciones;
import com.example.coronaproyecto.Modelos.Ciudades;
import com.example.coronaproyecto.Modelos.Paises;
import com.example.coronaproyecto.Modelos.Ubicacion;
import com.example.coronaproyecto.Modelos.Usuarios;
import com.example.coronaproyecto.R;
import com.example.coronaproyecto.Variables.Constantes;
import com.google.android.gms.common.util.concurrent.HandlerExecutor;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.Random;
import java.util.UUID;

import okhttp3.internal.cache.DiskLruCache;

import static com.example.coronaproyecto.Actividades.ActividadRegistro.usuarios;

// FUNCIONAMIENTO DE LA APP
/*
FUNCIONES:

 - REGISTRO DE USUARIO
 - LOGIN DE USUARIO CON IDENTIFICACION POR HUELLA DACTILAR
 -RECICLER VIEW CON LA GENTE EN TOTAL INFECTADA POR PAIS DE PROCEDENCIA
 - PANEL DE USUARIO CON TODA LA INFORMACION DEL MISMO
 -BOTON QUE TE ENLAZA A UNA WEB EXTERNA CON TODA LA INFORMACION ACTUAL DEL VIRUS EN TIEMPO REAL
 -
 -INFORMACÍON TANTO BD DE SQLITE COMO EN FIREBASE CON PERSISTENCIA DE DATOS

 */

public class Presentacion extends AppCompatActivity {

    private VideoView presentacion;
    private String path;
    private ImageView imagen;
    private FusedLocationProviderClient fusedLocationClient;
    private String longitud;
    private String latitud;
    FirebaseDatabase firebasedata;
    DatabaseReference bdubicacion;
    private ConexionSqliteUbicaciones sqliteubicacion;
    private SQLiteDatabase conx;
    private Ubicacion ubicacionessentencia;
    private Ubicacion ubicaciones;
    DatabaseReference bdpaises;
    private double latitude;
    private double longitude;
    private ArrayList<Ubicacion> listaubicasentencia;
    private String idubi;
    private DatabaseReference databaseReference;
    private ArrayList<Ubicacion> listaubicaciones;
    public static ArrayList<Usuarios> listadeusuarios;
    private Usuarios usuarios;
    private Location posicionActual;
    private Ubicacion pruebaUbicacion = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentacion);
        getSupportActionBar().hide();
        sqliteubicacion = new ConexionSqliteUbicaciones(this, "BD_Ubicacion.db", null, Constantes.versionubicacion);
        presentacion = findViewById(R.id.videopres);
        imagen = findViewById(R.id.imageView);
        firebasedata = FirebaseDatabase.getInstance();
        path = "android.resource://" + getPackageName() + "/" + R.raw.presentacion;
        Uri uri = Uri.parse(path);
        listaubicaciones = new ArrayList<>();
        listaubicasentencia = new ArrayList<>();
        presentacion.setVideoURI(uri);
        presentacion.setMediaController(new MediaController(this));
        presentacion.requestFocus();
        presentacion.stopPlayback();
        listadeusuarios = new ArrayList<Usuarios>();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        muestraDatosFirebaseUsuarios();

    }

    // METODO QUE RECOGE LOS DATOS DE FIREBASE
    public void muestraDatosFirebaseUsuarios() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Datos").child("Usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listadeusuarios.clear();
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    databaseReference.child("Datos").child("Usuarios").child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                usuarios = dataSnapshot.getValue(Usuarios.class);
                                listadeusuarios.add(usuarios);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    // LA LOCALIZACION SE ACTUALIZA CADA HORA

/*
    // METODO QUE RECOGE LATITUD Y LONGITUD Y LO METE EN FIREBASE
    public void obtieneUbicacionFireBase() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            idubi = UUID.randomUUID().toString();
                            posicionActual = location;
                            longitud = String.valueOf(posicionActual.getLongitude());
                            latitud = String.valueOf(posicionActual.getLatitude());
                            ubicaciones = new Ubicacion(latitud, longitud, idubi);
                            listaubicaciones.add(ubicaciones);

                        } else {

                            Toast.makeText(Presentacion.this, "fallo", Toast.LENGTH_SHORT).show();
                        }


                    }


                });


    }

 */


    // --------------ACTIVIDADES--------------------

    public void lanzalogueo(View view) {
        logueo();
    }


    public void logueo() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void webnoticias() {

        Intent intent = new Intent(this, ActividadNoticias.class);
        startActivity(intent);

    }

    public void lanzaweb(View view) {
        webnoticias();

    }
// ----------------------------------------------------------------
/*
    // METODO QUE GUARDA LAS UBICACIOONES EN SQLITE
    public long guardaUbicaciones() {
        long regubicacion = -1;
        // HACEMOS LA CONEXION CON SQLITE Y VAMOS METIENDO LOS DATOS EN LAS TABLAS INTERNAS
        for (Ubicacion ubi : listaubicasentencia) {
            if (!ubi.getLatitud().trim().equals(latitud) && !ubi.getLongitud().trim().equals(longitud)) {
                pruebaUbicacion = ubi;
                regubicacion = 1;
                break;

            }


        }

/*
                if (conx != null) {
                    ContentValues valorubicacion = new ContentValues();
                    valorubicacion.put(Constantes.longitud, longitud);
                    valorubicacion.put(Constantes.latitud, latitud);
                    valorubicacion.put(Constantes.idubicacion, idubi);
                    regubicacion = conx.insert(Constantes.tablaubicaciones, null, valorubicacion);
                }



        return regubicacion;
    }
 */






/*
    public ArrayList muestraUbicacion() {
        try {
            SQLiteDatabase db = sqliteubicacion.getReadableDatabase();
            Cursor cursorubica = db.rawQuery("SELECT * FROM UBICACIONES;", null);
            if (cursorubica != null) {
                while (cursorubica.moveToNext()) {
                    ubicacionessentencia = new Ubicacion();
                    ubicacionessentencia.setLongitud(cursorubica.getString(1));
                    ubicacionessentencia.setLatitud(cursorubica.getString(0));
                    ubicacionessentencia.setId(cursorubica.getString(2));
                    listaubicasentencia.add(ubicacionessentencia);
                }
                cursorubica.close();
            }
        } catch (SQLException i) {

            Toast.makeText(this, "fallo en la conexion", Toast.LENGTH_SHORT).show();

        }

        return listaubicasentencia;

    }

 */

/*
    public void guardaDatos() {
        SharedPreferences pref = getSharedPreferences("datos", Context.MODE_PRIVATE);
        String idubicacion = idubi;
        latitude = ubicaciones.getLatitud();
        longitude = ubicaciones.getLongitud();
        Toast.makeText(this, " longitud" + ubicaciones.getLongitud() + "latitud " + ubicaciones.getLatitud(), Toast.LENGTH_SHORT).show();
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("ubicacion", idubicacion);
        editor.putString("latitud", String.valueOf(latitude));
        editor.putString("longitud", String.valueOf(longitude));
        editor.commit();


    }



    /*

    public void cargaDatos() {
        SharedPreferences pref = getSharedPreferences("datos", Context.MODE_PRIVATE);

        String idubicacion = pref.getString("ubicacion", "No existe la informacion");
        String latitud = pref.getString("latitud", String.valueOf("No existe latitud"));
        String longitud = pref.getString("longitud", String.valueOf("No existe longitud"));

        /*
        if (pref.getLong("latitud", Long.parseLong("no se encuentra datos")) == latitude) {

            Toast.makeText(this, "es igual ", Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(this, "no son iguales ", Toast.LENGTH_SHORT).show();
        }
    }

     */

}
