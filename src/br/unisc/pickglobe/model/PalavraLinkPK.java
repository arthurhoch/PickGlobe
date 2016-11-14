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
import javax.persistence.Embeddable;

/**
 *
 * @author arthurhoch
 */
@Embeddable
public class PalavraLinkPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "codPalavra")
    private int codPalavra;
    @Basic(optional = false)
    @Column(name = "codLink")
    private int codLink;

    public PalavraLinkPK() {
    }

    public PalavraLinkPK(int codPalavra, int codLink) {
        this.codPalavra = codPalavra;
        this.codLink = codLink;
    }

    public int getCodPalavra() {
        return codPalavra;
    }

    public void setCodPalavra(int codPalavra) {
        this.codPalavra = codPalavra;
    }

    public int getCodLink() {
        return codLink;
    }

    public void setCodLink(int codLink) {
        this.codLink = codLink;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codPalavra;
        hash += (int) codLink;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PalavraLinkPK)) {
            return false;
        }
        PalavraLinkPK other = (PalavraLinkPK) object;
        if (this.codPalavra != other.codPalavra) {
            return false;
        }
        if (this.codLink != other.codLink) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.unisc.pickglobe.model.PalavraLinkPK[ codPalavra=" + codPalavra + ", codLink=" + codLink + " ]";
    }
    
}
