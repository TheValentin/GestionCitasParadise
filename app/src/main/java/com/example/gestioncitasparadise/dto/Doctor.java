package com.example.gestioncitasparadise.dto;

public class Doctor {
    int id_doctor,id_especialidad,id_distrito,id_sedes;
    String nombre_doctor,apellido_doctor,dni_doctor, telefono_doctor,direccion_doctor,email_doctor,password_doctor,rol;
    String NombreEspecialidad,NombreDistrito, NombreSede;

    public Doctor() {
    }

    public Doctor(int id_doctor, int id_especialidad, int id_distrito, int id_sedes, String nombre_doctor, String apellido_doctor, String dni_doctor, String telefono_doctor, String direccion_doctor, String email_doctor, String password_doctor, String rol) {
        this.id_doctor = id_doctor;
        this.id_especialidad = id_especialidad;
        this.id_distrito = id_distrito;
        this.id_sedes = id_sedes;
        this.nombre_doctor = nombre_doctor;
        this.apellido_doctor = apellido_doctor;
        this.dni_doctor = dni_doctor;
        this.telefono_doctor = telefono_doctor;
        this.direccion_doctor = direccion_doctor;
        this.email_doctor = email_doctor;
        this.password_doctor = password_doctor;
        this.rol = rol;
    }

    public Doctor(int id_doctor,String nombre_doctor, String nombreEspecialidad) {
        this.id_doctor = id_doctor;
        this.nombre_doctor = nombre_doctor;
        this.NombreEspecialidad = nombreEspecialidad;
    }

    public Doctor(int id_doctor, String NombreDistrito, String NombreSede, String nombre_doctor, String apellido_doctor, String dni_doctor, String telefono_doctor, String direccion_doctor, String email_doctor,String nombreEspecialidad) {
        this.id_doctor = id_doctor;
        this.NombreDistrito = NombreDistrito;
        this.NombreSede = NombreSede;
        this.nombre_doctor = nombre_doctor;
        this.apellido_doctor = apellido_doctor;
        this.dni_doctor = dni_doctor;
        this.telefono_doctor = telefono_doctor;
        this.direccion_doctor = direccion_doctor;
        this.email_doctor = email_doctor;
        this.NombreEspecialidad = nombreEspecialidad;
    }

    public String getNombreDistrito() {
        return NombreDistrito;
    }

    public void setNombreDistrito(String nombreDistrito) {
        NombreDistrito = nombreDistrito;
    }

    public String getNombreSede() {
        return NombreSede;
    }

    public void setNombreSede(String nombreSede) {
        NombreSede = nombreSede;
    }

    public String getNombreEspecialidad() {
        return NombreEspecialidad;
    }

    public void setNombreEspecialidad(String nombreEspecialidad) {
        NombreEspecialidad = nombreEspecialidad;
    }

    public int getId_doctor() {
        return id_doctor;
    }

    public void setId_doctor(int id_doctor) {
        this.id_doctor = id_doctor;
    }

    public int getId_especialidad() {
        return id_especialidad;
    }

    public void setId_especialidad(int id_especialidad) {
        this.id_especialidad = id_especialidad;
    }

    public int getId_distrito() {
        return id_distrito;
    }

    public void setId_distrito(int id_distrito) {
        this.id_distrito = id_distrito;
    }

    public int getId_sedes() {
        return id_sedes;
    }

    public void setId_sedes(int id_sedes) {
        this.id_sedes = id_sedes;
    }

    public String getNombre_doctor() {
        return nombre_doctor;
    }

    public void setNombre_doctor(String nombre_doctor) {
        this.nombre_doctor = nombre_doctor;
    }

    public String getApellido_doctor() {
        return apellido_doctor;
    }

    public void setApellido_doctor(String apellido_doctor) {
        this.apellido_doctor = apellido_doctor;
    }

    public String getDni_doctor() {
        return dni_doctor;
    }

    public void setDni_doctor(String dni_doctor) {
        this.dni_doctor = dni_doctor;
    }

    public String getTelefono_doctor() {
        return telefono_doctor;
    }

    public void setTelefono_doctor(String telefono_doctor) {
        this.telefono_doctor = telefono_doctor;
    }

    public String getDireccion_doctor() {
        return direccion_doctor;
    }

    public void setDireccion_doctor(String direccion_doctor) {
        this.direccion_doctor = direccion_doctor;
    }

    public String getEmail_doctor() {
        return email_doctor;
    }

    public void setEmail_doctor(String email_doctor) {
        this.email_doctor = email_doctor;
    }

    public String getPassword_doctor() {
        return password_doctor;
    }

    public void setPassword_doctor(String password_doctor) {
        this.password_doctor = password_doctor;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
