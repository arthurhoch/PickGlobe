
package br.unisc.pickglobe.model;

/**
 *
 * @author will
 */
public class Palavra {
    
    private int codPalavras;
    private String palavra;
    private String tipo;

    public int getCodPalavras() {
        return codPalavras;
    }

    public void setCodPalavras(int codPalavras) {
        this.codPalavras = codPalavras;
    }

    public String getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
}
