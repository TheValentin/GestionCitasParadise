package com.example.gestioncitasparadise.actividades.uiAdministrador.Especialidad;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;


public class agregarEspecialidadAdministradorFragment extends Fragment {

    private Button b_cancelarEspecialidad, b_AgregarEspecialidad;
    private TextInputLayout txtEspecialidad;
    private EditText txtMultiLine;
    private String url="https://thevalentin.000webhostapp.com/Proyectos/ServidorGestionCitas/AgregarEspecialidad.php";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_agregar_especialidad__administrador, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        txtEspecialidad=view.findViewById(R.id.TxtEspecialidad);
        txtMultiLine=view.findViewById(R.id.txtDescripcion);
        b_AgregarEspecialidad=view.findViewById(R.id.btnAgregarEspecialidad);
        b_cancelarEspecialidad = view.findViewById(R.id.btnCancelarEspecialidad);

        b_cancelarEspecialidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();

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
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Cargando");

        if (Especialidad.isEmpty()){
            Toast.makeText(getActivity(),"ingrese Especialidad",Toast.LENGTH_SHORT).show();
            return;
        }else {
            progressDialog.show();
            StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    String valor=response.trim();

                    if (valor.equalsIgnoreCase("Datos insertados correctamente.")) {
                        Toast.makeText(getActivity(), "Especialidad Registrada", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                        //retrocede al fragmento
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.popBackStack();

                    } else {
                        Toast.makeText(getActivity(), valor, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
            RequestQueue requestQueue= Volley.newRequestQueue( getActivity());
            requestQueue.add(request);
        }
    }

}