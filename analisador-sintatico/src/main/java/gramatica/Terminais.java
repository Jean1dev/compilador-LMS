package gramatica;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;

@JacksonXmlRootElement(localName = "terminais")
@Getter
public class Terminais {

    @JacksonXmlProperty(isAttribute = true, localName = "codigo")
    private String codigo;

    @JacksonXmlProperty(isAttribute = true, localName = "simbolo")
    private String simbolo;
}
