package com.example.gestioncitasparadise.actividades.uiAdministrador.Especialidad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gestioncitasparadise.Adapter.AdapterEspecialidad;
import com.example.gestioncitasparadise.R;
import com.example.gestioncitasparadise.dto.Especialidad;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListaEspecialidadAdministradorFragment extends Fragment {

    ListView listView;
    AdapterEspecialidad adapter;
    public static ArrayList<Especialidad> especialidadsArrayList =new ArrayList<>();
    Especialidad especialidad;
    Button btnEliminar;
    String url="https://thevalentin.000webhostapp.com/Proyectos/ServidorGestionCitas/Mostrar_especialidad.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registrar_especialidad_administrador, container, false);




    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationViewAdministrador);
        bottomNavigationView.setVisibility(View.VISIBLE);

        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatbtnAgregarEspecialidad);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AgregarEspecialdad();
            }
        });

        listView = view.findViewById(R.id.list_Especialidad);
        adapter=new AdapterEspecialidad(getActivity(),especialidadsArrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                // ProgressDialog progressDialog=new ProgressDialog(view.getContext());

                CharSequence[]dialogo={"EDITAR","ELIMINAR DATOS"};

                builder.setTitle(especialidadsArrayList.get(position).getNombreEspecialidad());

                builder.setItems(dialogo, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i){
                            case 0:


                                Log.i("infoxxx","entrooo");
                                Actualizar(position);

                                //startActivity(new Intent(getActivity(), ActualizarEspecialidad_AdministradorFragment.class).putExtra("position",position));
                            break;

                            case 1:
                                Log.i("infoxxx","entroooEliminar");
                                Eliminar(especialidadsArrayList.get(position).getId_Especialidad());
                                break;
                        }
                    }
                });
                builder.show();

            }
        });




        ListarDatos();
    }



    private void AgregarEspecialdad() {
        Log.i("infoxxx","entre");
        abrirFragmentoB();
//        Intent intent = new Intent(getContext(), AgregarEspecialidad_AdministradorFragment.class);
//        startActivity(intent);

    }

    private void Actualizar(int position) {

        ActualizarEspecialidad_AdministradorFragment fragmentoActualizar = ActualizarEspecialidad_AdministradorFragment.newInstance(position);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_hostFragment_administrador, fragmentoActualizar); // Reemplaza "R.id.container" con el ID correcto del contenedor del Fragmento en tu diseño XML
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    private void ListarDatos(){
        StringRequest request =new StringRequest(Request.Method.POST, url,
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
                                    adapter.notifyDataSetChanged();
                                }
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
    public  void Eliminar(final  String id){
        StringRequest request =new StringRequest(Request.Method.POST, "https://thevalentin.000webhostapp.com/Proyectos/ServidorGestionCitas/EliminarEspecialidad.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        String valor=response.trim();
                        Log.i("infoxxx",valor);
                        if (valor.equalsIgnoreCase("datos eliminados")) {
                            Toast.makeText(getActivity(), "elimino correctamente", Toast.LENGTH_SHORT).show();
                            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_hostFragment_administrador);
                            navController.navigate(R.id.page_Registrar_especialidad);



                           // startActivity(new Intent(getActivity(), AdministradorMenu.class));


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
                params.put("id",id);



                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);

    }

    public void abrirFragmentoB() {
        AgregarEspecialidad_AdministradorFragment fragmentB = new AgregarEspecialidad_AdministradorFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_hostFragment_administrador, fragmentB);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public static ListaEspecialidadAdministradorFragment newInstance(String data) {
        ListaEspecialidadAdministradorFragment fragment = new ListaEspecialidadAdministradorFragment();
        Bundle args = new Bundle();
        args.putString("position", data);
        fragment.setArguments(args);
        return fragment;
    }



}




