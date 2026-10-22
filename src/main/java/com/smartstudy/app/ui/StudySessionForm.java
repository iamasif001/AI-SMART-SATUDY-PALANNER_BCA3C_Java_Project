package com.smartstudy.app.ui;

import com.smartstudy.app.model.Student;
import com.smartstudy.app.model.StudySession;
import com.smartstudy.app.model.Subject;
import com.smartstudy.app.service.StudySessionService;
import com.smartstudy.app.service.SubjectService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * AI Smart Study Planner
 * Study Session Add / Edit Form
 */
public class StudySessionForm extends JFrame {

   
private static final Color BACKGROUND = new Color(7, 20, 22);
private static final Color CARD = new Color(11, 37, 40);
private static final Color DARK_TEXT = new Color(240, 248, 250);
private static final Color SECONDARY_TEXT = new Color(155, 184, 190);
private static final Color BLUE = new Color(121, 210, 232);
private static final Color BLUE_DARK = new Color(75, 175, 200);
private static final Color BORDER = new Color(28, 66, 70);
private static final Color FIELD = new Color(7, 27, 30);


    private final Student loggedInStudent;
    private final StudySession editingSession;

    private StudySessionService studySessionService;
    private SubjectService subjectService;

    private JComboBox<SubjectItem> subjectComboBox;
    private JTextField sessionDateField;
    private JTextField durationField;
    private JTextField topicField;
    private JTextArea notesArea;

    private JButton saveButton;
    private JButton cancelButton;

    public StudySessionForm(
            Student student
    ) {

        this(
                student,
                null
        );
    }

    public StudySessionForm(
            Student student,
            StudySession session
    ) {

        loggedInStudent =
                student;

        editingSession =
                session;

        try {

            studySessionService =
                    new StudySessionService();

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

        loadSessionData();
    }

    // =========================================================
    // FRAME
    // =========================================================

    private void initializeFrame() {

        setTitle(
                editingSession == null
                        ? "Add Study Session"
                        : "Edit Study Session"
        );

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

       
setSize(1000, 650);

setMinimumSize(
        new Dimension(700, 650)
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
        15,
        20,
        15,
        20
)
        );

        // -----------------------------------------------------
        // HEADER
        // -----------------------------------------------------

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
                        editingSession == null
                                ? "Add Study Session"
                                : "Edit Study Session"
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
                        editingSession == null
                                ? "Record a new study session."
                                : "Update your study session details."
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

        // -----------------------------------------------------
// TWO-PANEL LAYOUT
// -----------------------------------------------------

JPanel contentPanel = new JPanel(new GridLayout(1, 2, 20, 0));
contentPanel.setOpaque(false);

// LEFT: FORM
JPanel card = new JPanel(new GridBagLayout());
card.setBackground(CARD);
card.setBorder(BorderFactory.createCompoundBorder(
        new LineBorder(BORDER, 1, true),
        new EmptyBorder(20, 20, 20, 20)
));

GridBagConstraints gbc = new GridBagConstraints();
gbc.insets = new Insets(5, 8, 5, 8);
gbc.fill = GridBagConstraints.HORIZONTAL;
gbc.weightx = 1.0;

addLabel(card, gbc, "SUBJECT", 0);
subjectComboBox = new JComboBox<>();
styleComboBox(subjectComboBox);
addComponent(card, gbc, subjectComboBox, 1);

addLabel(card, gbc, "SESSION DATE", 2);
sessionDateField = new JTextField();
styleTextField(sessionDateField);
sessionDateField.setToolTipText("Format: YYYY-MM-DD");
addComponent(card, gbc, sessionDateField, 3);

addLabel(card, gbc, "DURATION (MINUTES)", 4);
durationField = new JTextField();
styleTextField(durationField);
durationField.setToolTipText("Example: 60");
addComponent(card, gbc, durationField, 5);

addLabel(card, gbc, "TOPIC COVERED", 6);
topicField = new JTextField();
styleTextField(topicField);
addComponent(card, gbc, topicField, 7);

addLabel(card, gbc, "NOTES", 8);
notesArea = new JTextArea(5, 20);
notesArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
notesArea.setForeground(DARK_TEXT);
notesArea.setBackground(FIELD);
notesArea.setCaretColor(BLUE);
notesArea.setLineWrap(true);
notesArea.setWrapStyleWord(true);
notesArea.setBorder(new EmptyBorder(8, 10, 8, 10));

JScrollPane notesScrollPane = new JScrollPane(notesArea);
notesScrollPane.setBorder(
        BorderFactory.createEmptyBorder()
);

notesArea.setBorder(
        BorderFactory.createCompoundBorder(
                new LineBorder(BORDER, 1, true),
                new EmptyBorder(8, 10, 8, 10)
        )
);

notesScrollPane.getViewport().setBackground(FIELD);
notesArea.setBackground(FIELD);
notesScrollPane.getVerticalScrollBar().setBackground(CARD);notesScrollPane.getViewport().setBackground(FIELD);
notesScrollPane.getVerticalScrollBar().setUI(
    new javax.swing.plaf.basic.BasicScrollBarUI() {

        @Override
        protected void configureScrollBarColors() {
            thumbColor = BORDER;
            trackColor = CARD;
        }

        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }

        private JButton createZeroButton() {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0));
            button.setMinimumSize(new Dimension(0, 0));
            button.setMaximumSize(new Dimension(0, 0));
            return button;
        }
    }
);
notesScrollPane.setPreferredSize(new Dimension(350, 90));
addComponent(card, gbc, notesScrollPane, 9);

contentPanel.add(card);

// RIGHT: STUDY PANEL
JPanel studyPanel = new JPanel(new GridBagLayout());
studyPanel.setBackground(CARD);
studyPanel.setBorder(new LineBorder(BORDER, 1, true));

JPanel illustration = new JPanel();
illustration.setOpaque(false);
illustration.setLayout(new BoxLayout(illustration, BoxLayout.Y_AXIS));

JLabel icon = new JLabel("📚");
icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 90));
icon.setForeground(BLUE);
icon.setAlignmentX(Component.CENTER_ALIGNMENT);

JLabel heading = new JLabel("FOCUS. LEARN. IMPROVE.");
heading.setFont(new Font("Segoe UI", Font.BOLD, 20));
heading.setForeground(DARK_TEXT);
heading.setAlignmentX(Component.CENTER_ALIGNMENT);

JLabel description = new JLabel(
        "<html><div style='text-align:center;'>"
        + "Every study session brings you<br>"
        + "one step closer to your goals."
        + "</div></html>"
);
description.setFont(new Font("Segoe UI", Font.PLAIN, 14));
description.setForeground(SECONDARY_TEXT);
description.setAlignmentX(Component.CENTER_ALIGNMENT);

illustration.add(icon);
illustration.add(Box.createVerticalStrut(20));
illustration.add(heading);
illustration.add(Box.createVerticalStrut(12));
illustration.add(description);

studyPanel.add(illustration);
contentPanel.add(studyPanel);

root.add(contentPanel, BorderLayout.CENTER);



// -----------------------------------------------------
        // BUTTONS
        // -----------------------------------------------------

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

        cancelButton.setUI(
        new javax.swing.plaf.basic.BasicButtonUI()
);

cancelButton.addMouseListener(new java.awt.event.MouseAdapter() {

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
        cancelButton.setBackground(new Color(16, 54, 58));
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {
        cancelButton.setBackground(FIELD);
    }
});

cancelButton.setBackground(FIELD);
cancelButton.setForeground(DARK_TEXT);
cancelButton.setOpaque(true);
cancelButton.setContentAreaFilled(true);
cancelButton.setBorderPainted(true);
cancelButton.setFocusPainted(false);

        saveButton =
                new JButton(
                        editingSession == null
                                ? "ADD SESSION"
                                : "UPDATE SESSION"
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

        // -----------------------------------------------------
        // ACTIONS
        // -----------------------------------------------------

        cancelButton.addActionListener(
                e -> dispose()
        );

        saveButton.addActionListener(
                e -> saveSession()
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

            for (
                    Subject subject :
                    subjects
            ) {

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
    // LOAD EDIT DATA
    // =========================================================

    private void loadSessionData() {

        if (editingSession == null) {

            sessionDateField.setText(
                    LocalDate.now().toString()
            );

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
                            == editingSession.getSubjectId()
            ) {

                subjectComboBox.setSelectedIndex(
                        i
                );

                break;
            }
        }

        sessionDateField.setText(
                editingSession.getSessionDate()
        );

        durationField.setText(
                String.valueOf(
                        editingSession
                                .getDurationMinutes()
                )
        );

        topicField.setText(
                editingSession.getTopicCovered()
                        == null
                        ? ""
                        : editingSession
                        .getTopicCovered()
        );

        notesArea.setText(
                editingSession.getNotes()
                        == null
                        ? ""
                        : editingSession.getNotes()
        );
    }

    // =========================================================
    // SAVE SESSION
    // =========================================================

    private void saveSession() {

        if (
                studySessionService == null
                        || loggedInStudent == null
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Study session service is not available.",
                    "Study Session",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        SubjectItem selectedSubject =
                (SubjectItem)
                        subjectComboBox
                                .getSelectedItem();

        String sessionDate =
                sessionDateField
                        .getText()
                        .trim();

        String durationText =
                durationField
                        .getText()
                        .trim();

        String topic =
                topicField
                        .getText()
                        .trim();

        String notes =
                notesArea
                        .getText()
                        .trim();

        // -----------------------------------------------------
        // SUBJECT VALIDATION
        // -----------------------------------------------------

        if (selectedSubject == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a subject.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // -----------------------------------------------------
        // DATE VALIDATION
        // -----------------------------------------------------

        if (sessionDate.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter session date.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            sessionDateField.requestFocus();

            return;
        }

        try {

            LocalDate.parse(
                    sessionDate
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid date.\nUse format: YYYY-MM-DD",
                    "Invalid Date",
                    JOptionPane.WARNING_MESSAGE
            );

            sessionDateField.requestFocus();

            return;
        }

        // -----------------------------------------------------
        // DURATION VALIDATION
        // -----------------------------------------------------

        if (durationText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter session duration.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            durationField.requestFocus();

            return;
        }

        int duration;

        try {

            duration =
                    Integer.parseInt(
                            durationText
                    );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Duration must be a valid number.",
                    "Invalid Duration",
                    JOptionPane.WARNING_MESSAGE
            );

            durationField.requestFocus();

            return;
        }

        if (duration <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Duration must be greater than 0 minutes.",
                    "Invalid Duration",
                    JOptionPane.WARNING_MESSAGE
            );

            durationField.requestFocus();

            return;
        }

        // -----------------------------------------------------
        // TOPIC VALIDATION
        // -----------------------------------------------------

        if (topic.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter the topic covered.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            topicField.requestFocus();

            return;
        }

        if (topic.length() > 300) {

            JOptionPane.showMessageDialog(
                    this,
                    "Topic Covered cannot exceed 300 characters.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            topicField.requestFocus();

            return;
        }

        // -----------------------------------------------------
        // NOTES VALIDATION
        // -----------------------------------------------------

        if (notes.length() > 1000) {

            JOptionPane.showMessageDialog(
                    this,
                    "Notes cannot exceed 1000 characters.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            notesArea.requestFocus();

            return;
        }

        // -----------------------------------------------------
        // SAVE
        // -----------------------------------------------------

        try {

            boolean success;

            if (editingSession == null) {

                StudySession session =
                        new StudySession(
                                loggedInStudent
                                        .getStudentId(),
                                selectedSubject
                                        .getSubjectId(),
                                sessionDate,
                                duration,
                                topic,
                                notes
                        );

                success =
                        studySessionService
                                .addStudySession(
                                        session
                                );

            } else {

                editingSession.setStudentId(
                        loggedInStudent
                                .getStudentId()
                );

                editingSession.setSubjectId(
                        selectedSubject
                                .getSubjectId()
                );

                editingSession.setSessionDate(
                        sessionDate
                );

                editingSession.setDurationMinutes(
                        duration
                );

                editingSession.setTopicCovered(
                        topic
                );

                editingSession.setNotes(
                        notes
                );

                success =
                        studySessionService
                                .updateStudySession(
                                        editingSession
                                );
            }

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        editingSession == null
                                ? "Study session added successfully!"
                                : "Study session updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Unable to save study session.",
                        "Study Session",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database error while saving study session.\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // LABEL
    // =========================================================

    private void addLabel(
        JPanel panel,
        GridBagConstraints gbc,
        String text,
        int row
) {
    JLabel label = new JLabel(text);

    label.setFont(new Font("Segoe UI", Font.BOLD, 12));
    label.setForeground(SECONDARY_TEXT);

    gbc.gridx = 0;
    gbc.gridy = row + 1;
    gbc.gridwidth = 1;
    gbc.weightx = 0;
    gbc.weighty = 0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.anchor = GridBagConstraints.CENTER;

    panel.add(label, gbc);
}

    // =========================================================
    // COMPONENT
    // =========================================================

    private void addComponent(
        JPanel panel,
        GridBagConstraints gbc,
        Component component,
        int row
) {
    gbc.gridx = 1;
    gbc.gridy = row;
    gbc.gridwidth = 1;
    gbc.weightx = 1.0;
    gbc.weighty = 0;
    gbc.anchor = GridBagConstraints.NORTHWEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.ipady = 0;

    if (component instanceof JScrollPane) {
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
    }

    panel.add(component, gbc);

    gbc.weighty = 0;
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

       field.setBackground(FIELD);
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

   
private void styleComboBox(JComboBox<?> comboBox) {

    comboBox.setFont(
            new Font("Segoe UI", Font.PLAIN, 14)
    );

    comboBox.setForeground(DARK_TEXT);
    comboBox.setFocusable(false);
    comboBox.setBackground(FIELD);
    comboBox.setOpaque(true);
    comboBox.setUI(new javax.swing.plaf.basic.BasicComboBoxUI() {
        @Override
public void paintCurrentValueBackground(
        Graphics g,
        Rectangle bounds,
        boolean hasFocus
) {
    g.setColor(FIELD);
    g.fillRect(
            bounds.x,
            bounds.y,
            bounds.width,
            bounds.height
    );
}
    @Override
    protected JButton createArrowButton() {
        JButton button = new JButton("▼");
        button.setBackground(CARD);
button.setOpaque(true);
button.setContentAreaFilled(true);
        button.setForeground(DARK_TEXT);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        return button;
    }
});

    comboBox.setBorder(
            new LineBorder(BORDER, 1, true)
    );

    comboBox.setPreferredSize(
            new Dimension(350, 42)
    );

    comboBox.setRenderer(new DefaultListCellRenderer() {
        @Override
        public Component getListCellRendererComponent(
                JList<?> list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus
        ) {
            super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus
            );

           setBackground(FIELD);
setForeground(DARK_TEXT);

if (index >= 0 && list.getMousePosition() != null) {
    Point p = list.getMousePosition();
    int hoveredIndex = list.locationToIndex(p);

    if (index == hoveredIndex) {
        setBackground(new Color(16, 54, 58));
        setForeground(DARK_TEXT);
    }
}

            setOpaque(true);
            setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));

            return this;
        }
    });
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
        new Color(11, 37, 40)
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
        new Color(16, 54, 58)
);
                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e
                    ) {

                        button.setBackground(
        new Color(11, 37, 40)
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
button.setBackground(FIELD);

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