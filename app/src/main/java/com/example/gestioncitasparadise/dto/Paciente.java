package com.example.gestioncitasparadise.dto;

public class Paciente {
    String id_paciente, nombrePaciente, apellidoPaciente, dniPaciente, FechaPaciente, TelefonoPaciente, DireccionPaciente, EmailPaciente,rolPaciente;

    public Paciente() {
    }

    public Paciente(String id_paciente, String nombrePaciente, String apellidoPaciente, String dniPaciente, String fechaPaciente, String telefonoPaciente, String direccionPaciente, String emailPaciente, String rolPaciente) {
        this.id_paciente = id_paciente;
        this.nombrePaciente = nombrePaciente;
        this.apellidoPaciente = apellidoPaciente;
        this.dniPaciente = dniPaciente;
        this.FechaPaciente = fechaPaciente;
        this.TelefonoPaciente = telefonoPaciente;
        this.DireccionPaciente = direccionPaciente;
        this.EmailPaciente = emailPaciente;
        this.rolPaciente=rolPaciente;
    }

    public String getRolPaciente() {
        return rolPaciente;
    }

    public void setRolPaciente(String rolPaciente) {
        this.rolPaciente = rolPaciente;
    }

    public String getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(String id_paciente) {
        this.id_paciente = id_paciente;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getApellidoPaciente() {
        return apellidoPaciente;
    }

    public void setApellidoPaciente(String apellidoPaciente) {
        this.apellidoPaciente = apellidoPaciente;
    }

    public String getDniPaciente() {
        return dniPaciente;
    }

    public void setDniPaciente(String dniPaciente) {
        this.dniPaciente = dniPaciente;
    }

    public String getFechaPaciente() {
        return FechaPaciente;
    }

    public void setFechaPaciente(String fechaPaciente) {
        FechaPaciente = fechaPaciente;
    }

    public String getTelefonoPaciente() {
        return TelefonoPaciente;
    }

    public void setTelefonoPaciente(String telefonoPaciente) {
        TelefonoPaciente = telefonoPaciente;
    }

    public String getDireccionPaciente() {
        return DireccionPaciente;
    }

    public void setDireccionPaciente(String direccionPaciente) {
        DireccionPaciente = direccionPaciente;
    }

    public String getEmailPaciente() {
        return EmailPaciente;
    }

    public void setEmailPaciente(String emailPaciente) {
        EmailPaciente = emailPaciente;
    }
}
