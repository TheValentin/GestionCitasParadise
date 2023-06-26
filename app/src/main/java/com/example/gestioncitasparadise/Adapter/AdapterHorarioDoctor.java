package com.example.gestioncitasparadise.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestioncitasparadise.R;
import com.example.gestioncitasparadise.dto.Doctor;
import com.example.gestioncitasparadise.dto.Especialidad;
import com.example.gestioncitasparadise.dto.Horarios;

import java.util.List;

public class AdapterHorarioDoctor  extends ArrayAdapter {


    Context context;
    private List<Horarios> HorarioLista;
    private int selectedItem = -1;

    public AdapterHorarioDoctor(@NonNull Context context, List<Horarios>HorarioLista) {
        super(context, R.layout.listarhorariodoctor,HorarioLista);
        this.context=context;
        this.HorarioLista=HorarioLista;
    }

    public View getView(int position, @NonNull View convertView , ViewGroup parent) {
        //View view= LayoutInflater.from(resource.getContext()).inflate(R.layout.listarhorariodoctor,null,true);

        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.listarhorariodoctor, parent, false);
        }

        TextView DiaSemana=view.findViewById(R.id.txtDiaHorarioDoctor);
        TextView HoraIniciFin=view.findViewById(R.id.txtHorasDoctor);

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
        if (HorarioLista != null && position < HorarioLista.size()) {
            DiaSemana.setText(HorarioLista.get(position).getDia_semana());
            HoraIniciFin.setText(HorarioLista.get(position).getHora_inicio()+"-"+HorarioLista.get(position).getHora_final());
        }
        // Establecer el selector como fondo del elemento
        view.setBackgroundResource(R.drawable.list_item_selector);
        return view;
    }
    public void setSelectedItem(int position) {
        selectedItem = position;
        notifyDataSetChanged();
    }




}
