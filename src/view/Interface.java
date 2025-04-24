/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import Util.DateUtil;
import control.ControleFinancas;
import java.awt.Color;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Financa;

/**
 *
 * @author Sismaria
 */
public class Interface extends javax.swing.JPanel {

    private double ganho;
    private double gasto;
    private double diferenca;

    public Interface() {
        initComponents();
        contemFinanca(false);

        this.ganho = 0.0;
        this.gasto = 0.0;
        this.diferenca = 0.0;

        if (((DefaultTableModel) jTable.getModel()).getRowCount() != 0) {
            contemFinanca(true);
        }

        jTable.getSelectionModel().addListSelectionListener((e) -> {
            int row = jTable.getSelectedRow();

            if (row != -1) {
                jTextFieldNome.setText(jTable.getValueAt(row, 0).toString());
                jTextFieldClassificacao.setText(jTable.getValueAt(row, 1).toString());
                jTextFieldValor.setText(jTable.getValueAt(row, 2).toString());
                jFormattedTextFieldDataEntrada.setText(jTable.getValueAt(row, 3).toString());
                if (jTable.getValueAt(row, 5).toString().equalsIgnoreCase("ganho")) {
                    jToggleButtonGasto.setSelected(false);
                    jToggleButtonGanho.setSelected(true);
                }
                if (jTable.getValueAt(row, 5).toString().equalsIgnoreCase("gasto")) {
                    jToggleButtonGasto.setSelected(true);
                    jToggleButtonGanho.setSelected(false);
                } else {
                    jToggleButtonGasto.setSelected(false);
                    jToggleButtonGanho.setSelected(false);
                }
            }
        });
    }

    // Eh chamado caso não haja itens no banco de dados
    public final void contemFinanca(boolean state) {
        jTable.setVisible(state);
        jScrollPane2.setVisible(state);
        jLabelRecebido.setVisible(state);
        jLabelGastos.setVisible(state);
        jLabelDiferenca.setVisible(state);
        jLabelDinheiroRecebido.setVisible(state);
        jLabelDinheiroGastos.setVisible(state);
        jLabelDinheiroDiferenca.setVisible(state);
        jLabelSemFinancas.setVisible(!state);
    }

    public String getTextNome() {
        return jTextFieldNome.getText();
    }

    public String getTextClassificacao() {
        return jTextFieldClassificacao.getText();
    }

    public String getTextValor() {
        return jTextFieldValor.getText();
    }

    public String getTextDataEntrada() {
        return jFormattedTextFieldDataEntrada.getText();
    }

    public String getTextDataDe() {
        return jFormattedTextFieldDe.getText();
    }

    public String getTextDataAte() {
        return jFormattedTextFieldAte.getText();
    }

    public String getTextTipoTransacao() {
        if (jToggleButtonGanho.isSelected()) {
            return "ganho";
        }
        if (jToggleButtonGasto.isSelected()) {
            return "gasto";
        }
        return "não informado";
    }

    public void addGanho(double value) {
        if (value < 0) {
            return;
        }
        ganho += value;
        diferenca += value;
        jLabelDinheiroRecebido.setText(String.valueOf(ganho));
        jLabelDinheiroDiferenca.setText(String.valueOf(diferenca));
    }

    public void addGasto(double value) {
        if (value < 0) {
            return;
        }
        gasto += value;
        diferenca -= value;
        jLabelDinheiroGastos.setText(String.valueOf(gasto));
        jLabelDinheiroDiferenca.setText(String.valueOf(diferenca));
    }

    private void setDiferencaColor() {
        if (diferenca > 0) {
            jLabelDinheiroDiferenca.setForeground(Color.GREEN);
        } else if (diferenca < 0) {
            jLabelDinheiroDiferenca.setForeground(Color.RED);
        } else {
            jLabelDinheiroDiferenca.setForeground(Color.BLACK);
        }
    }

    private void atualizarList(String data) {
        Date date = Util.DateUtil.getDateSQL(data);
        if (date == null) {
            return;
        }
        atualizarList(date);
    }

    private void atualizarList(Date date) {
        List<Financa> list;
        try {
            list = dao.FinancaDAO.getFinancas(date);
        } catch (SQLException ex) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        DefaultTableModel dtm = (DefaultTableModel) jTable.getModel();
        dtm.setRowCount(0);

        ganho = 0.0;
        gasto = 0.0;
        diferenca = 0.0;

        for (Financa f : list) {
            dtm.addRow(new Object[]{f.getNome(),
                f.getClassificacao(),
                "%.2f0".formatted(f.getValor()),
                DateUtil.getDate(f.getDataFinanca()), DateUtil.getDate(f.getDataCadastro()),
                f.getTipoTransacao()});

            if (f.getTipoTransacao().equalsIgnoreCase("ganho")) {
                addGanho(f.getValor());
            }
            if (f.getTipoTransacao().equalsIgnoreCase("gasto")) {
                addGasto(f.getValor());
            }
        }

        setDiferencaColor();

        contemFinanca(!list.isEmpty());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GanhoGasto = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabelFinanca = new javax.swing.JLabel();
        jButtonMesAtual = new javax.swing.JButton();
        jLabelDe = new javax.swing.JLabel();
        jLabelAte = new javax.swing.JLabel();
        jTextFieldNome = new javax.swing.JTextField();
        jLabelNome = new javax.swing.JLabel();
        jLabelClassificacao = new javax.swing.JLabel();
        jLabelValor = new javax.swing.JLabel();
        jLabelDataEntrada = new javax.swing.JLabel();
        jTextFieldClassificacao = new javax.swing.JTextField();
        jTextFieldValor = new javax.swing.JTextField();
        jToggleButtonGanho = new javax.swing.JToggleButton();
        jToggleButtonGasto = new javax.swing.JToggleButton();
        jButton1 = new javax.swing.JButton();
        jButtonX = new javax.swing.JButton();
        jLabelRecebido = new javax.swing.JLabel();
        jLabelGastos = new javax.swing.JLabel();
        jLabelDiferenca = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        jFormattedTextFieldDataEntrada = new javax.swing.JFormattedTextField();
        jFormattedTextFieldDe = new javax.swing.JFormattedTextField();
        jFormattedTextFieldAte = new javax.swing.JFormattedTextField();
        jLabelDinheiroRecebido = new javax.swing.JLabel();
        jLabelDinheiroGastos = new javax.swing.JLabel();
        jLabelDinheiroDiferenca = new javax.swing.JLabel();
        jLabelSemFinancas = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelFinanca.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabelFinanca.setForeground(new java.awt.Color(0, 0, 0));
        jLabelFinanca.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelFinanca.setText("Finanças Anual Seu José");
        jPanel1.add(jLabelFinanca, new org.netbeans.lib.awtextra.AbsoluteConstraints(381, 9, -1, -1));

        jButtonMesAtual.setBackground(new java.awt.Color(153, 204, 0));
        jButtonMesAtual.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonMesAtual.setForeground(new java.awt.Color(0, 0, 0));
        jButtonMesAtual.setText("MÊS ATUAL");
        jButtonMesAtual.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButtonMesAtual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMesAtualActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonMesAtual, new org.netbeans.lib.awtextra.AbsoluteConstraints(381, 61, 107, 38));

        jLabelDe.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelDe.setForeground(new java.awt.Color(0, 0, 0));
        jLabelDe.setText("De : ");
        jPanel1.add(jLabelDe, new org.netbeans.lib.awtextra.AbsoluteConstraints(505, 65, -1, -1));

        jLabelAte.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelAte.setForeground(new java.awt.Color(0, 0, 0));
        jLabelAte.setText("Até : ");
        jPanel1.add(jLabelAte, new org.netbeans.lib.awtextra.AbsoluteConstraints(723, 65, -1, -1));

        jTextFieldNome.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldNome.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jTextFieldNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(137, 162, 217, 38));

        jLabelNome.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelNome.setForeground(new java.awt.Color(0, 0, 0));
        jLabelNome.setText("Nome :");
        jPanel1.add(jLabelNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(69, 166, -1, -1));

        jLabelClassificacao.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelClassificacao.setForeground(new java.awt.Color(0, 0, 0));
        jLabelClassificacao.setText("Classificação : ");
        jPanel1.add(jLabelClassificacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(69, 228, -1, -1));

        jLabelValor.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelValor.setForeground(new java.awt.Color(0, 0, 0));
        jLabelValor.setText("Valor : ");
        jPanel1.add(jLabelValor, new org.netbeans.lib.awtextra.AbsoluteConstraints(69, 284, -1, -1));

        jLabelDataEntrada.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelDataEntrada.setForeground(new java.awt.Color(0, 0, 0));
        jLabelDataEntrada.setText("Data Entrada :");
        jPanel1.add(jLabelDataEntrada, new org.netbeans.lib.awtextra.AbsoluteConstraints(72, 347, -1, -1));

        jTextFieldClassificacao.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldClassificacao.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jTextFieldClassificacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 224, 144, 38));

        jTextFieldValor.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldValor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jTextFieldValor, new org.netbeans.lib.awtextra.AbsoluteConstraints(135, 280, 220, 38));

        jToggleButtonGanho.setBackground(new java.awt.Color(51, 204, 0));
        GanhoGasto.add(jToggleButtonGanho);
        jToggleButtonGanho.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jToggleButtonGanho.setForeground(new java.awt.Color(0, 0, 0));
        jToggleButtonGanho.setText("Ganho(+)");
        jToggleButtonGanho.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jToggleButtonGanho, new org.netbeans.lib.awtextra.AbsoluteConstraints(57, 430, 117, 50));

        jToggleButtonGasto.setBackground(new java.awt.Color(255, 0, 0));
        GanhoGasto.add(jToggleButtonGasto);
        jToggleButtonGasto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jToggleButtonGasto.setForeground(new java.awt.Color(0, 0, 0));
        jToggleButtonGasto.setText("Gasto(-)");
        jToggleButtonGasto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jToggleButtonGasto, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 430, 116, 50));

        jButton1.setBackground(new java.awt.Color(0, 153, 153));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 0));
        jButton1.setText("Cadastrar");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 500, 239, 53));

        jButtonX.setBackground(new java.awt.Color(255, 255, 255));
        jButtonX.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButtonX.setForeground(new java.awt.Color(0, 0, 0));
        jButtonX.setText("X");
        jButtonX.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204), 2));
        jPanel1.add(jButtonX, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 270, 69, 54));

        jLabelRecebido.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelRecebido.setForeground(new java.awt.Color(0, 0, 0));
        jLabelRecebido.setText("Recebido : R$");
        jPanel1.add(jLabelRecebido, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 560, -1, -1));

        jLabelGastos.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelGastos.setForeground(new java.awt.Color(0, 0, 0));
        jLabelGastos.setText("Gastos : R$");
        jPanel1.add(jLabelGastos, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 560, -1, -1));

        jLabelDiferenca.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelDiferenca.setForeground(new java.awt.Color(0, 0, 0));
        jLabelDiferenca.setText("Diferença : R$");
        jPanel1.add(jLabelDiferenca, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 560, -1, -1));

        jTable.setBackground(new java.awt.Color(255, 255, 255));
        jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "Classificação", "Valor", "Data", "Cadastro", "Tipo"
            }
        ));
        jTable.setToolTipText("");
        jTable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(jTable);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 160, 550, 340));

        jFormattedTextFieldDataEntrada.setBackground(new java.awt.Color(255, 255, 255));
        jFormattedTextFieldDataEntrada.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        try {
            jFormattedTextFieldDataEntrada.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedTextFieldDataEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextFieldDataEntradaActionPerformed(evt);
            }
        });
        jPanel1.add(jFormattedTextFieldDataEntrada, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 340, 150, 40));

        jFormattedTextFieldDe.setBackground(new java.awt.Color(255, 255, 255));
        jFormattedTextFieldDe.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        try {
            jFormattedTextFieldDe.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jPanel1.add(jFormattedTextFieldDe, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 60, 150, 40));

        jFormattedTextFieldAte.setBackground(new java.awt.Color(255, 255, 255));
        jFormattedTextFieldAte.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        try {
            jFormattedTextFieldAte.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jPanel1.add(jFormattedTextFieldAte, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 60, 150, 40));

        jLabelDinheiroRecebido.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelDinheiroRecebido.setForeground(new java.awt.Color(0, 255, 0));
        jLabelDinheiroRecebido.setText("0000");
        jPanel1.add(jLabelDinheiroRecebido, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 560, -1, -1));

        jLabelDinheiroGastos.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelDinheiroGastos.setForeground(new java.awt.Color(255, 0, 0));
        jLabelDinheiroGastos.setText("0000");
        jPanel1.add(jLabelDinheiroGastos, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 560, -1, -1));

        jLabelDinheiroDiferenca.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelDinheiroDiferenca.setForeground(new java.awt.Color(0, 0, 0));
        jLabelDinheiroDiferenca.setText("0000");
        jPanel1.add(jLabelDinheiroDiferenca, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 560, -1, -1));

        jLabelSemFinancas.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelSemFinancas.setForeground(new java.awt.Color(0, 0, 0));
        jLabelSemFinancas.setText("Não possui nenhuma finança cadastrada.");
        jPanel1.add(jLabelSemFinancas, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 290, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jFormattedTextFieldDataEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextFieldDataEntradaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextFieldDataEntradaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String nome = getTextNome();
        String classificacao = getTextClassificacao();
        float valor = Float.parseFloat(getTextValor());
        Date dataEntrada = Util.DateUtil.getDateSQL(getTextDataEntrada());
        Date dataCadastro = Util.DateUtil.getDateSQL(System.currentTimeMillis());
        String tipoTransacao = getTextTipoTransacao();

        Financa financa = new Financa(nome, classificacao, valor, dataEntrada, dataCadastro, tipoTransacao);

        if (ControleFinancas.criarFinanca(financa)) {
            JOptionPane.showMessageDialog(this, "Financa criada", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
            atualizarList(dataEntrada);
        } else {
            JOptionPane.showMessageDialog(this, "Não foi possível adicionar uma financa", "AVISO", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButtonMesAtualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMesAtualActionPerformed
        atualizarList(DateUtil.getDateSQL(System.currentTimeMillis()));
    }//GEN-LAST:event_jButtonMesAtualActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup GanhoGasto;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonMesAtual;
    private javax.swing.JButton jButtonX;
    private javax.swing.JFormattedTextField jFormattedTextFieldAte;
    private javax.swing.JFormattedTextField jFormattedTextFieldDataEntrada;
    private javax.swing.JFormattedTextField jFormattedTextFieldDe;
    private javax.swing.JLabel jLabelAte;
    private javax.swing.JLabel jLabelClassificacao;
    private javax.swing.JLabel jLabelDataEntrada;
    private javax.swing.JLabel jLabelDe;
    private javax.swing.JLabel jLabelDiferenca;
    private javax.swing.JLabel jLabelDinheiroDiferenca;
    private javax.swing.JLabel jLabelDinheiroGastos;
    private javax.swing.JLabel jLabelDinheiroRecebido;
    private javax.swing.JLabel jLabelFinanca;
    private javax.swing.JLabel jLabelGastos;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelRecebido;
    private javax.swing.JLabel jLabelSemFinancas;
    private javax.swing.JLabel jLabelValor;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable;
    private javax.swing.JTextField jTextFieldClassificacao;
    private javax.swing.JTextField jTextFieldNome;
    private javax.swing.JTextField jTextFieldValor;
    private javax.swing.JToggleButton jToggleButtonGanho;
    private javax.swing.JToggleButton jToggleButtonGasto;
    // End of variables declaration//GEN-END:variables
}
