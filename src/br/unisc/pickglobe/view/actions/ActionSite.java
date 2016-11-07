/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.view.actions;

import br.unisc.pickglobe.controller.exceptions.NonexistentEntityException;
import br.unisc.pickglobe.model.ListaExtensoes;
import br.unisc.pickglobe.model.ListaPalavras;
import br.unisc.pickglobe.model.Site;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arthurhoch
 */
public class ActionSite extends ActionView {

    public void deletarSite(String siteDeletar) {
        try {
            Site site = siteJpacontroller.findSite(getKeyComboNomeSite(siteDeletar));
            siteJpacontroller.destroy(site.getCodSite());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ActionSite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Site criarSite(String URL, String nomeListaPalavras, String nomeListaExtensoes, int intervaloConsulta) {
        Site site = new Site();

        int keyListaExtensoes = getKeyComboNomeExtensoes(nomeListaExtensoes);

        int keyListaPalavras = getKeyComboNomeListasPalavras(nomeListaPalavras);

        ListaExtensoes listaExtensoes = listaExtensoesJpaController.findListaExtensoes(keyListaExtensoes);

        ListaPalavras listaPalavras = listaPalavrasJpaController.findListaPalavras(keyListaPalavras);

        site.setUrl(URL);
        site.setCodListaExtensoes(listaExtensoes);
        site.setCodListaPalavras(listaPalavras);
        site.setIntervaloColeta(intervaloConsulta);

        siteJpacontroller.create(site);

        return site;
    }

    public Site atualizarSite(String URL, String novaURL, String novaListaPalavras, String novaListaExtensoes, int intervaloConsulta) {
        Site site = siteJpacontroller.findSite(getKeyComboNomeSite(URL));
        try {

            site.setUrl(novaURL);
            site.setCodListaPalavras(listaPalavrasJpaController.findListaPalavras(getKeyComboNomeListasPalavras(novaListaPalavras)));
            site.setCodListaExtensoes(listaExtensoesJpaController.findListaExtensoes(getKeyComboNomeExtensoes(novaListaExtensoes)));
            site.setIntervaloColeta(intervaloConsulta);

            siteJpacontroller.edit(site);
        } catch (Exception ex) {
            Logger.getLogger(ActionSite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return site;
    }

    public int getSiteIntervalo(String URL) {
        Site site = siteJpacontroller.findSite(getKeyComboNomeSite(URL));
        return site.getIntervaloColeta();
    }

}
