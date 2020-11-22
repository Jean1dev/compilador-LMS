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
        Assert.assertTrue(resultado.getPalavrasComLinha().isEmpty());
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

        Assert.assertEquals(30, resultado.getPalavras().size());
        Assert.assertEquals(30, resultado.getPalavrasComLinha().size());
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

        // array_a : ARRAY[0..20] OF INTEGER;

        ArquivoUtils.gravarArquivo(gramatica);
        ResultadoExecucao resultado = new Executor(DEFAULT_FILE_NAME)
                .lerArquivoParaPegarPrograma()
                .analisarTexto()
                .getResultado();

        Assert.assertEquals(63, resultado.getPalavras().size());
        Assert.assertEquals(63, resultado.getPalavrasComLinha().size());
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

        Assert.assertEquals(71, resultado.getPalavras().size());
        Assert.assertEquals(71, resultado.getPalavrasComLinha().size());
    }

    @Test
    public void testeGramaticaValida4() {
        String gramatica = "BEGIN\n" +
                "     (*Testa as possibilidades do REPEAT*)\n";

        ArquivoUtils.gravarArquivo(gramatica);
        ResultadoExecucao resultado = new Executor(DEFAULT_FILE_NAME)
                .lerArquivoParaPegarPrograma()
                .analisarTexto()
                .getResultado();

        Assert.assertEquals(1, resultado.getPalavras().size());
        Assert.assertEquals(1, resultado.getPalavrasComLinha().size());
    }

    @Test
    public void testeGramaticaValida5() {
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
                .analisarTexto()
                .getResultado();

        Assert.assertEquals(71, resultado.getPalavras().size());
        Assert.assertEquals(71, resultado.getPalavrasComLinha().size());
    }

    @Test
    public void testeGramaticaInvalida6() {
        String gramatica = "(*TESTE - Exemplo válido: multiplicação*)\n" +
                "PROGRAM TESTE123;\n" +
                "\tCONST\n" +
                "\t\ta = -100;\n" +
                "\t\tb = -200000000;\n" +
                "\tVAR\n" +
                "\t\t Y, Z : INTEGER;\n" +
                "BEGIN\n" +
                "\tZ := a * Y + 2;\n" +
                "END.";

        ArquivoUtils.gravarArquivo(gramatica);
        ResultadoExecucao resultado = new Executor(DEFAULT_FILE_NAME)
                .lerArquivoParaPegarPrograma()
                .analisarTexto()
                .validarTexto()
                .getResultado();

        Assert.assertEquals(30, resultado.getPalavras().size());
        Assert.assertEquals(30, resultado.getPalavrasComLinha().size());
        Assert.assertEquals(1, resultado.getMensagensValidacao().size());
    }

    @Test
    public void testeGramaticaValida7() {
        String gramatica = "(*TESTE - Exemplo Válido*)\n" +
                "PROGRAM TESTE123;\n" +
                "\tLABEL\n" +
                "\t\tlabel_a, label_b;\n" +
                "\n" +
                "\tCONST\n" +
                "\t\ta = -100;\n" +
                "\t\tb = -200;\n" +
                "\tVAR\n" +
                "\t\t X, Y, Z : INTEGER;\n" +
                "\t\tarray_a : ARRAY[0..20] OF INTEGER;\n" +
                "\t\tarray_b, array_c, array_d : ARRAY[0..1000] OF INTEGER;\n" +
                "\n" +
                "\t(*Declaração de procedure, inicia novo bloco*)\n" +
                "\tPROCEDURE p_teste(idd : INTEGER);\n" +
                "\t\tLABEL\n" +
                "\t\t\tlabel_a, label_b;\n" +
                "\n" +
                "\t\tCONST\n" +
                "\t\t\ta = 100;\n" +
                "\t\t\tb = 200;\n" +
                "\n" +
                "\t\tVAR\n" +
                "\t\t\tX, Y, Z : INTEGER;\n" +
                "\t\t\tarray_a : ARRAY[0..20] OF INTEGER;\n" +
                "\t\t\tarray_b : ARRAY[0..1000] OF INTEGER;\n" +
                "\tBEGIN\n" +
                "\t\tX := a * b;\n" +
                "\n" +
                "\tEND;\n" +
                "\n" +
                "(*\n" +
                "\t- Início do bloco principal\n" +
                "*)\n" +
                "\n" +
                "BEGIN\n" +
                "\tx := 150;\n" +
                "\tbegin\n" +
                "\t\tx := 20;\n" +
                "\tend;\n" +
                "\n" +
                "\t(*Chama a procedure*)\n" +
                "\tCALL p_teste( 100 );\n" +
                "\n" +
                "\t(*Testa as possibilidades do IF*)\n" +
                "\tIF (10 > 15) THEN\n" +
                "\t\tBEGIN\n" +
                "\t\tEND\n" +
                "\tELSE\n" +
                "\t\tBEGIN\n" +
                "\t\tEND;\n" +
                "\n" +
                "\t(*Testa as possibilidades do WHILE*)\n" +
                "\tWHILE(x <> 0)DO\n" +
                "\tBEGIN\n" +
                "\tEND;\n" +
                "\n" +
                "\t(*Testa as possibilidades do REPEAT*)\n" +
                "\tREPEAT\n" +
                "\tBEGIN\n" +
                "\tEND\n" +
                "\tUNTIL X > 10;\n" +
                "\n" +
                "\t(*Testa as possibilidades do WRITELN*)\n" +
                "\tWRITELN(X,Z,Y);\n" +
                "\n" +
                "\t(*Testa as possibilidades do FOR*)\n" +
                "\tFOR x := y > 2 TO 10 - 2 DO\n" +
                "\tBEGIN\n" +
                "\tEND;\n" +
                "\n" +
                "\t(*Testa as possibilidades do CASE*)\n" +
                "\tCASE A + 2 OF\n" +
                "\t\t10 : BEGIN END;\n" +
                "\t\t20 : BEGIN END;\n" +
                "\t\t30 : BEGIN END\n" +
                "\tEND;\n" +
                "END.";

        ArquivoUtils.gravarArquivo(gramatica);
        ResultadoExecucao resultado = new Executor(DEFAULT_FILE_NAME)
                .lerArquivoParaPegarPrograma()
                .analisarTexto()
                .validarTexto()
                .getResultado();

        Assert.assertEquals(209, resultado.getPalavras().size());
        Assert.assertEquals(209, resultado.getPalavrasComLinha().size());
    }

    @Test
    public void testeGramaticaValida8() {
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
                .validarTexto()
                .getResultado();

        Assert.assertEquals(30, resultado.getPalavras().size());
        Assert.assertEquals(30, resultado.getPalavrasComLinha().size());
    }

    @Test
    public void testeArray() {
        String gramatica = "ARRAY[0..20]";

        ArquivoUtils.gravarArquivo(gramatica);
        ResultadoExecucao resultado = new Executor(DEFAULT_FILE_NAME)
                .lerArquivoParaPegarPrograma()
                .analisarTexto()
                .validarTexto()
                .getResultado();

        Assert.assertEquals(6, resultado.getPalavras().size());
        Assert.assertEquals(6, resultado.getPalavrasComLinha().size());
    }

    @Test
    public void testeLiteral() {
        String gramatica = "(*TESTE - EXEMPLO VÁLIDO*)\n" +
                "PROGRAM TESTE123;\n" +
                "VAR x = 'ESSE ´E UM LITERAL';\n" +
                "BEGIN\n" +
                "END.";

        ArquivoUtils.gravarArquivo(gramatica);
        ResultadoExecucao resultado = new Executor(DEFAULT_FILE_NAME)
                .lerArquivoParaPegarPrograma()
                .analisarTexto()
                .validarTexto()
                .getResultado();

        Assert.assertEquals(11, resultado.getPalavras().size());
        Assert.assertEquals(11, resultado.getPalavrasComLinha().size());
    }
}
