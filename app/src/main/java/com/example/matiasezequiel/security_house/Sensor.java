package com.example.matiasezequiel.security_house;

public class Sensor {
    private int idSensor;
    private int idZona;
    private String nombre;

    public Sensor(int idSensor, int idZona, String nombre) {
        this.idSensor = idSensor;
        this.idZona = idZona;
        this.nombre = nombre;
    }

    public int getIdSensor() {
        return idSensor;
    }

    public int getIdZona() {
        return idZona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
