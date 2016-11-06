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
import javax.persistence.JoinColumn;
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
@Table(name = "Site")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Site.findAll", query = "SELECT s FROM Site s"),
    @NamedQuery(name = "Site.findByCodSite", query = "SELECT s FROM Site s WHERE s.codSite = :codSite"),
    @NamedQuery(name = "Site.findByIntervaloColeta", query = "SELECT s FROM Site s WHERE s.intervaloColeta = :intervaloColeta"),
    @NamedQuery(name = "Site.findByStatus", query = "SELECT s FROM Site s WHERE s.status = :status"),
    @NamedQuery(name = "Site.findByUrl", query = "SELECT s FROM Site s WHERE s.url = :url")})
public class Site implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codSite")
    private Integer codSite;
    @Basic(optional = false)
    @Column(name = "intervaloColeta")
    private int intervaloColeta;
    @Basic(optional = false)
    @Column(name = "status")
    private boolean status;
    @Basic(optional = false)
    @Column(name = "url")
    private String url;
    @JoinColumn(name = "codListaExtensoes", referencedColumnName = "codListaExtensoes")
    @ManyToOne
    private ListaExtensoes codListaExtensoes;
    @JoinColumn(name = "codListaPalavras", referencedColumnName = "codListaPalavras")
    @ManyToOne
    private ListaPalavras codListaPalavras;
    @OneToMany(mappedBy = "codSite")
    private List<Coleta> coletaList;

    public Site() {
    }
    
    public Site(Integer codSite) {
        this.codSite = codSite;
    }

    public Site(Integer codSite, int intervaloColeta, boolean status, String url) {
        this.codSite = codSite;
        this.intervaloColeta = intervaloColeta;
        this.status = status;
        this.url = url;
    }

    public Integer getCodSite() {
        return codSite;
    }

    public void setCodSite(Integer codSite) {
        this.codSite = codSite;
    }

    public int getIntervaloColeta() {
        return intervaloColeta;
    }

    public void setIntervaloColeta(int intervaloColeta) {
        this.intervaloColeta = intervaloColeta;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ListaExtensoes getCodListaExtensoes() {
        return codListaExtensoes;
    }

    public void setCodListaExtensoes(ListaExtensoes codListaExtensoes) {
        this.codListaExtensoes = codListaExtensoes;
    }

    public ListaPalavras getCodListaPalavras() {
        return codListaPalavras;
    }

    public void setCodListaPalavras(ListaPalavras codListaPalavras) {
        this.codListaPalavras = codListaPalavras;
    }
/*
    public int getTempoRestante() {
        return tempoRestante;
    }

    public void setTempoRestante(int tempoRestante) {
        this.tempoRestante = tempoRestante;
    }
*/
    @XmlTransient
    public List<Coleta> getColetaList() {
        return coletaList;
    }

    public void setColetaList(List<Coleta> coletaList) {
        this.coletaList = coletaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codSite != null ? codSite.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Site)) {
            return false;
        }
        Site other = (Site) object;
        if ((this.codSite == null && other.codSite != null) || (this.codSite != null && !this.codSite.equals(other.codSite))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.unisc.pickglobe.model.Site[ codSite=" + codSite + " ]";
    }
    
}
