
package com.smartstudy.app.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AIQuizForm extends JFrame {

    private final List<QuizQuestion> questions;
    private final int studentId;
private final int subjectId;
    private final List<JRadioButton> optionButtons = new ArrayList<>();

    private int currentQuestion = 0;
    private int score = 0;
    private boolean answered = false;

    private JLabel progressLabel;
    private JLabel questionLabel;
    private JLabel feedbackLabel;
    private JPanel optionsPanel;
    private JButton submitButton;
    private JButton nextButton;
    private ButtonGroup buttonGroup;

    private final Color GREEN = new Color(90, 200, 150);
private final Color RED = new Color(225, 90, 100);
private final Color BLUE = new Color(121, 210, 232);
private final Color LIGHT = new Color(7, 20, 22);

    public AIQuizForm(
        String generatedMCQs,
        int studentId,
        int subjectId
) {
        questions = parseQuestions(generatedMCQs);
        this.studentId = studentId;
this.subjectId = subjectId;

        setTitle("AI Smart Study Planner - AI Quiz");
        setSize(760, 580);
        setMinimumSize(new Dimension(600, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(18, 18));
        root.setBackground(LIGHT);
        root.setForeground(new Color(240, 248, 250));
        root.setBorder(new EmptyBorder(22, 26, 22, 26));
        setContentPane(root);

        JLabel heading = new JLabel("AI Practice Quiz");
        heading.setFont(new Font("Arial", Font.BOLD, 25));
        heading.setForeground(new Color(121, 210, 232));

        progressLabel = new JLabel();
        progressLabel.setFont(new Font("Arial", Font.BOLD, 14));
        progressLabel.setForeground(new Color(121, 210, 232));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.add(heading, BorderLayout.WEST);
        header.add(progressLabel, BorderLayout.EAST);
        root.add(header, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(12, 16));
        center.setBackground(new Color(11, 37, 40));
      center.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(28, 66, 70), 1, true),
        new EmptyBorder(22, 22, 22, 22)
));

        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        questionLabel.setForeground(new Color(240, 248, 250));

        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setOpaque(false);

        feedbackLabel = new JLabel("Choose an answer, then click Submit.");
        feedbackLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        feedbackLabel.setForeground(new Color(155, 184, 190));

        JPanel questionArea = new JPanel(new BorderLayout(8, 16));
        questionArea.setOpaque(false);
        questionArea.add(questionLabel, BorderLayout.NORTH);
        questionArea.add(optionsPanel, BorderLayout.CENTER);
        questionArea.add(feedbackLabel, BorderLayout.SOUTH);

        center.add(questionArea, BorderLayout.CENTER);
        root.add(center, BorderLayout.CENTER);

        submitButton = new JButton("Submit Answer");
        nextButton = new JButton("Next Question");

        styleButton(submitButton, BLUE);
styleButton(nextButton, GREEN);
nextButton.setForeground(Color.BLACK);
nextButton.setForeground(new Color(7, 20, 22));

        submitButton.addActionListener(e -> submitAnswer());
        nextButton.addActionListener(e -> nextQuestion());

JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 5));        footer.setOpaque(false);
        footer.add(submitButton);
        footer.add(nextButton);
        root.add(footer, BorderLayout.SOUTH);

        if (questions.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "No readable MCQs were found. Generate MCQs again.",
                    "Quiz Unavailable",
                    JOptionPane.WARNING_MESSAGE
            );
            submitButton.setEnabled(false);
            nextButton.setEnabled(false);
            questionLabel.setText("No questions available.");
        } else {
            showQuestion();
        }
    }

    
private void styleButton(JButton button, Color color) {
    button.setFont(new Font("Arial", Font.BOLD, 14));
    button.setForeground(new Color(7, 20, 22));
    button.setBackground(color);

    button.setOpaque(true);
    button.setContentAreaFilled(true);
    button.setBorderPainted(false);
    button.setFocusPainted(false);
    button.setRolloverEnabled(false);

    button.setBorder(new EmptyBorder(10, 16, 10, 16));
    button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    button.setUI(new javax.swing.plaf.basic.BasicButtonUI());
}


    private void showQuestion() {
        answered = false;
        buttonGroup = new ButtonGroup();
        optionButtons.clear();
        optionsPanel.removeAll();

        QuizQuestion question = questions.get(currentQuestion);

        progressLabel.setText(
                "Question " + (currentQuestion + 1) + " of " + questions.size()
        );
        questionLabel.setText("<html><div style='width:560px'>"
                + escapeHtml(question.text) + "</div></html>");

        for (int i = 0; i < question.options.length; i++) {
            JRadioButton option = new JRadioButton(
                    (char) ('A' + i) + ". " + question.options[i]
            );

            option.setFont(new Font("Arial", Font.PLAIN, 15));
            option.setOpaque(true);
            option.setFocusPainted(false);
            option.setBackground(new Color(11, 37, 40));
option.setForeground(new Color(240, 248, 250));
            option.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(28, 66, 70), 1, true),
        new EmptyBorder(10, 12, 10, 8)
));
            option.setAlignmentX(Component.LEFT_ALIGNMENT);
            option.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));

            buttonGroup.add(option);
            optionButtons.add(option);
            optionsPanel.add(option);
            optionsPanel.add(Box.createVerticalStrut(8));
        }

        feedbackLabel.setText("Choose an answer, then click Submit.");
       feedbackLabel.setForeground(new Color(155, 184, 190));
        submitButton.setEnabled(true);
        nextButton.setText(
                currentQuestion == questions.size() - 1
                        ? "Finish Quiz" : "Next Question"
        );
        nextButton.setEnabled(false);

        optionsPanel.revalidate();
        optionsPanel.repaint();
    }

    private void submitAnswer() {
        if (answered) {
            return;
        }

        int selected = -1;

        for (int i = 0; i < optionButtons.size(); i++) {
            if (optionButtons.get(i).isSelected()) {
                selected = i;
                break;
            }
        }

        if (selected == -1) {
            JOptionPane.showMessageDialog(this, "Please select an answer first.");
            return;
        }

        answered = true;
        QuizQuestion question = questions.get(currentQuestion);

        for (JRadioButton option : optionButtons) {
            option.setEnabled(false);
        }

        int correct = question.correctIndex;

        if (selected == correct) {
            score++;
            optionButtons.get(selected).setBackground(new Color(210, 245, 220));
            optionButtons.get(selected).setForeground(GREEN);
            feedbackLabel.setText("Correct! Well done.");
            feedbackLabel.setForeground(GREEN);
        } else {
            optionButtons.get(selected).setBackground(new Color(255, 220, 220));
            optionButtons.get(selected).setForeground(RED);

            if (correct >= 0 && correct < optionButtons.size()) {
                optionButtons.get(correct).setBackground(new Color(210, 245, 220));
                optionButtons.get(correct).setForeground(GREEN);
            }

            feedbackLabel.setText("Incorrect. The correct answer is highlighted in green.");
            feedbackLabel.setForeground(RED);
        }

        submitButton.setEnabled(false);
        nextButton.setEnabled(true);
    }

    private void nextQuestion() {
        if (!answered) {
            return;
        }

        if (currentQuestion < questions.size() - 1) {
            currentQuestion++;
            showQuestion();
        } else {
            showResult();
        }
    }

    private void showResult() {
        int total = questions.size();
int percentage = total == 0 ? 0 : (score * 100 / total);

String message = "Quiz completed!\n\n"
        + "Correct answers: " + score + " / " + total + "\n"
        + "Score: " + percentage + "%";

UIManager.put("OptionPane.background", new Color(7, 20, 22));
UIManager.put("Panel.background", new Color(7, 20, 22));
UIManager.put("OptionPane.messageForeground", new Color(240, 248, 250));
UIManager.put("Button.background", new Color(121, 210, 232));
UIManager.put("Button.foreground", new Color(7, 20, 22));
UIManager.put("Button.focus", new Color(121, 210, 232));

JOptionPane.showMessageDialog(
        this,
        message,
        "Quiz Result",
        JOptionPane.INFORMATION_MESSAGE
);
        saveQuizResult();

        dispose();

        
    }
    
private void saveQuizResult() {
    String sql = """
        INSERT INTO QUIZ_RESULTS
        (STUDENT_ID, SUBJECT_ID, TOTAL_QUESTIONS,
         CORRECT_ANSWERS, SCORE_PERCENTAGE)
        VALUES (?, ?, ?, ?, ?)
        """;

    try (Connection connection =
                 com.smartstudy.app.config.DatabaseConfig.getConnection();
         PreparedStatement statement =
                 connection.prepareStatement(sql)) {

        int total = questions.size();
        double percentage = total == 0 ? 0.0 : (score * 100.0 / total);

        statement.setInt(1, studentId);
        statement.setInt(2, subjectId);
        statement.setInt(3, total);
        statement.setInt(4, score);
        statement.setDouble(5, percentage);

        statement.executeUpdate();

        JOptionPane.showMessageDialog(
                this,
                "Quiz result saved successfully!",
                "Saved",
                JOptionPane.INFORMATION_MESSAGE
        );

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(
                this,
                "Could not save quiz result:\n" + ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}


    
private List<QuizQuestion> parseQuestions(String text) {
    List<QuizQuestion> result = new ArrayList<>();

    if (text == null || text.isBlank()) {
        return result;
    }

    Pattern questionPattern = Pattern.compile(
            "(?im)^\\s*(?:#{1,6}\\s*)?Question\\s*\\d+\\s*[:.)-]?\\s*"
    );

    Matcher questionMatcher = questionPattern.matcher(text);
    List<Integer> starts = new ArrayList<>();

    while (questionMatcher.find()) {
        starts.add(questionMatcher.start());
    }

    for (int i = 0; i < starts.size(); i++) {
        int start = starts.get(i);
        int end = (i + 1 < starts.size())
                ? starts.get(i + 1)
                : text.length();

        String block = text.substring(start, end);
        String[] lines = block.split("\\R");

        StringBuilder questionText = new StringBuilder();
        String[] options = new String[4];
        int correctIndex = -1;
        boolean readingQuestion = true;

        for (String rawLine : lines) {
            String line = rawLine.trim();

            if (line.isEmpty()
                    || line.matches("(?i)^#{1,6}\\s*Question\\s*\\d+.*")) {
                continue;
            }

            Matcher optionMatcher = Pattern.compile(
                    "(?i)^([A-D])\\s*[).:]\\s*(.*)$"
            ).matcher(line);

            if (optionMatcher.find()) {
                readingQuestion = false;

                int index = optionMatcher.group(1)
                        .toUpperCase().charAt(0) - 'A';

                options[index] = optionMatcher.group(2).trim();
                continue;
            }

            Matcher answerMatcher = Pattern.compile(
                    "(?i)^\\*{0,2}Correct Answer\\*{0,2}\\s*:\\s*\\*{0,2}\\s*([A-D])\\b"
            ).matcher(line);

            if (answerMatcher.find()) {
                correctIndex = answerMatcher.group(1)
                        .toUpperCase().charAt(0) - 'A';
                continue;
            }

            if (line.startsWith("---")
                    || line.startsWith("**Explanation:**")
                    || line.startsWith("Explanation:")) {
                readingQuestion = false;
                continue;
            }

            if (readingQuestion) {
                if (questionText.length() > 0) {
                    questionText.append(' ');
                }
                questionText.append(line);
            }
        }

        boolean valid = correctIndex >= 0;

        for (String option : options) {
            if (option == null || option.isBlank()) {
                valid = false;
                break;
            }
        }

        if (valid && !questionText.toString().isBlank()) {
            result.add(new QuizQuestion(
                    questionText.toString().trim(),
                    options,
                    correctIndex
            ));
        }
    }

    return result;
}


    private String escapeHtml(String text) {
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }

    private static class QuizQuestion {
        private final String text;
        private final String[] options;
        private final int correctIndex;

        private QuizQuestion(String text, String[] options, int correctIndex) {
            this.text = text;
            this.options = options;
            this.correctIndex = correctIndex;
        }
    }
}
