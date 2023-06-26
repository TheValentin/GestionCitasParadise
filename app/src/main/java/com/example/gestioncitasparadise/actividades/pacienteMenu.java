package com.example.gestioncitasparadise.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gestioncitasparadise.R;
import com.example.gestioncitasparadise.actividades.uiDoctor.DatosDoctorFragment;
import com.example.gestioncitasparadise.actividades.uiPaciente.DatosFragment;
import com.example.gestioncitasparadise.actividades.uiPaciente.HistorialFragment;
import com.example.gestioncitasparadise.actividades.uiPaciente.PerfilPacienteFragment;
import com.example.gestioncitasparadise.actividades.uiPaciente.ReservaFragment;
import com.example.gestioncitasparadise.dto.Paciente;
import com.example.gestioncitasparadise.login.IniciarSessionActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class pacienteMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;
    FragmentManager fragmentManager;
    Toolbar toolbar;

    public static ArrayList<Paciente> PacienteArrayList =new ArrayList<>();
    Paciente paciente;
    String usuario;
    String contraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_menu);
        SharedPreferences sharedPreferences = getSharedPreferences("mis_preferencias", Context.MODE_PRIVATE);
        usuario = sharedPreferences.getString("usuario", "");
        contraseña = sharedPreferences.getString("contraseña", "");
        LoginUsuario(usuario,contraseña);

        toolbar = findViewById(R.id.toobar_Paciente);
        setSupportActionBar(toolbar);

        drawerLayout= findViewById(R.id.menuPaciente);
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView= findViewById(R.id.navigation_drawer_Paciente);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = findViewById(R.id.bottom_navigation_Paciente);
        bottomNavigationView.setBackground(null);

        //menu inferior
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemID=item.getItemId();

                if (itemID==R.id.paga_datos_paciente){
                    //toolbar.setTitle(R.string.nav_Historial);
                    openFragment(new DatosFragment());
                    return true;
                }else if (itemID==R.id.page_reserva_paciente){
                    //toolbar.setTitle(R.string.nav_Doctor);
                    openFragment(new ReservaFragment());
                    return true;
                }else if (itemID==R.id.page_historial_paciente){
                    //toolbar.setTitle(R.string.nav_Especialidad);
                    openFragment(new HistorialFragment());
                    return true;
                }


                return false;
            }
        });

        fragmentManager = getSupportFragmentManager();

        openFragment(new HistorialFragment());


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemID=item.getItemId();

        if (itemID==R.id.nav_drawer_home_paciente){
            toolbar.setTitle(R.string.nav_perfil);
            openFragment(new PerfilPacienteFragment());
        }else if (itemID==R.id.nav_drawer_casa_paciente){
            toolbar.setTitle(R.string.nav_help);
            Toast.makeText(this,"Home",Toast.LENGTH_SHORT).show();
        }else if (itemID==R.id.nav_drawer_salir_paciente){
            cerrarSesion();
        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }


    private void cerrarSesion() {

        SharedPreferences sharedPreferences = getSharedPreferences("mis_preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("usuario");
        editor.remove("contraseña");
        editor.clear(); // Eliminar todas las preferencias
        editor.apply(); // Aplicar los cambios

        // Redirigir a la pantalla de inicio de sesión
        Intent intent = new Intent(this, IniciarSessionActivity.class);
        startActivity(intent);
        finish(); // Finalizar la actividad actual para evitar que el usuario vuelva atrás a la sesión cerrada
    }
    private void openFragment(Fragment fragment){
        LoginUsuario(usuario,contraseña);
        FragmentTransaction transaction= fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container_Paciente, fragment);
        transaction.commit();
    }

    public void LoginUsuario(String email, String passwork){
        String urlDetalle="https://thevalentin.000webhostapp.com/Proyectos/ServidorGestionCitas/LoginUsuario.php";

        StringRequest request =new StringRequest(Request.Method.POST, urlDetalle,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        PacienteArrayList.clear();
                        try {
                            JSONObject jsonObject =new JSONObject(response);
                            String exito=jsonObject.getString("exito");
                            JSONArray jsonArray =jsonObject.getJSONArray("datos");

                            if (exito.equals("1")){
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject object=jsonArray.getJSONObject(i);
                                    String id=object.getString("id_paciente");
                                    String nombre=object.getString("nombre");
                                    String apellido=object.getString("apellido");
                                    String dni=object.getString("dni");
                                    String Fecha=object.getString("fecha_nacimiento");
                                    String telefono=object.getString("telefono");
                                    String direccion=object.getString("direccion");
                                    String email=object.getString("email");
                                    String rol=object.getString("rol");
                                    paciente =new Paciente(id,nombre,apellido,dni,Fecha,telefono,direccion,email,rol);
                                    PacienteArrayList.add(paciente);

                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(pacienteMenu.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String>  params=new HashMap<String,String>();
                params.put("email",email);
                params.put("password",passwork);
                return params;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(pacienteMenu.this);
        requestQueue.add(request);

    }



}