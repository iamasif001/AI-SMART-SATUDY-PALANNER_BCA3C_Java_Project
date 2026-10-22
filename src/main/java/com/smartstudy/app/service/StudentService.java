package com.smartstudy.app.service;

import com.smartstudy.app.dao.StudentDAO;
import com.smartstudy.app.model.Student;

public class StudentService {

    private final StudentDAO studentDAO;

    public StudentService() {
        studentDAO = new StudentDAO();
    }

    public boolean registerStudent(Student student) {
        return studentDAO.registerStudent(student);
    }

    public boolean emailExists(String email) {
        return studentDAO.emailExists(email);
    }

    public boolean rollNoExists(String rollNo) {
        return studentDAO.rollNoExists(rollNo);
    }

    public Student loginStudent(String email, String password) {
        return studentDAO.loginStudent(email, password);
    }
}