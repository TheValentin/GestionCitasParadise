package com.example.gestioncitasparadise.actividades.uiAdministrador.Especialidad;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.example.gestioncitasparadise.actividades.AdministradorMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;


public class ActualizarEspecialidad_AdministradorFragment extends Fragment {


    private Button b_cancelarEspecialidad, b_EditEspecialidad;
    private TextInputLayout txtEspecialidad, TxtIDEspecialidadEdit;
    private EditText txtMultiLine;
    private String url="https://thevalentin.000webhostapp.com/Proyectos/ServidorGestionCitas/ActualizarEspecialiad.php";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_actualizar_especialidad__administrador, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TxtIDEspecialidadEdit=view.findViewById(R.id.TxtIDEspecialidadEdit);
        txtEspecialidad=view.findViewById(R.id.TxtEspecialidadEdit);
        txtMultiLine=view.findViewById(R.id.txtDescripcionEdit);
        b_EditEspecialidad=view.findViewById(R.id.btnEspecialidadEdit);
        b_cancelarEspecialidad = view.findViewById(R.id.btnCancelarEspecialidadEdit);



        Bundle args = getArguments();
        if (args != null) {
            int position = args.getInt("position");
            TxtIDEspecialidadEdit.getEditText().setText(ListaEspecialidadAdministradorFragment.especialidadsArrayList.get(position).getId_Especialidad());
            txtEspecialidad.getEditText().setText(ListaEspecialidadAdministradorFragment.especialidadsArrayList.get(position).getNombreEspecialidad());
            txtMultiLine.setText(ListaEspecialidadAdministradorFragment.especialidadsArrayList.get(position).getDescripcionEspecialidad());
        }


        b_cancelarEspecialidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();

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
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Cargando");

        if (IDEspecialidad.isEmpty()){
            Toast.makeText(getActivity(),"ingrese ID Especialidad",Toast.LENGTH_SHORT).show();
            return;
        }else {
            progressDialog.show();
            StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    String valor=response.trim();

                    if (valor.equalsIgnoreCase("datos actualizados")) {
                        Toast.makeText(getActivity(), "Especialidad Actualizada", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

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
                    params.put("IdEspecialidad",IDEspecialidad);
                    params.put("Especialidad",Especialidad);
                    params.put("descripcion",descripcion);
                    return params;
                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue( getActivity());
            requestQueue.add(request);
        }
    }

    public static ActualizarEspecialidad_AdministradorFragment newInstance(int data) {
        ActualizarEspecialidad_AdministradorFragment fragment = new ActualizarEspecialidad_AdministradorFragment();
        Bundle args = new Bundle();
        args.putInt("position", data);
        fragment.setArguments(args);
        return fragment;
    }
}