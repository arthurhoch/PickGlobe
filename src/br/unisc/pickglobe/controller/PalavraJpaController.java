/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.controller;

import br.unisc.pickglobe.controller.exceptions.IllegalOrphanException;
import br.unisc.pickglobe.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.unisc.pickglobe.model.ListaPalavras;
import br.unisc.pickglobe.model.Palavra;
import java.util.ArrayList;
import java.util.List;
import br.unisc.pickglobe.model.PalavraLink;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author arthurhoch
 */
public class PalavraJpaController implements Serializable {

    public PalavraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Palavra palavra) {
        if (palavra.getListaPalavrasList() == null) {
            palavra.setListaPalavrasList(new ArrayList<ListaPalavras>());
        }
        if (palavra.getPalavraLinkList() == null) {
            palavra.setPalavraLinkList(new ArrayList<PalavraLink>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ListaPalavras> attachedListaPalavrasList = new ArrayList<ListaPalavras>();
            for (ListaPalavras listaPalavrasListListaPalavrasToAttach : palavra.getListaPalavrasList()) {
                listaPalavrasListListaPalavrasToAttach = em.getReference(listaPalavrasListListaPalavrasToAttach.getClass(), listaPalavrasListListaPalavrasToAttach.getCodListaPalavras());
                attachedListaPalavrasList.add(listaPalavrasListListaPalavrasToAttach);
            }
            palavra.setListaPalavrasList(attachedListaPalavrasList);
            List<PalavraLink> attachedPalavraLinkList = new ArrayList<PalavraLink>();
            for (PalavraLink palavraLinkListPalavraLinkToAttach : palavra.getPalavraLinkList()) {
                palavraLinkListPalavraLinkToAttach = em.getReference(palavraLinkListPalavraLinkToAttach.getClass(), palavraLinkListPalavraLinkToAttach.getPalavraLinkPK());
                attachedPalavraLinkList.add(palavraLinkListPalavraLinkToAttach);
            }
            palavra.setPalavraLinkList(attachedPalavraLinkList);
            em.persist(palavra);
            for (ListaPalavras listaPalavrasListListaPalavras : palavra.getListaPalavrasList()) {
                listaPalavrasListListaPalavras.getPalavraList().add(palavra);
                listaPalavrasListListaPalavras = em.merge(listaPalavrasListListaPalavras);
            }
            for (PalavraLink palavraLinkListPalavraLink : palavra.getPalavraLinkList()) {
                Palavra oldPalavraOfPalavraLinkListPalavraLink = palavraLinkListPalavraLink.getPalavra();
                palavraLinkListPalavraLink.setPalavra(palavra);
                palavraLinkListPalavraLink = em.merge(palavraLinkListPalavraLink);
                if (oldPalavraOfPalavraLinkListPalavraLink != null) {
                    oldPalavraOfPalavraLinkListPalavraLink.getPalavraLinkList().remove(palavraLinkListPalavraLink);
                    oldPalavraOfPalavraLinkListPalavraLink = em.merge(oldPalavraOfPalavraLinkListPalavraLink);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Palavra palavra) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Palavra persistentPalavra = em.find(Palavra.class, palavra.getCodPalavra());
            List<ListaPalavras> listaPalavrasListOld = persistentPalavra.getListaPalavrasList();
            List<ListaPalavras> listaPalavrasListNew = palavra.getListaPalavrasList();
            List<PalavraLink> palavraLinkListOld = persistentPalavra.getPalavraLinkList();
            List<PalavraLink> palavraLinkListNew = palavra.getPalavraLinkList();
            List<String> illegalOrphanMessages = null;
            for (PalavraLink palavraLinkListOldPalavraLink : palavraLinkListOld) {
                if (!palavraLinkListNew.contains(palavraLinkListOldPalavraLink)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PalavraLink " + palavraLinkListOldPalavraLink + " since its palavra field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<ListaPalavras> attachedListaPalavrasListNew = new ArrayList<ListaPalavras>();
            for (ListaPalavras listaPalavrasListNewListaPalavrasToAttach : listaPalavrasListNew) {
                listaPalavrasListNewListaPalavrasToAttach = em.getReference(listaPalavrasListNewListaPalavrasToAttach.getClass(), listaPalavrasListNewListaPalavrasToAttach.getCodListaPalavras());
                attachedListaPalavrasListNew.add(listaPalavrasListNewListaPalavrasToAttach);
            }
            listaPalavrasListNew = attachedListaPalavrasListNew;
            palavra.setListaPalavrasList(listaPalavrasListNew);
            List<PalavraLink> attachedPalavraLinkListNew = new ArrayList<PalavraLink>();
            for (PalavraLink palavraLinkListNewPalavraLinkToAttach : palavraLinkListNew) {
                palavraLinkListNewPalavraLinkToAttach = em.getReference(palavraLinkListNewPalavraLinkToAttach.getClass(), palavraLinkListNewPalavraLinkToAttach.getPalavraLinkPK());
                attachedPalavraLinkListNew.add(palavraLinkListNewPalavraLinkToAttach);
            }
            palavraLinkListNew = attachedPalavraLinkListNew;
            palavra.setPalavraLinkList(palavraLinkListNew);
            palavra = em.merge(palavra);
            for (ListaPalavras listaPalavrasListOldListaPalavras : listaPalavrasListOld) {
                if (!listaPalavrasListNew.contains(listaPalavrasListOldListaPalavras)) {
                    listaPalavrasListOldListaPalavras.getPalavraList().remove(palavra);
                    listaPalavrasListOldListaPalavras = em.merge(listaPalavrasListOldListaPalavras);
                }
            }
            for (ListaPalavras listaPalavrasListNewListaPalavras : listaPalavrasListNew) {
                if (!listaPalavrasListOld.contains(listaPalavrasListNewListaPalavras)) {
                    listaPalavrasListNewListaPalavras.getPalavraList().add(palavra);
                    listaPalavrasListNewListaPalavras = em.merge(listaPalavrasListNewListaPalavras);
                }
            }
            for (PalavraLink palavraLinkListNewPalavraLink : palavraLinkListNew) {
                if (!palavraLinkListOld.contains(palavraLinkListNewPalavraLink)) {
                    Palavra oldPalavraOfPalavraLinkListNewPalavraLink = palavraLinkListNewPalavraLink.getPalavra();
                    palavraLinkListNewPalavraLink.setPalavra(palavra);
                    palavraLinkListNewPalavraLink = em.merge(palavraLinkListNewPalavraLink);
                    if (oldPalavraOfPalavraLinkListNewPalavraLink != null && !oldPalavraOfPalavraLinkListNewPalavraLink.equals(palavra)) {
                        oldPalavraOfPalavraLinkListNewPalavraLink.getPalavraLinkList().remove(palavraLinkListNewPalavraLink);
                        oldPalavraOfPalavraLinkListNewPalavraLink = em.merge(oldPalavraOfPalavraLinkListNewPalavraLink);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = palavra.getCodPalavra();
                if (findPalavra(id) == null) {
                    throw new NonexistentEntityException("The palavra with id " + id + " no longer exists.");
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
            Palavra palavra;
            try {
                palavra = em.getReference(Palavra.class, id);
                palavra.getCodPalavra();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The palavra with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<PalavraLink> palavraLinkListOrphanCheck = palavra.getPalavraLinkList();
            for (PalavraLink palavraLinkListOrphanCheckPalavraLink : palavraLinkListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Palavra (" + palavra + ") cannot be destroyed since the PalavraLink " + palavraLinkListOrphanCheckPalavraLink + " in its palavraLinkList field has a non-nullable palavra field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<ListaPalavras> listaPalavrasList = palavra.getListaPalavrasList();
            for (ListaPalavras listaPalavrasListListaPalavras : listaPalavrasList) {
                listaPalavrasListListaPalavras.getPalavraList().remove(palavra);
                listaPalavrasListListaPalavras = em.merge(listaPalavrasListListaPalavras);
            }
            em.remove(palavra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Palavra> findPalavraEntities() {
        return findPalavraEntities(true, -1, -1);
    }

    public List<Palavra> findPalavraEntities(int maxResults, int firstResult) {
        return findPalavraEntities(false, maxResults, firstResult);
    }

    private List<Palavra> findPalavraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Palavra.class));
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

    public Palavra findPalavra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Palavra.class, id);
        } finally {
            em.close();
        }
    }

    public int getPalavraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Palavra> rt = cq.from(Palavra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
