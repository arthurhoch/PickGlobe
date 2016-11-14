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
