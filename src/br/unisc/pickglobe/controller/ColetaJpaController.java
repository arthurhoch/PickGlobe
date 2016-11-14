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
import br.unisc.pickglobe.model.Coleta;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.unisc.pickglobe.model.Site;
import br.unisc.pickglobe.model.Link;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author arthurhoch
 */
public class ColetaJpaController implements Serializable {

    public ColetaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Coleta coleta) {
        if (coleta.getLinkList() == null) {
            coleta.setLinkList(new ArrayList<Link>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Site codSite = coleta.getCodSite();
            if (codSite != null) {
                codSite = em.getReference(codSite.getClass(), codSite.getCodSite());
                coleta.setCodSite(codSite);
            }
            List<Link> attachedLinkList = new ArrayList<Link>();
            for (Link linkListLinkToAttach : coleta.getLinkList()) {
                linkListLinkToAttach = em.getReference(linkListLinkToAttach.getClass(), linkListLinkToAttach.getCodLink());
                attachedLinkList.add(linkListLinkToAttach);
            }
            coleta.setLinkList(attachedLinkList);
            em.persist(coleta);
            if (codSite != null) {
                codSite.getColetaList().add(coleta);
                codSite = em.merge(codSite);
            }
            for (Link linkListLink : coleta.getLinkList()) {
                linkListLink.getColetaList().add(coleta);
                linkListLink = em.merge(linkListLink);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Coleta coleta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Coleta persistentColeta = em.find(Coleta.class, coleta.getCodColeta());
            Site codSiteOld = persistentColeta.getCodSite();
            Site codSiteNew = coleta.getCodSite();
            List<Link> linkListOld = persistentColeta.getLinkList();
            List<Link> linkListNew = coleta.getLinkList();
            if (codSiteNew != null) {
                codSiteNew = em.getReference(codSiteNew.getClass(), codSiteNew.getCodSite());
                coleta.setCodSite(codSiteNew);
            }
            List<Link> attachedLinkListNew = new ArrayList<Link>();
            for (Link linkListNewLinkToAttach : linkListNew) {
                linkListNewLinkToAttach = em.getReference(linkListNewLinkToAttach.getClass(), linkListNewLinkToAttach.getCodLink());
                attachedLinkListNew.add(linkListNewLinkToAttach);
            }
            linkListNew = attachedLinkListNew;
            coleta.setLinkList(linkListNew);
            coleta = em.merge(coleta);
            if (codSiteOld != null && !codSiteOld.equals(codSiteNew)) {
                codSiteOld.getColetaList().remove(coleta);
                codSiteOld = em.merge(codSiteOld);
            }
            if (codSiteNew != null && !codSiteNew.equals(codSiteOld)) {
                codSiteNew.getColetaList().add(coleta);
                codSiteNew = em.merge(codSiteNew);
            }
            for (Link linkListOldLink : linkListOld) {
                if (!linkListNew.contains(linkListOldLink)) {
                    linkListOldLink.getColetaList().remove(coleta);
                    linkListOldLink = em.merge(linkListOldLink);
                }
            }
            for (Link linkListNewLink : linkListNew) {
                if (!linkListOld.contains(linkListNewLink)) {
                    linkListNewLink.getColetaList().add(coleta);
                    linkListNewLink = em.merge(linkListNewLink);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = coleta.getCodColeta();
                if (findColeta(id) == null) {
                    throw new NonexistentEntityException("The coleta with id " + id + " no longer exists.");
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
            Coleta coleta;
            try {
                coleta = em.getReference(Coleta.class, id);
                coleta.getCodColeta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The coleta with id " + id + " no longer exists.", enfe);
            }
            Site codSite = coleta.getCodSite();
            if (codSite != null) {
                codSite.getColetaList().remove(coleta);
                codSite = em.merge(codSite);
            }
            List<Link> linkList = coleta.getLinkList();
            for (Link linkListLink : linkList) {
                linkListLink.getColetaList().remove(coleta);
                linkListLink = em.merge(linkListLink);
            }
            em.remove(coleta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Coleta> findColetaEntities() {
        return findColetaEntities(true, -1, -1);
    }

    public List<Coleta> findColetaEntities(int maxResults, int firstResult) {
        return findColetaEntities(false, maxResults, firstResult);
    }

    private List<Coleta> findColetaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Coleta.class));
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

    public Coleta findColeta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Coleta.class, id);
        } finally {
            em.close();
        }
    }

    public int getColetaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Coleta> rt = cq.from(Coleta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
