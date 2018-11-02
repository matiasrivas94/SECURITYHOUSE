package com.example.matiasezequiel.security_house;

public class Alarma {
    private int idAlarma;
    private String nombre,tipo,numTelefono,clave;

    public Alarma(int idAlarma, String nombre, String tipo, String numTelefono, String clave) {
        this.idAlarma = idAlarma;
        this.nombre = nombre;
        this.tipo = tipo;
        this.numTelefono = numTelefono;
        this.clave=clave;
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

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
