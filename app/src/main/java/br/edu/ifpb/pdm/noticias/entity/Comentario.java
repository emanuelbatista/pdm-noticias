package br.edu.ifpb.pdm.noticias.entity;

import java.io.Serializable;

/**
 * Representa o comentário de noticias
 *
 * Created by emanuel on 23/02/16.
 */
public class Comentario implements Serializable{

    private byte[] imagem;
    private String comentario;


    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
