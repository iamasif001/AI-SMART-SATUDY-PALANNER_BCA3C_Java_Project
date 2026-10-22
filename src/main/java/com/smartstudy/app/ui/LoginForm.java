package com.smartstudy.app.ui;

import javax.swing.*;
import java.awt.*;

import com.smartstudy.app.model.Student;
import com.smartstudy.app.service.StudentService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * AI Smart Study Planner
 * Student Login Form
 */
public class LoginForm extends JFrame {

    // ============================================================
    // COLORS
    // ============================================================

    private static final Color BG_COLOR = new Color(5, 20, 42);
    private static final Color PANEL_COLOR = new Color(17, 38, 70);
    private static final Color FIELD_COLOR = new Color(31, 58, 94);
    private static final Color BORDER_COLOR = new Color(54, 91, 135);

    private static final Color BLUE = new Color(45, 125, 235);
    private static final Color BLUE_HOVER = new Color(65, 145, 255);

    private static final Color CYAN = new Color(35, 205, 240);

    private static final Color WHITE = new Color(245, 248, 255);
    private static final Color TEXT_SECONDARY = new Color(150, 175, 205);

    // ============================================================
    // COMPONENTS
    // ============================================================

    private JTextField emailField;
    private JPasswordField passwordField;

    private JCheckBox showPasswordCheck;

    private JButton loginButton;
    private JButton clearButton;

    // ================================================aasif@gmail.com============
    // SERVICE
    // ============================================================

    private final StudentService studentService;

    // ============================================================
    // CONSTRUCTOR
    // ============================================================

    public LoginForm() {

        studentService = new StudentService();

        setTitle("AI Smart Study Planner - Student Login");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(1650, 930);

        setMinimumSize(new Dimension(1100, 700));

        setLocationRelativeTo(null);

        setResizable(true);

        buildUI();

        setVisible(true);
    }

    // ============================================================
    // BUILD UI
    // ============================================================

    private void buildUI() {

        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.setBackground(BG_COLOR);

        JPanel leftPanel = createLeftPanel();

        JPanel rightPanel = createLoginPanel();

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    // ============================================================
    // LEFT PANEL
    // ============================================================

    private JPanel createLeftPanel() {

        JPanel panel = new JPanel();

        panel.setPreferredSize(new Dimension(480, 0));

        panel.setBackground(new Color(7, 27, 56));

        panel.setBorder(
                new EmptyBorder(
                        45,
                        55,
                        35,
                        45
                )
        );

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel productivity = new JLabel(
                "ACADEMIC PRODUCTIVITY"
        );

        productivity.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        17
                )
        );

        productivity.setForeground(CYAN);
        productivity.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(productivity);

        panel.add(Box.createVerticalStrut(48));

        JLabel aiLabel = new JLabel("AI");

        aiLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        58
                )
        );

        aiLabel.setForeground(WHITE);
        aiLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(aiLabel);

        JLabel smartStudy = new JLabel("SMART STUDY");

        smartStudy.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        39
                )
        );

        smartStudy.setForeground(WHITE);
        smartStudy.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(smartStudy);

        JLabel planner = new JLabel("PLANNER");

        planner.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        39
                )
        );

        planner.setForeground(CYAN);
        planner.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(planner);

        panel.add(Box.createVerticalStrut(20));

        JPanel line = new JPanel();

        line.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        6
                )
        );

        line.setBackground(CYAN);
        line.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(line);

        panel.add(Box.createVerticalStrut(35));

        JLabel intelligent = new JLabel(
                "Your Intelligent"
        );

        intelligent.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        25
                )
        );

        intelligent.setForeground(WHITE);
        intelligent.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(intelligent);

        JLabel workspace = new JLabel(
                "Academic Workspace"
        );

        workspace.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        25
                )
        );

        workspace.setForeground(CYAN);
        workspace.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(workspace);

        panel.add(Box.createVerticalStrut(22));

        JLabel description = new JLabel(
                "<html>"
                        + "Plan, organize and track your<br>"
                        + "academic journey with one<br>"
                        + "intelligent study workspace."
                        + "</html>"
        );

        description.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        17
                )
        );

        description.setForeground(TEXT_SECONDARY);
        description.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(description);

        panel.add(Box.createVerticalGlue());

        addFeature(
                panel,
                "01",
                "PLAN",
                "Create smarter study schedules"
        );

        addFeature(
                panel,
                "02",
                "STUDY",
                "Organize subjects & exams"
        );

        addFeature(
                panel,
                "03",
                "TRACK",
                "Monitor your academic progress"
        );

        panel.add(Box.createVerticalGlue());

        JLabel footer = new JLabel(
                "SMART PLANNING  •  BETTER LEARNING  •  BRIGHTER FUTURE"
        );

        footer.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        footer.setForeground(
                new Color(80, 155, 225)
        );

        footer.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(footer);

        return panel;
    }

    // ============================================================
    // FEATURE ITEM
    // ============================================================

    private void addFeature(
            JPanel parent,
            String number,
            String title,
            String description
    ) {

        JPanel feature = new JPanel(
                new BorderLayout(
                        15,
                        0
                )
        );

        feature.setOpaque(false);

        feature.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        feature.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        70
                )
        );

        JLabel numberLabel = new JLabel(number);

        numberLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        19
                )
        );

        numberLabel.setForeground(
                new Color(50, 145, 255)
        );

        feature.add(
                numberLabel,
                BorderLayout.WEST
        );

        JPanel textPanel = new JPanel();

        textPanel.setOpaque(false);

        textPanel.setLayout(
                new BoxLayout(
                        textPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titleLabel = new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        17
                )
        );

        titleLabel.setForeground(WHITE);

        JLabel descLabel = new JLabel(description);

        descLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        descLabel.setForeground(TEXT_SECONDARY);

        textPanel.add(titleLabel);

        textPanel.add(
                Box.createVerticalStrut(4)
        );

        textPanel.add(descLabel);

        feature.add(
                textPanel,
                BorderLayout.CENTER
        );

        parent.add(feature);

        parent.add(
                Box.createVerticalStrut(12)
        );
    }

    // ============================================================
    // LOGIN PANEL
    // ============================================================

    private JPanel createLoginPanel() {

        JPanel outer = new JPanel(
                new GridBagLayout()
        );

        outer.setBackground(BG_COLOR);

        outer.setBorder(
                new EmptyBorder(
                        28,
                        35,
                        28,
                        35
                )
        );

        JPanel card = new JPanel();

        card.setBackground(PANEL_COLOR);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER_COLOR,
                                1
                        ),
                        new EmptyBorder(
                                42,
                                55,
                                35,
                                55
                        )
                )
        );

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel badge = new JLabel(
                "STUDENT PORTAL"
        );

        badge.setOpaque(true);

        badge.setBackground(
                new Color(35, 115, 190)
        );

        badge.setForeground(CYAN);

        badge.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        badge.setBorder(
                new EmptyBorder(
                        10,
                        18,
                        10,
                        18
                )
        );

        badge.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        card.add(badge);

        card.add(
                Box.createVerticalStrut(28)
        );

        JLabel title = new JLabel(
                "Welcome Back"
        );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        36
                )
        );

        title.setForeground(WHITE);

        title.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        card.add(title);

        card.add(
                Box.createVerticalStrut(5)
        );

        JLabel subtitle = new JLabel(
                "Sign in to continue your smarter study journey."
        );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        subtitle.setForeground(TEXT_SECONDARY);

        subtitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        card.add(subtitle);

        card.add(
                Box.createVerticalStrut(28)
        );

        JLabel emailLabel = createLabel(
                "EMAIL ADDRESS"
        );

        card.add(emailLabel);

        card.add(
                Box.createVerticalStrut(7)
        );

        emailField = createTextField(
                "Enter your registered email"
        );

        card.add(emailField);

        card.add(
                Box.createVerticalStrut(15)
        );

        JLabel passwordLabel = createLabel(
                "PASSWORD"
        );

        card.add(passwordLabel);

        card.add(
                Box.createVerticalStrut(7)
        );

        passwordField = new JPasswordField();

        passwordField.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        passwordField.setForeground(WHITE);

        passwordField.setBackground(FIELD_COLOR);

        passwordField.setCaretColor(CYAN);

        passwordField.setEchoChar('•');

        passwordField.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER_COLOR,
                                1
                        ),
                        new EmptyBorder(
                                10,
                                12,
                                10,
                                12
                        )
                )
        );

        passwordField.setPreferredSize(
                new Dimension(
                        0,
                        50
                )
        );

        passwordField.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        50
                )
        );

        passwordField.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        card.add(passwordField);

        showPasswordCheck = new JCheckBox(
                "Show password"
        );

        showPasswordCheck.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        showPasswordCheck.setForeground(
                TEXT_SECONDARY
        );

        showPasswordCheck.setBackground(
                PANEL_COLOR
        );

        showPasswordCheck.setFocusPainted(false);

        showPasswordCheck.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        showPasswordCheck.setBorder(
                new EmptyBorder(
                        5,
                        0,
                        0,
                        0
                )
        );

        showPasswordCheck.addActionListener(
                e -> togglePassword()
        );

        card.add(showPasswordCheck);

        card.add(
                Box.createVerticalStrut(20)
        );

        JPanel buttonPanel = new JPanel(
        new GridLayout(1, 2, 14, 0)
);

buttonPanel.setOpaque(false);
buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
buttonPanel.setMaximumSize(
        new Dimension(Integer.MAX_VALUE, 50)
);

loginButton = createButton(
        "LOGIN TO DASHBOARD",
        BLUE
);

clearButton = createButton(
        "CLEAR",
        new Color(35, 58, 91)
);

buttonPanel.add(loginButton);
buttonPanel.add(clearButton);

card.add(buttonPanel);

card.add(Box.createVerticalStrut(12));

JButton registerButton = createButton(
        "REGISTER NOW",
        new Color(22, 160, 180)
);

registerButton.setAlignmentX(Component.LEFT_ALIGNMENT);
registerButton.setMaximumSize(
        new Dimension(Integer.MAX_VALUE, 45)
);

registerButton.addActionListener(e -> {
    new RegisterForm().setVisible(true);
    dispose();
});

card.add(registerButton);

        card.add(
                Box.createVerticalStrut(20)
        );

        JLabel info = new JLabel(
                "Use your registered student email and password."
        );

        info.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        info.setForeground(
                new Color(115, 150, 190)
        );

        info.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        card.add(info);

        card.add(Box.createVerticalGlue());

        JLabel footer = new JLabel(
                "AI Smart Study Planner   •   Student Login"
        );

        footer.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        footer.setForeground(
                new Color(105, 145, 190)
        );

        footer.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        card.add(footer);

        // ========================================================
        // BUTTON EVENTS
        // ========================================================

        loginButton.addActionListener(
                e -> performLogin()
        );

        clearButton.addActionListener(
                e -> clearForm()
        );

        passwordField.addActionListener(
                e -> performLogin()
        );

        emailField.addActionListener(
                e -> passwordField.requestFocusInWindow()
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        gbc.fill = GridBagConstraints.BOTH;

        outer.add(card, gbc);

        return outer;
    }

    // ============================================================
    // LABEL
    // ============================================================

    private JLabel createLabel(String text) {

        JLabel label = new JLabel(text);

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        label.setForeground(WHITE);

        label.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        return label;
    }

    // ============================================================
    // TEXT FIELD
    // ============================================================

    private JTextField createTextField(
            String tooltip
    ) {

        JTextField field = new JTextField();

        field.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        field.setForeground(WHITE);

        field.setBackground(FIELD_COLOR);

        field.setCaretColor(CYAN);

        field.setToolTipText(tooltip);

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER_COLOR,
                                1
                        ),
                        new EmptyBorder(
                                10,
                                12,
                                10,
                                12
                        )
                )
        );

        field.setPreferredSize(
                new Dimension(
                        0,
                        50
                )
        );

        field.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        50
                )
        );

        field.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        return field;
    }

    // ============================================================
    // BUTTON
    // ============================================================

    private JButton createButton(
            String text,
            Color background
    ) {

        JButton button = new JButton(text);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        button.setForeground(Color.WHITE);

        button.setBackground(background);

        button.setOpaque(true);

        button.setContentAreaFilled(true);

        button.setBorderPainted(false);

        button.setFocusPainted(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setBorder(
                new EmptyBorder(
                        12,
                        15,
                        12,
                        15
                )
        );

        button.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            MouseEvent e
                    ) {

                        button.setBackground(
                                BLUE_HOVER
                        );
                    }

                    @Override
                    public void mouseExited(
                            MouseEvent e
                    ) {

                        button.setBackground(
                                background
                        );
                    }
                }
        );

        return button;
    }

    // ============================================================
    // SHOW / HIDE PASSWORD
    // ============================================================

    private void togglePassword() {

        if (showPasswordCheck.isSelected()) {

            passwordField.setEchoChar(
                    (char) 0
            );

        } else {

            passwordField.setEchoChar(
                    '•'
            );
        }
    }

    // ============================================================
    // CLEAR FORM
    // ============================================================

    private void clearForm() {

        emailField.setText("");

        passwordField.setText("");

        showPasswordCheck.setSelected(false);

        passwordField.setEchoChar('•');

        emailField.requestFocusInWindow();
    }

    // ============================================================
    // LOGIN
    // ============================================================

    private void performLogin() {

        String email =
                emailField
                        .getText()
                        .trim();

        String password =
                new String(
                        passwordField
                                .getPassword()
                ).trim();

        // --------------------------------------------------------
        // VALIDATION
        // --------------------------------------------------------

        if (email.isEmpty()) {

            showWarning(
                    "Please enter your email address."
            );

            emailField.requestFocusInWindow();

            return;
        }

        if (password.isEmpty()) {

            showWarning(
                    "Please enter your password."
            );

            passwordField.requestFocusInWindow();

            return;
        }

        // --------------------------------------------------------
        // LOGIN THROUGH SERVICE
        // --------------------------------------------------------

        try {

            Student student =
                    studentService.loginStudent(
                            email,
                            password
                    );

            if (student != null) {

                JDialog successDialog = new JDialog(
        this, "Login Successful", true
);

successDialog.setUndecorated(true);
successDialog.setSize(420, 240);
successDialog.setLocationRelativeTo(this);

JPanel panel = new JPanel(new BorderLayout(15, 15));
panel.setBackground(new Color(15, 35, 65));
panel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(
                new Color(40, 190, 220), 2
        ),
        BorderFactory.createEmptyBorder(25, 25, 20, 25)
));

JLabel iconLabel = new JLabel("SUCCESS", SwingConstants.CENTER);
iconLabel.setFont(new Font("Arial", Font.BOLD, 14));
iconLabel.setForeground(new Color(40, 220, 180));
iconLabel.setFont(new Font("Arial", Font.BOLD, 30));
iconLabel.setForeground(new Color(40, 220, 180));

JLabel titleLabel = new JLabel(
        "Welcome back, " + student.getName() + "!",
        SwingConstants.CENTER
);
titleLabel.setFont(new Font("Arial", Font.BOLD, 21));
titleLabel.setForeground(Color.WHITE);

JLabel messageLabel = new JLabel(
        "You have logged in successfully.",
        SwingConstants.CENTER
);
messageLabel.setFont(new Font("Arial", Font.PLAIN, 13));
messageLabel.setForeground(new Color(185, 205, 230));

JButton okButton = new JButton("CONTINUE");
okButton.setFont(new Font("Arial", Font.BOLD, 13));
okButton.setForeground(Color.WHITE);
okButton.setBackground(new Color(35, 125, 220));
okButton.setFocusPainted(false);
okButton.setBorderPainted(false);
okButton.setOpaque(true);
okButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
okButton.setPreferredSize(new Dimension(140, 38));

okButton.addActionListener(e -> successDialog.dispose());

JPanel textPanel = new JPanel(new GridLayout(2, 1, 5, 5));
textPanel.setBackground(new Color(15, 35, 65));
textPanel.add(titleLabel);
textPanel.add(messageLabel);

JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
centerPanel.setBackground(new Color(15, 35, 65));
centerPanel.add(iconLabel, BorderLayout.NORTH);
centerPanel.add(textPanel, BorderLayout.CENTER);

JPanel buttonPanel = new JPanel();
buttonPanel.setBackground(new Color(15, 35, 65));
buttonPanel.add(okButton);

panel.add(centerPanel, BorderLayout.CENTER);
panel.add(buttonPanel, BorderLayout.SOUTH);

successDialog.setContentPane(panel);
successDialog.getRootPane().setDefaultButton(okButton);
successDialog.setVisible(true);

                openDashboard(student);

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid email or password.\n\n"
                                + "Please check your registered credentials.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE
                );

                passwordField.selectAll();

                passwordField.requestFocusInWindow();
            }

        } catch (Exception ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to login.\n\n"
                            + "Please make sure Oracle Database "
                            + "is running and your database configuration "
                            + "is correct.\n\n"
                            + "Error: "
                            + ex.getMessage(),
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ============================================================
    // OPEN DASHBOARD
    // ============================================================

    private void openDashboard(Student student) {

        try {

            /*
             * IMPORTANT:
             * Dashboard will receive the logged-in Student object.
             *
             * For the next step, Dashboard will be changed to:
             *
             * public Dashboard(Student student)
             *
             * This allows Dashboard to display:
             * student.getName()
             * student.getSemester()
             * student.getRollNo()
             * student.getEmail()
             */

            Dashboard dashboard =
                    new Dashboard(student);

            dashboard.setLocationRelativeTo(null);

            dashboard.setVisible(true);

            dispose();

        } catch (Exception ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Login successful, but Dashboard could not be opened.\n\n"
                            + "The Dashboard constructor must accept Student.\n\n"
                            + "Next step: update Dashboard.java.",
                    "Dashboard Error",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // ============================================================
    // WARNING
    // ============================================================

    private void showWarning(
            String message
    ) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Input Required",
                JOptionPane.WARNING_MESSAGE
        );
    }

    // ============================================================
    // MAIN
    // ============================================================

    public static void main(String[] args) {

        try {

            UIManager.setLookAndFeel(
                    UIManager
                            .getSystemLookAndFeelClassName()
            );

        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(
                LoginForm::new
        );
    }
}