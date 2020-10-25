package main.analisador;

import main.resultado.ResultadoExecucao;
import main.resultado.StatusAnalise;

public class Executor {

    private final String arquivoPath;

    private final ResultadoExecucao resultado;

    public Executor(String arquivoPath) {
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

    public Executor lerArquivoParaPegarPrograma() {
        LeitorArquivo.of(resultado, arquivoPath).lerArquivoESetarNoContexto();
        return this;
    }

    public Executor analisarTexto() {
        AnalisadorTexto.of(resultado).analisar();
        return this;
    }

    public Executor validarTexto() {
        Validador.of(resultado).validarPilha();
        return this;
    }

    public Executor consolidarResultado() {
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
