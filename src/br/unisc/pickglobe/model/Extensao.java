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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author arthurhoch
 */
@Entity
@Table(name = "Extensao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Extensao.findAll", query = "SELECT e FROM Extensao e"),
    @NamedQuery(name = "Extensao.findByCodExtensao", query = "SELECT e FROM Extensao e WHERE e.codExtensao = :codExtensao"),
    @NamedQuery(name = "Extensao.findByNomeExtensao", query = "SELECT e FROM Extensao e WHERE e.nomeExtensao = :nomeExtensao")})
public class Extensao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codExtensao")
    private Integer codExtensao;
    @Basic(optional = false)
    @Column(name = "nomeExtensao")
    private String nomeExtensao;
    @JoinTable(name = "ListaExtensoesExtensao", joinColumns = {
        @JoinColumn(name = "codExtensoes", referencedColumnName = "codExtensao")}, inverseJoinColumns = {
        @JoinColumn(name = "codListaExtensoes", referencedColumnName = "codListaExtensoes")})
    @ManyToMany
    private List<ListaExtensoes> listaExtensoesList;

    public Extensao() {
    }

    public Extensao(Integer codExtensao) {
        this.codExtensao = codExtensao;
    }

    public Extensao(Integer codExtensao, String nomeExtensao) {
        this.codExtensao = codExtensao;
        this.nomeExtensao = nomeExtensao;
    }

    public Integer getCodExtensao() {
        return codExtensao;
    }

    public void setCodExtensao(Integer codExtensao) {
        this.codExtensao = codExtensao;
    }

    public String getNomeExtensao() {
        return nomeExtensao;
    }

    public void setNomeExtensao(String nomeExtensao) {
        this.nomeExtensao = nomeExtensao;
    }

    @XmlTransient
    public List<ListaExtensoes> getListaExtensoesList() {
        return listaExtensoesList;
    }

    public void setListaExtensoesList(List<ListaExtensoes> listaExtensoesList) {
        this.listaExtensoesList = listaExtensoesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codExtensao != null ? codExtensao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Extensao)) {
            return false;
        }
        Extensao other = (Extensao) object;
        if ((this.codExtensao == null && other.codExtensao != null) || (this.codExtensao != null && !this.codExtensao.equals(other.codExtensao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.unisc.pickglobe.model.Extensao[ codExtensao=" + codExtensao + " ]";
    }
    
}
