package com.example.objetivo09;

public class PostClass extends UserClass{
    private int id_publicacion;
    private String description;
    private int postImage;

    public PostClass(int id_usuario, String userName, int userPhoto, int id_publicacion, String description, int postImage) {
        super(id_usuario, userName, userPhoto);
        this.id_publicacion = id_publicacion;
        this.description = description;
        this.postImage = postImage;
    }

    public int getId_publicacion() {
        return id_publicacion;
    }

    public void setId_publicacion(int id_publicacion) {
        this.id_publicacion = id_publicacion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPostImage() {
        return postImage;
    }

    public void setPostImage(int postImage) {
        this.postImage = postImage;
    }
}
