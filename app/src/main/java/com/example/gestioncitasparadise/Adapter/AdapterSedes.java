package com.example.gestioncitasparadise.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.gestioncitasparadise.R;
import com.example.gestioncitasparadise.dto.Doctor;
import com.example.gestioncitasparadise.dto.Sedes;

import java.util.List;

public class AdapterSedes extends ArrayAdapter {
    Context context;

    List<Sedes> SedesLista;


    public AdapterSedes(@NonNull Context context, List<Sedes> SedesLista) {
        super(context, R.layout.lista_sedes,SedesLista);
        this.context=context;
        this.SedesLista=SedesLista;
    }

    public View getView(int position, @NonNull View Context , ViewGroup resource) {
        View view= LayoutInflater.from(resource.getContext()).inflate(R.layout.lista_sedes,null,true);
        TextView txtNombreSede=view.findViewById(R.id.txtlistaSedes);
        txtNombreSede.setText(SedesLista.get(position).getNombreSede());
        return view;
    }

}
