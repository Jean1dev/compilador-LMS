package analisador;

import gramatica.*;
import main.resultado.ResultadoExecucao;
import main.token.CaracterAnalisadoInfo;

import java.util.Arrays;
import java.util.Objects;
import java.util.Stack;
import java.util.stream.Collectors;

import static gramatica.SimboloComCodigo.simboloInicial;
import static gramatica.XMLInstanciador.getCatalogoPalavras;
import static gramatica.XMLInstanciador.getTabelaParser;
import static main.token.CaracterAnalisadoInfo.copy;

public class Executor {

    private final ResultadoExecucao resultadoExecucao;

    private final CatalogoPalavra catalogoPalavra;

    private final TabelaParsing tabelaParsing;

    public Executor(ResultadoExecucao resultadoExecucao) {
        this.resultadoExecucao = resultadoExecucao;
        catalogoPalavra = getCatalogoPalavras();
        tabelaParsing = getTabelaParser();
    }

    /*
     * CONTEUDO COMEÇA NOS 39 min
     *  A = representa o resultado da pilha no lexio
     *
     *
     * */
    public void doSintatico() {
        Stack<CaracterAnalisadoInfo> pilhaA = copiarPilha(resultadoExecucao.getStack());
        Stack<SimboloComCodigo> pilhaX = new Stack<>();

        pilhaX.push(simboloInicial()); // DE ACORDO COM A REGRA DEVE INICIAR A PILHA COM O COD 52

        while (!pilhaA.empty() || !pilhaX.empty()) {
            CaracterAnalisadoInfo topoPilhaA = pilhaA.lastElement();
            SimboloComCodigo topoPilhaX = pilhaX.lastElement();

            if (ehUmTerminal(topoPilhaX)) {

            } else {
                String referencia = topoPilhaA + "," + topoPilhaA;
                Item item = procurarPelaReferencia(referencia);
                pilhaX.pop();

                adicionarNaPilhaInformacoesDoParser(pilhaX, item);
            }
        }
    }

    private void adicionarNaPilhaInformacoesDoParser(Stack<SimboloComCodigo> pilhaX, Item item) {
        Arrays.stream(item.getDerivacao().split("|"))
                .collect(Collectors.toList())
                .forEach(s -> {
                    Integer codigo = pegaCodigo(s);
                    if (codigo > 0) {
                        pilhaX.add(new SimboloComCodigo(codigo, s));
                    }
                });
    }

    private Integer pegaCodigo(String palavra) {
        NaoTerminais naoTerminalFind = catalogoPalavra.getNaoTerminais()
                .stream()
                .filter(naoTerminais -> naoTerminais.getSimbolo().equals(palavra))
                .findFirst()
                .orElse(null);

        if (Objects.nonNull(naoTerminalFind)) {
            return Integer.valueOf(naoTerminalFind.getCodigo());
        }

        Terminais terminalFind = catalogoPalavra.getTerminais()
                .stream()
                .filter(terminais -> terminais.getSimbolo().equals(palavra))
                .findFirst()
                .orElse(null);

        return Objects.nonNull(terminalFind) ? Integer.parseInt(terminalFind.getCodigo()) : 0;
    }

    private Item procurarPelaReferencia(String referencia) {
        return tabelaParsing.getItem()
                .stream()
                .filter(item -> item.getCodigo().equals(referencia))
                .findFirst()
                .orElse(null);
    }

    private boolean ehUmTerminal(SimboloComCodigo topoPilhaX) {
        return topoPilhaX.getCodigo() < 52;
    }

    private Stack<CaracterAnalisadoInfo> copiarPilha(Stack<CaracterAnalisadoInfo> stack) {
        Stack<CaracterAnalisadoInfo> copy = new Stack<>();
        stack.forEach(caracterAnalisadoInfo -> copy.add(copy(caracterAnalisadoInfo)));
        return copy;
    }
}
