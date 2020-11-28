package analisador;

import classificacao.Categoria;
import classificacao.ItemClassificador;
import classificacao.TabelaClassificacao;
import main.resultado.ResultadoExecucao;
import main.token.CaracterAnalisadoInfo;

import java.util.Stack;

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

        for (int i = 0; i < pilha.size(); i++) {
            CaracterAnalisadoInfo atual = pilha.get(i);

            if (atual.getValor().equalsIgnoreCase("PROCEDURE") && !emProcedureSegundoNivel)
                alterarVariaveisDaClasse(2, true);


            if (atual.getValor().equalsIgnoreCase("END") && emProcedureSegundoNivel)
                alterarVariaveisDaClasse(1, false);

            if (!tabelaClassificacao.inserir(ItemClassificador.builder()
                    .nomeIdentificador(atual.getValor())
                    .nivel(nivel)
                    .categoria(descobreCategoria(atual, i, pilha))
                    .build())) {

                sinalizarErro(atual);
                return null;
            }

        }

        return tabelaClassificacao;
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

    private Categoria descobreCategoria(CaracterAnalisadoInfo caracterAnalisadoInfo, int posicao, Stack<CaracterAnalisadoInfo> pilha) {
        String simbolo = caracterAnalisadoInfo.getToken().getSimbolo();

    }

    private static Stack<CaracterAnalisadoInfo> copiarPilha(Stack<CaracterAnalisadoInfo> stack) {
        Stack<CaracterAnalisadoInfo> copy = new Stack<>();
        stack.forEach(caracterAnalisadoInfo -> copy.add(copy(caracterAnalisadoInfo)));
        return copy;
    }
}
