package com.example.matiasezequiel.security_house;

public class Alarma {
    private int idAlarma,cantZonas;
    private String nombre,tipo,numTelefono,clave;

    public Alarma(int idAlarma, String nombre, String tipo, String numTelefono, String clave, int cantZonas) {
        this.idAlarma = idAlarma;
        this.nombre = nombre;
        this.tipo = tipo;
        this.numTelefono = numTelefono;
        this.clave=clave;
        this.cantZonas=cantZonas;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumTelefono() {
        return numTelefono;
    }

    public void setNumTelefono(String numTelefono) {
        this.numTelefono = numTelefono;
    }

    public int getCantZonas() {
        return cantZonas;
    }

    public String getClave() {
        return clave;
    }

    public void setCantZonas(int cantZonas) {
        this.cantZonas = cantZonas;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
