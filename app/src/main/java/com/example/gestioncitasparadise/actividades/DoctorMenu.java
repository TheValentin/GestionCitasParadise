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
import com.example.gestioncitasparadise.actividades.uiDoctor.DiagnosticoDoctorFragment;
import com.example.gestioncitasparadise.actividades.uiDoctor.Horarios.HorariosDoctorFragment;
import com.example.gestioncitasparadise.actividades.uiDoctor.ListaDoctorFragment;
import com.example.gestioncitasparadise.actividades.uiDoctor.PerfilDoctorFragment;
import com.example.gestioncitasparadise.dto.Doctor;
import com.example.gestioncitasparadise.dto.Horarios;
import com.example.gestioncitasparadise.login.IniciarSessionActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoctorMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;
    FragmentManager fragmentManager;
    Toolbar toolbar;
    public static ArrayList<Doctor> DoctorArrayList =new ArrayList<>();
    public static List<Horarios> HorariosArray= new ArrayList<>();
    Horarios horarios;
    Doctor doctor;
    String usuario;
    String contraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_menu);
        toolbar = findViewById(R.id.toobar_doctor);
        setSupportActionBar(toolbar);


        SharedPreferences sharedPreferences = getSharedPreferences("mis_preferencias", Context.MODE_PRIVATE);
        usuario = sharedPreferences.getString("usuario", "");
        contraseña = sharedPreferences.getString("contraseña", "");
        LoginUsuario(usuario,contraseña);
        drawerLayout= findViewById(R.id.menuDoctor);
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView= findViewById(R.id.navigation_drawer_doctor);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = findViewById(R.id.bottom_navigation_doctor);
        bottomNavigationView.setBackground(null);





        //menu inferior
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemID=item.getItemId();

                if (itemID==R.id.paga_DatosDoctor){
                    //toolbar.setTitle(R.string.nav_Historial);
                    openFragment(new DatosDoctorFragment());
                    return true;
                }else if (itemID==R.id.page_Lista_Doctor){
                    //toolbar.setTitle(R.string.nav_Doctor);
                    openFragment(new ListaDoctorFragment());
                    return true;
                }else if (itemID==R.id.page_Diagnostico_doctor){
                    //toolbar.setTitle(R.string.nav_Especialidad);
                    openFragment(new DiagnosticoDoctorFragment());
                    return true;
                }


                return false;
            }
        });
        fragmentManager = getSupportFragmentManager();
        openFragment(new DatosDoctorFragment());



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemID=item.getItemId();
        if (itemID==R.id.nav_drawer_home_doctor){
            toolbar.setTitle(R.string.nav_perfil);
            openFragment(new PerfilDoctorFragment());

        }else if (itemID==R.id.nav_drawer_horario_doctor){
            toolbar.setTitle(R.string.nav_horarios);
            openFragment(new HorariosDoctorFragment());

        }else if (itemID==R.id.nav_drawer_casa_doctor){
            toolbar.setTitle(R.string.nav_help);
            Toast.makeText(this,"Home",Toast.LENGTH_SHORT).show();
        }else if (itemID==R.id.nav_drawer_salir_doctor){
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

    public void cerrarSesion() {

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


    public void openFragment(Fragment fragment){
        FragmentTransaction transaction= fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container_doctor, fragment);
        transaction.commit();

    }
    public void LoginUsuario(String email, String passwork){
        String urlDetalle="https://thevalentin.000webhostapp.com/Proyectos/ServidorGestionCitas/LoginUsuario.php";

        StringRequest request =new StringRequest(Request.Method.POST, urlDetalle,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        DoctorArrayList.clear();
                        try {
                            JSONObject jsonObject =new JSONObject(response);
                            String exito=jsonObject.getString("exito");
                            JSONArray jsonArray =jsonObject.getJSONArray("datos");

                            if (exito.equals("1")){
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject object=jsonArray.getJSONObject(i);
                                    int id=object.getInt("id_doctor");
                                    String nombre=object.getString("nombre");
                                    String apellido=object.getString("apeliido");
                                    String dni=object.getString("dni");
                                    String telefono=object.getString("telefono");
                                    String direccion=object.getString("direccion");
                                    String email=object.getString("email");
                                    String especialidadNombre=object.getString("especialidadnombre");
                                    String nombreSede=object.getString("NombreSede");
                                    String NombreDistrito=object.getString("DistritoNombre");
                                    String rol=object.getString("rol");
                                    doctor =new Doctor(id,NombreDistrito,nombreSede,nombre,apellido,dni,telefono,direccion,email,especialidadNombre,rol);
                                    DoctorArrayList.add(doctor);

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DoctorMenu.this,error.getMessage(),Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(DoctorMenu.this);
        requestQueue.add(request);

    }
}