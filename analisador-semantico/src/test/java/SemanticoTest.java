import analisador.ExecutorSemantico;
import analisador.ExecutorSintatico;
import main.analisador.Executor;
import main.arquivo.ArquivoUtils;
import main.resultado.ResultadoExecucao;
import org.junit.Assert;
import org.junit.Test;

import static main.resultado.StatusAnalise.SUCESSO;

public class SemanticoTest {
    private static final String DEFAULT_FILE_NAME = "arquivo.txt";

    @Test
    public void testeGramaticaValida1() {
        String gramatica = "(*TESTE - EXEMPLO VIDEO PROFESSOR*)\n" +
                "PROGRAM TESTE123;\n" +
                "BEGIN\n" +
                "END.";

        ArquivoUtils.gravarArquivo(gramatica);
        ResultadoExecucao resultado = new Executor(DEFAULT_FILE_NAME).analisarLexicamente();

        ExecutorSintatico sintatico = new ExecutorSintatico(resultado);
        sintatico.doSintatico();

        new ExecutorSemantico(resultado).executar();
        Assert.assertEquals(SUCESSO, resultado.getStatusAnalise());
    }
}
