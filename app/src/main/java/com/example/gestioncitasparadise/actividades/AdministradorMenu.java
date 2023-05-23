package com.example.gestioncitasparadise.actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.gestioncitasparadise.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdministradorMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador_menu);
        setupNavegacion();
    }

    private void setupNavegacion(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationViewAdministrador);
        NavHostFragment navHostFragment= (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_hostFragment_administrador);
        NavigationUI.setupWithNavController(
                bottomNavigationView,navHostFragment.getNavController()
        );
    }
}