/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.view.tabelas;

/**
 *
 * @author arthurhoch
 */
public class SitesUsados {
    
    private String nomeSite;
    private String nomeLista;
    private String intervaloConsulta;
    private boolean habilitado;

    public SitesUsados(String nomeSite, String nomeLista, String intervaloConsulta, boolean habilitado) {
        this.nomeSite = nomeSite;
        this.nomeLista = nomeLista;
        this.intervaloConsulta = intervaloConsulta;
        this.habilitado = habilitado;
    }

    public String getNomeSite() {
        return nomeSite;
    }

    public void setNomeSite(String nomeSite) {
        this.nomeSite = nomeSite;
    }

    public String getNomeLista() {
        return nomeLista;
    }

    public void setNomeLista(String nomeLista) {
        this.nomeLista = nomeLista;
    }

    public String getIntervaloConsulta() {
        return intervaloConsulta;
    }

    public void setIntervaloConsulta(String intervaloConsulta) {
        this.intervaloConsulta = intervaloConsulta;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }
    
    
    
    
}
