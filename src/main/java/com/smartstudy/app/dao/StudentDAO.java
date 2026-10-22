package com.smartstudy.app.dao;

import com.smartstudy.app.config.DatabaseConfig;
import com.smartstudy.app.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * AI Smart Study Planner
 * Student Data Access Object
 */
public class StudentDAO {

    // =========================================================
// REGISTER STUDENT
// =========================================================

public boolean registerStudent(Student student) {

    String sql =
            "INSERT INTO students " +
            "(name, roll_no, email, password, semester) " +
            "VALUES (?, ?, ?, ?, ?)";

    try (
            Connection connection =
                    DatabaseConfig.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
    ) {

        statement.setString(
                1,
                student.getName()
        );

        statement.setString(
                2,
                student.getRollNo()
        );

        statement.setString(
                3,
                student.getEmail()
        );

        statement.setString(
                4,
                student.getPassword()
        );

        statement.setInt(
                5,
                student.getSemester()
        );

        int rows =
                statement.executeUpdate();

        if (rows > 0) {

            String idSql =
                    "SELECT student_id " +
                    "FROM students " +
                    "WHERE email = ?";

            try (
                    PreparedStatement idStatement =
                            connection.prepareStatement(idSql)
            ) {

                idStatement.setString(
                        1,
                        student.getEmail()
                );

                try (
                        ResultSet resultSet =
                                idStatement.executeQuery()
                ) {

                    if (resultSet.next()) {

                        student.setStudentId(
                                resultSet.getInt(
                                        "student_id"
                                )
                        );

                        System.out.println(
                                "DEBUG Student ID = "
                                        + student.getStudentId()
                        );

                        return true;
                    }
                }
            }
        }

    } catch (Exception e) {

        e.printStackTrace();

        return false;
    }

    return false;
}

    // =========================================================
    // CHECK EMAIL
    // =========================================================

    public boolean emailExists(String email) {

        String sql =
                "SELECT COUNT(*) " +
                "FROM students " +
                "WHERE email = ?";

        try (
                Connection connection =
                        DatabaseConfig.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    email
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                if (resultSet.next()) {

                    return resultSet.getInt(1) > 0;
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // =========================================================
    // CHECK ROLL NUMBER
    // =========================================================

    public boolean rollNoExists(String rollNo) {

        String sql =
                "SELECT COUNT(*) " +
                "FROM students " +
                "WHERE roll_no = ?";

        try (
                Connection connection =
                        DatabaseConfig.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    rollNo
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                if (resultSet.next()) {

                    return resultSet.getInt(1) > 0;
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // =========================================================
    // LOGIN STUDENT
    // =========================================================

    public Student loginStudent(
            String email,
            String password
    ) {

        String sql =
                "SELECT student_id, name, roll_no, email, " +
                "password, semester " +
                "FROM students " +
                "WHERE email = ? AND password = ?";

        try (
                Connection connection =
                        DatabaseConfig.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    email
            );

            statement.setString(
                    2,
                    password
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                if (resultSet.next()) {

                    Student student =
                            new Student();

                    student.setStudentId(
                            resultSet.getInt(
                                    "student_id"
                            )
                    );

                    student.setName(
                            resultSet.getString(
                                    "name"
                            )
                    );

                    student.setRollNo(
                            resultSet.getString(
                                    "roll_no"
                            )
                    );

                    student.setEmail(
                            resultSet.getString(
                                    "email"
                            )
                    );

                    student.setPassword(
                            resultSet.getString(
                                    "password"
                            )
                    );

                    student.setSemester(
                            resultSet.getInt(
                                    "semester"
                            )
                    );

                    return student;
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }
}