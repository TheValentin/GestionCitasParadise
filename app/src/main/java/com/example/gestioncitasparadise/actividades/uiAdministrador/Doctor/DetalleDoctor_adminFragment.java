package com.example.gestioncitasparadise.actividades.uiAdministrador.Doctor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gestioncitasparadise.R;
import com.example.gestioncitasparadise.dto.Doctor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class  DetalleDoctor_adminFragment extends Fragment {


    private TextView txtNombreDoctor,txtEspecialidad,txtSedesDoctor, txtApellidoDoctor, txtDniDoctor,txtTelefonoDoctor,txtDirrecionDoctor, txtdistritoDocto, txtidDoctor, txtEmailDoctor;
    private ImageView ImagenAtras;

    public static DetalleDoctor_adminFragment newInstance(int data) {
        DetalleDoctor_adminFragment fragment = new DetalleDoctor_adminFragment();
        Bundle args = new Bundle();
        args.putInt("position", data);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalle_doctor_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtNombreDoctor=view.findViewById(R.id.TxtNombreDoctorDetalle);
        txtEspecialidad=view.findViewById(R.id.txtEspecialidadDoctorDetalle);
        txtApellidoDoctor=view.findViewById(R.id.txtApellidoDoctorDetalle);
        txtDniDoctor=view.findViewById(R.id.txtDniDoctorDetalle);
        txtTelefonoDoctor=view.findViewById(R.id.txtTelefonoDoctorDetalle);
        txtDirrecionDoctor=view.findViewById(R.id.txtdireccionDoctorDetalle);
        txtdistritoDocto=view.findViewById(R.id.txtDistritoDoctorDetalle);
        txtidDoctor=view.findViewById(R.id.txtIdDoctorDetalle);
        txtEmailDoctor=view.findViewById(R.id.txtEmailDoctorDetalle);
        txtSedesDoctor=view.findViewById(R.id.txtSedeDoctorDetalle);

        ImagenAtras=view.findViewById(R.id.imgAtras);
        ImagenAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        Bundle args = getArguments();
        if (args != null) {
            int position = args.getInt("position");

            String nombreDoctor = ListarDoctor_AdministradorFragment.DoctorArrayList.get(position).getNombre_doctor();
            String nombreDoctorFormateado = Character.toUpperCase(nombreDoctor.charAt(0)) + nombreDoctor.substring(1).toLowerCase();
            txtNombreDoctor.setText("Dr. "+nombreDoctorFormateado);
            txtApellidoDoctor.setText(ListarDoctor_AdministradorFragment.DoctorArrayList.get(position).getApellido_doctor());
            txtEspecialidad.setText(ListarDoctor_AdministradorFragment.DoctorArrayList.get(position).getNombreEspecialidad());
            txtDniDoctor.setText(ListarDoctor_AdministradorFragment.DoctorArrayList.get(position).getDni_doctor());
            txtTelefonoDoctor.setText(ListarDoctor_AdministradorFragment.DoctorArrayList.get(position).getTelefono_doctor());
            txtDirrecionDoctor.setText(ListarDoctor_AdministradorFragment.DoctorArrayList.get(position).getDireccion_doctor());
            txtdistritoDocto.setText(ListarDoctor_AdministradorFragment.DoctorArrayList.get(position).getNombreDistrito());
            txtidDoctor.setText(""+ListarDoctor_AdministradorFragment.DoctorArrayList.get(position).getId_doctor());
            txtEmailDoctor.setText(ListarDoctor_AdministradorFragment.DoctorArrayList.get(position).getEmail_doctor());
            txtSedesDoctor.setText(ListarDoctor_AdministradorFragment.DoctorArrayList.get(position).getNombreSede());




        }
    }
}