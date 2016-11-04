/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.view.actions;

import br.unisc.pickglobe.controller.SiteJpaController;
import br.unisc.pickglobe.model.ListaExtensoes;
import br.unisc.pickglobe.model.ListaPalavras;
import br.unisc.pickglobe.model.Site;
import br.unisc.pickglobe.view.tabelas.ComboItem;
import java.util.List;

/**
 *
 * @author arthurhoch
 */
public class ActionSite extends Action {

    private SiteJpaController siteController;

    public ActionSite() {
        this.siteController = new SiteJpaController(emf);
    }

    public List<ComboItem> getNomeExtensoes(String site) {
        /* Ordenar com a primeira exteção do site referente*/
        return getNomeExtensoes();
    }

    public void atualizarSite(String URL, String novaURL, String novaLista, int intervaloConsulta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void deletarSite(String siteDeletar) {

    }

    public void criarSite(String URL, String nomeListaPalavras, String nomeListaExtensoes, int intervaloConsulta) {
        Site site = new Site();

        int keyListaExtensoes = getKeyComboNomeExtensoes(nomeListaExtensoes);

        int keyListaPalavras = getKeyComboNomeListas(nomeListaPalavras);

        ListaExtensoes listaExtensoes = listaExtensoesJpaController.findListaExtensoes(keyListaExtensoes);

        ListaPalavras listaPalavras = listaPalavrasJpaController.findListaPalavras(keyListaPalavras);

        site.setUrl(URL);
        site.setCodListaExtensoes(listaExtensoes);
        site.setCodListaPalavras(listaPalavras);
        site.setIntervaloColeta(intervaloConsulta);

        siteController.create(site);
    }

    public void atualizarSite(String URL, String novaURL, String novaListaPalavras, String novaListaExtensoes, int intervaloConsulta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
