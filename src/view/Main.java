package view;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("Financas Seu Jose");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setSize(1050, 650);
            frame.setVisible(true);

            Interface panelInicial = new Interface();
            panelInicial.setVisible(true);

            frame.add(panelInicial);
        });
    }
}
