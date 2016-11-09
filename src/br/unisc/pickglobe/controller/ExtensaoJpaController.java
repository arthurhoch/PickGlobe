/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.controller;

import br.unisc.pickglobe.controller.exceptions.NonexistentEntityException;
import br.unisc.pickglobe.model.Extensao;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.unisc.pickglobe.model.ListaExtensoes;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author arthurhoch
 */
public class ExtensaoJpaController implements Serializable {

    public ExtensaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Extensao extensao) {
        if (extensao.getListaExtensoesList() == null) {
            extensao.setListaExtensoesList(new ArrayList<ListaExtensoes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ListaExtensoes> attachedListaExtensoesList = new ArrayList<ListaExtensoes>();
            for (ListaExtensoes listaExtensoesListListaExtensoesToAttach : extensao.getListaExtensoesList()) {
                listaExtensoesListListaExtensoesToAttach = em.getReference(listaExtensoesListListaExtensoesToAttach.getClass(), listaExtensoesListListaExtensoesToAttach.getCodListaExtensoes());
                attachedListaExtensoesList.add(listaExtensoesListListaExtensoesToAttach);
            }
            extensao.setListaExtensoesList(attachedListaExtensoesList);
            em.persist(extensao);
            for (ListaExtensoes listaExtensoesListListaExtensoes : extensao.getListaExtensoesList()) {
                listaExtensoesListListaExtensoes.getExtensaoList().add(extensao);
                listaExtensoesListListaExtensoes = em.merge(listaExtensoesListListaExtensoes);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Extensao extensao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Extensao persistentExtensao = em.find(Extensao.class, extensao.getCodExtensao());
            List<ListaExtensoes> listaExtensoesListOld = persistentExtensao.getListaExtensoesList();
            List<ListaExtensoes> listaExtensoesListNew = extensao.getListaExtensoesList();
            List<ListaExtensoes> attachedListaExtensoesListNew = new ArrayList<ListaExtensoes>();
            for (ListaExtensoes listaExtensoesListNewListaExtensoesToAttach : listaExtensoesListNew) {
                listaExtensoesListNewListaExtensoesToAttach = em.getReference(listaExtensoesListNewListaExtensoesToAttach.getClass(), listaExtensoesListNewListaExtensoesToAttach.getCodListaExtensoes());
                attachedListaExtensoesListNew.add(listaExtensoesListNewListaExtensoesToAttach);
            }
            listaExtensoesListNew = attachedListaExtensoesListNew;
            extensao.setListaExtensoesList(listaExtensoesListNew);
            extensao = em.merge(extensao);
            for (ListaExtensoes listaExtensoesListOldListaExtensoes : listaExtensoesListOld) {
                if (!listaExtensoesListNew.contains(listaExtensoesListOldListaExtensoes)) {
                    listaExtensoesListOldListaExtensoes.getExtensaoList().remove(extensao);
                    listaExtensoesListOldListaExtensoes = em.merge(listaExtensoesListOldListaExtensoes);
                }
            }
            for (ListaExtensoes listaExtensoesListNewListaExtensoes : listaExtensoesListNew) {
                if (!listaExtensoesListOld.contains(listaExtensoesListNewListaExtensoes)) {
                    listaExtensoesListNewListaExtensoes.getExtensaoList().add(extensao);
                    listaExtensoesListNewListaExtensoes = em.merge(listaExtensoesListNewListaExtensoes);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = extensao.getCodExtensao();
                if (findExtensao(id) == null) {
                    throw new NonexistentEntityException("The extensao with id " + id + " no longer exists.");
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
            Extensao extensao;
            try {
                extensao = em.getReference(Extensao.class, id);
                extensao.getCodExtensao();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The extensao with id " + id + " no longer exists.", enfe);
            }
            List<ListaExtensoes> listaExtensoesList = extensao.getListaExtensoesList();
            for (ListaExtensoes listaExtensoesListListaExtensoes : listaExtensoesList) {
                listaExtensoesListListaExtensoes.getExtensaoList().remove(extensao);
                listaExtensoesListListaExtensoes = em.merge(listaExtensoesListListaExtensoes);
            }
            em.remove(extensao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Extensao> findExtensaoEntities() {
        return findExtensaoEntities(true, -1, -1);
    }

    public List<Extensao> findExtensaoEntities(int maxResults, int firstResult) {
        return findExtensaoEntities(false, maxResults, firstResult);
    }

    private List<Extensao> findExtensaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Extensao.class));
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

    public Extensao findExtensao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Extensao.class, id);
        } finally {
            em.close();
        }
    }

    public int getExtensaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Extensao> rt = cq.from(Extensao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
