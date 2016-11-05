/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.tests;

import br.unisc.pickglobe.model.Palavra;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author arthurhoch
 */
public class AbrirArquivo {
     public static void main(String[] args) throws Exception {
        abrirArquivo();
    }
     
     
     public static void abrirArquivo() throws FileNotFoundException, IOException {
         
         List<Palavra> listaPalavras = new LinkedList<>();
                 
         BufferedReader br = new BufferedReader(new FileReader("/home/arthurhoch/palavras.txt"));
         
         String palavra;
         
         while( (palavra = br.readLine()) != null ) {
             Palavra p = new Palavra();
             p.setPalavra(palavra);
             listaPalavras.add(p);
         }
         
         
     }
}
