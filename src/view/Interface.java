package view;

import Util.DateUtil;
import Util.FilesUtil;
import control.ControleFinancas;
import control.ControlerLixeira;
import java.awt.Color;
import java.awt.Component;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import model.Financa;

/**
 *
 * @author Sismaria
 */
public class Interface extends javax.swing.JPanel {

    private float ganho; // Ganho feito pelo seu Jose
    private float gasto; // Gasto feito pelo seu Jose
    private float diferenca; // Diferenca entre o ganho e gasto
    private int row; // Linha selecionada na tabela

    public Interface() {
        initComponents();
        contemFinanca(false);

        this.ganho = 0.0f;
        this.gasto = 0.0f;
        this.diferenca = 0.0f;

        // Se contem itens, ativa tabelas e labels
        if (((DefaultTableModel) jTable.getModel()).getRowCount() != 0) {
            contemFinanca(true);
        }

        // Cria um listener para sempre que selecionar uma linha, atribui os valores aos respectivos campos para poderem ser alterados
        jTable.getSelectionModel().addListSelectionListener((e) -> {
            row = jTable.getSelectedRow();

            if (row != -1) {
                jTextFieldNome.setText(jTable.getValueAt(row, 0).toString());
                jTextFieldClassificacao.setText(jTable.getValueAt(row, 1).toString());
                jTextFieldValor.setText(jTable.getValueAt(row, 2).toString());
                jFormattedTextFieldDataEntrada.setText(DateUtil.formattedDate(jTable.getValueAt(row, 3).toString()));
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

    // Altera entre os estados de ter itens na tabela, state true, e não ter, state false
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

    private void atualizarList(Date date) {
        List<Financa> list;
        try {
            list = ControleFinancas.getFinancesByDate(date);
        } catch (SQLException ex) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        DefaultTableModel dtm = (DefaultTableModel) jTable.getModel();
        dtm.setRowCount(0);

        ganho = 0.0f;
        gasto = 0.0f;
        diferenca = 0.0f;

        for (Financa f : list) {
            dtm.addRow(new Object[]{f.getNome(),
                f.getClassificacao(),
                "%.2f".formatted(f.getValor()),
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

    private void atualizarList(Date date1, Date date2) {
        List<Financa> list;
        try {
            list = ControleFinancas.getFinancasByDates(date1, date2);
        } catch (SQLException ex) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        DefaultTableModel dtm = (DefaultTableModel) jTable.getModel();
        dtm.setRowCount(0);

        ganho = 0.0f;
        gasto = 0.0f;
        diferenca = 0.0f;

        for (Financa f : list) {
            dtm.addRow(new Object[]{f.getNome(),
                f.getClassificacao(),
                "%.2f".formatted(f.getValor()),
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
        jButtonLixeira = new javax.swing.JButton();
        jLabelRecebido = new javax.swing.JLabel();
        jLabelGastos = new javax.swing.JLabel();
        jLabelDiferenca = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable =     new javax.swing.JTable() {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                if (jTable.getValueAt(row, 5).toString().equalsIgnoreCase("ganho")) c.setBackground(new Color(0.0f, 0.80f, 0.0f, 0.5f));
                if (jTable.getValueAt(row, 5).toString().equalsIgnoreCase("gasto")) c.setBackground(new Color(0.80f, 0.0f, 0.0f, 0.5f));
                if (jTable.getSelectedRow() == row) c.setBackground(new Color(0.0f, 0.0f, 0.80f, 0.5f));
                return c;
            }
        };
        ;
        jFormattedTextFieldDataEntrada = new javax.swing.JFormattedTextField();
        jFormattedTextFieldDe = new javax.swing.JFormattedTextField();
        jFormattedTextFieldAte = new javax.swing.JFormattedTextField();
        jLabelDinheiroRecebido = new javax.swing.JLabel();
        jLabelDinheiroGastos = new javax.swing.JLabel();
        jLabelDinheiroDiferenca = new javax.swing.JLabel();
        jLabelSemFinancas = new javax.swing.JLabel();
        jButtonRecuperar = new javax.swing.JButton();
        jButtonX1 = new javax.swing.JButton();

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
        jButtonMesAtual.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
        jTextFieldValor.setMargin(new java.awt.Insets(2, 20, 2, 6));
        jPanel1.add(jTextFieldValor, new org.netbeans.lib.awtextra.AbsoluteConstraints(135, 280, 220, 38));

        jToggleButtonGanho.setBackground(new java.awt.Color(51, 204, 0));
        GanhoGasto.add(jToggleButtonGanho);
        jToggleButtonGanho.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jToggleButtonGanho.setForeground(new java.awt.Color(0, 0, 0));
        jToggleButtonGanho.setText("Ganho(+)");
        jToggleButtonGanho.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jToggleButtonGanho.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(jToggleButtonGanho, new org.netbeans.lib.awtextra.AbsoluteConstraints(57, 430, 117, 50));

        jToggleButtonGasto.setBackground(new java.awt.Color(255, 0, 0));
        GanhoGasto.add(jToggleButtonGasto);
        jToggleButtonGasto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jToggleButtonGasto.setForeground(new java.awt.Color(0, 0, 0));
        jToggleButtonGasto.setText("Gasto(-)");
        jToggleButtonGasto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jToggleButtonGasto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(jToggleButtonGasto, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 430, 116, 50));

        jButton1.setBackground(new java.awt.Color(0, 153, 153));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 0));
        jButton1.setText("Cadastrar");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 500, 239, 53));

        jButtonLixeira.setBackground(new java.awt.Color(255, 255, 255));
        jButtonLixeira.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButtonLixeira.setForeground(new java.awt.Color(0, 0, 0));
        jButtonLixeira.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/trash.png"))); // NOI18N
        jButtonLixeira.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204), 2));
        jButtonLixeira.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonLixeira.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLixeiraActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonLixeira, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 240, 60, 60));

        jLabelRecebido.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelRecebido.setForeground(new java.awt.Color(0, 0, 0));
        jLabelRecebido.setText("Recebido : R$");
        jPanel1.add(jLabelRecebido, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 510, -1, -1));

        jLabelGastos.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelGastos.setForeground(new java.awt.Color(0, 0, 0));
        jLabelGastos.setText("Gastos :      R$");
        jPanel1.add(jLabelGastos, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 530, -1, -1));

        jLabelDiferenca.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelDiferenca.setForeground(new java.awt.Color(0, 0, 0));
        jLabelDiferenca.setText("Diferença : R$");
        jPanel1.add(jLabelDiferenca, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 550, -1, -1));

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
        jTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
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
        jFormattedTextFieldDe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jFormattedTextFieldDeKeyReleased(evt);
            }
        });
        jPanel1.add(jFormattedTextFieldDe, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 60, 150, 40));

        jFormattedTextFieldAte.setBackground(new java.awt.Color(255, 255, 255));
        jFormattedTextFieldAte.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        try {
            jFormattedTextFieldAte.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedTextFieldAte.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jFormattedTextFieldAteKeyReleased(evt);
            }
        });
        jPanel1.add(jFormattedTextFieldAte, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 60, 150, 40));

        jLabelDinheiroRecebido.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelDinheiroRecebido.setForeground(new java.awt.Color(0, 255, 0));
        jLabelDinheiroRecebido.setText("0000");
        jPanel1.add(jLabelDinheiroRecebido, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 510, -1, -1));

        jLabelDinheiroGastos.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelDinheiroGastos.setForeground(new java.awt.Color(255, 0, 0));
        jLabelDinheiroGastos.setText("0000");
        jPanel1.add(jLabelDinheiroGastos, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 530, -1, -1));

        jLabelDinheiroDiferenca.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelDinheiroDiferenca.setForeground(new java.awt.Color(0, 0, 0));
        jLabelDinheiroDiferenca.setText("0000");
        jPanel1.add(jLabelDinheiroDiferenca, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 550, -1, -1));

        jLabelSemFinancas.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelSemFinancas.setForeground(new java.awt.Color(0, 0, 0));
        jLabelSemFinancas.setText("Não possui nenhuma finança cadastrada.");
        jPanel1.add(jLabelSemFinancas, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 290, -1, -1));

        jButtonRecuperar.setBackground(new java.awt.Color(255, 255, 255));
        jButtonRecuperar.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButtonRecuperar.setForeground(new java.awt.Color(0, 0, 0));
        jButtonRecuperar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/volta.png"))); // NOI18N
        jButtonRecuperar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204), 2));
        jButtonRecuperar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonRecuperar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRecuperarActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonRecuperar, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 310, 60, 60));

        jButtonX1.setBackground(new java.awt.Color(255, 255, 255));
        jButtonX1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButtonX1.setForeground(new java.awt.Color(0, 0, 0));
        jButtonX1.setText("Gerar extrato");
        jButtonX1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204), 2));
        jButtonX1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonX1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonX1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonX1, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 510, 210, 60));

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
        float valor = Float.parseFloat(getTextValor().replace(',', '.'));
        Date dataEntrada = DateUtil.getDateSQL(getTextDataEntrada());
        Date dataCadastro = DateUtil.getDateSQL(System.currentTimeMillis());
        String tipoTransacao = getTextTipoTransacao();

        Financa financa = ControleFinancas.criarFinanca(nome, classificacao, valor, dataEntrada, dataCadastro, tipoTransacao);

        if (ControleFinancas.verificarFinanca(financa)) {
            try {
                ControleFinancas.addFinanca(financa);
                JOptionPane.showMessageDialog(this, "Financa criada", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
                atualizarList(dataEntrada);
            } catch (SQLException ex) {
                Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Não foi possível adicionar uma financa", "AVISO", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButtonMesAtualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMesAtualActionPerformed
        atualizarList(DateUtil.getDateSQL(System.currentTimeMillis()));
    }//GEN-LAST:event_jButtonMesAtualActionPerformed

    private void jButtonLixeiraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLixeiraActionPerformed
        if (row == -1) {
            return;
        }

        String nome = jTable.getValueAt(row, 0).toString();
        String classificacao = jTable.getValueAt(row, 1).toString();
        float valor = Float.parseFloat(jTable.getValueAt(row, 2).toString().replace(',', '.'));
        Date dataEntrada = DateUtil.getDateSQL(jTable.getValueAt(row, 3).toString());
        Date dataCadastro = DateUtil.getDateSQL(jTable.getValueAt(row, 4).toString());
        String tipoTransacao = jTable.getValueAt(row, 5).toString();

        Financa financa = ControleFinancas.criarFinanca(nome, classificacao, valor, dataEntrada, dataCadastro, tipoTransacao);
        try {
            String message = "Tem certeza que deseja mover para a lixeira: \n"
                    + nome + ", "
                    + classificacao + ", "
                    + valor + ", "
                    + DateUtil.getDate(dataEntrada) + ", "
                    + DateUtil.getDate(dataCadastro) + ", "
                    + tipoTransacao;
            int showConfirmDialog = JOptionPane.showConfirmDialog(this, message, "WARNING", JOptionPane.WARNING_MESSAGE);
            if (showConfirmDialog == JOptionPane.OK_OPTION) {
                ControleFinancas.deleteFinanca(financa);
                JOptionPane.showMessageDialog(this, "Movido para a lixeira com sucesso", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
                atualizarList(dataEntrada);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButtonLixeiraActionPerformed

    private void jFormattedTextFieldDeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextFieldDeKeyReleased
        String dateDe = jFormattedTextFieldDe.getText();
        if (DateUtil.isDateFormatted(dateDe)) {
            String dateAte;
            if (DateUtil.isDateFormatted(jFormattedTextFieldAte.getText())) {
                dateAte = jFormattedTextFieldAte.getText();
            } else {
                dateAte = "31/12/9999";
            }
            atualizarList(DateUtil.getDateSQL(dateDe), DateUtil.getDateSQL(dateAte));
        }
    }//GEN-LAST:event_jFormattedTextFieldDeKeyReleased

    private void jFormattedTextFieldAteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextFieldAteKeyReleased
        String dateAte = jFormattedTextFieldAte.getText();
        if (DateUtil.isDateFormatted(dateAte)) {
            String dateDe;
            if (DateUtil.isDateFormatted(jFormattedTextFieldDe.getText())) {
                dateDe = jFormattedTextFieldDe.getText();
            } else {
                dateDe = "00/00/0000";
            }
            atualizarList(DateUtil.getDateSQL(dateDe), DateUtil.getDateSQL(dateAte));
        }
    }//GEN-LAST:event_jFormattedTextFieldAteKeyReleased

    private void jButtonRecuperarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRecuperarActionPerformed
        Financa financa = null;
        try {
            financa = ControlerLixeira.getFirstItemLixeira();

            if (financa == null) {
                JOptionPane.showMessageDialog(this, "Lixeira está vazia", "WARNING", JOptionPane.WARNING_MESSAGE);
                return;
            }
            ControleFinancas.addFinanca(financa);
            ControlerLixeira.deleteFinancaLixeira(financa);
            atualizarList(financa.getDataFinanca());
        } catch (SQLException ex) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonRecuperarActionPerformed

    private void jButtonX1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonX1ActionPerformed
        try {
            String data1 = jFormattedTextFieldDe.getText();
            String data2 = jFormattedTextFieldAte.getText();
            List<Financa> list = null;
            if (DateUtil.isDateFormatted(data1) && DateUtil.isDateFormatted(data2)) {
                list = ControleFinancas.getFinancasByDates(DateUtil.getDateSQL(data1), DateUtil.getDateSQL(data2));
            } else {
                JOptionPane.showMessageDialog(this, "Defina o intervalo da data acima.", "WARNING", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            FilesUtil.gerarExtrato(list, jLabelDinheiroRecebido.getText(), jLabelDinheiroGastos.getText(), jLabelDinheiroDiferenca.getText());
        } catch (SQLException | IOException ex) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonX1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup GanhoGasto;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonLixeira;
    private javax.swing.JButton jButtonMesAtual;
    private javax.swing.JButton jButtonRecuperar;
    private javax.swing.JButton jButtonX1;
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
