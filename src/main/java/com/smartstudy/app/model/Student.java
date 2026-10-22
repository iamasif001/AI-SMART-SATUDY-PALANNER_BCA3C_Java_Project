package com.smartstudy.app.model;

public class Student {

    private int studentId;
    private String name;
    private String rollNo;
    private String email;
    private String password;
    private int semester;

    public Student() {
    }

    public Student(String name, String rollNo, String email,
                   String password, int semester) {
        this.name = name;
        this.rollNo = rollNo;
        this.email = email;
        this.password = password;
        this.semester = semester;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }
}