package com.example.gestioncitasparadise.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegistrarActivity extends AppCompatActivity {

    private TextInputLayout t_nombre,t_apellido,t_telefono,t_dni,t_correo,t_contrasena,t_dirrecion,t_fecha;
    Button b_Registrar, btnCalendario;

    encriptacion encriptacion  = new encriptacion();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        enlazarControlado();
    }
    public void procesoIniciar(View v){
        Intent intent = new Intent(this, IniciarSessionActivity.class);
        startActivity(intent);
    }

    public void enlazarControlado() {
        t_nombre = (TextInputLayout) findViewById(R.id.txtnombre);
        t_apellido = (TextInputLayout) findViewById(R.id.txtapellido);
        t_telefono = (TextInputLayout) findViewById(R.id.txttelefono);
        t_dni =(TextInputLayout)  findViewById(R.id.txtdni);
        t_fecha = (TextInputLayout) findViewById(R.id.txtfecha);
        t_dirrecion = (TextInputLayout) findViewById(R.id.txtdireccion);
        t_correo = (TextInputLayout) findViewById(R.id.txtcorreo);
        t_contrasena = (TextInputLayout)  findViewById(R.id.txtcontraseñaRegistroUsuario);
        b_Registrar=findViewById(R.id.btnRegistrarRegistro);

        btnCalendario=findViewById(R.id.btnCalendario);




        btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(); // Método para mostrar el cuadro de calendario
            }
        });

        b_Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarDatos();

            }
        });

    }
    private void insertarDatos(){
         String nombre= t_nombre.getEditText().getText().toString();
         String apeliido= t_apellido.getEditText().getText().toString().trim();
         String telefono= t_telefono.getEditText().getText().toString().trim();
         String dni= t_dni.getEditText().getText().toString().trim();
         String fecha= t_fecha.getEditText().getText().toString().trim();
         String direccion= t_dirrecion.getEditText().getText().toString().trim();
         String correo= t_correo.getEditText().getText().toString().trim();
         String contrasena = t_contrasena.getEditText().getText().toString().trim();

        String encryptedPassword = encriptacion.encryptPassword(contrasena);


        if (nombre.isEmpty()) {
            t_nombre.getEditText().setError("Este campo es obligatorio");
            return;
        } else {
            t_nombre.setError(null);
        }

        if (apeliido.isEmpty()) {
            t_apellido.getEditText().setError("Este campo es obligatorio");
            return;
        } else {
            t_apellido.setError(null);
        }

        if (telefono.isEmpty()) {
            t_telefono.getEditText().setError("Este campo es obligatorio");
            return;
        } else {
            t_telefono.setError(null);
        }

        if (dni.isEmpty()) {
            t_dni.getEditText().setError("Este campo es obligatorio");
            return;
        } else {
            t_dni.setError(null);
        }

        if (fecha.isEmpty()) {
            t_fecha.getEditText().setError("Este campo es obligatorio");
            return;
        } else {
            t_fecha.setError(null);
        }

        if (correo.isEmpty()) {
            t_correo.getEditText().setError("Este campo es obligatorio");
            return;
        } else {
            t_correo.setError(null);
        }

        if (contrasena.isEmpty()) {
            t_contrasena.getEditText().setError("Este campo es obligatorio");
            return;
        } else {
            t_contrasena.setError(null);
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            // El correo electrónico no tiene el formato correcto
            t_correo.setError("Ingrese un correo electrónico válido");
            return;
        }



        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando");

        if (nombre.isEmpty()){
            Toast.makeText(this,"ingrese nombre",Toast.LENGTH_SHORT).show();
            return;
        }else {
            progressDialog.show();
            StringRequest request=new StringRequest(Request.Method.POST, "https://thevalentin.000webhostapp.com/Proyectos/ServidorGestionCitas/RegistrarPaciente.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    String valor=response.trim();

                    if (valor.equalsIgnoreCase("Datos insertados correctamente.")) {
                        Toast.makeText(RegistrarActivity.this, "Registrado con exito", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        startActivity(new Intent(getApplicationContext(), IniciarSessionActivity.class));
                        finish();
                    } else {
                        Toast.makeText(RegistrarActivity.this, valor, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RegistrarActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }){

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String>  params=new HashMap<String,String>();
                    params.put("nombre",nombre);
                    params.put("apellido",apeliido);
                   params.put("dni",dni);
                   params.put("fecha_nacimiento",fecha);
                  params.put("telefono",telefono);
                  params.put("direccion",direccion);
                   params.put("email",correo);
                    params.put("password",encryptedPassword);
                    return params;
                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue( RegistrarActivity.this);
            requestQueue.add(request);
        }
    }
    private void showDatePicker() {
        //calendario
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Crea un DatePickerDialog y establece el listener de fecha seleccionada
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Aquí puedes manejar la fecha seleccionada
                String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                t_fecha.getEditText().setText(selectedDate);
            }
        }, year, month, dayOfMonth);

        // Muestra el cuadro de calendario
        datePickerDialog.show();
    }


}