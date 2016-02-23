package br.edu.ifpb.pdm.noticias.entity;

import java.io.Serializable;

/**
 * Representa a categoria que a noticia está inserida
 * Created by emanuel on 19/02/16.
 */
public enum NoticiaCategory implements Serializable{

    ESPORTE("Esporte"),CONCURSO_EMPREGO("Concurso e Emprego"),
    POLITICA("Politica"),CIENCIA_TECNOLOGIA("Ciência e Tecnologia"),
    ECONOMIA("Economia");

    private String categoria;

    NoticiaCategory(String categoria) {
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * Retorna a categoria da noticia de acordo com o nome representante da categoria
     * @param name
     * @return Tipo de Noticia
     */
    public static NoticiaCategory categoryValueOf(String name){
       switch (name){
           case "Esporte":{
               return NoticiaCategory.ESPORTE;
           }
           case "Concurso e Emprego":{
               return NoticiaCategory.CONCURSO_EMPREGO;
           }
           case "Politica":{
               return NoticiaCategory.POLITICA;
           }
           case "Ciência e Tecnologia":{
               return NoticiaCategory.CIENCIA_TECNOLOGIA;
           }
           case "Economia":{
               return NoticiaCategory.ECONOMIA;
           }
       }
        return null;
    }

    @Override
    public String toString() {
        return "NoticiaCategory{" +
                "categoria='" + categoria + '\'' +
                '}';
    }
}
