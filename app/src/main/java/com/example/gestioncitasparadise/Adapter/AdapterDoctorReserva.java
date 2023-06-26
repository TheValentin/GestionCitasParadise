package com.example.gestioncitasparadise.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.gestioncitasparadise.R;
import com.example.gestioncitasparadise.dto.Doctor;

import java.util.List;

public class AdapterDoctorReserva extends ArrayAdapter {

    Context context;
    private int selectedItem = -1;
    List<Doctor> DoctorLista;

    public AdapterDoctorReserva(@NonNull Context context, List<Doctor>DoctorLista) {
        super(context, R.layout.lista_doctor_reserva_paciente,DoctorLista);
        this.context=context;
        this.DoctorLista=DoctorLista;

    }
    @Override
    public int getCount() {
        return DoctorLista.size();
    }

    public View getView(int position, @NonNull View Context , ViewGroup resource) {
        //View view= LayoutInflater.from(resource.getContext()).inflate(R.layout.lista_doctor_reserva_paciente,null,true);


        View view=Context;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.lista_doctor_reserva_paciente, resource, false);
        }
        TextView txtNombreDoctor=view.findViewById(R.id.txtNombreDoctorReserva);
        TextView txtEspecialidadDoctor=view.findViewById(R.id.txtEspecialidadDoctorReserva);
        RelativeLayout layoutItem = view.findViewById(R.id.relativeLayout); // El ID del RelativeLayout dentro de listar_especialidad_reserva_paciente


        int colorFondoEspecial = ContextCompat.getColor(getContext(), R.color.celeste);



        // Establecer el color de fondo del elemento
        if (position == selectedItem) {
            //view.setBackgroundColor(ContextCompat.getColor(context, R.color.celeste));
            layoutItem.setBackgroundColor(colorFondoEspecial);  // Cambia el color a rojo

        } else {
            //view.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
            layoutItem.setBackgroundColor(Color.WHITE);
        }

        if (!DoctorLista.isEmpty()){
            String nombreDoctor = DoctorLista.get(position).getNombre_doctor();
            String nombreDoctorFormateado = Character.toUpperCase(nombreDoctor.charAt(0)) + nombreDoctor.substring(1).toLowerCase();

            if (DoctorLista != null && position < DoctorLista.size()) {
                txtNombreDoctor.setText("Dr. " + nombreDoctorFormateado);
                txtEspecialidadDoctor.setText(DoctorLista.get(position).getNombreEspecialidad());
            }
        }


        return view;
    }
    public void setSelectedItem(int position) {
        selectedItem = position;
        notifyDataSetChanged();
    }



}
