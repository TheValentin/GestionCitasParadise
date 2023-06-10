package com.example.gestioncitasparadise.interfaces;

import androidx.fragment.app.Fragment;

public interface MetodosCrud {

    void Eliminar(final  int id);

    void Agregar();
    void ListarDatos();
    void Actualizar(int position);

    void openFragment(Fragment fragment);
}
