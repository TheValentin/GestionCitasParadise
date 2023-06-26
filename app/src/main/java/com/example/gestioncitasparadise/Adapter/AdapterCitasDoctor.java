package com.example.gestioncitasparadise.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.gestioncitasparadise.R;
import com.example.gestioncitasparadise.dto.Cita;

import java.util.List;

public class AdapterCitasDoctor extends ArrayAdapter {

    Context context;
    private List<Cita> CitaLista;

    public AdapterCitasDoctor(@NonNull Context context, List<Cita>CitaLista) {
        super(context, R.layout.lista_historial_cita_doctor,CitaLista);
        this.context=context;
        this.CitaLista=CitaLista;
    }
    public View getView(int position, @NonNull View convertView , ViewGroup parent) {
        //View view= LayoutInflater.from(resource.getContext()).inflate(R.layout.listarhorariodoctor,null,true);

        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.lista_historial_cita_doctor, parent, false);
        }

        TextView dia_semana=view.findViewById(R.id.txt_DiaSemanahistorialPaciente);
        TextView fehcas=view.findViewById(R.id.txt_fechacitahistorialPaciente);
        TextView HoraIniciFin=view.findViewById(R.id.txtDiaHorarioDoctorReserva);
        TextView paciente=view.findViewById(R.id.txtPacienteReservar);
        TextView estado=view.findViewById(R.id.txt_EstadoReservaDoctor);
        TextView telefono=view.findViewById(R.id.txtPacienteTelefonoReservar);
//doctor
        if (CitaLista != null && position < CitaLista.size()) {
            dia_semana.setText(CitaLista.get(position).getDia_semana());
            fehcas.setText(CitaLista.get(position).getFecha());
            HoraIniciFin.setText(CitaLista.get(position).getHora_inicio() + "-" + CitaLista.get(position).getHora_fin());
            paciente.setText(CitaLista.get(position).getNombrePaciente()+" "+CitaLista.get(position).getApellidoPaciente());
            telefono.setText(CitaLista.get(position).getTelefonoPaciente());
            estado.setText(CitaLista.get(position).getEstado());
        }
        return view;
    }
}
