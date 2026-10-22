package com.smartstudy.app.ui;

import java.sql.SQLException;

import com.smartstudy.app.model.Student;
import com.smartstudy.app.model.Subject;
import com.smartstudy.app.service.SubjectService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;

public class SubjectForm extends JFrame {

    // =========================================================
    // COLORS
    // =========================================================

    private static final Color BACKGROUND =
            new Color(247, 248, 252);

    private static final Color CARD =
            Color.WHITE;

    private static final Color DARK_TEXT =
            new Color(35, 48, 68);

    private static final Color SECONDARY_TEXT =
            new Color(105, 115, 130);

    private static final Color BLUE =
            new Color(55, 155, 215);

    private static final Color BORDER =
            new Color(220, 224, 230);

    private static final Color FIELD =
            new Color(248, 250, 253);

    private static final Color RED =
            new Color(225, 65, 70);


    // =========================================================
    // COMPONENTS
    // =========================================================

    private JTextField subjectNameField;

    private JComboBox<String> difficultyComboBox;

    private JTextField preparationField;

    private JTextField targetField;

    private JButton addSubjectButton;

    private JButton clearButton;


    // =========================================================
    // DATA
    // =========================================================

    private final Student loggedInStudent;

    private final Subject editingSubject;

    private SubjectService subjectService;


    // =========================================================
    // ADD MODE CONSTRUCTOR
    // =========================================================

    public SubjectForm(Student student) {

        this(
                student,
                null
        );
    }


    // =========================================================
    // EDIT MODE CONSTRUCTOR
    // =========================================================

    public SubjectForm(
            Student student,
            Subject subject
    ) {

        loggedInStudent =
                student;

        editingSubject =
                subject;

        try {

            subjectService =
                    new SubjectService();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to connect to database.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        initializeFrame();

        buildUI();

        loadSubjectData();
    }


    // =========================================================
    // FRAME
    // =========================================================

    private void initializeFrame() {

        if (editingSubject == null) {

            setTitle(
                    "AI Smart Study Planner - Add Subject"
            );

        } else {

            setTitle(
                    "AI Smart Study Planner - Edit Subject"
            );
        }

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setSize(
                850,
                650
        );

        setMinimumSize(
                new Dimension(
                        750,
                        600
                )
        );

        setLocationRelativeTo(null);

        setResizable(true);
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
                        35,
                        45,
                        35,
                        45
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
                        editingSubject == null
                                ? "Add Subject"
                                : "Edit Subject"
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        32
                )
        );

        title.setForeground(
                DARK_TEXT
        );


        JLabel subtitle =
                new JLabel(
                        editingSubject == null
                                ? "Add a subject to your academic study planner."
                                : "Update your subject details and preparation progress."
                );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        subtitle.setForeground(
                SECONDARY_TEXT
        );


        header.add(title);

        header.add(
                Box.createVerticalStrut(6)
        );

        header.add(subtitle);


        root.add(
                header,
                BorderLayout.NORTH
        );


        // =====================================================
        // CARD
        // =====================================================

        JPanel card =
                new JPanel();

        card.setBackground(
                CARD
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(
                                BORDER,
                                1
                        ),
                        new EmptyBorder(
                                35,
                                45,
                                35,
                                45
                        )
                )
        );

        card.setLayout(
                new GridBagLayout()
        );


        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        10,
                        10,
                        10,
                        10
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;


        // =====================================================
        // SUBJECT NAME
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;

        card.add(
                createLabel(
                        "SUBJECT NAME"
                ),
                gbc
        );


        gbc.gridx = 1;

        card.add(
                createLabel(
                        "DIFFICULTY"
                ),
                gbc
        );


        gbc.gridx = 0;
        gbc.gridy = 1;

        subjectNameField =
                createTextField();

        card.add(
                subjectNameField,
                gbc
        );


        gbc.gridx = 1;

        difficultyComboBox =
                new JComboBox<>(
                        new String[]{
                                "Select difficulty",
                                "Easy",
                                "Medium",
                                "Hard"
                        }
                );

        styleComboBox(
                difficultyComboBox
        );

        card.add(
                difficultyComboBox,
                gbc
        );


        difficultyComboBox.addActionListener(
                e -> updateDifficultyColor()
        );


        // =====================================================
        // PREPARATION
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 2;

        card.add(
                createLabel(
                        "PREPARATION PERCENTAGE"
                ),
                gbc
        );


        gbc.gridx = 1;

        card.add(
                createLabel(
                        "TARGET PERCENTAGE"
                ),
                gbc
        );


        gbc.gridx = 0;
        gbc.gridy = 3;

        preparationField =
                createTextField();

        preparationField.setToolTipText(
                "Enter percentage from 0 to 100"
        );

        card.add(
                preparationField,
                gbc
        );


        gbc.gridx = 1;

        targetField =
                createTextField();

        targetField.setToolTipText(
                "Enter percentage from 0 to 100"
        );

        card.add(
                targetField,
                gbc
        );


        // =====================================================
        // BUTTONS
        // =====================================================

        JPanel buttonPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                15,
                                0
                        )
                );

        buttonPanel.setOpaque(false);


        addSubjectButton =
                new JButton(
                        editingSubject == null
                                ? "ADD SUBJECT"
                                : "UPDATE SUBJECT"
                );

        stylePrimaryButton(
                addSubjectButton
        );


        clearButton =
                new JButton(
                        "CLEAR"
                );

        styleClearButton(
                clearButton
        );


        buttonPanel.add(
                addSubjectButton
        );

        buttonPanel.add(
                clearButton
        );


        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;

        gbc.insets =
                new Insets(
                        30,
                        10,
                        10,
                        10
                );

        card.add(
                buttonPanel,
                gbc
        );


        // =====================================================
        // STUDENT INFORMATION
        // =====================================================

        String studentInfo =
                getStudentInfo();


        JLabel studentLabel =
                new JLabel(
                        studentInfo
                );

        studentLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        studentLabel.setForeground(
                SECONDARY_TEXT
        );

        gbc.gridy = 5;

        gbc.insets =
                new Insets(
                        15,
                        10,
                        5,
                        10
                );

        card.add(
                studentLabel,
                gbc
        );


        root.add(
                card,
                BorderLayout.CENTER
        );


        // =====================================================
        // BUTTON ACTIONS
        // =====================================================

        addSubjectButton.addActionListener(
                e -> saveSubject()
        );


        clearButton.addActionListener(
                e -> clearForm()
        );


        setContentPane(root);
    }


    // =========================================================
    // LOAD SUBJECT DATA FOR EDIT MODE
    // =========================================================

    private void loadSubjectData() {

        if (editingSubject == null) {

            return;
        }


        subjectNameField.setText(
                editingSubject.getSubjectName()
        );


        String difficulty =
                editingSubject.getDifficulty();


        if (difficulty != null) {

            String formattedDifficulty =
                    difficulty.substring(0, 1).toUpperCase()
                            + difficulty.substring(1)
                            .toLowerCase();

            difficultyComboBox.setSelectedItem(
                    formattedDifficulty
            );
        }


        preparationField.setText(
                formatNumber(
                        editingSubject
                                .getPreparationPercentage()
                )
        );


        targetField.setText(
                formatNumber(
                        editingSubject
                                .getTargetPercentage()
                )
        );


        updateDifficultyColor();
    }


    // =========================================================
    // UPDATE DIFFICULTY COLOR
    // =========================================================

    private void updateDifficultyColor() {

        if (difficultyComboBox == null) {

            return;
        }


        if (
                difficultyComboBox.getSelectedIndex()
                        == 0
        ) {

            difficultyComboBox.setBackground(
                    Color.WHITE
            );

        } else {

            difficultyComboBox.setBackground(
                    RED
            );
        }
    }


    // =========================================================
    // FORMAT NUMBER
    // =========================================================

    private String formatNumber(
            double value
    ) {

        if (value == Math.floor(value)) {

            return String.format(
                    "%.0f",
                    value
            );
        }

        return String.format(
                "%.2f",
                value
        );
    }


    // =========================================================
    // LABEL
    // =========================================================

    private JLabel createLabel(
            String text
    ) {

        JLabel label =
                new JLabel(
                        text
                );

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        label.setForeground(
                DARK_TEXT
        );

        return label;
    }


    // =========================================================
    // TEXT FIELD
    // =========================================================

    private JTextField createTextField() {

        JTextField field =
                new JTextField();

        field.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        field.setForeground(
                DARK_TEXT
        );

        field.setBackground(
                FIELD
        );

        field.setCaretColor(
                BLUE
        );

        field.setBorder(
                new LineBorder(
                        BORDER,
                        1
                )
        );

        field.setPreferredSize(
                new Dimension(
                        300,
                        48
                )
        );

        return field;
    }


    // =========================================================
    // COMBO BOX
    // =========================================================

    private void styleComboBox(
            JComboBox<String> comboBox
    ) {

        comboBox.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        comboBox.setOpaque(true);

        comboBox.setBackground(
                Color.WHITE
        );

        comboBox.setBorder(
                new LineBorder(
                        BORDER,
                        1
                )
        );

        comboBox.setPreferredSize(
                new Dimension(
                        300,
                        48
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
                        14
                )
        );

        button.setForeground(
                Color.WHITE
        );

        button.setBackground(
                BLUE
        );

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setPreferredSize(
                new Dimension(
                        200,
                        45
                )
        );
    }


    // =========================================================
    // CLEAR BUTTON
    // =========================================================

    private void styleClearButton(
            JButton button
    ) {

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        button.setForeground(
                DARK_TEXT
        );

        button.setBackground(
                new Color(
                        235,
                        238,
                        243
                )
        );

        button.setFocusPainted(false);

        button.setBorder(
                new LineBorder(
                        BORDER,
                        1
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );
    }


    // =========================================================
    // STUDENT INFO
    // =========================================================

    private String getStudentInfo() {

        if (loggedInStudent == null) {

            return "Student: Not Available";
        }

        return "Student: "
                + loggedInStudent.getName()
                + "   •   Semester "
                + loggedInStudent.getSemester();
    }


    // =========================================================
    // SAVE SUBJECT
    // =========================================================

    private void saveSubject() {

        String subjectName =
                subjectNameField
                        .getText()
                        .trim();


        String difficulty =
                ((String)
                        difficultyComboBox
                                .getSelectedItem());


        String preparationText =
                preparationField
                        .getText()
                        .trim();


        String targetText =
                targetField
                        .getText()
                        .trim();


        // =====================================================
        // VALIDATION
        // =====================================================

        if (subjectName.isEmpty()) {

            showError(
                    "Please enter subject name."
            );

            subjectNameField.requestFocus();

            return;
        }


        if (
                difficulty == null
                        || difficulty.equals(
                        "Select difficulty"
                )
        ) {

            showError(
                    "Please select subject difficulty."
            );

            difficultyComboBox.requestFocus();

            return;
        }


        if (preparationText.isEmpty()) {

            showError(
                    "Please enter preparation percentage."
            );

            preparationField.requestFocus();

            return;
        }


        if (targetText.isEmpty()) {

            showError(
                    "Please enter target percentage."
            );

            targetField.requestFocus();

            return;
        }


        double preparation;

        double target;


        try {

            preparation =
                    Double.parseDouble(
                            preparationText
                    );

            target =
                    Double.parseDouble(
                            targetText
                    );

        } catch (NumberFormatException e) {

            showError(
                    "Percentage must contain numbers only."
            );

            return;
        }


        if (
                preparation < 0
                        || preparation > 100
        ) {

            showError(
                    "Preparation percentage must be between 0 and 100."
            );

            preparationField.requestFocus();

            return;
        }


        if (
                target < 0
                        || target > 100
        ) {

            showError(
                    "Target percentage must be between 0 and 100."
            );

            targetField.requestFocus();

            return;
        }


        if (loggedInStudent == null) {

            showError(
                    "Student information is not available."
            );

            return;
        }


        if (subjectService == null) {

            showError(
                    "Subject service is not available."
            );

            return;
        }


        // =====================================================
        // DIFFICULTY
        // =====================================================

        difficulty =
                difficulty.toUpperCase();


        // =====================================================
        // CREATE / UPDATE SUBJECT
        // =====================================================

        Subject subject;


        if (editingSubject == null) {

            subject =
                    new Subject(
                            loggedInStudent.getStudentId(),
                            subjectName,
                            difficulty,
                            preparation,
                            target
                    );

        } else {

            subject =
                    new Subject(
                            loggedInStudent.getStudentId(),
                            subjectName,
                            difficulty,
                            preparation,
                            target
                    );

            subject.setSubjectId(
                    editingSubject.getSubjectId()
            );
        }


        // =====================================================
        // DATABASE
        // =====================================================

        try {

            addSubjectButton.setEnabled(
                    false
            );


            if (editingSubject == null) {

                addSubjectButton.setText(
                        "ADDING..."
                );


                boolean added =
                        subjectService.addSubject(
                                subject
                        );


                if (added) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Subject added successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    clearForm();

                } else {

                    showError(
                            "Unable to add subject."
                    );
                }

            } else {

                addSubjectButton.setText(
                        "UPDATING..."
                );


                boolean updated =
                        subjectService.updateSubject(
                                subject
                        );


                if (updated) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Subject updated successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    dispose();

                } else {

                    showError(
                            "Unable to update subject."
                    );
                }
            }


        } catch (SQLException e) {

            e.printStackTrace();

            showError(
                    "Database error while saving subject."
            );

        } finally {

            addSubjectButton.setEnabled(
                    true
            );


            if (editingSubject == null) {

                addSubjectButton.setText(
                        "ADD SUBJECT"
                );

            } else {

                addSubjectButton.setText(
                        "UPDATE SUBJECT"
                );
            }
        }
    }


    // =========================================================
    // CLEAR FORM
    // =========================================================

    private void clearForm() {

        subjectNameField.setText("");

        difficultyComboBox.setSelectedIndex(
                0
        );

        preparationField.setText("");

        targetField.setText("");

        updateDifficultyColor();

        subjectNameField.requestFocus();
    }


    // =========================================================
    // ERROR
    // =========================================================

    private void showError(
            String message
    ) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Subject",
                JOptionPane.ERROR_MESSAGE
        );
    }


    // =========================================================
    // MAIN
    // =========================================================

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                () -> {

                    try {

                        UIManager.setLookAndFeel(
                                UIManager
                                        .getSystemLookAndFeelClassName()
                        );

                    } catch (Exception ignored) {
                    }


                    SubjectForm form =
                            new SubjectForm(
                                    null
                            );

                    form.setVisible(true);
                }
        );
    }
}