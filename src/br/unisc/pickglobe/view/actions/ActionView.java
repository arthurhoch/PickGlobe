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

import br.unisc.pickglobe.controller.ListaExtensoesJpaController;
import br.unisc.pickglobe.controller.ListaPalavrasJpaController;
import br.unisc.pickglobe.controller.SiteJpaController;
import br.unisc.pickglobe.controller.TipoListaJpaController;
import br.unisc.pickglobe.model.ListaExtensoes;
import br.unisc.pickglobe.model.ListaPalavras;
import br.unisc.pickglobe.model.Site;
import br.unisc.pickglobe.model.TipoLista;
import br.unisc.pickglobe.view.tabelas.ComboItem;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author arthurhoch
 */
public class ActionView {

    protected final EntityManagerFactory emf;
    protected final ListaExtensoesJpaController listaExtensoesJpaController;
    protected final ListaPalavrasJpaController listaPalavrasJpaController;
    protected final SiteJpaController siteJpacontroller;
    protected final TipoListaJpaController tipoListaJpaController;

    protected final List<ComboItem> comboNomeListas;
    protected final List<ComboItem> comboNomeExtensoes;
    protected final List<ComboItem> comboNomeSite;
    protected final List<ComboItem> comboTipoLista;

    public ActionView() {
        this.emf = Persistence.createEntityManagerFactory("TrabalhoPGBDPU");

        this.listaExtensoesJpaController = new ListaExtensoesJpaController(emf);
        this.listaPalavrasJpaController = new ListaPalavrasJpaController(emf);
        this.siteJpacontroller = new SiteJpaController(emf);
        this.tipoListaJpaController = new TipoListaJpaController(emf);

        this.comboNomeListas = new LinkedList<>();
        this.comboNomeExtensoes = new LinkedList<>();
        this.comboNomeSite = new LinkedList<>();
        this.comboTipoLista = new LinkedList<>();
        
    }

    public boolean existeSites() {
        List<Site> site = siteJpacontroller.findSiteEntities();
        return site.size() > 0;
    }

    public boolean exitesExtensoes() {
        List<ListaExtensoes> listaExtensoes = listaExtensoesJpaController.findListaExtensoesEntities();
        return listaExtensoes.size() > 0;
    }

    public boolean exiteListas() {
        List<ListaPalavras> listaPalavras = listaPalavrasJpaController.findListaPalavrasEntities();
        return listaPalavras.size() > 0;
    }

    public List<ComboItem> getNomeListas() {
        List<ListaPalavras> listaPalavras = listaPalavrasJpaController.findListaPalavrasEntities();

        comboNomeListas.clear();

        for (int i = 0; i < listaPalavras.size(); i++) {
            ListaPalavras lps = listaPalavras.get(i);
            comboNomeListas.add(new ComboItem(lps.getCodListaPalavras().toString(), lps.getNomeLista()));
        }

        return comboNomeListas;
    }

    public List<ComboItem> getNomeExtensoes() {
        List<ListaExtensoes> listaExtensoes = listaExtensoesJpaController.findListaExtensoesEntities();

        comboNomeExtensoes.clear();

        for (int i = 0; i < listaExtensoes.size(); i++) {
            ListaExtensoes le = listaExtensoes.get(i);
            comboNomeExtensoes.add(new ComboItem(le.getCodListaExtensoes().toString(), le.getNomeListaExtensoes()));
        }

        return comboNomeExtensoes;
    }

    public List<ComboItem> getNomeSites() {
        List<Site> listaStes = siteJpacontroller.findSiteEntities();

        comboNomeSite.clear();

        for (int i = 0; i < listaStes.size(); i++) {
            Site s = listaStes.get(i);
            comboNomeSite.add(new ComboItem(s.getCodSite().toString(), s.getUrl()));
        }

        return comboNomeSite;
    }

    public List<ComboItem> getNomeExtensoes(String siteUrl) {
        Site site = siteJpacontroller.findSite(getKeyComboNomeSite(siteUrl));
        String extensao = site.getCodListaExtensoes().getNomeListaExtensoes();

        List<ComboItem> comboItem = getNomeExtensoes();

        return ordernarComboItem(comboItem, extensao);
    }

    public List<ComboItem> getNomeListas(String siteUrl) {
        Site site = siteJpacontroller.findSite(getKeyComboNomeSite(siteUrl));
        String lista = site.getCodListaPalavras().getNomeLista();

        List<ComboItem> comboItem = getNomeExtensoes();

        return ordernarComboItem(comboItem, lista);

    }

    private List<ComboItem> ordernarComboItem(List<ComboItem> comboItem, String primeiroItem) {
        for (int i = 0; i < comboItem.size(); i++) {
            ComboItem item = comboItem.get(i);
            if (item.toString().equals(primeiroItem)) {
                ComboItem itemSite = item;
                ComboItem itemChange = comboItem.get(0);
                comboItem.set(0, itemSite);
                comboItem.set(i, itemChange);
                break;
            }
        }

        return comboItem;
    }

    public List<ComboItem> getNomeTipoLista() {
        List<TipoLista> listaTipoLista = tipoListaJpaController.findTipoListaEntities();

        comboTipoLista.clear();

        for (int i = 0; i < listaTipoLista.size(); i++) {
            TipoLista tl = listaTipoLista.get(i);
            comboTipoLista.add(new ComboItem(tl.getCodTipoLista().toString(), tl.getTipo()));
        }

        return comboTipoLista;
    }

    public int getKeyComboNomeListasPalavras(String value) {
        for (ComboItem item : comboNomeListas) {
            if (item.getValue().equals(value)) {
                return Integer.parseInt(item.getKey());
            }
        }
        return 0;
    }

    public int getKeyComboNomeExtensoes(String value) {
        for (ComboItem item : comboNomeExtensoes) {
            if (item.getValue().equals(value)) {
                return Integer.parseInt(item.getKey());
            }
        }
        return 0;
    }

    public int getKeyComboNomeSite(String value) {
        for (ComboItem item : comboNomeSite) {
            if (item.getValue().equals(value)) {
                return Integer.parseInt(item.getKey());
            }
        }
        return 0;
    }

    public int getKeyComboNomeListaTipo(String value) {
        for (ComboItem item : comboTipoLista) {
            if (item.getValue().equals(value)) {
                return Integer.parseInt(item.getKey());
            }
        }
        return 0;
    }

    public String calculateTime(long seconds) {
        int day = (int) TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24);
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
        long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60);

        return day + " Day " + hours + " Hour " + minute + " Minute " + second + " Seconds ";
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

}
