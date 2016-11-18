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
package br.unisc.pickglobe.view.estatisticas;

import br.unisc.pickglobe.controller.ColetaJpaController;
import br.unisc.pickglobe.controller.LinkJpaController;
import br.unisc.pickglobe.controller.PalavraJpaController;
import br.unisc.pickglobe.controller.PalavraLinkJpaController;
import br.unisc.pickglobe.controller.SiteJpaController;
import br.unisc.pickglobe.model.Coleta;
import br.unisc.pickglobe.model.PalavraLink;
import br.unisc.pickglobe.view.tabelas.PalavraContador;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author arthurhoch
 */
public class Estatisticas {

    private final EntityManagerFactory emf;
    private final PalavraJpaController palavraJpaController;
    private final PalavraLinkJpaController palavraLinkJpaController;
    private final LinkJpaController linkJpaController;
    private final ColetaJpaController coletaJpaController;
    private final SiteJpaController siteJpaController;

    public Estatisticas() {
        this.emf = Persistence.createEntityManagerFactory("TrabalhoPGBDPU");
        this.palavraJpaController = new PalavraJpaController(emf);
        this.palavraLinkJpaController = new PalavraLinkJpaController(emf);
        this.linkJpaController = new LinkJpaController(emf);
        this.coletaJpaController = new ColetaJpaController(emf);
        this.siteJpaController = new SiteJpaController(emf);
    }

    public List<PalavraContador> palavraQuantidade() {

        PalavraContador palavraContador = new PalavraContador();

        List<PalavraContador> palavraContadas = new LinkedList<>();

        List<PalavraLink> listPalavraLink = palavraLinkJpaController.findPalavraLinkEntities();

        String palavra = "";
        int quantidade = 0;

        for (PalavraLink palavraLink : listPalavraLink) {
            palavra = palavraLink.getPalavra().getPalavra();
            quantidade = palavraLink.getQuantidade();

            if (palavraContador.containsPalavra(palavraContadas, palavra)) {
                palavraContador.palavraContar(palavraContadas, palavra, quantidade);
            } else if (quantidade > 0) {
                palavraContadas.add(new PalavraContador(palavra, quantidade));
            }

        }
        return palavraContadas;
    }

    public List<Coleta> getColeta() {
        return coletaJpaController.findColetaEntities();
    }
}
