/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.view.actions;

import br.unisc.pickglobe.controller.SiteController;
import br.unisc.pickglobe.model.Site;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author arthurhoch
 */
public class ActionSite extends Action {

    public String[] getNomeSites() {
        SiteController siteController = new SiteController();
        List<Site> sites = siteController.selectAll();
        
        String sitesString = new String();
        
        for (Site site : sites) {
            
            if(sitesString.isEmpty())
                sitesString += site.getUrl();
            else
                sitesString += ";" + site.getUrl();
        }
        
        return sitesString.split(";");
    }

    public String[] getNomeExtensoes(String site) {
        /* Ordenar com a primeira exteção do site referente*/
        return getNomeExtensoes();
    }

    public void criarSite(String URL, String nomeListaPalavras, int intervaloConsulta) {
        Site site = new Site();

        site.setUrl(URL);

    }

    public void atualizarSite(String URL, String novaURL, String novaLista, int intervaloConsulta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void deletarSite(String siteDeletar) {

        try {
            SiteController siteController = new SiteController();
            Site site = (Site) siteController.select(siteDeletar);
            siteController.delete(site);
        } catch (SQLException ex) {
            //Logger.getLogger(ActionSite.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void criarSite(String URL, String nomeListaPalavras, String nomeListaExtensoes, int intervaloConsulta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void atualizarSite(String URL, String novaURL, String novaListaPalavras, String novaListaExtensoes, int intervaloConsulta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
