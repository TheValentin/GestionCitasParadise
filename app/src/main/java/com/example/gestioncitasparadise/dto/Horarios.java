package com.example.gestioncitasparadise.dto;

public class Horarios {

    String id_Horarios, id_doctor, Dia_semana,hora_inicio, hora_final;

    public Horarios() {
    }

    public Horarios(String id_Horarios, String id_doctor, String dia_semana, String hora_inicio, String hora_final) {
        this.id_Horarios = id_Horarios;
        this.id_doctor = id_doctor;
        Dia_semana = dia_semana;
        this.hora_inicio = hora_inicio;
        this.hora_final = hora_final;
    }

    public String getId_Horarios() {
        return id_Horarios;
    }

    public void setId_Horarios(String id_Horarios) {
        this.id_Horarios = id_Horarios;
    }

    public String getId_doctor() {
        return id_doctor;
    }

    public void setId_doctor(String id_doctor) {
        this.id_doctor = id_doctor;
    }

    public String getDia_semana() {
        return Dia_semana;
    }

    public void setDia_semana(String dia_semana) {
        Dia_semana = dia_semana;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHora_final() {
        return hora_final;
    }

    public void setHora_final(String hora_final) {
        this.hora_final = hora_final;
    }
}
