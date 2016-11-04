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
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author arthurhoch
 */
public class Action {
    
    final EntityManagerFactory emf;
    final ListaExtensoesJpaController listaExtensoesJpaController;
    final ListaPalavrasJpaController listaPalavrasJpaController;
    
    
    public Action() {
        this.emf = Persistence.createEntityManagerFactory("TrabalhoPGBDPU");
        this.listaExtensoesJpaController = new ListaExtensoesJpaController(emf);
        this.listaPalavrasJpaController = new ListaPalavrasJpaController(emf);
    }

    public String[] getNomeListas() {
        List<ListaPalavras> listaPalavras = listaPalavrasJpaController.findListaPalavrasEntities();
        String[] nomeArray = new String[listaPalavras.size()];
        
        
        for (int i = 0; i < listaPalavras.size(); i++) {
            nomeArray[i] = listaPalavras.get(i).getNomeLista();
        }

        return nomeArray;
    }

    public String[] getNomeExtensoes() {
        List<ListaExtensoes> listaExtensoes = listaExtensoesJpaController.findListaExtensoesEntities();
        String[] nomeArray = new String[listaExtensoes.size()];
        
        
        for (int i = 0; i < listaExtensoes.size(); i++) {
            nomeArray[i] = listaExtensoes.get(i).getNomeListaExtensoes();
        }

        return nomeArray;
    }

}
