package classificacao;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class TabelaClassificacao {

    private List<ItemClassificador> itens = new ArrayList<>();

    public Boolean remover(ItemClassificador item) {
        return itens.remove(item);
    }

    public Boolean inserir(ItemClassificador item) {
        if (buscar(item)) {
            itens.add(item);
            return true;
        }

        return false;
    }

    public void forcarInsercao(ItemClassificador itemClassificador) {
        itens.add(itemClassificador);
    }

    public Boolean buscar(ItemClassificador buscar) {
        if (Categoria.VARIAVEL.equals(buscar.getCategoria())) {
            ItemClassificador elemento = itens.stream()
                    .filter(item -> item.getNivel().equals(buscar.getNivel()))
                    .filter(item -> item.getNomeIdentificador().equalsIgnoreCase(buscar.getNomeIdentificador()))
                    .findFirst()
                    .orElse(null);

            return Objects.isNull(elemento);
        }

        return true;
    }
}
