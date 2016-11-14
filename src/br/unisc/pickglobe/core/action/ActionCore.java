/*
 * Copyright (C) 2016 arthurhoch
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
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
        try {
            linkJpaController.create(link);
        } catch (Exception ex) {
            Logger.getLogger(ActionCore.class.getName()).log(Level.SEVERE, null, ex);
        }
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

            if (link.getMd5().equals(listaLink.getMd5())) {
                return listaLink;
            }

        }
        return null;
    }

    public boolean temPalavra(int codPalavra, List<PalavraLink> listPalavraLink ) {
        
        for (PalavraLink palavraLink : listPalavraLink) {
            if(palavraLink.getPalavra().getCodPalavra() == codPalavra)
                return false;
        }
        
        return true;
    }

}
