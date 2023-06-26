package com.example.gestioncitasparadise.actividades.uiAdministrador;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.gestioncitasparadise.R;
import com.example.gestioncitasparadise.login.IniciarSessionActivity;


public class Historial_AdministradorFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_historial__administrador, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void cerrarSesion() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("mis_preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Eliminar todas las preferencias
        editor.apply(); // Aplicar los cambios

        // Redirigir a la pantalla de inicio de sesión
        Intent intent = new Intent(requireContext(), IniciarSessionActivity.class);
        startActivity(intent);
        requireActivity().finish(); // Finalizar la actividad actual para evitar que el usuario vuelva atrás a la sesión cerrada
    }
}