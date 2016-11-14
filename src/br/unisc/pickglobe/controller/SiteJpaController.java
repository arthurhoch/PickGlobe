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
package br.unisc.pickglobe.controller;

import br.unisc.pickglobe.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.unisc.pickglobe.model.ListaExtensoes;
import br.unisc.pickglobe.model.ListaPalavras;
import br.unisc.pickglobe.model.Coleta;
import br.unisc.pickglobe.model.Site;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author arthurhoch
 */
public class SiteJpaController implements Serializable {

    public SiteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Site site) {
        if (site.getColetaList() == null) {
            site.setColetaList(new ArrayList<Coleta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ListaExtensoes codListaExtensoes = site.getCodListaExtensoes();
            if (codListaExtensoes != null) {
                codListaExtensoes = em.getReference(codListaExtensoes.getClass(), codListaExtensoes.getCodListaExtensoes());
                site.setCodListaExtensoes(codListaExtensoes);
            }
            ListaPalavras codListaPalavras = site.getCodListaPalavras();
            if (codListaPalavras != null) {
                codListaPalavras = em.getReference(codListaPalavras.getClass(), codListaPalavras.getCodListaPalavras());
                site.setCodListaPalavras(codListaPalavras);
            }
            List<Coleta> attachedColetaList = new ArrayList<Coleta>();
            for (Coleta coletaListColetaToAttach : site.getColetaList()) {
                coletaListColetaToAttach = em.getReference(coletaListColetaToAttach.getClass(), coletaListColetaToAttach.getCodColeta());
                attachedColetaList.add(coletaListColetaToAttach);
            }
            site.setColetaList(attachedColetaList);
            em.persist(site);
            if (codListaExtensoes != null) {
                codListaExtensoes.getSiteList().add(site);
                codListaExtensoes = em.merge(codListaExtensoes);
            }
            if (codListaPalavras != null) {
                codListaPalavras.getSiteList().add(site);
                codListaPalavras = em.merge(codListaPalavras);
            }
            for (Coleta coletaListColeta : site.getColetaList()) {
                Site oldCodSiteOfColetaListColeta = coletaListColeta.getCodSite();
                coletaListColeta.setCodSite(site);
                coletaListColeta = em.merge(coletaListColeta);
                if (oldCodSiteOfColetaListColeta != null) {
                    oldCodSiteOfColetaListColeta.getColetaList().remove(coletaListColeta);
                    oldCodSiteOfColetaListColeta = em.merge(oldCodSiteOfColetaListColeta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Site site) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Site persistentSite = em.find(Site.class, site.getCodSite());
            ListaExtensoes codListaExtensoesOld = persistentSite.getCodListaExtensoes();
            ListaExtensoes codListaExtensoesNew = site.getCodListaExtensoes();
            ListaPalavras codListaPalavrasOld = persistentSite.getCodListaPalavras();
            ListaPalavras codListaPalavrasNew = site.getCodListaPalavras();
            List<Coleta> coletaListOld = persistentSite.getColetaList();
            List<Coleta> coletaListNew = site.getColetaList();
            if (codListaExtensoesNew != null) {
                codListaExtensoesNew = em.getReference(codListaExtensoesNew.getClass(), codListaExtensoesNew.getCodListaExtensoes());
                site.setCodListaExtensoes(codListaExtensoesNew);
            }
            if (codListaPalavrasNew != null) {
                codListaPalavrasNew = em.getReference(codListaPalavrasNew.getClass(), codListaPalavrasNew.getCodListaPalavras());
                site.setCodListaPalavras(codListaPalavrasNew);
            }
            List<Coleta> attachedColetaListNew = new ArrayList<Coleta>();
            for (Coleta coletaListNewColetaToAttach : coletaListNew) {
                coletaListNewColetaToAttach = em.getReference(coletaListNewColetaToAttach.getClass(), coletaListNewColetaToAttach.getCodColeta());
                attachedColetaListNew.add(coletaListNewColetaToAttach);
            }
            coletaListNew = attachedColetaListNew;
            site.setColetaList(coletaListNew);
            site = em.merge(site);
            if (codListaExtensoesOld != null && !codListaExtensoesOld.equals(codListaExtensoesNew)) {
                codListaExtensoesOld.getSiteList().remove(site);
                codListaExtensoesOld = em.merge(codListaExtensoesOld);
            }
            if (codListaExtensoesNew != null && !codListaExtensoesNew.equals(codListaExtensoesOld)) {
                codListaExtensoesNew.getSiteList().add(site);
                codListaExtensoesNew = em.merge(codListaExtensoesNew);
            }
            if (codListaPalavrasOld != null && !codListaPalavrasOld.equals(codListaPalavrasNew)) {
                codListaPalavrasOld.getSiteList().remove(site);
                codListaPalavrasOld = em.merge(codListaPalavrasOld);
            }
            if (codListaPalavrasNew != null && !codListaPalavrasNew.equals(codListaPalavrasOld)) {
                codListaPalavrasNew.getSiteList().add(site);
                codListaPalavrasNew = em.merge(codListaPalavrasNew);
            }
            for (Coleta coletaListOldColeta : coletaListOld) {
                if (!coletaListNew.contains(coletaListOldColeta)) {
                    coletaListOldColeta.setCodSite(null);
                    coletaListOldColeta = em.merge(coletaListOldColeta);
                }
            }
            for (Coleta coletaListNewColeta : coletaListNew) {
                if (!coletaListOld.contains(coletaListNewColeta)) {
                    Site oldCodSiteOfColetaListNewColeta = coletaListNewColeta.getCodSite();
                    coletaListNewColeta.setCodSite(site);
                    coletaListNewColeta = em.merge(coletaListNewColeta);
                    if (oldCodSiteOfColetaListNewColeta != null && !oldCodSiteOfColetaListNewColeta.equals(site)) {
                        oldCodSiteOfColetaListNewColeta.getColetaList().remove(coletaListNewColeta);
                        oldCodSiteOfColetaListNewColeta = em.merge(oldCodSiteOfColetaListNewColeta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = site.getCodSite();
                if (findSite(id) == null) {
                    throw new NonexistentEntityException("The site with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Site site;
            try {
                site = em.getReference(Site.class, id);
                site.getCodSite();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The site with id " + id + " no longer exists.", enfe);
            }
            ListaExtensoes codListaExtensoes = site.getCodListaExtensoes();
            if (codListaExtensoes != null) {
                codListaExtensoes.getSiteList().remove(site);
                codListaExtensoes = em.merge(codListaExtensoes);
            }
            ListaPalavras codListaPalavras = site.getCodListaPalavras();
            if (codListaPalavras != null) {
                codListaPalavras.getSiteList().remove(site);
                codListaPalavras = em.merge(codListaPalavras);
            }
            List<Coleta> coletaList = site.getColetaList();
            for (Coleta coletaListColeta : coletaList) {
                coletaListColeta.setCodSite(null);
                coletaListColeta = em.merge(coletaListColeta);
            }
            em.remove(site);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Site> findSiteEntities() {
        return findSiteEntities(true, -1, -1);
    }

    public List<Site> findSiteEntities(int maxResults, int firstResult) {
        return findSiteEntities(false, maxResults, firstResult);
    }

    private List<Site> findSiteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Site.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Site findSite(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Site.class, id);
        } finally {
            em.close();
        }
    }

    public int getSiteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Site> rt = cq.from(Site.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
