package view;

import control.ControlerLixeira;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {
        // Cria a janela na Thread na thread do Swing
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("Financas Seu Jose");
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.setSize(1050, 650);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            Interface panelInicial = new Interface();
            panelInicial.setVisible(true);

            frame.add(panelInicial);
            
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    // Se o usuario clicar no botao de fechar a janela
                    int showConfirmDialog = JOptionPane.showConfirmDialog(frame, "O aplicativo sera fechado, deseja esvaziar a lixeira?");
                    if (showConfirmDialog == JOptionPane.YES_OPTION) {
                        try {
                            ControlerLixeira.deleteALlFinancaLixeira();
                        } catch (SQLException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    frame.dispose();
                }
            });
        });
    }
}
