package main.analisador;

import main.resultado.ResultadoExecucao;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import static main.analisador.specs.ValidacoesLexicas.validarNomeVariavel;
import static main.analisador.specs.ValidacoesLexicas.verificarSeInteiroFoiAlocadoEmNoLugarCerto;

public class LeitorArquivo {

    private static final char ESPACO = ' ';

    private static final char QUEBRA_LINHA = '\n';

    private static final String INICIO_COMENTARIO = "(*";

    private static final String FIM_COMENTARIO = "*)";

    private final ResultadoExecucao resultadoExecucao;

    private final String arquivoPath;

    private int totalLinhasArquivo;

    private Boolean textoComentado = false;

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

                if (quebraLinha) {
                    totalLinhasArquivo++;
                }

                palavra.append(lido);

                if (!textoComentado) {
                    textoComentado = isInicioComentario(palavra);
                }

                if (textoComentado) {
                    boolean fimComentario = isFimComentario(palavra);
                    if (delimitador || quebraLinha || fimComentario) {
                        if (fimComentario) {
                            textoComentado = false;
                            palavra = new StringBuilder();
                        } else {
                            palavra = new StringBuilder();
                        }
                    }
                }

                if (!textoComentado) {
                    if (delimitador || quebraLinha) {
                        String palavraFormatada = palavra.toString().trim();
                        if (!palavraFormatada.isEmpty()) {
                            if (validarNomeVariavel(palavraFormatada)) {
                                if (!verificarSeInteiroFoiAlocadoEmNoLugarCerto(texto.get(texto.size() - 1))) {
                                    setarErroNoContexto(palavraFormatada);
                                }
                            }

                            texto.add(palavraFormatada);
                            palavra = new StringBuilder();
                        }
                    }
                }

                caracterLido = fileInputStream.read();
            }

            fileInputStream.close();
            return texto;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setarErroNoContexto(String variavel) {
        List<String> mensagensValidacao = resultadoExecucao.getMensagensValidacao();
        String erro = "Erro na linha " + totalLinhasArquivo + " a variavel " + variavel + " é invalida";
        mensagensValidacao.add(erro);
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
}
