package com.example.gestioncitasparadise.actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.gestioncitasparadise.R;
import com.example.gestioncitasparadise.login.IniciarSessionActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class pacienteMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_menu);
        setupNavegacion();
    }
    private void setupNavegacion(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavHostFragment navHostFragment= (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_hostFragment);
        NavigationUI.setupWithNavController(
                bottomNavigationView,navHostFragment.getNavController()
        );
    }


}