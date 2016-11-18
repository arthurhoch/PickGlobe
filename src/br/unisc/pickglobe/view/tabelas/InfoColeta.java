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

import br.unisc.pickglobe.model.Coleta;
import br.unisc.pickglobe.view.estatisticas.Estatisticas;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author arthurhoch
 */
public class InfoColeta extends AbstractTableModel {

    private final Estatisticas estatisticas;
    private List<Coleta> coletas;

    private final String[] colunas = {"CodColeta", "Site URL", "Data", "Hora"};

    public InfoColeta() {
        this.estatisticas = new Estatisticas();
        this.coletas = estatisticas.getColeta();

    }

    public void update() {
        coletas = estatisticas.getColeta();
    }

    @Override
    public int getRowCount() {
        return coletas.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Coleta coleta = coletas.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return coleta.getCodColeta();
            case 1:
                return coleta.getCodSite().getUrl();
            case 2:
                return coleta.getDate().toString().substring(0, 8);
            case 3:
                if(coleta.getTime().toString().length()  > 18)
                    return coleta.getTime().toString().substring(11, 19);
                else
                    return coleta.getTime().toString();
            default:
                return "";
        }
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }
}
