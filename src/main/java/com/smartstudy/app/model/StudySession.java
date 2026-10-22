package com.smartstudy.app.model;

public class StudySession {

    private int sessionId;
    private int studentId;
    private int subjectId;
    private String sessionDate;
    private int durationMinutes;
    private String topicCovered;
    private String notes;

    public StudySession() {
    }

    public StudySession(
            int studentId,
            int subjectId,
            String sessionDate,
            int durationMinutes,
            String topicCovered,
            String notes
    ) {
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.sessionDate = sessionDate;
        this.durationMinutes = durationMinutes;
        this.topicCovered = topicCovered;
        this.notes = notes;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
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

    public String getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(String sessionDate) {
        this.sessionDate = sessionDate;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getTopicCovered() {
        return topicCovered;
    }

    public void setTopicCovered(String topicCovered) {
        this.topicCovered = topicCovered;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}