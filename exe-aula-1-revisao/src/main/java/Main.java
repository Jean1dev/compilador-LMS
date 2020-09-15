import arquivo.Utils;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Utils.gravarArquivo(geraConteudo());
        StringBuilder arquivo = Utils.lerArquivo();
        mostraResultados(String.valueOf(arquivo));
    }

    private static void mostraResultados(String arquivo) {
        List<String> listaPalavras = Arrays.asList(arquivo.split(" "));
        Stack<String> pilha = new Stack<>();

        Collections.reverse(listaPalavras);
        listaPalavras.forEach(pilha::addElement);
        int totalElementos = listaPalavras.size();
        for (int i = 0; i < totalElementos; i++) {
            System.out.println(pilha.pop());
        }
    }

    private static String geraConteudo() {
        return "1 um jacare da ribeira enquanto uma onca pintada do pantanal fugia do fogo no pantanal";
    }
}
