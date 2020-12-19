package analisador;

import classificacao.Categoria;
import classificacao.TabelaClassificacao;
import main.resultado.ResultadoExecucao;

import java.util.Objects;

public class Validador {

    private final ResultadoExecucao resultadoExecucao;

    private final TabelaClassificacao tabelaClassificacao;

    public Validador(ResultadoExecucao resultadoExecucao, TabelaClassificacao tabelaClassificacao) {
        this.resultadoExecucao = resultadoExecucao;
        this.tabelaClassificacao = tabelaClassificacao;
    }

    public void validar() {
        validarVariaveis();
    }

    private void validarVariaveis() {
        if (Objects.isNull(tabelaClassificacao)) {
            return;
        }
        tabelaClassificacao.getItens()
                .stream()
                .filter(item -> Categoria.VARIAVEL.equals(item.getCategoria()))
                .forEach(itemClassificador -> {
                    System.out.println(itemClassificador);
                });
    }
}
