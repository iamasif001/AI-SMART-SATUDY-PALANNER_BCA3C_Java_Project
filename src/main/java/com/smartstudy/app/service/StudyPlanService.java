package com.smartstudy.app.service;

import com.smartstudy.app.dao.StudyPlanDAO;
import com.smartstudy.app.model.StudyPlan;

import java.sql.SQLException;
import java.util.List;

/**
 * AI Smart Study Planner
 * Study Plan Service
 */
public class StudyPlanService {

    private final StudyPlanDAO studyPlanDAO;

    public StudyPlanService() throws SQLException {
        studyPlanDAO = new StudyPlanDAO();
    }

    public StudyPlanDAO getStudyPlanDAO() {
        return studyPlanDAO;
    }

    // Add Study Plan
    public boolean addStudyPlan(StudyPlan plan) throws SQLException {
        return studyPlanDAO.addStudyPlan(plan);
    }

    // Get Study Plans
    public List<StudyPlan> getStudyPlansByStudentId(int studentId)
            throws SQLException {
        return studyPlanDAO.getStudyPlansByStudentId(studentId);
    }

    // Get One Study Plan
    public StudyPlan getStudyPlanById(int planId, int studentId)
            throws SQLException {
        return studyPlanDAO.getStudyPlanById(planId, studentId);
    }

    // Edit Study Plan
    public boolean updateStudyPlan(StudyPlan plan) throws SQLException {
        return studyPlanDAO.updateStudyPlan(plan);
    }

    // Update Status
    public boolean updateStudyPlanStatus(
            int planId, int studentId, String status) throws SQLException {
        return studyPlanDAO.updateStudyPlanStatus(
                planId, studentId, status);
    }

    // Delete Study Plan
    public boolean deleteStudyPlan(int planId, int studentId)
            throws SQLException {
        return studyPlanDAO.deleteStudyPlan(planId, studentId);
    }
}
