package com.example.gestioncitasparadise.actividades.uiPaciente;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gestioncitasparadise.R;
import com.example.gestioncitasparadise.actividades.DoctorMenu;
import com.example.gestioncitasparadise.actividades.pacienteMenu;


public class PerfilPacienteFragment extends Fragment {

    TextView nombrecentro,email,nombre,apellido,dni,telefono,cumpleaños,direccion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil_paciente, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        super.onViewCreated(view, savedInstanceState);
        nombrecentro=view.findViewById(R.id.txtNombrePacientePerfil);
        email=view.findViewById(R.id.txtEmailPacientePerfil);
        nombre=view.findViewById(R.id.txtnombrePacientePerfil);
        apellido=view.findViewById(R.id.txtapellidoPacientePerfil);
        dni=view.findViewById(R.id.txtdniPacientePerfil);
        telefono=view.findViewById(R.id.txttelefonoPacientePerfil);
        cumpleaños=view.findViewById(R.id.txtfechaPacientePerfil);
        direccion=view.findViewById(R.id.txtdireccionPacientePerfil);

        nombrecentro.setText(pacienteMenu.PacienteArrayList.get(0).getNombrePaciente().toString());
        email.setText(pacienteMenu.PacienteArrayList.get(0).getEmailPaciente().toString());
        nombre.setText(pacienteMenu.PacienteArrayList.get(0).getNombrePaciente().toString());
        apellido.setText(pacienteMenu.PacienteArrayList.get(0).getApellidoPaciente().toString());
        dni.setText(pacienteMenu.PacienteArrayList.get(0).getDniPaciente().toString());
        telefono.setText(pacienteMenu.PacienteArrayList.get(0).getTelefonoPaciente().toString());
        cumpleaños.setText(pacienteMenu.PacienteArrayList.get(0).getFechaPaciente().toString());
        direccion.setText(pacienteMenu.PacienteArrayList.get(0).getDireccionPaciente().toString());

    }
}