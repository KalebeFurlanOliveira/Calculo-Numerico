import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BisseccaoGUI extends JFrame {

    // Função fixa: f(x) = x³ - x - 2
    private double f(double x) {
        return Math.pow(x, 3) - x - 2;
    }

    public BisseccaoGUI() {
        setTitle("Método da Bissecção");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Título
        JLabel titulo = new JLabel("Método da Bissecção", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(titulo, BorderLayout.NORTH);

        // Painel de entrada
        JPanel painelEntrada = new JPanel(new GridLayout(4, 2, 10, 10));
        painelEntrada.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        JTextField campoA = new JTextField();
        JTextField campoB = new JTextField();
        JTextField campoTol = new JTextField("0.0001");
        JTextField campoIter = new JTextField("100");

        painelEntrada.add(new JLabel("Limite inferior (a):"));
        painelEntrada.add(campoA);
        painelEntrada.add(new JLabel("Limite superior (b):"));
        painelEntrada.add(campoB);
        painelEntrada.add(new JLabel("Tolerância (ε):"));
        painelEntrada.add(campoTol);
        painelEntrada.add(new JLabel("Máximo de iterações:"));
        painelEntrada.add(campoIter);

        add(painelEntrada, BorderLayout.CENTER);

        // Painel de saída
        JTextArea resultado = new JTextArea();
        resultado.setEditable(false);
        resultado.setBorder(BorderFactory.createTitledBorder("Resultado"));
        add(new JScrollPane(resultado), BorderLayout.SOUTH);

        // Botão
        JButton calcular = new JButton("Calcular");
        calcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double a = Double.parseDouble(campoA.getText());
                    double b = Double.parseDouble(campoB.getText());
                    double tol = Double.parseDouble(campoTol.getText());
                    int maxIter = Integer.parseInt(campoIter.getText());

                    if (f(a) * f(b) >= 0) {
                        resultado.setText("Erro: f(a) e f(b) devem ter sinais opostos!");
                        return;
                    }

                    double c = a;
                    int iter = 0;

                    // critério de parada corrigido -> (b - a) < tol
                    while ((b - a) > tol && iter < maxIter) {
                        c = (a + b) / 2;
                        if (f(c) == 0.0) break;
                        else if (f(a) * f(c) < 0)
                            b = c;
                        else
                            a = c;
                        iter++;
                    }

                    resultado.setText("Zero aproximado: " + c + "\n"
                            + "Iterações: " + iter + "\n"
                            + "f(c) = " + f(c));

                } catch (Exception ex) {
                    resultado.setText("Entrada inválida!");
                }
            }
        });

        add(calcular, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BisseccaoGUI());
    }
}
