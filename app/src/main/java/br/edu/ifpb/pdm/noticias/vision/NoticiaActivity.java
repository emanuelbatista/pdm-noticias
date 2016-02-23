package br.edu.ifpb.pdm.noticias.vision;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;

import br.edu.ifpb.pdm.noticias.R;
import br.edu.ifpb.pdm.noticias.entity.Noticia;
import br.edu.ifpb.pdm.noticias.repository.RepositoryNoticia;

public class NoticiaActivity extends AppCompatActivity {

    public static final String ID_NOTICIA="idNoticia";

    private Noticia noticia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia);
        Long idNoticia=(Long)this.getIntent().getSerializableExtra(ID_NOTICIA);
        //
        RepositoryNoticia repositoryNoticia = RepositoryNoticia.getInstance();
        this.noticia= repositoryNoticia.getNoticia(idNoticia);
        createVisiao();

    }

    private void createVisiao(){
        TextView tituloView=(TextView)findViewById(R.id.titulo);
        tituloView.setText(noticia.getTitutlo());
        //
        TextView noticiaView=(TextView)findViewById(R.id.noticia);
        noticiaView.setText(noticia.getNoticia());
        //
        ImageView imageView=(ImageView)findViewById(R.id.image_noticia);
        imageView.setBackgroundDrawable(Drawable.createFromStream(new ByteArrayInputStream(noticia.getImage()), noticia.getTitutlo()));

    }

    public void buttonClick(View v){
        Intent intent=new Intent(this,ComentariosActivity.class);
        intent.putExtra(NoticiaActivity.ID_NOTICIA,noticia.getId());
        startActivity(intent);
    }
}
