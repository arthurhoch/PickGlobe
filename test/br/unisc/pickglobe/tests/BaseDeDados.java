package br.unisc.pickglobe.tests;

import br.unisc.pickglobe.controller.ExtensaoController;
import br.unisc.pickglobe.model.Extensao;
import java.util.LinkedList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author arthurhoch
 */
public class BaseDeDados {
    
    public static void main(String[] args) {
        testarBase();
    }
    
    public static void testarBase() {
        ExtensaoController controllerExtensao = new ExtensaoController();
        
        Extensao ext = new Extensao();
        
        ext.setTipoExtensao("teste asdasdasdas");
        
        
        List<Extensao> listaExtensoes = new LinkedList<>();
        
        listaExtensoes = controllerExtensao.selectAll();
        
        for (Extensao list : listaExtensoes){
            System.out.println("Nome Extensao: "+ list.getTipoExtensao());
        }
        
        Extensao ext2 = new Extensao();
        
        
        System.out.println("ex: "+ext2.getTipoExtensao());
        
        Extensao ext3 = new Extensao();
        ext3.setTipoExtensao("asdasdasd");
        controllerExtensao.insert(ext3);
        
        Extensao ext4 = new Extensao();
        
        ext4.setCodExtensao(20);
        ext4.setTipoExtensao("dddd Alterada");
        controllerExtensao.update(ext4);
    }
    
}
