
package com.smartstudy.app.dao;

import com.smartstudy.app.config.DatabaseConfig;
import com.smartstudy.app.model.Progress;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * AI Smart Study Planner
 * Progress Data Access Object
 */
public class ProgressDAO {

    private final Connection connection;

    public ProgressDAO() throws SQLException {
        connection = DatabaseConfig.getConnection();
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean addProgress(Progress progress)
            throws SQLException {

        String sql =
                "INSERT INTO progress " +
                "(student_id, subject_id, completed_hours, " +
                "progress_percentage, last_updated) " +
                "VALUES (?, ?, ?, ?, SYSDATE)";

        try (PreparedStatement ps =
                     connection.prepareStatement(sql)) {

            ps.setInt(1, progress.getStudentId());
            ps.setInt(2, progress.getSubjectId());
            ps.setDouble(3, progress.getCompletedHours());
            ps.setDouble(4, progress.getProgressPercentage());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateProgress(Progress progress)
            throws SQLException {

        String sql =
                "UPDATE progress " +
                "SET completed_hours = ?, " +
                "progress_percentage = ?, " +
                "last_updated = SYSDATE " +
                "WHERE progress_id = ? AND student_id = ?";

        try (PreparedStatement ps =
                     connection.prepareStatement(sql)) {

            ps.setDouble(1, progress.getCompletedHours());
            ps.setDouble(2, progress.getProgressPercentage());
            ps.setInt(3, progress.getProgressId());
            ps.setInt(4, progress.getStudentId());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteProgress(int progressId, int studentId)
            throws SQLException {

        String sql =
                "DELETE FROM progress " +
                "WHERE progress_id = ? AND student_id = ?";

        try (PreparedStatement ps =
                     connection.prepareStatement(sql)) {

            ps.setInt(1, progressId);
            ps.setInt(2, studentId);

            return ps.executeUpdate() > 0;
        }
    }

    public Progress getProgressById(
            int progressId,
            int studentId
    ) throws SQLException {

        String sql =
                "SELECT progress_id, student_id, subject_id, " +
                "completed_hours, progress_percentage " +
                "FROM progress " +
                "WHERE progress_id = ? AND student_id = ?";

        try (PreparedStatement ps =
                     connection.prepareStatement(sql)) {

            ps.setInt(1, progressId);
            ps.setInt(2, studentId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapProgress(rs);
                }
            }
        }

        return null;
    }

    public List<Progress> getProgressByStudentId(
            int studentId
    ) throws SQLException {

        List<Progress> progressList = new ArrayList<>();

        String sql =
                "SELECT progress_id, student_id, subject_id, " +
                "completed_hours, progress_percentage " +
                "FROM progress " +
                "WHERE student_id = ? " +
                "ORDER BY progress_id DESC";

        try (PreparedStatement ps =
                     connection.prepareStatement(sql)) {

            ps.setInt(1, studentId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    progressList.add(mapProgress(rs));
                }
            }
        }

        return progressList;
    }

    public Progress getProgressBySubjectId(
            int studentId,
            int subjectId
    ) throws SQLException {

        String sql =
                "SELECT progress_id, student_id, subject_id, " +
                "completed_hours, progress_percentage " +
                "FROM progress " +
                "WHERE student_id = ? AND subject_id = ?";

        try (PreparedStatement ps =
                     connection.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, subjectId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapProgress(rs);
                }
            }
        }

        return null;
    }

    private Progress mapProgress(ResultSet rs)
            throws SQLException {

        Progress progress = new Progress();

        progress.setProgressId(rs.getInt("progress_id"));
        progress.setStudentId(rs.getInt("student_id"));
        progress.setSubjectId(rs.getInt("subject_id"));
        progress.setCompletedHours(
                rs.getDouble("completed_hours")
        );
        progress.setProgressPercentage(
                rs.getDouble("progress_percentage")
        );

        return progress;
    }
}
