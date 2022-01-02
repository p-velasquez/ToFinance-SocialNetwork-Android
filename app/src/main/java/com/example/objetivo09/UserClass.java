package com.example.objetivo09;

public class UserClass {
    int id_usuario;
    String userName;
    int userPhoto;
    String bio;

    public UserClass(int id_usuario, String userName, int userPhoto, String bio) {
        this.id_usuario = id_usuario;
        this.userName = userName;
        this.userPhoto = userPhoto;
        this.bio = bio;
    }

    public UserClass(int id_usuario, String userName, int userPhoto) {
        this.id_usuario = id_usuario;
        this.userName = userName;
        this.userPhoto = userPhoto;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(int userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
