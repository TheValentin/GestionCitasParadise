package com.example.gestioncitasparadise.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gestioncitasparadise.R;
import com.example.gestioncitasparadise.encriptacion;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

public class OlvidarContrasena extends AppCompatActivity {



    private TextInputLayout t_dni,t_correo,t_contrasenanueva;
    encriptacion encriptacion=new encriptacion();
    Button b_Cambiar;
    private String url="https://thevalentin.000webhostapp.com/Proyectos/ServidorGestionCitas/RestablecerContrase%C3%B1a.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvidar_contrasena);
        enlazarControlado();
    }



    public void enlazarControlado() {
        t_dni =(TextInputLayout)  findViewById(R.id.txtdniOlvidar);
        t_correo = (TextInputLayout) findViewById(R.id.txtCorreoOlvidar);
        t_contrasenanueva = (TextInputLayout)  findViewById(R.id.txtNuevaContrasenaOlvidar);
        t_contrasenanueva.setPasswordVisibilityToggleEnabled(true);
        b_Cambiar=findViewById(R.id.btnOlvidarContraseña);


        b_Cambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActualizarContrasena();

            }
        });

    }






    private void ActualizarContrasena(){
        String dni= t_dni.getEditText().getText().toString().trim();
        String correo= t_correo.getEditText().getText().toString().trim();
        String nuevacontrasena= t_contrasenanueva.getEditText().getText().toString().trim();

        String encryptedPassword = encriptacion.encryptPassword(nuevacontrasena);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando");

        if (dni.isEmpty()){
            t_dni.getEditText().setError("No puede quedar Vacio");

            return;
        }
        else if (correo.isEmpty()){
            t_correo.getEditText().setError("No puede quedar Vacio");
            return;
        }
        else if(nuevacontrasena.isEmpty()){
            t_contrasenanueva.getEditText().setError("No puede quedar Vacio");
            return;

        }else {
            progressDialog.show();
            StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    String valor=response.trim();

                    if (valor.equalsIgnoreCase("Contraseña Cambiada Correctamente")) {
                        Toast.makeText(OlvidarContrasena.this, "Se cambio contraseña, EXITO", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        startActivity(new Intent(getApplicationContext(), IniciarSessionActivity.class));
                        finish();
                    } else {
                        Toast.makeText(OlvidarContrasena.this, valor, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(OlvidarContrasena.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }){

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String>  params=new HashMap<String,String>();
                    params.put("email",correo);
                    params.put("dni",dni);
                    params.put("nuevacontraseña",encryptedPassword);

                    return params;
                }

            };

            RequestQueue requestQueue= Volley.newRequestQueue( OlvidarContrasena.this);
            requestQueue.add(request);

        }


    }
}