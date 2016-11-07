/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
