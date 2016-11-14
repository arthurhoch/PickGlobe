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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author arthurhoch
 */
@Entity
@Table(name = "PalavraLink")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PalavraLink.findAll", query = "SELECT p FROM PalavraLink p"),
    @NamedQuery(name = "PalavraLink.findByCodPalavra", query = "SELECT p FROM PalavraLink p WHERE p.palavraLinkPK.codPalavra = :codPalavra"),
    @NamedQuery(name = "PalavraLink.findByCodLink", query = "SELECT p FROM PalavraLink p WHERE p.palavraLinkPK.codLink = :codLink"),
    @NamedQuery(name = "PalavraLink.findByQuantidade", query = "SELECT p FROM PalavraLink p WHERE p.quantidade = :quantidade")})
public class PalavraLink implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PalavraLinkPK palavraLinkPK;
    @Basic(optional = false)
    @Column(name = "quantidade")
    private int quantidade;
    @JoinColumn(name = "codLink", referencedColumnName = "codLink", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Link link;
    @JoinColumn(name = "codPalavra", referencedColumnName = "codPalavra", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Palavra palavra;

    public PalavraLink() {
    }

    public PalavraLink(PalavraLinkPK palavraLinkPK) {
        this.palavraLinkPK = palavraLinkPK;
    }

    public PalavraLink(PalavraLinkPK palavraLinkPK, int quantidade) {
        this.palavraLinkPK = palavraLinkPK;
        this.quantidade = quantidade;
    }

    public PalavraLink(int codPalavra, int codLink) {
        this.palavraLinkPK = new PalavraLinkPK(codPalavra, codLink);
    }

    public PalavraLinkPK getPalavraLinkPK() {
        return palavraLinkPK;
    }

    public void setPalavraLinkPK(PalavraLinkPK palavraLinkPK) {
        this.palavraLinkPK = palavraLinkPK;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public Palavra getPalavra() {
        return palavra;
    }

    public void setPalavra(Palavra palavra) {
        this.palavra = palavra;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (palavraLinkPK != null ? palavraLinkPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PalavraLink)) {
            return false;
        }
        PalavraLink other = (PalavraLink) object;
        if ((this.palavraLinkPK == null && other.palavraLinkPK != null) || (this.palavraLinkPK != null && !this.palavraLinkPK.equals(other.palavraLinkPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.unisc.pickglobe.model.PalavraLink[ palavraLinkPK=" + palavraLinkPK + " ]";
    }
    
}
