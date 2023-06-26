package com.example.gestioncitasparadise.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.gestioncitasparadise.R;
import com.example.gestioncitasparadise.dto.Especialidad;

import java.util.List;

public class AdapterEspecialidadReserva extends ArrayAdapter {

    private int selectedItem = -1;
    Context context;
    List<Especialidad> especialidadsLista;


    public AdapterEspecialidadReserva(@NonNull Context context, List<Especialidad>especialidadsLista) {
        super(context, R.layout.listar_especialidad_reserva_paciente,especialidadsLista);
        this.context=context;
        this.especialidadsLista=especialidadsLista;
    }

    public View getView(int position, @NonNull View Context , ViewGroup resource) {

        View view = Context;
        if (view == null) {
            view = LayoutInflater.from(resource.getContext()).inflate(R.layout.listar_especialidad_reserva_paciente, resource, false);
        }

        //View view= LayoutInflater.from(resource.getContext()).inflate(R.layout.listar_especialidad_reserva_paciente,null,true);
        TextView txtEspecialidad=view.findViewById(R.id.txtNombreEspecialidadReserva);
        TextView txtDescripcion=view.findViewById(R.id.txtDescripcionEspecialidadReserva);
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


        if (especialidadsLista != null && position < especialidadsLista.size() && !especialidadsLista.isEmpty()) {
            txtEspecialidad.setText(especialidadsLista.get(position).getNombreEspecialidad());
            txtDescripcion.setText(especialidadsLista.get(position).getDescripcionEspecialidad());
        }

        return view;
    }
    public void setSelectedItem(int position) {
        selectedItem = position;
        notifyDataSetChanged();
    }
}
