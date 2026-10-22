
package com.smartstudy.app.dao;

import com.smartstudy.app.config.DatabaseConfig;
import com.smartstudy.app.model.StudyPlan;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudyPlanDAO {

    private final Connection connection;

    public StudyPlanDAO() throws SQLException {
        connection = DatabaseConfig.getConnection();
    }

    public Connection getConnection() {
        return connection;
    }

    // ADD STUDY PLAN
    public boolean addStudyPlan(StudyPlan plan)
            throws SQLException {

        String sql =
                "INSERT INTO STUDY_PLANS " +
                "(student_id, subject_id, study_date, " +
                "study_hours, task, status) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps =
                     connection.prepareStatement(sql)) {

            ps.setInt(1, plan.getStudentId());
            ps.setInt(2, plan.getSubjectId());
            ps.setDate(3, Date.valueOf(plan.getStudyDate()));
            ps.setDouble(4, plan.getStudyHours());
            ps.setString(5, plan.getTask());
            ps.setString(6, plan.getStatus());

            return ps.executeUpdate() > 0;
        }
    }

    // GET ALL PLANS FOR A STUDENT
    public List<StudyPlan> getStudyPlansByStudentId(
            int studentId) throws SQLException {

        List<StudyPlan> plans = new ArrayList<>();

        String sql =
                "SELECT plan_id, student_id, subject_id, " +
                "study_date, study_hours, task, status " +
                "FROM STUDY_PLANS " +
                "WHERE student_id = ? " +
                "ORDER BY study_date DESC, plan_id DESC";

        try (PreparedStatement ps =
                     connection.prepareStatement(sql)) {

            ps.setInt(1, studentId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    plans.add(mapStudyPlan(rs));
                }
            }
        }

        return plans;
    }

    // GET ONE PLAN
    public StudyPlan getStudyPlanById(
            int planId, int studentId) throws SQLException {

        String sql =
                "SELECT plan_id, student_id, subject_id, " +
                "study_date, study_hours, task, status " +
                "FROM STUDY_PLANS " +
                "WHERE plan_id = ? AND student_id = ?";

        try (PreparedStatement ps =
                     connection.prepareStatement(sql)) {

            ps.setInt(1, planId);
            ps.setInt(2, studentId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapStudyPlan(rs);
                }
            }
        }

        return null;
    }

    // UPDATE STUDY PLAN
    public boolean updateStudyPlan(StudyPlan plan)
            throws SQLException {

        String sql =
                "UPDATE STUDY_PLANS " +
                "SET subject_id = ?, study_date = ?, " +
                "study_hours = ?, task = ?, status = ? " +
                "WHERE plan_id = ? AND student_id = ?";

        try (PreparedStatement ps =
                     connection.prepareStatement(sql)) {

            ps.setInt(1, plan.getSubjectId());
            ps.setDate(2, Date.valueOf(plan.getStudyDate()));
            ps.setDouble(3, plan.getStudyHours());
            ps.setString(4, plan.getTask());
            ps.setString(5, plan.getStatus());
            ps.setInt(6, plan.getPlanId());
            ps.setInt(7, plan.getStudentId());

            return ps.executeUpdate() > 0;
        }
    }

    // UPDATE STATUS ONLY
    public boolean updateStudyPlanStatus(
            int planId, int studentId, String status)
            throws SQLException {

        String sql =
                "UPDATE STUDY_PLANS " +
                "SET status = ? " +
                "WHERE plan_id = ? AND student_id = ?";

        try (PreparedStatement ps =
                     connection.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, planId);
            ps.setInt(3, studentId);

            return ps.executeUpdate() > 0;
        }
    }

    // DELETE STUDY PLAN
    public boolean deleteStudyPlan(
            int planId, int studentId) throws SQLException {

        String sql =
                "DELETE FROM STUDY_PLANS " +
                "WHERE plan_id = ? AND student_id = ?";

        try (PreparedStatement ps =
                     connection.prepareStatement(sql)) {

            ps.setInt(1, planId);
            ps.setInt(2, studentId);

            return ps.executeUpdate() > 0;
        }
    }

    // MAP RESULT SET TO MODEL
    private StudyPlan mapStudyPlan(ResultSet rs)
            throws SQLException {

        StudyPlan plan = new StudyPlan();

        plan.setPlanId(rs.getInt("plan_id"));
        plan.setStudentId(rs.getInt("student_id"));
        plan.setSubjectId(rs.getInt("subject_id"));

        Date date = rs.getDate("study_date");
        if (date != null) {
            plan.setStudyDate(date.toString());
        }

        plan.setStudyHours(rs.getDouble("study_hours"));
        plan.setTask(rs.getString("task"));
        plan.setStatus(rs.getString("status"));

        return plan;
    }
}
