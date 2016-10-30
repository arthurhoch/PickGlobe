/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.main;

import br.unisc.pickglobe.core.Util;
import br.unisc.pickglobe.model.Link;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author will
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            Util util = new Util();
            LinkedList<Link> lista = null;
            lista = (LinkedList<Link>) util.getLinksPage("http://terra.com.br");

            System.out.println("Teste");

            for (Link link : lista) {
                System.out.println(link.getUrl());
                System.out.println(link.getCaminho());
            }

        } catch (IOException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
