package com.example.matiasezequiel.security_house;

public class Zona {
    private int idZona;
    private int idAlarma;
    private String nombre;
    private int estado;
    private int notificacion;

    public Zona(int idZona, int idAlarma, String nombre, int estado, int notificacion) {
        this.idZona = idZona;
        this.idAlarma = idAlarma;
        this.nombre = nombre;
        this.estado = estado;
        this.notificacion = notificacion;
    }

    public int getIdZona() {
        return idZona;
    }

    public void setIdZona(int idZona) {
        this.idZona = idZona;
    }

    public int getIdAlarma() {
        return idAlarma;
    }

    public void setIdAlarma(int idAlarma) {
        this.idAlarma = idAlarma;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getNotificacion() {
        return notificacion;
    }

    public void setNotificacion(int notificacion) {
        this.notificacion = notificacion;
    }
}
