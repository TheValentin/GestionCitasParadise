package com.example.gestioncitasparadise.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.gestioncitasparadise.R;
import com.example.gestioncitasparadise.dto.Horarios;

import java.util.List;

public class AdapterHorarioReserva extends ArrayAdapter {

        Context context;
        private List<Horarios> HorarioLista;

    public AdapterHorarioReserva(@NonNull Context context, List<Horarios>HorarioLista) {
            super(context, R.layout.lista_horarios_reserva_paciente,HorarioLista);
            this.context=context;
            this.HorarioLista=HorarioLista;
        }

        public View getView(int position, @NonNull View convertView , ViewGroup parent) {
            //View view= LayoutInflater.from(resource.getContext()).inflate(R.layout.listarhorariodoctor,null,true);

            View view = convertView;

            if (view == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                view = inflater.inflate(R.layout.lista_horarios_reserva_paciente, parent, false);
            }


            TextView DiaSemana=view.findViewById(R.id.txtDiaHorarioDoctor);
            TextView HoraIniciFin=view.findViewById(R.id.txtHorasDoctor);
            if (HorarioLista != null && position < HorarioLista.size()) {
                DiaSemana.setText(HorarioLista.get(position).getDia_semana());
                HoraIniciFin.setText(HorarioLista.get(position).getHora_inicio() + "-" + HorarioLista.get(position).getHora_final());
            }
            return view;
        }




    }
