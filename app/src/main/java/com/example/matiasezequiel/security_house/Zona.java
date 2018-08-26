package com.example.matiasezequiel.security_house;

public class Zona {
    private int idZona;
    private int idAlarma;
    private String nombre;

    public Zona(int idZona, int idAlarma, String nombre) {
        this.idZona = idZona;
        this.idAlarma = idAlarma;
        this.nombre = nombre;
    }

    public int getIdZona() {
        return idZona;
    }

    public int getIdAlarma() {
        return idAlarma;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
