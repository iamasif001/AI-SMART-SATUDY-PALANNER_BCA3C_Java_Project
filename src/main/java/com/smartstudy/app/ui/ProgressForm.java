
package com.smartstudy.app.ui;

import com.smartstudy.app.model.Progress;
import com.smartstudy.app.model.Student;
import com.smartstudy.app.model.Subject;
import com.smartstudy.app.service.ProgressService;
import com.smartstudy.app.service.SubjectService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProgressForm extends JFrame {

    private static final Color BACKGROUND = new Color(7, 21, 23);
private static final Color DARK_TEXT = new Color(240, 248, 250);
private static final Color SECONDARY_TEXT = new Color(160, 190, 198);
private static final Color BLUE = new Color(121, 210, 232);
private static final Color BORDER = new Color(28, 66, 70);

private static final Color CARD = new Color(11, 37, 40);
private static final Color HOVER = new Color(20, 55, 59);

    private final Student loggedInStudent;

    private ProgressService progressService;
    private SubjectService subjectService;

    private DefaultTableModel tableModel;
    private JTable progressTable;

    private JLabel subjectCountLabel;
    private JLabel averageProgressLabel;
    private JLabel totalHoursLabel;

    public ProgressForm(Student student) {
        loggedInStudent = student;

        try {
            progressService = new ProgressService();
            subjectService = new SubjectService();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to connect to database.\n" + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        initializeFrame();
        buildUI();
        loadProgress();
    }

    private void initializeFrame() {
        setTitle("My Study Progress");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1050, 650);
        setMinimumSize(new Dimension(800, 500));
        setLocationRelativeTo(null);
        setResizable(true);
    }

    private void buildUI() {
        JPanel root = new JPanel(new BorderLayout(0, 20));
        root.setBackground(BACKGROUND);
        root.setBorder(new EmptyBorder(25, 30, 25, 30));

        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("My Study Progress");
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(DARK_TEXT);

        JLabel subtitle = new JLabel(
                "Track your completed study hours and progress."
        );
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(SECONDARY_TEXT);

        header.add(title);
        header.add(Box.createVerticalStrut(5));
        header.add(subtitle);
        root.add(header, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(0, 18));
        center.setOpaque(false);

        JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        summaryPanel.setOpaque(false);

        JPanel subjectsCard = createSummaryCard("SUBJECTS", "0");
        JPanel progressCard = createSummaryCard("AVERAGE PROGRESS", "0%");
        JPanel hoursCard = createSummaryCard("COMPLETED HOURS", "0");

        subjectCountLabel =
                (JLabel) subjectsCard.getClientProperty("valueLabel");
        averageProgressLabel =
                (JLabel) progressCard.getClientProperty("valueLabel");
        totalHoursLabel =
                (JLabel) hoursCard.getClientProperty("valueLabel");

        summaryPanel.add(subjectsCard);
        summaryPanel.add(progressCard);
        summaryPanel.add(hoursCard);
        center.add(summaryPanel, BorderLayout.NORTH);

        String[] columns = {
                "PROGRESS ID", "SUBJECT", "COMPLETED HOURS", "PROGRESS"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        progressTable = new JTable(tableModel);
        progressTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        progressTable.setForeground(DARK_TEXT);
        progressTable.setBackground(CARD);
progressTable.setSelectionBackground(BORDER);
        progressTable.setRowHeight(45);
        progressTable.setShowVerticalLines(false);
        progressTable.setGridColor(BORDER);
        progressTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        progressTable.setSelectionBackground(new Color(28, 66, 70));
progressTable.setSelectionForeground(new Color(121, 210, 232));

        progressTable.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 12)
        );
        progressTable.getTableHeader().setBackground(CARD);
     
progressTable.getTableHeader().setDefaultRenderer(
    new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {

            JLabel label = (JLabel) super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);

            label.setBackground(CARD);
            label.setForeground(BLUE);
            label.setFont(new Font("Segoe UI", Font.BOLD, 12));
            label.setHorizontalAlignment(SwingConstants.LEFT);
            label.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER),
                    BorderFactory.createEmptyBorder(0, 8, 0, 8)
            ));
            label.setOpaque(true);

            return label;
        }
    }
);

progressTable.getTableHeader().setForeground(BLUE);
progressTable.getTableHeader().setOpaque(true);
        progressTable.getTableHeader().setForeground(SECONDARY_TEXT);
        progressTable.getTableHeader().setPreferredSize(new Dimension(0, 42));
        progressTable.setShowHorizontalLines(true);
progressTable.setShowVerticalLines(false);

        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        progressTable.getColumnModel().getColumn(0)
                .setMinWidth(0);
        progressTable.getColumnModel().getColumn(0)
                .setMaxWidth(0);
        progressTable.getColumnModel().getColumn(0)
                .setPreferredWidth(0);

        progressTable.getColumnModel().getColumn(2)
                .setCellRenderer(centerRenderer);
        progressTable.getColumnModel().getColumn(3)
                .setCellRenderer(centerRenderer);

        progressTable.getColumnModel().getColumn(1)
                .setPreferredWidth(350);
        progressTable.getColumnModel().getColumn(2)
                .setPreferredWidth(200);
        progressTable.getColumnModel().getColumn(3)
                .setPreferredWidth(200);

        JScrollPane scrollPane = new JScrollPane(progressTable);
        scrollPane.getViewport().setBackground(CARD);
scrollPane.setBorder(new LineBorder(BORDER, 1, true));
        scrollPane.setBorder(new LineBorder(BORDER, 1, true));
        center.add(scrollPane, BorderLayout.CENTER);

        root.add(center, BorderLayout.CENTER);

        JPanel footer = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 10, 0)
        );
        footer.setOpaque(false);

        JButton updateButton = new JButton("UPDATE PROGRESS");
        JButton refreshButton = new JButton("REFRESH");
        JButton closeButton = new JButton("CLOSE");

        styleButton(updateButton);
        styleButton(refreshButton);
        styleButton(closeButton);

        updateButton.setPreferredSize(new Dimension(165, 40));

        updateButton.addActionListener(e -> updateSelectedProgress());
        refreshButton.addActionListener(e -> loadProgress());
        closeButton.addActionListener(e -> dispose());

        footer.add(updateButton);
        footer.add(refreshButton);
        footer.add(closeButton);

        root.add(footer, BorderLayout.SOUTH);
        setContentPane(root);
    }

    private JPanel createSummaryCard(String title, String value) {
        JPanel card = new JPanel(new BorderLayout(0, 8));
        card.setBackground(CARD);
        card.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(BORDER, 1, true),
                        new EmptyBorder(15, 18, 15, 18)
                )
        );

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        titleLabel.setForeground(SECONDARY_TEXT);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 25));
        valueLabel.setForeground(BLUE);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        card.putClientProperty("valueLabel", valueLabel);

        return card;
    }

    private void styleButton(JButton button) {
    button.setFont(new Font("Segoe UI", Font.BOLD, 12));
    button.setForeground(BLUE);
button.setBackground(CARD);
button.setOpaque(true);
button.setContentAreaFilled(true);
button.setBorderPainted(true);
button.setUI(new javax.swing.plaf.basic.BasicButtonUI());
    button.setFocusPainted(false);
    button.setBorder(new LineBorder(BORDER, 1, true));
    button.setPreferredSize(new Dimension(110, 40));
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    button.setOpaque(true);

    button.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
            button.setBackground(HOVER);
            button.setForeground(BLUE);
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
            button.setBackground(CARD);
            button.setForeground(DARK_TEXT);
        }
    });
}

    private void loadProgress() {
        if (loggedInStudent == null
                || progressService == null
                || subjectService == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Student or progress service is unavailable.",
                    "Progress",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            List<Progress> progressList =
                    progressService.getProgressByStudentId(
                            loggedInStudent.getStudentId()
                    );

            List<Subject> subjects =
                    subjectService.getSubjectsByStudentId(
                            loggedInStudent.getStudentId()
                    );

            Map<Integer, String> subjectNames = new HashMap<>();

            for (Subject subject : subjects) {
                subjectNames.put(
                        subject.getSubjectId(),
                        subject.getSubjectName()
                );
            }

            tableModel.setRowCount(0);

            double totalHours = 0;
            double totalPercentage = 0;
            int validRecords = 0;

            for (Progress progress : progressList) {
                String subjectName =
                        subjectNames.get(progress.getSubjectId());

                if (subjectName == null) {
                    subjectName = "Unknown Subject";
                }

                double hours = progress.getCompletedHours();
                double percentage = progress.getProgressPercentage();

                tableModel.addRow(new Object[]{
                        progress.getProgressId(),
                        subjectName,
                        String.format("%.2f hrs", hours),
                        String.format("%.1f%%", percentage)
                });

                totalHours += hours;
                totalPercentage += percentage;
                validRecords++;
            }

            subjectCountLabel.setText(String.valueOf(validRecords));

            double averageProgress = validRecords == 0
                    ? 0 : totalPercentage / validRecords;

            averageProgressLabel.setText(
                    String.format("%.1f%%", averageProgress)
            );
            totalHoursLabel.setText(
                    String.format("%.2f hrs", totalHours)
            );

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load progress.\n" + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void updateSelectedProgress() {
        int selectedRow = progressTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a subject from the table first.",
                    "Select Subject",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        int modelRow = progressTable.convertRowIndexToModel(selectedRow);
        int progressId = Integer.parseInt(
                tableModel.getValueAt(modelRow, 0).toString()
        );

        try {
            Progress selectedProgress =
                    progressService.getProgressById(
                            progressId,
                            loggedInStudent.getStudentId()
                    );

            if (selectedProgress == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Progress record not found. Please refresh.",
                        "Progress",
                        JOptionPane.WARNING_MESSAGE
                );
                loadProgress();
                return;
            }

            JTextField hoursField = new JTextField(
                    String.valueOf(selectedProgress.getCompletedHours())
            );
            JTextField percentageField = new JTextField(
                    String.valueOf(selectedProgress.getProgressPercentage())
            );

            JPanel inputPanel = new JPanel(new GridLayout(0, 1, 5, 5));
            inputPanel.add(new JLabel("Completed Hours:"));
            inputPanel.add(hoursField);
            inputPanel.add(new JLabel("Progress Percentage (0-100):"));
            inputPanel.add(percentageField);

            int result = JOptionPane.showConfirmDialog(
                    this,
                    inputPanel,
                    "Update Subject Progress",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result != JOptionPane.OK_OPTION) {
                return;
            }

            double hours;
            double percentage;

            try {
                hours = Double.parseDouble(hoursField.getText().trim());
                percentage = Double.parseDouble(
                        percentageField.getText().trim()
                );
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Enter valid numeric values for hours and percentage.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            if (!Double.isFinite(hours) || hours < 0 || hours > 999999) {
                JOptionPane.showMessageDialog(
                        this,
                        "Completed hours must be between 0 and 999999.",
                        "Invalid Hours",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            if (!Double.isFinite(percentage)
                    || percentage < 0 || percentage > 100) {
                JOptionPane.showMessageDialog(
                        this,
                        "Progress percentage must be between 0 and 100.",
                        "Invalid Percentage",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            selectedProgress.setCompletedHours(hours);
            selectedProgress.setProgressPercentage(percentage);

            boolean updated =
                    progressService.updateProgress(selectedProgress);

            if (updated) {
                JOptionPane.showMessageDialog(
                        this,
                        "Progress updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
                loadProgress();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Progress was not updated. Please try again.",
                        "Update Failed",
                        JOptionPane.WARNING_MESSAGE
                );
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to update progress.\n" + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
