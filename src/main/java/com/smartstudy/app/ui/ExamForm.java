package com.smartstudy.app.ui;

import com.smartstudy.app.model.Exam;
import com.smartstudy.app.model.Student;
import com.smartstudy.app.model.Subject;
import com.smartstudy.app.service.ExamService;
import com.smartstudy.app.service.SubjectService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

/**
 * AI Smart Study Planner
 * Exam Add / Edit Form
 */
public class ExamForm extends JFrame {

    // =========================================================
    // COLORS
    // =========================================================

    private static final Color BACKGROUND =
            new Color(246, 248, 252);

    private static final Color CARD =
            Color.WHITE;

    private static final Color DARK_TEXT =
            new Color(30, 43, 61);

    private static final Color SECONDARY_TEXT =
            new Color(105, 116, 132);

    private static final Color BLUE =
            new Color(55, 155, 215);

    private static final Color BLUE_DARK =
            new Color(39, 132, 190);

    private static final Color BORDER =
            new Color(222, 226, 232);

    private static final Color RED =
            new Color(220, 76, 76);


    // =========================================================
    // DATA
    // =========================================================

    private final Student loggedInStudent;

    private final Exam editingExam;

    private ExamService examService;

    private SubjectService subjectService;


    // =========================================================
    // COMPONENTS
    // =========================================================

    private JComboBox<SubjectItem> subjectComboBox;

    private JTextField examNameField;

    private JTextField examDateField;

    private JTextField examTimeField;

    private JComboBox<String> examTypeComboBox;

    private JButton saveButton;

    private JButton cancelButton;


    // =========================================================
    // CONSTRUCTOR - ADD MODE
    // =========================================================

    public ExamForm(
            Student student
    ) {

        this(
                student,
                null
        );
    }


    // =========================================================
    // CONSTRUCTOR - ADD / EDIT MODE
    // =========================================================

    public ExamForm(
            Student student,
            Exam exam
    ) {

        loggedInStudent =
                student;

        editingExam =
                exam;

        try {

            examService =
                    new ExamService();

            subjectService =
                    new SubjectService();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to connect to database.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }


        initializeFrame();

        buildUI();

        loadSubjects();

        loadExamData();
    }


    // =========================================================
    // FRAME
    // =========================================================

    private void initializeFrame() {

        setTitle(
                editingExam == null
                        ? "Add Exam"
                        : "Edit Exam"
        );

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setSize(
                620,
                650
        );

        setMinimumSize(
                new Dimension(
                        560,
                        600
                )
        );

        setLocationRelativeTo(null);

        setResizable(false);
    }


    // =========================================================
    // BUILD UI
    // =========================================================

    private void buildUI() {

        JPanel root =
                new JPanel(
                        new BorderLayout()
                );

        root.setBackground(
                BACKGROUND
        );

        root.setBorder(
                new EmptyBorder(
                        25,
                        30,
                        25,
                        30
                )
        );


        // =====================================================
        // HEADER
        // =====================================================

        JPanel header =
                new JPanel();

        header.setOpaque(false);

        header.setLayout(
                new BoxLayout(
                        header,
                        BoxLayout.Y_AXIS
                )
        );


        JLabel title =
                new JLabel(
                        editingExam == null
                                ? "Add Exam"
                                : "Edit Exam"
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        30
                )
        );

        title.setForeground(
                DARK_TEXT
        );


        JLabel subtitle =
                new JLabel(
                        editingExam == null
                                ? "Add a new exam to your study planner."
                                : "Update exam information."
                );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        subtitle.setForeground(
                SECONDARY_TEXT
        );


        header.add(
                title
        );

        header.add(
                Box.createVerticalStrut(5)
        );

        header.add(
                subtitle
        );


        root.add(
                header,
                BorderLayout.NORTH
        );


        // =====================================================
        // FORM CARD
        // =====================================================

        JPanel card =
                new JPanel(
                        new GridBagLayout()
                );

        card.setBackground(
                CARD
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(
                                BORDER,
                                1,
                                true
                        ),
                        new EmptyBorder(
                                25,
                                25,
                                25,
                                25
                        )
                )
        );


        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        8,
                        8,
                        8,
                        8
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.weightx =
                1.0;


        // =====================================================
        // SUBJECT
        // =====================================================

        addLabel(
                card,
                gbc,
                "SUBJECT",
                0
        );


        subjectComboBox =
                new JComboBox<>();

        styleComboBox(
                subjectComboBox
        );

        addComponent(
                card,
                gbc,
                subjectComboBox,
                1
        );


        // =====================================================
        // EXAM NAME
        // =====================================================

        addLabel(
                card,
                gbc,
                "EXAM NAME",
                2
        );


        examNameField =
                new JTextField();

        styleTextField(
                examNameField
        );

        addComponent(
                card,
                gbc,
                examNameField,
                3
        );


        // =====================================================
        // EXAM DATE
        // =====================================================

        addLabel(
                card,
                gbc,
                "EXAM DATE",
                4
        );


        examDateField =
                new JTextField();

        styleTextField(
                examDateField
        );

        examDateField.setToolTipText(
                "Format: YYYY-MM-DD"
        );

        addComponent(
                card,
                gbc,
                examDateField,
                5
        );


        // =====================================================
        // EXAM TIME
        // =====================================================

        addLabel(
                card,
                gbc,
                "EXAM TIME",
                6
        );


        examTimeField =
                new JTextField();

        styleTextField(
                examTimeField
        );

        examTimeField.setToolTipText(
                "Example: 10:00 AM"
        );

        addComponent(
                card,
                gbc,
                examTimeField,
                7
        );


        // =====================================================
        // EXAM TYPE
        // =====================================================

        addLabel(
                card,
                gbc,
                "EXAM TYPE",
                8
        );


        examTypeComboBox =
        new JComboBox<>(
                new String[]{
                        "MCQ",
                        "SUBJECTIVE",
                        "MIXED"
                }
        );

        styleComboBox(
                examTypeComboBox
        );

        addComponent(
                card,
                gbc,
                examTypeComboBox,
                9
        );


        root.add(
                card,
                BorderLayout.CENTER
        );


        // =====================================================
        // BUTTON PANEL
        // =====================================================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                0
                        )
                );

        buttonPanel.setOpaque(false);


        cancelButton =
                new JButton(
                        "CANCEL"
                );

        styleSecondaryButton(
                cancelButton
        );


        saveButton =
                new JButton(
                        editingExam == null
                                ? "ADD EXAM"
                                : "UPDATE EXAM"
        );

        stylePrimaryButton(
                saveButton
        );


        buttonPanel.add(
                cancelButton
        );

        buttonPanel.add(
                saveButton
        );


        root.add(
                buttonPanel,
                BorderLayout.SOUTH
        );


        // =====================================================
        // BUTTON ACTIONS
        // =====================================================

        cancelButton.addActionListener(
                e -> dispose()
        );


        saveButton.addActionListener(
                e -> saveExam()
        );


        setContentPane(
                root
        );
    }


    // =========================================================
    // LOAD SUBJECTS
    // =========================================================

    private void loadSubjects() {

        if (
                loggedInStudent == null
                        || subjectService == null
        ) {

            return;
        }


        try {

            List<Subject> subjects =
                    subjectService
                            .getSubjectsByStudentId(
                                    loggedInStudent
                                            .getStudentId()
                            );


            subjectComboBox.removeAllItems();


            for (Subject subject : subjects) {

                subjectComboBox.addItem(
                        new SubjectItem(
                                subject.getSubjectId(),
                                subject.getSubjectName()
                        )
                );
            }


        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load subjects.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================================
    // LOAD EXAM DATA
    // =========================================================

    private void loadExamData() {

        if (editingExam == null) {
            return;
        }


        for (
                int i = 0;
                i < subjectComboBox.getItemCount();
                i++
        ) {

            SubjectItem item =
                    subjectComboBox.getItemAt(i);

            if (
                    item.getSubjectId()
                            == editingExam.getSubjectId()
            ) {

                subjectComboBox.setSelectedIndex(i);

                break;
            }
        }


        examNameField.setText(
                editingExam.getExamName()
        );

        examDateField.setText(
                editingExam.getExamDate()
        );

        examTimeField.setText(
                editingExam.getExamTime()
        );

        examTypeComboBox.setSelectedItem(
                editingExam.getExamType()
        );
    }


    // =========================================================
    // SAVE EXAM
    // =========================================================

    private void saveExam() {

        if (
                examService == null
                        || loggedInStudent == null
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Exam service is not available.",
                    "Exam",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        SubjectItem selectedSubject =
                (SubjectItem)
                        subjectComboBox
                                .getSelectedItem();


        String examName =
                examNameField
                        .getText()
                        .trim();


        String examDate =
                examDateField
                        .getText()
                        .trim();


        String examTime =
                examTimeField
                        .getText()
                        .trim();


        String examType =
                (String)
                        examTypeComboBox
                                .getSelectedItem();


        // =====================================================
        // VALIDATION
        // =====================================================

        if (selectedSubject == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a subject.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        if (examName.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter exam name.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            examNameField.requestFocus();

            return;
        }


        if (examDate.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter exam date.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            examDateField.requestFocus();

            return;
        }


        if (examTime.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter exam time.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            examTimeField.requestFocus();

            return;
        }


        // =====================================================
        // DATE VALIDATION
        // =====================================================

        try {

            java.time.LocalDate.parse(
                    examDate
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid date.\nUse format: YYYY-MM-DD",
                    "Invalid Date",
                    JOptionPane.WARNING_MESSAGE
            );

            examDateField.requestFocus();

            return;
        }


        // =====================================================
        // CREATE / UPDATE EXAM
        // =====================================================

        try {

            boolean success;


            if (editingExam == null) {

                Exam exam =
                        new Exam(
                                selectedSubject
                                        .getSubjectId(),
                                examName,
                                examDate,
                                examTime,
                                examType
                        );


                success =
                        examService.addExam(
                                exam
                        );


            } else {

                editingExam.setSubjectId(
                        selectedSubject
                                .getSubjectId()
                );

                editingExam.setExamName(
                        examName
                );

                editingExam.setExamDate(
                        examDate
                );

                editingExam.setExamTime(
                        examTime
                );

                editingExam.setExamType(
                        examType
                );


                success =
                        examService.updateExam(
                                editingExam
                        );
            }


            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        editingExam == null
                                ? "Exam added successfully!"
                                : "Exam updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Unable to save exam.",
                        "Exam",
                        JOptionPane.ERROR_MESSAGE
                );
            }


        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database error while saving exam.\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================================
    // ADD LABEL
    // =========================================================

    private void addLabel(
            JPanel panel,
            GridBagConstraints gbc,
            String text,
            int row
    ) {

        JLabel label =
                new JLabel(
                        text
                );

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        label.setForeground(
                SECONDARY_TEXT
        );


        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;


        panel.add(
                label,
                gbc
        );
    }


    // =========================================================
    // ADD COMPONENT
    // =========================================================

    private void addComponent(
            JPanel panel,
            GridBagConstraints gbc,
            Component component,
            int row
    ) {

        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 2;


        panel.add(
                component,
                gbc
        );
    }


    // =========================================================
    // TEXT FIELD STYLE
    // =========================================================

    private void styleTextField(
            JTextField field
    ) {

        field.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        field.setForeground(
                DARK_TEXT
        );

        field.setBackground(
                Color.WHITE
        );

        field.setBorder(
                new LineBorder(
                        BORDER,
                        1,
                        true
                )
        );

        field.setPreferredSize(
                new Dimension(
                        350,
                        42
                )
        );
    }


    // =========================================================
    // COMBO BOX STYLE
    // =========================================================

    private void styleComboBox(
            JComboBox<?> comboBox
    ) {

        comboBox.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        comboBox.setForeground(
                DARK_TEXT
        );

        comboBox.setBackground(
                Color.WHITE
        );

        comboBox.setBorder(
                new LineBorder(
                        BORDER,
                        1,
                        true
                )
        );

        comboBox.setPreferredSize(
                new Dimension(
                        350,
                        42
                )
        );
    }


    // =========================================================
    // PRIMARY BUTTON
    // =========================================================

    private void stylePrimaryButton(
            JButton button
    ) {

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        button.setForeground(
                Color.WHITE
        );

        button.setBackground(
                BLUE
        );

        button.setFocusPainted(
                false
        );

        button.setBorderPainted(
                false
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setPreferredSize(
                new Dimension(
                        135,
                        44
                )
        );


        button.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent e
                    ) {

                        button.setBackground(
                                BLUE_DARK
                        );
                    }


                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e
                    ) {

                        button.setBackground(
                                BLUE
                        );
                    }
                }
        );
    }


    // =========================================================
    // SECONDARY BUTTON
    // =========================================================

    private void styleSecondaryButton(
            JButton button
    ) {

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        button.setForeground(
                DARK_TEXT
        );

        button.setBackground(
                Color.WHITE
        );

        button.setFocusPainted(
                false
        );

        button.setBorder(
                new LineBorder(
                        BORDER,
                        1,
                        true
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setPreferredSize(
                new Dimension(
                        115,
                        44
                )
        );
    }


    // =========================================================
    // SUBJECT ITEM
    // =========================================================

    private static class SubjectItem {

        private final int subjectId;

        private final String subjectName;


        public SubjectItem(
                int subjectId,
                String subjectName
        ) {

            this.subjectId =
                    subjectId;

            this.subjectName =
                    subjectName;
        }


        public int getSubjectId() {

            return subjectId;
        }


        @Override
        public String toString() {

            return subjectName;
        }
    }
}