package br.edu.ifpb.pdm.noticias.vision;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import br.edu.ifpb.pdm.noticias.R;
import br.edu.ifpb.pdm.noticias.entity.Comentario;
import br.edu.ifpb.pdm.noticias.repository.RepositoryNoticia;
import br.edu.ifpb.pdm.noticias.util.StreamUtil;
import br.edu.ifpb.pdm.noticias.vision.list.ListComentario;

public class ComentariosActivity extends AppCompatActivity implements Validator.ValidationListener {

    private List<Comentario> comentarios;
    @Length(max = 40,message = "O comentário é limitado a 40 caracteres")
    @NotEmpty(message = "O comentário está vazio")
    private EditText comentarioText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cometarios);
        Long idNoticia=(Long)this.getIntent().getSerializableExtra(NoticiaActivity.ID_NOTICIA);
        this.comentarios=RepositoryNoticia.getInstance().getNoticia(idNoticia).getComentarios();
        ListComentario listComentario=new ListComentario(this, comentarios);
        //
        this.comentarioText=(EditText)findViewById(R.id.text_comentario);
        //
        ListView listView=(ListView)findViewById(R.id.list_comentario);
        listView.setAdapter(listComentario);
        //
    }

    public void comentar(View view){
        validar();
    }


    private void validar(){
        Validator validator=new Validator(this);
        validator.setValidationListener(this);
        validator.validate();

    }

    @Override
    public void onValidationSucceeded() {
        Comentario comentario=new Comentario();
        //
        InputStream in=getResources().openRawResource(R.raw.new_icon);
        try {
            comentario.setImagem(StreamUtil.convertBytes(in));
        } catch (IOException e) {
            e.printStackTrace();
        }

        comentario.setComentario(comentarioText.getText().toString());
        comentarioText.setText("");
        //
        ListView listView=(ListView)findViewById(R.id.list_comentario);
        ((ArrayAdapter<Comentario>)listView.getAdapter()).add(comentario);

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
       for(ValidationError error:errors){
           View v=error.getView();
           //
           if(v instanceof EditText){
               String message=error.getCollatedErrorMessage(this);
               ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.BLACK);
               SpannableString spannableString = new SpannableString(message);
               spannableString.setSpan(colorSpan, 0, message.length(), 0);
               ((EditText) v).setError(spannableString);
           }
       }
    }
}
