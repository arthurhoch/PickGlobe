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

import br.unisc.pickglobe.controller.PalavraJpaController;
import br.unisc.pickglobe.controller.exceptions.IllegalOrphanException;
import br.unisc.pickglobe.controller.exceptions.NonexistentEntityException;
import br.unisc.pickglobe.model.ListaPalavras;
import br.unisc.pickglobe.model.Palavra;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arthurhoch
 */
public class ActionLista extends ActionView {

    private final PalavraJpaController palavraJpaController;

    public ActionLista() {
        this.palavraJpaController = new PalavraJpaController(emf);
    }

    public void deletarLista(String nomeLista) {
        try {
            int key = getKeyComboNomeListasPalavras(nomeLista);
            ListaPalavras listaPalavras = listaPalavrasJpaController.findListaPalavras(key);
            deletarListaPalavra(listaPalavras.getPalavraList());
            listaPalavrasJpaController.destroy(key);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ActionLista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void deletarListaPalavra(List<Palavra> palavras) {
        for (Palavra palavra : palavras) {
            try {
                palavraJpaController.destroy(palavra.getCodPalavra());
            } catch (IllegalOrphanException | NonexistentEntityException ex) {
                Logger.getLogger(ActionLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void addPalavras(String nomeLista, String[] palavras) {
        try {
            int key = getKeyComboNomeListasPalavras(nomeLista);
            ListaPalavras listaPalavras = listaPalavrasJpaController.findListaPalavras(key);
            listaPalavras.setPalavraList(arrayExtesao2ListPalavras(palavras));
            listaPalavrasJpaController.edit(listaPalavras);
        } catch (Exception ex) {
            Logger.getLogger(ActionLista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void rmPalavras(String nomeLista, String[] palavras) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private List<Palavra> arrayExtesao2ListPalavras(String[] split) {
        List<Palavra> palavras = new LinkedList<>();

        for (String palavraString : split) {
            Palavra p = new Palavra();
            p.setPalavra(palavraString);
            palavraJpaController.create(p);
            palavras.add(p);
        }

        return palavras;
    }

    public void criarLista(String nomeLista, String[] palavras, String tipo) {
        ListaPalavras listaPalavras = new ListaPalavras();

        listaPalavras.setCodTipoLista(tipoListaJpaController.findTipoLista(getKeyComboNomeListaTipo(tipo)));

        listaPalavras.setNomeLista(nomeLista);
        listaPalavras.setPalavraList(arrayExtesao2ListPalavras(palavras));

        listaPalavrasJpaController.create(listaPalavras);
    }

    public void criarListaArquivo(String nomeLista, String arquivoPalavras, String tipo) {
        ListaPalavras listaPalavras = new ListaPalavras();

        listaPalavras.setCodTipoLista(tipoListaJpaController.findTipoLista(getKeyComboNomeListaTipo(tipo)));

        listaPalavras.setNomeLista(nomeLista);
        addArquivoPalavras(listaPalavras, arquivoPalavras);

        listaPalavrasJpaController.create(listaPalavras);
    }

    public void addArquivoPalavras(String nomeLista, String arquivoPalavras) {
        ListaPalavras listaPalavras = listaPalavrasJpaController.findListaPalavras(getKeyComboNomeListasPalavras(nomeLista));
        addArquivoPalavras(listaPalavras, arquivoPalavras);
    }

    private void addArquivoPalavras(ListaPalavras listaPalavras, String arquivoPalavras) {
        BufferedReader br = null;
        try {

            List<Palavra> palavras = new LinkedList<>();
            br = new BufferedReader(new FileReader(arquivoPalavras));
            String palavra;
            br.readLine();
            while ((palavra = br.readLine()) != null) {
                Palavra p = new Palavra();
                p.setPalavra(palavra);
                palavras.add(p);
                palavraJpaController.create(p);
            }

            listaPalavras.setPalavraList(palavras);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ActionLista.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ActionLista.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(ActionLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
