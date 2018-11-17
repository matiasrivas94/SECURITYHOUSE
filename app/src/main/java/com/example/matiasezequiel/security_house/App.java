package com.example.matiasezequiel.security_house;

public class App {
    private int idApp,premium;

    public App(int idApp, int premium){
        this.idApp = idApp;
        this.premium = premium;
    }

    public int getIdApp() {
        return idApp;
    }

    public void setIdApp(int idApp) {
        this.idApp = idApp;
    }

    public int getPremium() {
        return premium;
    }

    public void setPremium(int premium) {
        this.premium = premium;
    }
}
