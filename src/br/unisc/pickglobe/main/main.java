/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.main;

import br.unisc.pickglobe.core.Util;
import br.unisc.pickglobe.model.Extensao;
import br.unisc.pickglobe.model.Link;
import br.unisc.pickglobe.view.TelaPrincipal;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author will
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*
        Util util = new Util();

        Extensao extensao = new Extensao();
        extensao.setCodExtensao(1);
        extensao.setTipoExtensao(".html");
        List<Extensao> extensoes = new LinkedList<>();
        extensoes.add(extensao);

        LinkedList<Link> lista = null;
        lista = (LinkedList<Link>) util.getLinksPage("http://g1.com.br", extensoes);

        util.saveListLinks(lista, "g1");

        for (Link link : lista) {
            System.out.println(link.getUrl());
            System.out.println("Numero de palavras: " + util.contarPalavras(link, "casa"));

        }
        */
        
        new TelaPrincipal().setVisible(true);
        
    }

}
