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
@Table(name = "Link")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Link.findAll", query = "SELECT l FROM Link l"),
    @NamedQuery(name = "Link.findByCodLink", query = "SELECT l FROM Link l WHERE l.codLink = :codLink"),
    @NamedQuery(name = "Link.findByMd5", query = "SELECT l FROM Link l WHERE l.md5 = :md5"),
    @NamedQuery(name = "Link.findByCaminho", query = "SELECT l FROM Link l WHERE l.caminho = :caminho"),
    @NamedQuery(name = "Link.findByUrl", query = "SELECT l FROM Link l WHERE l.url = :url")})
public class Link implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codLink")
    private Integer codLink;
    @Basic(optional = false)
    @Column(name = "md5")
    private String md5;
    @Basic(optional = false)
    @Column(name = "caminho")
    private String caminho;
    @Basic(optional = false)
    @Column(name = "url")
    private String url;
    @ManyToMany(mappedBy = "linkList")
    private List<Coleta> coletaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "link")
    private List<PalavraLink> palavraLinkList;
    private String pagina;

    public Link() {
    }

    public Link(Integer codLink) {
        this.codLink = codLink;
    }

    public Link(Integer codLink, String md5, String caminho, String url) {
        this.codLink = codLink;
        this.md5 = md5;
        this.caminho = caminho;
        this.url = url;
    }

    public Integer getCodLink() {
        return codLink;
    }

    public void setCodLink(Integer codLink) {
        this.codLink = codLink;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPagina() {
        return pagina;
    }

    public void setPagina(String pagina) {
        this.pagina = pagina;
    }

    @XmlTransient
    public List<Coleta> getColetaList() {
        return coletaList;
    }

    public void setColetaList(List<Coleta> coletaList) {
        this.coletaList = coletaList;
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
        hash += (codLink != null ? codLink.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Link)) {
            return false;
        }
        Link other = (Link) object;
        if ((this.codLink == null && other.codLink != null) || (this.codLink != null && !this.codLink.equals(other.codLink))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.unisc.pickglobe.model.Link[ codLink=" + codLink + " ]";
    }
    
}
