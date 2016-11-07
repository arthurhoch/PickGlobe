/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author arthurhoch
 */
@Entity
@Table(name = "TipoLista")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoLista.findAll", query = "SELECT t FROM TipoLista t"),
    @NamedQuery(name = "TipoLista.findByCodTipoLista", query = "SELECT t FROM TipoLista t WHERE t.codTipoLista = :codTipoLista"),
    @NamedQuery(name = "TipoLista.findByTipo", query = "SELECT t FROM TipoLista t WHERE t.tipo = :tipo")})
public class TipoLista implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codTipoLista")
    private Integer codTipoLista;
    @Basic(optional = false)
    @Column(name = "tipo")
    private String tipo;
    @OneToMany(mappedBy = "codTipoLista")
    private List<ListaPalavras> listaPalavrasList;

    public TipoLista() {
    }

    public TipoLista(Integer codTipoLista) {
        this.codTipoLista = codTipoLista;
    }

    public TipoLista(Integer codTipoLista, String tipo) {
        this.codTipoLista = codTipoLista;
        this.tipo = tipo;
    }

    public Integer getCodTipoLista() {
        return codTipoLista;
    }

    public void setCodTipoLista(Integer codTipoLista) {
        this.codTipoLista = codTipoLista;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @XmlTransient
    public List<ListaPalavras> getListaPalavrasList() {
        return listaPalavrasList;
    }

    public void setListaPalavrasList(List<ListaPalavras> listaPalavrasList) {
        this.listaPalavrasList = listaPalavrasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codTipoLista != null ? codTipoLista.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoLista)) {
            return false;
        }
        TipoLista other = (TipoLista) object;
        if ((this.codTipoLista == null && other.codTipoLista != null) || (this.codTipoLista != null && !this.codTipoLista.equals(other.codTipoLista))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.unisc.pickglobe.model.TipoLista[ codTipoLista=" + codTipoLista + " ]";
    }

}
