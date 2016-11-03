/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.main;

import br.unisc.pickglobe.controller.ExtensaoController;
import br.unisc.pickglobe.model.Extensao;
import br.unisc.pickglobe.view.TelaPrincipal;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author will
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        
        /*
        ExtensaoController controllerExtensao = new ExtensaoController();
        
        Extensao ext = new Extensao();
        
        ext.setTipoExtensao("teste asdasdasdas");
        
        
        ArrayList<Extensao> listaExtensoes = new ArrayList();
        
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
        */
        
        TelaPrincipal tp = new TelaPrincipal();
        tp.setVisible(true);
        
    }

}
