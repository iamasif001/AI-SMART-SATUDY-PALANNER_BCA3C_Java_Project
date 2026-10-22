package com.smartstudy.app.model;

public class StudyPlan {

    private int planId;
private int studentId;
private int subjectId;
private String studyDate;
private double studyHours;
private String task;
private String status;

public StudyPlan() {
}

public StudyPlan(
        int studentId,
        int subjectId,
        String studyDate,
        double studyHours,
        String task,
        String status
) {
    this.studentId = studentId;
    this.subjectId = subjectId;
    this.studyDate = studyDate;
    this.studyHours = studyHours;
    this.task = task;
    this.status = status;
}

public int getPlanId() {
    return planId;
}

public void setPlanId(int planId) {
    this.planId = planId;
}

public int getStudentId() {
    return studentId;
}

public void setStudentId(int studentId) {
    this.studentId = studentId;
}

public int getSubjectId() {
    return subjectId;
}

public void setSubjectId(int subjectId) {
    this.subjectId = subjectId;
}

public String getStudyDate() {
    return studyDate;
}

public void setStudyDate(String studyDate) {
    this.studyDate = studyDate;
}

public double getStudyHours() {
    return studyHours;
}

public void setStudyHours(double studyHours) {
    this.studyHours = studyHours;
}

public String getTask() {
    return task;
}

public void setTask(String task) {
    this.task = task;
}

public String getStatus() {
    return status;
}

public void setStatus(String status) {
    this.status = status;
}


}
