
package com.smartstudy.app.ui;

import com.smartstudy.app.model.Student;
import com.smartstudy.app.model.Subject;
import com.smartstudy.app.service.NoteService;
import com.smartstudy.app.service.SubjectService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class NotesForm extends JFrame {

    private final Student student;
    private final NoteService noteService = new NoteService();
    private final SubjectService subjectService;

    private JComboBox<SubjectItem> subjectBox;
    private JTextField titleField;
    private JTextArea contentArea;
    private DefaultListModel<NoteService.Note> noteModel;
    private JList<NoteService.Note> noteList;
    private JLabel statusLabel;

    
private static final Color NAVY =
        new Color(7, 21, 23);

private static final Color BLUE =
        new Color(121, 210, 232);

private static final Color BG =
        new Color(7, 21, 23);

private static final Color CARD =
        new Color(11, 37, 40);

private static final Color TEXT =
        new Color(240, 248, 250);

private static final Color SECONDARY_TEXT =
        new Color(160, 190, 198);

private static final Color BORDER =
        new Color(28, 66, 70);


    public NotesForm(Student student) {
        this.student = student;

        try {
    this.subjectService = new SubjectService();
} catch (SQLException e) {
    throw new RuntimeException("Could not initialize SubjectService", e);
}

        setTitle("AI Smart Study Planner - Notes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1100, 700);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);

        buildUI();
        loadSubjects();
        loadNotes();
    }

    private void buildUI() {
        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(BG);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(CARD);
        header.setBorder(new EmptyBorder(20, 25, 20, 25));

        JLabel heading = new JLabel("My Notes");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 26));
        heading.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Create, organize and manage your study notes");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(new Color(205, 220, 240));

        JPanel headerText = new JPanel();
        headerText.setOpaque(false);
        headerText.setLayout(new BoxLayout(headerText, BoxLayout.Y_AXIS));
        headerText.add(heading);
        headerText.add(Box.createVerticalStrut(5));
        headerText.add(subtitle);

        header.add(headerText, BorderLayout.WEST);
        root.add(header, BorderLayout.NORTH);

        JPanel main = new JPanel(new BorderLayout(18, 0));
        main.setBackground(BG);
        main.setBorder(new EmptyBorder(22, 22, 22, 22));

        // Left: saved notes
        JPanel left = new JPanel(new BorderLayout(0, 12));
        left.setBackground(CARD);
        left.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(16, 16, 16, 16)
        ));
        left.setPreferredSize(new Dimension(330, 0));

        JLabel savedTitle = new JLabel("Saved Notes");
        savedTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        savedTitle.setForeground(BLUE);
        left.add(savedTitle, BorderLayout.NORTH);

        noteModel = new DefaultListModel<>();
        noteList = new JList<>(noteModel);
        noteList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        noteList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
noteList.setBackground(CARD);
noteList.setForeground(TEXT);
noteList.setSelectionBackground(BORDER);
noteList.setSelectionForeground(Color.WHITE);
noteList.setFixedCellHeight(45);

        noteList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index,
                    boolean selected, boolean focused) {

                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, selected, focused);

                if (value instanceof NoteService.Note note) {
                    label.setText("  " + note.getTitle());
                }

                label.setBorder(new EmptyBorder(8, 8, 8, 8));
                return label;
            }
        });

        noteList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showSelectedNote();
            }
        });

        left.add(new JScrollPane(noteList), BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refresh Notes");
        styleButton(refreshButton, NAVY);
        refreshButton.addActionListener(e -> loadNotes());
        left.add(refreshButton, BorderLayout.SOUTH);

        // Right: editor
        JPanel editor = new JPanel(new BorderLayout(0, 14));
        editor.setBackground(CARD);
        editor.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel editorTitle = new JLabel("Note Details");
        editorTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        editorTitle.setForeground(BLUE);

        subjectBox = new JComboBox<>();
        subjectBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subjectBox.setBackground(CARD);
        subjectBox.setEditable(false);
        subjectBox.setOpaque(true);
        subjectBox.setBorder(BorderFactory.createLineBorder(BORDER));
        subjectBox.setUI(new javax.swing.plaf.basic.BasicComboBoxUI() {
    @Override
    public void paintCurrentValueBackground(
            java.awt.Graphics g,
            java.awt.Rectangle bounds,
            boolean hasFocus) {
        g.setColor(CARD);
        g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
    }
});
        
        subjectBox.setLightWeightPopupEnabled(true);
       
subjectBox.setForeground(TEXT);
subjectBox.setRenderer(new javax.swing.DefaultListCellRenderer() {
    @Override
    public java.awt.Component getListCellRendererComponent(
            javax.swing.JList<?> list, Object value, int index,
            boolean isSelected, boolean cellHasFocus) {

        super.getListCellRendererComponent(
                list, value, index, isSelected, cellHasFocus);

        setBackground(isSelected ? BORDER : CARD);
        setForeground(TEXT);

        return this;
    }
});



        titleField = new JTextField();
        titleField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleField.setBackground(CARD);
titleField.setForeground(TEXT);
titleField.setCaretColor(BLUE);

        contentArea = new JTextArea();
        contentArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentArea.setBackground(CARD);
contentArea.setForeground(TEXT);
contentArea.setCaretColor(BLUE);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setMargin(new Insets(10, 10, 10, 10));

        JPanel fields = new JPanel(new GridBagLayout());
fields.setBackground(CARD);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 0, 5, 0);

        addField(fields, gbc, "Subject", subjectBox);
        addField(fields, gbc, "Note Title", titleField);

        gbc.gridy++;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        JPanel contentPanel = new JPanel(new BorderLayout(0, 6));
        contentPanel.setBackground(CARD);
        JLabel contentLabel = new JLabel("Note Content");
contentLabel.setForeground(TEXT);
contentLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
contentPanel.add(contentLabel, BorderLayout.NORTH);
        contentPanel.add(new JScrollPane(contentArea), BorderLayout.CENTER);

        fields.add(contentPanel, gbc);

        JPanel formTop = new JPanel(new BorderLayout(0, 15));
        formTop.setBackground(CARD);
        formTop.add(editorTitle, BorderLayout.NORTH);
        formTop.add(fields, BorderLayout.CENTER);

        editor.add(formTop, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new GridLayout(1, 4, 10, 0));
        buttons.setBackground(CARD);

        JButton addButton = new JButton("Add New");
        JButton saveButton = new JButton("Save Note");
        JButton deleteButton = new JButton("Delete");
        JButton clearButton = new JButton("Clear");

        styleButton(addButton, BLUE);
addButton.setForeground(Color.BLACK);
        styleButton(saveButton, new Color(32, 160, 100));
        styleButton(deleteButton, new Color(220, 65, 70));
        styleButton(clearButton, NAVY);

        addButton.addActionListener(e -> clearForm());
        saveButton.addActionListener(e -> saveNote());
        deleteButton.addActionListener(e -> deleteNote());
        clearButton.addActionListener(e -> clearForm());

        buttons.add(addButton);
        buttons.add(saveButton);
        buttons.add(deleteButton);
        buttons.add(clearButton);

        statusLabel = new JLabel("Ready");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setForeground(SECONDARY_TEXT);

        JPanel bottom = new JPanel(new BorderLayout(0, 10));
        bottom.setBackground(CARD);
        bottom.add(buttons, BorderLayout.NORTH);
        bottom.add(statusLabel, BorderLayout.SOUTH);

        editor.add(bottom, BorderLayout.SOUTH);

        main.add(left, BorderLayout.WEST);
        main.add(editor, BorderLayout.CENTER);
        root.add(main, BorderLayout.CENTER);

        setContentPane(root);
    }

    private void addField(JPanel panel, GridBagConstraints gbc,
                          String labelText, JComponent component) {
        gbc.gridy++;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(TEXT);
        panel.add(label, gbc);

        gbc.gridy++;
        panel.add(component, gbc);
    }

    private void styleButton(JButton button, Color color) {
    button.setFont(new Font("Segoe UI", Font.BOLD, 13));

    if (button.getText().equals("Add New")) {
        button.setForeground(Color.BLACK);
    } else {
        button.setForeground(Color.WHITE);
    }

    button.setBackground(color);
    button.setFocusPainted(false);
    button.setBorderPainted(false);
    button.setOpaque(true);
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    button.setPreferredSize(new Dimension(110, 38));

    Color hoverColor = color.brighter();

    button.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
            button.setBackground(hoverColor);
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
            button.setBackground(color);
        }
    });
}

    private void loadSubjects() {
        subjectBox.removeAllItems();

        if (student == null) {
            statusLabel.setText("Student login is required.");
            return;
        }

        try {
            List<Subject> subjects =
                    subjectService.getSubjectsByStudentId(student.getStudentId());

            for (Subject subject : subjects) {
                subjectBox.addItem(new SubjectItem(
                        subject.getSubjectId(), subject.getSubjectName()));
            }

        } catch (SQLException ex) {
            showError("Could not load subjects:\n" + ex.getMessage());
        }
    }

    private void loadNotes() {
        if (student == null) {
            return;
        }

        try {
            int selectedId = -1;
            NoteService.Note selected = noteList.getSelectedValue();

            if (selected != null) {
                selectedId = selected.getNoteId();
            }

            noteModel.clear();

            for (NoteService.Note note :
                    noteService.getNotesByStudentId(student.getStudentId())) {
                noteModel.addElement(note);
            }

            for (int i = 0; i < noteModel.size(); i++) {
                if (noteModel.get(i).getNoteId() == selectedId) {
                    noteList.setSelectedIndex(i);
                    break;
                }
            }

            statusLabel.setText(noteModel.size() + " saved note(s)");

        } catch (SQLException ex) {
            showError("Could not load notes:\n" + ex.getMessage());
        }
    }

    private void showSelectedNote() {
        NoteService.Note note = noteList.getSelectedValue();

        if (note == null) {
            return;
        }

        titleField.setText(note.getTitle());
        contentArea.setText(note.getContent());

        for (int i = 0; i < subjectBox.getItemCount(); i++) {
            SubjectItem item = subjectBox.getItemAt(i);

            if (item.id == note.getSubjectId()) {
                subjectBox.setSelectedIndex(i);
                break;
            }
        }

        statusLabel.setText("Selected: " + note.getTitle());
    }

    private void saveNote() {
        if (student == null) {
            showError("Please log in first.");
            return;
        }

        String title = titleField.getText().trim();
        String content = contentArea.getText().trim();
        SubjectItem subject = (SubjectItem) subjectBox.getSelectedItem();

        if (subject == null) {
            showError("Please add or select a subject first.");
            return;
        }

        if (title.isEmpty() || content.isEmpty()) {
            showError("Please enter both the note title and content.");
            return;
        }

        NoteService.Note selected = noteList.getSelectedValue();

        try {
            if (selected == null) {
                noteService.addNote(
                        student.getStudentId(), subject.id, title, content);
                statusLabel.setText("Note added successfully.");
            } else {
                noteService.updateNote(
                        selected.getNoteId(), student.getStudentId(),
                        subject.id, title, content);
                statusLabel.setText("Note updated successfully.");
            }

            loadNotes();
            clearForm();

        } catch (SQLException ex) {
            showError("Could not save note:\n" + ex.getMessage());
        }
    }

    private void deleteNote() {
        NoteService.Note selected = noteList.getSelectedValue();

        if (selected == null) {
            showError("Select a note to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete \"" + selected.getTitle() + "\"?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            noteService.deleteNote(
                    selected.getNoteId(), student.getStudentId());

            clearForm();
            loadNotes();
            statusLabel.setText("Note deleted successfully.");

        } catch (SQLException ex) {
            showError("Could not delete note:\n" + ex.getMessage());
        }
    }

    private void clearForm() {
        noteList.clearSelection();
        titleField.setText("");
        contentArea.setText("");

        if (subjectBox.getItemCount() > 0) {
            subjectBox.setSelectedIndex(0);
        }

        statusLabel.setText("Ready for a new note.");
    }

  
private void showError(String message) {
    JDialog dialog = new JDialog(this, "Notice", true);
    dialog.setSize(430, 190);
    dialog.setResizable(false);
    dialog.setLocationRelativeTo(this);

    JPanel panel = new JPanel(new BorderLayout(15, 15));
    panel.setBackground(CARD);
    panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1),
            new EmptyBorder(22, 22, 18, 22)
    ));

    JLabel icon = new JLabel("!");
    icon.setFont(new Font("Segoe UI", Font.BOLD, 25));
    icon.setForeground(new Color(255, 100, 100));
    icon.setHorizontalAlignment(SwingConstants.CENTER);
    icon.setPreferredSize(new Dimension(35, 40));

    JLabel messageLabel = new JLabel(
            "<html><div style='width:290px;'>"
                    + message.replace("&", "&amp;")
                             .replace("<", "&lt;")
                             .replace(">", "&gt;")
                             .replace("\n", "<br>")
                    + "</div></html>"
    );
    messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    messageLabel.setForeground(TEXT);

    JPanel messagePanel = new JPanel(new BorderLayout(12, 0));
    messagePanel.setOpaque(false);
    messagePanel.add(icon, BorderLayout.WEST);
    messagePanel.add(messageLabel, BorderLayout.CENTER);

    JButton okButton = new JButton("OK");
    styleButton(okButton, BLUE);
    okButton.setForeground(Color.BLACK);
    okButton.setPreferredSize(new Dimension(100, 36));
    okButton.addActionListener(e -> dialog.dispose());

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
    buttonPanel.setOpaque(false);
    buttonPanel.add(okButton);

    panel.add(messagePanel, BorderLayout.CENTER);
    panel.add(buttonPanel, BorderLayout.SOUTH);

    dialog.setContentPane(panel);
    dialog.getRootPane().setDefaultButton(okButton);
    dialog.getRootPane().registerKeyboardAction(
            e -> dialog.dispose(),
            KeyStroke.getKeyStroke("ESCAPE"),
            JComponent.WHEN_IN_FOCUSED_WINDOW
    );

    dialog.getRootPane().setBorder(
            BorderFactory.createLineBorder(BORDER, 1)
    );

    dialog.setVisible(true);
}

    private static class SubjectItem {
        private final int id;
        private final String name;

        SubjectItem(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
