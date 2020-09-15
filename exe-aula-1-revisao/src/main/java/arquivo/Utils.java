package arquivo;

import java.io.*;

public class Utils {
    private static final String DEFAULT_FILE_NAME = "arquivo.txt";

    public static void gravarArquivo(String content) {
        if (content.length() > 100)
            throw new RuntimeException("nao pode ter mais que 100 caracater");

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

    public static StringBuilder lerArquivo() {
        try {
            StringBuilder resultado = new StringBuilder();
            FileInputStream fileInputStream = new FileInputStream(new File(DEFAULT_FILE_NAME));

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
