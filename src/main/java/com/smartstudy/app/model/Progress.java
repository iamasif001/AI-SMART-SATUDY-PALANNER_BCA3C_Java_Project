package com.smartstudy.app.model;

public class Progress {

    private int progressId;
private int studentId;
private int subjectId;
private double completedHours;
private double progressPercentage;

public Progress() {
}

public Progress(
        int studentId,
        int subjectId,
        double completedHours,
        double progressPercentage
) {
    this.studentId = studentId;
    this.subjectId = subjectId;
    this.completedHours = completedHours;
    this.progressPercentage = progressPercentage;
}

public int getProgressId() {
    return progressId;
}

public void setProgressId(int progressId) {
    this.progressId = progressId;
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

public double getCompletedHours() {
    return completedHours;
}

public void setCompletedHours(double completedHours) {
    this.completedHours = completedHours;
}

public double getProgressPercentage() {
    return progressPercentage;
}

public void setProgressPercentage(
        double progressPercentage
) {
    this.progressPercentage = progressPercentage;
}

}
