package com.example.objetivo09;

public class Notification {
    private String interaccion;
    private String usuario;
    private String publicacion;
    private int profileIcon;

    public Notification(String interaccion, String usuario, String publicacion, int profileIcon) {
        this.interaccion = interaccion;
        this.usuario = usuario;
        this.publicacion = publicacion;
        this.profileIcon = profileIcon;
    }

    public String getInteraccion() {
        return interaccion;
    }

    public void setInteraccion(String interaccion) {
        this.interaccion = interaccion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(String publicacion) {
        this.publicacion = publicacion;
    }

    public int getProfileIcon() {
        return profileIcon;
    }

    public void setProfileIcon(int profileIcon) {
        this.profileIcon = profileIcon;
    }
}