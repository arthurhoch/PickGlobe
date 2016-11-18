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

import br.unisc.pickglobe.view.estatisticas.Estatisticas;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author arthurhoch
 */
public class PalavraQuantidade extends AbstractTableModel {

    private final List<PalavraContador> palavraQuantidade;
    private final Estatisticas estatisticas;

    private final String[] colunas = {"Palavra", "Quantidade"};

    public PalavraQuantidade() {
        this.estatisticas = new Estatisticas();
        this.palavraQuantidade = estatisticas.palavraQuantidade();
    }

    @Override
    public int getRowCount() {
        return palavraQuantidade.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        switch (columnIndex) {
            case 0:
                return palavraQuantidade.get(rowIndex).getPalavra();
            default:
                return palavraQuantidade.get(rowIndex).getQuantidade();
        }

    }

    @Override
    public String getColumnName(int columnIndex) {
        return colunas[columnIndex];
    }

}
