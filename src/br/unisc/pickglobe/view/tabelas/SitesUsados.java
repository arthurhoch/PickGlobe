/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.view.tabelas;

import br.unisc.pickglobe.controller.SiteJpaController;
import br.unisc.pickglobe.model.Site;
import br.unisc.pickglobe.view.actions.ActionSite;
import java.awt.Dimension;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class SitesUsados extends AbstractTableModel {

    private final ActionSite action;
    private final SiteJpaController siteJpaController;
    private static List<Site> linhas;

    private final String[] colunas = {"Site", "Lista palavras", "Intervalo", "Habilitado"};
    private TableRowSorter<SitesUsados> sorter;

    private final FilaExecucao filaExecucao;

    public SitesUsados(FilaExecucao filaExecucao) {
        this.action = new ActionSite();
        this.siteJpaController = new SiteJpaController(action.getEmf());
        this.filaExecucao = filaExecucao;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == colunas.length - 1) {
            return Boolean.class;
        }
        return super.getColumnClass(columnIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == colunas.length - 1;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        try {
            boolean status = (boolean) aValue;
            Site site = linhas.get(rowIndex);
            site.setStatus(status);
            siteJpaController.edit(site);
            filaExecucao.setStatus(site.getCodSite(), status);
        } catch (Exception ex) {
            Logger.getLogger(SitesUsados.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        Site site = linhas.get(rowIndex);

        switch (columnIndex) {

            case 0:
                return site.getUrl();
            case 1:
                return site.getCodListaPalavras().getNomeLista();
            case 2:
                return action.calculateTime(site.getIntervaloColeta());
            case 3:
                return site.getStatus();
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }

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
            //addListaDeSaidas(linhas);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao encher o table:" + e);
        }
        return linhas;
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
     */
    public void setValue(int row, Site s) {
        linhas.set(row, s);
        fireTableRowsUpdated(row, row);
    }

    public void requestFocusForFirstLine(JTable table) {
        table.addRowSelectionInterval(0, 0);
    }

}
