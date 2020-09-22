package main.arquivo;

import java.io.FileInputStream;

public class ArquivoUtils {
    public static StringBuilder lerArquivo(String filename) {
        try {
            StringBuilder resultado = new StringBuilder();
            FileInputStream fileInputStream = new FileInputStream(filename);

            int caracterRead = fileInputStream.read();
            while (caracterRead != -1) {
                resultado.append((char) caracterRead);
                caracterRead = fileInputStream.read();
            }

            fileInputStream.close();
            return resultado;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
