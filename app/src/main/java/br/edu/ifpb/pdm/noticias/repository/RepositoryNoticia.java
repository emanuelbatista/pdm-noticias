package br.edu.ifpb.pdm.noticias.repository;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.pdm.noticias.entity.Noticia;

/**
 * Repositório que contém todas as noticias do aplicativo
 * Created by emanuel on 19/02/16.
 */
public class RepositoryNoticia {

    //repositorios das entidades
    private List<Noticia> noticias;
    //
    private static RepositoryNoticia ourInstance = new RepositoryNoticia();

    public static RepositoryNoticia getInstance() {
        return ourInstance;
    }

    private RepositoryNoticia() {
        this.noticias = new ArrayList<>();
//                mountNoticias();
    }

//    private List<Noticia> mountNoticias() {
//        Noticia noticia=new Noticia();
//
//    }

    /**
     * Adiciona uma noticia no repositorio
     * @param noticia
     */
    public void addNoticia(Noticia noticia){
       int index=getNoticias().size();
        noticia.setId(Long.valueOf(String.valueOf(index)));
        getNoticias().add(index,noticia);
    }


    public List<Noticia> getNoticias() {
        return noticias;
    }

    /**
     * Retorna a noticia de acordo com o ID da noticia
     * @param id
     * @return Noticia
     */
    public Noticia getNoticia(Long id){
        for (Noticia noticia:getNoticias()){
            if(noticia.getId().equals(id)){
                return noticia;
            }
        }
        return null;
    }

    public void setNoticias(List<Noticia> noticias) {
        this.noticias = noticias;
    }

}
