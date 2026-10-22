package com.smartstudy.app.dao;

import com.smartstudy.app.config.DatabaseConfig;
import com.smartstudy.app.model.Exam;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * AI Smart Study Planner
 * Exam Data Access Object
 */
public class ExamDAO {

    // =========================================================
    // DATABASE CONNECTION
    // =========================================================

    private final Connection connection;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public ExamDAO() throws SQLException {

        connection =
                DatabaseConfig.getConnection();
    }


    // =========================================================
    // GET CONNECTION
    // =========================================================

    public Connection getConnection() {

        return connection;
    }


    // =========================================================
    // ADD EXAM
    // =========================================================

    public boolean addExam(
            Exam exam
    ) throws SQLException {

        String sql =
                "INSERT INTO exams " +
                "(subject_id, exam_name, exam_date, " +
                "exam_time, exam_type) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    exam.getSubjectId()
            );

            statement.setString(
                    2,
                    exam.getExamName()
            );

            statement.setDate(
                    3,
                    Date.valueOf(
                            exam.getExamDate()
                    )
            );

            statement.setString(
                    4,
                    exam.getExamTime()
            );

            statement.setString(
                    5,
                    exam.getExamType()
            );

            int rows =
                    statement.executeUpdate();

            return rows > 0;
        }
    }


    // =========================================================
    // UPDATE EXAM
    // =========================================================

    public boolean updateExam(
            Exam exam
    ) throws SQLException {

        String sql =
                "UPDATE exams SET " +
                "subject_id = ?, " +
                "exam_name = ?, " +
                "exam_date = ?, " +
                "exam_time = ?, " +
                "exam_type = ? " +
                "WHERE exam_id = ?";

        try (
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    exam.getSubjectId()
            );

            statement.setString(
                    2,
                    exam.getExamName()
            );

            statement.setDate(
                    3,
                    Date.valueOf(
                            exam.getExamDate()
                    )
            );

            statement.setString(
                    4,
                    exam.getExamTime()
            );

            statement.setString(
                    5,
                    exam.getExamType()
            );

            statement.setInt(
                    6,
                    exam.getExamId()
            );

            int rows =
                    statement.executeUpdate();

            return rows > 0;
        }
    }


    // =========================================================
    // DELETE EXAM
    // =========================================================

    public boolean deleteExam(
            int examId
    ) throws SQLException {

        String sql =
                "DELETE FROM exams " +
                "WHERE exam_id = ?";

        try (
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    examId
            );

            int rows =
                    statement.executeUpdate();

            return rows > 0;
        }
    }


    // =========================================================
    // GET EXAM BY ID
    // =========================================================

    public Exam getExamById(
            int examId
    ) throws SQLException {

        String sql =
                "SELECT exam_id, subject_id, exam_name, " +
                "exam_date, exam_time, exam_type " +
                "FROM exams " +
                "WHERE exam_id = ?";

        try (
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    examId
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                if (resultSet.next()) {

                    return createExamFromResultSet(
                            resultSet
                    );
                }
            }
        }

        return null;
    }


    // =========================================================
    // GET EXAMS BY SUBJECT
    // =========================================================

    public List<Exam> getExamsBySubjectId(
            int subjectId
    ) throws SQLException {

        String sql =
                "SELECT exam_id, subject_id, exam_name, " +
                "exam_date, exam_time, exam_type " +
                "FROM exams " +
                "WHERE subject_id = ? " +
                "ORDER BY exam_date, exam_time";

        List<Exam> exams =
                new ArrayList<>();

        try (
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    subjectId
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                while (resultSet.next()) {

                    exams.add(
                            createExamFromResultSet(
                                    resultSet
                            )
                    );
                }
            }
        }

        return exams;
    }


    // =========================================================
    // GET EXAMS BY STUDENT
    // =========================================================

    public List<Exam> getExamsByStudentId(
            int studentId
    ) throws SQLException {

        String sql =
                "SELECT e.exam_id, " +
                "e.subject_id, " +
                "e.exam_name, " +
                "e.exam_date, " +
                "e.exam_time, " +
                "e.exam_type " +
                "FROM exams e " +
                "INNER JOIN subjects s " +
                "ON e.subject_id = s.subject_id " +
                "WHERE s.student_id = ? " +
                "ORDER BY e.exam_date, e.exam_time";

        List<Exam> exams =
                new ArrayList<>();

        try (
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    studentId
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                while (resultSet.next()) {

                    exams.add(
                            createExamFromResultSet(
                                    resultSet
                            )
                    );
                }
            }
        }

        return exams;
    }


    // =========================================================
    // CREATE EXAM FROM RESULT SET
    // =========================================================

    private Exam createExamFromResultSet(
            ResultSet resultSet
    ) throws SQLException {

        Exam exam =
                new Exam();

        exam.setExamId(
                resultSet.getInt(
                        "exam_id"
                )
        );

        exam.setSubjectId(
                resultSet.getInt(
                        "subject_id"
                )
        );

        exam.setExamName(
                resultSet.getString(
                        "exam_name"
                )
        );

        Date examDate =
                resultSet.getDate(
                        "exam_date"
                );

        if (examDate != null) {

            exam.setExamDate(
                    examDate.toString()
            );
        }

        exam.setExamTime(
                resultSet.getString(
                        "exam_time"
                )
        );

        exam.setExamType(
                resultSet.getString(
                        "exam_type"
                )
        );

        return exam;
    }
}