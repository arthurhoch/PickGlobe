/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "Palavra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Palavra.findAll", query = "SELECT p FROM Palavra p"),
    @NamedQuery(name = "Palavra.findByCodPalavra", query = "SELECT p FROM Palavra p WHERE p.codPalavra = :codPalavra"),
    @NamedQuery(name = "Palavra.findByPalavra", query = "SELECT p FROM Palavra p WHERE p.palavra = :palavra")})
public class Palavra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codPalavra")
    private Integer codPalavra;
    @Basic(optional = false)
    @Column(name = "palavra")
    private String palavra;
    @ManyToMany(mappedBy = "palavraList")
    private List<ListaPalavras> listaPalavrasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "palavra")
    private List<PalavraLink> palavraLinkList;

    public Palavra() {
    }

    public Palavra(Integer codPalavra) {
        this.codPalavra = codPalavra;
    }

    public Palavra(Integer codPalavra, String palavra) {
        this.codPalavra = codPalavra;
        this.palavra = palavra;
    }

    public Integer getCodPalavra() {
        return codPalavra;
    }

    public void setCodPalavra(Integer codPalavra) {
        this.codPalavra = codPalavra;
    }

    public String getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }

    @XmlTransient
    public List<ListaPalavras> getListaPalavrasList() {
        return listaPalavrasList;
    }

    public void setListaPalavrasList(List<ListaPalavras> listaPalavrasList) {
        this.listaPalavrasList = listaPalavrasList;
    }

    @XmlTransient
    public List<PalavraLink> getPalavraLinkList() {
        return palavraLinkList;
    }

    public void setPalavraLinkList(List<PalavraLink> palavraLinkList) {
        this.palavraLinkList = palavraLinkList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codPalavra != null ? codPalavra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Palavra)) {
            return false;
        }
        Palavra other = (Palavra) object;
        if ((this.codPalavra == null && other.codPalavra != null) || (this.codPalavra != null && !this.codPalavra.equals(other.codPalavra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.unisc.pickglobe.model.Palavra[ codPalavra=" + codPalavra + " ]";
    }
    
}
