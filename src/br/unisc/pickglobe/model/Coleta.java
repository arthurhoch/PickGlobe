
package br.unisc.pickglobe.model;

/**
 *
 * @author will
 */
public class Coleta {
    private int codColeta;
    private String md5;
    private int site_codSites;

    public int getCodColeta() {
        return codColeta;
    }

    public void setCodColeta(int codColeta) {
        this.codColeta = codColeta;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public int getSite_codSites() {
        return site_codSites;
    }

    public void setSite_codSites(int site_codSites) {
        this.site_codSites = site_codSites;
    }
}
