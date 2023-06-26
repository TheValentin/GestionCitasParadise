package com.example.gestioncitasparadise.actividades.uiDoctor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gestioncitasparadise.Adapter.AdapterCitasDoctor;
import com.example.gestioncitasparadise.R;
import com.example.gestioncitasparadise.actividades.DoctorMenu;
import com.example.gestioncitasparadise.dto.Cita;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ListaDoctorFragment extends Fragment {



    AdapterCitasDoctor adapterCitasDoctor;
    public  static ArrayList<Cita> CitaDoctorArrayList=new ArrayList<>();

    Cita cita;

    ListView listarCitaspendientes;

    String id_doctor;
    String urlListar="https://thevalentin.000webhostapp.com/Proyectos/ServidorGestionCitas/MostrarCitasDoctor.php";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_doctor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listarCitaspendientes=view.findViewById(R.id.list_CitasReserva_doctor);
        adapterCitasDoctor=new AdapterCitasDoctor(getContext(),CitaDoctorArrayList);
        listarCitaspendientes.setAdapter(adapterCitasDoctor);

        if(DoctorMenu.DoctorArrayList.size()>0){

        }else {
            Toast.makeText(getContext(),"Actualiza ventana",Toast.LENGTH_SHORT).show();
        }

        id_doctor= String.valueOf(DoctorMenu.DoctorArrayList.get(0).getId_doctor());
        listarHistorialCita(id_doctor);


    }
    public void listarHistorialCita(String id_doctor){
        StringRequest request =new StringRequest(Request.Method.POST, urlListar,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        CitaDoctorArrayList.clear();
                        try {
                            JSONObject jsonObject =new JSONObject(response);
                            String exito=jsonObject.getString("exito");
                            JSONArray jsonArray =jsonObject.getJSONArray("datos");

                            if (exito.equals("1")){
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject object=jsonArray.getJSONObject(i);
                                    String id=object.getString("id_cita");
                                    String fecha=object.getString("fecha");
                                    String dia_semana=object.getString("dia_semana");
                                    String hora_inicio=object.getString("hora_inicio");
                                    String hora_fin=object.getString("hora_fin");
                                    String nombre=object.getString("nombre");
                                    String apeliido=object.getString("apeliido");
                                    String telefono=object.getString("telefono");
                                    String Estado=object.getString("Estado");
                                    cita =new Cita(id,fecha,dia_semana,hora_inicio,hora_fin,nombre,apeliido,telefono,Estado);
                                    CitaDoctorArrayList.add(cita);
                                }
                                adapterCitasDoctor.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String>  params=new HashMap<String,String>();
                params.put("id_doctor",id_doctor);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }
}
