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

    public void edit(TipoLista tipoLista) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoLista persistentTipoLista = em.find(TipoLista.class, tipoLista.getCodTipoLista());
            List<ListaPalavras> listaPalavrasListOld = persistentTipoLista.getListaPalavrasList();
            List<ListaPalavras> listaPalavrasListNew = tipoLista.getListaPalavrasList();
            List<String> illegalOrphanMessages = null;
            for (ListaPalavras listaPalavrasListOldListaPalavras : listaPalavrasListOld) {
                if (!listaPalavrasListNew.contains(listaPalavrasListOldListaPalavras)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ListaPalavras " + listaPalavrasListOldListaPalavras + " since its codTipoLista field is not nullable.");
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
            tipoLista.setListaPalavrasList(listaPalavrasListNew);
            tipoLista = em.merge(tipoLista);
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<String> illegalOrphanMessages = null;
            List<ListaPalavras> listaPalavrasListOrphanCheck = tipoLista.getListaPalavrasList();
            for (ListaPalavras listaPalavrasListOrphanCheckListaPalavras : listaPalavrasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoLista (" + tipoLista + ") cannot be destroyed since the ListaPalavras " + listaPalavrasListOrphanCheckListaPalavras + " in its listaPalavrasList field has a non-nullable codTipoLista field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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
