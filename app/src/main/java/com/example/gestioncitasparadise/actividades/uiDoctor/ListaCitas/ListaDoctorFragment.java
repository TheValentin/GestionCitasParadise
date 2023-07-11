package com.example.gestioncitasparadise.actividades.uiDoctor.ListaCitas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import com.example.gestioncitasparadise.Adapter.AdapterCitasDoctor;
import com.example.gestioncitasparadise.R;
import com.example.gestioncitasparadise.actividades.doctorMenu;
import com.example.gestioncitasparadise.dto.Cita;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ListaDoctorFragment extends Fragment {



    AdapterCitasDoctor adapterCitasDoctor;
    public  static ArrayList<Cita> citaDoctorArrayList=new ArrayList<>();
    Cita cita;
    ListView listarCitaspendientes;
    String idDoctor;
    String urlListar="https://thevalentin.000webhostapp.com/Proyectos/ServidorGestionCitas/MostrarCitasDoctor.php";
    String url_cancelarita="https://thevalentin.000webhostapp.com/Proyectos/ServidorGestionCitas/CancelarCitaPaciente.php";
    String urlNotificacion="https://thevalentin.000webhostapp.com/Proyectos/ServidorGestionCitas/EnviarNotificaciones.php";


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

        if(doctorMenu.DoctorArrayList.size()>0){
            idDoctor=String.valueOf(doctorMenu.DoctorArrayList.get(0).getId_doctor());
            listarHistorialCita(idDoctor);
            registrarDispositivo();
        }else {
            Toast.makeText(getContext(),"Actualiza ventana",Toast.LENGTH_SHORT).show();
        }

        listarCitaspendientes=view.findViewById(R.id.list_CitasReserva_doctor);
        adapterCitasDoctor=new AdapterCitasDoctor(getContext(),citaDoctorArrayList);
        listarCitaspendientes.setAdapter(adapterCitasDoctor);
        //id_doctor= String.valueOf(DoctorMenu.DoctorArrayList.get(0).getId_doctor());
        listarCitaspendientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                // ProgressDialog progressDialog=new ProgressDialog(view.getContext());

                CharSequence[]dialogo={"Reprogramar Cita","Cancelar Cita"};

                builder.setTitle(citaDoctorArrayList.get(position).getNombrePaciente()+"-"+citaDoctorArrayList.get(position).getDia_semana()+"-"+citaDoctorArrayList.get(position).getFecha());

                String idCita=citaDoctorArrayList.get(position).getId_cita();

                String Mensaje="Lo sentimos "+citaDoctorArrayList.get(position).getNombrePaciente()+" se Cancelo tu Cita del dia "+citaDoctorArrayList.get(position).getDia_semana()+" de "+citaDoctorArrayList.get(position).getFecha()+" de la hora "+citaDoctorArrayList.get(position).getHora_inicio();
                builder.setItems(dialogo, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i){
                            case 0:
                                Actualizar(position,idDoctor);
                                break;
                            case 1:
                                cancelarCita(idCita,Mensaje);
                                break;
                        }
                    }
                });
                builder.show();
            }
        });
    }
    public void listarHistorialCita(String idDoctor){
        StringRequest request =new StringRequest(Request.Method.POST, urlListar,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        citaDoctorArrayList.clear();
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
                                    citaDoctorArrayList.add(cita);
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
                params.put("id_doctor",idDoctor);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }
    private void cancelarCita(String idCita,String mensaje){
        String Estado="Cancelado";

        StringRequest request=new StringRequest(Request.Method.POST, url_cancelarita, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String valor=response.trim();
                if (valor.equalsIgnoreCase("datos actualizados")) {
                    Toast.makeText(getActivity(), "Cita Cancelada"+idCita, Toast.LENGTH_SHORT).show();
                    enviarNotificacion(idCita,"Cita Cancelada",mensaje);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.popBackStack();
                    listarHistorialCita(idDoctor);



                } else {
                    Toast.makeText(getActivity(), valor, Toast.LENGTH_SHORT).show();


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String>  params=new HashMap<String,String>();
                params.put("ID_cita",idCita);
                params.put("Estado",Estado);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue( getActivity());
        requestQueue.add(request);

    }
    public void Actualizar(int position, String idDoctor ) {
        //detalle de doctor
        DetalleCitasReservadasDoctorFragment fragmentoActualizar = DetalleCitasReservadasDoctorFragment.newInstance(position,idDoctor);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_doctor, fragmentoActualizar); // Reemplaza "R.id.container" con el ID correcto del contenedor del Fragmento en tu diseño XML
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public  void enviarNotificacion(String idCita, String titulo, String mensaje ){
        StringRequest request=new StringRequest(Request.Method.POST, urlNotificacion, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String>  params=new HashMap<String,String>();
                params.put("id_cita",idCita);
                params.put("TITULO",titulo);
                params.put("MENSAJE",mensaje);

                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue( getActivity());
        requestQueue.add(request);

    }

    private void registrarDispositivo(){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {

                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("SP_FILE", Context.MODE_PRIVATE);
                        String tokenGuardado = sharedPreferences.getString("DEVICEID", null);

                        if (token != null){
                            if(tokenGuardado ==null	|| !token.equals(tokenGuardado)){
                                doctorMenu.postRegistrarDispositivoEnServidor(token, getActivity(),idDoctor);
                            }
                        }
                    }
                });
    }
}
