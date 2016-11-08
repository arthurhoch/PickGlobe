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
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.JLabel;

/**
 *
 * @author arthurhoch
 */
public class Agenda {

    private static final String PASTA = "downloads";

    private final FilaExecucao modelFilaExecucao;
    private final JLabel status;
    private boolean rodando;
    private final static int SLEEP_TIME = 1;
    private final Md5helper md5;
    private final Util inutil;
    private final ActionCore action;

    public Agenda(FilaExecucao modelFilaExecucao, JLabel status) {
        this.modelFilaExecucao = modelFilaExecucao;
        this.rodando = false;
        this.status = status;
        this.md5 = new Md5helper();
        this.inutil = new Util();
        this.action = new ActionCore();
    }

    public void start() {
        Runnable runnable = () -> {
            try {
                while (rodando) {
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

            } catch (InterruptedException e) {
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void coletarPalavras(Site site) {
        status.setText(site.getUrl());

        Coleta coleta = new Coleta();
        coleta.setCodSite(site);
        Calendar calendar = Calendar.getInstance();
        java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
        coleta.setDate(date);
        java.sql.Time time = new java.sql.Time(calendar.getTime().getTime());
        coleta.setTime(time);

        List<Link> listaLinks = inutil.getLinksPage(site.getUrl(), site.getCodListaExtensoes().getExtensaoList());
        inutil.saveListLinks(listaLinks, PASTA);

        List<Palavra> palavras = site.getCodListaPalavras().getPalavraList();

        int tipo = site.getCodListaPalavras().getCodTipoLista().getCodTipoLista();

        for (Link link : listaLinks) {

            if (action.checkSiteIn(link.getMd5())) {

                status.setText(link.getUrl());

                for (Palavra palavra : palavras) {

                    int quantidade = 0;

                    System.out.println(tipo);

                    switch (tipo) {
                        case 1:
                        case 2:
                            System.out.println("Quantidade : " + quantidade);
                            quantidade = inutil.contarPalavras(link, palavra.getPalavra());
                            System.out.println("Quantidade : " + quantidade);
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

                    System.out.println("-----------------------------------------------------------------");
                    System.out.println("Tipo : " + site.getCodListaPalavras().getCodTipoLista().getTipo());
                    System.out.println("Palavar : " + palavra.getPalavra());
                    System.out.println("Link : " + link.getUrl());
                    System.out.println("Quantidade : " + quantidade);
                    System.out.println("-----------------------------------------------------------------");

                    PalavraLink palavraLink = new PalavraLink();
                    palavraLink.setLink(link);
                    palavraLink.setPalavra(palavra);
                    palavraLink.setQuantidade(quantidade);

                    action.createLink(link);
                    action.createPalavraLink(palavraLink);
                }
            }

            coleta.setLinkList(listaLinks);
            action.createColeta(coleta);

            status.setText("...");
        }
    }

    public boolean isRodando() {
        return rodando;
    }

    public void setRodando(boolean rodando) {
        this.rodando = rodando;
    }
}
