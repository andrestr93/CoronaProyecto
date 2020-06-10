package com.example.coronaproyecto.Actividades;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;

import com.example.coronaproyecto.BD.ConexionSqliteHelper;
import com.example.coronaproyecto.Modelos.Ciudades;
import com.example.coronaproyecto.Modelos.Paises;
import com.example.coronaproyecto.Modelos.Usuarios;
import com.example.coronaproyecto.R;
import com.example.coronaproyecto.Variables.Constantes;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.Objects;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText nombre, contrasena;
    private ArrayList<Usuarios> listauser;
    private Usuarios usuarios;
    private ConexionSqliteHelper conn;
    private Handler handler;
    private int resultado = 0;
    private BiometricManager biometricManager;
    private Executor executor;
    private ImageButton buthuella;
    private Intent intent;
    private Presentacion pres;
    private DatabaseReference databaseciudades;
    public static Ciudades ciudades;
    public static ArrayList<Ciudades> listaciudades;
    public static Usuarios busquedausuario = null;
    public static Ciudades busquedaciudad = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        pres = new Presentacion();
        conn = new ConexionSqliteHelper(this, "BD_Corona.db", null, Constantes.versionbd);
        listauser = null;
        usuarios = new Usuarios();
        listaciudades = new ArrayList<>();
        nombre = findViewById(R.id.username);
        contrasena = findViewById(R.id.userpass);
        buthuella = findViewById(R.id.buthuella);
        handler = new Handler();
        executor = new Executor() {


            @Override
            public void execute(Runnable command) {
                handler.post(command);
            }
        };
        muestraDatosFirebaseCiudades();


    }

    // LANZA ACTIVIDADES
    public void lanzalogueo(View view) {

        logueo();


    }


    public void logueo() {


        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }


    public void registro() {


        Intent intent = new Intent(this, ActividadRegistro.class);
        startActivity(intent);
    }

    // SWITCH PARA CONTROLAR TANTO EL BOTON DE LA HUELLA COMO EL BOTON DE REGISTRO
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.butregistro:
                registro();
                break;
            case R.id.buthuella:

                biometricManager = BiometricManager.from(this);
                switch (biometricManager.canAuthenticate()) {
                    case BiometricManager.BIOMETRIC_SUCCESS:
                        showBiometricPrompt(v);
                        break;
                    case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:

                        break;
                    case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:

                        Snackbar.make(v, "Accion no compatible con el terminal", Snackbar.LENGTH_LONG)
                                .setActionTextColor(getResources().getColor(R.color.gris4))
                                .setAction("Aceptar", new View.OnClickListener() {


                                    @Override
                                    public void onClick(View v) {

                                        recogeDatos(v);
                                    }


                                })
                                .show();

                        break;
                }


        }


    }


    private void showBiometricPrompt(View v) {
        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autentificacion con huella digital ")
                .setNegativeButtonText("Cancelar")
                .setConfirmationRequired(false)
                .build();

        BiometricPrompt biometricPrompt = new BiometricPrompt(MainActivity.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                        "Error al hacer la autentificacion: " + errString, Toast.LENGTH_SHORT)
                        .show();

            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                BiometricPrompt.CryptoObject authenticatedCryptoObject =
                        result.getCryptoObject();
                recogeDatos(v);

                // User has verified the signature, cipher, or message
                // authentication code (MAC) associated with the crypto object,
                // so you can use it in your app's crypto-driven workflows.
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Autentificacion fallida",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        // Displays the "log in" prompt.
        biometricPrompt.authenticate(promptInfo);
    }


    // METODO QUE RECOGE LOS DATOS DE FIREBASE
    public void muestraDatosFirebaseCiudades() {
        databaseciudades = FirebaseDatabase.getInstance().getReference();
        databaseciudades.child("Datos").child("Ciudades").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    databaseciudades.child("Datos").child("Ciudades").child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.exists()) {
                                ciudades = dataSnapshot.getValue(Ciudades.class);
                                listaciudades.add(ciudades);
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

    public int muestraDatos(String nombre, String pass) {
        int comprueba = 0;

// hacemos un bucle de lista de usuarios
        for (Usuarios usu : Presentacion.listadeusuarios) {

// COMPROBAMOS QUE CUANDO EL NOMBRE DE USUARIO QUE ESCRIBA EL USUARIO COINCIDA CON EL ARRAY
            if (usu.getUsuario().equals(nombre) && (usu.getPass().equals(pass))) {
                busquedausuario = usu;
// SI ES ASI IGUALAMOS UN OBJETO DEL MISMO TIPO
                comprueba = 1;


                break;
            }
        }
        if (busquedausuario != null) {

            for (Ciudades ciudades : listaciudades) {

                if (ciudades.getIdciudad().equals(busquedausuario.getPk_idciudad())) {

                    busquedaciudad = ciudades;

                }
            }
        } else {
            comprueba = 0;
        }


        return comprueba;
    }

    // METODO QUE RECOGE LOS DATOS DEL REGISTRO PARA POSTERIORMENTE MOSTRARLO EN LA ACTIVIDAD DE USUARIO
    public void recogeDatos(View v) {
        String nombreuser = nombre.getText().toString().toUpperCase();
        String passuser = contrasena.getText().toString().toUpperCase();

        if (!nombreuser.equals("") || !passuser.equals("")) {

            if (muestraDatos(nombreuser, passuser) == 1) {
                intent = new Intent(this, ActividadUsuario.class);
                intent.putExtra("datopk", busquedausuario.getPk_idciudad());
                intent.putExtra("datonombre", busquedausuario.getUsuario());
                intent.putExtra("datofech", busquedausuario.getFecnac());
                intent.putExtra("datoinfectado", busquedausuario.getInfectado());
                intent.putExtra("datociudad", busquedaciudad.getNombreciudad());
                intent.putExtra("datopost", busquedaciudad.getCodpostal());
                startActivity(intent);
                nombre.setText("");
                contrasena.setText("");


            } else {

                Snackbar.make(v, "Usuario o contraseña incorrectos", Snackbar.LENGTH_SHORT)
                        .show();
            }
        } else {
            Snackbar.make(v, "Campos o campo vacios .Escriba las credenciales", Snackbar.LENGTH_SHORT)
                    .show();

        }

    }


}



/*
    public int muestradatos(String nombreusuario, String passuser) {
        int resultado = 0;
        listauser = new ArrayList();
        SQLiteDatabase db = conn.getReadableDatabase();
        String sentencia = "SELECT * FROM USUARIOS WHERE nombreuser =" + " '" + nombreusuario + "' " + " AND PASS =" + "'" + passuser + "'";
        Cursor cursor = db.rawQuery(sentencia, null);
        Log.v("sql", "" + sentencia);

        do {
            if (cursor.moveToFirst()) {

                usuarios.setFecnac(cursor.getString(0));
                usuarios.setUsuario(cursor.getString(1));
                usuarios.setIduser(cursor.getString(3));
                usuarios.setPass(cursor.getString(4));
                usuarios.setInfectado(cursor.getInt(2));
                usuarios.setPk_idciudad(cursor.getString(7));

                resultado = 1;

            } else {
                resultado = 0;
            }

        } while (cursor.moveToNext());

        cursor.close();
        conn.close();



        /*
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                usuarios.setFecnac(cursor.getString(0));
                Log.v("datofecha", "" + usuarios.getFecnac());
                usuarios.setUsuario(cursor.getString(1));
                usuarios.setIduser(cursor.getString(3));
                usuarios.setPass(cursor.getString(4));
                compruebainfectado = usuarios.isInfectado();
                usuarios.setPk_idciudad(cursor.getString(5));
            }
            resultado = 1;
        } else {
            resultado = 0;

        }


        return resultado;

    }

 */
