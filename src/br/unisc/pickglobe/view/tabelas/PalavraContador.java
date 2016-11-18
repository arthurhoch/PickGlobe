/*
 * Copyright (C) 2016 arthurhoch
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package br.unisc.pickglobe.view.tabelas;

import java.util.List;

/**
 *
 * @author arthurhoch
 */
public class PalavraContador {

    private String palavra;
    private int quantidade;

    public PalavraContador(String palavra, Integer quantidade) {
        this.palavra = palavra;
        this.quantidade = quantidade;
    }

    public PalavraContador() {
        this.palavra = new String();
        this.quantidade = 0;
    }

    public String getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public boolean containsPalavra(List<PalavraContador> palavras, String palavraVerificar) {

        for (PalavraContador p : palavras) {

            if (p.getPalavra().equals(palavraVerificar)) {
                return true;
            }

        }
        return false;
    }
    
    public List<PalavraContador>  palavraContar(List<PalavraContador> palavrasContadas, String palavra, int somar) {
        
        for (PalavraContador palavrasContada : palavrasContadas) {
            if (palavrasContada.getPalavra().equals(palavra)) {
                palavrasContada.setQuantidade( palavrasContada.getQuantidade() + somar);
                return palavrasContadas;
            }
        }
        return palavrasContadas;
    }

}
