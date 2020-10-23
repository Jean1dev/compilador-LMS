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
                .analisarTexto()
                .getResultado();

        Assert.assertEquals(17, resultado.getPalavras().size());
    }

    @Test
    public void testeGramaticaValida2() {
        String gramatica = "(*TESTE 9 - Utilizando Arrays*)\n" +
                "PROGRAM TESTE123;\n" +
                "\tVAR \n" +
                "\t\t X, Y, Z : INTEGER;\n" +
                "\t\tarray_a : ARRAY[0..20] OF INTEGER;\n" +
                "BEGIN\n" +
                "\tW := 10;\n" +
                "\tarray_a[0] := W;\n" +
                "\tarray_a[1] := 20;\n" +
                "\t\n" +
                "\tFOR x := y > 2 TO 20 DO\n" +
                "\tBEGIN\n" +
                "\t\tarray_a[x] := 2;\n" +
                "\tEND;\n" +
                "\t\n" +
                "\n" +
                "END.";

        ArquivoUtils.gravarArquivo(gramatica);
        ResultadoExecucao resultado = new Executor(DEFAULT_FILE_NAME)
                .lerArquivoParaPegarPrograma()
                .getResultado();

        Assert.assertEquals(37, resultado.getPalavras().size());
    }


    @Test
    public void testeGramaticaValida3() {
        String gramatica = "(*TESTE - inválido: procedure duplicada*)\n" +
                "PROGRAM TESTE_PROC;\n" +
                "\t(*Proc A*)\n" +
                "\tPROCEDURE p_a(idd : INTEGER);\n" +
                "\t\tVAR\n" +
                "\t\t    X, Y, Z : INTEGER;\n" +
                "\tBEGIN\n" +
                "\t\tX := X * Y;\n" +
                "\tEND;\n" +
                "\n" +
                "\t(*Proc B com os mesmos dados de A*)\n" +
                "\tPROCEDURE p_a(idd : INTEGER);\n" +
                "\t\tVAR\n" +
                "\t\t    X, Y, Z : INTEGER;\n" +
                "\tBEGIN\n" +
                "\t\tX := Z *Z;\n" +
                "\tEND;\n" +
                "\n" +
                "\n" +
                "BEGIN\n" +
                "\tcall p_a(10 + 2);\n" +
                "\tcall p_b(5);\n" +
                "END.";

        ArquivoUtils.gravarArquivo(gramatica);
        ResultadoExecucao resultado = new Executor(DEFAULT_FILE_NAME)
                .lerArquivoParaPegarPrograma()
                .getResultado();

        Assert.assertEquals(42, resultado.getPalavras().size());
    }
}
