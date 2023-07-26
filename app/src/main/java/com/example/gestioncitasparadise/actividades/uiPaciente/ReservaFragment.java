package com.example.gestioncitasparadise.actividades.uiPaciente;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gestioncitasparadise.Adapter.AdapterDoctorReserva;
import com.example.gestioncitasparadise.Adapter.AdapterEspecialidadReserva;
import com.example.gestioncitasparadise.Adapter.AdapterHorarioDoctor;
import com.example.gestioncitasparadise.R;

import com.example.gestioncitasparadise.actividades.pacienteMenu;
import com.example.gestioncitasparadise.actividades.uiPaciente.CitasProgramadas.HistorialFragment;
import com.example.gestioncitasparadise.dto.Doctor;
import com.example.gestioncitasparadise.dto.Especialidad;
import com.example.gestioncitasparadise.dto.Horarios;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class ReservaFragment extends Fragment {


    TextInputLayout calendario;
    ListView listViewEspecialidad, listViewDoctor, listViewHorarios;

    TextView txt_doctor, txt_fecha,Dia_reservado;
    Button b_guardar, b_calendario;
    AdapterEspecialidadReserva adapterEspecialidadReserva;
    AdapterDoctorReserva adapterDoctorReserva;
     AdapterHorarioDoctor adapterHorarioDoctor;
    public static ArrayList<Especialidad> especialidadsArrayList =new ArrayList<>();
    public static ArrayList<Doctor> DoctorArrayList= new ArrayList<>();
    public static ArrayList<Horarios> HorariosArrayList=new ArrayList<>();

    Especialidad especialidad;
    Doctor doctor;
    Horarios horarios;

    String id_Doctor,id_paciente,fechaCita, hora;

    String urlEspecialidad="https://thevalentin.000webhostapp.com/Proyectos/ServidorGestionCitas/Mostrar_especialidad.php";
    String urlDoctor="https://thevalentin.000webhostapp.com/Proyectos/ServidorGestionCitas/MostrarDoctorFiltroEspecialidad.php";


    String urlHorarios="https://thevalentin.000webhostapp.com/Proyectos/ServidorGestionCitas/MostrarHorariosPacienteReserva.php";
    String UrlCita="https://thevalentin.000webhostapp.com/Proyectos/ServidorGestionCitas/RegistrarReservaCita.php";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reserva_paciente, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listViewEspecialidad=view.findViewById(R.id.list_EspecialidadReservaPaciente);
        adapterEspecialidadReserva=new AdapterEspecialidadReserva(getContext(),especialidadsArrayList);
        listViewEspecialidad.setAdapter(adapterEspecialidadReserva);


        listViewDoctor=view.findViewById(R.id.list_DoctorReservaPaciente);
        listViewHorarios=view.findViewById(R.id.list_HorarioReservaPaciente);

        txt_doctor=view.findViewById(R.id.textElegirDcotor);
        txt_fecha=view.findViewById(R.id.textElegirfecha);
        b_guardar=view.findViewById(R.id.guardarReserva);
        b_calendario=view.findViewById(R.id.btncalendarioReserva);
        calendario=view.findViewById(R.id.txtfechaConsultaReservaPaciente);
        Dia_reservado=view.findViewById(R.id.txt_diaSelecionadoReservar);







        listViewEspecialidad.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Actualizar la posición seleccionada
                adapterEspecialidadReserva.setSelectedItem(position);
                // Notificar al adaptador para que actualice la vista
                adapterEspecialidadReserva.notifyDataSetChanged();




                txt_fecha.setVisibility(View.GONE);
                calendario.setVisibility(View.GONE);
                b_calendario.setVisibility(View.GONE);
                b_guardar.setVisibility(View.GONE);
                listViewHorarios.setVisibility(View.GONE);



                adapterDoctorReserva=new AdapterDoctorReserva(getContext(),DoctorArrayList);
                listViewDoctor.setAdapter(adapterDoctorReserva);
                String id_especialidad=especialidadsArrayList.get(position).getId_Especialidad() ;
                txt_doctor.setVisibility(View.VISIBLE);
                ListarDatosDoctor(id_especialidad);
                if (DoctorArrayList.size()<0){
                    listViewDoctor.setVisibility(View.GONE);

                }

                if(especialidadsArrayList.size()>0){
                    listViewDoctor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            adapterDoctorReserva.setSelectedItem(position);
                            // Notificar al adaptador para que actualice la vista
                            adapterDoctorReserva.notifyDataSetChanged();


                            adapterHorarioDoctor=new AdapterHorarioDoctor(getContext(),HorariosArrayList);
                            listViewHorarios.setAdapter(adapterHorarioDoctor);
                            id_Doctor= String.valueOf(DoctorArrayList.get(position).getId_doctor());

                            txt_fecha.setVisibility(View.VISIBLE);
                            calendario.setVisibility(View.VISIBLE);
                            b_calendario.setVisibility(View.VISIBLE);
                            b_guardar.setVisibility(View.VISIBLE);



                            b_calendario.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showDatePicker(); // Método para mostrar el cuadro de calendario

                                }
                            });

                            String dia=Dia_reservado.getText().toString();
                            fechaCita=calendario.getEditText().getText().toString();
                            if (dia != null && !dia.isEmpty() && fechaCita !=null && !fechaCita.isEmpty()) {
                                ListardatosHorario(id_Doctor,dia,fechaCita );
                                listViewHorarios.setVisibility(View.VISIBLE);
                            }

                            listViewHorarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    adapterHorarioDoctor.setSelectedItem(position);
                                    // Notificar al adaptador para que actualice la vista
                                    adapterHorarioDoctor.notifyDataSetChanged();
                                    hora=HorariosArrayList.get(position).getId_Horarios();
                                }
                            });



                        }
                    });
                }


            }
        });

        b_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_paciente=pacienteMenu.PacienteArrayList.get(0).getId_paciente().toString();
                fechaCita=calendario.getEditText().getText().toString();
                if(  id_paciente!=null && hora!=null && fechaCita!=null &&   !id_paciente.isEmpty() && !hora.isEmpty() && !fechaCita.isEmpty()){
                    String mensaje="Tienes una nueva cita pendiente";
                    GuardarReserva(id_paciente,hora,fechaCita,mensaje);
                }else {
                    Toast.makeText(getContext(),"No se Registrado Correctamente",Toast.LENGTH_SHORT).show();
                }

            }
        });

        ListarDatosEspecialidad();
    }
    private void ListarDatosEspecialidad(){
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
                                    if(especialidadsArrayList.size()>0){
                                        adapterEspecialidadReserva.notifyDataSetChanged();
                                    }

                                }
                                adapterEspecialidadReserva.notifyDataSetChanged();
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

    private void ListarDatosDoctor(String id_especialidad ){
        StringRequest request =new StringRequest(Request.Method.POST, urlDoctor,
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
                                    String nombre=object.getString("nombre_doctor");
                                    String apellido=object.getString("nombre_especialidad");
                                    doctor =new Doctor(id,nombre,apellido);
                                    DoctorArrayList.add(doctor);
                                    if (DoctorArrayList.size() > 0) {
                                        //adapterDoctorReserva.notifyDataSetChanged();
                                    }

                                }
                                adapterDoctorReserva.notifyDataSetChanged();
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
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String>  params=new HashMap<String,String>();
                params.put("id_especialidad",id_especialidad);

                return params;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);





    }

    private   void ListardatosHorario(String id_Doctor, String Dia_semana, String fecha){
        StringRequest request =new StringRequest(Request.Method.POST, urlHorarios,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        HorariosArrayList.clear();
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
                                    HorariosArrayList.add(horarios);
                                    if (HorariosArrayList.size() > 0) {
                                        //adapterHorarioDoctor.notifyDataSetChanged();
                                    }

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
                params.put("id",id_Doctor);
                params.put("dia_semana",Dia_semana);
                params.put("fecha",fecha);


                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);


    }

    private void showDatePicker() {
        //calendario
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Crea un DatePickerDialog y establece el listener de fecha seleccionada
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                Calendar selectedCalendar = Calendar.getInstance();
                selectedCalendar.set(Calendar.YEAR, year);
                selectedCalendar.set(Calendar.MONTH, month);
                selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                // Restringir la selección de fechas
                long selectedTimeInMillis = selectedCalendar.getTimeInMillis();
                long currentTimeInMillis = System.currentTimeMillis();
                if (selectedTimeInMillis < currentTimeInMillis) {
                    // Fecha seleccionada anterior al día actual, no hacer nada
                    calendario.getEditText().setText("");
                    Dia_reservado.setText("");
                    Toast.makeText(getContext(),"Fecha no valida",Toast.LENGTH_SHORT).show();

                    return;
                }
                // Formato de fecha para obtener el día de la semana
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE", new Locale("es", "ES"));

                String dayOfWeek = dateFormat.format(selectedCalendar.getTime());

                // Aquí puedes manejar la fecha seleccionada

                String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                calendario.getEditText().setText(selectedDate);
                fechaCita=calendario.getEditText().getText().toString();

                if (dayOfWeek.equals("lunes")) {
                    Dia_reservado.setText("Lunes");
                } else if (dayOfWeek.equals("martes")) {
                    Dia_reservado.setText("Martes");
                } else if (dayOfWeek.equals("miércoles")) {
                    Dia_reservado.setText("Miercoles");
                } else if (dayOfWeek.equals("jueves")) {
                    Dia_reservado.setText("Jueves");
                } else if (dayOfWeek.equals("viernes")) {
                    Dia_reservado.setText("Viernes");
                } else if (dayOfWeek.equals("sábado")) {
                    Dia_reservado.setText("Sabado");
                } else if (dayOfWeek.equals("domingo")) {
                    Dia_reservado.setText("Domingo");
                } else {
                    Dia_reservado.setText("");
                }

                String dia= Dia_reservado.getText().toString();
                ListardatosHorario(id_Doctor,dia,fechaCita);
                listViewHorarios.setVisibility(View.VISIBLE);



            }
        }, year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        // Muestra el cuadro de calendario
        datePickerDialog.show();
    }
    private void GuardarReserva(String id_paciente,String id_horario, String fechaCita,String mensaje){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Cargando");

            progressDialog.show();
            StringRequest request=new StringRequest(Request.Method.POST, UrlCita, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String valor=response.trim();
                    if (valor.equalsIgnoreCase("Datos insertados correctamente.")) {
                        Toast.makeText(getActivity(), "Cita Registrada"+mensaje, Toast.LENGTH_SHORT).show();
                        enviarNotificacion(id_horario,"Nueva Cita",mensaje);
                        progressDialog.dismiss();
                        openFragment(new HistorialFragment());

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
                    params.put("id_paciente",id_paciente);
                    params.put("id_horario",id_horario);
                    params.put("fecha",fechaCita);
                    return params;
                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue( getActivity());
            requestQueue.add(request);
        }

    public void openFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction =getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_Paciente, fragment); // Reemplaza "R.id.fragment_container_Admin" con el ID correcto del contenedor del fragmento en tu diseño XML
        fragmentTransaction.commit();

    }

    public void enviarNotificacion(String idHora, String titulo, String mensaje ){
        String  urlNotificacion="https://thevalentin.000webhostapp.com/Proyectos/ServidorGestionCitas/EnviarNotificaciones.php";
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
                params.put("id_hora",idHora);
                params.put("TITULO",titulo);
                params.put("MENSAJE",mensaje);

                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue( getActivity());
        requestQueue.add(request);

    }

}