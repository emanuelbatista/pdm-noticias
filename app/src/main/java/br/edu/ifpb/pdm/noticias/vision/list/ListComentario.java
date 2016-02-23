package br.edu.ifpb.pdm.noticias.vision.list;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.util.List;

import br.edu.ifpb.pdm.noticias.R;
import br.edu.ifpb.pdm.noticias.entity.Comentario;

/**
 * Adiciona os valores do comentários em um {@link android.widget.ListView}
 * Created by emanuel on 23/02/16.
 */
public class ListComentario extends ArrayAdapter<Comentario> {

    public ListComentario(Context context, List<Comentario> objects) {
        super(context, 0, objects);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Comentario comentario = getItem(position);
        //
        convertView= LayoutInflater.from(getContext()).inflate(R.layout.list_comentario,null);
        //
        ImageView imageView=(ImageView)convertView.findViewById(R.id.imagem);
        imageView.setBackgroundDrawable(Drawable.createFromStream(new ByteArrayInputStream(comentario.getImagem()),comentario.getComentario()));
        //
        TextView comentarioView=(TextView) convertView.findViewById(R.id.comentario);
        comentarioView.setText(comentario.getComentario());

        return convertView;

    }
}
