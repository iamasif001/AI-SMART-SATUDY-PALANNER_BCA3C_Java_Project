
package com.smartstudy.app.dao;

import com.smartstudy.app.config.DatabaseConfig;
import com.smartstudy.app.model.StudySession;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * AI Smart Study Planner
 * Study Session Data Access Object
 */
public class StudySessionDAO {

    private final Connection connection;

    public StudySessionDAO() throws SQLException {
        connection = DatabaseConfig.getConnection();
    }

    public Connection getConnection() {
        return connection;
    }

    // =========================================================
    // ADD SESSION
    // =========================================================

    public boolean addStudySession(StudySession session)
            throws SQLException {

        String sql =
                "INSERT INTO study_sessions " +
                "(student_id, subject_id, session_date, " +
                "duration_minutes, topic_covered, notes) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, session.getStudentId());
            statement.setInt(2, session.getSubjectId());
            statement.setDate(
                    3, Date.valueOf(session.getSessionDate()));
            statement.setInt(4, session.getDurationMinutes());
            statement.setString(5, session.getTopicCovered());
            statement.setString(6, session.getNotes());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                syncCompletedHours(
                        session.getStudentId(),
                        session.getSubjectId());
                return true;
            }

            return false;
        }
    }

    // =========================================================
    // UPDATE SESSION
    // =========================================================

    public boolean updateStudySession(StudySession session)
            throws SQLException {

        int oldSubjectId = getSubjectIdForSession(
                session.getSessionId(),
                session.getStudentId());

        if (oldSubjectId == -1) {
            return false;
        }

        String sql =
                "UPDATE study_sessions " +
                "SET subject_id = ?, " +
                "session_date = ?, " +
                "duration_minutes = ?, " +
                "topic_covered = ?, " +
                "notes = ? " +
                "WHERE session_id = ? " +
                "AND student_id = ?";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, session.getSubjectId());
            statement.setDate(
                    2, Date.valueOf(session.getSessionDate()));
            statement.setInt(3, session.getDurationMinutes());
            statement.setString(4, session.getTopicCovered());
            statement.setString(5, session.getNotes());
            statement.setInt(6, session.getSessionId());
            statement.setInt(7, session.getStudentId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                // Recalculate the old subject if the session moved.
                syncCompletedHours(
                        session.getStudentId(),
                        oldSubjectId);

                // Recalculate the updated subject.
                if (session.getSubjectId() != oldSubjectId) {
                    syncCompletedHours(
                            session.getStudentId(),
                            session.getSubjectId());
                }

                return true;
            }

            return false;
        }
    }

    // =========================================================
    // DELETE SESSION
    // =========================================================

    public boolean deleteStudySession(
            int sessionId,
            int studentId
    ) throws SQLException {

        int subjectId = getSubjectIdForSession(
                sessionId, studentId);

        if (subjectId == -1) {
            return false;
        }

        String sql =
                "DELETE FROM study_sessions " +
                "WHERE session_id = ? " +
                "AND student_id = ?";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, sessionId);
            statement.setInt(2, studentId);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                syncCompletedHours(studentId, subjectId);
                return true;
            }

            return false;
        }
    }

    // =========================================================
    // GET SUBJECT ID FOR A SESSION
    // =========================================================

    private int getSubjectIdForSession(
            int sessionId,
            int studentId
    ) throws SQLException {

        String sql =
                "SELECT subject_id " +
                "FROM study_sessions " +
                "WHERE session_id = ? " +
                "AND student_id = ?";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, sessionId);
            statement.setInt(2, studentId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getInt("subject_id");
                }
            }
        }

        return -1;
    }

    // =========================================================
    // AUTOMATICALLY SYNC COMPLETED HOURS WITH PROGRESS
    // =========================================================

    private void syncCompletedHours(
            int studentId,
            int subjectId
    ) throws SQLException {

        String totalSql =
                "SELECT NVL(SUM(duration_minutes), 0) / 60.0 " +
                "AS completed_hours " +
                "FROM study_sessions " +
                "WHERE student_id = ? " +
                "AND subject_id = ?";

        double completedHours;

        try (PreparedStatement statement =
                     connection.prepareStatement(totalSql)) {

            statement.setInt(1, studentId);
            statement.setInt(2, subjectId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                resultSet.next();
                completedHours =
                        resultSet.getDouble("completed_hours");
            }
        }

        // Update hours without changing the progress percentage.
        String updateSql =
                "UPDATE progress " +
                "SET completed_hours = ?, " +
                "last_updated = SYSDATE " +
                "WHERE student_id = ? " +
                "AND subject_id = ?";

        int updatedRows;

        try (PreparedStatement statement =
                     connection.prepareStatement(updateSql)) {

            statement.setDouble(1, completedHours);
            statement.setInt(2, studentId);
            statement.setInt(3, subjectId);

            updatedRows = statement.executeUpdate();
        }

        // Create a progress record if this subject has none.
        if (updatedRows == 0) {

            String insertSql =
                    "INSERT INTO progress " +
                    "(student_id, subject_id, completed_hours, " +
                    "progress_percentage, last_updated) " +
                    "VALUES (?, ?, ?, 0, SYSDATE)";

            try (PreparedStatement statement =
                         connection.prepareStatement(insertSql)) {

                statement.setInt(1, studentId);
                statement.setInt(2, subjectId);
                statement.setDouble(3, completedHours);

                statement.executeUpdate();
            }
        }
    }

    // =========================================================
    // GET SESSION BY ID
    // =========================================================

    public StudySession getStudySessionById(int sessionId)
            throws SQLException {

        String sql =
                "SELECT session_id, student_id, subject_id, " +
                "session_date, duration_minutes, " +
                "topic_covered, notes " +
                "FROM study_sessions " +
                "WHERE session_id = ?";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, sessionId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {
                    return createStudySessionFromResultSet(
                            resultSet);
                }
            }
        }

        return null;
    }

    // =========================================================
    // GET SESSIONS BY STUDENT
    // =========================================================

    public List<StudySession> getStudySessionsByStudentId(
            int studentId
    ) throws SQLException {

        List<StudySession> sessions = new ArrayList<>();

        String sql =
                "SELECT ss.session_id, ss.student_id, " +
                "ss.subject_id, ss.session_date, " +
                "ss.duration_minutes, ss.topic_covered, ss.notes " +
                "FROM study_sessions ss " +
                "INNER JOIN subjects s " +
                "ON ss.subject_id = s.subject_id " +
                "WHERE ss.student_id = ? " +
                "AND s.student_id = ? " +
                "ORDER BY ss.session_date DESC, " +
                "ss.session_id DESC";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);
            statement.setInt(2, studentId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {
                    sessions.add(
                            createStudySessionFromResultSet(
                                    resultSet));
                }
            }
        }

        return sessions;
    }
    
    // =========================================================
    // GET TODAY'S SESSIONS BY STUDENT
    // =========================================================

    public List<StudySession> getTodayStudySessionsByStudentId(
            int studentId
    ) throws SQLException {

        List<StudySession> sessions = new ArrayList<>();

        String sql =
                "SELECT ss.session_id, ss.student_id, " +
                "ss.subject_id, ss.session_date, " +
                "ss.duration_minutes, ss.topic_covered, ss.notes " +
                "FROM study_sessions ss " +
                "INNER JOIN subjects s " +
                "ON ss.subject_id = s.subject_id " +
                "WHERE ss.student_id = ? " +
                "AND s.student_id = ? " +
               
"AND ss.session_date >= TRUNC(SYSDATE) " +

                "ORDER BY ss.session_id DESC";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);
            statement.setInt(2, studentId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    sessions.add(
                            createStudySessionFromResultSet(resultSet)
                    );
                }
            }
        }

        return sessions;
    }


    // =========================================================
    // GET SESSIONS BY SUBJECT
    // =========================================================

    public List<StudySession> getStudySessionsBySubjectId(
            int subjectId
    ) throws SQLException {

        List<StudySession> sessions = new ArrayList<>();

        String sql =
                "SELECT session_id, student_id, subject_id, " +
                "session_date, duration_minutes, " +
                "topic_covered, notes " +
                "FROM study_sessions " +
                "WHERE subject_id = ? " +
                "ORDER BY session_date DESC, session_id DESC";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, subjectId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {
                    sessions.add(
                            createStudySessionFromResultSet(
                                    resultSet));
                }
            }
        }

        return sessions;
    }

    // =========================================================
    // CREATE OBJECT FROM RESULT SET
    // =========================================================

    private StudySession createStudySessionFromResultSet(
            ResultSet resultSet
    ) throws SQLException {

        StudySession session = new StudySession();

        session.setSessionId(
                resultSet.getInt("session_id"));
        session.setStudentId(
                resultSet.getInt("student_id"));
        session.setSubjectId(
                resultSet.getInt("subject_id"));

        Date sessionDate = resultSet.getDate("session_date");

        if (sessionDate != null) {
            session.setSessionDate(sessionDate.toString());
        }

        session.setDurationMinutes(
                resultSet.getInt("duration_minutes"));
        session.setTopicCovered(
                resultSet.getString("topic_covered"));
        session.setNotes(
                resultSet.getString("notes"));

        return session;
    }
}
