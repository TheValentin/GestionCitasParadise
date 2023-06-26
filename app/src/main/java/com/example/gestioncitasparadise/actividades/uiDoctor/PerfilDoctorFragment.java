package com.example.gestioncitasparadise.actividades.uiDoctor;

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
import com.example.gestioncitasparadise.login.IniciarSessionActivity;


public class PerfilDoctorFragment extends Fragment {


    TextView nombredoctor,email,nombre,apellido, dni, telefono, direccion,especialidad;

    int codigo=DoctorMenu.DoctorArrayList.get(0).getId_doctor();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil_doctor, container, false);


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nombredoctor=view.findViewById(R.id.txtNombreDoctorDoctor);
        email=view.findViewById(R.id.txtEmailDoctorDoctor);
        nombre=view.findViewById(R.id.txtNombreDoctoPerfil);
        apellido=view.findViewById(R.id.txtapeliidoDoctorPerfil);
        dni=view.findViewById(R.id.txtDniDocentePerfil);
        telefono=view.findViewById(R.id.txtTelefonoDocentePerfil);
        direccion=view.findViewById(R.id.txtdireccionDocentePerfil);
        especialidad=view.findViewById(R.id.txtEspecialidadDoctorPerfil);
        //mostrar en pantalla

        nombredoctor.setText(DoctorMenu.DoctorArrayList.get(0).getNombre_doctor().toString());
        email.setText(DoctorMenu.DoctorArrayList.get(0).getEmail_doctor().toString());
        nombre.setText(DoctorMenu.DoctorArrayList.get(0).getNombre_doctor().toString());
        apellido.setText(DoctorMenu.DoctorArrayList.get(0).getApellido_doctor().toString());
        dni.setText(DoctorMenu.DoctorArrayList.get(0).getDni_doctor().toString());
        telefono.setText(DoctorMenu.DoctorArrayList.get(0).getTelefono_doctor().toString());
        direccion.setText(DoctorMenu.DoctorArrayList.get(0).getDireccion_doctor().toString());
        especialidad.setText(DoctorMenu.DoctorArrayList.get(0).getNombreEspecialidad().toString());


    }


}