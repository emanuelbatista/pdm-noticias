package br.edu.ifpb.pdm.noticias;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import br.edu.ifpb.pdm.noticias.entity.NoticiaCategory;
import br.edu.ifpb.pdm.noticias.vision.AddNoticia;
import br.edu.ifpb.pdm.noticias.vision.NoticiasActivity;


public class MainActivity extends AppCompatActivity {

    public static String NOTICIA_CATEGORIA="NoticiaCategoria";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void clickEsporteNoticia(View v){
       openNoticia(NoticiaCategory.ESPORTE);
    }
    public void clickPoliticaNoticia(View v){
       openNoticia(NoticiaCategory.POLITICA);
    }
    public void clickConcurdoEmpregoNoticia(View v){
       openNoticia(NoticiaCategory.CONCURSO_EMPREGO);
    }
    public void clickCienciaTecnologiaNoticia(View v){
       openNoticia(NoticiaCategory.CIENCIA_TECNOLOGIA);
    }
    public void clickEconomiaNoticia(View v){
       openNoticia(NoticiaCategory.ECONOMIA);
    }

    public void openNoticia(NoticiaCategory category){
        Intent intent=new Intent(this, NoticiasActivity.class);
        intent.putExtra(MainActivity.NOTICIA_CATEGORIA,category);
        startActivity(intent);
    }
    public void clickAddNoticia(View v){
        Intent intent=new Intent(this, AddNoticia.class);
        startActivity(intent);
    }
}
