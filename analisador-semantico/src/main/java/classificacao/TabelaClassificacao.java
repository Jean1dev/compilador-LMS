package classificacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public Boolean buscar(ItemClassificador buscar) {
        ItemClassificador elemento = itens.stream()
                .filter(item -> item.getNivel().equals(buscar.getNivel()))
                .filter(item -> item.getNomeIdentificador().equalsIgnoreCase(buscar.getNomeIdentificador()))
                .findFirst()
                .orElse(null);

        return Objects.nonNull(elemento);
    }
}
