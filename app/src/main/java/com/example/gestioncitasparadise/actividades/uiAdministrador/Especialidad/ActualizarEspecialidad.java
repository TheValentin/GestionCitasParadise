package com.example.gestioncitasparadise.actividades.uiAdministrador.Especialidad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

public class ActualizarEspecialidad extends AppCompatActivity {

    private Button b_cancelarEspecialidad, b_EditEspecialidad;
    private TextInputLayout txtEspecialidad, TxtIDEspecialidadEdit;
    private EditText txtMultiLine;
    private  int position;
    private String url="https://thevalentin.000webhostapp.com/Proyectos/ServidorGestionCitas/ActualizarEspecialiad.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_especialidad);
        enlazarControlado();
    }

    private void enlazarControlado() {
        TxtIDEspecialidadEdit=findViewById(R.id.TxtIDEspecialidadEdit);
        txtEspecialidad=findViewById(R.id.TxtEspecialidadEdit);
        txtMultiLine=findViewById(R.id.txtDescripcionEdit);
        b_EditEspecialidad=findViewById(R.id.btnEspecialidadEdit);
        b_cancelarEspecialidad = findViewById(R.id.btnCancelarEspecialidadEdit);

        Intent intent=getIntent();
        position=intent.getExtras().getInt("position");

        TxtIDEspecialidadEdit.getEditText().setText(ListaEspecialidadAdministradorFragment.especialidadsArrayList.get(position).getId_Especialidad());
        txtEspecialidad.getEditText().setText(ListaEspecialidadAdministradorFragment.especialidadsArrayList.get(position).getNombreEspecialidad());
        txtMultiLine.setText(ListaEspecialidadAdministradorFragment.especialidadsArrayList.get(position).getDescripcionEspecialidad());


        b_cancelarEspecialidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        b_EditEspecialidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActualizarDatos();

            }
        });



    }

    private void ActualizarDatos() {

        String IDEspecialidad= TxtIDEspecialidadEdit.getEditText().getText().toString();
        String Especialidad= txtEspecialidad.getEditText().getText().toString();
        String descripcion= txtMultiLine.getText().toString().trim();

        if (IDEspecialidad.isEmpty()) {
            TxtIDEspecialidadEdit.getEditText().setError("Este campo es obligatorio");
            return;
        } else {
            TxtIDEspecialidadEdit.setError(null);
        }

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

        if (IDEspecialidad.isEmpty()){
            Toast.makeText(this,"ingrese ID Especialidad",Toast.LENGTH_SHORT).show();
            return;
        }else {
            progressDialog.show();
            StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    String valor=response.trim();

                    if (valor.equalsIgnoreCase("datos actualizados")) {
                        Toast.makeText(ActualizarEspecialidad.this, "Especialidad Actualizada", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();



                   startActivity(new Intent(ActualizarEspecialidad.this, AdministradorMenu.class));


                    } else {
                        Toast.makeText(ActualizarEspecialidad.this, valor, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ActualizarEspecialidad.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }){

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String>  params=new HashMap<String,String>();
                    params.put("IdEspecialidad",IDEspecialidad);
                    params.put("Especialidad",Especialidad);
                    params.put("descripcion",descripcion);
                    return params;
                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue( ActualizarEspecialidad.this);
            requestQueue.add(request);
        }
    }


}