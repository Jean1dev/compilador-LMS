package main.analisador;

import main.resultado.ResultadoExecucao;

import java.io.FileInputStream;
import java.util.ArrayList;

import static main.analisador.GramaticaConstants.*;
import static main.analisador.specs.ValidacoesLexicas.validarNomeVariavel;
import static main.analisador.specs.ValidacoesLexicas.verificarSeInteiroFoiAlocadoEmNoLugarCerto;

public class LeitorArquivo {

    private final ResultadoExecucao resultadoExecucao;

    private final String arquivoPath;

    private int totalLinhasArquivo = 1;

    private Boolean textoComentado = false;

    private Boolean textoLiteral = false;

    private LeitorArquivo(ResultadoExecucao resultadoExecucao, String arquivoPath) {
        this.resultadoExecucao = resultadoExecucao;
        this.arquivoPath = arquivoPath;
    }

    public static LeitorArquivo of(ResultadoExecucao resultadoExecucao, String arquivoPath) {
        return new LeitorArquivo(resultadoExecucao, arquivoPath);
    }

    public void lerArquivoESetarNoContexto() {
        resultadoExecucao.setPalavras(identificarPalavras(arquivoPath));
    }

    private ArrayList<String> identificarPalavras(String filename) {
        try {
            ArrayList<String> texto = new ArrayList<>();
            StringBuilder palavra = new StringBuilder();
            FileInputStream fileInputStream = new FileInputStream(filename);

            int caracterLido = fileInputStream.read();
            while (caracterLido != -1) {
                char lido = (char) caracterLido;
                boolean delimitador = isDelimitador(lido);
                boolean quebraLinha = isQuebraLinha(lido);

                adicionaLinha(quebraLinha);

                palavra.append(lido);

                if (!textoComentado) {
                    textoComentado = isInicioComentario(palavra);
                }

                if (textoComentado) {
                    palavra = resolverTextoComentado(palavra, delimitador, quebraLinha);
                }

                if (!textoComentado) {
                    palavra = resolverTextoNaoComentado(lido, palavra, delimitador, quebraLinha, texto);
                }

                caracterLido = fileInputStream.read();
            }

            fileInputStream.close();
            return texto;
        } catch (Exception e) {
            e.printStackTrace();
            resultadoExecucao.addMessage("Erro na leitura do arquivo");
            return null;
        }
    }

    private StringBuilder resolverTextoNaoComentado(char lido,
                                                    StringBuilder palavra,
                                                    boolean delimitador,
                                                    boolean quebraLinha,
                                                    ArrayList<String> texto) {
        if (palavra.toString().trim().length() > 1 && isOperador(lido)) {
            String conteudo = removeOperador(palavra.toString(), lido);
            if (!conteudo.isEmpty()) {
                aplicarRegrasDoLexico(conteudo, texto);
                adicionarNoTexto(conteudo, texto);
            }
            add(texto, String.valueOf(lido));
            return new StringBuilder();
        }

        if (delimitador || quebraLinha) {
            String palavraFormatada = palavra.toString().trim();
            if (!palavraFormatada.isEmpty()) {
                aplicarRegrasDoLexico(palavraFormatada, texto);
                return adicionarNoTexto(palavraFormatada, texto);
            }
        }

        if (isOperador(lido) && !texto.isEmpty()) {
            int index = texto.size()- 1;
            if (ehArray(texto.get(index), lido)) {
                texto.remove(index);
                add(texto, "..", true);
                return new StringBuilder();
            }
        }

        return palavra;
    }

    private StringBuilder adicionarNoTexto(String conteudo, ArrayList<String> texto) {
        if (ehLiteral(conteudo)) {
            textoLiteral = true;
            if (verificarSeOInicialEFinalSaoAspas(conteudo)) {
                textoLiteral = false;
                add(texto, conteudo);
                return new StringBuilder();
            }

            return new StringBuilder(conteudo);
        }

        add(texto, conteudo);
        return new StringBuilder();
    }

    private void add(ArrayList<String> texto, String conteudo) {
        texto.add(conteudo);
        resultadoExecucao.addPalavraComLinha(conteudo + "#" + totalLinhasArquivo);
    }

    private void add(ArrayList<String> texto, String conteudo, boolean addArray) {
        texto.add(conteudo);
        resultadoExecucao.removeLastPalavraComLinha();
        resultadoExecucao.addPalavraComLinha(conteudo + "#" + totalLinhasArquivo);
    }

    private boolean verificarSeOInicialEFinalSaoAspas(String conteudo) {
        String posicaoInicial = String.valueOf(conteudo.charAt(0));
        String posicaoFinal = String.valueOf(conteudo.charAt(conteudo.length() - 1));
        return ehLiteral(posicaoInicial) && ehLiteral(posicaoFinal);
    }

    private boolean ehLiteral(String palavraFormatada) {
        return palavraFormatada.contains(String.valueOf(ASPAS_SIMPLES));
    }

    private boolean ehArray(String ultimaNaPilha, char atual) {
        return ultimaNaPilha.equals(String.valueOf(PONTO)) && atual == PONTO;
    }

    private void aplicarRegrasDoLexico(String conteudo, ArrayList<String> texto) {
        if (validarNomeVariavel(conteudo) && !verificarSeInteiroFoiAlocadoEmNoLugarCerto(texto.get(texto.size() - 1)))
            setarErroNoContexto(conteudo);
    }

    private String removeOperador(String palavra, char lido) {
        return palavra.replace(lido, ' ').trim();
    }

    private StringBuilder resolverTextoComentado(StringBuilder palavra, boolean delimitador, boolean quebraLinha) {
        boolean fimComentario = isFimComentario(palavra);
        if (delimitador || quebraLinha || fimComentario) {
            if (fimComentario) {
                textoComentado = false;
                palavra = new StringBuilder();
            } else {
                palavra = new StringBuilder();
            }
        }

        return palavra;
    }

    private void adicionaLinha(boolean quebraLinha) {
        if (quebraLinha) {
            totalLinhasArquivo++;
        }
    }

    private void setarErroNoContexto(String variavel) {
        String erro = "Erro na linha " + totalLinhasArquivo + " a variavel " + variavel + " é invalida";
        resultadoExecucao.addMessage(erro);
    }

    private boolean isFimComentario(StringBuilder palavra) {
        return palavra.toString().equalsIgnoreCase(FIM_COMENTARIO) || palavra.toString().contains(FIM_COMENTARIO);
    }

    private boolean isQuebraLinha(char lido) {
        return lido == QUEBRA_LINHA;
    }

    private boolean isDelimitador(char caracterRead) {
        return caracterRead == ESPACO || caracterRead == QUEBRA_LINHA;
    }

    private boolean isInicioComentario(StringBuilder palavra) {
        return palavra.toString().equalsIgnoreCase(INICIO_COMENTARIO) || palavra.toString().contains(INICIO_COMENTARIO);
    }

    private boolean isOperador(char lido) {
        return lido == PONTO_VIRGULA ||
                lido == MAIS ||
                lido == MENOS ||
                lido == MULTI ||
                lido == DIV ||
                lido == ABRE_PARENTESES ||
                lido == FECHA_PARENTESES ||
                lido == MAIOR ||
                lido == MENOR ||
                lido == VIRGULA ||
                lido == PONTO ||
                lido == ABRE_CHAVES ||
                lido == FECHA_FECHAS;
    }
}
