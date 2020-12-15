package analisador;

import gramatica.*;
import main.resultado.ResultadoExecucao;
import main.resultado.StatusAnalise;
import main.token.CaracterAnalisadoInfo;

import java.util.Arrays;
import java.util.Objects;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
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
            CaracterAnalisadoInfo topoPilhaA = pilhaA.firstElement();
            SimboloComCodigo topoPilhaX = pilhaX.firstElement();
            System.out.println("PILHA A:: " + topoPilhaA.getValor() + " " + topoPilhaA.getToken().getSimbolo());
            System.out.println("PILHA X:: " + topoPilhaX.getSimbolo());
            System.out.println("\n");

            if (ehUmTerminal(topoPilhaX)) {
                if (topoPilhaX.getCodigo().equals(topoPilhaA.getToken().getCod())) {
                    pilhaA.remove(topoPilhaA);
                    pilhaX.remove(topoPilhaX);
                    logPilhaX(pilhaX);
                } else {
                    informarErro(topoPilhaA);
                    return;
                }
            } else {
                String referencia = topoPilhaX.getCodigo() + "," + topoPilhaA.getToken().getCod();
                System.out.println("referencia buscada " + referencia);
                Item item = procurarPelaReferencia(referencia);

                if (Objects.nonNull(item)) {
                    pilhaX.remove(topoPilhaX);
                    adicionarNaPilhaInformacoesDoParser(pilhaX, item);
                } else {
                    informarErro(topoPilhaA);
                    return;
                }
            }
        }

        resultadoExecucao.setStatusAnalise(StatusAnalise.SUCESSO);
    }

    private void informarErro(CaracterAnalisadoInfo topoPilhaA) {
        resultadoExecucao.setStatusAnalise(StatusAnalise.FALHA);
        resultadoExecucao.addMessage("Falha na alise sintatica, problema esta na linha " + topoPilhaA.getNLinha());
    }

    private void adicionarNaPilhaInformacoesDoParser(Stack<SimboloComCodigo> pilhaX, Item item) {
        AtomicInteger index = new AtomicInteger();
        Arrays.stream(item.getDerivacao().split("\\|"))
                .collect(Collectors.toList())
                .forEach(s -> {
                    Integer codigo = pegaCodigo(s);
                    if (codigo > 0) {
                        pilhaX.add(index.get(), new SimboloComCodigo(codigo, s));
                        index.getAndIncrement();
                    }
                });

        logPilhaX(pilhaX);
    }

    private void logPilhaX(Stack<SimboloComCodigo> pilhaX) {
        System.out.println("\n");
        AtomicReference<String> log = new AtomicReference<>("");
        pilhaX.forEach(simboloComCodigo -> log.set(log.get() + (simboloComCodigo.getSimbolo() + "|")));
        System.out.println(log);
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
