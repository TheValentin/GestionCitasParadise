package com.example.gestioncitasparadise.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.gestioncitasparadise.R;
import com.example.gestioncitasparadise.dto.Doctor;

import java.util.List;

public class AdapterDoctor extends ArrayAdapter {

    Context context;

    List<Doctor> doctorLista;

    public AdapterDoctor(@NonNull Context context, List<Doctor>doctorLista) {
        super(context, R.layout.lista_doctor_administrador, doctorLista);
        this.context=context;
        this.doctorLista=doctorLista;
    }

    public View getView(int position, @NonNull View context , ViewGroup resource) {
        View view= LayoutInflater.from(resource.getContext()).inflate(R.layout.lista_doctor_administrador,null,true);
        TextView txtNombreDoctor=view.findViewById(R.id.txtNombreDoctor);
        TextView txtEspecialidadDoctor=view.findViewById(R.id.txtEspecialidadDoctor);
        String nombreDoctor = doctorLista.get(position).getNombre_doctor();
        String nombreDoctorFormateado = Character.toUpperCase(nombreDoctor.charAt(0)) + nombreDoctor.substring(1).toLowerCase();
        txtNombreDoctor.setText("Dr. "+nombreDoctorFormateado);
        txtEspecialidadDoctor.setText(doctorLista.get(position).getNombreEspecialidad());
        return view;
    }
}
