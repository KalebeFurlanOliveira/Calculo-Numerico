import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class EliminacaoGauss extends JFrame {
    private JPanel matrixPanel;  
    private JPanel resultPanel;  
    private JTextField[][] matrixFields; 
    private JTextField[] resultFields;   
    private int size;
    public EliminacaoGauss() {
        setTitle("Eliminação de Gauss");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Tamanho da matriz:"));
        JTextField sizeField = new JTextField(5);
        JButton generateButton = new JButton("Gerar");
        topPanel.add(sizeField);
        topPanel.add(generateButton);
        add(topPanel, BorderLayout.NORTH);
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        matrixPanel = new JPanel();
        resultPanel = new JPanel();
        centerPanel.add(matrixPanel);
        centerPanel.add(resultPanel);
        add(centerPanel, BorderLayout.CENTER);
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    size = Integer.parseInt(sizeField.getText());
                    createMatrix(size);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Digite um número válido!");
                }
            }
        });
    }
    private void createMatrix(int n) {
        matrixPanel.removeAll();
        resultPanel.removeAll();
        matrixPanel.setLayout(new GridLayout(n, n, 5, 5));
        resultPanel.setLayout(new GridLayout(n, 1, 5, 5));
        matrixFields = new JTextField[n][n];
        resultFields = new JTextField[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrixFields[i][j] = new JTextField(3);
                matrixPanel.add(matrixFields[i][j]);
            }
        }
        for (int i = 0; i < n; i++) {
            resultFields[i] = new JTextField(3);
            resultPanel.add(resultFields[i]);
        }
        matrixPanel.revalidate();
        matrixPanel.repaint();
        resultPanel.revalidate();
        resultPanel.repaint();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EliminacaoGauss().setVisible(true);
        });
    }
}
