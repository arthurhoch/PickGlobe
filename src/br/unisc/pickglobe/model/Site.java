
package br.unisc.pickglobe.model;

import java.sql.Timestamp;

/**
 *
 * @author will
 */
public class Site {
    private int codSites;
    private Timestamp tempoColeta;
    private String url;
    private int status;
    private String descricao;

    public int getCodSites() {
        return codSites;
    }

    public void setCodSites(int codSites) {
        this.codSites = codSites;
    }

    public Timestamp getTempoColeta() {
        return tempoColeta;
    }

    public void setTempoColeta(Timestamp tempoColeta) {
        this.tempoColeta = tempoColeta;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    

}
