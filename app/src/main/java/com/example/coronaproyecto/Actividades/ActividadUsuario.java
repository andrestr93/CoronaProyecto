package com.example.coronaproyecto.Actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coronaproyecto.BD.ConexionSqliteHelper;
import com.example.coronaproyecto.Modelos.Ciudades;
import com.example.coronaproyecto.Modelos.Paises;
import com.example.coronaproyecto.Modelos.Usuarios;
import com.example.coronaproyecto.R;
import com.example.coronaproyecto.Variables.Constantes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActividadUsuario extends AppCompatActivity {

    String datoidpkuser, datonombre, datofech, datouser, datociudad, datopais, datopost;
    TextView ciudad, pais, infec, nombre, fec;
    private ConexionSqliteHelper conn;
    private Ciudades ciudades;
    private Paises paises;
    FirebaseDatabase firebasedata;
    private DatabaseReference databaseReference;
    public static ArrayList<Paises> listapaises;
    public static Paises paisesConsulta = null;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_actividad_usuario);
        ciudades = new Ciudades();
        conn = new ConexionSqliteHelper(this, "BD_Corona.db", null, Constantes.versionbd);
        listapaises = new ArrayList<>();
        firebasedata = FirebaseDatabase.getInstance();
        pais = findViewById(R.id.viewpaisuser);
        ciudad = findViewById(R.id.viewciudad);
        nombre = findViewById(R.id.viewnombreuser);
        fec = findViewById(R.id.viewfechuser);
        infec = findViewById(R.id.viewinfectadouser);
        datoidpkuser = getIntent().getStringExtra("datopk");
        datonombre = getIntent().getStringExtra("datonombre");
        datofech = getIntent().getStringExtra("datofech");
        datouser = getIntent().getStringExtra("datouser");
        datociudad = getIntent().getStringExtra("datociudad");
        datopost = getIntent().getStringExtra("datopost");
        nombre.setText("USUARIO: " + datonombre);
        fec.setText("FECHA: " + datofech);
        ciudad.setText("CIUDAD: " + datociudad);
        muestraFirebasePaises();
        muestradatos();

        if (MainActivity.busquedausuario.getInfectado() == 1) {

            infec.setText("INFECTADO");

        } else {

            infec.setText("NO INFECTADO");
        }


        // pais.setText("PAIS: " + datopais);


        //obtienedatos();


    }


    public void muestradatos() {

        for (Paises paises : listapaises) {

            if (MainActivity.busquedaciudad.getPk_id_pais().equals(paises.getIdpais())) {
                paisesConsulta = paises;

                break;

            }
            if (paisesConsulta != null) {

                datopais = paisesConsulta.getNombrepais();
            }

        }

    }


    /*

     public int muestraDatos(String nombre, String pass) {
        int comprueba = 0;

// hacemos un bucle de lista de usuarios
        for (Usuarios usu : Presentacion.listadeusuarios) {

            Log.v("comprueba ", "usuarios " + usu.getUsuario() + " pass " + usu.getPass());
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







     */
    // RECOGE EN FIREBASE LOS PAISES
    public ArrayList muestraFirebasePaises() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Datos").child("Paises").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listapaises.clear();
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    databaseReference.child("Datos").child("Paises").child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                paises = dataSnapshot.getValue(Paises.class);
                                listapaises.add(paises);
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

        return listapaises;
    }

// AL PULSAR SOBRE EL BOTON DE LA LISTA TE LANZA AL RECYCLER
    public void lista() {


        Intent intent = new Intent(this, Recycler.class);
        startActivity(intent);

    }

    public void onClick(View view) {

        lista();
    }


}




    /*
        public void obtienedatos() {

        try {
            SQLiteDatabase db = conn.getReadableDatabase();
            Cursor cursorciudad = db.rawQuery("SELECT * FROM CIUDADES WHERE IDCIUDAD =" + "'" + datoidpkuser + "'", null);
            if (cursorciudad != null) {
                while (cursorciudad.moveToNext()) {
                    ciudades.setCodpostal(cursorciudad.getInt(0));
                    ciudades.setNombreciudad(cursorciudad.getString(1));
                    ciudades.setIdciudad(cursorciudad.getString(2));
                    ciudades.setPk_id_pais(cursorciudad.getString(3));
                }
                cursorciudad.close();


            }
            Cursor cursorpais = db.rawQuery("SELECT * FROM PAISES WHERE IDPAIS =" + "'" + ciudades.getPk_id_pais() + "'", null);
            if (cursorpais != null) {
                while (cursorpais.moveToNext()) {
                    paises.setNombrepais(cursorpais.getString(0));
                    paises.setIdpais(cursorpais.getString(1));
                }
                cursorpais.close();
            }
            conn.close();


            ciudad.setText("CIUDAD: " + ciudades.getNombreciudad());
            pais.setText("PAIS: " + paises.getNombrepais());

        } catch (Exception o) {

            Toast.makeText(this, "Error con la base de datos", Toast.LENGTH_SHORT).show();

        }
    }

     */
