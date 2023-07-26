package com.example.gestioncitasparadise.actividades.uiDoctor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gestioncitasparadise.R;
import com.example.gestioncitasparadise.actividades.doctorMenu;


public class PerfilDoctorFragment extends Fragment {


    TextView nombredoctor,email,nombre,apellido, dni, telefono, direccion,especialidad;


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

        nombredoctor.setText(doctorMenu.DoctorArrayList.get(0).getNombre_doctor().toString());
        email.setText(doctorMenu.DoctorArrayList.get(0).getEmail_doctor().toString());
        nombre.setText(doctorMenu.DoctorArrayList.get(0).getNombre_doctor().toString());
        apellido.setText(doctorMenu.DoctorArrayList.get(0).getApellido_doctor().toString());
        dni.setText(doctorMenu.DoctorArrayList.get(0).getDni_doctor().toString());
        telefono.setText(doctorMenu.DoctorArrayList.get(0).getTelefono_doctor().toString());
        direccion.setText(doctorMenu.DoctorArrayList.get(0).getDireccion_doctor().toString());
        especialidad.setText(doctorMenu.DoctorArrayList.get(0).getNombreEspecialidad().toString());


    }


}