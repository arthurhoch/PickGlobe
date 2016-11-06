/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.view.actions;

import br.unisc.pickglobe.model.Site;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author arthurhoch
 */
public class ActionPrincipal extends Action {
    
    public void popularSites(DefaultTableModel modelo) {
        
        List<Site> sites = siteJpacontroller.findSiteEntities();
        
        for (Site site : sites) {
            modelo.addRow(new Object[]{site.getUrl(), site.getCodListaPalavras().getNomeLista(), calculateTime(site.getIntervaloColeta()), site.getStatus()});
        }
    }
}
