/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author arthurhoch
 */
@Entity
@Table(name = "Coleta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Coleta.findAll", query = "SELECT c FROM Coleta c"),
    @NamedQuery(name = "Coleta.findByCodColeta", query = "SELECT c FROM Coleta c WHERE c.codColeta = :codColeta"),
    @NamedQuery(name = "Coleta.findByDate", query = "SELECT c FROM Coleta c WHERE c.date = :date"),
    @NamedQuery(name = "Coleta.findByTime", query = "SELECT c FROM Coleta c WHERE c.time = :time")})
public class Coleta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codColeta")
    private Integer codColeta;
    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @Column(name = "time")
    @Temporal(TemporalType.TIME)
    private Date time;
    @JoinTable(name = "ColetaLink", joinColumns = {
        @JoinColumn(name = "codColeta", referencedColumnName = "codColeta")}, inverseJoinColumns = {
        @JoinColumn(name = "codLink", referencedColumnName = "codLink")})
    @ManyToMany
    private List<Link> linkList;
    @JoinColumn(name = "codSite", referencedColumnName = "codSite")
    @ManyToOne
    private Site codSite;

    public Coleta() {
    }

    public Coleta(Integer codColeta) {
        this.codColeta = codColeta;
    }

    public Coleta(Integer codColeta, Date date, Date time) {
        this.codColeta = codColeta;
        this.date = date;
        this.time = time;
    }

    public Integer getCodColeta() {
        return codColeta;
    }

    public void setCodColeta(Integer codColeta) {
        this.codColeta = codColeta;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @XmlTransient
    public List<Link> getLinkList() {
        return linkList;
    }

    public void setLinkList(List<Link> linkList) {
        this.linkList = linkList;
    }

    public Site getCodSite() {
        return codSite;
    }

    public void setCodSite(Site codSite) {
        this.codSite = codSite;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codColeta != null ? codColeta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Coleta)) {
            return false;
        }
        Coleta other = (Coleta) object;
        if ((this.codColeta == null && other.codColeta != null) || (this.codColeta != null && !this.codColeta.equals(other.codColeta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.unisc.pickglobe.model.Coleta[ codColeta=" + codColeta + " ]";
    }
    
}
