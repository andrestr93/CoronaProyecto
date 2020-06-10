package com.example.coronaproyecto.Adaptador;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coronaproyecto.Actividades.ActividadRegistro;
import com.example.coronaproyecto.Modelos.Ciudades;
import com.example.coronaproyecto.Modelos.Paises;
import com.example.coronaproyecto.R;
import com.squareup.picasso.Picasso;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder> implements View.OnClickListener {

    ArrayList<Paises> listapaises = new ArrayList();
    ArrayList<Ciudades>listaciudades = new ArrayList();
    private View.OnClickListener listener;
    private Context context;




    public Adaptador(ArrayList<Paises> listapaises, ArrayList<Ciudades> listaciudades,  Context context) {
        this.listapaises = listapaises;
        this.listaciudades = listaciudades;
        this.context = context;


    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.pais.setText("Pais: " + listapaises.get(position).getNombrepais());
        holder.ciudad.setText("Ciudad: " + listaciudades.get(position).getNombreciudad());


    }

    @Override
    public int getItemCount() {
        return listapaises.size();

    }


    public void setOnclicklistener(View.OnClickListener listener) {

        this.listener = listener;
    }

    @Override
    public void onClick(View v) {

        if (listener != null){

            listener.onClick(v);
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView pais;
        TextView ciudad;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pais = itemView.findViewById(R.id.texto_pais);
            ciudad = itemView.findViewById(R.id.texto_ciudad);




        }


    }

}

