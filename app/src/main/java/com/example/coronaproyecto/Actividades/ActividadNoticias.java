package com.example.coronaproyecto.Actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.coronaproyecto.R;

public class ActividadNoticias extends AppCompatActivity {

    WebView web;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_actividad_noticias);
        web = findViewById(R.id.webnoticias);
        url = "https://www.lavanguardia.com/vida/20200423/48684019600/coronavirus-espana-contagios-muertes-madrid-catalunya-castilla-la-mancha-castilla-leon-estado-alarma-ultimas-noticias-hoy-en-directo.html";
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().getBuiltInZoomControls();
        web.loadUrl(url);
    web.setWebViewClient(new WebViewClient());
    }
}
