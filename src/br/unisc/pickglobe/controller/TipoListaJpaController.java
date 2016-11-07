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
import br.unisc.pickglobe.model.ListaPalavras;
import br.unisc.pickglobe.model.TipoLista;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author arthurhoch
 */
public class TipoListaJpaController implements Serializable {

    public TipoListaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoLista tipoLista) {
        if (tipoLista.getListaPalavrasList() == null) {
            tipoLista.setListaPalavrasList(new ArrayList<ListaPalavras>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ListaPalavras> attachedListaPalavrasList = new ArrayList<ListaPalavras>();
            for (ListaPalavras listaPalavrasListListaPalavrasToAttach : tipoLista.getListaPalavrasList()) {
                listaPalavrasListListaPalavrasToAttach = em.getReference(listaPalavrasListListaPalavrasToAttach.getClass(), listaPalavrasListListaPalavrasToAttach.getCodListaPalavras());
                attachedListaPalavrasList.add(listaPalavrasListListaPalavrasToAttach);
            }
            tipoLista.setListaPalavrasList(attachedListaPalavrasList);
            em.persist(tipoLista);
            for (ListaPalavras listaPalavrasListListaPalavras : tipoLista.getListaPalavrasList()) {
                TipoLista oldCodTipoListaOfListaPalavrasListListaPalavras = listaPalavrasListListaPalavras.getCodTipoLista();
                listaPalavrasListListaPalavras.setCodTipoLista(tipoLista);
                listaPalavrasListListaPalavras = em.merge(listaPalavrasListListaPalavras);
                if (oldCodTipoListaOfListaPalavrasListListaPalavras != null) {
                    oldCodTipoListaOfListaPalavrasListListaPalavras.getListaPalavrasList().remove(listaPalavrasListListaPalavras);
                    oldCodTipoListaOfListaPalavrasListListaPalavras = em.merge(oldCodTipoListaOfListaPalavrasListListaPalavras);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoLista tipoLista) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoLista persistentTipoLista = em.find(TipoLista.class, tipoLista.getCodTipoLista());
            List<ListaPalavras> listaPalavrasListOld = persistentTipoLista.getListaPalavrasList();
            List<ListaPalavras> listaPalavrasListNew = tipoLista.getListaPalavrasList();
            List<ListaPalavras> attachedListaPalavrasListNew = new ArrayList<ListaPalavras>();
            for (ListaPalavras listaPalavrasListNewListaPalavrasToAttach : listaPalavrasListNew) {
                listaPalavrasListNewListaPalavrasToAttach = em.getReference(listaPalavrasListNewListaPalavrasToAttach.getClass(), listaPalavrasListNewListaPalavrasToAttach.getCodListaPalavras());
                attachedListaPalavrasListNew.add(listaPalavrasListNewListaPalavrasToAttach);
            }
            listaPalavrasListNew = attachedListaPalavrasListNew;
            tipoLista.setListaPalavrasList(listaPalavrasListNew);
            tipoLista = em.merge(tipoLista);
            for (ListaPalavras listaPalavrasListOldListaPalavras : listaPalavrasListOld) {
                if (!listaPalavrasListNew.contains(listaPalavrasListOldListaPalavras)) {
                    listaPalavrasListOldListaPalavras.setCodTipoLista(null);
                    listaPalavrasListOldListaPalavras = em.merge(listaPalavrasListOldListaPalavras);
                }
            }
            for (ListaPalavras listaPalavrasListNewListaPalavras : listaPalavrasListNew) {
                if (!listaPalavrasListOld.contains(listaPalavrasListNewListaPalavras)) {
                    TipoLista oldCodTipoListaOfListaPalavrasListNewListaPalavras = listaPalavrasListNewListaPalavras.getCodTipoLista();
                    listaPalavrasListNewListaPalavras.setCodTipoLista(tipoLista);
                    listaPalavrasListNewListaPalavras = em.merge(listaPalavrasListNewListaPalavras);
                    if (oldCodTipoListaOfListaPalavrasListNewListaPalavras != null && !oldCodTipoListaOfListaPalavrasListNewListaPalavras.equals(tipoLista)) {
                        oldCodTipoListaOfListaPalavrasListNewListaPalavras.getListaPalavrasList().remove(listaPalavrasListNewListaPalavras);
                        oldCodTipoListaOfListaPalavrasListNewListaPalavras = em.merge(oldCodTipoListaOfListaPalavrasListNewListaPalavras);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoLista.getCodTipoLista();
                if (findTipoLista(id) == null) {
                    throw new NonexistentEntityException("The tipoLista with id " + id + " no longer exists.");
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
            TipoLista tipoLista;
            try {
                tipoLista = em.getReference(TipoLista.class, id);
                tipoLista.getCodTipoLista();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoLista with id " + id + " no longer exists.", enfe);
            }
            List<ListaPalavras> listaPalavrasList = tipoLista.getListaPalavrasList();
            for (ListaPalavras listaPalavrasListListaPalavras : listaPalavrasList) {
                listaPalavrasListListaPalavras.setCodTipoLista(null);
                listaPalavrasListListaPalavras = em.merge(listaPalavrasListListaPalavras);
            }
            em.remove(tipoLista);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoLista> findTipoListaEntities() {
        return findTipoListaEntities(true, -1, -1);
    }

    public List<TipoLista> findTipoListaEntities(int maxResults, int firstResult) {
        return findTipoListaEntities(false, maxResults, firstResult);
    }

    private List<TipoLista> findTipoListaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoLista.class));
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

    public TipoLista findTipoLista(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoLista.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoListaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoLista> rt = cq.from(TipoLista.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
