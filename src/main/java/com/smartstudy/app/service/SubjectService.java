package com.smartstudy.app.service;

import com.smartstudy.app.dao.SubjectDAO;
import com.smartstudy.app.model.Subject;

import java.sql.SQLException;
import java.util.List;

/**
 * AI Smart Study Planner
 * Subject Service
 */
public class SubjectService {

    // =========================================================
    // DAO
    // =========================================================

    private final SubjectDAO subjectDAO;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public SubjectService() throws SQLException {

        subjectDAO =
                new SubjectDAO();
    }


    // =========================================================
    // ADD SUBJECT
    // =========================================================

    public boolean addSubject(Subject subject)
            throws SQLException {

        return subjectDAO.addSubject(
                subject
        );
    }


    // =========================================================
    // UPDATE SUBJECT
    // =========================================================

    public boolean updateSubject(Subject subject)
            throws SQLException {

        return subjectDAO.updateSubject(
                subject
        );
    }


    // =========================================================
    // DELETE SUBJECT
    // =========================================================

    public boolean deleteSubject(
            int subjectId,
            int studentId
    ) throws SQLException {

        return subjectDAO.deleteSubject(
                subjectId,
                studentId
        );
    }


    // =========================================================
    // GET SUBJECTS BY STUDENT ID
    // =========================================================

    public List<Subject> getSubjectsByStudentId(
            int studentId
    ) throws SQLException {

        return subjectDAO.getSubjectsByStudentId(
                studentId
        );
    }


    // =========================================================
    // GET DAO
    // =========================================================

    public SubjectDAO getSubjectDAO() {

        return subjectDAO;
    }
}