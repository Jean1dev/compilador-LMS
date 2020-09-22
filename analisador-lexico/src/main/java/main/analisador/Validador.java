package main.analisador;

import main.resultado.ResultadoExecucao;
import main.token.CaracterAnalisadoInfo;

import java.util.List;

public class Validador {

    private static final Integer INTEIRO = 26;
    private static final Integer LITERAL = 48;

    private final ResultadoExecucao resultadoExecucao;

    private Validador(ResultadoExecucao resultadoExecucao) {
        this.resultadoExecucao = resultadoExecucao;
    }

    public static Validador of(ResultadoExecucao resultadoExecucao) {
        return new Validador(resultadoExecucao);
    }

    public void validarPilha() {
        resultadoExecucao.getStack().forEach(info -> {
            validarLiteral(info);
            validarInteiro(info);
        });
    }

    private void validarInteiro(CaracterAnalisadoInfo info) {
        if (info.getToken().getCod().equals(INTEIRO)) {
            Integer valor = Integer.valueOf(info.getValor());

            if (valor < -32767 || valor > 32767) {
                List<String> validacao = resultadoExecucao.getMensagensValidacao();
                validacao.add("Conjuntos de números definidos na faixa de –32.767 a 32.767. Somente inteiros\n" +
                        "são aceitos na linguagem LMS. ");

                resultadoExecucao.setMensagensValidacao(validacao);
            }
        }
    }

    private void validarLiteral(CaracterAnalisadoInfo info) {
        if (info.getToken().getCod().equals(LITERAL)) {
            String valor = info.getValor();

            if (valor.length() > 200) {
                List<String> validacao = resultadoExecucao.getMensagensValidacao();
                validacao.add("Sequência de caracteres (letras/símbolos/números) delimitados por apóstrofe,\n" +
                        "contendo não mais do que 255 caracteres. ");

                resultadoExecucao.setMensagensValidacao(validacao);
            }
        }
    }
}
