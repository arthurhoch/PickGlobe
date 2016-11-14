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
package br.unisc.pickglobe.view.actions;

import br.unisc.pickglobe.controller.ColetaJpaController;
import br.unisc.pickglobe.controller.LinkJpaController;
import br.unisc.pickglobe.controller.PalavraLinkJpaController;
import br.unisc.pickglobe.controller.exceptions.IllegalOrphanException;
import br.unisc.pickglobe.controller.exceptions.NonexistentEntityException;
import br.unisc.pickglobe.model.Coleta;
import br.unisc.pickglobe.model.Link;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arthurhoch
 */
public class ActionPrincipal extends ActionView {

    private final ColetaJpaController coletaJpaController;
    private final LinkJpaController linkJpaController;
    private final PalavraLinkJpaController palavraLinkJpaController;

    public ActionPrincipal() {
        this.coletaJpaController = new ColetaJpaController(emf);
        this.linkJpaController = new LinkJpaController(emf);
        this.palavraLinkJpaController = new PalavraLinkJpaController(emf);
    }

    public void delAllColetas() {
        Runnable runnable = () -> {

            List<Coleta> coletas = coletaJpaController.findColetaEntities();

            for (Coleta coleta : coletas) {
                try {
                    coletaJpaController.destroy(coleta.getCodColeta());
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(ActionPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            List<Link> links = linkJpaController.findLinkEntities();

            for (Link link : links) {
                try {
                    linkJpaController.destroy(link.getCodLink());
                } catch (IllegalOrphanException | NonexistentEntityException ex) {
                    Logger.getLogger(ActionPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        };

        Thread thread = new Thread(runnable);
        thread.start();

    }
}
