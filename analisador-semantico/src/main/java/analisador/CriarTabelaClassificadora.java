package analisador;

import classificacao.Categoria;
import classificacao.ItemClassificador;
import classificacao.TabelaClassificacao;
import main.resultado.ResultadoExecucao;
import main.resultado.StatusAnalise;
import main.token.CaracterAnalisadoInfo;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import static analisador.GramaticaDetalhes.*;
import static main.token.CaracterAnalisadoInfo.copy;

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
        removerCaracateresQueDevemSerIgnorados(pilha);

        for (int i = 0; i < pilha.size(); i++) {
            CaracterAnalisadoInfo atual = pilha.get(i);

            if (atual.getValor().equalsIgnoreCase("PROCEDURE") && !emProcedureSegundoNivel)
                alterarVariaveisDaClasse(2, true);


            if (atual.getValor().equalsIgnoreCase("END") && emProcedureSegundoNivel)
                alterarVariaveisDaClasse(1, false);

            ItemClassificador itemClassificador = ItemClassificador.builder()
                    .nomeIdentificador(atual.getValor())
                    .nivel(nivel)
                    .categoria(descobreCategoria(atual))
                    .build();

            if (!tabelaClassificacao.inserir(itemClassificador)) {

                if (verificarDuplicidadeDeDeclaracao(atual, pilha)) {
                    sinalizarDuplicidadeNaDeclaracao(atual);
                    return null;
                }

                tabelaClassificacao.forcarInsercao(itemClassificador);
            }

        }

        return tabelaClassificacao;
    }

    private void sinalizarDuplicidadeNaDeclaracao(CaracterAnalisadoInfo atual) {
        int linha = atual.getNLinha();
        String valor = atual.getValor();
        resultadoExecucao.addMessage("Erro na analise semantica | DUPLICIDADE NA DECLARACAO na linha " + linha + " com o valor " + valor + " em nivel " + (emProcedureSegundoNivel ? "2" : "1"));
        resultadoExecucao.setStatusAnalise(StatusAnalise.FALHA);
    }

    private boolean verificarDuplicidadeDeDeclaracao(CaracterAnalisadoInfo atual, Stack<CaracterAnalisadoInfo> pilha) {
        String atualValor = atual.getValor();
        int vezesDeclarado = 0;
        int vezesDeclaradoEmProcedure = 0;
        boolean emProcedure = false;
        for (int i = 0; i < pilha.size(); i++) {
            String atualNaFila = pilha.get(i).getValor();

            if (atualNaFila.equalsIgnoreCase("PROCEDURE")) {
                emProcedure = true;
            }

            if (atualNaFila.equalsIgnoreCase("END") && emProcedure) {
                if (vezesDeclaradoEmProcedure < 2) {
                    vezesDeclaradoEmProcedure = 0;
                }
                emProcedure = false;
            }

            if (atualNaFila.equalsIgnoreCase("CONST")) {
                for (int j = i; j < pilha.size(); j++) {
                    String valorNaJ = pilha.get(j).getValor();
                    if (valorNaJ.equalsIgnoreCase(atualValor)) {
                        if (emProcedure) {
                            vezesDeclaradoEmProcedure++;
                        } else {
                            vezesDeclarado++;
                        }
                    }

                    if (valorNaJ.equalsIgnoreCase("VAR") ||
                            valorNaJ.equalsIgnoreCase("LABEL") ||
                            valorNaJ.equalsIgnoreCase("PROCEDURE") ||
                            valorNaJ.equalsIgnoreCase("BEGIN"))
                        break;
                }
            }

            if (atualNaFila.equalsIgnoreCase("VAR")) {
                for (int j = i; j < pilha.size(); j++) {
                    String valorNaJ = pilha.get(j).getValor();
                    if (valorNaJ.equalsIgnoreCase(atualValor)) {
                        if (emProcedure) {
                            vezesDeclaradoEmProcedure++;
                        } else {
                            vezesDeclarado++;
                        }
                    }

                    if (valorNaJ.equalsIgnoreCase("CONST") ||
                            valorNaJ.equalsIgnoreCase("LABEL") ||
                            valorNaJ.equalsIgnoreCase("PROCEDURE") ||
                            valorNaJ.equalsIgnoreCase("BEGIN"))
                        break;
                }
            }

            if (atualNaFila.equalsIgnoreCase("LABEL")) {
                for (int j = i; j < pilha.size(); j++) {
                    String valorNaJ = pilha.get(j).getValor();
                    if (valorNaJ.equalsIgnoreCase(atualValor)) {
                        if (emProcedure) {
                            vezesDeclaradoEmProcedure++;
                        } else {
                            vezesDeclarado++;
                        }
                    }

                    if (valorNaJ.equalsIgnoreCase("CONST") ||
                            valorNaJ.equalsIgnoreCase("VAR") ||
                            valorNaJ.equalsIgnoreCase("PROCEDURE") ||
                            valorNaJ.equalsIgnoreCase("BEGIN"))
                        break;
                }
            }
        }

        return vezesDeclarado > 1 || vezesDeclaradoEmProcedure > 1;
    }

    private void removerCaracateresQueDevemSerIgnorados(Stack<CaracterAnalisadoInfo> pilha) {
        List<String> ignorados = Arrays.asList("PROGRAM", "+", "-", "*", "/", "[", "]", "(", ")", ":", "=", ">", ">=", ",", ".", "..");
        ignorados.forEach(s ->
                pilha.removeIf(caracterAnalisadoInfo -> caracterAnalisadoInfo.getValor().equalsIgnoreCase(s)));
    }

    private void sinalizarErro(CaracterAnalisadoInfo atual) {
        int linha = atual.getNLinha();
        String valor = atual.getValor();
        resultadoExecucao.addMessage("Erro na analise semantica verifique a linha " + linha + " com o valor " + valor + " em nivel " + (emProcedureSegundoNivel ? "2" : "1"));
        resultadoExecucao.setStatusAnalise(StatusAnalise.FALHA);
    }

    private void alterarVariaveisDaClasse(int nivel, boolean emProcedureSegundoNivel) {
        this.nivel = nivel;
        this.emProcedureSegundoNivel = emProcedureSegundoNivel;
    }

    private Categoria descobreCategoria(CaracterAnalisadoInfo caracterAnalisadoInfo) {
        Integer cod = caracterAnalisadoInfo.getToken().getCod();
        if (cod.equals(LABEL) || cod.equals(CONST) || cod.equals(VAR)) return Categoria.ROTULO;

        if (cod.equals(INTEGER) || cod.equals(INTEIRO)) return Categoria.INTEIRO;

        if (cod.equals(IDENTIFICADOR)) return Categoria.VARIAVEL;

        if (cod.equals(PROCEDURE)) return Categoria.PROCEDURE;

        return Categoria.NAO_DETERMINADO;
    }

    private static Stack<CaracterAnalisadoInfo> copiarPilha(Stack<CaracterAnalisadoInfo> stack) {
        Stack<CaracterAnalisadoInfo> copy = new Stack<>();
        stack.forEach(caracterAnalisadoInfo -> copy.add(copy(caracterAnalisadoInfo)));
        return copy;
    }
}
