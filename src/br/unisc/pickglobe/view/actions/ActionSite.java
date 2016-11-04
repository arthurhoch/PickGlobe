/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.view.actions;

import br.unisc.pickglobe.view.tabelas.ComboItem;
import java.util.List;

/**
 *
 * @author arthurhoch
 */
public class ActionSite extends Action {

    public String[] getNomeSites() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<ComboItem> getNomeExtensoes(String site) {
        /* Ordenar com a primeira exteção do site referente*/
        return getNomeExtensoes();
    }

    public void criarSite(String URL, String nomeListaPalavras, int intervaloConsulta) {

    }

    public void atualizarSite(String URL, String novaURL, String novaLista, int intervaloConsulta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void deletarSite(String siteDeletar) {

    }

    public void criarSite(String URL, String nomeListaPalavras, String nomeListaExtensoes, int intervaloConsulta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void atualizarSite(String URL, String novaURL, String novaListaPalavras, String novaListaExtensoes, int intervaloConsulta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
