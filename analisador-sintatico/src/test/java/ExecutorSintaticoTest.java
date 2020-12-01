import analisador.ExecutorSintatico;
import main.analisador.Executor;
import main.arquivo.ArquivoUtils;
import main.resultado.ResultadoExecucao;
import org.junit.Assert;
import org.junit.Test;

import static main.resultado.StatusAnalise.FALHA;
import static main.resultado.StatusAnalise.SUCESSO;

public class ExecutorSintaticoTest {

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
        ResultadoExecucao resultado = new Executor(DEFAULT_FILE_NAME).analisarLexicamente();

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
        ResultadoExecucao resultado = new Executor(DEFAULT_FILE_NAME).analisarLexicamente();

        ExecutorSintatico sintatico = new ExecutorSintatico(resultado);
        sintatico.doSintatico();

        Assert.assertEquals(SUCESSO, resultado.getStatusAnalise());
    }
}
