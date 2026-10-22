
package com.smartstudy.app.ui;

import com.smartstudy.app.ai.GeminiAIService;
import org.apache.tika.Tika;

import com.smartstudy.app.config.DatabaseConfig;
import com.smartstudy.app.model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudyMaterialsListForm extends JFrame {

    private final Student loggedInStudent;
    private final DefaultTableModel tableModel;
    private final JTable materialsTable;

    public StudyMaterialsListForm(Student student) {

        loggedInStudent = student;
        UIManager.put("OptionPane.background", new Color(11, 37, 40));
UIManager.put("Panel.background", new Color(11, 37, 40));
UIManager.put("OptionPane.messageForeground", new Color(240, 248, 250));
UIManager.put("Button.background", new Color(121, 210, 232));
UIManager.put("Button.foreground", new Color(7, 20, 22));
UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 12));

        setTitle("AI Smart Study Planner - Saved Study Materials");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(7, 20, 22));
getContentPane().setForeground(new Color(240, 248, 250));
getContentPane().setLayout(new BorderLayout(10, 10));

        JLabel heading = new JLabel("Saved Study Materials");
        heading.setForeground(new Color(121, 210, 232));
        heading.setForeground(new Color(121, 210, 232));
heading.setOpaque(true);
heading.setBackground(new Color(11, 37, 40));
        heading.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));

        String[] columns = {
                "Material ID",
                "Subject ID",
                "Material Name",
                "Type",
                "File Path",
                "Uploaded At"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        materialsTable = new JTable(tableModel);
        materialsTable.setRowHeight(25);
        materialsTable.setBackground(new Color(11, 37, 40));
materialsTable.setForeground(new Color(240, 248, 250));
materialsTable.setGridColor(new Color(28, 66, 70));
materialsTable.setSelectionBackground(new Color(28, 80, 85));
materialsTable.setSelectionForeground(Color.WHITE);
materialsTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
materialsTable.setShowGrid(true);
materialsTable.setIntercellSpacing(new Dimension(1, 1));
        materialsTable.setAutoCreateRowSorter(true);
        

      
JScrollPane scrollPane = new JScrollPane(materialsTable);

javax.swing.table.JTableHeader tableHeader =
        materialsTable.getTableHeader();

tableHeader.setBackground(new Color(7, 20, 22));
tableHeader.setForeground(new Color(121, 210, 232));
tableHeader.setFont(new Font("Segoe UI", Font.BOLD, 13));
tableHeader.setPreferredSize(new Dimension(0, 35));
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

                JLabel label = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                label.setOpaque(true);
                label.setBackground(new Color(7, 20, 22));
                label.setForeground(new Color(121, 210, 232));
                label.setFont(new Font("Segoe UI", Font.BOLD, 13));
                label.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6));

                return label;
            }
        }
);

scrollPane.getViewport().setBackground(new Color(11, 37, 40));
scrollPane.setBorder(
        BorderFactory.createLineBorder(new Color(28, 66, 70))
);

scrollPane.getViewport().setBackground(new Color(11, 37, 40));
scrollPane.setBorder(
        BorderFactory.createLineBorder(new Color(28, 66, 70))
);

        JButton refreshButton = new JButton("Refresh");
        styleButton(refreshButton, new Color(121, 210, 232));
        
        JButton openButton = new JButton("Open Selected File");
        styleButton(openButton, new Color(121, 210, 232));
        openButton.addActionListener(e -> openSelectedFile());

        
JButton generateMCQsButton = new JButton("Generate MCQs");
styleButton(generateMCQsButton, new Color(121, 210, 232));
generateMCQsButton.addActionListener(e -> generateMCQsFromSelectedFile());


        JButton deleteButton = new JButton("Delete Selected");
        styleButton(deleteButton, new Color(225, 90, 100));
        deleteButton.addActionListener(e -> deleteSelectedMaterial());

        refreshButton.addActionListener(e -> loadMaterials());

        JButton closeButton = new JButton("Close");
        styleButton(closeButton, new Color(121, 210, 232));
        closeButton.addActionListener(e -> dispose());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 12));
        bottomPanel.setBackground(new Color(7, 20, 22));
        
        bottomPanel.add(openButton);
        bottomPanel.add(deleteButton);
        bottomPanel.add(generateMCQsButton);
        bottomPanel.add(refreshButton);
        bottomPanel.add(closeButton);


        add(heading, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        loadMaterials();
    }

    private void styleButton(JButton button, Color background) {
    button.setFont(new Font("Segoe UI", Font.BOLD, 12));
    button.setForeground(new Color(7, 20, 22));
    button.setBackground(background);
    button.setOpaque(true);
    button.setContentAreaFilled(true);
    button.setBorderPainted(false);
    button.setFocusPainted(false);
    button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    button.setBorder(BorderFactory.createEmptyBorder(9, 14, 9, 14));
    button.setUI(new javax.swing.plaf.basic.BasicButtonUI());
}

    private void loadMaterials() {

        tableModel.setRowCount(0);

        String sql =
                "SELECT MATERIAL_ID, SUBJECT_ID, MATERIAL_NAME, " +
                "MATERIAL_TYPE, FILE_PATH, UPLOADED_AT " +
                "FROM STUDY_MATERIALS " +
                "ORDER BY MATERIAL_ID DESC";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Object[] row = {
                        resultSet.getInt("MATERIAL_ID"),
                        resultSet.getInt("SUBJECT_ID"),
                        resultSet.getString("MATERIAL_NAME"),
                        resultSet.getString("MATERIAL_TYPE"),
                        resultSet.getString("FILE_PATH"),
                        resultSet.getTimestamp("UPLOADED_AT")
                };

                tableModel.addRow(row);
            }

            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "No study materials found.",
                        "Saved Materials",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not load saved materials:\n" + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    private void openSelectedFile() {
        int row = materialsTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a material first."
            );
            return;
        }

        int modelRow = materialsTable.convertRowIndexToModel(row);
        String filePath = String.valueOf(
                tableModel.getValueAt(modelRow, 4)
        );

        java.io.File file = new java.io.File(filePath);

        if (!file.exists()) {
            JOptionPane.showMessageDialog(
                    this,
                    "File not found:\n" + filePath,
                    "File Not Found",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        try {
            if (java.awt.Desktop.isDesktopSupported()) {
                java.awt.Desktop.getDesktop().open(file);
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Opening files is not supported on this computer."
                );
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not open file:\n" + ex.getMessage(),
                    "Open Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    
private void generateMCQsFromSelectedFile() {
    int row = materialsTable.getSelectedRow();

    if (row == -1) {
        JOptionPane.showMessageDialog(
                this,
                "Please select a study material first."
        );
        return;
    }

    int modelRow = materialsTable.convertRowIndexToModel(row);

    String filePath = String.valueOf(
            tableModel.getValueAt(modelRow, 4)
    );

    int subjectId = Integer.parseInt(
            String.valueOf(tableModel.getValueAt(modelRow, 1))
    );

    java.io.File file = new java.io.File(filePath);

    if (!file.exists()) {
        JOptionPane.showMessageDialog(
                this,
                "File not found:\n" + filePath,
                "File Not Found",
                JOptionPane.ERROR_MESSAGE
        );
        return;
    }

    String materialText;

    try {
        Tika tika = new Tika();
        materialText = tika.parseToString(file);

        if (materialText == null || materialText.trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "No readable text found in this file."
            );
            return;
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(
                this,
                "Could not extract text:\n" + ex.getMessage(),
                "Extraction Error",
                JOptionPane.ERROR_MESSAGE
        );
        return;
    }

    String input = JOptionPane.showInputDialog(
            this,
            "How many MCQs do you want? (1-30)",
            "10"
    );

    if (input == null) {
        return;
    }

    int numberOfQuestions;

    try {
        numberOfQuestions = Integer.parseInt(input.trim());

        if (numberOfQuestions < 1 || numberOfQuestions > 30) {
            throw new NumberFormatException();
        }
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(
                this,
                "Please enter a number between 1 and 30."
        );
        return;
    }

    final String textToProcess = materialText;
    final int questionCount = numberOfQuestions;

    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

    SwingWorker<String, Void> worker = new SwingWorker<>() {
        @Override
        protected String doInBackground() throws Exception {
            GeminiAIService aiService = new GeminiAIService();
            return aiService.generateMCQs(textToProcess, questionCount);
        }

        @Override
        protected void done() {
            setCursor(Cursor.getDefaultCursor());

            try {
                String result = get();

                if (result == null || result.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(
                            StudyMaterialsListForm.this,
                            "AI did not generate any MCQs."
                    );
                    return;
                }

                AIQuizForm quizForm = new AIQuizForm(
                        result,
                        loggedInStudent.getStudentId(),
                        subjectId
                );

                quizForm.setLocationRelativeTo(
                        StudyMaterialsListForm.this
                );
                quizForm.setVisible(true);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        StudyMaterialsListForm.this,
                        "Could not generate MCQs:\n" + ex.getMessage(),
                        "AI Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    };

    worker.execute();
}


    private void deleteSelectedMaterial() {
        int row = materialsTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a material to delete."
            );
            return;
        }

        int modelRow = materialsTable.convertRowIndexToModel(row);

        int materialId = Integer.parseInt(
                String.valueOf(
                        tableModel.getValueAt(modelRow, 0)
                )
        );

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete material record ID " + materialId + "?\n"
                        + "The original file on your computer will not be deleted.",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        String sql =
                "DELETE FROM STUDY_MATERIALS WHERE MATERIAL_ID = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, materialId);
            int deleted = statement.executeUpdate();

            if (deleted > 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Material record deleted successfully."
                );
                loadMaterials();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Material record was not found."
                );
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not delete material:\n" + ex.getMessage(),
                    "Delete Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

}
