package com.example.matiasezequiel.security_house;

public class DatosItemZona {
    int id;
    String nombreZona;
    int imagen;

    public DatosItemZona(int id, String nombreZona, int imagen){
        this.id=id;
        this.nombreZona=nombreZona;
        this.imagen=imagen;
    }

    public int getId() {
        return id;
    }

    public String getNombreZona() {
        return nombreZona;
    }

    public int getImagen() {
        return imagen;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombreZona(String nombreZona) {
        this.nombreZona = nombreZona;
    }

    public void setImgen(int imagen) {
        this.imagen = imagen;
    }
}
