/*
 * Copyright (C) 2016 arthurhoch
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
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
