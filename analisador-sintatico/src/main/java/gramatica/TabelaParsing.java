package gramatica;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;

import java.util.Collection;

@JacksonXmlRootElement(localName = "tabelaParsing")
@Getter
public class TabelaParsing {

    @JacksonXmlProperty(localName = "items")
    private Collection<Item> item;
}
