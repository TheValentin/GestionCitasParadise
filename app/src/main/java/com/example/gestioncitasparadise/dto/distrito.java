package com.example.gestioncitasparadise.dto;

public class distrito {

    String id_distrito, DistritoNombre;

    public distrito(String id_distrito, String distritoNombre) {
        this.id_distrito = id_distrito;
        DistritoNombre = distritoNombre;
    }

    public distrito() {
    }

    public String getId_distrito() {
        return id_distrito;
    }

    public void setId_distrito(String id_distrito) {
        this.id_distrito = id_distrito;
    }

    public String getDistritoNombre() {
        return DistritoNombre;
    }

    public void setDistritoNombre(String distritoNombre) {
        DistritoNombre = distritoNombre;
    }
}
