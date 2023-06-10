package com.example.gestioncitasparadise.dto;

public class Sedes {
    String id_sedes, id_distrito, NombreSede;


    public Sedes(String id_sedes, String id_distrito, String nombreSede) {
        this.id_sedes = id_sedes;
        this.id_distrito = id_distrito;
        NombreSede = nombreSede;
    }

    public Sedes() {
    }

    public Sedes(String id_sedes, String nombreSede) {
        this.id_sedes = id_sedes;
        NombreSede = nombreSede;
    }

    public String getId_sedes() {
        return id_sedes;
    }

    public void setId_sedes(String id_sedes) {
        this.id_sedes = id_sedes;
    }

    public String getId_distrito() {
        return id_distrito;
    }

    public void setId_distrito(String id_distrito) {
        this.id_distrito = id_distrito;
    }

    public String getNombreSede() {
        return NombreSede;
    }

    public void setNombreSede(String nombreSede) {
        NombreSede = nombreSede;
    }
}
