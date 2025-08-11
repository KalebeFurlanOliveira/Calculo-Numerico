import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * BisectionApp.java
 * 
 * Aplicativo Swing que executa o método da bissecção para f(x) = x^3 - 9x + 3
 * - Permite inserir [a,b], tolerância e máximo de iterações
 * - Mostra cada iteração (intervalo, f(a), f(b), f(m), m)
 * - Mantém um histórico (lista) de execuções realizadas ("modelos")
 * - Permite salvar o relatório da execução em arquivo texto
 *
 * Como compilar:
 *   javac BisectionApp.java
 *   java BisectionApp
 */
public class BisectionApp extends JFrame {
    private JTextField fieldA;
    private JTextField fieldB;
    private JTextField fieldTol;
    private JTextField fieldMaxIter;
    private JButton btnRun;
    private JButton btnSave;
    private JTextArea textArea;
    private DefaultListModel<String> runsModel;
    private JList<String> runsList;
    private List<RunResult> runResults;

    private static final DecimalFormat DF = new DecimalFormat("0.######");

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BisectionApp app = new BisectionApp();
            app.setVisible(true);
        });
    }

    public BisectionApp() {
        setTitle("Bisection — f(x)=x^3 - 9x + 3");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JPanel main = new JPanel(new BorderLayout(8, 8));
        main.setBorder(new EmptyBorder(8, 8, 8, 8));

        // Left: controls + runs list
        JPanel left = new JPanel(new BorderLayout(8, 8));
        left.setPreferredSize(new Dimension(320, 0));

        JPanel controls = new JPanel();
        controls.setLayout(new GridLayout(0, 2, 6, 6));
        controls.setBorder(BorderFactory.createTitledBorder("Entradas"));

        controls.add(new JLabel("a:"));
        fieldA = new JTextField("-4");
        controls.add(fieldA);

        controls.add(new JLabel("b:"));
        fieldB = new JTextField("4");
        controls.add(fieldB);

        controls.add(new JLabel("Tolerância (ex: 1E-6):"));
        fieldTol = new JTextField("1E-6");
        controls.add(fieldTol);

        controls.add(new JLabel("Máx de iterações:"));
        fieldMaxIter = new JTextField("100");
        controls.add(fieldMaxIter);

        left.add(controls, BorderLayout.NORTH);

        btnRun = new JButton("Executar Bissecção");
        btnRun.addActionListener(e -> runBisectionAction());

        btnSave = new JButton("Salvar relatório");
        btnSave.addActionListener(e -> saveReportAction());
        btnSave.setEnabled(false);

        JPanel buttons = new JPanel(new GridLayout(1, 2, 8, 8));
        buttons.add(btnRun);
        buttons.add(btnSave);
        left.add(buttons, BorderLayout.CENTER);

        runsModel = new DefaultListModel<>();
        runsList = new JList<>(runsModel);
        runsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        runsList.addListSelectionListener(evt -> showSelectedRun());

        JPanel runsPanel = new JPanel(new BorderLayout());
        runsPanel.setBorder(BorderFactory.createTitledBorder("Execuções (modelos)"));
        runsPanel.add(new JScrollPane(runsList), BorderLayout.CENTER);
        left.add(runsPanel, BorderLayout.SOUTH);

        // Center: detailed output
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane centerScroll = new JScrollPane(textArea);
        centerScroll.setBorder(BorderFactory.createTitledBorder("Detalhes da execução"));

        main.add(left, BorderLayout.WEST);
        main.add(centerScroll, BorderLayout.CENTER);

        add(main);

        runResults = new ArrayList<>();
    }

    // Função f(x) = x^3 - 9x + 3
    private double f(double x) {
        return x * x * x - 9 * x + 3;
    }

    private void runBisectionAction() {
        double a, b, tol;
        int maxIter;
        try {
            a = Double.parseDouble(fieldA.getText().trim());
            b = Double.parseDouble(fieldB.getText().trim());
            tol = Double.parseDouble(fieldTol.getText().trim());
            maxIter = Integer.parseInt(fieldMaxIter.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Entrada inválida. Verifique os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double fa = f(a);
        double fb = f(b);
        if (Double.isNaN(fa) || Double.isNaN(fb) || Double.isInfinite(fa) || Double.isInfinite(fb)) {
            JOptionPane.showMessageDialog(this, "f(a) ou f(b) retornou valor inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (fa * fb > 0) {
            JOptionPane.showMessageDialog(this, "f(a) e f(b) precisam ter sinais opostos (Teorema de Bolzano).", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Executa bissecção e guarda passos
        List<String> steps = new ArrayList<>();
        double left = a;
        double right = b;
        double mid = Double.NaN;
        double fmid;

        steps.add(String.format("Inicio: a=%s, b=%s, f(a)=%s, f(b)=%s", DF.format(left), DF.format(right), DF.format(f(left)), DF.format(f(right))));

        int iter;
        for (iter = 1; iter <= maxIter; iter++) {
            mid = (left + right) / 2.0;
            fmid = f(mid);
            String line = String.format("%02d: a=%10s, b=%10s, m=%10s, f(a)=%10s, f(b)=%10s, f(m)=%10s",
                    iter,
                    DF.format(left),
                    DF.format(right),
                    DF.format(mid),
                    DF.format(f(left)),
                    DF.format(f(right)),
                    DF.format(fmid));
            steps.add(line);

            if (Math.abs(fmid) == 0.0 || (right - left) / 2.0 < tol) {
                // convergiu
                break;
            }

            if (f(left) * fmid <= 0) {
                right = mid;
            } else {
                left = mid;
            }
        }

        boolean converged = iter <= maxIter;
        double approxRoot = mid;
        RunResult rr = new RunResult(a, b, approxRoot, iter, converged, steps);
        runResults.add(rr);
        runsModel.addElement(String.format("Run %d: [ %s , %s ] -> m=%s (%d it)", runResults.size(), DF.format(a), DF.format(b), DF.format(approxRoot), iter));

        // mostra detalhes
        showRunDetails(rr);
        btnSave.setEnabled(true);
    }

    private void showRunDetails(RunResult rr) {
        StringBuilder sb = new StringBuilder();
        sb.append("Método: Bissecção\n");
        sb.append("Função: f(x) = x^3 - 9x + 3\n\n");
        sb.append(String.format("Intervalo inicial: [%s, %s]\n", DF.format(rr.a), DF.format(rr.b)));
        sb.append(String.format("Aproximação final: %s\n", DF.format(rr.root)));
        sb.append(String.format("Iterações: %d\n", rr.iterations));
        sb.append(String.format("Convergiu: %s\n\n", rr.converged ? "Sim" : "Não"));
        sb.append("Passos:\n");
        for (String s : rr.steps) {
            sb.append(s).append('\n');
        }

        textArea.setText(sb.toString());
    }

    private void showSelectedRun() {
        int idx = runsList.getSelectedIndex();
        if (idx >= 0 && idx < runResults.size()) {
            showRunDetails(runResults.get(idx));
        }
    }

    private void saveReportAction() {
        int idx = runsList.getSelectedIndex();
        RunResult rr = null;
        if (idx >= 0 && idx < runResults.size()) rr = runResults.get(idx);
        else if (!runResults.isEmpty()) rr = runResults.get(runResults.size() - 1);

        if (rr == null) {
            JOptionPane.showMessageDialog(this, "Nenhuma execução para salvar.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new java.io.File("bisection_report.txt"));
        int res = chooser.showSaveDialog(this);
        if (res != JFileChooser.APPROVE_OPTION) return;

        java.io.File out = chooser.getSelectedFile();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(out))) {
            bw.write("Relatório do método da bissecção\n");
            bw.write("Função: f(x) = x^3 - 9x + 3\n\n");
            bw.write(String.format("Intervalo inicial: [%s, %s]\n", DF.format(rr.a), DF.format(rr.b)));
            bw.write(String.format("Aproximação final: %s\n", DF.format(rr.root)));
            bw.write(String.format("Iterações: %d\n", rr.iterations));
            bw.write(String.format("Convergiu: %s\n\n", rr.converged ? "Sim" : "Não"));
            bw.write("Passos:\n");
            for (String s : rr.steps) bw.write(s + "\n");
            JOptionPane.showMessageDialog(this, "Salvo em: " + out.getAbsolutePath(), "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Classe para armazenar resultado de uma execução
    private static class RunResult {
        final double a;
        final double b;
        final double root;
        final int iterations;
        final boolean converged;
        final List<String> steps;

        RunResult(double a, double b, double root, int iterations, boolean converged, List<String> steps) {
            this.a = a;
            this.b = b;
            this.root = root;
            this.iterations = iterations;
            this.converged = converged;
            this.steps = steps;
        }
    }
}
