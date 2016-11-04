/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.view.actions;

import br.unisc.pickglobe.controller.ExtensaoJpaController;
import br.unisc.pickglobe.model.Extensao;
import br.unisc.pickglobe.model.ListaExtensoes;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author m93492
 */
public class ActionExtensao extends Action {

    private final ExtensaoJpaController extensaoJpaController;

    public ActionExtensao() {
        this.extensaoJpaController = new ExtensaoJpaController(emf);
    }

    public void criarListaExtensao(String nomeLista, String[] split) {
        ListaExtensoes listaExtensoes = new ListaExtensoes();

        listaExtensoes.setNomeListaExtensoes(nomeLista);

        List<Extensao> extensoes = new LinkedList<>();

        for (String nomeExtensao : split) {
            Extensao extensao = new Extensao();
            extensao.setNomeExtensao(nomeExtensao);
            extensaoJpaController.create(extensao);
            extensoes.add(extensao);
        }

        listaExtensoes.setExtensaoList(extensoes);
        listaExtensoesJpaController.create(listaExtensoes);
    }

    public void atualizarListaExtensao(String nomeLista, String[] split) {
       
    }

    public String getListaExtensoes(String listaExtensoesNome) {
        List<ListaExtensoes> listaExtensoes = listaExtensoesJpaController.findListaExtensoesEntities();
        List<Extensao> extensoes;
        String nomeExtensoes = new String();
        
        for (ListaExtensoes listaExtensoe : listaExtensoes) {
            if(listaExtensoe.getNomeListaExtensoes() == null ? listaExtensoesNome == null : listaExtensoe.getNomeListaExtensoes().equals(listaExtensoesNome)) {
                extensoes = listaExtensoe.getExtensaoList();
                
                for (int i = 0; i < extensoes.size(); i++) {
                    nomeExtensoes += extensoes.get(i).getNomeExtensao();
                    if(i != extensoes.size())
                        nomeExtensoes += ";";
                }
                
                for (Extensao extensao : extensoes) {
                    
                }
            }
        }
        
        return nomeExtensoes;
    }

}
