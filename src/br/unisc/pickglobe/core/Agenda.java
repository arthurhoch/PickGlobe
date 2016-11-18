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
package br.unisc.pickglobe.core;

import br.unisc.pickglobe.core.action.ActionCore;
import br.unisc.pickglobe.model.Coleta;
import br.unisc.pickglobe.model.Link;
import br.unisc.pickglobe.model.Palavra;
import br.unisc.pickglobe.model.PalavraLink;
import br.unisc.pickglobe.model.Site;
import br.unisc.pickglobe.view.tabelas.FilaExecucao;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 *
 * @author arthurhoch
 */
public class Agenda {

    private static final String PASTA = "Downloads";

    private final FilaExecucao modelFilaExecucao;
    private final JLabel status;

    private final JTextArea txtArea;
    private boolean rodando;
    private final static int SLEEP_TIME = 1;
    private final Md5helper md5;
    private final Util inutil;
    private final ActionCore action;

    private boolean bloked;

    public Agenda(FilaExecucao modelFilaExecucao, JLabel status, JTextArea txtArea) {
        this.modelFilaExecucao = modelFilaExecucao;
        this.rodando = false;
        this.status = status;
        this.txtArea = txtArea;
        this.md5 = new Md5helper();
        this.inutil = new Util();
        this.action = new ActionCore();
        this.bloked = false;
    }

    public void start() {
        Runnable runnable = () -> {
            try {
                while (rodando) {
                    bloked = true;
                    List<Site> sites = modelFilaExecucao.getLinhas();
                    TimeUnit.SECONDS.sleep(SLEEP_TIME);
                    for (Site site : sites) {

                        if (site.getStatus()) {

                            int key = site.getCodSite();
                            int novoTempo = modelFilaExecucao.getTempoRestante(key);
                            novoTempo -= SLEEP_TIME;

                            if (novoTempo == 0) {
                                modelFilaExecucao.setTempoRestante(key, site.getIntervaloColeta());
                                coletarPalavras(site);
                            } else {

                                if (novoTempo < 0) {
                                    novoTempo = 0;
                                }

                                modelFilaExecucao.setTempoRestante(key, novoTempo);
                            }
                        }
                    }
                }
                bloked = false;
            } catch (InterruptedException e) {
                bloked = false;
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void coletarPalavras(Site site) {
        status.setText(site.getUrl());
        txtArea.append("Analizando: " + site.getUrl() + "\n");

        Coleta coleta = new Coleta();
        coleta.setCodSite(site);
        Calendar calendar = Calendar.getInstance();
        java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
        coleta.setDate(date);
        java.sql.Time time = new java.sql.Time(calendar.getTime().getTime());
        coleta.setTime(time);
        coleta.setMd5(md5.string2md5(coleta.getCodSite().getUrl()));

        List<Link> listaLinks = inutil.getLinksPage(site.getUrl(), site.getCodListaExtensoes().getExtensaoList());
        List<Link> novaListaLinks = new LinkedList<>();
        inutil.saveListLinks(listaLinks, PASTA + "/" + coleta.getMd5());
        txtArea.append("Baixando páginas 2º nível: " + site.getUrl() + "\n");
        List<Palavra> palavras = site.getCodListaPalavras().getPalavraList();

        int tipo = site.getCodListaPalavras().getCodTipoLista().getCodTipoLista();

        txtArea.append("Contando palavras: " + site.getUrl() + "\n");

        for (Link link : listaLinks) {

            Link tempLink;
            if ((tempLink = action.checkMd5(link)) == null) {
                action.createLink(link);
                novaListaLinks.add(link);
            } else {
                link = tempLink;
                novaListaLinks.add(tempLink);
            }

            txtArea.append("Verificando: " + link.getUrl() + "\n");
            
            for (Palavra palavra : palavras) {

                txtArea.append("Por: (" + palavra.getPalavra() + ")" + "\n");

                if (action.temPalavra(palavra.getCodPalavra(), link.getPalavraLinkList())) {

                    int quantidade = 0;

                    switch (tipo) {
                        case 1:
                        case 2:
                            quantidade = inutil.contarPalavras(link, palavra.getPalavra());
                            break;
                        case 3:
                            quantidade = inutil.contarPalavrasComcapitalizacao(link, palavra.getPalavra());
                            break;
                        case 4:
                            quantidade = inutil.contarPalavrasSemcapitalizacao(link, palavra.getPalavra());
                            break;
                        default:
                            quantidade = inutil.contarPalavras(link, palavra.getPalavra());
                            break;

                    }
                    
                                    
                    PalavraLink palavraLink = new PalavraLink();
                    palavraLink.setLink(link);
                    palavraLink.setPalavra(palavra);
                    palavraLink.setQuantidade(quantidade);

                    action.createPalavraLink(palavraLink);

                }
            }
        }

        coleta.setLinkList(novaListaLinks);
        action.createColeta(coleta);

        txtArea.setText("");
        status.setText("...");
    }

    public boolean isRodando() {
        return rodando;
    }

    public void setRodando(boolean rodando) {
        this.rodando = rodando;
    }

    public boolean isBloked() {
        return bloked;
    }

    public void setBloked(boolean bloked) {
        this.bloked = bloked;
    }
}
