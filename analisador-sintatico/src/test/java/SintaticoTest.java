import analisador.ExecutorSintatico;
import main.analisador.ExecutorLexico;
import main.arquivo.ArquivoUtils;
import main.resultado.ResultadoExecucao;
import org.junit.Assert;
import org.junit.Test;

import static main.resultado.StatusAnalise.FALHA;
import static main.resultado.StatusAnalise.SUCESSO;

public class SintaticoTest {

    private static final String DEFAULT_FILE_NAME = "arquivo.txt";

    @Test
    public void testeGramaticaValida4() {
        String gramatica = "PROGRAM TESTE;\n" +
                "VAR\n" +
                "x,y,z: INTEGER;\n" +
                "BEGIN\n" +
                "END.";

        ArquivoUtils.gravarArquivo(gramatica);
        ResultadoExecucao resultado = new ExecutorLexico(DEFAULT_FILE_NAME).analisarLexicamente();

        ExecutorSintatico sintatico = new ExecutorSintatico(resultado);
        sintatico.doSintatico();

        Assert.assertEquals(SUCESSO, resultado.getStatusAnalise());
    }

    @Test
    public void testePossibilidadeIF() {
        String gramatica = "PROGRAM TESTE123;\n" +
                "\n" +
                "BEGIN\n" +
                "\t(*Testa as possibilidades do IF*)\n" +
                "\tIF (10 > 15) THEN\n" +
                "\t\tBEGIN\n" +
                "\t\tEND\n" +
                "\tELSE\n" +
                "\t\tBEGIN\n" +
                "\t\tEND;\n" +
                "END.";

        ArquivoUtils.gravarArquivo(gramatica);
        ResultadoExecucao resultado = new ExecutorLexico(DEFAULT_FILE_NAME).analisarLexicamente();

        ExecutorSintatico sintatico = new ExecutorSintatico(resultado);
        sintatico.doSintatico();

        Assert.assertEquals(SUCESSO, resultado.getStatusAnalise());
    }

    @Test
    public void testeProcedure() {
        String gramatica = "PROGRAM TESTE123;\n" +
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
                "BEGIN\n" +
                "\tcall p_teste(a);\n" +
                "\tcall p_teste(b);\n" +
                "\tcall p_teste(X);\n" +
                "END.";

        ArquivoUtils.gravarArquivo(gramatica);
        ResultadoExecucao resultado = new ExecutorLexico(DEFAULT_FILE_NAME).analisarLexicamente();

        ExecutorSintatico sintatico = new ExecutorSintatico(resultado);
        sintatico.doSintatico();

        Assert.assertEquals(SUCESSO, resultado.getStatusAnalise());
    }

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

        Assert.assertEquals(SUCESSO, resultado.getStatusAnalise());
    }

    @Test
    public void testeGramaticaValida3() {
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

        Assert.assertEquals(SUCESSO, resultado.getStatusAnalise());
    }

    @Test
    public void testeGramaticaInvalida() {
        String gramatica = "(*TESTE - Erro*)\n" +
                "(*A derivao [66,26] 'COMANDO,INTEIRO' no foi encontrada na tabela de parsing.*)\n" +
                "PROGRAM TESTE123;\n" +
                "VAR x, A, B : INTEGER;\n" +
                "BEGIN\n" +
                "     REPEAT 5\n" +
                "     BEGIN\n" +
                "\tREADLN(A,B);\n" +
                "                        x := A * B;\n" +
                "     END\n" +
                "     UNTIL X > 10;\n" +
                "\n" +
                "END.";

        ArquivoUtils.gravarArquivo(gramatica);
        ResultadoExecucao resultado = new ExecutorLexico(DEFAULT_FILE_NAME).analisarLexicamente();

        ExecutorSintatico sintatico = new ExecutorSintatico(resultado);
        sintatico.doSintatico();

        Assert.assertEquals(FALHA, resultado.getStatusAnalise());
    }

    @Test
    public void testeGramaticaValida() {
        String gramatica = "(*TESTE - Exemplo Válido com procedures*)\n" +
                "PROGRAM TESTE123;\n" +
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
                "BEGIN\n" +
                "\tcall p_teste(a);\n" +
                "\tcall p_teste(b);\n" +
                "\tcall p_teste(X);\n" +
                "END.";

        ArquivoUtils.gravarArquivo(gramatica);
        ResultadoExecucao resultado = new ExecutorLexico(DEFAULT_FILE_NAME).analisarLexicamente();

        ExecutorSintatico sintatico = new ExecutorSintatico(resultado);
        sintatico.doSintatico();

        Assert.assertEquals(SUCESSO, resultado.getStatusAnalise());
    }
}
