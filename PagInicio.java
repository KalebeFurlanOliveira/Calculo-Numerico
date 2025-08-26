import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PagInicio extends JFrame {

    public PagInicio() {
        setTitle("Aulas Práticas de Cálculo Numérico");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        JLabel titulo = new JLabel("AULAS PRÁTICAS DE CÁLCULO NUMÉRICO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(titulo, BorderLayout.NORTH);
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(2, 1, 10, 10));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(20, 100, 50, 100));
        JButton bisseccaoButton = new JButton("Método de bissecção para zeros de função");
        JButton sistemasButton = new JButton("Método para resolução de sistemas lineares");
        bisseccaoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            new BisseccaoGUI();
        }
        });
        sistemasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            new PagMetodoGauss();
            }
        });
        painelBotoes.add(bisseccaoButton);
        painelBotoes.add(sistemasButton);
        add(painelBotoes, BorderLayout.CENTER);
        setVisible(true);
    }
    public static void main(String[] args) { 
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PagInicio();
            }
        });
    }
}
