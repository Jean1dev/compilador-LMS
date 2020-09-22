package main.analisador;

import main.resultado.ResultadoExecucao;
import main.token.CaracterAnalisadoInfo;
import main.token.CriarTabelaToken;
import main.token.Token;

import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class AnalisadorTexto {

    private final ResultadoExecucao resultadoExecucao;

    private final List<Token> tabelaTokens = new CriarTabelaToken().getTabelaTokens();

    private AnalisadorTexto(ResultadoExecucao resultadoExecucao) {
        this.resultadoExecucao = resultadoExecucao;
    }

    public static AnalisadorTexto of(ResultadoExecucao resultadoExecucao) {
        return new AnalisadorTexto(resultadoExecucao);
    }

    public void analisar() {
        Stack<CaracterAnalisadoInfo> stack = new Stack<>();
        resultadoExecucao.getPalavras().forEach(palavra -> {
            Token token = getToken(palavra);

            CaracterAnalisadoInfo info = CaracterAnalisadoInfo.builder()
                    .valor(palavra)
                    .build();

            if (Objects.isNull(token)) {
                info.setToken(Token.identificador());
            } else {
                info.setToken(token);
            }

            stack.add(info);
        });

        resultadoExecucao.setStack(stack);
    }

    private Token getToken(String palavra) {
        return tabelaTokens.stream()
                .filter(token -> token.getSimbolo().equalsIgnoreCase(palavra))
                .findFirst()
                .orElse(null);
    }
}
