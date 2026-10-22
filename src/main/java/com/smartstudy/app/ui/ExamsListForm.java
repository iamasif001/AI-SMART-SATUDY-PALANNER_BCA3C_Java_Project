package com.smartstudy.app.ui;

import com.smartstudy.app.model.Exam;
import com.smartstudy.app.model.Student;
import com.smartstudy.app.model.Subject;
import com.smartstudy.app.service.ExamService;
import com.smartstudy.app.service.SubjectService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AI Smart Study Planner
 * Exams List
 */
public class ExamsListForm extends JFrame {

   private static final Color BACKGROUND = new Color(7, 20, 22);
private static final Color CARD = new Color(11, 37, 40);
private static final Color DARK_TEXT = new Color(240, 248, 250);
private static final Color SECONDARY_TEXT = new Color(155, 184, 190);
private static final Color BLUE = new Color(121, 210, 232);
private static final Color BLUE_DARK = new Color(75, 175, 200);
private static final Color RED = new Color(225, 90, 100);
private static final Color GREEN = new Color(90, 200, 150);
private static final Color ORANGE = new Color(240, 175, 85);
private static final Color BORDER = new Color(28, 66, 70);
    private final Student loggedInStudent;

    private ExamService examService;
    private SubjectService subjectService;

    private JTable examTable;
    private DefaultTableModel tableModel;

    private JTextField searchField;

    private JLabel examCountLabel;

    private JButton refreshButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton addButton;

    private Map<Integer, String> subjectNames;

    public ExamsListForm(
            Student student
    ) {

        loggedInStudent =
                student;

        subjectNames =
                new HashMap<>();

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

        loadExams();
    }

    private void initializeFrame() {

        setTitle(
                "My Exams"
        );

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setSize(
                1050,
                650
        );

        setMinimumSize(
                new Dimension(
                        900,
                        600
                )
        );

        setLocationRelativeTo(null);

    }

    private void buildUI() {

        JPanel root =
                new JPanel(
                        new BorderLayout(
                                0,
                                15
                        )
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
                        "My Exams"
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
                        "Manage your upcoming and scheduled exams."
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

        titlePanel.add(
                title
        );

        titlePanel.add(
                Box.createVerticalStrut(5)
        );

        titlePanel.add(
                subtitle
        );

        header.add(
                titlePanel,
                BorderLayout.WEST
        );

        /*
         * =========================
         * HEADER BUTTON
         * =========================
         */

        refreshButton =
                new JButton(
                        "REFRESH"
                );

        styleSecondaryButton(
                refreshButton,
                105
        );

        refreshButton.setBackground(BLUE);
        refreshButton.setOpaque(true);
refreshButton.setContentAreaFilled(true);
refreshButton.setBorderPainted(false);
refreshButton.setForeground(new Color(7, 20, 22));

        refreshButton.addActionListener(
                e -> refreshExamData()
        );

        header.add(
                refreshButton,
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

        JPanel card =
                new JPanel(
                        new BorderLayout(
                                0,
                                15
                        )
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
                                18,
                                18,
                                18,
                                18
                        )
                )
        );

        /*
         * =========================
         * SEARCH HEADER
         * =========================
         */

        JPanel cardHeader =
                new JPanel(
                        new BorderLayout(
                                10,
                                0
                        )
                );

        cardHeader.setOpaque(false);

        JPanel searchPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                8,
                                0
                        )
                );

        searchPanel.setOpaque(false);

        JLabel searchLabel =
                new JLabel(
                        "SEARCH"
                );

        searchLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        searchLabel.setForeground(
                SECONDARY_TEXT
        );

        searchField =
                new JTextField(
                        20
                );

                searchField.setBackground(new Color(7, 27, 30));
searchField.setForeground(DARK_TEXT);
searchField.setCaretColor(DARK_TEXT);

        searchField.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        searchField.setPreferredSize(
                new Dimension(
                        230,
                        34
                )
        );

        searchField.setBorder(
                new LineBorder(
                        BORDER,
                        1,
                        true
                )
        );

        searchPanel.add(
                searchLabel
        );

        searchPanel.add(
                searchField
        );

        cardHeader.add(
                searchPanel,
                BorderLayout.WEST
        );

        /*
         * =========================
         * COUNT
         * =========================
         */

        examCountLabel =
                new JLabel(
                        "0 Exams"
                );

        examCountLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        examCountLabel.setForeground(
                BLUE
        );

        cardHeader.add(
                examCountLabel,
                BorderLayout.EAST
        );

        card.add(
                cardHeader,
                BorderLayout.NORTH
        );

        /*
         * =========================
         * TABLE
         * =========================
         */

        String[] columns = {
                "ID",
                "EXAM NAME",
                "SUBJECT",
                "DATE",
                "TIME",
                "TYPE",
                "DAYS REMAINING"
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

        examTable =
                new JTable(
                        tableModel
                );

        examTable.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        examTable.setForeground(
                DARK_TEXT
        );

examTable.setBackground(
        CARD
);

examTable.setSelectionBackground(
        new Color(25, 68, 73)
);

examTable.setSelectionForeground(
        Color.WHITE
);
        examTable.setRowHeight(
                42
        );

        examTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        examTable.setShowGrid(true);
examTable.setGridColor(BORDER);

        examTable.setIntercellSpacing(
                new Dimension(
                        0,
                        0
                )
        );

        examTable.getTableHeader()
                .setFont(
                        new Font(
                                "Segoe UI",
                                Font.BOLD,
                                12
                        )
                );

        examTable.getTableHeader()
                .setForeground(
                        SECONDARY_TEXT
                );

        

        examTable.getTableHeader()
        .setBackground(CARD);

        examTable.getTableHeader().setDefaultRenderer(
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
                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column
                );

                c.setBackground(CARD);
                c.setForeground(SECONDARY_TEXT);
                c.setFont(new Font("Segoe UI", Font.BOLD, 12));

                return c;
            }
        }
);

        examTable.getTableHeader().setOpaque(true);
        /*
         * COLUMN WIDTHS
         */

        examTable
                .getColumnModel()
                .getColumn(0)
                .setPreferredWidth(45);

        examTable
                .getColumnModel()
                .getColumn(1)
                .setPreferredWidth(190);

        examTable
                .getColumnModel()
                .getColumn(2)
                .setPreferredWidth(160);

        examTable
                .getColumnModel()
                .getColumn(3)
                .setPreferredWidth(100);

        examTable
                .getColumnModel()
                .getColumn(4)
                .setPreferredWidth(100);

        examTable
                .getColumnModel()
                .getColumn(5)
                .setPreferredWidth(100);

        examTable
                .getColumnModel()
                .getColumn(6)
                .setPreferredWidth(120);

        /*
         * CENTER ALIGNMENT
         */

        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        examTable
                .getColumnModel()
                .getColumn(0)
                .setCellRenderer(
                        centerRenderer
                );

        examTable
                .getColumnModel()
                .getColumn(3)
                .setCellRenderer(
                        centerRenderer
                );

        examTable
                .getColumnModel()
                .getColumn(4)
                .setCellRenderer(
                        centerRenderer
                );

        examTable
                .getColumnModel()
                .getColumn(5)
                .setCellRenderer(
                        centerRenderer
                );

        examTable
                .getColumnModel()
                .getColumn(6)
                .setCellRenderer(
                        new DaysRemainingRenderer()
                );

        JScrollPane scrollPane =
                new JScrollPane(
                        examTable
                );

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        BORDER
                )
        );

       scrollPane.getViewport().setBackground(CARD);
        card.add(
                scrollPane,
                BorderLayout.CENTER
        );

        root.add(
                card,
                BorderLayout.CENTER
        );

        /*
         * =========================
         * BOTTOM BUTTONS
         * =========================
         */

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                8,
                                0
                        )
                );

        buttonPanel.setOpaque(false);

        deleteButton =
                new JButton(
                        "DELETE EXAM"
                );

        styleDangerButton(
                deleteButton,
                125
        );
        deleteButton.setOpaque(true);
deleteButton.setContentAreaFilled(true);
deleteButton.setBorderPainted(true);

        editButton =
                new JButton(
                        "EDIT EXAM"
                );

        styleSecondaryButton(
                editButton,
                110
        );
        

        editButton.setForeground(Color.BLACK);

        editButton.setOpaque(true);
editButton.setContentAreaFilled(true);
editButton.setForeground(Color.BLACK);

        addButton =
                new JButton(
                        "ADD EXAM"
                );

        stylePrimaryButton(
                addButton,
                110
        );

        buttonPanel.add(
                deleteButton
        );

        buttonPanel.add(
                editButton
        );

        buttonPanel.add(
                addButton
        );

        root.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        /*
         * =========================
         * BUTTON ACTIONS
         * =========================
         */

        addButton.addActionListener(
                e -> openAddExamForm()
        );

        editButton.addActionListener(
                e -> editSelectedExam()
        );

        deleteButton.addActionListener(
                e -> deleteSelectedExam()
        );

        /*
         * =========================
         * SEARCH
         * =========================
         */

        searchField
                .getDocument()
                .addDocumentListener(
                        new DocumentListener() {

                            @Override
                            public void insertUpdate(
                                    DocumentEvent e
                            ) {

                                filterTable();
                            }

                            @Override
                            public void removeUpdate(
                                    DocumentEvent e
                            ) {

                                filterTable();
                            }

                            @Override
                            public void changedUpdate(
                                    DocumentEvent e
                            ) {

                                filterTable();
                            }
                        }
                );

        /*
         * =========================
         * DOUBLE CLICK
         * =========================
         */

        examTable.addMouseListener(
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

                            editSelectedExam();
                        }
                    }
                }
        );

        setContentPane(
                root
        );
    }

    /*
     * =========================
     * LOAD SUBJECTS
     * =========================
     */

    private void loadSubjects() {

        subjectNames.clear();

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

            for (
                    Subject subject :
                    subjects
            ) {

                subjectNames.put(
                        subject.getSubjectId(),
                        subject.getSubjectName()
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

    /*
     * =========================
     * LOAD EXAMS
     * =========================
     */

    private void loadExams() {

        if (
                loggedInStudent == null
                        || examService == null
        ) {

            return;
        }

        try {

            List<Exam> exams =
                    examService
                            .getExamsByStudentId(
                                    loggedInStudent
                                            .getStudentId()
                            );

            displayExams(
                    exams
            );

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load exams.\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /*
     * =========================
     * DISPLAY EXAMS
     * =========================
     */

    private void displayExams(
            List<Exam> exams
    ) {

        tableModel.setRowCount(
                0
        );

        for (
                Exam exam :
                exams
        ) {

            String subjectName =
                    subjectNames.get(
                            exam.getSubjectId()
                    );

            if (subjectName == null) {

                subjectName =
                        "Unknown Subject";
            }

            tableModel.addRow(
                    new Object[]{
                            exam.getExamId(),
                            exam.getExamName(),
                            subjectName,
                            exam.getExamDate(),
                            exam.getExamTime(),
                            exam.getExamType(),
                            calculateDaysRemaining(
                                    exam.getExamDate()
                            )
                    }
            );
        }

        updateExamCount();
    }

    /*
     * =========================
     * FILTER
     * =========================
     */

    private void filterTable() {

        String searchText =
                searchField
                        .getText()
                        .trim()
                        .toLowerCase();

        if (searchText.isEmpty()) {

            loadExams();

            return;
        }

        try {

            List<Exam> exams =
                    examService
                            .getExamsByStudentId(
                                    loggedInStudent
                                            .getStudentId()
                            );

            tableModel.setRowCount(
                    0
            );

            for (
                    Exam exam :
                    exams
            ) {

                String subjectName =
                        subjectNames.get(
                                exam.getSubjectId()
                        );

                if (subjectName == null) {

                    subjectName =
                            "Unknown Subject";
                }

                String searchableText =
                        (
                                exam.getExamName()
                                        + " "
                                        + subjectName
                                        + " "
                                        + exam.getExamDate()
                                        + " "
                                        + exam.getExamTime()
                                        + " "
                                        + exam.getExamType()
                        ).toLowerCase();

                if (
                        searchableText
                                .contains(
                                        searchText
                                )
                ) {

                    tableModel.addRow(
                            new Object[]{
                                    exam.getExamId(),
                                    exam.getExamName(),
                                    subjectName,
                                    exam.getExamDate(),
                                    exam.getExamTime(),
                                    exam.getExamType(),
                                    calculateDaysRemaining(
                                            exam.getExamDate()
                                    )
                            }
                    );
                }
            }

            updateExamCount();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to search exams.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /*
     * =========================
     * DAYS REMAINING
     * =========================
     */

    private String calculateDaysRemaining(
            String examDate
    ) {

        try {

            LocalDate date =
                    LocalDate.parse(
                            examDate
                    );

            LocalDate today =
                    LocalDate.now();

            long days =
                    ChronoUnit.DAYS.between(
                            today,
                            date
                    );

            if (days < 0) {

                return "Overdue";
            }

            if (days == 0) {

                return "Today";
            }

            if (days == 1) {

                return "1 Day";
            }

            return days + " Days";

        } catch (Exception e) {

            return "-";
        }
    }

    /*
     * =========================
     * REFRESH
     * =========================
     */

    private void refreshExamData() {

        loadSubjects();

        loadExams();

        searchField.setText(
                ""
        );
    }

    /*
     * =========================
     * ADD EXAM
     * =========================
     */

    private void openAddExamForm() {

        if (loggedInStudent == null) {

            return;
        }

        ExamForm examForm =
                new ExamForm(
                        loggedInStudent
                );

        examForm.setLocationRelativeTo(
                this
        );

        examForm.addWindowListener(
                new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosed(
                            java.awt.event.WindowEvent e
                    ) {

                        refreshExamData();
                    }
                }
        );

        examForm.setVisible(
                true
        );
    }

    /*
     * =========================
     * EDIT EXAM
     * =========================
     */

    private void editSelectedExam() {

        int selectedRow =
                examTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an exam to edit.",
                    "Edit Exam",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int modelRow =
                examTable.convertRowIndexToModel(
                        selectedRow
                );

        Object examIdValue =
                tableModel.getValueAt(
                        modelRow,
                        0
                );

        int examId;

        try {

            examId =
                    Integer.parseInt(
                            examIdValue.toString()
                    );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to identify selected exam.",
                    "Edit Exam",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        try {

            Exam exam =
                    examService.getExamById(
                            examId
                    );

            if (exam == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Selected exam could not be found.",
                        "Edit Exam",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            ExamForm examForm =
                    new ExamForm(
                            loggedInStudent,
                            exam
                    );

            examForm.setLocationRelativeTo(
                    this
            );

            examForm.addWindowListener(
                    new java.awt.event.WindowAdapter() {

                        @Override
                        public void windowClosed(
                                java.awt.event.WindowEvent e
                        ) {

                            refreshExamData();
                        }
                    }
            );

            examForm.setVisible(
                    true
            );

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load selected exam.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /*
     * =========================
     * DELETE EXAM
     * =========================
     */

    private void deleteSelectedExam() {

        int selectedRow =
                examTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an exam to delete.",
                    "Delete Exam",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int modelRow =
                examTable.convertRowIndexToModel(
                        selectedRow
                );

        int examId =
                Integer.parseInt(
                        tableModel
                                .getValueAt(
                                        modelRow,
                                        0
                                )
                                .toString()
                );

        String examName =
                tableModel
                        .getValueAt(
                                modelRow,
                                1
                        )
                        .toString();

        int confirmation =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete\n"
                                + "exam: "
                                + examName
                                + "?",
                        "Delete Exam",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (
                confirmation
                        != JOptionPane.YES_OPTION
        ) {

            return;
        }

        try {

            boolean deleted =
                    examService.deleteExam(
                            examId
                    );

            if (deleted) {

                JOptionPane.showMessageDialog(
                        this,
                        "Exam deleted successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                refreshExamData();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Unable to delete exam.",
                        "Delete Exam",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database error while deleting exam.\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /*
     * =========================
     * COUNT
     * =========================
     */

    private void updateExamCount() {

        int count =
                tableModel.getRowCount();

        examCountLabel.setText(
                count
                        + (
                        count == 1
                                ? " Exam"
                                : " Exams"
                )
        );
    }

    /*
     * =========================
     * PRIMARY BUTTON
     * =========================
     */

   private void stylePrimaryButton(JButton button, int width) {
    button.setFont(new Font("Segoe UI", Font.BOLD, 12));
    button.setForeground(Color.BLACK);
    button.setBackground(new Color(121, 210, 232));
    button.setOpaque(true);
    button.setContentAreaFilled(true);
    button.setBorderPainted(false);
    button.setFocusPainted(false);
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    button.setPreferredSize(new Dimension(width, 42));
}
    /*
     * =========================
     * SECONDARY BUTTON
     * =========================
     */

   private void styleSecondaryButton(
        JButton button,
        int width
) {

    button.setFont(
            new Font(
                    "Segoe UI",
                    Font.BOLD,
                    12
            )
    );

    button.setBackground(
            new Color(121, 210, 232)
    );

    

    button.setOpaque(true);
    button.setContentAreaFilled(true);
    button.setBorderPainted(false);
    button.setFocusPainted(false);

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
                    width,
                    42
            )
    );
}

    /*
     * =========================
     * DANGER BUTTON
     * =========================
     */
private void styleDangerButton(JButton button, int width) {
    button.setFont(new Font("Segoe UI", Font.BOLD, 12));
    button.setForeground(Color.BLACK);
    button.setBackground(new Color(225, 90, 100));
    button.setUI(new javax.swing.plaf.basic.BasicButtonUI());
    button.setOpaque(true);
    button.setContentAreaFilled(true);
    button.setBorderPainted(false);
    button.setFocusPainted(false);
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    button.setPreferredSize(new Dimension(width, 42));
}

    /*
     * =========================
     * DAYS RENDERER
     * =========================
     */

    private static class DaysRemainingRenderer
            extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column
        ) {

            Component component =
                    super.getTableCellRendererComponent(
                            table,
                            value,
                            isSelected,
                            hasFocus,
                            row,
                            column
                    );

            setHorizontalAlignment(
                    SwingConstants.CENTER
            );

            if (!isSelected) {
                setBackground(CARD);

                String text =
                        value == null
                                ? ""
                                : value.toString();

                if (
                        text.equals(
                                "Overdue"
                        )
                ) {

                    setForeground(
                            RED
                    );

                    setFont(
                            new Font(
                                    "Segoe UI",
                                    Font.BOLD,
                                    12
                            )
                    );

                } else if (
                        text.equals(
                                "Today"
                        )
                ) {

                    setForeground(
                            ORANGE
                    );

                    setFont(
                            new Font(
                                    "Segoe UI",
                                    Font.BOLD,
                                    12
                            )
                    );

                } else {

                    setForeground(
                            GREEN
                    );

                    setFont(
                            new Font(
                                    "Segoe UI",
                                    Font.BOLD,
                                    12
                            )
                    );
                }

          } else {

    setBackground(
            new Color(25, 68, 73)
    );

    setForeground(
            Color.WHITE
    );
}

            return component;
        }
    }
}