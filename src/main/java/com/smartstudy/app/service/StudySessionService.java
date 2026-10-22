package com.smartstudy.app.service;

import com.smartstudy.app.dao.StudySessionDAO;
import com.smartstudy.app.model.StudySession;

import java.sql.SQLException;
import java.util.List;

/**
 * AI Smart Study Planner
 * Study Session Service
 */
public class StudySessionService {

    private final StudySessionDAO studySessionDAO;

    public StudySessionService() throws SQLException {

        studySessionDAO =
                new StudySessionDAO();
    }

    // =========================================================
    // ADD SESSION
    // =========================================================

    public boolean addStudySession(
            StudySession session
    ) throws SQLException {

        return studySessionDAO.addStudySession(
                session
        );
    }

    // =========================================================
    // UPDATE SESSION
    // =========================================================

    public boolean updateStudySession(
            StudySession session
    ) throws SQLException {

        return studySessionDAO.updateStudySession(
                session
        );
    }

    // =========================================================
    // DELETE SESSION
    // =========================================================

    public boolean deleteStudySession(
            int sessionId,
            int studentId
    ) throws SQLException {

        return studySessionDAO.deleteStudySession(
                sessionId,
                studentId
        );
    }

    // =========================================================
    // GET SESSION BY ID
    // =========================================================

    public StudySession getStudySessionById(
            int sessionId
    ) throws SQLException {

        return studySessionDAO.getStudySessionById(
                sessionId
        );
    }

    // =========================================================
    // GET STUDENT SESSIONS
    // =========================================================

    public List<StudySession> getStudySessionsByStudentId(
            int studentId
    ) throws SQLException {

        return studySessionDAO.getStudySessionsByStudentId(
                studentId
        );
    }

    
    // =========================================================
    // GET TODAY'S STUDY SESSIONS
    // =========================================================

    public List<StudySession> getTodayStudySessionsByStudentId(
            int studentId
    ) throws SQLException {

        return studySessionDAO.getTodayStudySessionsByStudentId(
                studentId
        );
    }


    // =========================================================
    // GET SUBJECT SESSIONS
    // =========================================================

    public List<StudySession> getStudySessionsBySubjectId(
            int subjectId
    ) throws SQLException {

        return studySessionDAO.getStudySessionsBySubjectId(
                subjectId
        );
    }

    // =========================================================
    // GET DAO
    // =========================================================

    public StudySessionDAO getStudySessionDAO() {

        return studySessionDAO;
    }
}