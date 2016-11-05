/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.view.actions;

import br.unisc.pickglobe.controller.PalavraJpaController;
import br.unisc.pickglobe.controller.exceptions.IllegalOrphanException;
import br.unisc.pickglobe.controller.exceptions.NonexistentEntityException;
import br.unisc.pickglobe.model.ListaPalavras;
import br.unisc.pickglobe.model.Palavra;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arthurhoch
 */
public class ActionLista extends Action {

    private final PalavraJpaController palavraJpaController;

    public ActionLista() {
        this.palavraJpaController = new PalavraJpaController(emf);;
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
        System.out.println("Teste");
        for (Palavra palavra : palavras) {
            System.out.println("Cod: " + palavra.getCodPalavra());
            try {
                palavraJpaController.destroy(palavra.getCodPalavra());
            } catch (IllegalOrphanException | NonexistentEntityException ex) {
                Logger.getLogger(ActionLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void addPalavras(String nomeLista, String[] palavras) {
        
    }

    public void rmPalavras(String nomeLista, String[] palavras) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private List<Palavra> arrayExtesao2ListPalavras(String[] split, String tipo) {
        List<Palavra> palavras = new LinkedList<>();

        for (String palavraString : split) {
            Palavra p = new Palavra();
            p.setTipo(tipo);
            p.setPalavra(palavraString);
            palavraJpaController.create(p);
            palavras.add(p);
        }

        return palavras;
    }

    public void criarLista(String nomeLista, String[] palavras, String tipo) {
        ListaPalavras listaPalavras = new ListaPalavras();
        
        listaPalavras.setNomeLista(nomeLista);
        listaPalavras.setPalavraList(arrayExtesao2ListPalavras(palavras, tipo));

        listaPalavrasJpaController.create(listaPalavras);
    }

    public void criarListaArquivo(String nomeLista, String arquivoPalavras, String tipo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void addArquivoPalavras(String nomeLista, String arquivoAdd) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void rmArquivoPalavras(String nomeLista, String arquivoRm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
