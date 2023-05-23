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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Historial_AdministradorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Historial_AdministradorFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Historial_AdministradorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Historial_AdministradorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Historial_AdministradorFragment newInstance(String param1, String param2) {
        Historial_AdministradorFragment fragment = new Historial_AdministradorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_historial__administrador, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnCerrarSesion = view.findViewById(R.id.btnCerrarSession);
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarSesion();
            }
        });

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