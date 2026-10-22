
package com.smartstudy.app.ui;

import com.smartstudy.app.config.DatabaseConfig;
import com.smartstudy.app.model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QuizHistoryForm extends JFrame {

    private final Student loggedInStudent;
    private final DefaultTableModel tableModel;
    private final JTable historyTable;

    private final JLabel totalQuizzesLabel;
    private final JLabel averageScoreLabel;
    private final JLabel bestScoreLabel;

    public QuizHistoryForm(Student student) {

        loggedInStudent = student;

        setTitle("AI Smart Study Planner - Quiz History");
        setSize(1000, 600);
        setMinimumSize(new Dimension(850, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(12, 12));

        getContentPane().setBackground(new Color(7, 20, 22));

        JLabel heading = new JLabel("Quiz History");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 26));
        heading.setForeground(new Color(121, 210, 232));
heading.setFont(new Font("Segoe UI", Font.BOLD, 28));
heading.setBorder(
        BorderFactory.createEmptyBorder(18, 20, 5, 20)
);

        JLabel subtitle = new JLabel(
                "Track your previous quiz attempts and performance."
        );
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
       subtitle.setForeground(new Color(155, 184, 190));

        JPanel headingPanel = new JPanel(new BorderLayout(5, 5));
        headingPanel.setOpaque(false);
        headingPanel.setBorder(
                BorderFactory.createEmptyBorder(5, 5, 10, 5)
        );
        headingPanel.add(heading, BorderLayout.NORTH);
        headingPanel.add(subtitle, BorderLayout.CENTER);

        // Summary cards
        totalQuizzesLabel = new JLabel("0");
        averageScoreLabel = new JLabel("0%");
        bestScoreLabel = new JLabel("0%");

        JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        summaryPanel.setOpaque(false);
        summaryPanel.setBorder(
                BorderFactory.createEmptyBorder(0, 20, 10, 20)
        );

        summaryPanel.add(createSummaryCard(
                "TOTAL QUIZZES",
                totalQuizzesLabel,
                new Color(42, 93, 180)
        ));

        summaryPanel.add(createSummaryCard(
                "AVERAGE SCORE",
                averageScoreLabel,
                new Color(22, 145, 90)
        ));

        summaryPanel.add(createSummaryCard(
                "BEST SCORE",
                bestScoreLabel,
                new Color(132, 80, 190)
        ));

        JPanel topPanel = new JPanel(new BorderLayout(0, 12));
        topPanel.setOpaque(false);
        topPanel.add(headingPanel, BorderLayout.NORTH);
        topPanel.add(summaryPanel, BorderLayout.CENTER);

        // History table
        String[] columns = {
                "Quiz ID",
                "Subject Name",
                "Total Questions",
                "Correct Answers",
                "Score (%)",
                "Quiz Date"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        historyTable = new JTable(tableModel);
        historyTable.setRowHeight(32);
        historyTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        historyTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );
        historyTable.setAutoCreateRowSorter(true);
        historyTable.setFillsViewportHeight(true);
        historyTable.setGridColor(new Color(225, 230, 238));
        historyTable.setBackground(new Color(11, 37, 40));
historyTable.setForeground(new Color(240, 248, 250));
historyTable.setSelectionBackground(new Color(28, 80, 85));
historyTable.setSelectionForeground(Color.WHITE);
historyTable.setShowGrid(true);
historyTable.setIntercellSpacing(new Dimension(1, 1));

        javax.swing.table.JTableHeader tableHeader =
        historyTable.getTableHeader();

tableHeader.setBackground(new Color(7, 20, 22));
tableHeader.setForeground(new Color(121, 210, 232));
tableHeader.setFont(new Font("Segoe UI", Font.BOLD, 13));
tableHeader.setOpaque(true);
tableHeader.setPreferredSize(new Dimension(0, 38));
tableHeader.setReorderingAllowed(false);

tableHeader.setDefaultRenderer(
        new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {

                JLabel label = (JLabel)
                        super.getTableCellRendererComponent(
                                table, value, isSelected,
                                hasFocus, row, column);

                label.setOpaque(true);
                label.setBackground(new Color(7, 20, 22));
                label.setForeground(new Color(121, 210, 232));
                label.setFont(new Font("Segoe UI", Font.BOLD, 13));
                label.setBorder(
                        BorderFactory.createEmptyBorder(0, 6, 0, 6)
                );

                return label;
            }
        }
);

tableHeader.repaint();
        JScrollPane scrollPane = new JScrollPane(historyTable);
        scrollPane.setBackground(new Color(11, 37, 40));
scrollPane.getViewport().setBackground(new Color(11, 37, 40));
        scrollPane.setBorder(
        BorderFactory.createEmptyBorder(0, 0, 0, 0)
);

        // Buttons
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadQuizHistory());

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());

        styleButton(refreshButton, new Color(42, 93, 180));
        styleButton(closeButton, new Color(100, 110, 125));

        JPanel bottomPanel = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 12, 12)
        );
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(
                BorderFactory.createEmptyBorder(0, 15, 5, 15)
        );
        bottomPanel.add(refreshButton);
        bottomPanel.add(closeButton);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        loadQuizHistory();
    }

    private JPanel createSummaryCard(
            String title,
            JLabel valueLabel,
            Color accentColor) {

        JPanel card = new JPanel(new BorderLayout(5, 12));
        card.setBackground(new Color(11, 37, 40));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(28, 66, 70)
                ),
                BorderFactory.createEmptyBorder(15, 18, 15, 18)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        titleLabel.setForeground(new Color(155, 184, 190));

        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(accentColor);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    private void styleButton(JButton button, Color color) {

        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );
        button.setBorder(BorderFactory.createEmptyBorder(
                10, 22, 10, 22
        ));
    }

    private void loadQuizHistory() {

        tableModel.setRowCount(0);

        totalQuizzesLabel.setText("0");
        averageScoreLabel.setText("0%");
        bestScoreLabel.setText("0%");

        if (loggedInStudent == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Student information is not available.",
                    "Quiz History",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String sql =
                "SELECT qr.RESULT_ID, qr.SUBJECT_ID, " +
                "s.SUBJECT_NAME, qr.TOTAL_QUESTIONS, " +
                "qr.CORRECT_ANSWERS, qr.SCORE_PERCENTAGE, " +
                "qr.QUIZ_DATE " +
                "FROM QUIZ_RESULTS qr " +
                "LEFT JOIN SUBJECTS s " +
                "ON qr.SUBJECT_ID = s.SUBJECT_ID " +
                "WHERE qr.STUDENT_ID = ? " +
                "ORDER BY qr.QUIZ_DATE DESC";

        int totalQuizzes = 0;
        double scoreSum = 0;
        double bestScore = 0;

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    loggedInStudent.getStudentId()
            );

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    int resultId = resultSet.getInt("RESULT_ID");
                    String subjectName =
                            resultSet.getString("SUBJECT_NAME");

                    if (subjectName == null
                            || subjectName.trim().isEmpty()) {
                        subjectName =
                                "Subject ID: "
                                + resultSet.getInt("SUBJECT_ID");
                    }

                    int totalQuestions =
                            resultSet.getInt("TOTAL_QUESTIONS");

                    int correctAnswers =
                            resultSet.getInt("CORRECT_ANSWERS");

                    double score =
                            resultSet.getDouble("SCORE_PERCENTAGE");

                    java.sql.Timestamp quizDate =
                            resultSet.getTimestamp("QUIZ_DATE");

                    Object[] row = {
                            resultId,
                            subjectName,
                            totalQuestions,
                            correctAnswers,
                            String.format("%.2f%%", score),
                            quizDate
                    };

                    tableModel.addRow(row);

                    totalQuizzes++;
                    scoreSum += score;

                    if (totalQuizzes == 1 || score > bestScore) {
                        bestScore = score;
                    }
                }
            }

            totalQuizzesLabel.setText(
                    String.valueOf(totalQuizzes)
            );

            if (totalQuizzes > 0) {

                double averageScore = scoreSum / totalQuizzes;

                averageScoreLabel.setText(
                        String.format("%.1f%%", averageScore)
                );

                bestScoreLabel.setText(
                        String.format("%.1f%%", bestScore)
                );
            }

            if (totalQuizzes == 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "No quiz history found for this student.",
                        "Quiz History",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not load quiz history:\n" + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
