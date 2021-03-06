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

import br.unisc.pickglobe.controller.SiteJpaController;
import br.unisc.pickglobe.model.Site;
import br.unisc.pickglobe.view.actions.ActionSite;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author arthurhoch
 */
public class FilaExecucao extends AbstractTableModel {

    private final ActionSite action;
    private final SiteJpaController siteJpaController;
    private static List<Site> linhas;
    private final List<Temporizador> tempoRestante;

    private final String[] colunas = {"Site", "TempoRestante"};
    private TableRowSorter<SitesUsados> sorter;

    public FilaExecucao() {
        this.action = new ActionSite();
        this.siteJpaController = new SiteJpaController(action.getEmf());
        this.tempoRestante = new LinkedList<>();
    }

    @Override
    public int getRowCount() {
        return linhas.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Site s = linhas.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return s.getUrl();
            case 1:
                return action.calculateTime(getTempoRestante(s.getCodSite()));
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");

        }
    }

    public int getTempoRestante(int key) {
        for (Temporizador t : tempoRestante) {
            if (t.getKey() == key) {
                return t.getTime();
            }
        }
        return -1;
    }

    public void setTempoRestante(int key, int tempo) {
        for (int i = 0; i < tempoRestante.size(); i++) {
            Temporizador t = tempoRestante.get(i);
            if (t.getKey() == key) {
                t.setTime(tempo);
                setValue(i, linhas.get(i));
            }
        }
    }

    public List<Site> getLinhas() {
        return linhas;
    }

    public void setLinhas(List<Site> linhas) {
        FilaExecucao.linhas = linhas;
    }

    @Override
    public String getColumnName(int columnIndex) {
        // Retorna o conteÃºdo do Array que possui o nome das colunas  
        return colunas[columnIndex];
    }

    public Site getSite(int rowIndex) {
        return linhas.get(rowIndex);
    }

    /* Adiciona um registro. */
    public void addSite(Site s) {
        // Adiciona o registro.  
        linhas.add(s);
        tempoRestante.add(new Temporizador(s.getCodSite(), s.getIntervaloColeta()));
        //JOptionPane.showMessageDialog(null,linhas.get(0).getDescricaoPatrimonio());
        int ultimoIndice = getRowCount() - 1;
        fireTableRowsInserted(ultimoIndice, ultimoIndice);
    }

    /* Remove a linha especificada. */
    public void removeSite(int indiceLinha) {
        linhas.remove(indiceLinha);
        fireTableRowsDeleted(indiceLinha, indiceLinha);
    }

    /* Remove todos os registros. */
    public void limpar() {
        linhas.clear();
        tempoRestante.clear();
        fireTableDataChanged();
    }

    /* Verifica se este table model esta vazio. */
    public boolean isEmpty() {
        return linhas.isEmpty();
    }

    /* Adiciona uma lista de Cliente ao final dos registros. */
    public void addListaDeSites(List<Site> m) {
        // Pega o tamanho antigo da tabela.  
        int tamanhoAntigo = getRowCount();
        // Adiciona os registros.  
        linhas.addAll(m);
        fireTableRowsInserted(tamanhoAntigo, getRowCount() - 1);
    }

    public Object[][] getAllThings() {
        int row = getRowCount();
        int col = getColumnCount();
        Object[][] dados = new Object[row][4];
        for (int i = 0; i < row; i++) {
            //dados[i][0] = getValueAt2(i, 0);
            //dados[i][1] = getValueAt2(i, 1);
            //dados[i][2] = getValueAt2(i, 2);
            // estou usando o primeiro valueAt pois já me retorna o Id
            dados[i][3] = getValueAt(i, 0);
        }
        return dados;
    }

    /* public Integer getIdInto(int row){
       //Integer id = getValueAt3(row, 0);
       return id;
   }*/
    public List<Site> fillingRows() {
        try {

            linhas = siteJpaController.findSiteEntities();

            linhas.stream().forEach((linha) -> {

                if (getTempoRestante(linha.getCodSite()) > 0) {
                    setTempoRestante(linha.getCodSite(), linha.getIntervaloColeta());
                } else {
                    tempoRestante.add(new Temporizador(linha.getCodSite(), linha.getIntervaloColeta()));
                }
            }); //addListaDeSaidas(linhas);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao encher o table:" + e);
        }
        return linhas;
    }

    public void setStatus(int codSite, boolean status) {

        for (Site site : linhas) {
            if (site.getCodSite() == codSite) {
                site.setStatus(status);
            }
        }

    }

    public void sorter(JTable table, final JTextField filterText, SitesUsados model) {
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        table.setPreferredScrollableViewportSize(new Dimension(785, 220));
        table.setFillsViewportHeight(true);
        //setando o tamanho da coluna descrição,OBS
        table.getColumnModel().getColumn(1).setPreferredWidth(50);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        //For the purposes of this example, better to have a single
        //selection.
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //Whenever filterText changes, invoke newFilter.
        filterText.getDocument().addDocumentListener(
                new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                newFilter(filterText);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                newFilter(filterText);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                newFilter(filterText);
            }
        });
    }

    private void newFilter(JTextField filterText) {
        RowFilter<SitesUsados, Object> rf = null;
        //If current expression doesn't parse, don't update.
        try {
            //para alterar qual campo será filtrado na tabela basta alterar o índice abaixo!
            rf = RowFilter.regexFilter(filterText.getText(), 1);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
    }

    public static List<Site> getAllExits() {
        return linhas;
    }

    public String getIdIntoTheRow(JTable table) {
        int row = table.getSelectedRow();
        String pk = String.valueOf(table.getValueAt(row, 0));
        return pk;
    }

    /**
     * Método construído para atualizar algum registro que foi atualizado em
     * tempo de execução! Pego a linha e passo o objeto para poder atualizar o
     * model e a table.
     *
     * @param row
     * @param s
     */
    public void setValue(int row, Site s) {
        linhas.set(row, s);
        fireTableRowsUpdated(row, row);
    }

    public void requestFocusForFirstLine(JTable table) {
        table.addRowSelectionInterval(0, 0);
    }

}
