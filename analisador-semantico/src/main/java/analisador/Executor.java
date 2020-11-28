package analisador;

import main.resultado.ResultadoExecucao;

public class Executor {

    private final ResultadoExecucao resultadoExecucao;

    public Executor(ResultadoExecucao resultadoExecucao) {
        this.resultadoExecucao = resultadoExecucao;
    }

    protected Executor criarTabelaClassificadora() {
        return this;
    }
}
