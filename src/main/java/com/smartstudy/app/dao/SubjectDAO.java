package com.smartstudy.app.dao;

import com.smartstudy.app.config.DatabaseConfig;
import com.smartstudy.app.model.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * AI Smart Study Planner
 * Subject Data Access Object
 */
public class SubjectDAO {

    // =========================================================
    // DATABASE CONNECTION
    // =========================================================

    private final Connection connection;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public SubjectDAO() throws SQLException {

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
    // ADD SUBJECT
    // =========================================================

    public boolean addSubject(Subject subject)
            throws SQLException {

        String sql =
                "INSERT INTO subjects " +
                "(student_id, subject_name, difficulty, " +
                "preparation_percentage, target_percentage) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    subject.getStudentId()
            );

            statement.setString(
                    2,
                    subject.getSubjectName()
            );

            statement.setString(
                    3,
                    subject.getDifficulty()
            );

            statement.setDouble(
                    4,
                    subject.getPreparationPercentage()
            );

            statement.setDouble(
                    5,
                    subject.getTargetPercentage()
            );

            int rows =
                    statement.executeUpdate();

            return rows > 0;
        }
    }


    // =========================================================
    // UPDATE SUBJECT
    // =========================================================

    public boolean updateSubject(Subject subject)
            throws SQLException {

        String sql =
                "UPDATE subjects SET " +
                "subject_name = ?, " +
                "difficulty = ?, " +
                "preparation_percentage = ?, " +
                "target_percentage = ? " +
                "WHERE subject_id = ? " +
                "AND student_id = ?";

        try (
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    subject.getSubjectName()
            );

            statement.setString(
                    2,
                    subject.getDifficulty()
            );

            statement.setDouble(
                    3,
                    subject.getPreparationPercentage()
            );

            statement.setDouble(
                    4,
                    subject.getTargetPercentage()
            );

            statement.setInt(
                    5,
                    subject.getSubjectId()
            );

            statement.setInt(
                    6,
                    subject.getStudentId()
            );

            int rows =
                    statement.executeUpdate();

            return rows > 0;
        }
    }


    // =========================================================
    // DELETE SUBJECT
    // =========================================================

    public boolean deleteSubject(
            int subjectId,
            int studentId
    ) throws SQLException {

        String sql =
                "DELETE FROM subjects " +
                "WHERE subject_id = ? " +
                "AND student_id = ?";

        try (
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    subjectId
            );

            statement.setInt(
                    2,
                    studentId
            );

            int rows =
                    statement.executeUpdate();

            return rows > 0;
        }
    }


    // =========================================================
    // GET SUBJECTS BY STUDENT ID
    // =========================================================

    public List<Subject> getSubjectsByStudentId(
            int studentId
    ) throws SQLException {

        String sql =
                "SELECT subject_id, student_id, subject_name, " +
                "difficulty, preparation_percentage, " +
                "target_percentage " +
                "FROM subjects " +
                "WHERE student_id = ? " +
                "ORDER BY subject_id";

        List<Subject> subjects =
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

                    Subject subject =
                            new Subject();

                    subject.setSubjectId(
                            resultSet.getInt(
                                    "subject_id"
                            )
                    );

                    subject.setStudentId(
                            resultSet.getInt(
                                    "student_id"
                            )
                    );

                    subject.setSubjectName(
                            resultSet.getString(
                                    "subject_name"
                            )
                    );

                    subject.setDifficulty(
                            resultSet.getString(
                                    "difficulty"
                            )
                    );

                    subject.setPreparationPercentage(
                            resultSet.getDouble(
                                    "preparation_percentage"
                            )
                    );

                    subject.setTargetPercentage(
                            resultSet.getDouble(
                                    "target_percentage"
                            )
                    );

                    subjects.add(subject);
                }
            }
        }

        return subjects;
    }
}