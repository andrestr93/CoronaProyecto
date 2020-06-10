package com.example.coronaproyecto.Actividades;

// IMPORTANTE HACE FALTA VINCULARLO CON FIREBASE QUE NO SE TE OLVIDE.

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coronaproyecto.BD.ConexionSqliteHelper;
import com.example.coronaproyecto.Modelos.Ciudades;
import com.example.coronaproyecto.Modelos.Paises;
import com.example.coronaproyecto.Modelos.Usuarios;
import com.example.coronaproyecto.R;
import com.example.coronaproyecto.Variables.Constantes;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.UUID;

public class ActividadRegistro extends AppCompatActivity {

    EditText usuario, pass, poblacion, codigopostal, pais, fechnac;
    Switch srespirar, sttest, spaises;
    Button butregistro;
    FirebaseDatabase firebasedata;
    DatabaseReference bdpaises;
    DatabaseReference bdciudades;
    DatabaseReference bdusuarios;
    private ArrayList<Usuarios> listausuarios;
    private ArrayList<Ciudades> listaciudades;
    private ArrayList<Paises> listapaises;
    private ConexionSqliteHelper sqlite;
    private SQLiteDatabase conx;
    public static Usuarios usuarios;
    private Paises paises;
    private Ciudades ciudades;
    private Uri path;
    private FusedLocationProviderClient fusedLocationClient;
    private Double longitud;
    private Double latitud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_registro2);
        getSupportActionBar().hide();
        butregistro = findViewById(R.id.butregistro);
        srespirar = findViewById(R.id.strespirar);
        sttest = findViewById(R.id.stfiebre);
        spaises = findViewById(R.id.stpaises);
        usuario = findViewById(R.id.editnombre);
        pass = findViewById(R.id.editpass);
        poblacion = findViewById(R.id.editpoblacion);
        codigopostal = findViewById(R.id.editcodigo);
        pais = findViewById(R.id.editpais);
        fechnac = findViewById(R.id.editfecha);
        sqlite = new ConexionSqliteHelper(this, "BD_Corona.db", null, Constantes.versionbd);
        firebasedata = FirebaseDatabase.getInstance();
        listausuarios = new ArrayList();
        listaciudades = new ArrayList();
        listapaises = new ArrayList();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


    }


    // BOTON DE REGISTRAR
    public void botonReg(View v) {


        // METODO INSERTA DATO TE DEVUELVE UN DISTINTO DE MENOS 1 CUANDO HAY UN USUARIO REGISTRADO
        try {
            if (insertadato(v) != -1) {

                Snackbar.make(v, "Usuario Registrado correctamente", Snackbar.LENGTH_SHORT)
                        .show();

            }

        } catch (NumberFormatException e) {

            Toast.makeText(this, "Por favor verifique bien los campos ", Toast.LENGTH_SHORT).show();

        }


    }

    // METODO INSERTADATO
    public long insertadato(View v) {
        long regusuarios = -1;

// COGEMOS LOS EDITTEXT DEL FORMULARIO Y CREAMOS LAS VARIABLES
        String nombreuser = usuario.getText().toString().toUpperCase().trim();
        String contrasena = pass.getText().toString().toUpperCase().trim();
        String poblacionuser = poblacion.getText().toString().toUpperCase().trim();
        String paisuser = pais.getText().toString().toUpperCase().trim();
        int codigopostuser = Integer.parseInt(codigopostal.getText().toString().trim());
        String fech = fechnac.getText().toString().trim();

        // HACEMOS LAS COMPROBACIONES POR SI HAY ALGUN CAMPO VACIO
        if (nombreuser.equals("")) {

            usuario.setError("Campo obligatorio");

        } else if ((contrasena.equals(""))) {

            pass.setError("Campo obligatorio");

        } else if (poblacionuser.equals("")) {

            poblacion.setError("Campo obligatorio");

        } else if (codigopostal.getText().equals("")) {

            codigopostal.setError("Campo obligatorio");

        } else if (paisuser.equals("")) {

            pais.setError("Campo obligatorio");

        } else if (fech.equals("")) {

            fechnac.setError("Campo obligatorio");

        }
        // DESPUES DE HACER LAS OCMPROBACIONES UN TRY CATCH
        try {

// GENERAMOS EL ID ALEATORIO
            String iduser = UUID.randomUUID().toString();

            String idciudad = UUID.randomUUID().toString();
            String idpais = UUID.randomUUID().toString();

            // INICIALIZAMOS OBJETOS CON EL CONSTRUCTOR Y LOS METEMOS EN UN ARRAY DESPUES
            // VAMOS METIENDO LOS DATOS EN LAS TABLAS CORRESPONDIENTES EN FIREBASE

            verificaInfectado();
            // CREAMOS EL OBJETO USUARIOS CON LOS VALORES DE LOS CAMPOS Y LA COMPROBACION SI ESTA INFECTADO O NO
            usuarios = new Usuarios(nombreuser, fech, verificaInfectado(), iduser, contrasena, idciudad);
            Log.v("verifica infectado", "" + verificaInfectado());
            // METO EN EL ARRYLIST
            listausuarios.add(usuarios);
            // HACEMOS LA MISMA OPERACION PERO EN ESTE CASO CON CIUDADES Y LUEGO CON PAISES
            ciudades = new Ciudades(codigopostuser, poblacionuser, idciudad, idpais);
            listaciudades.add(ciudades);
            paises = new Paises(paisuser, idpais);
            listapaises.add(paises);

            bdpaises = firebasedata.getReference("Datos").child("Paises").child(idpais);
            bdpaises.setValue(paises);
            // METEMOS LOS DATOS EN FIREBASE
            bdciudades = firebasedata.getReference("Datos").child("Ciudades").child(idciudad);
            bdciudades.setValue(ciudades);
            bdusuarios = firebasedata.getReference("Datos").child("Usuarios").child(iduser);
            bdusuarios.setValue(usuarios);

            conx = sqlite.getWritableDatabase();


            // HACEMOS LA CONEXION CON SQLITE Y VAMOS METIENDO LOS DATOS EN LAS TABLAS INTERNAS
            if (conx != null) {

                ContentValues valorpais = new ContentValues();
                valorpais.put(Constantes.nombrepais, paises.getNombrepais());
                valorpais.put(Constantes.idpais, paises.getIdpais());
                regusuarios = conx.insert(Constantes.tablapaises, null, valorpais);

                ContentValues valorciudad = new ContentValues();
                valorciudad.put(Constantes.codigopostal, ciudades.getCodpostal());
                valorciudad.put(Constantes.nombreciudad, ciudades.getNombreciudad());
                valorciudad.put(Constantes.idciudad, ciudades.getIdciudad());
                valorciudad.put(Constantes.pk_id_pais, ciudades.getPk_id_pais());

                regusuarios = conx.insert(Constantes.tablaciudades, null, valorciudad);

                ContentValues values = new ContentValues();
                values.put(Constantes.campofec, usuarios.getFecnac());
                values.put(Constantes.iduser, usuarios.getIduser());
                values.put(Constantes.infectadousu, usuarios.getInfectado());
                values.put(Constantes.nombreusu, usuarios.getUsuario());
                values.put(Constantes.passuser, usuarios.getPass());
                values.put(Constantes.pkidciudad, usuarios.getPk_idciudad());


                regusuarios = conx.insert(Constantes.tablausu, null, values);


                conx.close();


                // LLAMAMOS A ESTE METODO QUE VACÍA LOS CAMPOS
                limpiatxts();


            } else {


                Toast.makeText(this, "fallo al registrar datos", Toast.LENGTH_SHORT).show();
            }

            // CATCH NECESARIO PARA CONTROLAR LOS CAMPOS VACIOS
        } catch (NumberFormatException o) {

            if (usuario.getText().toString().equals("") || pass.getText().toString().equals("") || poblacion.getText().toString().equals("")) {

                Toast.makeText(this, "Campos vacios.Por favor complete bien los campos ", Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(this, "Error en codigo postal. Reviselo ", Toast.LENGTH_SHORT).show();

            }


        }
        return regusuarios;


    }

    // COMPRUEBA SI EL USUARIO ESTA INFECTADO O NO
    public int verificaInfectado() {

        int compruebainfectado = 0 ;

        if (sttest.isChecked() && spaises.isChecked() && srespirar.isChecked()) {

            compruebainfectado = 1;
        } else {

            compruebainfectado = 0;
        }

       return compruebainfectado;

    }


    public void limpiatxts() {
        pass.setText("");
        poblacion.setText("");
        codigopostal.setText("");
        usuario.setText("");
        pais.setText("");
        fechnac.setText("");
        sttest.setChecked(false);
        spaises.setChecked(false);
        srespirar.setChecked(false);
    }


}



