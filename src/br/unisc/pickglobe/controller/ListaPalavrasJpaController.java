/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.controller;

import br.unisc.pickglobe.controller.exceptions.NonexistentEntityException;
import br.unisc.pickglobe.model.ListaPalavras;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.unisc.pickglobe.model.Palavra;
import java.util.ArrayList;
import java.util.List;
import br.unisc.pickglobe.model.Site;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author arthurhoch
 */
public class ListaPalavrasJpaController implements Serializable {

    public ListaPalavrasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ListaPalavras listaPalavras) {
        if (listaPalavras.getPalavraList() == null) {
            listaPalavras.setPalavraList(new ArrayList<Palavra>());
        }
        if (listaPalavras.getSiteList() == null) {
            listaPalavras.setSiteList(new ArrayList<Site>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Palavra> attachedPalavraList = new ArrayList<Palavra>();
            for (Palavra palavraListPalavraToAttach : listaPalavras.getPalavraList()) {
                palavraListPalavraToAttach = em.getReference(palavraListPalavraToAttach.getClass(), palavraListPalavraToAttach.getCodPalavra());
                attachedPalavraList.add(palavraListPalavraToAttach);
            }
            listaPalavras.setPalavraList(attachedPalavraList);
            List<Site> attachedSiteList = new ArrayList<Site>();
            for (Site siteListSiteToAttach : listaPalavras.getSiteList()) {
                siteListSiteToAttach = em.getReference(siteListSiteToAttach.getClass(), siteListSiteToAttach.getCodSite());
                attachedSiteList.add(siteListSiteToAttach);
            }
            listaPalavras.setSiteList(attachedSiteList);
            em.persist(listaPalavras);
            for (Palavra palavraListPalavra : listaPalavras.getPalavraList()) {
                palavraListPalavra.getListaPalavrasList().add(listaPalavras);
                palavraListPalavra = em.merge(palavraListPalavra);
            }
            for (Site siteListSite : listaPalavras.getSiteList()) {
                ListaPalavras oldCodListaPalavrasOfSiteListSite = siteListSite.getCodListaPalavras();
                siteListSite.setCodListaPalavras(listaPalavras);
                siteListSite = em.merge(siteListSite);
                if (oldCodListaPalavrasOfSiteListSite != null) {
                    oldCodListaPalavrasOfSiteListSite.getSiteList().remove(siteListSite);
                    oldCodListaPalavrasOfSiteListSite = em.merge(oldCodListaPalavrasOfSiteListSite);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ListaPalavras listaPalavras) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ListaPalavras persistentListaPalavras = em.find(ListaPalavras.class, listaPalavras.getCodListaPalavras());
            List<Palavra> palavraListOld = persistentListaPalavras.getPalavraList();
            List<Palavra> palavraListNew = listaPalavras.getPalavraList();
            List<Site> siteListOld = persistentListaPalavras.getSiteList();
            List<Site> siteListNew = listaPalavras.getSiteList();
            List<Palavra> attachedPalavraListNew = new ArrayList<Palavra>();
            for (Palavra palavraListNewPalavraToAttach : palavraListNew) {
                palavraListNewPalavraToAttach = em.getReference(palavraListNewPalavraToAttach.getClass(), palavraListNewPalavraToAttach.getCodPalavra());
                attachedPalavraListNew.add(palavraListNewPalavraToAttach);
            }
            palavraListNew = attachedPalavraListNew;
            listaPalavras.setPalavraList(palavraListNew);
            List<Site> attachedSiteListNew = new ArrayList<Site>();
            for (Site siteListNewSiteToAttach : siteListNew) {
                siteListNewSiteToAttach = em.getReference(siteListNewSiteToAttach.getClass(), siteListNewSiteToAttach.getCodSite());
                attachedSiteListNew.add(siteListNewSiteToAttach);
            }
            siteListNew = attachedSiteListNew;
            listaPalavras.setSiteList(siteListNew);
            listaPalavras = em.merge(listaPalavras);
            for (Palavra palavraListOldPalavra : palavraListOld) {
                if (!palavraListNew.contains(palavraListOldPalavra)) {
                    palavraListOldPalavra.getListaPalavrasList().remove(listaPalavras);
                    palavraListOldPalavra = em.merge(palavraListOldPalavra);
                }
            }
            for (Palavra palavraListNewPalavra : palavraListNew) {
                if (!palavraListOld.contains(palavraListNewPalavra)) {
                    palavraListNewPalavra.getListaPalavrasList().add(listaPalavras);
                    palavraListNewPalavra = em.merge(palavraListNewPalavra);
                }
            }
            for (Site siteListOldSite : siteListOld) {
                if (!siteListNew.contains(siteListOldSite)) {
                    siteListOldSite.setCodListaPalavras(null);
                    siteListOldSite = em.merge(siteListOldSite);
                }
            }
            for (Site siteListNewSite : siteListNew) {
                if (!siteListOld.contains(siteListNewSite)) {
                    ListaPalavras oldCodListaPalavrasOfSiteListNewSite = siteListNewSite.getCodListaPalavras();
                    siteListNewSite.setCodListaPalavras(listaPalavras);
                    siteListNewSite = em.merge(siteListNewSite);
                    if (oldCodListaPalavrasOfSiteListNewSite != null && !oldCodListaPalavrasOfSiteListNewSite.equals(listaPalavras)) {
                        oldCodListaPalavrasOfSiteListNewSite.getSiteList().remove(siteListNewSite);
                        oldCodListaPalavrasOfSiteListNewSite = em.merge(oldCodListaPalavrasOfSiteListNewSite);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = listaPalavras.getCodListaPalavras();
                if (findListaPalavras(id) == null) {
                    throw new NonexistentEntityException("The listaPalavras with id " + id + " no longer exists.");
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
            ListaPalavras listaPalavras;
            try {
                listaPalavras = em.getReference(ListaPalavras.class, id);
                listaPalavras.getCodListaPalavras();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The listaPalavras with id " + id + " no longer exists.", enfe);
            }
            List<Palavra> palavraList = listaPalavras.getPalavraList();
            for (Palavra palavraListPalavra : palavraList) {
                palavraListPalavra.getListaPalavrasList().remove(listaPalavras);
                palavraListPalavra = em.merge(palavraListPalavra);
            }
            List<Site> siteList = listaPalavras.getSiteList();
            for (Site siteListSite : siteList) {
                siteListSite.setCodListaPalavras(null);
                siteListSite = em.merge(siteListSite);
            }
            em.remove(listaPalavras);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ListaPalavras> findListaPalavrasEntities() {
        return findListaPalavrasEntities(true, -1, -1);
    }

    public List<ListaPalavras> findListaPalavrasEntities(int maxResults, int firstResult) {
        return findListaPalavrasEntities(false, maxResults, firstResult);
    }

    private List<ListaPalavras> findListaPalavrasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ListaPalavras.class));
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

    public ListaPalavras findListaPalavras(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ListaPalavras.class, id);
        } finally {
            em.close();
        }
    }

    public int getListaPalavrasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ListaPalavras> rt = cq.from(ListaPalavras.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
