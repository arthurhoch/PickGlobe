/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.model;

/**
 *
 * @author arthurhoch
 */
public class Link {
    private int codLink;
    private String pagina;

    public int getCodLink() {
        return codLink;
    }

    public void setCodLink(int codLink) {
        this.codLink = codLink;
    }
    private String url;
    private String caminho;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public String getPagina() {
        return pagina;
    }

    public void setPagina(String pagina) {
        this.pagina = pagina;
    }
}
