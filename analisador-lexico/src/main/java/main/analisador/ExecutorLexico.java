package main.analisador;

import main.resultado.ResultadoExecucao;
import main.resultado.StatusAnalise;

public class ExecutorLexico {

    private final String arquivoPath;

    private final ResultadoExecucao resultado;

    public ExecutorLexico(String arquivoPath) {
        this.arquivoPath = arquivoPath;
        resultado = new ResultadoExecucao();
    }

    public ResultadoExecucao analisarLexicamente() {
        return lerArquivoParaPegarPrograma()
                .analisarTexto()
                .validarTexto()
                .consolidarResultado()
                .getResultado();
    }

    public ExecutorLexico lerArquivoParaPegarPrograma() {
        LeitorArquivo.of(resultado, arquivoPath).lerArquivoESetarNoContexto();
        return this;
    }

    public ExecutorLexico analisarTexto() {
        AnalisadorTexto.of(resultado).analisar();
        return this;
    }

    public ExecutorLexico validarTexto() {
        Validador.of(resultado).validarPilha();
        return this;
    }

    public ExecutorLexico consolidarResultado() {
        if (resultado.getMensagensValidacao().isEmpty()) {
            resultado.setStatusAnalise(StatusAnalise.SUCESSO);
        } else {
            resultado.setStatusAnalise(StatusAnalise.FALHA);
        }

        return this;
    }

    public ResultadoExecucao getResultado() {
        return resultado;
    }
}
