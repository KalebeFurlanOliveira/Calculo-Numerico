import javax.swing.*;
import java.awt.*;

public class PagMetodoGauss extends JFrame {

    public PagMetodoGauss() {
        setTitle("Escolha o Método");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Escolha o método que deseja", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(titulo, BorderLayout.NORTH);

        JButton gaussButton = new JButton("Eliminação de Gauss");
        gaussButton.setFont(new Font("Arial", Font.PLAIN, 16));

        gaussButton.addActionListener(e -> 
            JOptionPane.showMessageDialog(null, "Aqui você chamaria o método de Eliminação de Gauss!")
        );

        JPanel painelBotao = new JPanel();
        painelBotao.add(gaussButton);
        add(painelBotao, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PagMetodoGauss::new);
    }
}
