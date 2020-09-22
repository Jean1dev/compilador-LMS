package main.analisador;

import main.arquivo.ArquivoUtils;
import main.resultado.ResultadoExecucao;

import java.util.Arrays;

public class LeitorArquivo {

    private static final String DELIMITADOR = " ";

    private final ResultadoExecucao resultadoExecucao;

    private final String arquivoPath;

    private LeitorArquivo(ResultadoExecucao resultadoExecucao, String arquivoPath) {
        this.resultadoExecucao = resultadoExecucao;
        this.arquivoPath = arquivoPath;
    }

    public static LeitorArquivo of(ResultadoExecucao resultadoExecucao, String arquivoPath) {
        return new LeitorArquivo(resultadoExecucao, arquivoPath);
    }

    public void lerArquivoESetarNoContexto() {
        StringBuilder arquivo = ArquivoUtils.lerArquivo(arquivoPath);
        assert arquivo != null;

        String[] palavras = arquivo.toString().split(DELIMITADOR);
        resultadoExecucao.setPalavras(Arrays.asList(palavras));
    }

    public void lerStringESetarNoContexto(String content) {
        String[] palavras = content.toString().split(DELIMITADOR);
        resultadoExecucao.setPalavras(Arrays.asList(palavras));
    }
}
