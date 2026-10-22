package com.smartstudy.app.ui;

import com.smartstudy.app.model.Student;

import com.smartstudy.app.service.ExamService;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Dashboard extends JFrame {

    // =========================================================
    // COLORS — DARK TEAL THEME
    // =========================================================

    private static final Color SIDEBAR = new Color(5, 23, 26);
    private static final Color SIDEBAR_TEXT = new Color(225, 242, 245);

    private static final Color MAIN_BG = new Color(7, 20, 22);
    private static final Color WHITE = new Color(11, 37, 40);

    private static final Color DARK_TEXT = new Color(240, 248, 250);
    private static final Color SECONDARY_TEXT = new Color(155, 184, 190);

    private static final Color BLUE = new Color(121, 210, 232);
    private static final Color PURPLE = new Color(25, 68, 73);
    private static final Color GREEN = new Color(25, 68, 73);
    private static final Color ORANGE = new Color(25, 68, 73);
    private static final Color RED = new Color(225, 90, 100);

    private static final Color BORDER = new Color(28, 66, 70);

    // =========================================================
    // FONTS
    // =========================================================

    private static final Font TITLE_FONT =
            new Font("Segoe UI", Font.BOLD, 32);

    private static final Font WELCOME_FONT =
            new Font("Segoe UI", Font.BOLD, 29);

    private static final Font SECTION_FONT =
            new Font("Segoe UI", Font.BOLD, 23);

    private static final Font NORMAL_FONT =
            new Font("Segoe UI", Font.PLAIN, 15);

    private static final Font SMALL_FONT =
            new Font("Segoe UI", Font.PLAIN, 13);

    private static final Font STAT_NUMBER_FONT =
            new Font("Segoe UI", Font.BOLD, 29);

    // =========================================================
    // STUDENT
    // =========================================================

    private Student loggedInStudent;
    private JPanel activeMenuItem;

    // =========================================================
    // DEFAULT CONSTRUCTOR
    // =========================================================

    public Dashboard() {
        this(null);
    }

    // =========================================================
    // STUDENT CONSTRUCTOR
    // =========================================================

    public Dashboard(Student student) {

        loggedInStudent = student;
if (loggedInStudent != null) {
    System.out.println(
            "DEBUG Student ID = "
                    + loggedInStudent.getStudentId()
    );
}
        setTitle("AI Smart Study Planner - Dashboard");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(1600, 900);

        setMinimumSize(
                new Dimension(1200, 700)
        );

        setLocationRelativeTo(null);

        setResizable(true);

        buildUI();
    }

    // =========================================================
    // BUILD UI
    // =========================================================

    private void buildUI() {

        JPanel root =
                new JPanel(new BorderLayout());

        root.setBackground(MAIN_BG);

        root.add(
                createSidebar(),
                BorderLayout.WEST
        );

        root.add(
                createMainArea(),
                BorderLayout.CENTER
        );

        setContentPane(root);
    }

    // =========================================================
    // STUDENT NAME
    // =========================================================

    private String getStudentName() {

        if (loggedInStudent != null
                && loggedInStudent.getName() != null
                && !loggedInStudent.getName().trim().isEmpty()) {

            return loggedInStudent.getName();
        }

        return "Aasif";
    }

    // =========================================================
    // STUDENT SEMESTER
    // =========================================================

    private String getStudentSemester() {

        if (loggedInStudent != null) {

            return String.valueOf(
                    loggedInStudent.getSemester()
            );
        }

        return "Not Set";
    }

    // =========================================================
    // SIDEBAR
    // =========================================================

    private JPanel createSidebar() {

        JPanel sidebar =
                new JPanel(new BorderLayout());

        sidebar.setPreferredSize(
                new Dimension(260, 0)
        );

        sidebar.setBackground(SIDEBAR);

        sidebar.setBorder(
                new EmptyBorder(
                        35,
                        30,
                        25,
                        22
                )
        );

        // -----------------------------------------------------
        // LOGO
        // -----------------------------------------------------

        JPanel logoPanel =
                new JPanel();

        logoPanel.setOpaque(false);

        logoPanel.setLayout(
                new BoxLayout(
                        logoPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel ai =
                new JLabel("AI");

        ai.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        38
                )
        );

        ai.setForeground(Color.WHITE);

        ai.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel smart =
                new JLabel("SMART STUDY");

        smart.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        22
                )
        );

        smart.setForeground(
        new Color(121, 210, 232)
);

        smart.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel planner =
                new JLabel("PLANNER");

        planner.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        23
                )
        );

       planner.setForeground(
        new Color(121, 210, 232)
);

        planner.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        logoPanel.add(ai);
        logoPanel.add(smart);
        logoPanel.add(planner);

        sidebar.add(
                logoPanel,
                BorderLayout.NORTH
        );

        // -----------------------------------------------------
        // MENU
        // -----------------------------------------------------

        JPanel menu =
                new JPanel();

        menu.setOpaque(false);

        menu.setLayout(
                new BoxLayout(
                        menu,
                        BoxLayout.Y_AXIS
                )
        );

        menu.add(
                Box.createVerticalStrut(40)
        );

        addMenu(menu, "Dashboard", true);
        addMenu(menu, "Subjects", false);
        addMenu(menu, "Study Plan", false);
        addMenu(menu, "Study Sessions", false);
        addMenu(menu, "Progress", false);
        addMenu(menu, "Exams", false);
        addMenu(menu, "Notes", false);
        addMenu(menu, "Study Materials", false);
        addMenu(menu, "MCQ Practice", false);
        addMenu(menu, "AI Recommendations", false);
        addMenu(menu, "Notifications", false);
        addMenu(menu, "My Profile", false);

        sidebar.add(
                menu,
                BorderLayout.CENTER
        );

        // -----------------------------------------------------
        // LOGOUT
        // -----------------------------------------------------

        JPanel bottom =
                new JPanel();

        bottom.setOpaque(false);

        bottom.setLayout(
                new BoxLayout(
                        bottom,
                        BoxLayout.Y_AXIS
                )
        );

        JButton logout =
                new JButton("LOGOUT");

        logout.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        logout.setForeground(Color.WHITE);

        logout.setBackground(new Color(11, 37, 40));

        logout.setFocusPainted(false);

        logout.setBorderPainted(false);

        logout.setOpaque(true);

        logout.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        47
                )
        );

        logout.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        logout.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        logout.addActionListener(
                e -> logout()
        );

        bottom.add(logout);

        sidebar.add(
                bottom,
                BorderLayout.SOUTH
        );

        return sidebar;
    }

    // =========================================================
    // ADD MENU
    // =========================================================

    private void addMenu(
            JPanel menu,
            String text,
            boolean active
    ) {

        menu.add(
                createMenuItem(
                        text,
                        active
                )
        );

        menu.add(
                Box.createVerticalStrut(7)
        );
    }

   // =========================================================
// MENU ITEM
// =========================================================

private JPanel createMenuItem(
        String text,
        boolean active
) {

    JPanel panel =
            new JPanel(
                    new BorderLayout()
            );

    panel.setMaximumSize(
            new Dimension(
                    Integer.MAX_VALUE,
                    42
            )
    );

    panel.setPreferredSize(
            new Dimension(
                    210,
                    42
            )
    );

    panel.setBackground(
            active
                    ? new Color(16, 54, 58)
                    : SIDEBAR
    );

    JLabel label =
            new JLabel(text);

    label.setFont(
            NORMAL_FONT
    );

    label.setForeground(
            active
                    ? Color.WHITE
                    : SIDEBAR_TEXT
    );

    panel.add(
            label,
            BorderLayout.WEST
    );
    if (active) {
    activeMenuItem = panel;
}

java.awt.event.MouseAdapter menuClickListener =
        new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        if (activeMenuItem != null) {
            activeMenuItem.setBackground(SIDEBAR);

            Component oldComponent =
                    activeMenuItem.getComponent(0);

            if (oldComponent instanceof JLabel oldLabel) {
                oldLabel.setForeground(SIDEBAR_TEXT);
            }
        }

        activeMenuItem = panel;
        panel.setBackground(new Color(16, 54, 58));
        label.setForeground(Color.WHITE);
    }
};

panel.addMouseListener(menuClickListener);
label.addMouseListener(menuClickListener);

    // =====================================================
    // SUBJECTS
    // =====================================================

    if (text.equals("Subjects")) {

        panel.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        panel.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            java.awt.event.MouseEvent e
                    ) {

                        SubjectsListForm subjectsListForm =
        new SubjectsListForm(
                loggedInStudent
        );

subjectsListForm.setLocationRelativeTo(
        Dashboard.this
);

subjectsListForm.setVisible(true);
                    }
                }
        );
    }

    // =====================================================
    // STUDY PLAN
    // =====================================================

    if (text.equals("Study Plan")) {

        panel.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        panel.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            java.awt.event.MouseEvent e
                    ) {

                        StudyPlanForm studyPlanForm =
                                new StudyPlanForm(
                                        loggedInStudent
                                );

                        studyPlanForm.setLocationRelativeTo(
                                Dashboard.this
                        );

                        studyPlanForm.setVisible(true);
                    }
                }
        );
    }

    // =====================================================
// EXAMS
// =====================================================

if (text.equals("Exams")) {

    panel.setCursor(
            new Cursor(
                    Cursor.HAND_CURSOR
            )
    );

    panel.addMouseListener(
            new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(
                        java.awt.event.MouseEvent e
                ) {

                    ExamsListForm examsListForm =
                            new ExamsListForm(
                                    loggedInStudent
                            );

                    examsListForm.setLocationRelativeTo(
                            Dashboard.this
                    );

                    examsListForm.setVisible(true);
                }
            }
    );
}



// 👇 YAHAN PASTE KARO
if (text.equals("Study Sessions")) {
    panel.setCursor(
            new Cursor(
                    Cursor.HAND_CURSOR
            )
    );

    panel.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(
                        java.awt.event.MouseEvent e
                ) {
                    StudySessionsListForm studySessionsListForm =
                            new StudySessionsListForm(
                                    loggedInStudent
                            );

                    studySessionsListForm.setLocationRelativeTo(
                            Dashboard.this
                    );

                    studySessionsListForm.setVisible(true);
                }
                
            }
    );
}

if (text.equals("Progress")) {

    panel.setCursor(
            new Cursor(
                    Cursor.HAND_CURSOR
            )
    );

    panel.addMouseListener(
            new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(
                        java.awt.event.MouseEvent e
                ) {

                    ProgressForm progressForm =
                            new ProgressForm(
                                    loggedInStudent
                            );

                    progressForm.setLocationRelativeTo(
                            Dashboard.this
                    );

                    progressForm.setVisible(true);
                }
            }
    );
}

        // NOTES
        if (text.equals("Notes")) {
    panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

    panel.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            NotesForm notesForm = new NotesForm(loggedInStudent);
            notesForm.setLocationRelativeTo(Dashboard.this);
            notesForm.setVisible(true);
        }
    });
}
        
        
       
        // STUDY MATERIALS

        if (text.equals("Study Materials")) {
            panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

            panel.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {

                    JPanel choicePanel = new JPanel();
                    choicePanel.setLayout(
                            new BoxLayout(choicePanel, BoxLayout.Y_AXIS)
                    );
                    choicePanel.setBackground(new Color(7, 21, 23));
                    choicePanel.setBorder(
                            new EmptyBorder(22, 24, 22, 24)
                    );
                    choicePanel.setPreferredSize(
                            new Dimension(400, 180)
                    );

                    JLabel titleLabel =
                            new JLabel("STUDY MATERIALS");

                    titleLabel.setFont(
                            new Font("Segoe UI", Font.BOLD, 22)
                    );
                    titleLabel.setForeground(
                            new Color(121, 210, 232)
                    );
                    titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

                    JLabel subtitleLabel =
                            new JLabel("Choose an action to continue");

                    subtitleLabel.setFont(
                            new Font("Segoe UI", Font.PLAIN, 13)
                    );
                    subtitleLabel.setForeground(
                            new Color(160, 190, 198)
                    );
                    subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

                    choicePanel.add(titleLabel);
                    choicePanel.add(Box.createVerticalStrut(8));
                    choicePanel.add(subtitleLabel);
                    choicePanel.add(Box.createVerticalStrut(22));

                    
JButton uploadButton = new JButton("Upload Study Material");
JButton viewButton = new JButton("View Saved Materials");
JButton cancelButton = new JButton("Cancel");

JButton[] buttons = {uploadButton, viewButton, cancelButton};

for (JButton button : buttons) {
    button.setFont(new Font("Segoe UI", Font.BOLD, 12));
    button.setBackground(new Color(11, 37, 40));
    button.setForeground(new Color(121, 210, 232));
    button.setOpaque(true);
    button.setContentAreaFilled(true);
    button.setBorderPainted(true);
    button.setFocusPainted(false);
    button.setBorder(new LineBorder(new Color(28, 66, 70), 1));
    button.setPreferredSize(new Dimension(155, 36));
}


                    UIManager.put("OptionPane.background", new Color(7, 21, 23));
UIManager.put("Panel.background", new Color(7, 21, 23));
UIManager.put("OptionPane.messageArea.background", new Color(7, 21, 23));

                    UIManager.put("Button.background", new Color(11, 37, 40));
UIManager.put("Button.foreground", new Color(121, 210, 232));
UIManager.put("Button.select", new Color(11, 37, 40));
UIManager.put("Button.focus", new Color(11, 37, 40));
UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 12));

                   
JOptionPane optionPane = new JOptionPane(
        choicePanel,
        JOptionPane.PLAIN_MESSAGE,
        JOptionPane.DEFAULT_OPTION,
        null,
        buttons,
        uploadButton
);

JDialog dialog = optionPane.createDialog(
        Dashboard.this,
        "Study Materials"
);

final int[] choice = {-1};

uploadButton.addActionListener(event -> {
    choice[0] = 0;
    dialog.dispose();
});

viewButton.addActionListener(event -> {
    choice[0] = 1;
    dialog.dispose();
});

cancelButton.addActionListener(event -> {
    choice[0] = 2;
    dialog.dispose();
});

dialog.setVisible(true);

int selectedChoice = choice[0];


                    if (selectedChoice == 0) {
                        StudyMaterialsForm form =
                                new StudyMaterialsForm(loggedInStudent);

                        form.setLocationRelativeTo(Dashboard.this);
                        form.setVisible(true);

                    } else if (selectedChoice == 1) {
                        StudyMaterialsListForm form =
                                new StudyMaterialsListForm(loggedInStudent);

                        form.setLocationRelativeTo(Dashboard.this);
                        form.setVisible(true);
                    }
                }
            });
        }



        
        // MCQ PRACTICE
        if (text.equals("MCQ Practice")) {
            panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

            panel.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    StudyMaterialsListForm form =
                            new StudyMaterialsListForm(loggedInStudent);

                    form.setLocationRelativeTo(Dashboard.this);
                    form.setVisible(true);
                }
            });
        }

       // AI RECOMMENDATIONS
if (text.equals("AI Recommendations")) {
    panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

    panel.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            if (loggedInStudent == null) {
                JOptionPane.showMessageDialog(
                        Dashboard.this,
                        "Please log in first.",
                        "AI Recommendations",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            StringBuilder recommendations = new StringBuilder();
            recommendations.append("YOUR STUDY RECOMMENDATIONS\n");
recommendations.append("-----------------------------------------\n\n");

            String sql = """
                    SELECT s.SUBJECT_NAME,
                           COUNT(qr.RESULT_ID) AS TOTAL_QUIZZES,
                           NVL(AVG(qr.SCORE_PERCENTAGE), 0) AS AVG_SCORE
                    FROM SUBJECTS s
                    LEFT JOIN QUIZ_RESULTS qr
                        ON s.SUBJECT_ID = qr.SUBJECT_ID
                        AND qr.STUDENT_ID = ?
                    WHERE s.STUDENT_ID = ?
                    GROUP BY s.SUBJECT_ID, s.SUBJECT_NAME
                    ORDER BY AVG_SCORE ASC, s.SUBJECT_NAME
                    """;

            boolean foundSubject = false;

            try (
                    Connection connection =
                            com.smartstudy.app.config.DatabaseConfig.getConnection();
                    PreparedStatement statement =
                            connection.prepareStatement(sql)
            ) {
                statement.setInt(1, loggedInStudent.getStudentId());
                statement.setInt(2, loggedInStudent.getStudentId());

                try (java.sql.ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        foundSubject = true;

                        String subject = rs.getString("SUBJECT_NAME");
                        int quizzes = rs.getInt("TOTAL_QUIZZES");
                        double score = rs.getDouble("AVG_SCORE");

                        recommendations.append(subject).append(": ");

                        if (quizzes == 0) {
                            recommendations.append(
                                    "Start your first quiz to check your preparation."
                            );
                        } else if (score < 40) {
                            recommendations.append(
                                    "Weak subject. Revise the topics and practise more MCQs."
                            );
                        } else if (score < 75) {
                            recommendations.append(
                                    "Needs improvement. Revise mistakes and attempt another quiz."
                            );
                        } else {
                            recommendations.append(
                                    "Good performance. Continue practising to maintain your score."
                            );
                        }

                        recommendations.append("\n\n");
                    }
                }

                if (!foundSubject) {
                    recommendations.append(
                            "No subjects found. Add subjects to receive recommendations."
                    );
                }

               JTextArea area = new JTextArea(recommendations.toString(), 16, 48);
area.setEditable(false);
area.setLineWrap(true);
area.setWrapStyleWord(true);
area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
area.setCaretPosition(0);
area.setBackground(new Color(11, 37, 40));
area.setForeground(new Color(240, 248, 250));
area.setBorder(new EmptyBorder(15, 15, 15, 15));
                


                JScrollPane scrollPane = new JScrollPane(area);
                scrollPane.setPreferredSize(new Dimension(550, 350));

                scrollPane.getViewport().setBackground(new Color(11, 37, 40));
scrollPane.setBorder(new LineBorder(new Color(28, 66, 70), 1));

JFrame dialog = new JFrame("AI Study Recommendations");

dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
dialog.setLayout(new BorderLayout());
dialog.add(scrollPane, BorderLayout.CENTER);

dialog.setSize(800, 600);
dialog.setMinimumSize(new Dimension(500, 400));
dialog.setResizable(true);
dialog.setLocationRelativeTo(Dashboard.this);
dialog.setVisible(true);
            } catch (java.sql.SQLException ex) {
                JOptionPane.showMessageDialog(
                        Dashboard.this,
                        "Could not load recommendations:\n" + ex.getMessage(),
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    });
}

    // NOTIFICATIONS
if (text.equals("Notifications")) {
    panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

    panel.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {

            if (loggedInStudent == null) {
                JOptionPane.showMessageDialog(
                        Dashboard.this,
                        "Please log in first.",
                        "Notifications",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            StringBuilder message = new StringBuilder();
            message.append("YOUR NOTIFICATIONS\n\n");

            int count = 0;

            try {
                ExamService examService = new ExamService();

                java.util.List<com.smartstudy.app.model.Exam> exams =
                        examService.getExamsByStudentId(
                                loggedInStudent.getStudentId()
                        );

                java.time.LocalDate today =
                        java.time.LocalDate.now();

                for (com.smartstudy.app.model.Exam exam : exams) {
                    try {
                        java.time.LocalDate examDate =
                                java.time.LocalDate.parse(exam.getExamDate());

                        if (!examDate.isBefore(today)) {
                            message.append("UPCOMING EXAM: ")
                                    .append(exam.getExamName())
                                    .append(" - ")
                                    .append(examDate)
                                    .append("\n\n");
                            count++;
                        }
                    } catch (Exception ignored) {
                        // Skip invalid exam dates.
                    }
                }

                com.smartstudy.app.service.StudyPlanService planService =
                        new com.smartstudy.app.service.StudyPlanService();

                java.util.List<com.smartstudy.app.model.StudyPlan> plans =
                        planService.getStudyPlansByStudentId(
                                loggedInStudent.getStudentId()
                        );

                for (com.smartstudy.app.model.StudyPlan plan : plans) {
                    if ("PENDING".equalsIgnoreCase(plan.getStatus())) {
                        message.append("PENDING STUDY PLAN: ")
                                .append(plan.getTask())
                                .append("\n\n");
                        count++;
                    }
                }

                if (count == 0) {
                    message.append("You're all caught up! No new notifications.");
                }

                
JPanel notificationPanel = new JPanel(new BorderLayout(0, 18));
notificationPanel.setBackground(new Color(7, 21, 23));
notificationPanel.setBorder(new EmptyBorder(22, 24, 18, 24));
notificationPanel.setPreferredSize(new Dimension(520, 330));

JPanel headerPanel = new JPanel(new BorderLayout());
headerPanel.setBackground(new Color(7, 21, 23));

JLabel titleLabel = new JLabel("Your Notifications");
titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
titleLabel.setForeground(new Color(121, 210, 232));

JLabel countLabel = new JLabel(count + " notification(s)");
countLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
countLabel.setForeground(new Color(160, 190, 198));

headerPanel.add(titleLabel, BorderLayout.NORTH);
headerPanel.add(countLabel, BorderLayout.SOUTH);

JPanel listPanel = new JPanel();
listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
listPanel.setBackground(new Color(7, 21, 23));

String[] items = message.toString().split("\\n\\n");

for (int i = 1; i < items.length; i++) {
    String item = items[i].trim();

    if (item.isEmpty()) {
        continue;
    }

    JPanel card = new JPanel(new BorderLayout(0, 8));
    card.setBackground(new Color(11, 37, 40));
    card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(28, 66, 70), 1, true),
            new EmptyBorder(14, 16, 14, 16)
    ));
    card.setAlignmentX(Component.LEFT_ALIGNMENT);
    card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));

    String heading;
    String details;
    Color accent;

    if (item.startsWith("UPCOMING EXAM:")) {
        heading = "UPCOMING EXAM";
        details = item.substring("UPCOMING EXAM:".length()).trim();
        accent = new Color(121, 210, 232);
    } else if (item.startsWith("PENDING STUDY PLAN:")) {
        heading = "PENDING STUDY PLAN";
        details = item.substring("PENDING STUDY PLAN:".length()).trim();
        accent = new Color(255, 190, 100);
    } else {
        heading = "ALL CAUGHT UP";
        details = item;
        accent = new Color(120, 220, 160);
    }

    JLabel headingLabel = new JLabel(heading);
    headingLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
    headingLabel.setForeground(accent);

    JTextArea detailsArea = new JTextArea(details);
    detailsArea.setEditable(false);
    detailsArea.setLineWrap(true);
    detailsArea.setWrapStyleWord(true);
    detailsArea.setOpaque(false);
    detailsArea.setFocusable(false);
    detailsArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    detailsArea.setForeground(new Color(240, 248, 250));

    card.add(headingLabel, BorderLayout.NORTH);
    card.add(detailsArea, BorderLayout.CENTER);

    listPanel.add(card);
    listPanel.add(Box.createVerticalStrut(12));
}

JScrollPane scrollPane = new JScrollPane(listPanel);
scrollPane.setBorder(BorderFactory.createEmptyBorder());
scrollPane.getViewport().setBackground(new Color(7, 21, 23));
scrollPane.getVerticalScrollBar().setUnitIncrement(16);

notificationPanel.add(headerPanel, BorderLayout.NORTH);
notificationPanel.add(scrollPane, BorderLayout.CENTER);

UIManager.put("OptionPane.background", new Color(7, 21, 23));
UIManager.put("Panel.background", new Color(7, 21, 23));
UIManager.put("Button.background", new Color(11, 37, 40));
UIManager.put("Button.foreground", new Color(121, 210, 232));
UIManager.put("Button.select", new Color(11, 37, 40));
UIManager.put("Button.focus", new Color(11, 37, 40));
UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 13));
UIManager.put("Button.opaque", true);


JButton okButton = new JButton("OK");
okButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
okButton.setBackground(new Color(11, 37, 40));
okButton.setForeground(new Color(121, 210, 232));
okButton.setOpaque(true);
okButton.setContentAreaFilled(true);
okButton.setBorderPainted(true);
okButton.setFocusPainted(false);
okButton.setBorder(new LineBorder(new Color(28, 66, 70), 1));
okButton.setPreferredSize(new Dimension(120, 32));

JOptionPane optionPane = new JOptionPane(
        notificationPanel,
        JOptionPane.PLAIN_MESSAGE,
        JOptionPane.DEFAULT_OPTION,
        null,
        new Object[]{okButton},
        okButton
);

JDialog notificationDialog = optionPane.createDialog(
        Dashboard.this,
        "Notifications (" + count + ")"
);

okButton.addActionListener(event -> notificationDialog.dispose());

notificationDialog.setVisible(true);


            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        Dashboard.this,
                        "Unable to load notifications:\n" + ex.getMessage(),
                        "Notifications Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    });
}

       
 // MY PROFILE
if (text.equals("My Profile")) {
    panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

    panel.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {

            if (loggedInStudent == null) {
                JOptionPane.showMessageDialog(
                        Dashboard.this,
                        "Please log in first.",
                        "My Profile",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            String sql = """
                    SELECT NAME, ROLL_NO, EMAIL, SEMESTER
                    FROM STUDENTS
                    WHERE STUDENT_ID = ?
                    """;

            try (
                    Connection connection =
                            com.smartstudy.app.config.DatabaseConfig.getConnection();
                    PreparedStatement statement =
                            connection.prepareStatement(sql)
            ) {
                statement.setInt(
                        1,
                        loggedInStudent.getStudentId()
                );

                try (java.sql.ResultSet rs =
                             statement.executeQuery()) {

                    if (rs.next()) {

                        JPanel profilePanel = new JPanel();
                        profilePanel.setLayout(
                                new BoxLayout(profilePanel, BoxLayout.Y_AXIS)
                        );
                        profilePanel.setBackground(new Color(7, 21, 23));
                        profilePanel.setBorder(
                                new EmptyBorder(22, 24, 22, 24)
                        );
                       

                        JLabel titleLabel = new JLabel("MY PROFILE");
                        titleLabel.setFont(
                                new Font("Segoe UI", Font.BOLD, 23)
                        );
                        titleLabel.setForeground(
                                new Color(121, 210, 232)
                        );
                        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

                        JLabel subtitleLabel =
                                new JLabel("Your personal information");
                        subtitleLabel.setFont(
                                new Font("Segoe UI", Font.PLAIN, 13)
                        );
                        subtitleLabel.setForeground(
                                new Color(160, 190, 198)
                        );
                        subtitleLabel.setAlignmentX(
                                Component.LEFT_ALIGNMENT
                        );

                        profilePanel.add(titleLabel);
                        profilePanel.add(Box.createVerticalStrut(5));
                        profilePanel.add(subtitleLabel);
                        profilePanel.add(Box.createVerticalStrut(22));

                        String[] labels = {
                                "FULL NAME",
                                "ROLL NUMBER",
                                "EMAIL ADDRESS",
                                "CURRENT SEMESTER"
                        };

                        String[] values = {
                                rs.getString("NAME"),
                                rs.getString("ROLL_NO"),
                                rs.getString("EMAIL"),
                                String.valueOf(rs.getInt("SEMESTER"))
                        };

                        for (int i = 0; i < labels.length; i++) {

                            JPanel row = new JPanel(
                                    new BorderLayout(0, 6)
                            );
                            row.setBackground(new Color(11, 37, 40));
                            row.setBorder(
                                    BorderFactory.createCompoundBorder(
                                            new LineBorder(
                                                    new Color(28, 66, 70),
                                                    1,
                                                    true
                                            ),
                                            new EmptyBorder(10, 13, 10, 13)
                                    )
                            );
                            row.setAlignmentX(Component.LEFT_ALIGNMENT);
                            row.setMaximumSize(
                                    new Dimension(Integer.MAX_VALUE, 65)
                            );

                            JLabel fieldLabel = new JLabel(labels[i]);
                            fieldLabel.setFont(
                                    new Font("Segoe UI", Font.BOLD, 10)
                            );
                            fieldLabel.setForeground(
                                    i == 3
                                            ? new Color(255, 190, 100)
                                            : new Color(121, 210, 232)
                            );

                            JLabel valueLabel = new JLabel(values[i]);
                            valueLabel.setFont(
                                    new Font("Segoe UI", Font.PLAIN, 14)
                            );
                            valueLabel.setForeground(
                                    new Color(240, 248, 250)
                            );

                            row.add(fieldLabel, BorderLayout.NORTH);
                            row.add(valueLabel, BorderLayout.CENTER);

                            profilePanel.add(row);

                            if (i < labels.length - 1) {
                                profilePanel.add(
                                        Box.createVerticalStrut(10)
                                );
                            }
                        }

                        JScrollPane profileScrollPane = new JScrollPane(profilePanel);

profileScrollPane.setPreferredSize(new Dimension(470, 400));
profileScrollPane.setBorder(BorderFactory.createEmptyBorder());
profileScrollPane.getViewport().setBackground(new Color(7, 21, 23));
profileScrollPane.getVerticalScrollBar().setUnitIncrement(16);

                        JOptionPane.showMessageDialog(
        Dashboard.this,
        profileScrollPane,
        "My Profile",
        JOptionPane.PLAIN_MESSAGE
);

                    } else {
                        JOptionPane.showMessageDialog(
                                Dashboard.this,
                                "Student profile not found.",
                                "My Profile",
                                JOptionPane.WARNING_MESSAGE
                        );
                    }
                }

            } catch (java.sql.SQLException ex) {
                JOptionPane.showMessageDialog(
                        Dashboard.this,
                        "Could not load profile:\n" + ex.getMessage(),
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    });
}

for (java.awt.event.MouseListener listener
        : panel.getMouseListeners()) {

    if (listener != menuClickListener) {
        label.addMouseListener(listener);
    }
}

label.setCursor(panel.getCursor());

    return panel;
}

    // =========================================================
    // MAIN AREA
    // =========================================================

    private double getAverageQuizScore() {
    String sql = """
        SELECT NVL(AVG(SCORE_PERCENTAGE), 0)
        FROM QUIZ_RESULTS
        WHERE STUDENT_ID = ?
        """;

    try (Connection connection =
                 com.smartstudy.app.config.DatabaseConfig.getConnection();
         PreparedStatement statement =
                 connection.prepareStatement(sql)) {

        if (loggedInStudent == null) {
            return 0;
        }

        statement.setInt(1, loggedInStudent.getStudentId());

        try (java.sql.ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getDouble(1);
            }
        }

    } catch (java.sql.SQLException e) {
        e.printStackTrace();
    }

    return 0;
}

private double getPreparationProgress() {
    String sql = """
        SELECT NVL(AVG(PROGRESS_PERCENTAGE), 0)
        FROM PROGRESS
        WHERE STUDENT_ID = ?
        """;

    if (loggedInStudent == null) {
        return 0;
    }

    try (Connection connection =
                 com.smartstudy.app.config.DatabaseConfig.getConnection();
         PreparedStatement statement =
                 connection.prepareStatement(sql)) {

        statement.setInt(1, loggedInStudent.getStudentId());

        try (java.sql.ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        }

    } catch (java.sql.SQLException e) {
        e.printStackTrace();
    }

    return 0;
}

private int getSubjectCount() {
    if (loggedInStudent == null) {
        return 0;
    }

    try {
        com.smartstudy.app.service.SubjectService subjectService =
                new com.smartstudy.app.service.SubjectService();

        java.util.List<com.smartstudy.app.model.Subject> subjects =
                subjectService.getSubjectsByStudentId(
                        loggedInStudent.getStudentId()
                );

        return subjects.size();

    } catch (java.sql.SQLException e) {
        e.printStackTrace();
        return 0;
    }
}


private int getStudySessionCount() {
    if (loggedInStudent == null) return 0;

    try {
        com.smartstudy.app.service.StudySessionService sessionService =
                new com.smartstudy.app.service.StudySessionService();

        java.util.List<com.smartstudy.app.model.StudySession> sessions =
                sessionService.getStudySessionsByStudentId(
                        loggedInStudent.getStudentId()
                );

        return sessions.size();

    } catch (java.sql.SQLException e) {
        e.printStackTrace();
        return 0;
    }
}


private int getUpcomingExamCount() {
    if (loggedInStudent == null) {
        return 0;
    }
    try {
        ExamService examService = new ExamService();

        java.util.List<com.smartstudy.app.model.Exam> exams =
                examService.getExamsByStudentId(
                        loggedInStudent.getStudentId()
                );

        int count = 0;
        java.time.LocalDate today = java.time.LocalDate.now();

        for (com.smartstudy.app.model.Exam exam : exams) {
            try {
                java.time.LocalDate examDate =
                        java.time.LocalDate.parse(exam.getExamDate());

                if (!examDate.isBefore(today)) {
                    count++;
                }
            } catch (Exception ignored) {
                // Ignore exam dates that cannot be parsed.
            }
        }

        return count;

    } catch (java.sql.SQLException e) {
        e.printStackTrace();
        return 0;
    }
}


private String getNextExamDetails() {
    if (loggedInStudent == null) {
        return "No Exam";
    }

    try {
        ExamService examService = new ExamService();

        java.util.List<com.smartstudy.app.model.Exam> exams =
                examService.getExamsByStudentId(
                        loggedInStudent.getStudentId()
                );

        java.time.LocalDate today = java.time.LocalDate.now();

        for (com.smartstudy.app.model.Exam exam : exams) {
            try {
                java.time.LocalDate examDate =
                        java.time.LocalDate.parse(exam.getExamDate());

                if (!examDate.isBefore(today)) {
                    String subjectName = "Subject";

                    com.smartstudy.app.service.SubjectService subjectService =
                            new com.smartstudy.app.service.SubjectService();

                    for (com.smartstudy.app.model.Subject subject :
                            subjectService.getSubjectsByStudentId(
                                    loggedInStudent.getStudentId())) {

                        if (subject.getSubjectId() == exam.getSubjectId()) {
                            subjectName = subject.getSubjectName();
                            break;
                        }
                    }

                    String formattedDate = examDate.format(
                            java.time.format.DateTimeFormatter.ofPattern(
                                    "dd MMM yyyy"
                            )
                    );

                    return exam.getExamName()
                            + " | " + subjectName
                            + " | " + formattedDate;
                }
            } catch (Exception ignored) {
                // Skip exams with invalid dates.
            }
        }

    } catch (java.sql.SQLException e) {
        e.printStackTrace();
    }

    return "No Upcoming Exam";
}



    private JPanel createMainArea() {

        JPanel main =
                new JPanel(
                        new BorderLayout()
                );

        main.setBackground(MAIN_BG);

        // -----------------------------------------------------
        // HEADER
        // -----------------------------------------------------

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setBackground(WHITE);

        header.setBorder(
                new EmptyBorder(
                        22,
                        38,
                        22,
                        38
                )
        );

        JLabel title =
                new JLabel("Dashboard");

        title.setFont(TITLE_FONT);

        title.setForeground(DARK_TEXT);

        header.add(
                title,
                BorderLayout.WEST
        );

        JPanel userDate =
                new JPanel();

        userDate.setOpaque(false);

        userDate.setLayout(
                new BoxLayout(
                        userDate,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel user =
                new JLabel(
                        "USER  "
                                + getStudentName().toUpperCase()
                );

        user.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        user.setForeground(DARK_TEXT);

        user.setAlignmentX(
                Component.RIGHT_ALIGNMENT
        );

        String today =
                new SimpleDateFormat(
                        "dd MMMM yyyy"
                ).format(
                        new Date()
                );

        JLabel date =
                new JLabel(
                        "Today • " + today
                );

        date.setFont(SMALL_FONT);

        date.setForeground(SECONDARY_TEXT);

        date.setAlignmentX(
                Component.RIGHT_ALIGNMENT
        );

        userDate.add(user);

        userDate.add(
                Box.createVerticalStrut(5)
        );

        userDate.add(date);

        header.add(
                userDate,
                BorderLayout.EAST
        );

        main.add(
                header,
                BorderLayout.NORTH
        );

        // -----------------------------------------------------
        // CONTENT
        // -----------------------------------------------------

        JPanel content =
                new JPanel();

        content.setBackground(MAIN_BG);

        content.setBorder(
                new EmptyBorder(
                        30,
                        37,
                        35,
                        37
                )
        );

        content.setLayout(
                new BoxLayout(
                        content,
                        BoxLayout.Y_AXIS
                )
        );

        
        
        // -----------------------------------------------------
        // WELCOME BANNER — DARK TEAL THEME
        // -----------------------------------------------------

        JPanel welcomeBanner = new JPanel(new BorderLayout(15, 0));
        welcomeBanner.setBackground(new Color(16, 54, 58));
        welcomeBanner.setBorder(
        BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(45, 100, 105), 1, true),
                new EmptyBorder(22, 26, 22, 26)
        )
);
        welcomeBanner.setAlignmentX(Component.LEFT_ALIGNMENT);
        welcomeBanner.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 115)
        );

        JPanel welcomeTextPanel = new JPanel();
        welcomeTextPanel.setOpaque(false);
        welcomeTextPanel.setLayout(
                new BoxLayout(welcomeTextPanel, BoxLayout.Y_AXIS)
        );

        JLabel welcome = new JLabel(
                "Hello, " + getStudentName() + "!"
        );
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 30));
        welcome.setForeground(new Color(240, 248, 250));
        welcome.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel(
                "Stay organized, study smarter, and achieve your academic goals."
        );
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subtitle.setForeground(new Color(155, 184, 190));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        welcomeTextPanel.add(welcome);
        welcomeTextPanel.add(Box.createVerticalStrut(7));
        welcomeTextPanel.add(subtitle);

        JLabel bannerMessage = new JLabel(
                "<html><div style='text-align:center;'>"
                + "<b>STUDY SMARTER</b><br>"
                + "<span style='font-size:11px;'>"
                + "Build your success<br>one day at a time"
                + "</span></div></html>"
        );
        bannerMessage.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        bannerMessage.setForeground(new Color(121, 210, 232));
        bannerMessage.setHorizontalAlignment(SwingConstants.CENTER);
        bannerMessage.setOpaque(true);
        bannerMessage.setBackground(new Color(7, 27, 30));
        bannerMessage.setBorder(
                new EmptyBorder(12, 20, 12, 20)
        );

        welcomeBanner.add(welcomeTextPanel, BorderLayout.CENTER);
        welcomeBanner.add(bannerMessage, BorderLayout.EAST);

        content.add(welcomeBanner);


        // -----------------------------------------------------
        // STAT CARDS
        // -----------------------------------------------------

        content.add(
                Box.createVerticalStrut(26)
        );

        JPanel stats =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                17,
                                0
                        )
                );

        stats.setOpaque(false);

        stats.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        118
                )
        );

        stats.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        stats.add(
                createStatCard(
        "SUBJECTS",
        String.valueOf(getSubjectCount()),
        BLUE
)
        );

        stats.add(
               createStatCard(
        "UPCOMING EXAMS",
        String.valueOf(getUpcomingExamCount()),
        PURPLE
)
        );

        stats.add(
              createStatCard(
        "PROGRESS",
        String.format("%.0f%%", getPreparationProgress()),
        GREEN
)
        );

        stats.add(
               
createStatCard(
        "STUDY SESSIONS",
        String.valueOf(getStudySessionCount()),
        ORANGE
)

        );

        content.add(stats);

        
        // -----------------------------------------------------
        // QUICK ACTIONS
        // -----------------------------------------------------

        content.add(Box.createVerticalStrut(30));

        JLabel quick = new JLabel("Quick Actions");
        quick.setFont(SECTION_FONT);
        quick.setForeground(DARK_TEXT);
        quick.setAlignmentX(Component.LEFT_ALIGNMENT);

        content.add(quick);
        content.add(Box.createVerticalStrut(17));

        JPanel actions = new JPanel(
                new GridLayout(2, 3, 17, 12)
        );

        actions.setOpaque(false);
        actions.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 210)
        );
        actions.setAlignmentX(Component.LEFT_ALIGNMENT);

        actions.add(createActionButton("CREATE STUDY PLAN"));
        actions.add(createActionButton("START STUDY SESSION"));
        actions.add(createActionButton("VIEW EXAMS"));
        actions.add(createActionButton("PRACTICE MCQs"));
        actions.add(createActionButton("QUIZ HISTORY"));

        content.add(actions);


        // -----------------------------------------------------
        // TODAY
        // -----------------------------------------------------

        content.add(
                Box.createVerticalStrut(30)
        );

        JLabel todayTitle =
                new JLabel(
                        "Today's Study Overview"
                );

        todayTitle.setFont(SECTION_FONT);

        todayTitle.setForeground(DARK_TEXT);

        todayTitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        content.add(todayTitle);

        content.add(
                Box.createVerticalStrut(17)
        );

        JPanel todayCard =
                createWhiteCard();

                
todayCard.setBackground(WHITE);
todayCard.setOpaque(true);
todayCard.setForeground(DARK_TEXT);
todayCard.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(BORDER, 1, true),
        new EmptyBorder(20, 22, 20, 22)
));

todayCard.setBorder(BorderFactory.createCompoundBorder(
        new LineBorder(new Color(210, 222, 238), 1, true),
        new EmptyBorder(20, 22, 20, 22)
));
todayCard.setMaximumSize(
        new Dimension(Integer.MAX_VALUE, 120)
);

        
if (loggedInStudent == null) {
    JLabel noStudy = new JLabel(
            "Please log in to view today's study sessions."
    );
    noStudy.setFont(NORMAL_FONT);
    noStudy.setForeground(SECONDARY_TEXT);
    todayCard.add(noStudy, BorderLayout.CENTER);
} else {
    try {
        com.smartstudy.app.service.StudySessionService sessionService =
                new com.smartstudy.app.service.StudySessionService();

        java.util.List<com.smartstudy.app.model.StudySession> todaySessions =
                sessionService.getTodayStudySessionsByStudentId(
                        loggedInStudent.getStudentId()
                );

        if (todaySessions.isEmpty()) {
            JLabel noStudy = new JLabel(
                    "No study sessions planned for today."
            );
            noStudy.setFont(NORMAL_FONT);
            noStudy.setForeground(SECONDARY_TEXT);
            todayCard.add(noStudy, BorderLayout.CENTER);
        } else {
            JPanel sessionList = new JPanel();
            sessionList.setOpaque(false);
            sessionList.setLayout(
                    new BoxLayout(sessionList, BoxLayout.Y_AXIS)
            );

            for (com.smartstudy.app.model.StudySession session : todaySessions) {
                
String topic = session.getTopicCovered();

if (topic == null || topic.trim().isEmpty()) {
    topic = "Study Session";
}

String subjectName = "Subject ID: " + session.getSubjectId();

try {
    com.smartstudy.app.service.SubjectService subjectService =
            new com.smartstudy.app.service.SubjectService();

    for (com.smartstudy.app.model.Subject subject :
            subjectService.getSubjectsByStudentId(
                    loggedInStudent.getStudentId())) {

        if (subject.getSubjectId() == session.getSubjectId()) {
            subjectName = subject.getSubjectName();
            break;
        }
    }
} catch (java.sql.SQLException e) {
    e.printStackTrace();
}


java.time.LocalDate sessionDate =
        java.time.LocalDate.parse(session.getSessionDate());

String formattedDate =
        sessionDate.format(
                java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy")
        );

String status = sessionDate.equals(java.time.LocalDate.now())
        ? "TODAY"
        : "UPCOMING";

JLabel sessionLabel = new JLabel(
        status + "  |  "
        + topic + "  |  "
        + subjectName + "  |  "
        + formattedDate + "  |  "
        + session.getDurationMinutes() + " minutes"
);


                sessionLabel.setFont(NORMAL_FONT);
                sessionLabel.setForeground(DARK_TEXT);
                sessionList.add(sessionLabel);
                sessionList.add(Box.createVerticalStrut(8));
            }

            todayCard.add(sessionList, BorderLayout.CENTER);
        }

    } catch (java.sql.SQLException e) {
        e.printStackTrace();

        JLabel errorLabel = new JLabel(
                "Unable to load today's study sessions."
        );
        errorLabel.setFont(NORMAL_FONT);
        errorLabel.setForeground(RED);
        todayCard.add(errorLabel, BorderLayout.CENTER);
    }
}

        content.add(todayCard);

        // -----------------------------------------------------
        // ACADEMIC OVERVIEW
        // -----------------------------------------------------

        content.add(
                Box.createVerticalStrut(30)
        );

        JLabel academic =
                new JLabel(
                        "Academic Overview"
                );

        academic.setFont(SECTION_FONT);

        academic.setForeground(DARK_TEXT);

        academic.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        content.add(academic);

        content.add(
                Box.createVerticalStrut(17)
        );

        JPanel academicCards =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                17,
                                0
                        )
                );

        academicCards.setOpaque(false);

        academicCards.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        105
                )
        );

        academicCards.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        academicCards.add(
                createAcademicCard(
                        "CURRENT SEMESTER",
                        getStudentSemester()
                )
        );

       
academicCards.add(
        createAcademicCard(
                "NEXT EXAM",
                getNextExamDetails()
        )
);


        academicCards.add(
                createAcademicCard(
        "AVERAGE QUIZ SCORE",
        String.format("%.0f%%", getAverageQuizScore())
)
        );

        content.add(academicCards);


        // -----------------------------------------------------
// SUBJECT-WISE QUIZ PERFORMANCE
// -----------------------------------------------------

content.add(Box.createVerticalStrut(30));

JPanel subjectPerformanceSection =
        createSubjectPerformanceSection();

subjectPerformanceSection.setAlignmentX(
        Component.LEFT_ALIGNMENT
);

content.add(subjectPerformanceSection);

        JScrollPane scrollPane =
                new JScrollPane(content);

                scrollPane.getVerticalScrollBar().setUnitIncrement(20);

        scrollPane.setBorder(null);

        main.add(
                scrollPane,
                BorderLayout.CENTER
        );

        return main;
    }

    
    // =========================================================
    // STAT CARD — DARK TEAL THEME
    // =========================================================

    private JPanel createStatCard(
            String title,
            String number,
            Color color
    ) {
        JPanel card = new JPanel();

        // Consistent dark teal background for every card
        card.setBackground(new Color(11, 37, 40));

        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(35, 85, 90), 1, true),
                new EmptyBorder(18, 22, 18, 20)
        ));

        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setAlignmentY(Component.TOP_ALIGNMENT);
        card.setOpaque(true);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        titleLabel.setForeground(new Color(155, 184, 190));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel numberLabel = new JLabel(number);
        numberLabel.setFont(STAT_NUMBER_FONT);
        numberLabel.setForeground(new Color(240, 248, 250));
        numberLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(titleLabel);
        card.add(Box.createVerticalStrut(12));
        card.add(numberLabel);

        return card;
    }

    
    // =========================================================
    // ACTION BUTTON — DARK TEAL THEME
    // =========================================================

    private JButton createActionButton(String text) {

        JButton button = new JButton(text);

       
Color normalColor = new Color(11, 37, 40);
Color hoverColor = new Color(16, 55, 60);
Color cyanBorder = new Color(45, 100, 105);
Color cyanGlow = new Color(121, 210, 232);


        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(new Color(240, 248, 250));
        button.setBackground(normalColor);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);

button.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(cyanBorder, 1, true),
        new EmptyBorder(16, 12, 16, 12)
));


        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(220, 65));
button.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
        button.setBackground(hoverColor);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(cyanGlow, 1, true),
                new EmptyBorder(16, 12, 16, 12)
        ));
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {
        button.setBackground(normalColor);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(cyanBorder, 1, true),
                new EmptyBorder(16, 12, 16, 12)
        ));
    }
});

        if (text.equals("CREATE STUDY PLAN")) {
            button.addActionListener(e -> {
                StudyPlanForm form = new StudyPlanForm(loggedInStudent);
                form.setLocationRelativeTo(Dashboard.this);
                form.setVisible(true);
            });
        }

        if (text.equals("PRACTICE MCQs")) {
            button.addActionListener(e -> {
                StudyMaterialsListForm form =
                        new StudyMaterialsListForm(loggedInStudent);
                form.setLocationRelativeTo(Dashboard.this);
                form.setVisible(true);
            });
        }

        if (text.equals("QUIZ HISTORY")) {
            button.addActionListener(e -> {
                QuizHistoryForm form = new QuizHistoryForm(loggedInStudent);
                form.setLocationRelativeTo(Dashboard.this);
                form.setVisible(true);
            });
        }

        if (text.equals("VIEW EXAMS")) {
            button.addActionListener(e -> {
                ExamsListForm form = new ExamsListForm(loggedInStudent);
                form.setLocationRelativeTo(Dashboard.this);
                form.setVisible(true);
            });
        }

        if (text.equals("START STUDY SESSION")) {
            button.addActionListener(e -> {
                StudySessionForm form = new StudySessionForm(loggedInStudent);
                form.setLocationRelativeTo(Dashboard.this);
                form.setVisible(true);
            });
        }

        return button;
    }




    // =========================================================
    // WHITE CARD
    // =========================================================

    private JPanel createWhiteCard() {

        JPanel card =
                new JPanel(
                        new BorderLayout()
                );

        card.setBackground(WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(
                                BORDER,
                                1
                        ),
                        new EmptyBorder(
                                25,
                                27,
                                25,
                                27
                        )
                )
        );

        card.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        return card;
    }

    // =========================================================
    // ACADEMIC CARD — DARK TEAL THEME
    // =========================================================

    private JPanel createAcademicCard(
            String title,
            String value
    ) {

        JPanel card = createWhiteCard();

        card.setBackground(new Color(11, 37, 40));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(45, 100, 105), 1, true
                ),
                new EmptyBorder(20, 22, 20, 22)
        ));

        JPanel inside = new JPanel();
        inside.setOpaque(false);
        inside.setLayout(
                new BoxLayout(inside, BoxLayout.Y_AXIS)
        );

        JPanel accent = new JPanel();
        accent.setBackground(new Color(121, 210, 232));
        accent.setPreferredSize(new Dimension(42, 3));
        accent.setMaximumSize(new Dimension(42, 3));
        accent.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        titleLabel.setForeground(new Color(155, 184, 190));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 21));
        valueLabel.setForeground(new Color(240, 248, 250));
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        inside.add(accent);
        inside.add(Box.createVerticalStrut(12));
        inside.add(titleLabel);
        inside.add(Box.createVerticalStrut(7));
        inside.add(valueLabel);

        card.add(inside, BorderLayout.CENTER);

        return card;
    }


    // =========================================================
    // LOGOUT
    // =========================================================

    private void logout() {

        UIManager.put("OptionPane.messageForeground", Color.BLACK);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to logout?",
                        "Logout",
                        JOptionPane.YES_NO_OPTION
                );

        if (result == JOptionPane.YES_OPTION) {

            dispose();

            SwingUtilities.invokeLater(
                    () -> new LoginForm()
            );
        }
    }


    // =========================================================
    // MAIN
    // =========================================================

    // =========================================================
// MAIN
// =========================================================

public static void main(String[] args) {

    try {
        UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName()
        );
    } catch (Exception ignored) {
    }

    SwingUtilities.invokeLater(() -> {
        new LoginForm().setVisible(true);
    });
}
    // =========================================================
// SUBJECT-WISE QUIZ PERFORMANCE
// =========================================================

private JPanel createSubjectPerformanceSection() {

    JPanel section = new JPanel();

    section.setOpaque(false);

    section.setLayout(
            new BoxLayout(section, BoxLayout.Y_AXIS)
    );

    JLabel heading = new JLabel(
            "Subject-wise Quiz Performance"
    );

    heading.setFont(SECTION_FONT);
    heading.setForeground(DARK_TEXT);
    heading.setAlignmentX(Component.LEFT_ALIGNMENT);

    section.add(heading);
    section.add(Box.createVerticalStrut(17));

    JPanel performanceCard = createWhiteCard();

    performanceCard.setLayout(
            new BorderLayout(0, 12)
    );

    performanceCard.setBorder(
            new EmptyBorder(18, 20, 18, 20)
    );

    performanceCard.setAlignmentX(
            Component.LEFT_ALIGNMENT
    );

    if (loggedInStudent == null) {

        JLabel message = new JLabel(
                "Please log in to view quiz performance."
        );

        message.setFont(NORMAL_FONT);
        message.setForeground(SECONDARY_TEXT);

        performanceCard.add(
                message,
                BorderLayout.CENTER
        );

    } else {

        String sql = """
                SELECT s.SUBJECT_NAME,
                       COUNT(qr.RESULT_ID) AS TOTAL_QUIZZES,
                       NVL(AVG(qr.SCORE_PERCENTAGE), 0)
                           AS AVERAGE_SCORE
                FROM SUBJECTS s
                LEFT JOIN QUIZ_RESULTS qr
                    ON s.SUBJECT_ID = qr.SUBJECT_ID
                    AND qr.STUDENT_ID = ?
                WHERE s.STUDENT_ID = ?
                GROUP BY s.SUBJECT_ID, s.SUBJECT_NAME
                ORDER BY s.SUBJECT_NAME
                """;

        JPanel rowsPanel = new JPanel();

        rowsPanel.setOpaque(false);

        rowsPanel.setLayout(
                new BoxLayout(
                        rowsPanel,
                        BoxLayout.Y_AXIS
                )
        );

        boolean hasSubjects = false;

        try (
                Connection connection =
                        com.smartstudy.app.config.DatabaseConfig
                                .getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    loggedInStudent.getStudentId()
            );

            statement.setInt(
                    2,
                    loggedInStudent.getStudentId()
            );

            try (
                    java.sql.ResultSet rs =
                            statement.executeQuery()
            ) {

                while (rs.next()) {

                    hasSubjects = true;

                    String subjectName =
                            rs.getString("SUBJECT_NAME");

                    int totalQuizzes =
                            rs.getInt("TOTAL_QUIZZES");

                    double averageScore =
                            rs.getDouble("AVERAGE_SCORE");

                    JPanel subjectRow =
                            new JPanel(
                                    new BorderLayout(12, 8)
                            );

                    subjectRow.setOpaque(false);

                    subjectRow.setMaximumSize(
                            new Dimension(
                                    Integer.MAX_VALUE,
                                    68
                            )
                    );

                    subjectRow.setAlignmentX(
                            Component.LEFT_ALIGNMENT
                    );

                    JPanel detailsPanel =
                            new JPanel(
                                    new BorderLayout(0, 5)
                            );

                    detailsPanel.setOpaque(false);

                    JLabel subjectLabel =
                            new JLabel(subjectName);

                    subjectLabel.setFont(
                            new Font(
                                    "Segoe UI",
                                    Font.BOLD,
                                    14
                            )
                    );

                    subjectLabel.setForeground(DARK_TEXT);

                    String recommendation;

if (totalQuizzes == 0) {
    recommendation = "Start your first quiz";
} else if (averageScore < 40) {
    recommendation = "Weak subject - Practice more";
} else if (averageScore < 75) {
    recommendation = "Needs improvement";
} else {
    recommendation = "Good performance";
}

String quizText = totalQuizzes + " quiz(es) completed"
        + "  |  " + recommendation;
                    JLabel quizLabel =
                            new JLabel(quizText);

                    quizLabel.setFont(SMALL_FONT);
                    quizLabel.setForeground(SECONDARY_TEXT);

                    detailsPanel.add(
                            subjectLabel,
                            BorderLayout.NORTH
                    );

                    detailsPanel.add(
                            quizLabel,
                            BorderLayout.SOUTH
                    );

                   

JProgressBar progressBar = new JProgressBar(0, 100);

int displayedScore = (int) Math.round(
        Math.max(0, Math.min(100, averageScore))
);

Color barBackground = new Color(7, 27, 30);
Color barBorder = new Color(45, 100, 105);
Color barText = new Color(240, 248, 250);

progressBar.setValue(displayedScore);
progressBar.setStringPainted(true);
progressBar.setFont(new Font("Segoe UI", Font.BOLD, 12));
progressBar.setPreferredSize(new Dimension(172, 38));
progressBar.setMinimumSize(new Dimension(172, 38));
progressBar.setMaximumSize(new Dimension(172, 38));
progressBar.setOpaque(false);

if (totalQuizzes == 0) {
    progressBar.setValue(0);
    progressBar.setString("NO QUIZZES YET");
    progressBar.setForeground(new Color(100, 120, 130));
} else {
    progressBar.setString(
            String.format("%.1f%%", averageScore)
    );

    if (averageScore < 50) {
        progressBar.setForeground(new Color(225, 90, 100));
    } else if (averageScore < 80) {
        progressBar.setForeground(new Color(235, 180, 70));
    } else {
        progressBar.setForeground(new Color(65, 190, 130));
    }
}

progressBar.setBorder(
        BorderFactory.createLineBorder(barBorder, 1, true)
);

progressBar.setUI(new javax.swing.plaf.basic.BasicProgressBarUI() {
    @Override
    protected void paintDeterminate(
            Graphics g,
            javax.swing.JComponent c
    ) {
        Graphics2D g2 = (Graphics2D) g.create();

        int width = progressBar.getWidth();
        int height = progressBar.getHeight();

        g2.setColor(barBackground);
        g2.fillRoundRect(0, 0, width, height, 8, 8);

        Insets insets = progressBar.getInsets();

        int innerWidth = width - insets.left - insets.right;
        int innerHeight = height - insets.top - insets.bottom;

        int fillWidth = (int) (
                innerWidth * progressBar.getPercentComplete()
        );

        if (fillWidth > 0 && totalQuizzes > 0) {
            g2.setColor(progressBar.getForeground());
            g2.fillRoundRect(
                    insets.left,
                    insets.top,
                    fillWidth,
                    innerHeight,
                    8,
                    8
            );
        }

        g2.setColor(barBorder);
        g2.drawRoundRect(
                0, 0, width - 1, height - 1, 8, 8
        );

        String text = progressBar.getString();

        g2.setFont(progressBar.getFont());
        g2.setColor(barText);

        java.awt.FontMetrics fm = g2.getFontMetrics();

        int x = (width - fm.stringWidth(text)) / 2;
        int y = (height - fm.getHeight()) / 2 + fm.getAscent();

        g2.drawString(text, x, y);

        g2.dispose();
    }
});


                    subjectRow.add(
                            detailsPanel,
                            BorderLayout.CENTER
                    );

                    subjectRow.add(
                            progressBar,
                            BorderLayout.EAST
                    );

                    rowsPanel.add(subjectRow);

                    rowsPanel.add(
                            Box.createVerticalStrut(14)
                    );
                }
            }

        } catch (java.sql.SQLException e) {

            e.printStackTrace();

            JLabel errorLabel = new JLabel(
                    "Unable to load subject-wise quiz performance."
            );

            errorLabel.setFont(NORMAL_FONT);
            errorLabel.setForeground(RED);

            rowsPanel.add(errorLabel);
            hasSubjects = true;
        }

        if (!hasSubjects) {

            JLabel emptyLabel = new JLabel(
                    "No subjects found. Add subjects to get started."
            );

            emptyLabel.setFont(NORMAL_FONT);
            emptyLabel.setForeground(SECONDARY_TEXT);

            rowsPanel.add(emptyLabel);
        }

        performanceCard.add(
                rowsPanel,
                BorderLayout.CENTER
        );
    }

    performanceCard.setMaximumSize(
            new Dimension(
                    Integer.MAX_VALUE,
                    Integer.MAX_VALUE
            )
    );

    section.add(performanceCard);

    return section;
}
}