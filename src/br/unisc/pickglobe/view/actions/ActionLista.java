/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.view.actions;

import br.unisc.pickglobe.controller.PalavraJpaController;
import br.unisc.pickglobe.model.ListaPalavras;
import br.unisc.pickglobe.model.Palavra;
import java.util.LinkedList;
import java.util.List;

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
        int key = getKeyComboNomeListas(nomeLista);
        
        ListaPalavras listaPalavras = listaPalavrasJpaController.findListaPalavras(key);
        
        
        
        
    }

    public void addPalavras(String nomeLista, String[] palavras) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void rmPalavras(String nomeLista, String[] palavras) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void criarLista(String nomeLista, String[] palavras, String tipo) {
        ListaPalavras listaPalavras = new ListaPalavras();
        listaPalavras.setNomeLista(nomeLista);

        listaPalavras.setPalavraList(arrayExtesao2ListPalavras(palavras, tipo));

        listaPalavrasJpaController.create(listaPalavras);
    }

    private List<Palavra> arrayExtesao2ListPalavras(String[] split, String tipo) {
        List<Palavra> palavras = new LinkedList<>();

        for (String palavraString : split) {
            Palavra p = new Palavra();
            p.setTipo(tipo);
            p.setPalavra(palavraString);

            System.out.println(p.getCodPalavra() + p.getPalavra() + p.getTipo());

            palavraJpaController.create(p);
        }

        return palavras;
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
