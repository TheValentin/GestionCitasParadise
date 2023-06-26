package com.example.gestioncitasparadise.actividades.uiAdministrador.Doctor;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gestioncitasparadise.R;
import com.example.gestioncitasparadise.actividades.uiAdministrador.Especialidad.ActualizarEspecialidad_AdministradorFragment;
import com.example.gestioncitasparadise.actividades.uiAdministrador.Especialidad.ListaEspecialidadAdministradorFragment;
import com.example.gestioncitasparadise.dto.Especialidad;
import com.example.gestioncitasparadise.encriptacion;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AgregarDoctor_adminFragment extends Fragment {

    private TextInputLayout t_nombre,t_apellido,t_telefono,t_dni,t_correo;
    Button btn_generar,btnagregar,btncancelar;
    AutoCompleteTextView autoCompleteTextView;

    String url="https://thevalentin.000webhostapp.com/Proyectos/ServidorGestionCitas/RegistrarDoctor.php";
    String urlEspecialidad="https://thevalentin.000webhostapp.com/Proyectos/ServidorGestionCitas/Mostrar_especialidad.php";

    encriptacion encriptacion  = new encriptacion();

    public static ArrayList<Especialidad> especialidadsArrayList =new ArrayList<>();
    Especialidad especialidad;
    String selectedEspecialidadId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_agregar_doctor_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        t_nombre = view.findViewById(R.id.txtnombreDoctor);
        t_apellido = view.findViewById(R.id.txtapellidoDoctor);
        t_telefono = view.findViewById(R.id.txttelefonoDoctor);
        t_dni =view.findViewById(R.id.txtdniDoctor);
        t_correo =view.findViewById(R.id.txtcorreoDoctor);

        btn_generar=view.findViewById(R.id.btngenerarCorreoDoctor);
        btnagregar=view.findViewById(R.id.btnRegistrarRegistroDoctor);
        btncancelar=view.findViewById(R.id.btnCancelarDoctor);
        autoCompleteTextView= view.findViewById(R.id.autoCompleteTextViewAgregarDoctor);


        listarEspecialidad();


        autoCompleteTextView.setInputType(InputType.TYPE_NULL);



        autoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                autoCompleteTextView.showDropDown();
            }
        });




        btncancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();

            }
        });


        btn_generar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generarCorreo();;
            }
        });


            btnagregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    insertarDatos();
                }
            });


    }
    public void generarCorreo(){
        t_correo.getEditText().setText("");
        String dni= t_dni.getEditText().getText().toString();
        if (dni.isEmpty()) {
            t_dni.getEditText().setError("Este campo es obligatorio");
            return;
        }
        t_correo.getEditText().setText(dni+"@gmail.com");
    }
    public void insertarDatos(){
        String nombre= t_nombre.getEditText().getText().toString();
        String apellido= t_apellido.getEditText().getText().toString();
        String telefono= t_telefono.getEditText().getText().toString();
        String dni= t_dni.getEditText().getText().toString();
        String correo= t_correo.getEditText().getText().toString();
        String encryptedPassword = encriptacion.encryptPassword(dni);

        if (nombre.isEmpty()) {
            t_nombre.getEditText().setError("Este campo es obligatorio");
            return;
        } else {
            t_nombre.setError(null);
        }

        if (apellido.isEmpty()) {
            t_apellido.setError("Este campo es obligatorio");
            return;
        } else {
            t_apellido.setError(null);
        }
        if (telefono.isEmpty()) {
            t_telefono.setError("Este campo es obligatorio");
            return;
        } else {
            t_telefono.setError(null);
        }
        if (dni.isEmpty()) {
            t_dni.setError("Este campo es obligatorio");
            return;
        } else {
            t_dni.setError(null);
        }
        if (correo.isEmpty()) {
            t_correo.setError("Este campo es obligatorio");
            return;
        } else {
            t_correo.setError(null);
        }
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Cargando");

        if (dni.isEmpty()){
            Toast.makeText(getActivity(),"ingrese Dni",Toast.LENGTH_SHORT).show();
            return;
        }else {
            progressDialog.show();
            StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    String valor=response.trim();


                    if (valor.equalsIgnoreCase("Datos insertados correctamente.")) {
                        Toast.makeText(getActivity(), "Doctor Registrada", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();


                        openFragment(new ListarDoctor_AdministradorFragment());

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
                    params.put("nombre",nombre);
                    params.put("apellido",apellido);
                    params.put("telefono",telefono);
                    params.put("dni",dni);
                    params.put("correo",correo);
                    params.put("password",encryptedPassword);
                    params.put("id_especialidad",selectedEspecialidadId);


                    return params;
                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue( getActivity());
            requestQueue.add(request);
        }
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction =getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_Admin, fragment); // Reemplaza "R.id.fragment_container_Admin" con el ID correcto del contenedor del fragmento en tu diseño XML
        fragmentTransaction.commit();

    }
    private void listarEspecialidad(){
        StringRequest request =new StringRequest(Request.Method.POST, urlEspecialidad,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        especialidadsArrayList.clear();
                        try {
                            JSONObject jsonObject =new JSONObject(response);
                            String exito=jsonObject.getString("exito");
                            JSONArray jsonArray =jsonObject.getJSONArray("datos");

                            if (exito.equals("1")){
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject object=jsonArray.getJSONObject(i);
                                    String id=object.getString("id");
                                    String nombre=object.getString("nombreEspecialidad");
                                    String descripcion=object.getString("descripcionEspecialidad");
                                    especialidad =new Especialidad(id,nombre,descripcion);
                                    especialidadsArrayList.add(especialidad);

                                }
                                // Configurar el adaptador personalizado
                                ArrayAdapter<Especialidad> adapter = new ArrayAdapter<Especialidad>(requireContext(), android.R.layout.simple_dropdown_item_1line, especialidadsArrayList) {
                                    @NonNull
                                    @Override
                                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                        View view = super.getView(position, convertView, parent);
                                        TextView textView = (TextView) view.findViewById(android.R.id.text1);
                                        textView.setTextColor(Color.BLACK);
                                        return view;
                                    }
                                };

                                // Configurar el adaptador en el AutoCompleteTextView
                                autoCompleteTextView.setAdapter(adapter);

                                autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Especialidad selectedEspecialidad = (Especialidad) parent.getItemAtPosition(position);
                                        selectedEspecialidadId = selectedEspecialidad.getId_Especialidad();

                                        // Realiza las acciones que deseas con el ID de la especialidad seleccionada
                                        Toast.makeText(requireContext(), "ID de la especialidad seleccionada: " + selectedEspecialidadId, Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
        ){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }
}