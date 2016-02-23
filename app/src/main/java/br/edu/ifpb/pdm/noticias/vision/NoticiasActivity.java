package br.edu.ifpb.pdm.noticias.vision;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.pdm.noticias.MainActivity;
import br.edu.ifpb.pdm.noticias.R;
import br.edu.ifpb.pdm.noticias.entity.Noticia;
import br.edu.ifpb.pdm.noticias.entity.NoticiaCategory;
import br.edu.ifpb.pdm.noticias.repository.RepositoryNoticia;

public class NoticiasActivity extends AppCompatActivity {

    private RepositoryNoticia repositoryNoticia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_noticias, null);
        setContentView(view);
        //
        repositoryNoticia = RepositoryNoticia.getInstance();
        //
        NoticiaCategory noticiaCategory=(NoticiaCategory)this.getIntent().
                getSerializableExtra(MainActivity.NOTICIA_CATEGORIA);
        addTituloCategoria(noticiaCategory.getCategoria());
        addListaNoticia(noticiaCategory);

    }

    private void addTituloCategoria(String titulo){
       TextView tituloNoticias=(TextView)findViewById(R.id.titulo);
        StringBuilder stringBuilder=new StringBuilder();
        String novoTitulo=stringBuilder.append(tituloNoticias.getText()).append(titulo).toString();
        tituloNoticias.setText(novoTitulo);
    }

    /**
     * Add as noticias numa lista na tela
     */
    private void addListaNoticia(NoticiaCategory noticiaCategory) {
        ViewGroup root = (ViewGroup) findViewById(R.id.content_view);
        List<Noticia> noticias =listPorCategoria(noticiaCategory);
        //
        for (Noticia noticia : noticias) {
            LinearLayout listNoticia = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.list_noticia, null);
            listNoticia.setOnClickListener(clickList());
            //
            //
            ImageView imagem=(ImageView)listNoticia.findViewById(R.id.image_noticia);
            //add imagem da noticia
            imagem.setBackgroundDrawable(Drawable.createFromStream(new ByteArrayInputStream(noticia.getImage()), String.valueOf(noticia.getId())));
            //
            //
            //add titulo da noticia
            TextView tituloNoticiaView=(TextView)listNoticia.findViewById(R.id.title_noticia);
            tituloNoticiaView.setText(noticia.getTitutlo());
            //
            //
            //add texto noticia
            TextView textNoticia=(TextView)listNoticia.findViewById(R.id.text_noticia);
            int tamanhoMinTexto=noticia.getNoticia().length()>25?25:noticia.getNoticia().length();
            String texto=noticia.getNoticia().substring(0,tamanhoMinTexto)+"...";
            textNoticia.setText(texto);
            //
            TextView idNoticia=(TextView)listNoticia.findViewById(R.id.id_noticia);
            idNoticia.setText(String.valueOf(noticia.getId()));
            root.addView(listNoticia);
        }
    }

    private View.OnClickListener clickList(){
       return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView idNoticia=(TextView) v.findViewById(R.id.id_noticia);
                Intent intent=new Intent(NoticiasActivity.this, NoticiaActivity.class);
                intent.putExtra(NoticiaActivity.ID_NOTICIA,Long.valueOf(idNoticia.getText().toString()));
                startActivity(intent);
            }
        };
    }

    private List<Noticia> listPorCategoria(NoticiaCategory category){
        List<Noticia> noticiaList= repositoryNoticia.getNoticias();
        List<Noticia> noticiasListCategoria=new ArrayList<>();
        //
        for (Noticia noticia:noticiaList){
            if(noticia.getCategory().equals(category)){
                noticiasListCategoria.add(noticia);
            }
        }
        //
        return noticiasListCategoria;
    }
}
