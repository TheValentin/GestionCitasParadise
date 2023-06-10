package com.example.gestioncitasparadise.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.gestioncitasparadise.R;
import com.example.gestioncitasparadise.actividades.uiAdministrador.Doctor.ListarDoctor_AdministradorFragment;
import com.example.gestioncitasparadise.dto.Doctor;

import java.util.List;

public class AdapterDoctor extends ArrayAdapter {

    Context context;

    List<Doctor> DoctorLista;

    public AdapterDoctor(@NonNull Context context, List<Doctor>DoctorLista) {
        super(context, R.layout.lista_doctor_administrador,DoctorLista);
        this.context=context;
        this.DoctorLista=DoctorLista;
    }

    public View getView(int position, @NonNull View Context , ViewGroup resource) {
        View view= LayoutInflater.from(resource.getContext()).inflate(R.layout.lista_doctor_administrador,null,true);
        TextView txtNombreDoctor=view.findViewById(R.id.txtNombreDoctor);
        TextView txtEspecialidadDoctor=view.findViewById(R.id.txtEspecialidadDoctor);

        String nombreDoctor = DoctorLista.get(position).getNombre_doctor();
        String nombreDoctorFormateado = Character.toUpperCase(nombreDoctor.charAt(0)) + nombreDoctor.substring(1).toLowerCase();
        txtNombreDoctor.setText("Dr. "+nombreDoctorFormateado);
        txtEspecialidadDoctor.setText(DoctorLista.get(position).getNombreEspecialidad());
        return view;
    }
}
