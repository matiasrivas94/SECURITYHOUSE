package com.example.matiasezequiel.security_house;

public class Camara {
    private int idCamara;
    private int idZona;
    private String tipo;
    private String numTelefono;
    private String ip;
    private String usuario;
    private String contraseña;
    private String codigoQR;


    public Camara(int idCamara, int idZona, String tipo, String numTelefono, String ip, String usuario, String contraseña, String codigoQR) {
        this.idCamara = idCamara;
        this.idZona = idZona;
        this.tipo = tipo;
        this.numTelefono = numTelefono;
        this.ip = ip;
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.codigoQR = codigoQR;
    }

    public int getIdCamara() {
        return idCamara;
    }

    public int getIdZona() {
        return idZona;
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

    public String getCodigoQR() {
        return codigoQR;
    }

    public void setCodigoQR(String codigoQR) {
        this.codigoQR = codigoQR;
    }
}
