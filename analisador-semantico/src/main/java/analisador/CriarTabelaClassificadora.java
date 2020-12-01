package analisador;

import classificacao.Categoria;
import classificacao.ItemClassificador;
import classificacao.TabelaClassificacao;
import main.resultado.ResultadoExecucao;
import main.token.CaracterAnalisadoInfo;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import static analisador.GramaticaDetalhes.*;
import static main.token.CaracterAnalisadoInfo.copy;
import static utils.Utils.reverseStack;

public class CriarTabelaClassificadora {

    private final ResultadoExecucao resultadoExecucao;

    private Integer nivel = 1;

    private Boolean emProcedureSegundoNivel = false;

    public CriarTabelaClassificadora(ResultadoExecucao resultadoExecucao) {
        this.resultadoExecucao = resultadoExecucao;
    }

    public TabelaClassificacao criar() {
        TabelaClassificacao tabelaClassificacao = new TabelaClassificacao();
        Stack<CaracterAnalisadoInfo> pilha = copiarPilha(resultadoExecucao.getStack());
        reverseStack(pilha);
        removerCaracateresQueDevemSerIgnorados(pilha);

        for (int i = 0; i < pilha.size(); i++) {
            CaracterAnalisadoInfo atual = pilha.get(i);

            if (atual.getValor().equalsIgnoreCase("PROCEDURE") && !emProcedureSegundoNivel)
                alterarVariaveisDaClasse(2, true);


            if (atual.getValor().equalsIgnoreCase("END") && emProcedureSegundoNivel)
                alterarVariaveisDaClasse(1, false);

            if (!tabelaClassificacao.inserir(ItemClassificador.builder()
                    .nomeIdentificador(atual.getValor())
                    .nivel(nivel)
                    .categoria(descobreCategoria(atual))
                    .build())) {

                sinalizarErro(atual);
                return null;
            }

        }

        return tabelaClassificacao;
    }

    private void removerCaracateresQueDevemSerIgnorados(Stack<CaracterAnalisadoInfo> pilha) {
        List<String> ignorados = Arrays.asList("PROGRAM", "+", "-", "*", "/", "[", "]", "(", ")", ":", "=", ">", ">=", ",", ".", "..");
        ignorados.forEach(s ->
                pilha.removeIf(caracterAnalisadoInfo -> caracterAnalisadoInfo.getValor().equalsIgnoreCase(s)));
    }

    private void sinalizarErro(CaracterAnalisadoInfo atual) {
        int linha = atual.getNLinha();
        String valor = atual.getValor();
        resultadoExecucao.addMessage("Erro na analise semantica verifique a linha " + linha + " com o valor " + valor);
    }

    private void alterarVariaveisDaClasse(int nivel, boolean emProcedureSegundoNivel) {
        this.nivel = nivel;
        this.emProcedureSegundoNivel = emProcedureSegundoNivel;
    }

    private Categoria descobreCategoria(CaracterAnalisadoInfo caracterAnalisadoInfo) {
        Integer cod = caracterAnalisadoInfo.getToken().getCod();
        if (cod.equals(LABEL) || cod.equals(CONST) || cod.equals(VAR)) return Categoria.ROTULO;

        if (cod.equals(INTEGER) || cod.equals(INTEIRO) || cod.equals(IDENTIFICADOR)) return Categoria.VARIAVEL;

        if (cod.equals(PROCEDURE)) return Categoria.PROCEDURE;

        return Categoria.NAO_DETERMINADO;
    }

    private static Stack<CaracterAnalisadoInfo> copiarPilha(Stack<CaracterAnalisadoInfo> stack) {
        Stack<CaracterAnalisadoInfo> copy = new Stack<>();
        stack.forEach(caracterAnalisadoInfo -> copy.add(copy(caracterAnalisadoInfo)));
        return copy;
    }
}
