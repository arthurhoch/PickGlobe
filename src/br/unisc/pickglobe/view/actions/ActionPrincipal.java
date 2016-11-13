/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.view.actions;

import br.unisc.pickglobe.controller.ColetaJpaController;
import br.unisc.pickglobe.controller.exceptions.NonexistentEntityException;
import br.unisc.pickglobe.model.Coleta;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author arthurhoch
 */
public class ActionPrincipal extends ActionView {

    private ColetaJpaController coletaJpaController;

    public ActionPrincipal() {
        this.coletaJpaController = new ColetaJpaController(emf);
    }

    public void delAllColetas() {
        List<Coleta> coletas = coletaJpaController.findColetaEntities();

        for (Coleta coleta : coletas) {
            try {
                coletaJpaController.destroy(coleta.getCodColeta());
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(ActionPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
    public void salvarHistorico(String input) throws IOException{

        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(null);

        String cam = null;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File arq = fc.getSelectedFile().getAbsoluteFile();
            cam = arq.getAbsolutePath();
        }
        FileWriter arq = new FileWriter(cam);
        
        PrintWriter gravarArq = new PrintWriter(arq);

        
        gravarArq.printf(input);

        arq.close();
    }

}
