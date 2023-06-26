package com.example.gestioncitasparadise.dto;

public class Especialidad {

    String Id_Especialidad, NombreEspecialidad, DescripcionEspecialidad;

    public Especialidad() {
    }

    public Especialidad(String id_Especialidad, String nombreEspecialidad, String descripcionEspecialidad) {
        Id_Especialidad = id_Especialidad;
        NombreEspecialidad = nombreEspecialidad;
        DescripcionEspecialidad = descripcionEspecialidad;
    }
    public String getId_Especialidad() {
        return Id_Especialidad;
    }

    public void setId_Especialidad(String id_Especialidad) {
        Id_Especialidad = id_Especialidad;
    }

    public String getNombreEspecialidad() {
        return NombreEspecialidad;
    }

    public void setNombreEspecialidad(String nombreEspecialidad) {
        NombreEspecialidad = nombreEspecialidad;
    }

    public String getDescripcionEspecialidad() {
        return DescripcionEspecialidad;
    }

    public void setDescripcionEspecialidad(String descripcionEspecialidad) {
        DescripcionEspecialidad = descripcionEspecialidad;
    }
    @Override
    public String toString() {
        return NombreEspecialidad;
    }

}
