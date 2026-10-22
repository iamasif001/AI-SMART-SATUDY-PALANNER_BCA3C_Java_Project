package com.smartstudy.app.ui;

import com.smartstudy.app.model.Student;
import com.smartstudy.app.service.StudentService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * AI Smart Study Planner
 * Student Registration Form
 *
 * Professional Dark AI themed Swing UI.
 */
public class RegisterForm extends JFrame {

    // ============================================================
    // COLORS
    // ============================================================

    private static final Color BG_DARK =
            new Color(7, 18, 38);

    private static final Color BG_LEFT =
            new Color(9, 27, 55);

    private static final Color CARD =
            new Color(15, 34, 64);

    private static final Color FIELD =
            new Color(27, 52, 86);

    private static final Color FIELD_HOVER =
            new Color(32, 61, 99);

    private static final Color BORDER =
            new Color(53, 87, 130);

    private static final Color PRIMARY =
            new Color(43, 124, 255);

    private static final Color PRIMARY_HOVER =
            new Color(65, 142, 255);

    private static final Color CYAN =
            new Color(40, 205, 245);

    private static final Color WHITE =
            new Color(245, 248, 255);

    private static final Color MUTED =
            new Color(145, 166, 195);


    // ============================================================
    // UI COMPONENTS
    // ============================================================

    private JTextField nameField;
    private JTextField rollNoField;
    private JTextField emailField;

    private JPasswordField passwordField;

    private JComboBox<String> semesterComboBox;

    private JCheckBox showPasswordCheckBox;

    private RoundedButton createAccountButton;
    private RoundedButton clearButton;

    private final StudentService studentService;


    // ============================================================
    // CONSTRUCTOR
    // ============================================================

    public RegisterForm() {

        studentService = new StudentService();

        initializeFrame();
        buildUI();
    }


    // ============================================================
    // FRAME SETTINGS
    // ============================================================

    private void initializeFrame() {

        setTitle(
                "AI Smart Study Planner - Create Student Account"
        );

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setMinimumSize(
                new Dimension(
                        1100,
                        700
                )
        );

        setExtendedState(
                JFrame.MAXIMIZED_BOTH
        );

        setLocationRelativeTo(null);

        getContentPane().setBackground(
                BG_DARK
        );
    }


    // ============================================================
    // BUILD MAIN UI
    // ============================================================

    private void buildUI() {

        JPanel root =
                new JPanel(
                        new GridBagLayout()
                );

        root.setBackground(
                BG_DARK
        );

        root.setBorder(
                new EmptyBorder(
                        24,
                        24,
                        24,
                        24
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();


        // --------------------------------------------------------
        // LEFT PANEL
        // --------------------------------------------------------

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.38;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        gbc.insets =
                new Insets(
                        0,
                        0,
                        0,
                        18
                );

        JPanel leftPanel =
                createLeftPanel();

        root.add(
                leftPanel,
                gbc
        );


        // --------------------------------------------------------
        // RIGHT PANEL
        // --------------------------------------------------------

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.62;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        gbc.insets =
                new Insets(
                        0,
                        0,
                        0,
                        0
                );

        JPanel rightPanel =
                createRightPanel();

        root.add(
                rightPanel,
                gbc
        );


        add(
                root,
                BorderLayout.CENTER
        );
    }


    // ============================================================
    // LEFT BRAND PANEL
    // ============================================================

    private JPanel createLeftPanel() {

        JPanel panel =
                new JPanel() {

                    @Override
                    protected void paintComponent(
                            Graphics g
                    ) {

                        super.paintComponent(g);

                        Graphics2D g2 =
                                (Graphics2D) g.create();

                        g2.setRenderingHint(
                                RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON
                        );

                        int width =
                                getWidth();

                        int height =
                                getHeight();


                        // Main background

                        g2.setColor(
                                BG_LEFT
                        );

                        g2.fillRect(
                                0,
                                0,
                                width,
                                height
                        );


                        // Large blue circle

                        g2.setColor(
                                new Color(
                                        16,
                                        57,
                                        111
                                )
                        );

                        g2.fillOval(
                                -180,
                                -150,
                                500,
                                500
                        );


                        // Bottom blue circle

                        g2.setColor(
                                new Color(
                                        12,
                                        45,
                                        91
                                )
                        );

                        g2.fillOval(
                                width - 260,
                                height - 260,
                                450,
                                450
                        );


                        // AI network lines

                        g2.setStroke(
                                new BasicStroke(
                                        1.2f
                                )
                        );

                        g2.setColor(
                                new Color(
                                        43,
                                        124,
                                        255,
                                        70
                                )
                        );

                        int centerX =
                                width / 2;

                        int centerY =
                                height / 2;


                        g2.drawLine(
                                centerX,
                                centerY - 130,
                                centerX - 120,
                                centerY - 40
                        );

                        g2.drawLine(
                                centerX,
                                centerY - 130,
                                centerX + 130,
                                centerY - 20
                        );

                        g2.drawLine(
                                centerX - 120,
                                centerY - 40,
                                centerX - 150,
                                centerY + 100
                        );

                        g2.drawLine(
                                centerX + 130,
                                centerY - 20,
                                centerX + 160,
                                centerY + 120
                        );

                        g2.drawLine(
                                centerX - 150,
                                centerY + 100,
                                centerX,
                                centerY + 180
                        );

                        g2.drawLine(
                                centerX + 160,
                                centerY + 120,
                                centerX,
                                centerY + 180
                        );


                        // Network nodes

                        drawNode(
                                g2,
                                centerX,
                                centerY - 130
                        );

                        drawNode(
                                g2,
                                centerX - 120,
                                centerY - 40
                        );

                        drawNode(
                                g2,
                                centerX + 130,
                                centerY - 20
                        );

                        drawNode(
                                g2,
                                centerX - 150,
                                centerY + 100
                        );

                        drawNode(
                                g2,
                                centerX + 160,
                                centerY + 120
                        );

                        drawNode(
                                g2,
                                centerX,
                                centerY + 180
                        );

                        g2.dispose();
                    }
                };


        panel.setOpaque(
                true
        );

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );

        panel.setBorder(
                new EmptyBorder(
                        42,
                        52,
                        42,
                        52
                )
        );


        // --------------------------------------------------------
        // TOP LABEL
        // --------------------------------------------------------

        JLabel topLabel =
                new JLabel(
                        "ACADEMIC PRODUCTIVITY"
                );

        topLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );

        topLabel.setForeground(
                CYAN
        );

        topLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        // --------------------------------------------------------
        // AI TITLE
        // --------------------------------------------------------

        JLabel aiLabel =
                new JLabel(
                        "AI"
                );

        aiLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        64
                )
        );

        aiLabel.setForeground(
                WHITE
        );

        aiLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        JLabel smartLabel =
                new JLabel(
                        "SMART STUDY"
                );

        smartLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        40
                )
        );

        smartLabel.setForeground(
                WHITE
        );

        smartLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        JLabel plannerLabel =
                new JLabel(
                        "PLANNER"
                );

        plannerLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        40
                )
        );

        plannerLabel.setForeground(
                CYAN
        );

        plannerLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        // --------------------------------------------------------
        // SEPARATOR
        // --------------------------------------------------------

        JPanel separator =
                new JPanel();

        separator.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        3
                )
        );

        separator.setBackground(
                CYAN
        );

        separator.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        // --------------------------------------------------------
        // DESCRIPTION
        // --------------------------------------------------------

        JLabel intelligent =
                new JLabel(
                        "Your Intelligent"
                );

        intelligent.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        25
                )
        );

        intelligent.setForeground(
                WHITE
        );

        intelligent.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        JLabel workspace =
                new JLabel(
                        "Academic Workspace"
                );

        workspace.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        25
                )
        );

        workspace.setForeground(
                CYAN
        );

        workspace.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        JLabel description =
                new JLabel(
                        "<html>"
                                + "Plan, organize and track your academic journey<br>"
                                + "with one intelligent study workspace."
                                + "</html>"
                );

        description.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        16
                )
        );

        description.setForeground(
                MUTED
        );

        description.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        // --------------------------------------------------------
        // FEATURE ITEMS
        // --------------------------------------------------------

        JPanel features =
                new JPanel();

        features.setOpaque(
                false
        );

        features.setLayout(
                new BoxLayout(
                        features,
                        BoxLayout.Y_AXIS
                )
        );

        features.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        features.add(
                createFeature(
                        "01",
                        "PLAN",
                        "Create smarter study schedules"
                )
        );

        features.add(
                Box.createVerticalStrut(
                        22
                )
        );

        features.add(
                createFeature(
                        "02",
                        "STUDY",
                        "Organize subjects and exams"
                )
        );

        features.add(
                Box.createVerticalStrut(
                        22
                )
        );

        features.add(
                createFeature(
                        "03",
                        "TRACK",
                        "Monitor your academic progress"
                )
        );


        // --------------------------------------------------------
        // FOOTER
        // --------------------------------------------------------

        JLabel footer =
                new JLabel(
                        "SMART PLANNING  •  BETTER LEARNING  •  BRIGHTER FUTURE"
                );

        footer.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );

        footer.setForeground(
                new Color(
                        115,
                        168,
                        235
                )
        );

        footer.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        // --------------------------------------------------------
        // ADD EVERYTHING
        // --------------------------------------------------------

        panel.add(
                topLabel
        );

        panel.add(
                Box.createVerticalStrut(
                        55
                )
        );

        panel.add(
                aiLabel
        );

        panel.add(
                Box.createVerticalStrut(
                        2
                )
        );

        panel.add(
                smartLabel
        );

        panel.add(
                plannerLabel
        );

        panel.add(
                Box.createVerticalStrut(
                        20
                )
        );

        panel.add(
                separator
        );

        panel.add(
                Box.createVerticalStrut(
                        35
                )
        );

        panel.add(
                intelligent
        );

        panel.add(
                workspace
        );

        panel.add(
                Box.createVerticalStrut(
                        22
                )
        );

        panel.add(
                description
        );

        panel.add(
                Box.createVerticalGlue()
        );

        panel.add(
                features
        );

        panel.add(
                Box.createVerticalGlue()
        );

        panel.add(
                footer
        );


        return panel;
    }


    // ============================================================
    // NETWORK NODE
    // ============================================================

    private void drawNode(
            Graphics2D g2,
            int x,
            int y
    ) {

        g2.setColor(
                CYAN
        );

        g2.fillOval(
                x - 5,
                y - 5,
                10,
                10
        );
    }


    // ============================================================
    // FEATURE COMPONENT
    // ============================================================

    private JPanel createFeature(
            String number,
            String title,
            String description
    ) {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                18,
                                0
                        )
                );

        panel.setOpaque(
                false
        );

        panel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        JLabel numberLabel =
                new JLabel(
                        number
                );

        numberLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        numberLabel.setForeground(
                new Color(
                        65,
                        145,
                        255
                )
        );


        JPanel textPanel =
                new JPanel();

        textPanel.setOpaque(
                false
        );

        textPanel.setLayout(
                new BoxLayout(
                        textPanel,
                        BoxLayout.Y_AXIS
                )
        );


        JLabel titleLabel =
                new JLabel(
                        title
                );

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        16
                )
        );

        titleLabel.setForeground(
                WHITE
        );


        JLabel descriptionLabel =
                new JLabel(
                        description
                );

        descriptionLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
                
        );

        descriptionLabel.setForeground(
                MUTED
        );


        textPanel.add(
                titleLabel
        );

        textPanel.add(
                Box.createVerticalStrut(
                        4
                )
        );

        textPanel.add(
                descriptionLabel
        );


        panel.add(
                numberLabel,
                BorderLayout.WEST
        );

        panel.add(
                textPanel,
                BorderLayout.CENTER
        );


        return panel;
    }
        // ============================================================
    // RIGHT REGISTRATION PANEL
    // ============================================================

    private JPanel createRightPanel() {

        JPanel outerPanel =
                new JPanel(
                        new BorderLayout()
                );

        outerPanel.setBackground(
                CARD
        );

        outerPanel.setBorder(
                new LineBorder(
                        BORDER,
                        1,
                        true
                )
        );


        JPanel content =
                new JPanel();

        content.setOpaque(
                false
        );

        content.setLayout(
                new BoxLayout(
                        content,
                        BoxLayout.Y_AXIS
                )
        );

        content.setBorder(
                new EmptyBorder(
                        40,
                        60,
                        28,
                        60
                )
        );


        // --------------------------------------------------------
        // NEW STUDENT HEADER
        // --------------------------------------------------------

        JPanel header =
                new JPanel(
                        new GridBagLayout()
                );

        header.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        38
                )
        );

        header.setBackground(
                new Color(
                        39,
                        116,
                        190
                )
        );


        JLabel headerLabel =
                new JLabel(
                        "NEW STUDENT"
                );

        headerLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        headerLabel.setForeground(
                CYAN
        );

        header.add(
                headerLabel
        );


        // --------------------------------------------------------
        // TITLE
        // --------------------------------------------------------

        JLabel title =
                new JLabel(
                        "Create your account"
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        38
                )
        );

        title.setForeground(
                WHITE
        );

        title.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        // --------------------------------------------------------
        // SUBTITLE
        // --------------------------------------------------------

        JLabel subtitle =
                new JLabel(
                        "Start building your smarter study journey."
                );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        subtitle.setForeground(
                MUTED
        );

        subtitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        // --------------------------------------------------------
        // NAME FIELD
        // --------------------------------------------------------

        JLabel nameLabel =
                createFieldLabel(
                        "FULL NAME"
                );

        nameField =
                createTextField();


        // --------------------------------------------------------
        // ROLL NUMBER FIELD
        // --------------------------------------------------------

        JLabel rollLabel =
                createFieldLabel(
                        "ROLL NUMBER"
                );

        rollNoField =
                createTextField();


        // --------------------------------------------------------
        // EMAIL FIELD
        // --------------------------------------------------------

        JLabel emailLabel =
                createFieldLabel(
                        "EMAIL ADDRESS"
                );

        emailField =
                createTextField();


        // --------------------------------------------------------
        // PASSWORD FIELD
        // --------------------------------------------------------

        JLabel passwordLabel =
                createFieldLabel(
                        "PASSWORD"
                );

        passwordField =
                new JPasswordField();

        styleTextField(
                passwordField
        );


        // --------------------------------------------------------
        // SHOW PASSWORD
        // --------------------------------------------------------

        showPasswordCheckBox =
                new JCheckBox(
                        "Show password"
                );

        showPasswordCheckBox.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        showPasswordCheckBox.setForeground(
                MUTED
        );

        showPasswordCheckBox.setOpaque(
                false
        );

        showPasswordCheckBox.setFocusPainted(
                false
        );

        showPasswordCheckBox.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        showPasswordCheckBox.addActionListener(
                e -> {

                    if (
                            showPasswordCheckBox.isSelected()
                    ) {

                        passwordField.setEchoChar(
                                (char) 0
                        );

                    } else {

                        passwordField.setEchoChar(
                                '•'
                        );
                    }
                }
        );


        // --------------------------------------------------------
        // SEMESTER LABEL
        // --------------------------------------------------------

        JLabel semesterLabel =
                createFieldLabel(
                        "CURRENT SEMESTER"
                );


        // --------------------------------------------------------
        // SEMESTER COMBO BOX
        // --------------------------------------------------------

        semesterComboBox =
                new JComboBox<>(
                        new String[]{
                                "Select semester",
                                "Semester 1",
                                "Semester 2",
                                "Semester 3",
                                "Semester 4",
                                "Semester 5",
                                "Semester 6"
                        }
                );


        styleSemesterComboBox();


        // --------------------------------------------------------
        // BUTTONS
        // --------------------------------------------------------

        createAccountButton =
                new RoundedButton(
                        "CREATE ACCOUNT"
                );

        createAccountButton.setBackground(
                PRIMARY
        );

        createAccountButton.setForeground(
                WHITE
        );

        createAccountButton.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        createAccountButton.setFocusPainted(
                false
        );


        clearButton =
                new RoundedButton(
                        "CLEAR"
                );

        clearButton.setBackground(
                new Color(
                        30,
                        58,
                        94
                )
        );

        clearButton.setForeground(
                WHITE
        );

        clearButton.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        clearButton.setFocusPainted(
                false
        );


        // --------------------------------------------------------
        // BUTTON PANEL
        // --------------------------------------------------------

        JPanel buttonPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                18,
                                0
                        )
                );

        buttonPanel.setOpaque(
                false
        );

        buttonPanel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        42
                )
        );

        buttonPanel.add(
                createAccountButton
        );

        buttonPanel.add(
                clearButton

                
        );
        JButton backToLoginButton = new JButton("BACK TO LOGIN");

backToLoginButton.setBackground(new Color(30, 58, 94));
backToLoginButton.setForeground(WHITE);
backToLoginButton.setFont(
        new Font("Segoe UI", Font.BOLD, 14)
);
backToLoginButton.setFocusPainted(false);
backToLoginButton.setCursor(
        new Cursor(Cursor.HAND_CURSOR)
);

backToLoginButton.addActionListener(e -> {
    dispose();
    new LoginForm().setVisible(true);
});


        // --------------------------------------------------------
        // FOOTER
        // --------------------------------------------------------

        JLabel footer =
                new JLabel(
                        "AI Smart Study Planner   •   Student Portal"
                );

        footer.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        footer.setForeground(
                MUTED
        );

        footer.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        // ========================================================
        // ADD COMPONENTS
        // ========================================================

        content.add(
                header
        );

        content.add(
                Box.createVerticalStrut(
                        40
                )
        );

        content.add(
                title
        );

        content.add(
                Box.createVerticalStrut(
                        8
                )
        );

        content.add(
                subtitle
        );

        content.add(
                Box.createVerticalStrut(
                        38
                )
        );


        // NAME

        content.add(
                nameLabel
        );

        content.add(
                Box.createVerticalStrut(
                        8
                )
        );

        content.add(
                nameField
        );

        content.add(
                Box.createVerticalStrut(
                        20
                )
        );


        // ROLL NUMBER

        content.add(
                rollLabel
        );

        content.add(
                Box.createVerticalStrut(
                        8
                )
        );

        content.add(
                rollNoField
        );

        content.add(
                Box.createVerticalStrut(
                        20
                )
        );


        // EMAIL

        content.add(
                emailLabel
        );

        content.add(
                Box.createVerticalStrut(
                        8
                )
        );

        content.add(
                emailField
        );

        content.add(
                Box.createVerticalStrut(
                        20
                )
        );


        // PASSWORD

        content.add(
                passwordLabel
        );

        content.add(
                Box.createVerticalStrut(
                        8
                )
        );

        content.add(
                passwordField
        );

        content.add(
                Box.createVerticalStrut(
                        8
                )
        );

        content.add(
                showPasswordCheckBox
        );

        content.add(
                Box.createVerticalStrut(
                        14
                )
        );


        // SEMESTER

        content.add(
                semesterLabel
        );

        content.add(
                Box.createVerticalStrut(
                        8
                )
        );

        content.add(
                semesterComboBox
        );

        content.add(
                Box.createVerticalStrut(
                        38
                )
        );


        // BUTTONS

        content.add(
                buttonPanel
        );


backToLoginButton.setBackground(new Color(30, 58, 94));
backToLoginButton.setForeground(Color.WHITE);
backToLoginButton.setOpaque(true);
backToLoginButton.setContentAreaFilled(true);
backToLoginButton.setBorderPainted(false);
backToLoginButton.setFont(
        new Font("Segoe UI", Font.BOLD, 14)
);
backToLoginButton.setFocusPainted(false);
backToLoginButton.setCursor(
        new Cursor(Cursor.HAND_CURSOR)
);
backToLoginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
backToLoginButton.setMaximumSize(
        new Dimension(Integer.MAX_VALUE, 42)
);

backToLoginButton.addActionListener(e -> {
    dispose();
    new LoginForm().setVisible(true);
});

content.add(Box.createVerticalStrut(12));
content.add(backToLoginButton);

        content.add(
                Box.createVerticalStrut(
                        22
                )
        );


        content.add(
                footer
        );


        outerPanel.add(
                content,
                BorderLayout.CENTER
        );


        // --------------------------------------------------------
        // BUTTON ACTIONS
        // --------------------------------------------------------

        createAccountButton.addActionListener(
                e -> registerStudent()
        );

        clearButton.addActionListener(
                e -> clearForm()
        );


        return outerPanel;
    }


    // ============================================================
    // FIELD LABEL
    // ============================================================

    private JLabel createFieldLabel(
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
                WHITE
        );

        label.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        return label;
    }


    // ============================================================
    // TEXT FIELD
    // ============================================================

    private JTextField createTextField() {

        JTextField field =
                new JTextField();

        styleTextField(
                field
        );

        return field;
    }


    // ============================================================
    // TEXT FIELD STYLE
    // ============================================================

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
                CYAN
        );

        field.setBorder(
                new LineBorder(
                        BORDER,
                        1
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


        field.addFocusListener(
                new FocusAdapter() {

                    @Override
                    public void focusGained(
                            FocusEvent e
                    ) {

                        field.setBackground(
                                FIELD_HOVER
                        );

                        field.setBorder(
                                new LineBorder(
                                        CYAN,
                                        1
                                )
                        );
                    }


                    @Override
                    public void focusLost(
                            FocusEvent e
                    ) {

                        field.setBackground(
                                FIELD
                        );

                        field.setBorder(
                                new LineBorder(
                                        BORDER,
                                        1
                                )
                        );
                    }
                }
        );
    }


   // ============================================================
// SEMESTER COMBO BOX STYLE
// ============================================================

private void styleSemesterComboBox() {

    semesterComboBox.setFont(
            new Font("Segoe UI", Font.PLAIN, 14)
    );

    semesterComboBox.setForeground(Color.WHITE);
    semesterComboBox.setBackground(FIELD);
    semesterComboBox.setOpaque(true);
    semesterComboBox.setEditable(false);

    semesterComboBox.setMaximumSize(
            new Dimension(Integer.MAX_VALUE, 48)
    );

    semesterComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);

    semesterComboBox.setBorder(
            new LineBorder(BORDER, 1)
    );

    semesterComboBox.setUI(
            new javax.swing.plaf.basic.BasicComboBoxUI() {

                @Override
                protected void installDefaults() {
                    super.installDefaults();

                    comboBox.setBackground(FIELD);
                    comboBox.setForeground(Color.WHITE);
                    comboBox.setOpaque(true);
                }

                @Override
                protected JButton createArrowButton() {
                    JButton button = new JButton("▼");

                    button.setBackground(FIELD);
                    button.setForeground(Color.WHITE);
                    button.setOpaque(true);
                    button.setContentAreaFilled(true);
                    button.setBorderPainted(false);
                    button.setFocusPainted(false);

                    return button;
                }
            }
    );

    semesterComboBox.setRenderer(
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
                                    list,
                                    value,
                                    index,
                                    isSelected,
                                    cellHasFocus
                            );

                    label.setOpaque(true);
                    label.setFont(
                            new Font("Segoe UI", Font.PLAIN, 14)
                    );

                    label.setBackground(
                            isSelected
                                    ? new Color(35, 115, 190)
                                    : FIELD
                    );

                    label.setForeground(Color.WHITE);

                    return label;
                }
            }
    );

    semesterComboBox.repaint();
}
        // ============================================================
    // REGISTER STUDENT
    // ============================================================

    private void registerStudent() {

        String name =
                nameField.getText().trim();

        String rollNo =
                rollNoField.getText().trim();

        String email =
                emailField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                ).trim();


        int selectedSemester =
                semesterComboBox.getSelectedIndex();


        // --------------------------------------------------------
        // VALIDATION
        // --------------------------------------------------------

        if (name.isEmpty()) {

            showError(
                    "Please enter your full name."
            );

            nameField.requestFocus();

            return;
        }


        if (rollNo.isEmpty()) {

            showError(
                    "Please enter your roll number."
            );

            rollNoField.requestFocus();

            return;
        }


        if (email.isEmpty()) {

            showError(
                    "Please enter your email address."
            );

            emailField.requestFocus();

            return;
        }


        if (!isValidEmail(email)) {

            showError(
                    "Please enter a valid email address."
            );

            emailField.requestFocus();

            return;
        }


        if (password.isEmpty()) {

            showError(
                    "Please enter a password."
            );

            passwordField.requestFocus();

            return;
        }


        if (password.length() < 6) {

            showError(
                    "Password must contain at least 6 characters."
            );

            passwordField.requestFocus();

            return;
        }


        if (selectedSemester == 0) {

            showError(
                    "Please select your current semester."
            );

            semesterComboBox.requestFocus();

            return;
        }


        // --------------------------------------------------------
        // CHECK DUPLICATE EMAIL
        // --------------------------------------------------------

        if (studentService.emailExists(email)) {

            showError(
                    "This email address is already registered."
            );

            emailField.requestFocus();

            return;
        }


        // --------------------------------------------------------
        // CHECK DUPLICATE ROLL NUMBER
        // --------------------------------------------------------

        if (studentService.rollNoExists(rollNo)) {

            showError(
                    "This roll number is already registered."
            );

            rollNoField.requestFocus();

            return;
        }


        // --------------------------------------------------------
        // CREATE STUDENT OBJECT
        // --------------------------------------------------------

        Student student =
                new Student(
                        name,
                        rollNo,
                        email,
                        password,
                        selectedSemester
                );


        // --------------------------------------------------------
        // DISABLE BUTTON
        // --------------------------------------------------------

        createAccountButton.setEnabled(
                false
        );

        createAccountButton.setText(
                "CREATING..."
        );


        // --------------------------------------------------------
        // DATABASE OPERATION
        // --------------------------------------------------------

        SwingWorker<Boolean, Void> worker =
                new SwingWorker<Boolean, Void>() {

                    @Override
                    protected Boolean doInBackground() {

                        return studentService
                                .registerStudent(
                                        student
                                );
                    }


                    @Override
                    protected void done() {

                        createAccountButton.setEnabled(
                                true
                        );

                        createAccountButton.setText(
                                "CREATE ACCOUNT"
                        );


                        try {

                            boolean registered =
                                    get();


                            if (registered) {

                                JOptionPane.showMessageDialog(
                                        RegisterForm.this,
                                        "Student account created successfully!",
                                        "Registration Successful",
                                        JOptionPane.INFORMATION_MESSAGE
                                );


                                clearForm();


                                // ------------------------------------------------
                                // OPEN DASHBOARD
                                // ------------------------------------------------

                                openDashboard(
                                        student
                                );

                            } else {

                                showError(
                                        "Registration failed.\n\n"
                                                + "Please check your database connection."
                                );
                            }

                        } catch (Exception e) {

                            e.printStackTrace();

                            showError(
                                    "Unable to create account.\n\n"
                                            + "Please check your database connection."
                            );
                        }
                    }
                };


        worker.execute();
    }


    // ============================================================
    // OPEN DASHBOARD
    // ============================================================

    private void openDashboard(
            Student student
    ) {

        try {

            /*
             * Dashboard class ko abhi directly open
             * karne ki koshish nahi kar rahe.
             *
             * Agar Dashboard class project me available hai
             * to neeche wala code usko open karega.
             */

            Class<?> dashboardClass =
                    Class.forName(
                            "com.smartstudy.app.ui.Dashboard"
                    );


            Object dashboardObject =
                    dashboardClass
                            .getDeclaredConstructor(
                                    Student.class
                            )
                            .newInstance(
                                    student
                            );


            if (
                    dashboardObject
                            instanceof JFrame
            ) {

                JFrame dashboard =
                        (JFrame) dashboardObject;

                dashboard.setLocationRelativeTo(
                        null
                );

                dashboard.setVisible(
                        true
                );


                dispose();
            }

        } catch (ClassNotFoundException e) {

            /*
             * Dashboard class abhi project me nahi hai.
             *
             * Registration successful rahega.
             */

            System.out.println(
                    "Dashboard class not found."
            );

        } catch (Exception e) {

            e.printStackTrace();

            showError(
                    "Account created successfully,\n"
                            + "but Dashboard could not be opened."
            );
        }
    }


    // ============================================================
    // EMAIL VALIDATION
    // ============================================================

    private boolean isValidEmail(
            String email
    ) {

        return email.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        );
    }


    // ============================================================
    // CLEAR FORM
    // ============================================================

    private void clearForm() {

        nameField.setText(
                ""
        );

        rollNoField.setText(
                ""
        );

        emailField.setText(
                ""
        );

        passwordField.setText(
                ""
        );


        passwordField.setEchoChar(
                '•'
        );


        showPasswordCheckBox.setSelected(
                false
        );


        semesterComboBox.setSelectedIndex(
                0
        );


        // Default semester box
        // normal state

       semesterComboBox.setBackground(
        FIELD
);



        semesterComboBox.setForeground(
                new Color(
                        220,
                        225,
                        235
                )
        );


        nameField.requestFocus();
    }


    // ============================================================
    // ERROR MESSAGE
    // ============================================================

    private void showError(
            String message
    ) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Registration",
                JOptionPane.ERROR_MESSAGE
        );
    }


    // ============================================================
    // ROUNDED BUTTON
    // ============================================================

    private static class RoundedButton
            extends JButton {

        private Color normalColor =
                PRIMARY;

        private Color hoverColor =
                PRIMARY_HOVER;

        private boolean hovering =
                false;


        public RoundedButton(
                String text
        ) {

            super(text);


            setFont(
                    new Font(
                            "Segoe UI",
                            Font.BOLD,
                            14
                    )
            );


            setForeground(
                    Color.WHITE
            );


            setBackground(
                    normalColor
            );


            setFocusPainted(
                    false
            );


            setBorderPainted(
                    false
            );


            setContentAreaFilled(
                    false
            );


            setOpaque(
                    false
            );


            setCursor(
                    new Cursor(
                            Cursor.HAND_CURSOR
                    )
            );


            addMouseListener(
                    new MouseAdapter() {

                        @Override
                        public void mouseEntered(
                                MouseEvent e
                        ) {

                            hovering = true;

                            repaint();
                        }


                        @Override
                        public void mouseExited(
                                MouseEvent e
                        ) {

                            hovering = false;

                            repaint();
                        }
                    }
            );
        }


        @Override
        public void setBackground(
                Color color
        ) {

            normalColor =
                    color;


            hoverColor =
                    new Color(
                            Math.min(
                                    color.getRed() + 20,
                                    255
                            ),
                            Math.min(
                                    color.getGreen() + 20,
                                    255
                            ),
                            Math.min(
                                    color.getBlue() + 20,
                                    255
                            )
                    );


            super.setBackground(
                    color
            );


            repaint();
        }


        @Override
        protected void paintComponent(
                Graphics g
        ) {

            Graphics2D g2 =
                    (Graphics2D) g.create();


            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );


            Color background =
                    isEnabled()
                            ? (
                            hovering
                                    ? hoverColor
                                    : normalColor
                    )
                            : new Color(
                            55,
                            70,
                            92
                    );


            g2.setColor(
                    background
            );


            g2.fillRoundRect(
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    12,
                    12
            );


            g2.dispose();


            super.paintComponent(
                    g
            );
        }
    }


    // ============================================================
    // MAIN METHOD
    // ============================================================

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


                    RegisterForm form =
                            new RegisterForm();


                    form.setVisible(
                            true
                    );


                    SwingUtilities.invokeLater(
                            () -> {

                                form.revalidate();

                                form.repaint();
                            }
                    );
                }
        );
    }
}