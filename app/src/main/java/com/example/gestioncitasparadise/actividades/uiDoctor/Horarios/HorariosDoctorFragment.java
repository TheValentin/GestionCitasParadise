package com.example.gestioncitasparadise.actividades.uiDoctor.Horarios;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.gestioncitasparadise.Adapter.AdapterHorarioDoctor;
import com.example.gestioncitasparadise.R;
import com.example.gestioncitasparadise.actividades.DoctorMenu;
import com.example.gestioncitasparadise.actividades.uiAdministrador.Doctor.AgregarDoctor_adminFragment;
import com.example.gestioncitasparadise.actividades.uiAdministrador.Especialidad.ListaEspecialidadAdministradorFragment;
import com.example.gestioncitasparadise.dto.Doctor;
import com.example.gestioncitasparadise.dto.Horarios;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HorariosDoctorFragment extends Fragment {

    public static List<Horarios> HorariosArray= new ArrayList<>();
    Horarios horarios;

    AdapterHorarioDoctor adapterHorarioDoctor;
    FragmentManager fragmentManager;
    ListView listView;
    String codiog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_horarios_doctor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String codigo = String.valueOf(DoctorMenu.DoctorArrayList.get(0).getId_doctor());
        listView = view.findViewById(R.id.list_horariosDoctor);
        adapterHorarioDoctor=new AdapterHorarioDoctor(getActivity(),HorariosArray);
        listView.setAdapter(adapterHorarioDoctor);
        ListarHorario(codigo);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                // ProgressDialog progressDialog=new ProgressDialog(view.getContext());

                CharSequence[]dialogo={"ELIMINAR DATOS"};

                builder.setTitle(HorariosArray.get(position).getDia_semana());

                //Ejemplo de como pedir //////////////////////////////////
                codiog=HorariosArray.get(position).getId_Horarios();
                Log.i("infoxxx","::"+codiog);


                builder.setItems(dialogo, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i){
                            case 0:
                                Eliminar(codiog);
                                break;
                        }
                    }
                });
                builder.show();

            }
        });


        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatbtnAgregarHorario);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Agregar();
            }
        });

    }
    public void ListarHorario(String idDoctor){
        String url="https://thevalentin.000webhostapp.com/Proyectos/ServidorGestionCitas/HorariosDoctor.php";

        StringRequest request =new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        HorariosArray.clear();
                        try {
                            JSONObject jsonObject =new JSONObject(response);
                            String exito=jsonObject.getString("exito");
                            JSONArray jsonArray =jsonObject.getJSONArray("datos");

                            if (exito.equals("1")){
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject object=jsonArray.getJSONObject(i);
                                    String id_horario=object.getString("id_horario");
                                    String id_doctor=object.getString("id_doctor");
                                    String dia_semana=object.getString("dia_semana");
                                    String Hora_inicio=object.getString("hora_inicio");
                                    String Hora_final=object.getString("hora_fin");
                                    horarios =new Horarios(id_horario,id_doctor,dia_semana,Hora_inicio,Hora_final);
                                    HorariosArray.add(horarios);
                                }
                                adapterHorarioDoctor.notifyDataSetChanged();
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
                params.put("id",idDoctor);

                return params;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);

    }

    public void Agregar() {
        openFragment(new AgregarHorariosDoctorFragment());
    }
    public void openFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction =getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_doctor, fragment); // Reemplaza "R.id.fragment_container_Admin" con el ID correcto del contenedor del fragmento en tu diseño XML
        fragmentTransaction.commit();

    }

    public  void Eliminar( String id){

        StringRequest request =new StringRequest(Request.Method.POST, "https://thevalentin.000webhostapp.com/Proyectos/ServidorGestionCitas/EliminarHorarioDoctor.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String valor=response.trim();
                        if (valor.equalsIgnoreCase("datos eliminados")) {
                            Toast.makeText(getActivity(), "elimino correctamente", Toast.LENGTH_SHORT).show();
                            openFragment(new HorariosDoctorFragment());

                        } else {
                            Toast.makeText(getActivity(), "No disponible para eliminar", Toast.LENGTH_SHORT).show();

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


}