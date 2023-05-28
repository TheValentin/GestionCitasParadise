package com.example.gestioncitasparadise.actividades.uiAdministrador.Especialidad;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

public class AgregarEspecialidad_administrador extends AppCompatActivity {

    private Button b_cancelarEspecialidad, b_AgregarEspecialidad;
    private TextInputLayout txtEspecialidad;
    private EditText txtMultiLine;
    private String url="https://thevalentin.000webhostapp.com/Proyectos/ServidorGestionCitas/AgregarEspecialidad.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_especialidad_administrador);
        enlazarControlado();
    }

    private void enlazarControlado() {
        txtEspecialidad=findViewById(R.id.TxtEspecialidad);
        txtMultiLine=findViewById(R.id.txtDescripcion);
        b_AgregarEspecialidad=findViewById(R.id.btnAgregarEspecialidad);
        b_cancelarEspecialidad = findViewById(R.id.btnCancelarEspecialidad);

        b_cancelarEspecialidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        b_AgregarEspecialidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarDatos();

            }
        });



    }

    private void insertarDatos() {
        String Especialidad= txtEspecialidad.getEditText().getText().toString();
        String descripcion= txtMultiLine.getText().toString().trim();

        if (Especialidad.isEmpty()) {
            txtEspecialidad.getEditText().setError("Este campo es obligatorio");
            return;
        } else {
            txtEspecialidad.setError(null);
        }

        if (descripcion.isEmpty()) {
            txtMultiLine.setError("Este campo es obligatorio");
            return;
        } else {
            txtMultiLine.setError(null);
        }
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando");

        if (Especialidad.isEmpty()){
            Toast.makeText(this,"ingrese Especialidad",Toast.LENGTH_SHORT).show();
            return;
        }else {
            progressDialog.show();
            StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    String valor=response.trim();

                    if (valor.equalsIgnoreCase("Datos insertados correctamente.")) {
                        Toast.makeText(AgregarEspecialidad_administrador.this, "Especialidad Registrada", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();


                    startActivity(new Intent(AgregarEspecialidad_administrador.this, AdministradorMenu.class));


                    } else {
                        Toast.makeText(AgregarEspecialidad_administrador.this, valor, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AgregarEspecialidad_administrador.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }){

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String>  params=new HashMap<String,String>();
                    params.put("Especialidad",Especialidad);
                    params.put("descripcion",descripcion);
                    return params;
                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue( AgregarEspecialidad_administrador.this);
            requestQueue.add(request);
        }
    }

}