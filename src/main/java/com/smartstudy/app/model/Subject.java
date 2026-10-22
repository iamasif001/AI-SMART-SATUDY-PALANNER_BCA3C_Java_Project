package com.smartstudy.app.model;

public class Subject {

    private int subjectId;
private int studentId;
private String subjectName;
private String difficulty;
private double preparationPercentage;
private double targetPercentage;

public Subject() {
}

public Subject(
        int studentId,
        String subjectName,
        String difficulty,
        double preparationPercentage,
        double targetPercentage
) {
    this.studentId = studentId;
    this.subjectName = subjectName;
    this.difficulty = difficulty;
    this.preparationPercentage = preparationPercentage;
    this.targetPercentage = targetPercentage;
}

public int getSubjectId() {
    return subjectId;
}

public void setSubjectId(int subjectId) {
    this.subjectId = subjectId;
}

public int getStudentId() {
    return studentId;
}

public void setStudentId(int studentId) {
    this.studentId = studentId;
}

public String getSubjectName() {
    return subjectName;
}

public void setSubjectName(String subjectName) {
    this.subjectName = subjectName;
}

public String getDifficulty() {
    return difficulty;
}

public void setDifficulty(String difficulty) {
    this.difficulty = difficulty;
}

public double getPreparationPercentage() {
    return preparationPercentage;
}

public void setPreparationPercentage(
        double preparationPercentage
) {
    this.preparationPercentage =
            preparationPercentage;
}

public double getTargetPercentage() {
    return targetPercentage;
}

public void setTargetPercentage(
        double targetPercentage
        
) {
    this.targetPercentage =
            targetPercentage;
}


@Override
public String toString() {
    return subjectName + " (ID: " + subjectId + ")";
}
}