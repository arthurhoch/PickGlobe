/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.core;

import br.unisc.pickglobe.model.Site;
import br.unisc.pickglobe.view.tabelas.FilaExecucao;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JLabel;

/**
 *
 * @author arthurhoch
 */
public class Agenda {

    private final FilaExecucao modelFilaExecucao;
    private final JLabel status;
    private boolean rodando;
    private final static int SLEEP_TIME = 1;

    public Agenda(FilaExecucao modelFilaExecucao, JLabel status) {
        this.modelFilaExecucao = modelFilaExecucao;
        this.rodando = false;
        this.status = status;
        /*
        List<Site> sites = modelFilaExecucao.getLinhas();
        
        for (Site site : sites) {
            modelFilaExecucao.setTempoRestante(site.getCodSite(), 1);
        }
         */
    }

    public void start() {
        Runnable runnable = () -> {
            try {
                while (rodando) {
                    List<Site> sites = modelFilaExecucao.getLinhas();
                    TimeUnit.SECONDS.sleep(SLEEP_TIME);
                    for (Site site : sites) {
                        int key = site.getCodSite();
                        int novoTempo = modelFilaExecucao.getTempoRestante(key);
                        novoTempo -= SLEEP_TIME;
                        
                        if (novoTempo < 0) novoTempo = 0;
                        
                        
                        if (novoTempo == 0) {
                            coletarPalavras(site);
                        }
                        
                        modelFilaExecucao.setTempoRestante(key, novoTempo);
                    }
                }

            } catch (InterruptedException e) {
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }
    
    private void coletarPalavras(Site site) {
        modelFilaExecucao.setTempoRestante(site.getCodSite(), site.getIntervaloColeta());
        status.setText(site.getUrl());
        
    }

    public boolean isRodando() {
        return rodando;
    }

    public void setRodando(boolean rodando) {
        this.rodando = rodando;
    }

    public void persist(Object object) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TrabalhoPGBDPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

}
