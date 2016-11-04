package br.unisc.pickglobe.tests;

import br.unisc.pickglobe.controller.ExtensaoJpaController;
import br.unisc.pickglobe.controller.SiteJpaController;
import br.unisc.pickglobe.model.Extensao;
import br.unisc.pickglobe.model.Site;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;




/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author arthurhoch
 */
public class BaseDeDados {

    public static void main(String[] args) throws Exception {
        testarBase();
    }

    public static void testarBase() throws Exception {

        EntityManagerFactory emf= Persistence.createEntityManagerFactory("TrabalhoPGBDPU");
        ExtensaoJpaController extensaoJpa = new ExtensaoJpaController(emf);
        
        Extensao extensao = new Extensao();
        
        extensao.setNomeExtensao("teste");
        
        extensaoJpa.create(extensao);
        
        SiteJpaController siteJpa = new SiteJpaController(emf);
        
        Site site = new Site();
        
        site.setUrl("www.google.com.br");
        site.setIntervaloColeta(1000);
        site.setStatus(true);
        
        siteJpa.create(site);
        
    }

}
