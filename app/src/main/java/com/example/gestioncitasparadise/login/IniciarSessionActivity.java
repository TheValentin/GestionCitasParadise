package com.example.gestioncitasparadise.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gestioncitasparadise.R;
import com.example.gestioncitasparadise.actividades.AdministradorMenu;
import com.example.gestioncitasparadise.actividades.DoctorMenu;
import com.example.gestioncitasparadise.actividades.pacienteMenu;
import com.example.gestioncitasparadise.encriptacion;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

public class IniciarSessionActivity extends AppCompatActivity {

    private TextInputLayout txtUsuario, txtContraseña;
    encriptacion encriptacion  = new encriptacion();


    String url = "https://thevalentin.000webhostapp.com/Proyectos/ServidorGestionCitas/Login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_session);
        enlazarControlado();

    }

    private void enlazarControlado() {
        txtUsuario = (TextInputLayout) findViewById(R.id.txtUsuario);
        txtContraseña = (TextInputLayout) findViewById(R.id.txtContrasena);
        txtContraseña.setPasswordVisibilityToggleEnabled(true);

    }

    public void CambiarContraseña(View v) {
        Intent intent = new Intent(this, OlvidarContrasena.class);
        startActivity(intent);
    }



    public void procesoRegistrar(View v) {
        Intent intent = new Intent(this, RegistrarActivity.class);
        startActivity(intent);
    }

    public void login(View v) {
        String email = txtUsuario.getEditText().getText().toString().trim();
        String contraseña = txtContraseña.getEditText().getText().toString().trim();

        String encryptedPassword = encriptacion.encryptPassword(contraseña);

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // El correo electrónico no tiene el formato correcto
            txtUsuario.setError("Ingrese un correo electrónico válido");
            return;
        }

        if (email.equals("")) {
            txtUsuario.getEditText().setError("No puede quedar Vacio");
            Toast.makeText(this, "Ingrese su correo", Toast.LENGTH_SHORT).show();
        } else if (contraseña.equals("")) {
            Toast.makeText(this, "Ingrese su contraseña", Toast.LENGTH_SHORT).show();
        } else {


            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Por favor espera...");

            progressDialog.show();

            StringRequest request = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            progressDialog.dismiss();

                            String valor=response.trim();
                            SharedPreferences sharedPreferences = getSharedPreferences("mis_preferencias", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();


                            if (valor.equalsIgnoreCase("inicio de sesión exitoso como administrador")) {


                                txtUsuario.getEditText().setText("");
                                txtContraseña.getEditText().setText("");


                                editor.putBoolean("isLoggedAdmin", true); // Estado de inicio de sesión para el rol de administrador
                                // Repite este paso para los otros roles (por ejemplo, "isLoggedPaciente", "isLoggedDoctor")

                                startActivity(new Intent(IniciarSessionActivity.this,AdministradorMenu.class));


                            } else if (valor.equalsIgnoreCase("inicio de sesión exitoso como paciente")) {
                                // Acciones para un inicio de sesión exitoso como cliente



                                txtUsuario.getEditText().setText("");
                                txtContraseña.getEditText().setText("");


                                editor.putBoolean("isLoggedPaciente", true); // Estado de inicio de sesión para el rol de administrador
                                // Repite este paso para los otros roles (por ejemplo, "isLoggedPaciente", "isLoggedDoctor")


                                startActivity(new Intent(getApplicationContext(),pacienteMenu.class));



                            } else if (valor.equalsIgnoreCase("inicio de sesión exitoso como doctor")) {
                                // Acciones para un inicio de sesión exitoso como empleado

                                txtUsuario.getEditText().setText("");
                                txtContraseña.getEditText().setText("");

                                editor.putBoolean("isLoggedDoctor", true); // Estado de inicio de sesión para el rol de administrador
                                // Repite este paso para los otros roles (por ejemplo, "isLoggedPaciente", "isLoggedDoctor")



                                Intent intent = new Intent(IniciarSessionActivity.this,DoctorMenu.class);
                                startActivity(intent);


                            } else {

                                Toast.makeText(IniciarSessionActivity.this, valor, Toast.LENGTH_SHORT).show();

                            }
                            editor.apply();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Manejo de errores de la solicitud
                            Toast.makeText( IniciarSessionActivity.this, "Error en la solicitud: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("correo", email);
                    params.put("contrasena", encryptedPassword);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(IniciarSessionActivity.this);
            requestQueue.add(request);
        }
    }
}