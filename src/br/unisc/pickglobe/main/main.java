/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.main;

import br.unisc.pickglobe.view.TelaPrincipal;
import java.sql.SQLException;

/**
 *
 * @author will
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {    
        
        TelaPrincipal tp = new TelaPrincipal();
        tp.setVisible(true);

    }

}
