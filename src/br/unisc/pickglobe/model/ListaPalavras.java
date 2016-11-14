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
package br.unisc.pickglobe.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "ListaPalavras")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ListaPalavras.findAll", query = "SELECT l FROM ListaPalavras l"),
    @NamedQuery(name = "ListaPalavras.findByCodListaPalavras", query = "SELECT l FROM ListaPalavras l WHERE l.codListaPalavras = :codListaPalavras"),
    @NamedQuery(name = "ListaPalavras.findByNomeLista", query = "SELECT l FROM ListaPalavras l WHERE l.nomeLista = :nomeLista")})
public class ListaPalavras implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codListaPalavras")
    private Integer codListaPalavras;
    @Basic(optional = false)
    @Column(name = "nomeLista")
    private String nomeLista;
    @JoinTable(name = "PalavraListaPalavras", joinColumns = {
        @JoinColumn(name = "codListaPalavras", referencedColumnName = "codListaPalavras")}, inverseJoinColumns = {
        @JoinColumn(name = "codPalavra", referencedColumnName = "codPalavra")})
    @ManyToMany
    private List<Palavra> palavraList;
    @OneToMany(mappedBy = "codListaPalavras")
    private List<Site> siteList;
    @JoinColumn(name = "codTipoLista", referencedColumnName = "codTipoLista")
    @ManyToOne(optional = false)
    private TipoLista codTipoLista;

    public ListaPalavras() {
    }

    public ListaPalavras(Integer codListaPalavras) {
        this.codListaPalavras = codListaPalavras;
    }

    public ListaPalavras(Integer codListaPalavras, String nomeLista) {
        this.codListaPalavras = codListaPalavras;
        this.nomeLista = nomeLista;
    }

    public Integer getCodListaPalavras() {
        return codListaPalavras;
    }

    public void setCodListaPalavras(Integer codListaPalavras) {
        this.codListaPalavras = codListaPalavras;
    }

    public String getNomeLista() {
        return nomeLista;
    }

    public void setNomeLista(String nomeLista) {
        this.nomeLista = nomeLista;
    }

    @XmlTransient
    public List<Palavra> getPalavraList() {
        return palavraList;
    }

    public void setPalavraList(List<Palavra> palavraList) {
        this.palavraList = palavraList;
    }

    @XmlTransient
    public List<Site> getSiteList() {
        return siteList;
    }

    public void setSiteList(List<Site> siteList) {
        this.siteList = siteList;
    }

    public TipoLista getCodTipoLista() {
        return codTipoLista;
    }

    public void setCodTipoLista(TipoLista codTipoLista) {
        this.codTipoLista = codTipoLista;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codListaPalavras != null ? codListaPalavras.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListaPalavras)) {
            return false;
        }
        ListaPalavras other = (ListaPalavras) object;
        if ((this.codListaPalavras == null && other.codListaPalavras != null) || (this.codListaPalavras != null && !this.codListaPalavras.equals(other.codListaPalavras))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.unisc.pickglobe.model.ListaPalavras[ codListaPalavras=" + codListaPalavras + " ]";
    }
    
}
