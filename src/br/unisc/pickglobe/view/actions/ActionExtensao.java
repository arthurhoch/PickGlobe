/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.view.actions;

import br.unisc.pickglobe.controller.ExtensaoJpaController;
import br.unisc.pickglobe.controller.exceptions.NonexistentEntityException;
import br.unisc.pickglobe.model.Extensao;
import br.unisc.pickglobe.model.ListaExtensoes;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author m93492
 */
public class ActionExtensao extends ActionView {

    private final ExtensaoJpaController extensaoJpaController;

    public ActionExtensao() {
        this.extensaoJpaController = new ExtensaoJpaController(emf);
    }

    private List<Extensao> arrayExtesao2List(String[] split) {
        List<Extensao> extensoes = new LinkedList<>();

        for (String nomeExtensao : split) {
            Extensao extensao = new Extensao();
            extensao.setNomeExtensao(nomeExtensao);
            extensaoJpaController.create(extensao);
            extensoes.add(extensao);
        }

        return extensoes;
    }

    public void criarListaExtensao(String nomeLista, String[] split) {
        ListaExtensoes listaExtensoes = new ListaExtensoes();

        listaExtensoes.setNomeListaExtensoes(nomeLista);
        listaExtensoes.setExtensaoList(arrayExtesao2List(split));
        listaExtensoesJpaController.create(listaExtensoes);
    }

    public void atualizarListaExtensao(String nomeLista, String[] split) {

        int key = getKeyComboNomeExtensoes(nomeLista);

        ListaExtensoes listaExtensoes = listaExtensoesJpaController.findListaExtensoes(key);

        deletarExtensoes(listaExtensoes.getExtensaoList());

        listaExtensoes.setExtensaoList(arrayExtesao2List(split));

        try {
            listaExtensoesJpaController.edit(listaExtensoes);
        } catch (Exception ex) {
            Logger.getLogger(ActionExtensao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void deletarExtensoes(List<Extensao> extensoes) {
        for (Extensao extensao : extensoes) {
            try {
                extensaoJpaController.destroy(extensao.getCodExtensao());
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(ActionExtensao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String getListaExtensoes(String listaExtensoesNome) {
        List<ListaExtensoes> listaExtensoes = listaExtensoesJpaController.findListaExtensoesEntities();
        List<Extensao> extensoes;
        String nomeExtensoes = new String();

        for (ListaExtensoes listaExtensoe : listaExtensoes) {
            if (listaExtensoe.getNomeListaExtensoes() == null ? listaExtensoesNome == null : listaExtensoe.getNomeListaExtensoes().equals(listaExtensoesNome)) {
                extensoes = listaExtensoe.getExtensaoList();

                for (int i = 0; i < extensoes.size(); i++) {
                    nomeExtensoes += extensoes.get(i).getNomeExtensao();
                    if (i != extensoes.size()) {
                        nomeExtensoes += ";";
                    }
                }
            }
        }

        return nomeExtensoes;
    }

    public void deletarListaExtensao(String nomeLista) {
        try {
            int key = getKeyComboNomeExtensoes(nomeLista);
            ListaExtensoes listaExtensoes = listaExtensoesJpaController.findListaExtensoes(key);
            deletarExtensoes(listaExtensoes.getExtensaoList());
            listaExtensoesJpaController.destroy(key);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ActionExtensao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
