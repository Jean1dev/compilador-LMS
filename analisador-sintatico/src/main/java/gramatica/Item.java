package gramatica;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;

@JacksonXmlRootElement(localName = "items")
@Getter
public class Item {

    @JacksonXmlProperty(isAttribute = true, localName = "codigo")
    private String codigo;

    @JacksonXmlProperty(isAttribute = true, localName = "derivacao")
    private String derivacao;
}
