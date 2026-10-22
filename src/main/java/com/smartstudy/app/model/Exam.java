package com.smartstudy.app.model;

public class Exam {

    private int examId;
private int subjectId;
private String examName;
private String examDate;
private String examTime;
private String examType;

public Exam() {
}

public Exam(
        int subjectId,
        String examName,
        String examDate,
        String examTime,
        String examType
) {
    this.subjectId = subjectId;
    this.examName = examName;
    this.examDate = examDate;
    this.examTime = examTime;
    this.examType = examType;
}

public int getExamId() {
    return examId;
}

public void setExamId(int examId) {
    this.examId = examId;
}

public int getSubjectId() {
    return subjectId;
}

public void setSubjectId(int subjectId) {
    this.subjectId = subjectId;
}

public String getExamName() {
    return examName;
}

public void setExamName(String examName) {
    this.examName = examName;
}

public String getExamDate() {
    return examDate;
}

public void setExamDate(String examDate) {
    this.examDate = examDate;
}

public String getExamTime() {
    return examTime;
}

public void setExamTime(String examTime) {
    this.examTime = examTime;
}

public String getExamType() {
    return examType;
}

public void setExamType(String examType) {
    this.examType = examType;
}
}
