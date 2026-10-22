
package com.smartstudy.app.service;

import com.smartstudy.app.config.DatabaseConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.List;

public class NoteService {

    public static class Note {
        private int noteId;
        private int subjectId;
        private String title;
        private String content;

        public Note(int noteId, int subjectId,
                    String title, String content) {
            this.noteId = noteId;
            this.subjectId = subjectId;
            this.title = title;
            this.content = content;
        }

        public int getNoteId() { return noteId; }
        public int getSubjectId() { return subjectId; }
        public String getTitle() { return title; }
        public String getContent() { return content; }
    }

    public List<Note> getNotesByStudentId(int studentId)
            throws SQLException {

        List<Note> notes = new ArrayList<>();

        String sql = """
            SELECT NOTE_ID, SUBJECT_ID, TITLE, NOTE_CONTENT
            FROM NOTES
            WHERE STUDENT_ID = ?
            ORDER BY CREATED_AT DESC NULLS LAST, NOTE_ID DESC
            """;

        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Clob clob = rs.getClob("NOTE_CONTENT");
                    String content = clob == null
                            ? ""
                            : clob.getSubString(1,
                                (int) clob.length());

                    notes.add(new Note(
                            rs.getInt("NOTE_ID"),
                            rs.getInt("SUBJECT_ID"),
                            rs.getString("TITLE"),
                            content
                    ));
                }
            }
        }

        return notes;
    }

    public void addNote(int studentId, int subjectId,
                        String title, String content)
            throws SQLException {

        String sql = """
            INSERT INTO NOTES
                (STUDENT_ID, SUBJECT_ID, TITLE,
                 NOTE_CONTENT, CREATED_AT)
            VALUES (?, ?, ?, ?, SYSDATE)
            """;

        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, subjectId);
            ps.setString(3, title);
            ps.setString(4, content);
            ps.executeUpdate();
        }
    }

    public void updateNote(int noteId, int studentId,
                           int subjectId, String title,
                           String content)
            throws SQLException {

        String sql = """
            UPDATE NOTES
            SET SUBJECT_ID = ?, TITLE = ?, NOTE_CONTENT = ?
            WHERE NOTE_ID = ? AND STUDENT_ID = ?
            """;

        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, subjectId);
            ps.setString(2, title);
            ps.setString(3, content);
            ps.setInt(4, noteId);
            ps.setInt(5, studentId);

            if (ps.executeUpdate() == 0) {
                throw new SQLException("Note not found.");
            }
        }
    }

    public void deleteNote(int noteId, int studentId)
            throws SQLException {

        String sql = """
            DELETE FROM NOTES
            WHERE NOTE_ID = ? AND STUDENT_ID = ?
            """;

        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, noteId);
            ps.setInt(2, studentId);

            if (ps.executeUpdate() == 0) {
                throw new SQLException("Note not found.");
            }
        }
    }
}
