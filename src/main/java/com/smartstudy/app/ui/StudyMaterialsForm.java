
package com.smartstudy.app.ui;

import com.smartstudy.app.config.DatabaseConfig;
import com.smartstudy.app.dao.SubjectDAO;
import com.smartstudy.app.model.Student;
import com.smartstudy.app.model.Subject;

import org.apache.tika.Tika;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

public class StudyMaterialsForm extends JFrame {

    private final Student loggedInStudent;
    private final JComboBox<Subject> subjectComboBox;
    private final JLabel fileNameLabel;
    private final JTextArea extractedTextArea;

    private File selectedFile;
    private String extractedText = "";

    public StudyMaterialsForm(Student student) {

        loggedInStudent = student;

        setTitle("AI Smart Study Planner - Study Materials");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBackground(new Color(7, 21, 23));
        setLayout(new BorderLayout(10, 10));
        UIManager.put("ComboBox.background", new Color(11, 37, 40));
UIManager.put("ComboBox.foreground", new Color(240, 248, 250));
UIManager.put("Button.background", new Color(11, 37, 40));
UIManager.put("Button.foreground", new Color(121, 210, 232));
UIManager.put("Button.select", new Color(28, 66, 70));
UIManager.put("Button.focus", new Color(11, 37, 40));
UIManager.put("Button.opaque", true);
UIManager.put("OptionPane.messageForeground", new Color(240, 248, 250));
UIManager.put("OptionPane.foreground", new Color(240, 248, 250));
UIManager.put("OptionPane.background", new Color(7, 21, 23));
UIManager.put("Panel.background", new Color(7, 21, 23));
        getContentPane().setBackground(new Color(7, 21, 23));

        
subjectComboBox = new JComboBox<>();
subjectComboBox.setBackground(new Color(11, 37, 40));
subjectComboBox.setForeground(new Color(240, 248, 250));
subjectComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
subjectComboBox.setOpaque(true);

subjectComboBox.setRenderer(new DefaultListCellRenderer() {
    @Override
    public Component getListCellRendererComponent(
            JList<?> list, Object value, int index,
            boolean isSelected, boolean cellHasFocus) {

        super.getListCellRendererComponent(
                list, value, index, isSelected, cellHasFocus);

        setBackground(isSelected
                ? new Color(28, 66, 70)
                : new Color(11, 37, 40));

        setForeground(new Color(240, 248, 250));
        setOpaque(true);
        return this;
    }
});

subjectComboBox.setUI(new javax.swing.plaf.basic.BasicComboBoxUI() {
    @Override
    protected JButton createArrowButton() {
        JButton arrowButton = new JButton("▼");
        arrowButton.setBackground(new Color(11, 37, 40));
        arrowButton.setForeground(new Color(121, 210, 232));
        arrowButton.setOpaque(true);
        arrowButton.setContentAreaFilled(true);
        arrowButton.setBorder(
                BorderFactory.createEmptyBorder(0, 8, 0, 8));
        return arrowButton;
    }
});

        fileNameLabel = new JLabel("No file selected");
fileNameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
fileNameLabel.setForeground(new Color(160, 190, 198)); 

        
extractedTextArea = new JTextArea();
extractedTextArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
extractedTextArea.setLineWrap(true);
extractedTextArea.setWrapStyleWord(true);
extractedTextArea.setEditable(false);
extractedTextArea.setBackground(new Color(11, 37, 40));
extractedTextArea.setForeground(new Color(240, 248, 250));
extractedTextArea.setCaretColor(new Color(121, 210, 232));
extractedTextArea.setBorder(
        BorderFactory.createEmptyBorder(12, 12, 12, 12)
);


        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));

        JLabel heading = new JLabel("Study Materials");
heading.setFont(new Font("Segoe UI", Font.BOLD, 26));
heading.setForeground(new Color(121, 210, 232));

        JPanel subjectPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel subjectLabel = new JLabel("Select Subject:");
subjectLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
subjectLabel.setForeground(new Color(240, 248, 250));
subjectPanel.add(subjectLabel);
        subjectPanel.add(subjectComboBox);

       
JButton chooseButton = new JButton("Choose PDF / PPT / PPTX");
chooseButton.addActionListener(e -> chooseAndExtractFile());

JButton uploadButton = new JButton("Save Material to Database");
uploadButton.addActionListener(e -> saveMaterialToDatabase());

JButton generateMCQButton = new JButton("Generate MCQs with AI");
generateMCQButton.addActionListener(e -> generateMCQsWithAI());

JButton[] actionButtons = {
        chooseButton, uploadButton, generateMCQButton
};



for (JButton button : actionButtons) {
    button.setFont(new Font("Segoe UI", Font.BOLD, 12));
    button.setBackground(new Color(11, 37, 40));
    button.setForeground(new Color(121, 210, 232));
    button.setOpaque(true);
    button.setContentAreaFilled(true);
    button.setFocusPainted(false);
    button.setBorderPainted(true);
    button.setUI(new javax.swing.plaf.basic.BasicButtonUI());
    button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(28, 66, 70), 1),
            BorderFactory.createEmptyBorder(7, 12, 7, 12)
    ));
}





        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonsPanel.add(chooseButton);
        buttonsPanel.add(uploadButton);
        buttonsPanel.add(generateMCQButton);

        topPanel.add(heading);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(subjectPanel);
        topPanel.add(fileNameLabel);
        topPanel.add(buttonsPanel);

      
JScrollPane scrollPane = new JScrollPane(extractedTextArea);

javax.swing.border.TitledBorder materialBorder =
        BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(28, 66, 70), 1),
                "Extracted Study Material"
        );

materialBorder.setTitleColor(new Color(121, 210, 232));
materialBorder.setTitleFont(new Font("Segoe UI", Font.BOLD, 13));

scrollPane.setBorder(materialBorder);
scrollPane.getViewport().setBackground(new Color(11, 37, 40));
scrollPane.setBackground(new Color(7, 21, 23));
scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        

JButton saveTextButton = new JButton("Save Extracted Text");
saveTextButton.addActionListener(e -> saveExtractedText());

saveTextButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
saveTextButton.setBackground(new Color(11, 37, 40));
saveTextButton.setForeground(new Color(121, 210, 232));
saveTextButton.setOpaque(true);
saveTextButton.setContentAreaFilled(true);
saveTextButton.setFocusPainted(false);
saveTextButton.setBorderPainted(true);
saveTextButton.setUI(new javax.swing.plaf.basic.BasicButtonUI());
saveTextButton.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(28, 66, 70), 1),
        BorderFactory.createEmptyBorder(7, 12, 7, 12)
));



        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(saveTextButton);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        loadSubjects();
    }

    private void loadSubjects() {

        if (loggedInStudent == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Student information is unavailable. Please login again.",
                    "Student Not Found",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        try {
            SubjectDAO subjectDAO = new SubjectDAO();

            List<Subject> subjects =
                    subjectDAO.getSubjectsByStudentId(
                            loggedInStudent.getStudentId()
                    );

            for (Subject subject : subjects) {
                subjectComboBox.addItem(subject);
            }

            if (subjects.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "No subjects found for this student. Add a subject first.",
                        "No Subjects",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not load subjects:\n" + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void chooseAndExtractFile() {

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select Study Material");

        if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file = chooser.getSelectedFile();
        String name = file.getName().toLowerCase();

        if (!(name.endsWith(".pdf")
                || name.endsWith(".ppt")
                || name.endsWith(".pptx"))) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a PDF, PPT, or PPTX file.",
                    "Unsupported File",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            Tika tika = new Tika();
            String text = tika.parseToString(file);

            selectedFile = file;
            extractedText = text;

            fileNameLabel.setText("Selected file: " + file.getName());
            extractedTextArea.setText(text);
            extractedTextArea.setCaretPosition(0);

            JOptionPane.showMessageDialog(
                    this,
                    "Study material text extracted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not read the file:\n" + ex.getMessage(),
                    "Extraction Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void saveMaterialToDatabase() {

        Subject subject =
                (Subject) subjectComboBox.getSelectedItem();

        if (subject == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a subject first."
            );
            return;
        }

        if (selectedFile == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please choose a PDF, PPT, or PPTX file first."
            );
            return;
        }

        String fileName = selectedFile.getName();
        String lowerName = fileName.toLowerCase();
        String materialType;

        if (lowerName.endsWith(".pdf")) {
            materialType = "PDF";
        } else if (lowerName.endsWith(".pptx")) {
            materialType = "PPTX";
        } else {
            materialType = "PPT";
        }

        String sql =
                "INSERT INTO STUDY_MATERIALS " +
                "(SUBJECT_ID, MATERIAL_NAME, MATERIAL_TYPE, FILE_PATH, UPLOADED_AT) " +
                "VALUES (?, ?, ?, ?, SYSDATE)";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, subject.getSubjectId());
            statement.setString(2, fileName);
            statement.setString(3, materialType);
            statement.setString(4, selectedFile.getAbsolutePath());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Material record saved successfully!\n\n"
                                + "Subject: " + subject.getSubjectName()
                                + "\nFile: " + fileName,
                        "Upload Successful",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not save material to database:\n"
                            + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void saveExtractedText() {

        if (extractedText.trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a file and extract its text first."
            );
            return;
        }

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save Extracted Text");
        chooser.setSelectedFile(new File("extracted.txt"));

        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File outputFile = chooser.getSelectedFile();

        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(extractedText);

            JOptionPane.showMessageDialog(
                    this,
                    "Extracted text saved to:\n"
                            + outputFile.getAbsolutePath(),
                    "Saved",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not save text:\n" + ex.getMessage(),
                    "Save Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
private void generateMCQsWithAI() {

    if (extractedText == null || extractedText.isBlank()) {
        JOptionPane.showMessageDialog(
                this,
                "Please select a PDF, PPT, or PPTX file first.",
                "No Study Material",
                JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    String input = JOptionPane.showInputDialog(
            this,
            "How many MCQs do you want to generate?",
            "Generate MCQs with AI",
            JOptionPane.QUESTION_MESSAGE
    );

    if (input == null) {
        return;
    }

    int numberOfQuestions;

    try {
        numberOfQuestions = Integer.parseInt(input.trim());

        if (numberOfQuestions < 1 || numberOfQuestions > 30) {
            JOptionPane.showMessageDialog(
                    this,
                    "Enter a number between 1 and 30."
            );
            return;
        }

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(
                this,
                "Please enter a valid number."
        );
        return;
    }

    extractedTextArea.setText("Generating MCQs with Gemini AI...");

    new SwingWorker<String, Void>() {

        @Override
        protected String doInBackground() throws Exception {
            com.smartstudy.app.ai.GeminiAIService aiService =
                    new com.smartstudy.app.ai.GeminiAIService();

            return aiService.generateMCQs(
                    extractedText,
                    numberOfQuestions
            );
        }

        @Override
        protected void done() {
            try {
                
String result = get();

Subject selectedSubject = (Subject) subjectComboBox.getSelectedItem();

if (selectedSubject == null) {
    JOptionPane.showMessageDialog(
            StudyMaterialsForm.this,
            "Please select a subject first.",
            "No Subject Selected",
            JOptionPane.WARNING_MESSAGE
    );
    return;
}

AIQuizForm quizForm = new AIQuizForm(
        result,
        loggedInStudent.getStudentId(),
        selectedSubject.getSubjectId()
);
quizForm.setVisible(true);

JOptionPane.showMessageDialog(
        StudyMaterialsForm.this,
        "MCQs generated successfully! Quiz is ready.",
        "AI Success",
        JOptionPane.INFORMATION_MESSAGE
);

            } catch (Exception ex) {
                extractedTextArea.setText(ex.getMessage());

                JOptionPane.showMessageDialog(
                        StudyMaterialsForm.this,
                        "Could not generate MCQs:\n" + ex.getMessage(),
                        "AI Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }

    }.execute();
}

}
