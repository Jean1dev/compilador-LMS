package gramatica;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SimboloComCodigo {

    private Integer codigo;

    private String simbolo;

    public static SimboloComCodigo simboloInicial() {
        return new SimboloComCodigo(52, "PROGRAMA");
    }
}
