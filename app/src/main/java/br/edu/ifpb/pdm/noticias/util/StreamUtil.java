package br.edu.ifpb.pdm.noticias.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import br.edu.ifpb.pdm.noticias.R;

/**
 * Classe utilitaria, com finalidade de manipular Stream
 *
 * Created by emanuel on 23/02/16.
 */
public class StreamUtil {

    /**
     * Retorna os bytes de dado do tipo {@link InputStream}
     * @param in
     * @return
     * @throws IOException
     */
    public static byte[] convertBytes(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int lenght;

        while ((lenght = in.read(bytes)) != -1) {
            out.write(bytes, 0, lenght);
        }

        return out.toByteArray();
    }
}
