package com.example.matiasezequiel.security_house;

public class Camara {
    private int idCamara;
    private String nombre;
    private String ip;
    private String usuario;
    private String contraseña;
    private int puerto;


    public Camara(int idCamara, String nombre, String ip, String usuario, String contraseña, int puerto) {
        this.idCamara = idCamara;
        this.nombre = nombre;
        this.ip = ip;
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.puerto = puerto;
    }

    public int getIdCamara() {
        return idCamara;
    }

    public void setIdCamara(int idCamara) {
        this.idCamara = idCamara;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }
}
