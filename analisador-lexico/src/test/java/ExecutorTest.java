import main.analisador.Executor;
import main.arquivo.ArquivoUtils;
import main.resultado.ResultadoExecucao;
import org.junit.Assert;
import org.junit.Test;

public class ExecutorTest {

    private static final String DEFAULT_FILE_NAME = "arquivo.txt";

    @Test
    public void testeIgnorarComentarios() {
        String gramatica = "(* t *)";
        ArquivoUtils.gravarArquivo(gramatica);

        ResultadoExecucao resultado = new Executor(DEFAULT_FILE_NAME)
                .lerArquivoParaPegarPrograma()
                .getResultado();

        Assert.assertTrue(resultado.getPalavras().isEmpty());
    }

    @Test
    public void testeGramaticaValida1() {
        String gramatica = "(*TESTE - EXEMPLO VÁLIDO*)\n" +
                "PROGRAM TESTE123;\n" +
                "VAR x, A, B : INTEGER;\n" +
                "\n" +
                "BEGIN\n" +
                "     (*Testa as possibilidades do REPEAT*)\n" +
                "     REPEAT \n" +
                "     BEGIN \n" +
                "\tREADLN(A,B);\n" +
                "     END\n" +
                "     UNTIL X > 10;\n" +
                "\n" +
                "END.";

        ArquivoUtils.gravarArquivo(gramatica);
        ResultadoExecucao resultado = new Executor(DEFAULT_FILE_NAME)
                .lerArquivoParaPegarPrograma()
                .getResultado();

        System.out.println("teste");
    }
}
