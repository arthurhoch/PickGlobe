/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.view.actions;

import br.unisc.pickglobe.controller.ListaExtensoesJpaController;
import br.unisc.pickglobe.controller.ListaPalavrasJpaController;
import br.unisc.pickglobe.model.ListaExtensoes;
import br.unisc.pickglobe.model.ListaPalavras;
import br.unisc.pickglobe.view.tabelas.ComboItem;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author arthurhoch
 */
public class Action {

    protected final EntityManagerFactory emf;
    protected final ListaExtensoesJpaController listaExtensoesJpaController;
    protected final ListaPalavrasJpaController listaPalavrasJpaController;
    
    protected final List<ComboItem> comboNomeListas;
    protected final List<ComboItem> comboNomeExtensoes;
    
    public Action() {
        this.emf = Persistence.createEntityManagerFactory("TrabalhoPGBDPU");
        this.listaExtensoesJpaController = new ListaExtensoesJpaController(emf);
        this.listaPalavrasJpaController = new ListaPalavrasJpaController(emf);
        this.comboNomeListas = new LinkedList<>();
        this.comboNomeExtensoes = new LinkedList<>();
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
    
    public int getKeyComboNomeListas(String value) {
        for (ComboItem item : comboNomeListas) {
            if(item.getValue().equals(value))
                return Integer.parseInt(item.getKey()) ;
        }
        return 0;
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
    
    public int getKeyComboNomeExtensoes(String value) {
        for (ComboItem item : comboNomeExtensoes) {
            if(item.getValue().equals(value))
                return Integer.parseInt(item.getKey()) ;
        }
        return 0;
    }

}
