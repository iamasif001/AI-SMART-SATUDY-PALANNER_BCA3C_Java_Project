package com.smartstudy.app.service;

import com.smartstudy.app.dao.ExamDAO;
import com.smartstudy.app.model.Exam;

import java.sql.SQLException;
import java.util.List;

/**
 * AI Smart Study Planner
 * Exam Service
 */
public class ExamService {

    // =========================================================
    // DAO
    // =========================================================

    private final ExamDAO examDAO;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public ExamService() throws SQLException {

        examDAO =
                new ExamDAO();
    }


    // =========================================================
    // ADD EXAM
    // =========================================================

    public boolean addExam(
            Exam exam
    ) throws SQLException {

        return examDAO.addExam(
                exam
        );
    }


    // =========================================================
    // UPDATE EXAM
    // =========================================================

    public boolean updateExam(
            Exam exam
    ) throws SQLException {

        return examDAO.updateExam(
                exam
        );
    }


    // =========================================================
    // DELETE EXAM
    // =========================================================

    public boolean deleteExam(
            int examId
    ) throws SQLException {

        return examDAO.deleteExam(
                examId
        );
    }


    // =========================================================
    // GET EXAM BY ID
    // =========================================================

    public Exam getExamById(
            int examId
    ) throws SQLException {

        return examDAO.getExamById(
                examId
        );
    }


    // =========================================================
    // GET EXAMS BY SUBJECT
    // =========================================================

    public List<Exam> getExamsBySubjectId(
            int subjectId
    ) throws SQLException {

        return examDAO.getExamsBySubjectId(
                subjectId
        );
    }


    // =========================================================
    // GET EXAMS BY STUDENT
    // =========================================================

    public List<Exam> getExamsByStudentId(
            int studentId
    ) throws SQLException {

        return examDAO.getExamsByStudentId(
                studentId
        );
    }


    // =========================================================
    // GET DAO
    // =========================================================

    public ExamDAO getExamDAO() {

        return examDAO;
    }
}