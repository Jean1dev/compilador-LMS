/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screen;

import main.arquivo.ArquivoUtils;

/**
 *
 * @author jeanfernandes
 */
public class ActionCodeEditor {

    public void salvar(String conteudo) {
        ArquivoUtils.gravarArquivo(conteudo);
    }
}
