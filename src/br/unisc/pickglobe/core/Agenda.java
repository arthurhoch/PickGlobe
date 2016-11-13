/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
        txtArea.append(site.getUrl()+"\n");

        Coleta coleta = new Coleta();
        coleta.setCodSite(site);
        Calendar calendar = Calendar.getInstance();
        java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
        coleta.setDate(date);
        java.sql.Time time = new java.sql.Time(calendar.getTime().getTime());
        coleta.setTime(time);

        List<Link> listaLinks = inutil.getLinksPage(site.getUrl(), site.getCodListaExtensoes().getExtensaoList());
        List<Link> listaLinksNovos = new LinkedList<>();
        inutil.saveListLinks(listaLinks, PASTA);

        List<Palavra> palavras = site.getCodListaPalavras().getPalavraList();

        int tipo = site.getCodListaPalavras().getCodTipoLista().getCodTipoLista();

        for (Link link : listaLinks) {

            Link linkTemp = action.checkMd5(link);
            if ((linkTemp) == null) {

                action.createLink(link);
                listaLinksNovos.add(link);

                for (Palavra palavra : palavras) {

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

                    status.setText(link.getUrl().substring(0, 30) + " (" + palavra.getPalavra() + ")");

                    txtArea.append(link.getUrl().substring(0, 30) + " (" + palavra.getPalavra() + ")"+"\n");
                    
                    PalavraLink palavraLink = new PalavraLink();
                    palavraLink.setLink(link);
                    palavraLink.setPalavra(palavra);
                    palavraLink.setQuantidade(quantidade);

                    action.createPalavraLink(palavraLink);

                }
            } else {
                listaLinksNovos.add(linkTemp);
            }

        }

        coleta.setLinkList(listaLinksNovos);
        action.createColeta(coleta);

        status.setText("...");
        txtArea.setText("...");
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
