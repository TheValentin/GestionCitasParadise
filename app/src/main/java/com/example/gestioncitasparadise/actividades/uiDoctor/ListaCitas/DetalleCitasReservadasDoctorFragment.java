package com.example.gestioncitasparadise.actividades.uiDoctor.ListaCitas;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


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
import com.example.gestioncitasparadise.Adapter.AdapterHorarioDoctor;
import com.example.gestioncitasparadise.R;
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


public class DetalleCitasReservadasDoctorFragment extends Fragment {

    String urlActualizarCita="https://thevalentin.000webhostapp.com/Proyectos/ServidorGestionCitas/ActualizarCitaReservadaPAciente.php";
    String urlHorarios="https://thevalentin.000webhostapp.com/Proyectos/ServidorGestionCitas/MostrarHorariosPacienteReserva.php";
    String urlNotificacion="https://thevalentin.000webhostapp.com/Proyectos/ServidorGestionCitas/EnviarNotificaciones.php";

    TextView txtFechaCita, txtDiaCita, txtHorarioCita,txtPacienteCita,txtTelefonoCita,txtEstadoCita,Dia_reservado;
    TextInputLayout calendario;
    String fechaCita, id_Doctor, hora, id_cita,mensaje;
    public static ArrayList<Horarios> HorariosArrayList=new ArrayList<>();
    Horarios horarios;

    AdapterHorarioDoctor adapterHorarioDoctor;
    ListView listViewHorarios;
    Button b_calendario, b_guardar, b_cancelar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalle_citas_reservadas_doctor, container, false);
    }

    public static DetalleCitasReservadasDoctorFragment newInstance(int data, String id_Doctor) {
        DetalleCitasReservadasDoctorFragment fragment = new DetalleCitasReservadasDoctorFragment();
        Bundle args = new Bundle();
        args.putInt("position", data);
        args.putString("Id_doctor", id_Doctor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        txtFechaCita=view.findViewById(R.id.txtFechaCitaDoctor);
        txtDiaCita=view.findViewById(R.id.txtDiaCitaDoctor);
        txtHorarioCita=view.findViewById(R.id.txtHorarioCitaDoctor);
        txtPacienteCita=view.findViewById(R.id.txtPacienteCitaDoctor);
        txtTelefonoCita=view.findViewById(R.id.txtTelefonoCitaDoctor);
        txtEstadoCita=view.findViewById(R.id.txtEstadoCitaDoctor);

        calendario=view.findViewById(R.id.txtfechaConsultaReservaPacienteReprogramarDoctor);
        Dia_reservado=view.findViewById(R.id.txt_diaSelecionadoReservarReprogramarDoctor);
        listViewHorarios=view.findViewById(R.id.list_HorarioReservaPacienteReprogramarDoctor);
        b_calendario=view.findViewById(R.id.btncalendarioReservaReprogramarDoctor);
        b_guardar=view.findViewById(R.id.btnCitaReprogramarDoctor);
        b_cancelar=view.findViewById(R.id.btnCancelarCitaReprogramadaDoctor);

        listViewHorarios.setVisibility(View.GONE);
        Bundle args = getArguments();
        if (args != null) {
            int position = args.getInt("position");
            String id_doctor= args.getString("Id_doctor");
            id_Doctor=id_doctor;
            id_cita=ListaDoctorFragment.citaDoctorArrayList.get(position).getId_cita();
            txtFechaCita.setText(ListaDoctorFragment.citaDoctorArrayList.get(position).getFecha());
            txtDiaCita.setText(ListaDoctorFragment.citaDoctorArrayList.get(position).getDia_semana());
            txtHorarioCita.setText(ListaDoctorFragment.citaDoctorArrayList.get(position).getHora_inicio()+"-"+ListaDoctorFragment.citaDoctorArrayList.get(position).getHora_fin());
            txtPacienteCita.setText(ListaDoctorFragment.citaDoctorArrayList.get(position).getNombrePaciente()+" "+ListaDoctorFragment.citaDoctorArrayList.get(position).getApellidoPaciente());
            txtTelefonoCita.setText(ListaDoctorFragment.citaDoctorArrayList.get(position).getTelefonoPaciente());
            txtEstadoCita.setText(ListaDoctorFragment.citaDoctorArrayList.get(position).getEstado());
        }

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
        adapterHorarioDoctor=new AdapterHorarioDoctor(getContext(),HorariosArrayList);
        listViewHorarios.setAdapter(adapterHorarioDoctor);

        listViewHorarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapterHorarioDoctor.setSelectedItem(position);
                // Notificar al adaptador para que actualice la vista
                adapterHorarioDoctor.notifyDataSetChanged();

                b_guardar.setVisibility(View.VISIBLE);
                hora=HorariosArrayList.get(position).getId_Horarios();

            }
        });
        b_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFragment(new ListaDoctorFragment());
            }
        });
        b_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fechaCita=calendario.getEditText().getText().toString();
                if(  hora!=null && fechaCita!=null && !hora.isEmpty() && !fechaCita.isEmpty()){

                    mensaje="";

                    ReprogramarCita(id_cita,hora,fechaCita,mensaje);
                }else {
                    Toast.makeText(getContext(),"No se Reprogramno Correctamente",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void showDatePicker() {
        //calendario
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Crea un DatePickerDialog y establece el listener de fecha seleccionada
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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
                fechaCita = calendario.getEditText().getText().toString();

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

        // Muestra el cuadro de calendario
        datePickerDialog.show();
    }

    private  void ListardatosHorario(String id_Doctor, String Dia_semana, String fecha){
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

    private void ReprogramarCita(String id_cita, String hora, String fecha,String mensaje) {


        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Cargando");

        progressDialog.show();
        StringRequest request=new StringRequest(Request.Method.POST, urlActualizarCita, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String valor=response.trim();

                if (valor.equalsIgnoreCase("datos actualizados")) {
                    Toast.makeText(getActivity(), "Cita Reprogramada", Toast.LENGTH_SHORT).show();
                    enviarNotificacion(id_cita,"Cita Reprogramada",mensaje);
                    progressDialog.dismiss();

                    openFragment(new ListaDoctorFragment());



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
                params.put("id_cita",id_cita);
                params.put("id_horario",hora);
                params.put("fecha",fecha);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue( getActivity());
        requestQueue.add(request);
    }

    public void openFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction =getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_doctor, fragment); // Reemplaza "R.id.fragment_container_Admin" con el ID correcto del contenedor del fragmento en tu diseño XML
        fragmentTransaction.commit();

    }

    public  void enviarNotificacion(String id_cita, String TITULO, String MENSAJE ){
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
                params.put("id_cita",id_cita);
                params.put("TITULO",TITULO);
                params.put("MENSAJE",MENSAJE);

                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue( getActivity());
        requestQueue.add(request);

    }



}