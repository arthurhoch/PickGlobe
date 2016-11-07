/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.controller;

import br.unisc.pickglobe.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.unisc.pickglobe.model.Extensao;
import br.unisc.pickglobe.model.ListaExtensoes;
import java.util.ArrayList;
import java.util.List;
import br.unisc.pickglobe.model.Site;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author arthurhoch
 */
public class ListaExtensoesJpaController implements Serializable {

    public ListaExtensoesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ListaExtensoes listaExtensoes) {
        if (listaExtensoes.getExtensaoList() == null) {
            listaExtensoes.setExtensaoList(new ArrayList<Extensao>());
        }
        if (listaExtensoes.getSiteList() == null) {
            listaExtensoes.setSiteList(new ArrayList<Site>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Extensao> attachedExtensaoList = new ArrayList<Extensao>();
            for (Extensao extensaoListExtensaoToAttach : listaExtensoes.getExtensaoList()) {
                extensaoListExtensaoToAttach = em.getReference(extensaoListExtensaoToAttach.getClass(), extensaoListExtensaoToAttach.getCodExtensao());
                attachedExtensaoList.add(extensaoListExtensaoToAttach);
            }
            listaExtensoes.setExtensaoList(attachedExtensaoList);
            List<Site> attachedSiteList = new ArrayList<Site>();
            for (Site siteListSiteToAttach : listaExtensoes.getSiteList()) {
                siteListSiteToAttach = em.getReference(siteListSiteToAttach.getClass(), siteListSiteToAttach.getCodSite());
                attachedSiteList.add(siteListSiteToAttach);
            }
            listaExtensoes.setSiteList(attachedSiteList);
            em.persist(listaExtensoes);
            for (Extensao extensaoListExtensao : listaExtensoes.getExtensaoList()) {
                extensaoListExtensao.getListaExtensoesList().add(listaExtensoes);
                extensaoListExtensao = em.merge(extensaoListExtensao);
            }
            for (Site siteListSite : listaExtensoes.getSiteList()) {
                ListaExtensoes oldCodListaExtensoesOfSiteListSite = siteListSite.getCodListaExtensoes();
                siteListSite.setCodListaExtensoes(listaExtensoes);
                siteListSite = em.merge(siteListSite);
                if (oldCodListaExtensoesOfSiteListSite != null) {
                    oldCodListaExtensoesOfSiteListSite.getSiteList().remove(siteListSite);
                    oldCodListaExtensoesOfSiteListSite = em.merge(oldCodListaExtensoesOfSiteListSite);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ListaExtensoes listaExtensoes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ListaExtensoes persistentListaExtensoes = em.find(ListaExtensoes.class, listaExtensoes.getCodListaExtensoes());
            List<Extensao> extensaoListOld = persistentListaExtensoes.getExtensaoList();
            List<Extensao> extensaoListNew = listaExtensoes.getExtensaoList();
            List<Site> siteListOld = persistentListaExtensoes.getSiteList();
            List<Site> siteListNew = listaExtensoes.getSiteList();
            List<Extensao> attachedExtensaoListNew = new ArrayList<Extensao>();
            for (Extensao extensaoListNewExtensaoToAttach : extensaoListNew) {
                extensaoListNewExtensaoToAttach = em.getReference(extensaoListNewExtensaoToAttach.getClass(), extensaoListNewExtensaoToAttach.getCodExtensao());
                attachedExtensaoListNew.add(extensaoListNewExtensaoToAttach);
            }
            extensaoListNew = attachedExtensaoListNew;
            listaExtensoes.setExtensaoList(extensaoListNew);
            List<Site> attachedSiteListNew = new ArrayList<Site>();
            for (Site siteListNewSiteToAttach : siteListNew) {
                siteListNewSiteToAttach = em.getReference(siteListNewSiteToAttach.getClass(), siteListNewSiteToAttach.getCodSite());
                attachedSiteListNew.add(siteListNewSiteToAttach);
            }
            siteListNew = attachedSiteListNew;
            listaExtensoes.setSiteList(siteListNew);
            listaExtensoes = em.merge(listaExtensoes);
            for (Extensao extensaoListOldExtensao : extensaoListOld) {
                if (!extensaoListNew.contains(extensaoListOldExtensao)) {
                    extensaoListOldExtensao.getListaExtensoesList().remove(listaExtensoes);
                    extensaoListOldExtensao = em.merge(extensaoListOldExtensao);
                }
            }
            for (Extensao extensaoListNewExtensao : extensaoListNew) {
                if (!extensaoListOld.contains(extensaoListNewExtensao)) {
                    extensaoListNewExtensao.getListaExtensoesList().add(listaExtensoes);
                    extensaoListNewExtensao = em.merge(extensaoListNewExtensao);
                }
            }
            for (Site siteListOldSite : siteListOld) {
                if (!siteListNew.contains(siteListOldSite)) {
                    siteListOldSite.setCodListaExtensoes(null);
                    siteListOldSite = em.merge(siteListOldSite);
                }
            }
            for (Site siteListNewSite : siteListNew) {
                if (!siteListOld.contains(siteListNewSite)) {
                    ListaExtensoes oldCodListaExtensoesOfSiteListNewSite = siteListNewSite.getCodListaExtensoes();
                    siteListNewSite.setCodListaExtensoes(listaExtensoes);
                    siteListNewSite = em.merge(siteListNewSite);
                    if (oldCodListaExtensoesOfSiteListNewSite != null && !oldCodListaExtensoesOfSiteListNewSite.equals(listaExtensoes)) {
                        oldCodListaExtensoesOfSiteListNewSite.getSiteList().remove(siteListNewSite);
                        oldCodListaExtensoesOfSiteListNewSite = em.merge(oldCodListaExtensoesOfSiteListNewSite);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = listaExtensoes.getCodListaExtensoes();
                if (findListaExtensoes(id) == null) {
                    throw new NonexistentEntityException("The listaExtensoes with id " + id + " no longer exists.");
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
            ListaExtensoes listaExtensoes;
            try {
                listaExtensoes = em.getReference(ListaExtensoes.class, id);
                listaExtensoes.getCodListaExtensoes();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The listaExtensoes with id " + id + " no longer exists.", enfe);
            }
            List<Extensao> extensaoList = listaExtensoes.getExtensaoList();
            for (Extensao extensaoListExtensao : extensaoList) {
                extensaoListExtensao.getListaExtensoesList().remove(listaExtensoes);
                extensaoListExtensao = em.merge(extensaoListExtensao);
            }
            List<Site> siteList = listaExtensoes.getSiteList();
            for (Site siteListSite : siteList) {
                siteListSite.setCodListaExtensoes(null);
                siteListSite = em.merge(siteListSite);
            }
            em.remove(listaExtensoes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ListaExtensoes> findListaExtensoesEntities() {
        return findListaExtensoesEntities(true, -1, -1);
    }

    public List<ListaExtensoes> findListaExtensoesEntities(int maxResults, int firstResult) {
        return findListaExtensoesEntities(false, maxResults, firstResult);
    }

    private List<ListaExtensoes> findListaExtensoesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ListaExtensoes.class));
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

    public ListaExtensoes findListaExtensoes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ListaExtensoes.class, id);
        } finally {
            em.close();
        }
    }

    public int getListaExtensoesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ListaExtensoes> rt = cq.from(ListaExtensoes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
