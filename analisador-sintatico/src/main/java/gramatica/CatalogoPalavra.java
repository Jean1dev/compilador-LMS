package gramatica;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;

import java.util.Collection;

@JacksonXmlRootElement(localName = "gramatica")
@Getter
public class CatalogoPalavra {

    @JacksonXmlProperty(localName = "terminais")
    private Collection<Terminais> terminais;

    @JacksonXmlProperty(localName = "naoTerminais")
    private Collection<NaoTerminais> naoTerminais;
}
