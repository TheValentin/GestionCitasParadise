package com.example.gestioncitasparadise.actividades.uiPaciente;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
import com.example.gestioncitasparadise.Adapter.AdapterReservarCitaPaciente;
import com.example.gestioncitasparadise.R;
import com.example.gestioncitasparadise.actividades.pacienteMenu;
import com.example.gestioncitasparadise.dto.Cita;
import com.example.gestioncitasparadise.dto.Paciente;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class HistorialFragment extends Fragment {


    AdapterReservarCitaPaciente adapterReservarCitaPaciente;
    public  static ArrayList<Cita> CitaArrayList=new ArrayList<>();

    Cita cita;
    ListView ListarCitasProgramadas;

    String UrlEliminar="https://thevalentin.000webhostapp.com/Proyectos/ServidorGestionCitas/EliminarReservaCitasPaciente.php";
    String UrlListarCita="https://thevalentin.000webhostapp.com/Proyectos/ServidorGestionCitas/MostrarCitasPaciente.php";

    String id_paciente;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(pacienteMenu.PacienteArrayList.size()>0){
            id_paciente= pacienteMenu.PacienteArrayList.get(0).getId_paciente().toString();
            listarHistorialCita(id_paciente);
        }else {
            Toast.makeText(getContext(),"Actualiza ventana",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_historial_paciente, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListarCitasProgramadas=view.findViewById(R.id.list_CitasReserva_paciente);
        adapterReservarCitaPaciente=new AdapterReservarCitaPaciente(getContext(),CitaArrayList);
        ListarCitasProgramadas.setAdapter(adapterReservarCitaPaciente);
    }

    public void listarHistorialCita(String id_paciente){
        StringRequest request =new StringRequest(Request.Method.POST, UrlListarCita,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        CitaArrayList.clear();
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
                                    String especialidadnombre=object.getString("especialidadnombre");
                                    cita =new Cita(id,fecha,dia_semana,hora_inicio,hora_fin,nombre,apeliido,especialidadnombre);
                                    CitaArrayList.add(cita);


                                }
                                adapterReservarCitaPaciente.notifyDataSetChanged();
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
                params.put("id_paciente",id_paciente);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

    public void openFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction =getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_Paciente, fragment); // Reemplaza "R.id.fragment_container_Admin" con el ID correcto del contenedor del fragmento en tu diseño XML
        fragmentTransaction.commit();

    }
}