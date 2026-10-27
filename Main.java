
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Main {
    private JFrame frame;
    private JTextField nameField, javaField, dsaField, dbmsField;
    private JLabel averageLabel, statusLabel;
    private JTextArea reportArea;

    private final Color navy = new Color(25, 31, 76);
    private final Color purple = new Color(105, 72, 235);
    private final Color background = new Color(244, 246, 255);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().createDashboard());
    }

    private void createDashboard() {
        frame = new JFrame("LearnMate - Personalized Learning");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 750);
        frame.setMinimumSize(new Dimension(650, 600));
        frame.setLocationRelativeTo(null);

        JPanel page = new JPanel();
        page.setBackground(background);
        page.setBorder(new EmptyBorder(22, 28, 22, 28));
        page.setLayout(new BoxLayout(page, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("LearnMate");
        title.setFont(new Font("SansSerif", Font.BOLD, 30));
        title.setForeground(navy);

        JLabel subtitle = new JLabel(
            "Personalized Learning and Academic Performance Dashboard");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));

        page.add(title);
        page.add(Box.createVerticalStrut(6));
        page.add(subtitle);
        page.add(Box.createVerticalStrut(22));

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 12, 12));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(225, 229, 247)),
            new EmptyBorder(18, 18, 18, 18)));

        nameField = new JTextField();
        javaField = new JTextField();
        dsaField = new JTextField();
        dbmsField = new JTextField();

        inputPanel.add(new JLabel("Student Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Java Marks (out of 100):"));
        inputPanel.add(javaField);
        inputPanel.add(new JLabel("DSA Marks (out of 100):"));
        inputPanel.add(dsaField);
        inputPanel.add(new JLabel("DBMS Marks (out of 100):"));
        inputPanel.add(dbmsField);

        page.add(inputPanel);
        page.add(Box.createVerticalStrut(14));

        JButton calculateButton = new JButton("Calculate Performance");
        calculateButton.setBackground(purple);
        calculateButton.setForeground(Color.WHITE);
        calculateButton.setFocusPainted(false);
        calculateButton.addActionListener(e -> calculatePerformance());

        page.add(calculateButton);
        page.add(Box.createVerticalStrut(15));

        averageLabel = new JLabel("Average: --");
        averageLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        averageLabel.setForeground(navy);

        statusLabel = new JLabel("Enter your marks to begin.");
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        page.add(averageLabel);
        page.add(Box.createVerticalStrut(8));
        page.add(statusLabel);
        page.add(Box.createVerticalStrut(12));

        JLabel reportTitle = new JLabel("Personalized Learning Plan");
        reportTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        page.add(reportTitle);
        page.add(Box.createVerticalStrut(8));

        reportArea = new JTextArea(7, 40);
        reportArea.setEditable(false);
        reportArea.setLineWrap(true);
        reportArea.setWrapStyleWord(true);
        reportArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        reportArea.setText(
            "Your learning recommendations will appear here after calculation.");
        reportArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane reportScroll = new JScrollPane(reportArea);
        page.add(reportScroll);
        page.add(Box.createVerticalStrut(16));

        JButton dsaButton = new JButton("Open DSA Concepts Lab");
        dsaButton.addActionListener(e -> new LearningTree().showLab());
        page.add(dsaButton);

        JLabel footer = new JLabel(
            "LEARNMATE | Learn smarter, one step at a time");
        footer.setForeground(Color.GRAY);
        page.add(Box.createVerticalStrut(18));
        page.add(footer);

        JScrollPane scrollPane = new JScrollPane(page);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(background);

        frame.add(scrollPane);
        frame.setVisible(true);
    }

    private void calculatePerformance() {
        try {
            String name = nameField.getText().trim();
            int java = Integer.parseInt(javaField.getText().trim());
            int dsa = Integer.parseInt(dsaField.getText().trim());
            int dbms = Integer.parseInt(dbmsField.getText().trim());

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                    "Please enter the student's name.");
                return;
            }

            if (!validMark(java) || !validMark(dsa) || !validMark(dbms)) {
                JOptionPane.showMessageDialog(frame,
                    "Marks must be between 0 and 100.");
                return;
            }

            double average = (java + dsa + dbms) / 3.0;
            averageLabel.setText(String.format("Average: %.2f%%", average));

            if (average >= 75) {
                statusLabel.setText("Performance: Excellent");
                statusLabel.setForeground(new Color(20, 130, 70));
            } else if (average >= 50) {
                statusLabel.setText("Performance: Good - Keep Improving");
                statusLabel.setForeground(new Color(190, 120, 0));
            } else {
                statusLabel.setText("Performance: Needs Improvement");
                statusLabel.setForeground(Color.RED);
            }

            StringBuilder report = new StringBuilder();
            report.append("Student: ").append(name).append("\n\n");
            report.append("Java: ").append(java).append("/100\n");
            report.append("DSA: ").append(dsa).append("/100\n");
            report.append("DBMS: ").append(dbms).append("/100\n\n");

            report.append("Recommended learning plan:\n");

            if (java < 50) {
                report.append("- Revise Java basics and practise programs.\n");
            }
            if (dsa < 50) {
                report.append("- Practise searching, sorting and data structures.\n");
            }
            if (dbms < 50) {
                report.append("- Revise SQL, keys and database concepts.\n");
            }
            if (java >= 50 && dsa >= 50 && dbms >= 50) {
                report.append("- Maintain your performance and solve advanced problems.\n");
            }

            reportArea.setText(report.toString());

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame,
                "Please enter a valid name and numeric marks in all fields.");
        }
    }

    private boolean validMark(int mark) {
        return mark >= 0 && mark <= 100;
    }
}
