package classificacao;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ItemClassificador {

    private String nomeIdentificador;

    private Categoria categoria;

    private String tipo;

    private Integer nivel;
}
