package gramatica;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;

public final class XMLInstanciador {

    public static CatalogoPalavra getCatalogoPalavras() {
        File xml = getFile("gramatica.xml");

        try {
            CatalogoPalavra catalogoPalavra = new XmlMapper().readValue(xml, CatalogoPalavra.class);
            return catalogoPalavra;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static TabelaParsing getTabelaParser() {
        File xml = getFile("tabelaParsing_sem_acoes_semanticas.xml");

        try {
            TabelaParsing tabelaParsing = new XmlMapper().readValue(xml, TabelaParsing.class);
            return tabelaParsing;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private static File getFile(String path) {
        return new File(XMLInstanciador.class.getClassLoader().getResource(path).getFile());
    }
}
