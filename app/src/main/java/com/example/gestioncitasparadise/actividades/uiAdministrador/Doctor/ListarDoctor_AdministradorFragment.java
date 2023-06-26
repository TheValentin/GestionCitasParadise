package com.example.gestioncitasparadise.actividades.uiAdministrador.Doctor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gestioncitasparadise.Adapter.AdapterDoctor;
import com.example.gestioncitasparadise.R;
import com.example.gestioncitasparadise.interfaces.MetodosCrud;
import com.example.gestioncitasparadise.dto.Doctor;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ListarDoctor_AdministradorFragment extends Fragment implements MetodosCrud {

    ListView listView;
    AdapterDoctor adapter;
    public static ArrayList<Doctor> DoctorArrayList =new ArrayList<>();
    Doctor doctor;
    String urlDetalle="https://thevalentin.000webhostapp.com/Proyectos/ServidorGestionCitas/MostraraDetalleDoctor.php";


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatbtnAgregarDoctor);

        Log.i("infoxxx","doctor");

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Agregar();
            }
        });

        listView = view.findViewById(R.id.list_Doctores);
        adapter=new AdapterDoctor(getActivity(),DoctorArrayList);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());

                CharSequence[]dialogo={"DETALLE","ELIMINAR DATOS"};

                builder.setTitle(DoctorArrayList.get(position).getNombre_doctor());

                builder.setItems(dialogo, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i){
                            case 0:

                                Actualizar(position);


                                break;
                            case 1:
                                Eliminar(DoctorArrayList.get(position).getId_doctor());

                                break;
                        }
                    }
                });
                builder.show();
            }
        });

        ListarDatos();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_listar_doctor__administrador, container, false);
    }
    @Override
    public void Eliminar(final  int id) {

        StringRequest request =new StringRequest(Request.Method.POST, "https://thevalentin.000webhostapp.com/Proyectos/ServidorGestionCitas/EliminarDoctor.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String valor=response.trim();
                        if (valor.equalsIgnoreCase("datos eliminados")) {
                            Toast.makeText(getActivity(), "elimino correctamente", Toast.LENGTH_SHORT).show();

                            openFragment(new ListarDoctor_AdministradorFragment());
                        } else {
                            Toast.makeText(getActivity(), "Error no se puede registrar", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override

            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String>params=new HashMap<>();
                params.put("id", String.valueOf(id));
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);

    }
    @Override
    public void Agregar() {
        openFragment(new AgregarDoctor_adminFragment());
    }



    @Override
    public void ListarDatos(){
        StringRequest request =new StringRequest(Request.Method.POST, urlDetalle,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        DoctorArrayList.clear();
                        try {
                            JSONObject jsonObject =new JSONObject(response);
                            String exito=jsonObject.getString("exito");
                            JSONArray jsonArray =jsonObject.getJSONArray("datos");

                            if (exito.equals("1")){
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject object=jsonArray.getJSONObject(i);
                                    int id=object.getInt("id_doctor");
                                    String nombre=object.getString("nombre");
                                    String apellido=object.getString("apeliido");
                                    String dni=object.getString("dni");
                                    String telefono=object.getString("telefono");
                                    String direccion=object.getString("direccion");
                                    String email=object.getString("email");
                                    String especialidadNombre=object.getString("especialidadnombre");
                                    String nombreSede=object.getString("NombreSede");
                                    String NombreDistrito=object.getString("DistritoNombre");
                                    String rol=object.getString("rol");
                                    doctor =new Doctor(id,NombreDistrito,nombreSede,nombre,apellido,dni,telefono,direccion,email,especialidadNombre,rol);
                                    DoctorArrayList.add(doctor);
                                }
                                adapter.notifyDataSetChanged();
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
    @Override
    public void Actualizar(int position) {
        //detalle de doctor
        DetalleDoctor_adminFragment fragmentoActualizar = DetalleDoctor_adminFragment.newInstance(position);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_Admin, fragmentoActualizar); // Reemplaza "R.id.container" con el ID correcto del contenedor del Fragmento en tu diseño XML
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    @Override
    public void openFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction =getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_Admin, fragment); // Reemplaza "R.id.fragment_container_Admin" con el ID correcto del contenedor del fragmento en tu diseño XML
        fragmentTransaction.commit();

    }

    public static ListarDoctor_AdministradorFragment newInstance(String data) {
        ListarDoctor_AdministradorFragment fragment = new ListarDoctor_AdministradorFragment();
        Bundle args = new Bundle();
        args.putString("position", data);
        fragment.setArguments(args);
        return fragment;
    }
}
