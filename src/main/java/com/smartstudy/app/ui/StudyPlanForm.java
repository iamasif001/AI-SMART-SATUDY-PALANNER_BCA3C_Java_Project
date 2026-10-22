package com.smartstudy.app.ui;

import com.smartstudy.app.model.Student;
import com.smartstudy.app.model.Subject;
import com.smartstudy.app.service.StudyPlanService;
import com.smartstudy.app.service.SubjectService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StudyPlanForm extends JFrame {

    // =========================================================
    // DATA
    // =========================================================

    private final Student loggedInStudent;

    private SubjectService subjectService;
    private StudyPlanService studyPlanService;

    // =========================================================
    // UI COMPONENTS
    // =========================================================

    private JComboBox<SubjectItem> subjectComboBox;

    private JTextField dateField;

    private JSpinner hoursSpinner;

    private JTextArea taskArea;

    private JComboBox<String> statusComboBox;

    private DefaultListModel<String> planListModel;

    private JList<String> planList;
    private JButton addButton;
private int editingPlanId = -1;
private JButton cancelEditButton;

    // =========================================================
    // COLORS
    // =========================================================

   
private static final Color BACKGROUND =
        new Color(7, 20, 22);

private static final Color CARD =
        new Color(11, 37, 40);

private static final Color FIELD =
        new Color(7, 27, 30);

private static final Color FIELD_HOVER =
        new Color(16, 54, 58);

private static final Color BORDER =
        new Color(45, 100, 105);

private static final Color WHITE =
        new Color(240, 248, 250);

private static final Color MUTED =
        new Color(155, 184, 190);

private static final Color ACCENT =
        new Color(121, 210, 232);

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public StudyPlanForm(Student student) {

        loggedInStudent = student;

        try {

            subjectService =
                    new SubjectService();

            studyPlanService =
                    new StudyPlanService();

        } catch (Exception e) {

            e.printStackTrace();

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

        loadStudyPlans();
    }

    // =========================================================
    // INITIALIZE FRAME
    // =========================================================

    private void initializeFrame() {

        setTitle(
                "AI Smart Study Planner - Study Plan"
        );

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setSize(
                1200,
                750
        );

        setMinimumSize(
                new Dimension(
                        1000,
                        650
                )
        );

        setLocationRelativeTo(null);

        setResizable(true);
    }

        // =========================================================
    // BUILD UI
    // =========================================================

    private void buildUI() {

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(
                                20,
                                20
                        )
                );

        mainPanel.setBackground(
                BACKGROUND
        );

        mainPanel.setBorder(
                new EmptyBorder(
                        25,
                        25,
                        25,
                        25
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

        JLabel title =
                new JLabel(
                        "Study Plan"
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        28
                )
        );

        title.setForeground(
                WHITE
        );

        JLabel subtitle =
                new JLabel(
                        "Create and manage your study schedule"
                );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        subtitle.setForeground(
                MUTED
        );

        JPanel titlePanel =
                new JPanel();

        titlePanel.setOpaque(false);

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
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

        mainPanel.add(
                header,
                BorderLayout.NORTH
        );

        // =====================================================
        // CENTER
        // =====================================================

        JPanel centerPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                20,
                                0
                        )
                );

        centerPanel.setOpaque(false);

        centerPanel.add(
                createFormPanel()
        );

        centerPanel.add(
                createPlanListPanel()
        );

        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );

        setContentPane(mainPanel);
    }

    // =========================================================
    // FORM PANEL
    // =========================================================

    private JPanel createFormPanel() {

        JPanel card =
                createCard();

        card.setLayout(
                new GridBagLayout()
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.gridx = 0;
        gbc.fill =
                GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.insets =
                new Insets(
                        7,
                        10,
                        7,
                        10
                );

        int row = 0;

        // =====================================================
        // SUBJECT
        // =====================================================

        gbc.gridy = row++;

        card.add(
                createLabel("Subject"),
                gbc
        );

        subjectComboBox =
                new JComboBox<>();

        styleComboBox(
                subjectComboBox
        );

        gbc.gridy = row++;

        card.add(
                subjectComboBox,
                gbc
        );

        // =====================================================
        // DATE
        // =====================================================

        gbc.gridy = row++;

        card.add(
                createLabel(
                        "Study Date (DD-MM-YYYY)"
                ),
                gbc
        );

        dateField =
                new JTextField();
dateField.setText(
        java.time.LocalDate.now().format(
                java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy")
        )
);

        styleTextField(
                dateField
        );

        gbc.gridy = row++;

        card.add(
                dateField,
                gbc
        );

        // =====================================================
        // STUDY HOURS
        // =====================================================

        gbc.gridy = row++;

        card.add(
                createLabel(
                        "Study Hours"
                ),
                gbc
        );

        hoursSpinner =
                new JSpinner(
                        new SpinnerNumberModel(
                                1.0,
                                0.5,
                                24.0,
                                0.5
                        )
                );

        styleSpinner(
                hoursSpinner
        );

        gbc.gridy = row++;

        card.add(
                hoursSpinner,
                gbc
        );

        // =====================================================
        // TASK
        // =====================================================

        gbc.gridy = row++;

        card.add(
                createLabel(
                        "Task"
                ),
                gbc
        );

        taskArea =
                new JTextArea(
                        5,
                        20
                );

        taskArea.setLineWrap(true);

        taskArea.setWrapStyleWord(true);

        taskArea.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        taskArea.setForeground(
                WHITE
        );

        taskArea.setBackground(
                FIELD
        );

        taskArea.setCaretColor(
                WHITE
        );

        taskArea.setBorder(
                new EmptyBorder(
                        8,
                        8,
                        8,
                        8
                )
        );

        JScrollPane taskScroll =
                new JScrollPane(
                        taskArea
                );

        taskScroll.setBorder(
                new LineBorder(
                        BORDER,
                        1
                )
        );

        gbc.gridy = row++;

        gbc.fill =
                GridBagConstraints.BOTH;

        gbc.weighty = 1.0;

        card.add(
                taskScroll,
                gbc
        );

        gbc.weighty = 0;

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

                        // =====================================================
        // STATUS
        // =====================================================

        gbc.gridy = row++;

        card.add(
                createLabel(
                        "Status"
                ),
                gbc
        );

        statusComboBox =
                new JComboBox<>(
                        
new String[]{
    "PENDING",
    "IN_PROGRESS",
    "COMPLETED",
    "SKIPPED"
}

                );

        styleComboBox(
                statusComboBox
        );

        gbc.gridy = row++;

        card.add(
                statusComboBox,
                gbc
        );

        // =====================================================
        // ADD BUTTON
        // =====================================================

       addButton = new JButton("Add Study Plan");
       cancelEditButton = new JButton("Cancel Edit");

styleButton(cancelEditButton);

cancelEditButton.setVisible(false);

cancelEditButton.addActionListener(e -> resetPlanForm());

        styleButton(
                addButton
        );

        addButton.addActionListener(
                e -> addStudyPlan()
        );

        gbc.gridy = row++;

        gbc.insets =
                new Insets(
                        15,
                        10,
                        10,
                        10
                );

       JPanel formButtonPanel = new JPanel(
        new GridLayout(1, 2, 10, 0)
);

formButtonPanel.setOpaque(false);

formButtonPanel.add(addButton);
formButtonPanel.add(cancelEditButton);

gbc.gridy = row++;

gbc.insets = new Insets(15, 10, 10, 10);

card.add(formButtonPanel, gbc);

        return card;
    }

    // =========================================================
    // PLAN LIST PANEL
    // =========================================================

    private JPanel createPlanListPanel() {

        JPanel card =
                createCard();

        card.setLayout(
                new BorderLayout(
                        10,
                        10
                )
        );

        JLabel title =
                new JLabel(
                        "My Study Plans"
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );

        title.setForeground(
                WHITE
        );

        card.add(
                title,
                BorderLayout.NORTH
        );

        planListModel =
                new DefaultListModel<>();

        planList =
                new JList<>(
                        planListModel
                );

        planList.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        planList.setForeground(
                WHITE
        );

        planList.setBackground(
                FIELD
        );

        planList.setSelectionBackground(
                FIELD_HOVER
        );

        planList.setSelectionForeground(
                WHITE
        );

        planList.setBorder(
                new EmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );

        

planList.setFixedCellHeight(100);


planList.setFixedCellHeight(112);

planList.setCellRenderer(new DefaultListCellRenderer() {
    @Override
    public Component getListCellRendererComponent(
            JList<?> list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus
    ) {
        JLabel label = (JLabel) super.getListCellRendererComponent(
                list, value, index, isSelected, cellHasFocus
        );

        String formattedText = String.valueOf(value)
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\n", "<br>");

        // Make the plan title bold
        int lineEnd = formattedText.indexOf("<br>");
        if (lineEnd > 0) {
            formattedText = "<b>"
                    + formattedText.substring(0, lineEnd)
                    + "</b>"
                    + formattedText.substring(lineEnd);
        }

        label.setText(
                "<html><div style='width:420px; line-height:1.4;'>"
                + formattedText
                + "</div></html>"
        );

        label.setOpaque(true);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(WHITE);
        label.setBackground(isSelected ? FIELD_HOVER : FIELD);

        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER),
                new EmptyBorder(14, 14, 14, 14)
        ));

        return label;
    }
});



        JScrollPane scrollPane =
                new JScrollPane(
                        planList
                );

        scrollPane.setBorder(
                new LineBorder(
                        BORDER,
                        1
                )
        );

        card.add(scrollPane, BorderLayout.CENTER);

JPanel actionPanel = new JPanel(
        new GridLayout(2, 2, 8, 8)
);
actionPanel.setOpaque(false);

JButton editButton = new JButton("Edit Plan");
JButton deleteButton = new JButton("Delete Plan");
JButton statusButton = new JButton("Update Status");
JButton refreshButton = new JButton("Refresh");

styleButton(editButton);
styleButton(deleteButton);
styleButton(statusButton);
styleButton(refreshButton);

editButton.addActionListener(e -> editStudyPlan());
deleteButton.addActionListener(e -> deleteStudyPlan());
statusButton.addActionListener(e -> updatePlanStatus());
refreshButton.addActionListener(e -> loadStudyPlans());

actionPanel.add(editButton);
actionPanel.add(deleteButton);
actionPanel.add(statusButton);
actionPanel.add(refreshButton);

card.add(actionPanel, BorderLayout.SOUTH);

return card;
    }

        // =========================================================
    // LOAD SUBJECTS
    // =========================================================

    private void loadSubjects() {

        if (subjectService == null) {
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
                                subject
                        )
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load subjects.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // ADD STUDY PLAN
    // =========================================================

    
private void addStudyPlan() {

    if (subjectComboBox.getSelectedItem() == null) {
        JOptionPane.showMessageDialog(
                this,
                "Please select a subject.",
                "Validation",
                JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    String studyDate = dateField.getText().trim();
    String task = taskArea.getText().trim();

    if (studyDate.isEmpty()) {
        JOptionPane.showMessageDialog(
                this,
                "Please enter study date.",
                "Validation",
                JOptionPane.WARNING_MESSAGE
        );
        dateField.requestFocus();
        return;
    }

    if (task.isEmpty()) {
        JOptionPane.showMessageDialog(
                this,
                "Please enter a study task.",
                "Validation",
                JOptionPane.WARNING_MESSAGE
        );
        taskArea.requestFocus();
        return;
    }

    java.time.LocalDate parsedDate;

    try {
        java.time.format.DateTimeFormatter formatter =
                java.time.format.DateTimeFormatter.ofPattern(
                        "dd-MM-uuuu"
                ).withResolverStyle(
                        java.time.format.ResolverStyle.STRICT
                );

        parsedDate = java.time.LocalDate.parse(
                studyDate,
                formatter
        );

    } catch (java.time.format.DateTimeParseException ex) {
        JOptionPane.showMessageDialog(
                this,
                "Enter a valid date in DD-MM-YYYY format.\n"
                        + "Example: 10-10-2026",
                "Invalid Date",
                JOptionPane.WARNING_MESSAGE
        );
        dateField.requestFocus();
        return;
    }

    SubjectItem selectedItem =
            (SubjectItem) subjectComboBox.getSelectedItem();

    Subject selectedSubject = selectedItem.getSubject();

    double studyHours =
            ((Number) hoursSpinner.getValue()).doubleValue();

    String status = (String) statusComboBox.getSelectedItem();

    com.smartstudy.app.model.StudyPlan plan =
            new com.smartstudy.app.model.StudyPlan(
                    loggedInStudent.getStudentId(),
                    selectedSubject.getSubjectId(),
                    parsedDate.toString(),
                    studyHours,
                    task,
                    status
            );

    try {
        boolean saved;

        if (editingPlanId != -1) {

            plan.setPlanId(editingPlanId);

            saved = studyPlanService.updateStudyPlan(plan);

            if (saved) {
                JOptionPane.showMessageDialog(
                        this,
                        "Study plan updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

        } else {

            saved = studyPlanService.addStudyPlan(plan);

            if (saved) {
                JOptionPane.showMessageDialog(
                        this,
                        "Study plan added successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        }

        if (saved) {
            resetPlanForm();
            loadStudyPlans();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "No changes were saved. Please try again.",
                    "Information",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }

    } catch (Exception e) {
        e.printStackTrace();

        JOptionPane.showMessageDialog(
                this,
                "Unable to save study plan.\n\n"
                        + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
        // =========================================================
    // LOAD STUDY PLANS
    // =========================================================

    
private void loadStudyPlans() {

    if (planListModel == null || studyPlanService == null) {
        return;
    }

    planListModel.clear();

    try {
        java.util.List<com.smartstudy.app.model.StudyPlan> plans =
                studyPlanService.getStudyPlanDAO()
                        .getStudyPlansByStudentId(
                                loggedInStudent.getStudentId()
                        );

        java.util.List<Subject> subjects =
                subjectService.getSubjectsByStudentId(
                        loggedInStudent.getStudentId()
                );

        for (com.smartstudy.app.model.StudyPlan plan : plans) {

            String subjectName = "Unknown Subject";

            for (Subject subject : subjects) {
                if (subject.getSubjectId() == plan.getSubjectId()) {
                    subjectName = subject.getSubjectName();
                    break;
                }
            }

          
String planText =
        "PLAN #" + plan.getPlanId()
        + "\nSubject: " + subjectName
        + "\nDate: " + plan.getStudyDate()
        + "  |  Hours: " + plan.getStudyHours() + " hrs"
        + "\nStatus: " + plan.getStatus()
        + "  |  Task: " + plan.getTask();

planListModel.addElement(planText);

        }

    } catch (Exception e) {
        e.printStackTrace();

        JOptionPane.showMessageDialog(
                this,
                "Unable to load study plans.\n\n" + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}

    // =========================================================
    // CREATE CARD
    // =========================================================


// =========================================================
// EDIT STUDY PLAN
// =========================================================

private void editStudyPlan() {

    int index = planList.getSelectedIndex();

    if (index == -1) {
        JOptionPane.showMessageDialog(
                this,
                "Please select a study plan to edit.",
                "Select Plan",
                JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    try {
        List<com.smartstudy.app.model.StudyPlan> plans =
                studyPlanService.getStudyPlansByStudentId(
                        loggedInStudent.getStudentId()
                );

        if (index >= plans.size()) {
            loadStudyPlans();
            return;
        }

        com.smartstudy.app.model.StudyPlan plan = plans.get(index);

        editingPlanId = plan.getPlanId();

        for (int i = 0; i < subjectComboBox.getItemCount(); i++) {
            SubjectItem item = subjectComboBox.getItemAt(i);

            if (item.getSubject().getSubjectId() == plan.getSubjectId()) {
                subjectComboBox.setSelectedIndex(i);
                break;
            }
        }

        java.time.LocalDate parsedDate =
                java.time.LocalDate.parse(plan.getStudyDate());

        dateField.setText(
                parsedDate.format(
                        java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy")
                )
        );

        hoursSpinner.setValue(plan.getStudyHours());
        taskArea.setText(plan.getTask());
        statusComboBox.setSelectedItem(plan.getStatus());

        addButton.setText("Update Study Plan");
        if (cancelEditButton != null) {
    cancelEditButton.setVisible(true);
}

        JOptionPane.showMessageDialog(
                this,
                "Plan loaded. Edit the details and click Update Study Plan.",
                "Edit Study Plan",
                JOptionPane.INFORMATION_MESSAGE
        );

    } catch (Exception e) {
        JOptionPane.showMessageDialog(
                this,
                "Unable to load plan for editing.\n\n" + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}


// =========================================================
// DELETE STUDY PLAN
// =========================================================

private void deleteStudyPlan() {

    int index = planList.getSelectedIndex();

    if (index == -1) {
        JOptionPane.showMessageDialog(
                this,
                "Please select a study plan to delete.",
                "Select Plan",
                JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete the selected study plan?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
    );

    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }

    try {
        List<com.smartstudy.app.model.StudyPlan> plans =
                studyPlanService.getStudyPlansByStudentId(
                        loggedInStudent.getStudentId()
                );

        if (index >= plans.size()) {
            loadStudyPlans();
            return;
        }

        int planId = plans.get(index).getPlanId();

        boolean deleted = studyPlanService.deleteStudyPlan(
                planId,
                loggedInStudent.getStudentId()
        );

        if (deleted) {
            JOptionPane.showMessageDialog(
                    this,
                    "Study plan deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            resetPlanForm();
            loadStudyPlans();
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(
                this,
                "Unable to delete study plan.\n\n" + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}


// =========================================================
// UPDATE STUDY PLAN STATUS
// =========================================================

private void updatePlanStatus() {

    int index = planList.getSelectedIndex();

    if (index == -1) {
        JOptionPane.showMessageDialog(
                this,
                "Please select a study plan.",
                "Select Plan",
                JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    String[] statuses = {
            "PENDING",
            "IN_PROGRESS",
            "COMPLETED",
            "SKIPPED"
    };

    String selectedStatus = (String) JOptionPane.showInputDialog(
            this,
            "Select the new status:",
            "Update Status",
            JOptionPane.QUESTION_MESSAGE,
            null,
            statuses,
            statuses[0]
    );

    if (selectedStatus == null) {
        return;
    }

    try {
        List<com.smartstudy.app.model.StudyPlan> plans =
                studyPlanService.getStudyPlansByStudentId(
                        loggedInStudent.getStudentId()
                );

        if (index >= plans.size()) {
            loadStudyPlans();
            return;
        }

        int planId = plans.get(index).getPlanId();

        boolean updated = studyPlanService.updateStudyPlanStatus(
                planId,
                loggedInStudent.getStudentId(),
                selectedStatus
        );

        if (updated) {
            JOptionPane.showMessageDialog(
                    this,
                    "Study plan status updated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadStudyPlans();
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(
                this,
                "Unable to update status.\n\n" + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}


// =========================================================
// RESET FORM
// =========================================================

private void resetPlanForm() {

    editingPlanId = -1;

    if (addButton != null) {
        addButton.setText("Add Study Plan");
    }

    if (cancelEditButton != null) {
        cancelEditButton.setVisible(false);
    }

    if (subjectComboBox.getItemCount() > 0) {
        subjectComboBox.setSelectedIndex(0);
    }

    dateField.setText(
            java.time.LocalDate.now().format(
                    java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy")
            )
    );

    hoursSpinner.setValue(1.0);
    taskArea.setText("");
    statusComboBox.setSelectedItem("PENDING");
}




    private JPanel createCard() {

        JPanel panel =
                new JPanel();

        panel.setBackground(
                CARD
        );

        panel.setBorder(
                new LineBorder(
                        BORDER,
                        1,
                        true
                )
        );

        return panel;
    }

    // =========================================================
    // CREATE LABEL
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
                        14
                )
        );

        label.setForeground(
                WHITE
        );

        return label;
    }

        // =========================================================
    // STYLE TEXT FIELD
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
                WHITE
        );

        field.setBackground(
                FIELD
        );

        field.setCaretColor(
                WHITE
        );

        field.setBorder(
                new LineBorder(
                        BORDER,
                        1
                )
        );
    }

    // =========================================================
// STYLE COMBO BOX
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
            WHITE
    );

    comboBox.setBackground(
            FIELD
    );

    comboBox.setOpaque(
            true
    );

    comboBox.setBorder(
            new LineBorder(
                    BORDER,
                    1
            )
    );

    
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
    public void paintCurrentValue(
            Graphics g,
            Rectangle bounds,
            boolean hasFocus
    ) {
        ListCellRenderer<Object> renderer =
                (ListCellRenderer<Object>) comboBox.getRenderer();

        Component component = renderer.getListCellRendererComponent(
                listBox,
                comboBox.getSelectedItem(),
                -1,
                false,
                false
        );

        component.setBackground(FIELD);
        component.setForeground(WHITE);

        if (component instanceof JComponent jc) {
            jc.setOpaque(true);
        }

        currentValuePane.paintComponent(
                g,
                component,
                comboBox,
                bounds.x,
                bounds.y,
                bounds.width,
                bounds.height,
                true
        );
    }
});


    comboBox.setRenderer(
            new DefaultListCellRenderer() {

                @Override
                public Component getListCellRendererComponent(
                        JList<?> list,
                        Object value,
                        int index,
                        boolean isSelected,
                        boolean cellHasFocus
                ) {

                    JLabel label =
                            (JLabel)
                                    super
                                            .getListCellRendererComponent(
                                                    list,
                                                    value,
                                                    index,
                                                    isSelected,
                                                    cellHasFocus
                                            );

                    label.setFont(
                            new Font(
                                    "Segoe UI",
                                    Font.PLAIN,
                                    14
                            )
                    );

                    label.setForeground(
                            WHITE
                    );
label.setBackground(
        isSelected
                ? new Color(16, 54, 58)
                : FIELD
);

label.setForeground(
        new Color(240, 248, 250)
);

                    return label;
                }
            }
    );
}
    // =========================================================
    // STYLE SPINNER
    // =========================================================

    private void styleSpinner(
            JSpinner spinner
    ) {

        spinner.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        spinner.setForeground(
                WHITE
        );

        spinner.setBackground(
                FIELD
        );

        JComponent editor =
                spinner.getEditor();

        if (
                editor
                        instanceof
                        JSpinner.DefaultEditor
        ) {

            JTextField textField =
                    (
                            (JSpinner.DefaultEditor)
                                    editor
                    ).getTextField();

            textField.setForeground(
                    WHITE
            );

            textField.setBackground(
                    FIELD
            );

            textField.setCaretColor(
                    WHITE
            );
        }
    }

   // =========================================================
// STYLE BUTTON
// =========================================================


private void styleButton(JButton button) {
    Color normal = new Color(11, 37, 40);
    Color hover = new Color(16, 54, 58);
    Color cyan = new Color(121, 210, 232);
    Color text = new Color(240, 248, 250);

    button.setFont(new Font("Segoe UI", Font.BOLD, 14));
    button.setForeground(text);
    button.setBackground(normal);
    button.setOpaque(true);
    button.setContentAreaFilled(true);
    button.setBorderPainted(true);
    button.setFocusPainted(false);

    button.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(45, 100, 105), 1, true),
            new EmptyBorder(8, 12, 8, 12)
    ));

    button.setUI(new javax.swing.plaf.basic.BasicButtonUI());
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));

    button.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
            button.setBackground(hover);
            button.setBorder(new LineBorder(cyan, 1, true));
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
            button.setBackground(normal);
            button.setBorder(new LineBorder(
                    new Color(45, 100, 105), 1, true
            ));
        }
    });
}

    // =========================================================
    // SUBJECT ITEM
    // =========================================================

    private static class SubjectItem {

        private final Subject subject;

        public SubjectItem(
                Subject subject
        ) {

            this.subject =
                    subject;
        }

        public Subject getSubject() {

            return subject;
        }

        @Override
        public String toString() {

            return subject
                    .getSubjectName();
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
                                UIManager
                                        .getSystemLookAndFeelClassName()
                        );

                    } catch (Exception ignored) {
                    }

                    // This main method is only for testing.
                    // Normally StudyPlanForm is opened
                    // from Dashboard.java.
                }
        );
    }
}