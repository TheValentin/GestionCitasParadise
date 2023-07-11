package com.example.gestioncitasparadise.actividades.uiDoctor.Horarios;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gestioncitasparadise.R;
import com.example.gestioncitasparadise.actividades.doctorMenu;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AgregarHorariosDoctorFragment extends Fragment {

    private int horaInicio, minutoInicio;
    private int horaFin, minutoFin;

    private TextInputLayout t_hora_inicio,t_hora_fin;
    Button b_agregar,b_cancelar;
    AutoCompleteTextView autoCompleteTextView;
    String url="https://thevalentin.000webhostapp.com/Proyectos/ServidorGestionCitas/AgregarHorario.php";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_agregar_horarios_doctor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        t_hora_inicio=view.findViewById(R.id.txthoraioinicio);
        t_hora_fin=view.findViewById(R.id.txthoraiofin);
        b_agregar=view.findViewById(R.id.btnAgregarHorarioDoctor);
        b_cancelar=view.findViewById(R.id.btnCancelarHorario);



        b_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarDatos();
            }
        });

        b_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(new HorariosDoctorFragment());
            }
        });








        autoCompleteTextView= view.findViewById(R.id.autoCompleteTextView2);

        String[] datos = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes","Sabado","Domingo"};



        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, datos);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setInputType(InputType.TYPE_NULL);



        autoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                autoCompleteTextView.showDropDown();
            }
        });

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                // Realiza las acciones que deseas con el valor seleccionado
                Toast.makeText(requireContext(), "Seleccionaste: " + selectedItem, Toast.LENGTH_SHORT).show();
            }
        });


        Button btnHorarioInicio = view.findViewById(R.id.btnhorario_inicio);
        Button btnHorarioFin = view.findViewById(R.id.btnhorario_fin);

        btnHorarioInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t_hora_fin.getEditText().setText("");
                mostrarSelectorTiempo(true);
            }
        });

        btnHorarioFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarSelectorTiempo(false);
            }
        });
    }
    public void openFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction =getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_doctor, fragment); // Reemplaza "R.id.fragment_container_Admin" con el ID correcto del contenedor del fragmento en tu diseño XML
        fragmentTransaction.commit();

    }
    private void mostrarSelectorTiempo(final boolean isInicio) {
        // Obtén la hora y el minuto actuales
        Calendar calendario = Calendar.getInstance();
        int horaActual = calendario.get(Calendar.HOUR_OF_DAY);
        int minutoActual = calendario.get(Calendar.MINUTE);

        // Crea un diálogo de selección de tiempo
        TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hora, int minuto) {
                        // Guarda la hora y el minuto seleccionados
                        if (isInicio) {
                            horaInicio = hora;
                            minutoInicio = minuto;
                        } else {
                            horaFin = hora;
                            minutoFin = minuto;
                        }

                        // Actualiza los campos de texto con la hora seleccionada
                        actualizarTextoHora(isInicio, hora, minuto);
                    }
                }, horaActual, minutoActual, false);

        // Muestra el diálogo de selección de tiempo
        timePickerDialog.show();
    }

    private void actualizarTextoHora(boolean isInicio, int hora, int minuto) {
        String horaSeleccionada = String.format(Locale.getDefault(), "%02d:%02d", hora, minuto);

        if (isInicio) {
            t_hora_inicio.getEditText().setText(horaSeleccionada);
        } else {

            String horaInicioTexto = t_hora_inicio.getEditText().getText().toString();
            String[] partesInicio = horaInicioTexto.split(":");
            int horaInicio = Integer.parseInt(partesInicio[0]);
            int minutoInicio = Integer.parseInt(partesInicio[1]);

            // Comparar las horas
            if (hora > horaInicio || (hora == horaInicio && minuto > minutoInicio)) {
                t_hora_fin.getEditText().setText(horaSeleccionada);
            } else {
                Toast.makeText(requireContext(), "La hora de fin debe ser mayor que la hora de inicio", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void insertarDatos() {

        String id_doctor= String.valueOf(doctorMenu.DoctorArrayList.get(0).getId_doctor());
        String Dia_semana= autoCompleteTextView.getText().toString();
        String hora_inicio= t_hora_inicio.getEditText().getText().toString();
        String hora_fin= t_hora_fin.getEditText().getText().toString();

        if (hora_inicio.isEmpty()) {
            Toast.makeText(getActivity(),"ingrese Horario",Toast.LENGTH_SHORT).show();
            return;
        }
        if (hora_fin.isEmpty()) {
            Toast.makeText(getActivity(),"ingrese Horario fin",Toast.LENGTH_SHORT).show();
            return;
        }
        if (Dia_semana.isEmpty()) {
            Toast.makeText(getActivity(),"ingrese Dia de semana",Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Cargando");


        if (Dia_semana.isEmpty()){
            Toast.makeText(getActivity(),"ingrese Dia de semana",Toast.LENGTH_SHORT).show();
            return;
        }else {
            progressDialog.show();
            StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    String valor=response.trim();

                    if (valor.equalsIgnoreCase("Datos insertados correctamente.")) {
                        openFragment(new HorariosDoctorFragment());
                        Toast.makeText(getActivity(), "Horario Registrado", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

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
                    params.put("id_doctor",id_doctor );
                    params.put("dia_semana",Dia_semana);
                    params.put("hora_inicio",hora_inicio);
                    params.put("hora_fin",hora_fin);
                    return params;
                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue( getActivity());
            requestQueue.add(request);
        }
    }


}