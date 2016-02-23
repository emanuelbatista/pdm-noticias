package br.edu.ifpb.pdm.noticias.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma noticia
 *
 * Created by emanuel on 19/02/16.
 */
public class Noticia implements Serializable{

    private Long id;
    private byte[] image;
    private String titutlo;
    private String noticia;
    private NoticiaCategory category;
    private List<Comentario> comentarios;

    public Noticia() {
        this.comentarios = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public NoticiaCategory getCategory() {
        return category;
    }

    public void setCategory(NoticiaCategory category) {
        this.category = category;
    }

    public String getNoticia() {
        return noticia;
    }

    public void setNoticia(String noticia) {
        this.noticia = noticia;
    }

    public String getTitutlo() {
        return titutlo;
    }

    public void setTitutlo(String titutlo) {
        this.titutlo = titutlo;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
}
