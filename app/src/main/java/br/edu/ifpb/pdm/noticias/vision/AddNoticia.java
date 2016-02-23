package br.edu.ifpb.pdm.noticias.vision;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.pdm.noticias.MainActivity;
import br.edu.ifpb.pdm.noticias.entity.Noticia;
import br.edu.ifpb.pdm.noticias.entity.NoticiaCategory;
import br.edu.ifpb.pdm.noticias.R;
import br.edu.ifpb.pdm.noticias.repository.RepositoryNoticia;
import br.edu.ifpb.pdm.noticias.util.StreamUtil;

public class AddNoticia extends AppCompatActivity implements Validator.ValidationListener {

    //Entitade noticia
    private Noticia noticia;
    //
    //Compoenentes visuais
    private ImageButton imageButton;
    @NotEmpty(message = "O titulo é obrigatório")
    @Length(max = 30,message = "O titulo só pode ter no máximo 30 caracteres")
    private EditText tituloView;
    @NotEmpty(message = "A noticia é obrigatório")
    private EditText noticiaView;
    private Spinner spinner;

    public AddNoticia() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_noticia);
        createSpinnerValues();
        mountViewComponent();
        this.noticia = new Noticia();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    private void mountViewComponent() {
        this.tituloView = (EditText) findViewById(R.id.tituloView);
        this.noticiaView = (EditText) findViewById(R.id.noticiaView);
        this.imageButton = (ImageButton) findViewById(R.id.image_button);
        InputStream in=getResources().openRawResource(R.raw.new_icon);
        imageButton.setBackgroundDrawable(Drawable.createFromStream(in, in.getClass().getSimpleName()));
        this.spinner=(Spinner)findViewById(R.id.categoria);
    }

    private void addImageComponent() {
        byte[] image = noticia.getImage();
        ByteArrayInputStream in = new ByteArrayInputStream(image);
        Drawable drawable = Drawable.createFromStream(in, in.toString());
        //
        imageButton.setBackgroundDrawable(drawable);

    }

    /**
     * Cria o spinner das categorias de noticias
     */
    private void createSpinnerValues() {
        Spinner spinner = (Spinner) findViewById(R.id.categoria);
        List<String> listSpinner = new ArrayList<>();
        for (NoticiaCategory noticiaCategory : NoticiaCategory.values()) {
            listSpinner.add(noticiaCategory.getCategoria());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listSpinner);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }

    /**
     * Chama a intent <em>chooser</em> do Android para add a imagem
     *
     * @param v
     */
    public void addImage(View v) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            addImagem(uri);
        }
        //
    }

    private void addImagem(Uri uri) {
        ImageButton imageButton = (ImageButton) findViewById(R.id.image_button);
        //
        try {
            //adiciona a imagem ao ImageButton
            InputStream in = getContentResolver().openInputStream(uri);
            imageButton.setBackgroundDrawable(Drawable.createFromStream(in, in.toString()));
            //
            //transforma a imagem em byte[]
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            in = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            byte[] imagem = out.toByteArray();
            //
            //imagem recuperada
            noticia.setImage(imagem);
            //
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void addNoticiaClick(View v) {
        //Validando
        Validator validator = new Validator(this);
        validator.setValidationListener(this);
        validator.validate();

    }

    @Override
    public void onValidationSucceeded() {
        addNoticiaClick();
    }

    private void addNoticiaClick() {
        //
        //Montando Entidade noticia
        noticia.setTitutlo(tituloView.getText().toString());
        noticia.setNoticia(noticiaView.getText().toString());
        NoticiaCategory noticiaCategory=NoticiaCategory.categoryValueOf(spinner.getSelectedItem().toString());
        noticia.setCategory(noticiaCategory);
        if(noticia.getImage()==null){
            InputStream in=getResources().openRawResource(R.raw.new_icon);
            try {
                noticia.setImage(StreamUtil.convertBytes(in));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //
        //Salvando Entidade
        RepositoryNoticia repositoryNoticia = RepositoryNoticia.getInstance();
        repositoryNoticia.addNoticia(noticia);
        //
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError erro : errors) {
            //View do erro
            View v = erro.getView();
            //
            //Messagem de erro
            String message = erro.getCollatedErrorMessage(this);
            //
            Log.i("message", message);
            //
            if (v instanceof EditText) {
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.BLUE);
                SpannableString spannableString = new SpannableString(message);
                spannableString.setSpan(colorSpan, 0, message.length(), 0);
                ((EditText) v).setError(spannableString);

            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }


        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putByteArray("imagem",noticia.getImage());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        noticia.setImage(savedInstanceState.getByteArray("imagem"));
        if (noticia.getImage() != null) {
            addImageComponent();
        }
    }
}

