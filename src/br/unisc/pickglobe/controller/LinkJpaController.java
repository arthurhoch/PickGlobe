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

import br.unisc.pickglobe.controller.exceptions.IllegalOrphanException;
import br.unisc.pickglobe.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.unisc.pickglobe.model.Coleta;
import br.unisc.pickglobe.model.Link;
import java.util.ArrayList;
import java.util.List;
import br.unisc.pickglobe.model.PalavraLink;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author arthurhoch
 */
public class LinkJpaController implements Serializable {

    public LinkJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Link link) {
        if (link.getColetaList() == null) {
            link.setColetaList(new ArrayList<Coleta>());
        }
        if (link.getPalavraLinkList() == null) {
            link.setPalavraLinkList(new ArrayList<PalavraLink>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Coleta> attachedColetaList = new ArrayList<Coleta>();
            for (Coleta coletaListColetaToAttach : link.getColetaList()) {
                coletaListColetaToAttach = em.getReference(coletaListColetaToAttach.getClass(), coletaListColetaToAttach.getCodColeta());
                attachedColetaList.add(coletaListColetaToAttach);
            }
            link.setColetaList(attachedColetaList);
            List<PalavraLink> attachedPalavraLinkList = new ArrayList<PalavraLink>();
            for (PalavraLink palavraLinkListPalavraLinkToAttach : link.getPalavraLinkList()) {
                palavraLinkListPalavraLinkToAttach = em.getReference(palavraLinkListPalavraLinkToAttach.getClass(), palavraLinkListPalavraLinkToAttach.getPalavraLinkPK());
                attachedPalavraLinkList.add(palavraLinkListPalavraLinkToAttach);
            }
            link.setPalavraLinkList(attachedPalavraLinkList);
            em.persist(link);
            for (Coleta coletaListColeta : link.getColetaList()) {
                coletaListColeta.getLinkList().add(link);
                coletaListColeta = em.merge(coletaListColeta);
            }
            for (PalavraLink palavraLinkListPalavraLink : link.getPalavraLinkList()) {
                Link oldLinkOfPalavraLinkListPalavraLink = palavraLinkListPalavraLink.getLink();
                palavraLinkListPalavraLink.setLink(link);
                palavraLinkListPalavraLink = em.merge(palavraLinkListPalavraLink);
                if (oldLinkOfPalavraLinkListPalavraLink != null) {
                    oldLinkOfPalavraLinkListPalavraLink.getPalavraLinkList().remove(palavraLinkListPalavraLink);
                    oldLinkOfPalavraLinkListPalavraLink = em.merge(oldLinkOfPalavraLinkListPalavraLink);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Link link) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Link persistentLink = em.find(Link.class, link.getCodLink());
            List<Coleta> coletaListOld = persistentLink.getColetaList();
            List<Coleta> coletaListNew = link.getColetaList();
            List<PalavraLink> palavraLinkListOld = persistentLink.getPalavraLinkList();
            List<PalavraLink> palavraLinkListNew = link.getPalavraLinkList();
            List<String> illegalOrphanMessages = null;
            for (PalavraLink palavraLinkListOldPalavraLink : palavraLinkListOld) {
                if (!palavraLinkListNew.contains(palavraLinkListOldPalavraLink)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PalavraLink " + palavraLinkListOldPalavraLink + " since its link field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Coleta> attachedColetaListNew = new ArrayList<Coleta>();
            for (Coleta coletaListNewColetaToAttach : coletaListNew) {
                coletaListNewColetaToAttach = em.getReference(coletaListNewColetaToAttach.getClass(), coletaListNewColetaToAttach.getCodColeta());
                attachedColetaListNew.add(coletaListNewColetaToAttach);
            }
            coletaListNew = attachedColetaListNew;
            link.setColetaList(coletaListNew);
            List<PalavraLink> attachedPalavraLinkListNew = new ArrayList<PalavraLink>();
            for (PalavraLink palavraLinkListNewPalavraLinkToAttach : palavraLinkListNew) {
                palavraLinkListNewPalavraLinkToAttach = em.getReference(palavraLinkListNewPalavraLinkToAttach.getClass(), palavraLinkListNewPalavraLinkToAttach.getPalavraLinkPK());
                attachedPalavraLinkListNew.add(palavraLinkListNewPalavraLinkToAttach);
            }
            palavraLinkListNew = attachedPalavraLinkListNew;
            link.setPalavraLinkList(palavraLinkListNew);
            link = em.merge(link);
            for (Coleta coletaListOldColeta : coletaListOld) {
                if (!coletaListNew.contains(coletaListOldColeta)) {
                    coletaListOldColeta.getLinkList().remove(link);
                    coletaListOldColeta = em.merge(coletaListOldColeta);
                }
            }
            for (Coleta coletaListNewColeta : coletaListNew) {
                if (!coletaListOld.contains(coletaListNewColeta)) {
                    coletaListNewColeta.getLinkList().add(link);
                    coletaListNewColeta = em.merge(coletaListNewColeta);
                }
            }
            for (PalavraLink palavraLinkListNewPalavraLink : palavraLinkListNew) {
                if (!palavraLinkListOld.contains(palavraLinkListNewPalavraLink)) {
                    Link oldLinkOfPalavraLinkListNewPalavraLink = palavraLinkListNewPalavraLink.getLink();
                    palavraLinkListNewPalavraLink.setLink(link);
                    palavraLinkListNewPalavraLink = em.merge(palavraLinkListNewPalavraLink);
                    if (oldLinkOfPalavraLinkListNewPalavraLink != null && !oldLinkOfPalavraLinkListNewPalavraLink.equals(link)) {
                        oldLinkOfPalavraLinkListNewPalavraLink.getPalavraLinkList().remove(palavraLinkListNewPalavraLink);
                        oldLinkOfPalavraLinkListNewPalavraLink = em.merge(oldLinkOfPalavraLinkListNewPalavraLink);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = link.getCodLink();
                if (findLink(id) == null) {
                    throw new NonexistentEntityException("The link with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Link link;
            try {
                link = em.getReference(Link.class, id);
                link.getCodLink();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The link with id " + id + " no longer exists.", enfe);
            }
            /*
            List<String> illegalOrphanMessages = null;
            List<PalavraLink> palavraLinkListOrphanCheck = link.getPalavraLinkList();
            for (PalavraLink palavraLinkListOrphanCheckPalavraLink : palavraLinkListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Link (" + link + ") cannot be destroyed since the PalavraLink " + palavraLinkListOrphanCheckPalavraLink + " in its palavraLinkList field has a non-nullable link field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
             */
            List<Coleta> coletaList = link.getColetaList();
            for (Coleta coletaListColeta : coletaList) {
                coletaListColeta.getLinkList().remove(link);
                coletaListColeta = em.merge(coletaListColeta);
            }
            em.remove(link);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Link> findLinkEntities() {
        return findLinkEntities(true, -1, -1);
    }

    public List<Link> findLinkEntities(int maxResults, int firstResult) {
        return findLinkEntities(false, maxResults, firstResult);
    }

    private List<Link> findLinkEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Link.class));
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

    public Link findLink(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Link.class, id);
        } finally {
            em.close();
        }
    }

    public int getLinkCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Link> rt = cq.from(Link.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
