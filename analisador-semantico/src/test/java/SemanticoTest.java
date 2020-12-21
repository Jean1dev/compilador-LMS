import analisador.ExecutorSemantico;
import analisador.ExecutorSintatico;
import main.analisador.ExecutorLexico;
import main.arquivo.ArquivoUtils;
import main.resultado.ResultadoExecucao;
import org.junit.Assert;
import org.junit.Test;

import static main.resultado.StatusAnalise.FALHA;
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
        ResultadoExecucao resultado = new ExecutorLexico(DEFAULT_FILE_NAME).analisarLexicamente();

        ExecutorSintatico sintatico = new ExecutorSintatico(resultado);
        sintatico.doSintatico();

        new ExecutorSemantico(resultado).executar();
        Assert.assertEquals(SUCESSO, resultado.getStatusAnalise());
    }

    @Test
    public void testeGramaticaValida2() {
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
        ResultadoExecucao resultado = new ExecutorLexico(DEFAULT_FILE_NAME).analisarLexicamente();

        ExecutorSintatico sintatico = new ExecutorSintatico(resultado);
        sintatico.doSintatico();

        new ExecutorSemantico(resultado).executar();
        Assert.assertEquals(SUCESSO, resultado.getStatusAnalise());
    }

    @Test
    public void testeGramaticaInvalidaDuplicidade() {
        String gramatica = "\n" +
                "    (*TESTE - Exemplo inválido: A varivel 'A' foi declarada em duplicidade*)\n" +
                "    PROGRAM TESTE123;\n" +
                "    CONST\n" +
                "            a = -100;\n" +
                "    b = -200;\n" +
                "    VAR\n" +
                "    a, Y, Z : INTEGER;\n" +
                "    BEGIN\n" +
                "    Z := 0;\n" +
                "    END.";

        ArquivoUtils.gravarArquivo(gramatica);
        ResultadoExecucao resultado = new ExecutorLexico(DEFAULT_FILE_NAME).analisarLexicamente();

        ExecutorSintatico sintatico = new ExecutorSintatico(resultado);
        sintatico.doSintatico();

        new ExecutorSemantico(resultado).executar();
        Assert.assertEquals(FALHA, resultado.getStatusAnalise());
    }

    @Test
    public void testeGramaticaValida89() {
        String gramatica = "\n" +
                "    (*TESTE - Exemplo inválido: A varivel 'A' foi declarada em duplicidade*)\n" +
                "    PROGRAM TESTE123;\n" +
                "    CONST\n" +
                "            a = -100;\n" +
                "    b = -200;\n" +
                "    VAR\n" +
                "    k, Y, Z : INTEGER;\n" +
                "    BEGIN\n" +
                "    Z := 0;\n" +
                "    END.";

        ArquivoUtils.gravarArquivo(gramatica);
        ResultadoExecucao resultado = new ExecutorLexico(DEFAULT_FILE_NAME).analisarLexicamente();

        ExecutorSintatico sintatico = new ExecutorSintatico(resultado);
        sintatico.doSintatico();

        new ExecutorSemantico(resultado).executar();
        Assert.assertEquals(SUCESSO, resultado.getStatusAnalise());
    }

    @Test
    public void testeGramaticaValida3() {
        String gramatica = "(*TESTE 9 - Utilizando Arrays*)\n" +
                "PROGRAM TESTE123;\n" +
                "\tVAR\n" +
                "\t\t X, Y, Z : INTEGER;\n" +
                "\t\tarray_a : ARRAY[0..20] OF INTEGER;\n" +
                "BEGIN\n" +
                "\tW := 10;\n" +
                "\tarray_a[0] := W;\n" +
                "\tarray_a[1] := 20;\n" +
                "\n" +
                "\tFOR x := y > 2 TO 20 DO\n" +
                "\tBEGIN\n" +
                "\t\tarray_a[x] := 2;\n" +
                "\tEND;\n" +
                "\n" +
                "\n" +
                "END.";

        ArquivoUtils.gravarArquivo(gramatica);
        ResultadoExecucao resultado = new ExecutorLexico(DEFAULT_FILE_NAME).analisarLexicamente();

        ExecutorSintatico sintatico = new ExecutorSintatico(resultado);
        sintatico.doSintatico();

        new ExecutorSemantico(resultado).executar();
        Assert.assertEquals(SUCESSO, resultado.getStatusAnalise());
    }

    @Test
    public void testeGramaticaValida9() {
        String gramatica = "(*TESTE - valido com duas procedures*)\n" +
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
                "\tPROCEDURE p_b(idd : INTEGER);\n" +
                "\t\tVAR\n" +
                "\t\t    X, Y, Z : INTEGER;\n" +
                "\tBEGIN\n" +
                "\t\tX := Z * Z;\n" +
                "\tEND;\n" +
                "\n" +
                "\n" +
                "BEGIN\n" +
                "\tcall p_a(10 + 2);\n" +
                "\tcall p_b(5);\n" +
                "END.";

        ArquivoUtils.gravarArquivo(gramatica);
        ResultadoExecucao resultado = new ExecutorLexico(DEFAULT_FILE_NAME).analisarLexicamente();

        ExecutorSintatico sintatico = new ExecutorSintatico(resultado);
        sintatico.doSintatico();

        new ExecutorSemantico(resultado).executar();
        Assert.assertEquals(SUCESSO, resultado.getStatusAnalise());
    }
}
