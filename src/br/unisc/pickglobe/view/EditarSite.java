/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.view;

import br.unisc.pickglobe.model.Site;
import br.unisc.pickglobe.view.actions.ActionSite;
import br.unisc.pickglobe.view.tabelas.ComboItem;
import br.unisc.pickglobe.view.tabelas.SitesUsados;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author arthurhoch
 */
public class EditarSite extends javax.swing.JFrame {

    private final ActionSite action;
    private final SitesUsados model;

    /**
     * Creates new form EditarSite
     *
     * @param model
     */
    public EditarSite(SitesUsados model) {
        this.action = new ActionSite();
        initComponents();
        initVariables();
        this.model = model;
    }

    public EditarSite() {
        this.action = new ActionSite();
        initComponents();
        initVariables();
        this.model = null;
    }

    private void initVariables() {
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jComboBoxPalavras = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jComboBoxSite = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jSlider1 = new javax.swing.JSlider();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jComboBoxExtensoes = new javax.swing.JComboBox<>();
        lblResultIntervalo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Editar site");
        setAlwaysOnTop(true);
        setLocationByPlatform(true);
        setResizable(false);

        jLabel1.setText("Site:");

        List<ComboItem> listaComboItem1 = action.getNomeListas();

        for(ComboItem item : listaComboItem1) {
            jComboBoxPalavras.addItem(item.toString());
        }

        jLabel2.setText("Lista de palavras: ");

        List<ComboItem> listaComboItem2 = action.getNomeSites();

        for(ComboItem item : listaComboItem2) {
            jComboBoxSite.addItem(item.toString());
        }
        jComboBoxSite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxSiteActionPerformed(evt);
            }
        });

        jLabel3.setText("Intervalo: ");

        jSlider1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                handlerMoved(evt);
            }
        });

        jScrollPane1.setViewportView(jTextPane1);
        jTextPane1.setText(jComboBoxSite.getItemAt(jComboBoxSite.getSelectedIndex()));

        jLabel7.setText("URL:");

        jButton1.setText("Cancelar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Salvar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Apagar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel8.setText("Lista de extensões:");

        List<ComboItem> listaComboItem3 = action.getNomeExtensoes(jComboBoxSite.getItemAt(jComboBoxSite.getSelectedIndex()));

        for(ComboItem item : listaComboItem3) {
            jComboBoxExtensoes.addItem(item.toString());
        }
        /*
        jComboBoxExtensoes.setModel(new javax.swing.DefaultComboBoxModel<>(action.getNomeExtensoes(jComboBoxSite.getItemAt(jComboBoxSite.getSelectedIndex()))));
        */

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblResultIntervalo)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jComboBoxPalavras, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jComboBoxSite, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(jLabel7)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jButton1)
                                            .addGap(18, 18, 18)
                                            .addComponent(jButton3)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jButton2))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel8)
                                            .addGap(3, 3, 3)
                                            .addComponent(jComboBoxExtensoes, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jSlider1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBoxSite, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBoxPalavras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jComboBoxExtensoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblResultIntervalo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1)
                    .addComponent(jButton3))
                .addGap(26, 26, 26))
        );

        jSlider1.setMinimum(1);
        jSlider1.setMaximum(172800);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String siteDeletar = jComboBoxSite.getItemAt(jComboBoxSite.getSelectedIndex());
        action.deletarSite(siteDeletar);
        jComboBoxSite.removeItemAt(jComboBoxSite.getSelectedIndex());
        model.limpar();
        model.fillingRows();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jComboBoxSiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxSiteActionPerformed
        jTextPane1.setText(jComboBoxSite.getItemAt(jComboBoxSite.getSelectedIndex()));
    }//GEN-LAST:event_jComboBoxSiteActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        String URL = jComboBoxSite.getItemAt(jComboBoxSite.getSelectedIndex());
        String novaURL = jTextPane1.getText();
        String novaListaPalavras = jComboBoxPalavras.getItemAt(jComboBoxPalavras.getSelectedIndex());
        String novaListaExtensoes = jComboBoxExtensoes.getItemAt(jComboBoxExtensoes.getSelectedIndex());
        int intervaloConsulta = jSlider1.getValue();

        if (!URL.isEmpty() && !novaURL.isEmpty() && !novaListaPalavras.isEmpty() && !novaListaExtensoes.isEmpty()) {
            Site s = action.atualizarSite(URL, novaURL, novaListaPalavras, novaListaExtensoes, intervaloConsulta);
            model.limpar();
            model.fillingRows();
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Verifique os campos");
        }


    }//GEN-LAST:event_jButton2ActionPerformed

    private void handlerMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_handlerMoved
        lblResultIntervalo.setText(String.valueOf(action.calculateTime(jSlider1.getValue())));
    }//GEN-LAST:event_handlerMoved

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EditarSite.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditarSite.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditarSite.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditarSite.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditarSite().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBoxExtensoes;
    private javax.swing.JComboBox<String> jComboBoxPalavras;
    private javax.swing.JComboBox<String> jComboBoxSite;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JLabel lblResultIntervalo;
    // End of variables declaration//GEN-END:variables
}
