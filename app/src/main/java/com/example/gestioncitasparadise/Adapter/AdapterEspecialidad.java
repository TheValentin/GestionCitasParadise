package com.example.gestioncitasparadise.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.gestioncitasparadise.R;
import com.example.gestioncitasparadise.dto.Especialidad;

import java.util.List;

public class AdapterEspecialidad extends ArrayAdapter {

    Context context;
    List<Especialidad>especialidadsLista;

    public AdapterEspecialidad(@NonNull Context context, List<Especialidad>especialidadsLista) {
        super(context, R.layout.lista_especialidad,especialidadsLista);
    this.context=context;
    this.especialidadsLista=especialidadsLista;
    }

    public View getView(int position, @NonNull View Context , ViewGroup resource) {
        View view= LayoutInflater.from(resource.getContext()).inflate(R.layout.lista_especialidad,null,true);

        TextView txtEspecialidad=view.findViewById(R.id.txtNombreEspecialidad);
        TextView txtDescripcion=view.findViewById(R.id.txtDescripcionEspecialidad);

        txtEspecialidad.setText(especialidadsLista.get(position).getNombreEspecialidad());
        txtDescripcion.setText(especialidadsLista.get(position).getDescripcionEspecialidad());
        return view;
    }


}
