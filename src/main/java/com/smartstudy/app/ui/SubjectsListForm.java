package com.smartstudy.app.ui;

import com.smartstudy.app.model.Student;
import com.smartstudy.app.model.Subject;
import com.smartstudy.app.service.SubjectService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class SubjectsListForm extends JFrame {

    // =========================================================
    // COLORS
    // =========================================================

    private static final Color BACKGROUND = new Color(7, 20, 22);

    private static final Color CARD = new Color(11, 37, 40);

   private static final Color DARK_TEXT =
        new Color(240, 248, 250);

    private static final Color SECONDARY_TEXT =
        new Color(155, 184, 190);
private static final Color BLUE =
        new Color(121, 210, 232);

    private static final Color BLUE_DARK =
        new Color(75, 175, 200);

   private static final Color BORDER =
        new Color(28, 66, 70);
private static final Color TABLE_HEADER =
        new Color(7, 20, 22);

    private static final Color GREEN =
            new Color(46, 160, 95);

    private static final Color RED =
            new Color(220, 76, 76);

    private static final Color ORANGE =
            new Color(230, 145, 45);

    private static final Color LIGHT_GREEN =
            new Color(232, 247, 238);

    private static final Color LIGHT_RED =
            new Color(253, 236, 236);

    private static final Color LIGHT_ORANGE =
            new Color(255, 245, 228);


    // =========================================================
    // DATA
    // =========================================================

    private final Student loggedInStudent;

    private SubjectService subjectService;


    // =========================================================
    // COMPONENTS
    // =========================================================

    private JTable subjectTable;

    private DefaultTableModel tableModel;

    private JLabel subjectCountLabel;

    private JButton refreshButton;

    private JButton editSubjectButton;

    private JButton deleteSubjectButton;

    private JButton addSubjectButton;

    private JButton updateProgressButton;

    private JTextField searchField;

    private JComboBox<String> difficultyFilterComboBox;

    
    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public SubjectsListForm(Student student) {

        loggedInStudent =
                student;

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

        loadSubjects();
    }


    // =========================================================
    // FRAME
    // =========================================================

    private void initializeFrame() {

        setTitle(
                "AI Smart Study Planner - Subjects"
        );

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setSize(
                1350,
                720
        );

        setMinimumSize(
                new Dimension(
                        1050,
                        620
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
                        28,
                        34,
                        28,
                        34
                )
        );


        // =====================================================
        // HEADER
        // =====================================================

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
                        "My Subjects"
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        34
                )
        );

        title.setForeground(
                DARK_TEXT
        );


        JLabel subtitle =
                new JLabel(
                        "Manage your subjects and track your preparation progress."
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


        // =====================================================
        // HEADER BUTTONS
        // =====================================================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                8,
                                4
                        )
                );

        buttonPanel.setOpaque(false);


        refreshButton =
                new JButton(
                        "REFRESH"
                );

        styleSecondaryButton(
                refreshButton,
                105
        );


        editSubjectButton =
                new JButton(
                        "EDIT SUBJECT"
                );

        styleSecondaryButton(
                editSubjectButton,
                125
        );


        deleteSubjectButton =
                new JButton(
                        "DELETE SUBJECT"
                );

        styleDeleteButton(
                deleteSubjectButton
        );
        updateProgressButton =
        new JButton(
                "UPDATE PROGRESS"
        );

styleSecondaryButton(
        updateProgressButton,
        145
);


        addSubjectButton =
                new JButton(
                        "ADD SUBJECT"
                );

        stylePrimaryButton(
                addSubjectButton
        );


        buttonPanel.add(
                refreshButton
        );

        buttonPanel.add(
                editSubjectButton
        );

        buttonPanel.add(
                deleteSubjectButton
        );

        buttonPanel.add(
        deleteSubjectButton
);

buttonPanel.add(
        addSubjectButton
);

        buttonPanel.add(
                addSubjectButton
        );

        buttonPanel.add(
        updateProgressButton
);


        header.add(
                buttonPanel,
                BorderLayout.EAST
        );


        root.add(
                header,
                BorderLayout.NORTH
        );


        // =====================================================
        // MAIN CONTENT WRAPPER
        // =====================================================

        JPanel contentPanel =
                new JPanel(
                        new BorderLayout()
                );

        contentPanel.setOpaque(false);

        contentPanel.setBorder(
                new EmptyBorder(
                        22,
                        0,
                        0,
                        0
                )
        );


        // =====================================================
        // TABLE CARD
        // =====================================================

        JPanel card =
                new JPanel(
                        new BorderLayout()
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
                                22,
                                22,
                                22,
                                22
                        )
                )
        );


        // =====================================================
        // CARD HEADER
        // =====================================================

        JPanel cardHeader =
                new JPanel(
                        new BorderLayout()
                );

        cardHeader.setOpaque(false);


        JPanel cardTitlePanel =
                new JPanel();

        cardTitlePanel.setOpaque(false);

        cardTitlePanel.setLayout(
                new BoxLayout(
                        cardTitlePanel,
                        BoxLayout.Y_AXIS
                )
        );


        JLabel subjectsTitle =
                new JLabel(
                        "Subjects"
                );

        subjectsTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        21
                )
        );

        subjectsTitle.setForeground(
                DARK_TEXT
        );


        JLabel tableSubtitle =
                new JLabel(
                        "Your registered subjects and preparation status"
                );

        tableSubtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        tableSubtitle.setForeground(
                SECONDARY_TEXT
        );


        cardTitlePanel.add(
                subjectsTitle
        );

        cardTitlePanel.add(
                Box.createVerticalStrut(4)
        );

        cardTitlePanel.add(
                tableSubtitle
        );


        cardHeader.add(
                cardTitlePanel,
                BorderLayout.WEST
        );

        
        // =====================================================
// SEARCH AND FILTER PANEL
// =====================================================

JPanel filterPanel =
        new JPanel(
                new FlowLayout(
                        FlowLayout.RIGHT,
                        10,
                        0
                )
        );

filterPanel.setOpaque(false);


// =====================================================
// SEARCH LABEL
// =====================================================

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


// =====================================================
// SEARCH FIELD
// =====================================================

searchField =
        new JTextField();

searchField.setFont(
        new Font(
                "Segoe UI",
                Font.PLAIN,
                13
        )
);

searchField.setForeground(
        DARK_TEXT
);
searchField.setBackground(new Color(11, 37, 40));
searchField.setBorder(
        new LineBorder(
                BORDER,
                1,
                true
        )
);

searchField.setPreferredSize(
        new Dimension(
                220,
                38
        )
);

searchField.setToolTipText(
        "Search subject by name"
);


// =====================================================
// DIFFICULTY LABEL
// =====================================================

JLabel difficultyLabel =
        new JLabel(
                "DIFFICULTY"
        );

difficultyLabel.setFont(
        new Font(
                "Segoe UI",
                Font.BOLD,
                12
        )
);

difficultyLabel.setForeground(
        SECONDARY_TEXT
);


        // =====================================================
        // DIFFICULTY FILTER
        // =====================================================

        difficultyFilterComboBox = new JComboBox<>(
                new String[]{
                        "All Difficulties",
                        "Easy",
                        "Medium",
                        "Hard"
                }
        );

        difficultyFilterComboBox.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );

      difficultyFilterComboBox.setUI(
    new javax.swing.plaf.basic.BasicComboBoxUI() {
        @Override
        protected JButton createArrowButton() {
            JButton arrow = new JButton("▼");
            arrow.setBackground(CARD);
            arrow.setForeground(BLUE);
            arrow.setOpaque(true);
            arrow.setContentAreaFilled(true);
            arrow.setBorderPainted(false);
            arrow.setFocusPainted(false);
            return arrow;
        }
    }
);

difficultyFilterComboBox.setBackground(CARD);
difficultyFilterComboBox.setOpaque(true);

difficultyFilterComboBox.setRenderer(
    new DefaultListCellRenderer() {
        @Override
        public Component getListCellRendererComponent(
                JList<?> list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {

            JLabel label = (JLabel)
                super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);

            label.setOpaque(true);
            label.setBackground(
                isSelected ? BLUE_DARK : CARD
            );
            label.setForeground(Color.BLACK);

            return label;
        }
    }
);

difficultyFilterComboBox.repaint();

difficultyFilterComboBox.setEditable(false);
difficultyFilterComboBox.setBackground(CARD);
difficultyFilterComboBox.setForeground(Color.BLACK);;
difficultyFilterComboBox.setOpaque(true);

difficultyFilterComboBox.setRenderer(
    new DefaultListCellRenderer() {
        @Override
        public Component getListCellRendererComponent(
                JList<?> list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {

            JLabel label = (JLabel)
                super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);

            label.setOpaque(true);
            label.setBackground(isSelected ? BLUE_DARK : CARD);
            label.setForeground(Color.BLACK);
            return label;
        }
    }
);
        difficultyFilterComboBox.setRenderer(
    new DefaultListCellRenderer() {
        @Override
        public Component getListCellRendererComponent(
                JList<?> list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {

            JLabel label = (JLabel)
                    super.getListCellRendererComponent(
                            list, value, index,
                            isSelected, cellHasFocus);

            label.setOpaque(true);
            label.setBackground(
                    isSelected ? BLUE_DARK : CARD);
            label.setForeground(Color.BLACK); 

            return label;
        }
    }
);

difficultyFilterComboBox.setBackground(CARD);
difficultyFilterComboBox.setForeground(Color.BLACK);
difficultyFilterComboBox.setOpaque(true);
difficultyFilterComboBox.setEditable(false);

        difficultyFilterComboBox.setBackground(CARD);
        difficultyFilterComboBox.setForeground(Color.BLACK);
        difficultyFilterComboBox.setOpaque(true);
        difficultyFilterComboBox.setEditable(false);

        difficultyFilterComboBox.setRenderer(
                new DefaultListCellRenderer() {
                    @Override
                    public Component getListCellRendererComponent(
                            JList<?> list,
                            Object value,
                            int index,
                            boolean isSelected,
                            boolean cellHasFocus
                    ) {
                        JLabel label = (JLabel)
                                super.getListCellRendererComponent(
                                        list, value, index,
                                        isSelected, cellHasFocus
                                );

                        label.setOpaque(true);
                        label.setBackground(
                                isSelected ? BLUE_DARK : CARD
                        );
                        label.setForeground(Color.BLACK);
                        label.setBorder(
                                new EmptyBorder(5, 5, 5, 5)
                        );

                        return label;
                    }
                }
        );

        difficultyFilterComboBox.setBorder(
                new LineBorder(BORDER, 1, true)
        );

        difficultyFilterComboBox.setPreferredSize(
                new Dimension(160, 38)
        );



// =====================================================
// SUBJECT COUNT
// =====================================================

subjectCountLabel =
        new JLabel(
                "0 Subjects"
        );

subjectCountLabel.setFont(
        new Font(
                "Segoe UI",
                Font.BOLD,
                14
        )
);

subjectCountLabel.setForeground(
        BLUE
);

subjectCountLabel.setHorizontalAlignment(
        SwingConstants.CENTER
);

subjectCountLabel.setBorder(
        new EmptyBorder(
                7,
                14,
                7,
                14
        )
);

subjectCountLabel.setOpaque(
        true
);

subjectCountLabel.setBackground(
        CARD
);

// =====================================================
// ADD SEARCH / FILTER CONTROLS
// =====================================================

filterPanel.add(
        searchLabel
);

filterPanel.add(
        searchField
);

filterPanel.add(
        difficultyLabel
);

filterPanel.add(
        difficultyFilterComboBox
);

filterPanel.add(
        subjectCountLabel
);


cardHeader.add(
        filterPanel,
        BorderLayout.EAST
);
        card.add(
                cardHeader,
                BorderLayout.NORTH
        );


        // =====================================================
        // TABLE
        // =====================================================

        String[] columns = {

                "ID",
                "SUBJECT NAME",
                "DIFFICULTY",
                "PREPARATION",
                "TARGET",
                "GAP"
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


        subjectTable =
                new JTable(
                        tableModel
                );


        styleTable();


        JScrollPane scrollPane =
                new JScrollPane(
                        subjectTable
                );

        scrollPane.setBorder(
                new LineBorder(
                        BORDER,
                        1
                )
        );

        scrollPane.getViewport().setBackground(new Color(11, 37, 40));


        JPanel tableWrapper =
                new JPanel(
                        new BorderLayout()
                );

        tableWrapper.setOpaque(false);

        tableWrapper.add(
                Box.createVerticalStrut(18),
                BorderLayout.NORTH
        );

        tableWrapper.add(
                scrollPane,
                BorderLayout.CENTER
        );


        card.add(
                tableWrapper,
                BorderLayout.CENTER
        );


        contentPanel.add(
                card,
                BorderLayout.CENTER
        );


        root.add(
                contentPanel,
                BorderLayout.CENTER
        );


       // =====================================================
        // BUTTON ACTIONS
        // = ====================================================

        refreshButton.addActionListener(
                e -> loadSubjects()
        );


        addSubjectButton.addActionListener(
                e -> openAddSubjectForm()
        );


        editSubjectButton.addActionListener(
                e -> openEditSubjectForm()
        );


        deleteSubjectButton.addActionListener(
                e -> deleteSelectedSubject()
        );
        
        updateProgressButton.addActionListener(
        e -> updateSelectedSubjectProgress()
);
        // =====================================================
// SEARCH ACTION
// =====================================================

searchField.getDocument().addDocumentListener(
        new javax.swing.event.DocumentListener() {

            @Override
            public void insertUpdate(
                    javax.swing.event.DocumentEvent e
            ) {

                filterSubjects();
            }

            @Override
            public void removeUpdate(
                    javax.swing.event.DocumentEvent e
            ) {

                filterSubjects();
            }

            @Override
            public void changedUpdate(
                    javax.swing.event.DocumentEvent e
            ) {

                filterSubjects();
            }
        }
);


// =====================================================
// DIFFICULTY FILTER ACTION
// =====================================================

difficultyFilterComboBox.addActionListener(
        e -> filterSubjects()
);
        


        setContentPane(root);
    }


    // =========================================================
    // TABLE STYLE
    // =========================================================

    private void styleTable() {

        subjectTable.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );


        subjectTable.setForeground(
                DARK_TEXT
        );


        subjectTable.setBackground(new Color(11, 37, 40));

        subjectTable.setRowHeight(
                52
        );


        subjectTable.setShowGrid(
                true
        );


        subjectTable.setGridColor(new Color(28, 66, 70));


        subjectTable.setIntercellSpacing(
                new Dimension(
                        0,
                        1
                )
        );


        subjectTable.setSelectionBackground(new Color(28, 80, 85));

        subjectTable.setSelectionForeground(Color.WHITE);

        subjectTable.setAutoCreateRowSorter(
                true
        );


        subjectTable.setFillsViewportHeight(
                true
        );


        // =====================================================
        // HEADER
        // =====================================================

        subjectTable.getTableHeader()
                .setFont(
                        new Font(
                                "Segoe UI",
                                Font.BOLD,
                                12
                        )
                );


        subjectTable.getTableHeader()
        .setForeground(
                BLUE
        );

        subjectTable.getTableHeader()
                .setBackground(
                        TABLE_HEADER
                );

                subjectTable.getTableHeader().setUI(
        new javax.swing.plaf.basic.BasicTableHeaderUI()
);

subjectTable.getTableHeader().setDefaultRenderer(
        new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column
            ) {
                JLabel label = new JLabel(
                        value == null ? "" : value.toString()
                );

                label.setFont(
                        new Font("Segoe UI", Font.BOLD, 12)
                );
                label.setForeground(BLUE);
                label.setBackground(CARD);
                label.setOpaque(true);
                label.setBorder(
                        BorderFactory.createCompoundBorder(
                                new LineBorder(BORDER, 1),
                                new EmptyBorder(0, 8, 0, 8)
                        )
                );

                return label;
            }
        }
);

                subjectTable.getTableHeader().setOpaque(true);


        subjectTable.getTableHeader()
                .setPreferredSize(
                        new Dimension(
                                0,
                                45
                        )
                );


        subjectTable.getTableHeader()
                .setReorderingAllowed(
                        false
                );


        // =====================================================
        // ID RENDERER
        // =====================================================

        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();


        centerRenderer.setHorizontalAlignment(
                SwingConstants.CENTER
        );


        subjectTable
                .getColumnModel()
                .getColumn(0)
                .setCellRenderer(
                        centerRenderer
                );


        // =====================================================
        // SUBJECT NAME
        // =====================================================

        DefaultTableCellRenderer nameRenderer =
                new DefaultTableCellRenderer();


        nameRenderer.setHorizontalAlignment(
                SwingConstants.LEFT
        );


        nameRenderer.setBorder(
                new EmptyBorder(
                        0,
                        8,
                        0,
                        8
                )
        );


        subjectTable
                .getColumnModel()
                .getColumn(1)
                .setCellRenderer(
                        nameRenderer
                );


        // =====================================================
        // DIFFICULTY
        // =====================================================

        subjectTable
                .getColumnModel()
                .getColumn(2)
                .setCellRenderer(
                        new DifficultyRenderer()
                );


        // =====================================================
        // PREPARATION
        // =====================================================

        subjectTable
                .getColumnModel()
                .getColumn(3)
                .setCellRenderer(
                        new PercentageRenderer()
                );


        // =====================================================
        // TARGET
        // =====================================================

        subjectTable
                .getColumnModel()
                .getColumn(4)
                .setCellRenderer(
                        new PercentageRenderer()
                );


        // =====================================================
        // GAP
        // =====================================================

        subjectTable
                .getColumnModel()
                .getColumn(5)
                .setCellRenderer(
                        new GapRenderer()
                );


        // =====================================================
        // COLUMN WIDTH
        // =====================================================

        subjectTable
                .getColumnModel()
                .getColumn(0)
                .setPreferredWidth(
                        55
                );


        subjectTable
                .getColumnModel()
                .getColumn(1)
                .setPreferredWidth(
                        280
                );


        subjectTable
                .getColumnModel()
                .getColumn(2)
                .setPreferredWidth(
                        140
                );


        subjectTable
                .getColumnModel()
                .getColumn(3)
                .setPreferredWidth(
                        160
                );


        subjectTable
                .getColumnModel()
                .getColumn(4)
                .setPreferredWidth(
                        140
                );


        subjectTable
                .getColumnModel()
                .getColumn(5)
                .setPreferredWidth(
                        120
                );
    }


    // =========================================================
    // LOAD SUBJECTS
    // =========================================================

    private void loadSubjects() {

        if (loggedInStudent == null) {

            tableModel.setRowCount(
                    0
            );

            subjectCountLabel.setText(
                    "0 Subjects"
            );

            return;
        }


        if (subjectService == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Subject service is not available.",
                    "Subjects",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        try {

            List<Subject> subjects =
                    subjectService
                            .getSubjectsByStudentId(
                                    loggedInStudent
                                            .getStudentId()
                            );


            tableModel.setRowCount(
                    0
            );


            for (Subject subject : subjects) {

                double preparation =
                        subject
                                .getPreparationPercentage();


                double target =
                        subject
                                .getTargetPercentage();


                double gap =
                        target - preparation;


                tableModel.addRow(
                        new Object[]{

                                subject.getSubjectId(),

                                subject.getSubjectName(),

                                subject.getDifficulty(),

                                formatPercentage(
                                        preparation
                                ),

                                formatPercentage(
                                        target
                                ),

                                formatPercentage(
                                        gap
                                )
                        }
                );
            }


            int count =
                    subjects.size();


            subjectCountLabel.setText(
                    count
                            + (
                            count == 1
                                    ? " Subject"
                                    : " Subjects"
                    )
            );


        } catch (SQLException e) {

            e.printStackTrace();


            JOptionPane.showMessageDialog(
                    this,
                    "Database error while loading subjects.",
                    "Subjects",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =====================================================
// FILTER SUBJECTS
// =====================================================

private void filterSubjects() {

    if (loggedInStudent == null) {
        return;
    }

    try {

        String searchText =
                searchField
                        .getText()
                        .trim()
                        .toLowerCase();

        String selectedDifficulty =
                (String) difficultyFilterComboBox
                        .getSelectedItem();

        boolean showAllDifficulties =
                selectedDifficulty == null
                        || selectedDifficulty.equals(
                                "All Difficulties"
                        );

        List<Subject> subjects =
                subjectService
                        .getSubjectsByStudentId(
                                loggedInStudent
                                        .getStudentId()
                        );

        tableModel.setRowCount(0);

        int filteredCount = 0;

        for (Subject subject : subjects) {

            String subjectName =
                    subject.getSubjectName()
                            .toLowerCase();

            boolean matchesSearch =
                    subjectName.contains(
                            searchText
                    );

            boolean matchesDifficulty =
                    showAllDifficulties
                            || subject.getDifficulty()
                                    .equalsIgnoreCase(
                                            selectedDifficulty
                                    );

            if (
                    matchesSearch
                            && matchesDifficulty
            ) {

                tableModel.addRow(
                        new Object[]{
                                subject.getSubjectId(),
                                subject.getSubjectName(),
                                subject.getDifficulty(),
                                formatPercentage(
                                        subject
                                                .getPreparationPercentage()
                                ),
                                formatPercentage(
                                        subject
                                                .getTargetPercentage()
                                ),
                                calculateGap(
                                        subject
                                                .getPreparationPercentage(),
                                        subject
                                                .getTargetPercentage()
                                )
                        }
                );

                filteredCount++;
            }
        }

        subjectCountLabel.setText(
                filteredCount
                        + (
                        filteredCount == 1
                                ? " Subject"
                                : " Subjects"
                )
        );

    } catch (SQLException ex) {

        JOptionPane.showMessageDialog(
                this,
                "Unable to filter subjects.\n"
                        + ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}


    // =========================================================
    // FORMAT PERCENTAGE
    // =========================================================

    private String formatPercentage(
            double value
    ) {

        if (value == Math.floor(value)) {

            return String.format(
                    "%.0f%%",
                    value
            );
        }


        return String.format(
                "%.2f%%",
                value
        );
    }


    // =========================================================
    // OPEN ADD SUBJECT
    // =========================================================

    private void openAddSubjectForm() {

        SubjectForm subjectForm =
                new SubjectForm(
                        loggedInStudent
                );


        subjectForm.setLocationRelativeTo(
                this
        );


        subjectForm.addWindowListener(
                new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosed(
                            java.awt.event.WindowEvent e
                    ) {

                        loadSubjects();
                    }
                }
        );


        subjectForm.setVisible(
                true
        );
    }


    // =========================================================
    // OPEN EDIT SUBJECT
    // =========================================================

    private void openEditSubjectForm() {

        int selectedRow =
                subjectTable.getSelectedRow();


        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a subject to edit.",
                    "Edit Subject",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        int modelRow =
                subjectTable.convertRowIndexToModel(
                        selectedRow
                );


        Object subjectIdValue =
                tableModel.getValueAt(
                        modelRow,
                        0
                );


        int subjectId;


        try {

            subjectId =
                    Integer.parseInt(
                            subjectIdValue.toString()
                    );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to identify selected subject.",
                    "Edit Subject",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        Subject selectedSubject =
                findSubjectById(
                        subjectId
                );


        if (selectedSubject == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selected subject could not be found.",
                    "Edit Subject",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        SubjectForm subjectForm =
                new SubjectForm(
                        loggedInStudent,
                        selectedSubject
                );


        subjectForm.setLocationRelativeTo(
                this
        );


        subjectForm.addWindowListener(
                new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosed(
                            java.awt.event.WindowEvent e
                    ) {

                        loadSubjects();
                    }
                }
        );


        subjectForm.setVisible(
                true
        );
    }


    // =========================================================
    // DELETE SELECTED SUBJECT
    // =========================================================

    private void deleteSelectedSubject() {

        int selectedRow =
                subjectTable.getSelectedRow();


        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a subject to delete.",
                    "Delete Subject",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        int modelRow =
                subjectTable.convertRowIndexToModel(
                        selectedRow
                );


        Object subjectIdValue =
                tableModel.getValueAt(
                        modelRow,
                        0
                );


        Object subjectNameValue =
                tableModel.getValueAt(
                        modelRow,
                        1
                );


        int subjectId;


        try {

            subjectId =
                    Integer.parseInt(
                            subjectIdValue.toString()
                    );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to identify selected subject.",
                    "Delete Subject",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        String subjectName =
                subjectNameValue == null
                        ? "this subject"
                        : subjectNameValue.toString();


        int confirmation =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete\n"
                                + subjectName
                                + "?",
                        "Delete Subject",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );


        if (
                confirmation
                        != JOptionPane.YES_OPTION
        ) {

            return;
        }


        if (loggedInStudent == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student information is not available.",
                    "Delete Subject",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        if (subjectService == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Subject service is not available.",
                    "Delete Subject",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        try {

            deleteSubjectButton.setEnabled(
                    false
            );

            deleteSubjectButton.setText(
                    "DELETING..."
            );


            boolean deleted =
                    subjectService.deleteSubject(
                            subjectId,
                            loggedInStudent
                                    .getStudentId()
                    );


            if (deleted) {

                JOptionPane.showMessageDialog(
                        this,
                        "Subject deleted successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );


                loadSubjects();


            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Unable to delete subject.",
                        "Delete Subject",
                        JOptionPane.ERROR_MESSAGE
                );
            }


        } catch (SQLException e) {

            e.printStackTrace();


            JOptionPane.showMessageDialog(
                    this,
                    "Database error while deleting subject.",
                    "Delete Subject",
                    JOptionPane.ERROR_MESSAGE
            );


        } finally {

            deleteSubjectButton.setEnabled(
                    true
            );

            deleteSubjectButton.setText(
                    "DELETE SUBJECT"
            );
        }
    }
    // =========================================================
// UPDATE SELECTED SUBJECT PROGRESS
// =========================================================

private void updateSelectedSubjectProgress() {

    int selectedRow =
            subjectTable.getSelectedRow();

    if (selectedRow == -1) {

        JOptionPane.showMessageDialog(
                this,
                "Please select a subject to update progress.",
                "Update Progress",
                JOptionPane.WARNING_MESSAGE
        );

        return;
    }


    int modelRow =
            subjectTable.convertRowIndexToModel(
                    selectedRow
            );


    Object subjectIdValue =
            tableModel.getValueAt(
                    modelRow,
                    0
            );


    int subjectId;

    try {

        subjectId =
                Integer.parseInt(
                        subjectIdValue.toString()
                );

    } catch (Exception e) {

        JOptionPane.showMessageDialog(
                this,
                "Unable to identify selected subject.",
                "Update Progress",
                JOptionPane.ERROR_MESSAGE
        );

        return;
    }


    Subject selectedSubject =
            findSubjectById(
                    subjectId
            );


    if (selectedSubject == null) {

        JOptionPane.showMessageDialog(
                this,
                "Selected subject could not be found.",
                "Update Progress",
                JOptionPane.ERROR_MESSAGE
        );

        return;
    }


    String input =
            JOptionPane.showInputDialog(
                    this,
                    "Enter new preparation percentage (0-100):",
                    selectedSubject
                            .getPreparationPercentage()
            );


    if (input == null) {
        return;
    }


    try {

        double preparation =
                Double.parseDouble(
                        input.trim()
                );


        if (
                preparation < 0
                        || preparation > 100
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a value between 0 and 100.",
                    "Invalid Progress",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        selectedSubject
                .setPreparationPercentage(
                        preparation
                );


        boolean updated =
                subjectService.updateSubject(
                        selectedSubject
                );


        if (updated) {

            JOptionPane.showMessageDialog(
                    this,
                    "Subject progress updated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadSubjects();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to update subject progress.",
                    "Update Progress",
                    JOptionPane.ERROR_MESSAGE
            );
        }


    } catch (NumberFormatException e) {

        JOptionPane.showMessageDialog(
                this,
                "Please enter a valid number.",
                "Invalid Progress",
                JOptionPane.WARNING_MESSAGE
        );

    } catch (SQLException e) {

        JOptionPane.showMessageDialog(
                this,
                "Database error while updating progress.",
                "Update Progress",
                JOptionPane.ERROR_MESSAGE
        );
    }
}


    // =========================================================
    // FIND SUBJECT BY ID
    // =========================================================
    

    private Subject findSubjectById(
            int subjectId
    ) {
        

        if (loggedInStudent == null) {

            return null;
        }


        if (subjectService == null) {

            return null;
        }


        try {

            List<Subject> subjects =
                    subjectService
                            .getSubjectsByStudentId(
                                    loggedInStudent
                                            .getStudentId()
                            );


            for (Subject subject : subjects) {

                if (
                        subject.getSubjectId()
                                == subjectId
                ) {

                    return subject;
                }
            }


        } catch (SQLException e) {

            e.printStackTrace();


            JOptionPane.showMessageDialog(
                    this,
                    "Database error while finding subject.",
                    "Edit Subject",
                    JOptionPane.ERROR_MESSAGE
            );
        }


        return null;
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
            JButton button,
            int width
    ) {

        button.setUI(
                new javax.swing.plaf.basic.BasicButtonUI()
        );

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        button.setForeground(DARK_TEXT);
        button.setBackground(CARD);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setFocusPainted(false);

        button.setBorder(
                new LineBorder(
                        BORDER,
                        1,
                        true
                )
        );

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        button.setPreferredSize(
                new Dimension(width, 44)
        );

        button.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent e
                    ) {
                        button.setBackground(BLUE_DARK);
                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e
                    ) {
                        button.setBackground(CARD);
                    }
                }
        );
    }



    // =========================================================
    // DELETE BUTTON
    // =========================================================

    private void styleDeleteButton(
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
                RED
        );


     button.setBackground(
        CARD
);


        button.setFocusPainted(
                false
        );


        button.setBorder(
                new LineBorder(
                        RED,
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
                        140,
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
        new Color(65, 30, 35)
);
                    }


                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e
                    ) {

                        button.setBackground(
        CARD
);
                    }
                }
        );
    }


    // =========================================================
    // DIFFICULTY RENDERER
    // =========================================================

    private static class DifficultyRenderer
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

            JLabel label =
                    new JLabel(
                            value == null
                                    ? ""
                                    : value.toString()
                    );


            label.setHorizontalAlignment(
                    SwingConstants.CENTER
            );


            label.setFont(
                    new Font(
                            "Segoe UI",
                            Font.BOLD,
                            12
                    )
            );


            String difficulty =
                    value == null
                            ? ""
                            : value.toString()
                            .toLowerCase();


            if (difficulty.contains("easy")) {

                label.setForeground(
                        GREEN
                );

                label.setOpaque(
                        true
                );

                label.setBackground(
        new Color(20, 55, 43)
);


            } else if (
                    difficulty.contains("hard")
            ) {

                label.setForeground(
                        RED
                );

                label.setOpaque(
                        true
                );

               label.setBackground(
        new Color(65, 30, 35)
);


            } else {

                label.setForeground(
                        ORANGE
                );

                label.setOpaque(
                        true
                );

                label.setBackground(
        new Color(65, 48, 25)
);
            }


            return label;
        }
    }


    // =========================================================
    // PERCENTAGE RENDERER
    // =========================================================

    private static class PercentageRenderer
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

            JPanel panel =
                    new JPanel(
                            new BorderLayout(
                                    8,
                                    0
                            )
                    );


            panel.setOpaque(
                    true
            );


            panel.setBackground(
        isSelected
                ? new Color(
                        28,
                        80,
                        85
                )
                : new Color(
                        11,
                        37,
                        40
                )
);


            String text =
                    value == null
                            ? "0%"
                            : value.toString();


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
                    DARK_TEXT
            );


            label.setHorizontalAlignment(
                    SwingConstants.RIGHT
            );


            JProgressBar progressBar =
                    new JProgressBar(
                            0,
                            100
                    );


            progressBar.setValue(
                    extractPercentage(
                            text
                    )
            );


            progressBar.setStringPainted(
                    false
            );


            progressBar.setBorderPainted(
                    false
            );

          

            progressBar.setPreferredSize(
                    new Dimension(
                            65,
                            7
                    )
            );


            progressBar.setBackground(
        new Color(
                28,
                66,
                70
        )
);


            progressBar.setForeground(
                    BLUE
            );


            panel.setBorder(
                    new EmptyBorder(
                            0,
                            8,
                            0,
                            8
                    )
            );


            panel.add(
                    progressBar,
                    BorderLayout.CENTER
            );


            panel.add(
                    label,
                    BorderLayout.EAST
            );


            return panel;
        }


        private int extractPercentage(
                String value
        ) {

            try {

                String number =
                        value.replace(
                                "%",
                                ""
                        ).trim();


                double percentage =
                        Double.parseDouble(
                                number
                        );


                return Math.max(
                        0,
                        Math.min(
                                100,
                                (int) Math.round(
                                        percentage
                                )
                        )
                );


            } catch (Exception e) {

                return 0;
            }
        }
    }


    // =========================================================
    // GAP RENDERER
    // =========================================================

    private static class GapRenderer
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

            JLabel label =
                    new JLabel(
                            value == null
                                    ? "0%"
                                    : value.toString()
                    );


            label.setHorizontalAlignment(
                    SwingConstants.CENTER
            );


            label.setFont(
                    new Font(
                            "Segoe UI",
                            Font.BOLD,
                            12
                    )
            );


            String text =
                    value == null
                            ? "0"
                            : value.toString()
                            .replace(
                                    "%",
                                    ""
                            )
                            .trim();


            try {

                double gap =
                        Double.parseDouble(
                                text
                        );


                if (gap <= 0) {

                    label.setForeground(
                            GREEN
                    );

                    label.setOpaque(
                            true
                    );

                   label.setBackground(
        new Color(20, 55, 43)
);


                } else {

                    label.setForeground(
                            RED
                    );

                    label.setOpaque(
                            true
                    );

                    label.setBackground(
        new Color(65, 30, 35)
);
                }


            } catch (Exception e) {

                label.setForeground(
                        DARK_TEXT
                );
            }


            return label;
        }
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
    UIManager.getSystemLookAndFeelClassName()
);

                    } catch (Exception ignored) {
                    }


                    SubjectsListForm form =
                            new SubjectsListForm(
                                    null
                            );


                    form.setVisible(
                            true
                    );
                }
        );
    }
    // =====================================================
// CALCULATE GAP
// =====================================================

private String calculateGap(
        double preparationPercentage,
        double targetPercentage
) {

    double gap =
            targetPercentage
                    - preparationPercentage;

    if (gap <= 0) {
        return "On Target";
    }

    return String.format(
            "%.0f%% Gap",
            gap
    );
}
}