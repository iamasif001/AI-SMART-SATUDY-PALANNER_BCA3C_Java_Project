
package com.smartstudy.app.service;

import com.smartstudy.app.dao.ProgressDAO;
import com.smartstudy.app.model.Progress;

import java.sql.SQLException;
import java.util.List;

/**
 * AI Smart Study Planner
 * Progress Service
 */
public class ProgressService {

    private final ProgressDAO progressDAO;

    public ProgressService() throws SQLException {
        progressDAO = new ProgressDAO();
    }

    public ProgressDAO getProgressDAO() {
        return progressDAO;
    }

    public boolean addProgress(Progress progress)
            throws SQLException {

        return progressDAO.addProgress(progress);
    }

    public boolean updateProgress(Progress progress)
            throws SQLException {

        return progressDAO.updateProgress(progress);
    }

    public boolean deleteProgress(
            int progressId,
            int studentId
    ) throws SQLException {

        return progressDAO.deleteProgress(
                progressId,
                studentId
        );
    }

    public Progress getProgressById(
            int progressId,
            int studentId
    ) throws SQLException {

        return progressDAO.getProgressById(
                progressId,
                studentId
        );
    }

    public List<Progress> getProgressByStudentId(
            int studentId
    ) throws SQLException {

        return progressDAO.getProgressByStudentId(
                studentId
        );
    }

    public Progress getProgressBySubjectId(
            int studentId,
            int subjectId
    ) throws SQLException {

        return progressDAO.getProgressBySubjectId(
                studentId,
                subjectId
        );
    }
}
