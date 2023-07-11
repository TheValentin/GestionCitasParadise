package com.example.gestioncitasparadise;


import androidx.appcompat.app.AppCompatActivity;


import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;

import android.view.View;

import com.example.gestioncitasparadise.actividades.AdministradorMenu;
import com.example.gestioncitasparadise.actividades.doctorMenu;
import com.example.gestioncitasparadise.actividades.pacienteMenu;
import com.example.gestioncitasparadise.login.IniciarSessionActivity;
import com.example.gestioncitasparadise.login.RegistrarActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configuración de SSLContext para ignorar errores de certificado SSL
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) {}

                public void checkServerTrusted(X509Certificate[] chain, String authType) {}

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, null);
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }

        preferenias();
    }
    public void procesoIniciar(View v){
        Intent intent = new Intent(this, IniciarSessionActivity.class);
        startActivity(intent);
    }
    public void procesoRegistrar(View v){
        Intent intent = new Intent(this, RegistrarActivity.class);
        startActivity(intent);
    }
    public void preferenias(){
        SharedPreferences sharedPreferences = getSharedPreferences("mis_preferencias", Context.MODE_PRIVATE);
        boolean isLoggedAdmin = sharedPreferences.getBoolean("isLoggedAdmin", false);
        boolean isLoggedPaciente = sharedPreferences.getBoolean("isLoggedPaciente", false);
        boolean isLoggedDoctor = sharedPreferences.getBoolean("isLoggedDoctor", false);

        if (isLoggedAdmin) {
            // El administrador está iniciado sesión, continuar con la actividad principal del administrador
            Intent intent = new Intent(this, AdministradorMenu.class);
            startActivity(intent);

        } else if (isLoggedPaciente) {
            // El paciente está iniciado sesión, continuar con la actividad principal del paciente
            Intent intent = new Intent(this, pacienteMenu.class);
            startActivity(intent);

        } else if (isLoggedDoctor) {
            // El doctor está iniciado sesión, continuar con la actividad principal del doctor
            Intent intent = new Intent(this, doctorMenu.class);
            startActivity(intent);

        } else {
            // Ningún usuario ha iniciado sesión, redirigir a la pantalla de inicio de sesión
            startActivity(new Intent(this, IniciarSessionActivity.class));
            finish();
        }
    }
    // Declare the launcher at the top of your Activity/Fragment:



}