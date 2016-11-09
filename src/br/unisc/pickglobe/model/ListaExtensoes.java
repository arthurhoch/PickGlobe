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
import javax.persistence.ManyToMany;
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
@Table(name = "ListaExtensoes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ListaExtensoes.findAll", query = "SELECT l FROM ListaExtensoes l"),
    @NamedQuery(name = "ListaExtensoes.findByCodListaExtensoes", query = "SELECT l FROM ListaExtensoes l WHERE l.codListaExtensoes = :codListaExtensoes"),
    @NamedQuery(name = "ListaExtensoes.findByNomeListaExtensoes", query = "SELECT l FROM ListaExtensoes l WHERE l.nomeListaExtensoes = :nomeListaExtensoes")})
public class ListaExtensoes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codListaExtensoes")
    private Integer codListaExtensoes;
    @Basic(optional = false)
    @Column(name = "nomeListaExtensoes")
    private String nomeListaExtensoes;
    @ManyToMany(mappedBy = "listaExtensoesList")
    private List<Extensao> extensaoList;
    @OneToMany(mappedBy = "codListaExtensoes")
    private List<Site> siteList;

    public ListaExtensoes() {
    }

    public ListaExtensoes(Integer codListaExtensoes) {
        this.codListaExtensoes = codListaExtensoes;
    }

    public ListaExtensoes(Integer codListaExtensoes, String nomeListaExtensoes) {
        this.codListaExtensoes = codListaExtensoes;
        this.nomeListaExtensoes = nomeListaExtensoes;
    }

    public Integer getCodListaExtensoes() {
        return codListaExtensoes;
    }

    public void setCodListaExtensoes(Integer codListaExtensoes) {
        this.codListaExtensoes = codListaExtensoes;
    }

    public String getNomeListaExtensoes() {
        return nomeListaExtensoes;
    }

    public void setNomeListaExtensoes(String nomeListaExtensoes) {
        this.nomeListaExtensoes = nomeListaExtensoes;
    }

    @XmlTransient
    public List<Extensao> getExtensaoList() {
        return extensaoList;
    }

    public void setExtensaoList(List<Extensao> extensaoList) {
        this.extensaoList = extensaoList;
    }

    @XmlTransient
    public List<Site> getSiteList() {
        return siteList;
    }

    public void setSiteList(List<Site> siteList) {
        this.siteList = siteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codListaExtensoes != null ? codListaExtensoes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListaExtensoes)) {
            return false;
        }
        ListaExtensoes other = (ListaExtensoes) object;
        if ((this.codListaExtensoes == null && other.codListaExtensoes != null) || (this.codListaExtensoes != null && !this.codListaExtensoes.equals(other.codListaExtensoes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.unisc.pickglobe.model.ListaExtensoes[ codListaExtensoes=" + codListaExtensoes + " ]";
    }
    
}
