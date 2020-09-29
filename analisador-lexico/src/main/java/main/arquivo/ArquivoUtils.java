package main.arquivo;

import java.io.*;

@Deprecated
public class ArquivoUtils {

    private static final String DEFAULT_FILE_NAME = "arquivo.txt";

    @Deprecated
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

    public static void gravarArquivo(String content) {
        try {
            File file = new File(DEFAULT_FILE_NAME);
            FileOutputStream stream = new FileOutputStream(file);

            PrintStream printStream = new PrintStream(stream);
            printStream.print(content);
            printStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
