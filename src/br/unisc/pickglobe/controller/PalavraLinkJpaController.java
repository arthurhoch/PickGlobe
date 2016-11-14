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
import br.unisc.pickglobe.controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.unisc.pickglobe.model.Link;
import br.unisc.pickglobe.model.Palavra;
import br.unisc.pickglobe.model.PalavraLink;
import br.unisc.pickglobe.model.PalavraLinkPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author arthurhoch
 */
public class PalavraLinkJpaController implements Serializable {

    public PalavraLinkJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PalavraLink palavraLink) throws PreexistingEntityException, Exception {
        if (palavraLink.getPalavraLinkPK() == null) {
            palavraLink.setPalavraLinkPK(new PalavraLinkPK());
        }
        palavraLink.getPalavraLinkPK().setCodLink(palavraLink.getLink().getCodLink());
        palavraLink.getPalavraLinkPK().setCodPalavra(palavraLink.getPalavra().getCodPalavra());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Link link = palavraLink.getLink();
            if (link != null) {
                link = em.getReference(link.getClass(), link.getCodLink());
                palavraLink.setLink(link);
            }
            Palavra palavra = palavraLink.getPalavra();
            if (palavra != null) {
                palavra = em.getReference(palavra.getClass(), palavra.getCodPalavra());
                palavraLink.setPalavra(palavra);
            }
            em.persist(palavraLink);
            if (link != null) {
                link.getPalavraLinkList().add(palavraLink);
                link = em.merge(link);
            }
            if (palavra != null) {
                palavra.getPalavraLinkList().add(palavraLink);
                palavra = em.merge(palavra);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPalavraLink(palavraLink.getPalavraLinkPK()) != null) {
                throw new PreexistingEntityException("PalavraLink " + palavraLink + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PalavraLink palavraLink) throws NonexistentEntityException, Exception {
        palavraLink.getPalavraLinkPK().setCodLink(palavraLink.getLink().getCodLink());
        palavraLink.getPalavraLinkPK().setCodPalavra(palavraLink.getPalavra().getCodPalavra());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PalavraLink persistentPalavraLink = em.find(PalavraLink.class, palavraLink.getPalavraLinkPK());
            Link linkOld = persistentPalavraLink.getLink();
            Link linkNew = palavraLink.getLink();
            Palavra palavraOld = persistentPalavraLink.getPalavra();
            Palavra palavraNew = palavraLink.getPalavra();
            if (linkNew != null) {
                linkNew = em.getReference(linkNew.getClass(), linkNew.getCodLink());
                palavraLink.setLink(linkNew);
            }
            if (palavraNew != null) {
                palavraNew = em.getReference(palavraNew.getClass(), palavraNew.getCodPalavra());
                palavraLink.setPalavra(palavraNew);
            }
            palavraLink = em.merge(palavraLink);
            if (linkOld != null && !linkOld.equals(linkNew)) {
                linkOld.getPalavraLinkList().remove(palavraLink);
                linkOld = em.merge(linkOld);
            }
            if (linkNew != null && !linkNew.equals(linkOld)) {
                linkNew.getPalavraLinkList().add(palavraLink);
                linkNew = em.merge(linkNew);
            }
            if (palavraOld != null && !palavraOld.equals(palavraNew)) {
                palavraOld.getPalavraLinkList().remove(palavraLink);
                palavraOld = em.merge(palavraOld);
            }
            if (palavraNew != null && !palavraNew.equals(palavraOld)) {
                palavraNew.getPalavraLinkList().add(palavraLink);
                palavraNew = em.merge(palavraNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PalavraLinkPK id = palavraLink.getPalavraLinkPK();
                if (findPalavraLink(id) == null) {
                    throw new NonexistentEntityException("The palavraLink with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PalavraLinkPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PalavraLink palavraLink;
            try {
                palavraLink = em.getReference(PalavraLink.class, id);
                palavraLink.getPalavraLinkPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The palavraLink with id " + id + " no longer exists.", enfe);
            }
            Link link = palavraLink.getLink();
            if (link != null) {
                link.getPalavraLinkList().remove(palavraLink);
                link = em.merge(link);
            }
            Palavra palavra = palavraLink.getPalavra();
            if (palavra != null) {
                palavra.getPalavraLinkList().remove(palavraLink);
                palavra = em.merge(palavra);
            }
            em.remove(palavraLink);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PalavraLink> findPalavraLinkEntities() {
        return findPalavraLinkEntities(true, -1, -1);
    }

    public List<PalavraLink> findPalavraLinkEntities(int maxResults, int firstResult) {
        return findPalavraLinkEntities(false, maxResults, firstResult);
    }

    private List<PalavraLink> findPalavraLinkEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PalavraLink.class));
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

    public PalavraLink findPalavraLink(PalavraLinkPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PalavraLink.class, id);
        } finally {
            em.close();
        }
    }

    public int getPalavraLinkCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PalavraLink> rt = cq.from(PalavraLink.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
