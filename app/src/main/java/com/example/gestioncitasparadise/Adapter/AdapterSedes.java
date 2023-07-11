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

    List<Sedes> sedesLista;


    public AdapterSedes(@NonNull Context context, List<Sedes> sedesLista) {
        super(context, R.layout.lista_sedes,sedesLista);
        this.context=context;
        this.sedesLista=sedesLista;
    }

    public View getView(int position, @NonNull View context , ViewGroup resource) {
        View view= LayoutInflater.from(resource.getContext()).inflate(R.layout.lista_sedes,null,true);
        TextView txtNombreSede=view.findViewById(R.id.txtlistaSedes);
        txtNombreSede.setText(sedesLista.get(position).getNombreSede());
        return view;
    }

}
