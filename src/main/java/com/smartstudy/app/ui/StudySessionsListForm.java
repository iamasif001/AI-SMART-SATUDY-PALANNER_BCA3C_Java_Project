package com.smartstudy.app.ui;

import com.smartstudy.app.model.Student;
import com.smartstudy.app.model.StudySession;
import com.smartstudy.app.model.Subject;
import com.smartstudy.app.service.StudySessionService;
import com.smartstudy.app.service.SubjectService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AI Smart Study Planner
 * Study Sessions List
 */
public class StudySessionsListForm extends JFrame {

   
private static final Color BACKGROUND =
        new Color(7, 21, 23);

private static final Color CARD =
        new Color(11, 37, 40);

private static final Color DARK_TEXT =
        new Color(240, 248, 250);

private static final Color SECONDARY_TEXT =
        new Color(160, 190, 198);

private static final Color BLUE =
        new Color(121, 210, 232);

private static final Color RED =
        new Color(255, 90, 90);

private static final Color BORDER =
        new Color(28, 66, 70);

private static final Color TABLE_HEADER =
        new Color(14, 48, 51);

    private final Student loggedInStudent;

    private StudySessionService studySessionService;
    private SubjectService subjectService;

    private JTable sessionsTable;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;

    private JTextField searchField;

    private JLabel sessionCountLabel;
    private JLabel totalTimeLabel;

    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton refreshButton;

    private List<StudySession> studySessions;

    private Map<Integer, String> subjectNames =
            new HashMap<>();

    public StudySessionsListForm(
            Student student
    ) {

        loggedInStudent = student;

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

        loadStudySessions();
    }

    private void initializeFrame() {

        setTitle("My Study Sessions");

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setSize(1100, 700);

        setMinimumSize(
                new Dimension(950, 620)
        );

        setLocationRelativeTo(null);
    }

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

        /*
         * =========================
         * HEADER
         * =========================
         */

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setOpaque(false);

        JPanel titlePanel =
                new JPanel();

        titlePanel.setOpaque(false);

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel title =
                new JLabel(
                        "My Study Sessions"
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
                        "Track and manage your study sessions."
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

        titlePanel.add(title);

        titlePanel.add(
                Box.createVerticalStrut(5)
        );

        titlePanel.add(subtitle);

        header.add(
                titlePanel,
                BorderLayout.WEST
        );

        /*
         * =========================
         * SUMMARY
         * =========================
         */

        JPanel summaryPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                12,
                                5
                        )
                );

        summaryPanel.setOpaque(false);

        JPanel sessionsSummary =
                createSummaryCard(
                        "SESSIONS",
                        "0"
                );

        JPanel timeSummary =
                createSummaryCard(
                        "TOTAL TIME",
                        "0 min"
                );

        sessionCountLabel =
                (JLabel)
                        sessionsSummary
                                .getClientProperty(
                                        "valueLabel"
                                );

        totalTimeLabel =
                (JLabel)
                        timeSummary
                                .getClientProperty(
                                        "valueLabel"
                                );

        summaryPanel.add(
                sessionsSummary
        );

        summaryPanel.add(
                timeSummary
        );

        header.add(
                summaryPanel,
                BorderLayout.EAST
        );

        root.add(
                header,
                BorderLayout.NORTH
        );

        /*
         * =========================
         * MAIN CARD
         * =========================
         */

        JPanel mainCard =
                new JPanel(
                        new BorderLayout(
                                0,
                                15
                        )
                );

        mainCard.setBackground(
                CARD
        );

        mainCard.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(
                                BORDER,
                                1,
                                true
                        ),
                        new EmptyBorder(
                                20,
                                20,
                                20,
                                20
                        )
                )
        );

        /*
         * =========================
         * TOOLBAR
         * =========================
         */

        JPanel toolbar =
                new JPanel(
                        new BorderLayout(
                                15,
                                0
                        )
                );

        toolbar.setOpaque(false);

        JPanel searchPanel =
                new JPanel(
                        new BorderLayout()
                );

        searchPanel.setOpaque(false);

        searchField =
                new JTextField();

        searchField.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        searchField.setForeground(
                DARK_TEXT
        );

        searchField.setBackground(CARD);
searchField.setCaretColor(BLUE);
searchField.setSelectionColor(
        new Color(28, 66, 70)
);
searchField.setSelectedTextColor(Color.WHITE);

        searchField.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(
                                BORDER,
                                1,
                                true
                        ),
                        new EmptyBorder(
                                0,
                                12,
                                0,
                                12
                        )
                )
        );

        searchField.setPreferredSize(
                new Dimension(
                        300,
                        40
                )
        );

        searchField.setToolTipText(
                "Search by subject or topic"
        );

        searchPanel.add(
                searchField,
                BorderLayout.CENTER
        );

        toolbar.add(
                searchPanel,
                BorderLayout.WEST
        );

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                8,
                                0
                        )
                );

        buttonPanel.setOpaque(false);

        refreshButton = createButton(
        "REFRESH", CARD, BLUE, BORDER
);

editButton = createButton(
        "EDIT", CARD, BLUE, BLUE
);

deleteButton = createButton(
        "DELETE", CARD, RED, RED
);

addButton = createButton(
        "ADD SESSION", BLUE, Color.WHITE, BLUE
);

                addButton.setOpaque(true);
addButton.setContentAreaFilled(true);
addButton.setBorderPainted(true);
addButton.setBackground(BLUE);
addButton.setForeground(Color.BLACK);

        buttonPanel.add(
                refreshButton
        );

        buttonPanel.add(
                editButton
        );

        buttonPanel.add(
                deleteButton
        );

        buttonPanel.add(
                addButton
        );

        toolbar.add(
                buttonPanel,
                BorderLayout.EAST
        );

        mainCard.add(
                toolbar,
                BorderLayout.NORTH
        );

        /*
         * =========================
         * TABLE
         * =========================
         */

        String[] columns = {
                "ID",
                "SUBJECT",
                "DATE",
                "DURATION",
                "TOPIC COVERED",
                "NOTES"
        };

        tableModel =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        sessionsTable =
                new JTable(
                        tableModel
                );

        sessionsTable.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        sessionsTable.setForeground(
                DARK_TEXT
        );
        
sessionsTable.setBackground(CARD);
sessionsTable.setOpaque(true);


        sessionsTable.setRowHeight(
                48
        );

        sessionsTable.setShowVerticalLines(true);

        sessionsTable.setShowHorizontalLines(true);
sessionsTable.setGridColor(BORDER);

        sessionsTable.setGridColor(
                BORDER
        );

        sessionsTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        
sessionsTable.setSelectionBackground(
        new Color(28, 66, 70)
);

sessionsTable.setSelectionForeground(
        Color.WHITE
);


        JTableHeader tableHeader =
                sessionsTable.getTableHeader();

                tableHeader.setUI(
        new javax.swing.plaf.basic.BasicTableHeaderUI()
);

        tableHeader.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        tableHeader.setForeground(
                SECONDARY_TEXT
        );

        tableHeader.setBackground(
                TABLE_HEADER
        );
        tableHeader.setOpaque(true);

        tableHeader.setPreferredSize(
                new Dimension(
                        0,
                        42
                )
        );

        tableHeader.setDefaultRenderer(
        new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column
            ) {
                JLabel label = (JLabel)
                        super.getTableCellRendererComponent(
                                table,
                                value,
                                isSelected,
                                hasFocus,
                                row,
                                column
                        );

                label.setOpaque(true);
                label.setBackground(TABLE_HEADER);
                label.setForeground(BLUE);
                label.setFont(
                        new Font("Segoe UI", Font.BOLD, 12)
                );
                label.setBorder(
                        BorderFactory.createEmptyBorder(0, 6, 0, 6)
                );

                return label;
            }
        }
);

        /*
         * Column widths
         */

        sessionsTable
                .getColumnModel()
                .getColumn(0)
                .setPreferredWidth(50);

        sessionsTable
                .getColumnModel()
                .getColumn(1)
                .setPreferredWidth(160);

        sessionsTable
                .getColumnModel()
                .getColumn(2)
                .setPreferredWidth(110);

        sessionsTable
                .getColumnModel()
                .getColumn(3)
                .setPreferredWidth(100);

        sessionsTable
                .getColumnModel()
                .getColumn(4)
                .setPreferredWidth(240);

        sessionsTable
                .getColumnModel()
                .getColumn(5)
                .setPreferredWidth(300);

        
/*
 * Left align all table values
 */
DefaultTableCellRenderer leftRenderer =
        new DefaultTableCellRenderer();

leftRenderer.setHorizontalAlignment(
        SwingConstants.LEFT
);

for (int i = 0; i < sessionsTable.getColumnCount(); i++) {
    sessionsTable.getColumnModel()
            .getColumn(i)
            .setCellRenderer(leftRenderer);
}

        JScrollPane scrollPane =
                new JScrollPane(
                        sessionsTable
                );

        scrollPane.setBorder(
                new LineBorder(
                        BORDER,
                        1,
                        true
                )
        );

        
scrollPane.getViewport()
        .setBackground(CARD);


        mainCard.add(
                scrollPane,
                BorderLayout.CENTER
        );

        root.add(
                mainCard,
                BorderLayout.CENTER
        );

        setContentPane(root);

        /*
         * =========================
         * SORTER / SEARCH
         * =========================
         */

        sorter =
                new TableRowSorter<>(
                        tableModel
                );

        sessionsTable.setRowSorter(
                sorter
        );

        searchField
                .getDocument()
                .addDocumentListener(
                        new javax.swing.event.DocumentListener() {

                            @Override
                            public void insertUpdate(
                                    javax.swing.event.DocumentEvent e
                            ) {
                                filterTable();
                            }

                            @Override
                            public void removeUpdate(
                                    javax.swing.event.DocumentEvent e
                            ) {
                                filterTable();
                            }

                            @Override
                            public void changedUpdate(
                                    javax.swing.event.DocumentEvent e
                            ) {
                                filterTable();
                            }
                        }
                );

        /*
         * =========================
         * BUTTON ACTIONS
         * =========================
         */

        refreshButton.addActionListener(
                e -> loadStudySessions()
        );

        addButton.addActionListener(
                e -> openAddForm()
        );

        editButton.addActionListener(
                e -> editSelectedSession()
        );

        deleteButton.addActionListener(
                e -> deleteSelectedSession()
        );

        sessionsTable.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            MouseEvent e
                    ) {

                        if (
                                e.getClickCount() == 2
                                        && SwingUtilities
                                        .isLeftMouseButton(e)
                        ) {

                            editSelectedSession();
                        }
                    }
                }
        );
    }

    private JPanel createSummaryCard(
            String title,
            String value
    ) {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                10,
                                0
                        )
                );

panel.setBackground(CARD);


        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(
                                BORDER,
                                1,
                                true
                        ),
                        new EmptyBorder(
                                8,
                                14,
                                8,
                                14
                        )
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        10
                )
        );

        titleLabel.setForeground(
                SECONDARY_TEXT
        );

        JLabel valueLabel =
                new JLabel(value);

        valueLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        valueLabel.setForeground(
                BLUE
        );

        panel.add(
                titleLabel,
                BorderLayout.NORTH
        );

        panel.add(
                valueLabel,
                BorderLayout.CENTER
        );

        panel.putClientProperty(
                "valueLabel",
                valueLabel
        );

        panel.setPreferredSize(
                new Dimension(
                        125,
                        58
                )
        );

        return panel;
    }

   
private JButton createButton(
        String text,
        Color background,
        Color foreground,
        Color borderColor
) {
    JButton button = new JButton(text);

    button.setFont(
            new Font("Segoe UI", Font.BOLD, 11)
    );

    button.setBackground(background);
    button.setForeground(foreground);

    button.setOpaque(true);
    button.setContentAreaFilled(true);
    button.setBorderPainted(true);
    button.setFocusPainted(false);

    
button.addMouseListener(new java.awt.event.MouseAdapter() {

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
        button.setBackground(BLUE);
        button.setForeground(Color.BLACK);
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {
        button.setBackground(background);
        button.setForeground(foreground);
    }
});


    button.setCursor(
            new Cursor(Cursor.HAND_CURSOR)
    );

    button.setPreferredSize(
            new Dimension(
                    text.equals("ADD SESSION") ? 125 : 95,
                    40
            )
    );

    button.setBorder(
            new LineBorder(borderColor, 1, true)
    );

    // Force the requested background color
    button.setUI(
            new javax.swing.plaf.basic.BasicButtonUI()
    );

    button.setBackground(background);
    button.setForeground(foreground);
    button.setOpaque(true);
    button.setContentAreaFilled(true);

    return button;
}


    private void loadSubjects() {

        if (
                subjectService == null
                        || loggedInStudent == null
        ) {
            return;
        }

        try {

            subjectNames.clear();

            List<Subject> subjects =
                    subjectService
                            .getSubjectsByStudentId(
                                    loggedInStudent
                                            .getStudentId()
                            );

            for (Subject subject : subjects) {

                subjectNames.put(
                        subject.getSubjectId(),
                        subject.getSubjectName()
                );
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load subjects.\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void loadStudySessions() {

        if (
                studySessionService == null
                        || loggedInStudent == null
        ) {
            return;
        }

        try {

            studySessions =
                    studySessionService
                            .getStudySessionsByStudentId(
                                    loggedInStudent
                                            .getStudentId()
                            );

            tableModel.setRowCount(0);

            int totalMinutes = 0;

            for (
                    StudySession session :
                    studySessions
            ) {

                String subjectName =
                        subjectNames.get(
                                session.getSubjectId()
                        );

                if (
                        subjectName == null
                ) {
                    subjectName =
                            "Unknown Subject";
                }

                int duration =
                        session.getDurationMinutes();

                totalMinutes += duration;

                tableModel.addRow(
                        new Object[]{
                                session.getSessionId(),
                                subjectName,
                                session.getSessionDate(),
                                formatDuration(duration),
                                session.getTopicCovered(),
                                session.getNotes()
                        }
                );
            }

            updateSummary(
                    studySessions.size(),
                    totalMinutes
            );

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load study sessions.\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void updateSummary(
            int sessionCount,
            int totalMinutes
    ) {

        sessionCountLabel.setText(
                String.valueOf(
                        sessionCount
                )
        );

        totalTimeLabel.setText(
                formatDuration(totalMinutes)
        );
    }

    private String formatDuration(
            int minutes
    ) {

        if (minutes < 60) {

            return minutes + " min";
        }

        int hours =
                minutes / 60;

        int remainingMinutes =
                minutes % 60;

        if (remainingMinutes == 0) {

            return hours + " hr";
        }

        return hours
                + " hr "
                + remainingMinutes
                + " min";
    }

    private void filterTable() {

        if (sorter == null) {
            return;
        }

        String text =
                searchField
                        .getText()
                        .trim();

        if (text.isEmpty()) {

            sorter.setRowFilter(null);

        } else {

            sorter.setRowFilter(
                    RowFilter.regexFilter(
                            "(?i)"
                                    + java.util.regex.Pattern
                                    .quote(text)
                    )
            );
        }
    }

    private StudySession getSelectedSession() {

        int selectedRow =
                sessionsTable
                        .getSelectedRow();

        if (selectedRow < 0) {
            return null;
        }

        int modelRow =
                sessionsTable
                        .convertRowIndexToModel(
                                selectedRow
                        );

        int sessionId =
                (int)
                        tableModel
                                .getValueAt(
                                        modelRow,
                                        0
                                );

        for (
                StudySession session :
                studySessions
        ) {

            if (
                    session.getSessionId()
                            == sessionId
            ) {

                return session;
            }
        }

        return null;
    }

    private void openAddForm() {

        StudySessionForm form =
                new StudySessionForm(
                        loggedInStudent
                );

        form.setLocationRelativeTo(
                this
        );

        form.addWindowListener(
                new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosed(
                            java.awt.event.WindowEvent e
                    ) {

                        loadSubjects();

                        loadStudySessions();
                    }
                }
        );

        form.setVisible(true);
    }

    private void editSelectedSession() {

        StudySession selectedSession =
                getSelectedSession();

        
if (selectedSession == null) {

    JDialog dialog = new JDialog(
            this, "Edit Study Session", true
    );

    dialog.setSize(380, 180);
    dialog.setResizable(false);

    JPanel panel = new JPanel(new BorderLayout(15, 15));
    panel.setBackground(CARD);
    panel.setBorder(
            new EmptyBorder(20, 20, 20, 20)
    );

    JLabel message = new JLabel(
            "Please select a study session first."
    );
    message.setFont(
            new Font("Segoe UI", Font.PLAIN, 14)
    );
    message.setForeground(DARK_TEXT);

    JButton okButton = new JButton("OK");
    okButton.setFont(
            new Font("Segoe UI", Font.BOLD, 12)
    );
    okButton.setBackground(BLUE);
    okButton.setForeground(Color.BLACK);
    okButton.setOpaque(true);
    okButton.setContentAreaFilled(true);
    okButton.setFocusPainted(false);

    okButton.addActionListener(e -> dialog.dispose());

    JPanel buttonPanel = new JPanel(
            new FlowLayout(FlowLayout.RIGHT)
    );
    buttonPanel.setBackground(CARD);
    buttonPanel.add(okButton);

    panel.add(message, BorderLayout.CENTER);
    panel.add(buttonPanel, BorderLayout.SOUTH);

    dialog.setContentPane(panel);
    dialog.getRootPane().setDefaultButton(okButton);
    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true);

    return;
}


        StudySessionForm form =
                new StudySessionForm(
                        loggedInStudent,
                        selectedSession
                );

        form.setLocationRelativeTo(
                this
        );

        form.addWindowListener(
                new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosed(
                            java.awt.event.WindowEvent e
                    ) {

                        loadSubjects();

                        loadStudySessions();
                    }
                }
        );

        form.setVisible(true);
    }

    private void deleteSelectedSession() {

        StudySession selectedSession =
                getSelectedSession();

        if (selectedSession == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a study session first.",
                    "Delete Study Session",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String subjectName =
                subjectNames.get(
                        selectedSession
                                .getSubjectId()
                );

        if (subjectName == null) {
            subjectName =
                    "Unknown Subject";
        }

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete this study session?\n\n"
                                + "Subject: "
                                + subjectName
                                + "\n"
                                + "Date: "
                                + selectedSession
                                .getSessionDate()
                                + "\n"
                                + "Topic: "
                                + selectedSession
                                .getTopicCovered(),
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (
                choice
                        != JOptionPane.YES_OPTION
        ) {
            return;
        }

        try {

            boolean deleted =
                    studySessionService
                            .deleteStudySession(
                                    selectedSession
                                            .getSessionId(),
                                    loggedInStudent
                                            .getStudentId()
                            );

            if (deleted) {

                JOptionPane.showMessageDialog(
                        this,
                        "Study session deleted successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                loadStudySessions();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Unable to delete study session.",
                        "Delete Study Session",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database error while deleting study session.\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}