/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.core.action;

import br.unisc.pickglobe.controller.ColetaJpaController;
import br.unisc.pickglobe.controller.LinkJpaController;
import br.unisc.pickglobe.controller.PalavraLinkJpaController;
import br.unisc.pickglobe.model.Coleta;
import br.unisc.pickglobe.model.Link;
import br.unisc.pickglobe.model.PalavraLink;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author arthurhoch
 */
public class ActionCore {

    protected final EntityManagerFactory emf;

    private final ColetaJpaController coletaJpaController;
    private final LinkJpaController linkJpaController;
    private final PalavraLinkJpaController palavraLinkJpaController;

    public ActionCore() {
        this.emf = Persistence.createEntityManagerFactory("TrabalhoPGBDPU");
        this.coletaJpaController = new ColetaJpaController(emf);
        this.linkJpaController = new LinkJpaController(emf);
        this.palavraLinkJpaController = new PalavraLinkJpaController(emf);
    }

    public void createColeta(Coleta coleta) {
        coletaJpaController.create(coleta);
    }

    public void createLink(Link link) {
        linkJpaController.create(link);
    }

    public void createPalavraLink(PalavraLink palavraLink) {
        try {
            palavraLinkJpaController.create(palavraLink);
        } catch (Exception ex) {
            Logger.getLogger(ActionCore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean checkSiteIn(String md5) {

        try {

            List<Link> links = linkJpaController.findLinkEntities();

            for (Link link : links) {
                if (link.getMd5().equals(md5)) {
                    return true;
                }
            }

            return false;
        } catch (Exception ex) {
            return false;
        }
    }

    public Link checkMd5(Link link) {
        
        List<Link> listaLinks = linkJpaController.findLinkEntities();
        
        for (Link listaLink : listaLinks) {
            
            if(link.getMd5().equals(listaLink.getMd5())) {
                return listaLink;
            }
            
        }
        return null;
    }

}
