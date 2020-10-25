package main.analisador.specs;

import java.util.Arrays;
import java.util.List;

public class ValidacoesLexicas {

    /** Nome de variaveis nao pode comecar com numero
     * @return Boolean.TRUE se for invalido */
    public static Boolean validarNomeVariavel(String nomeVariavel) {
        return Character.isDigit(nomeVariavel.charAt(0));
    }

    /** Verifica se o caracter no array antes é inteiro é uma opcao valida de acordo com a gramatica
     * @return Boolean.TRUE se for valido */
    public static Boolean verificarSeInteiroFoiAlocadoEmNoLugarCerto(String posicaoAntesDele) {
        List<String> permitidos = Arrays.asList(">", "<", "=", "+", "-", "/", "*", "(", ")", "[", "]", ":=");
        long count = permitidos.stream().filter(s -> s.equalsIgnoreCase(posicaoAntesDele)).count();
        return count > 0;
    }
}
