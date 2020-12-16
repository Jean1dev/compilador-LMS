package analisador;

import classificacao.TabelaClassificacao;
import lombok.Getter;
import main.resultado.ResultadoExecucao;

@Getter
public class ExecutorSemantico {

    private final ResultadoExecucao resultadoExecucao;

    private TabelaClassificacao tabelaClassificacao;

    public ExecutorSemantico(ResultadoExecucao resultadoExecucao) {
        this.resultadoExecucao = resultadoExecucao;
    }

    public ResultadoExecucao executar() {
        return criarTabelaClassificadora()
                .validarTabelaClassicacao()
                .getResultadoExecucao();
    }

    protected ExecutorSemantico criarTabelaClassificadora() {
        tabelaClassificacao = new CriarTabelaClassificadora(resultadoExecucao).criar();
        return this;
    }

    protected ExecutorSemantico validarTabelaClassicacao() {
        new Validador(resultadoExecucao, tabelaClassificacao).validar();
        return this;
    }
}
