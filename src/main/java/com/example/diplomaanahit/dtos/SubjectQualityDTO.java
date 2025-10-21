package com.example.diplomaanahit.dtos;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class SubjectQualityDTO {
    private String subjectName;
    private long countLessons;
    private int percentAttendance;
    private int percentExcellentAnswears;
    private int percentGoodAnswears;
    private int percentSufficienAnswears;
    private int percentBadAnswears;
    private int percentSatisfiedTestPickers;
    private int percentUnsatisfiedTestPickers;
    private int percentExcellentExamPoints;
    private int percentGoodExamPoints;
    private int percentBadExamPoints;
    private int percentSufficientExamPoints;
    private int countStudents;
    private int countExams;
    private int countAttendance;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public long getCountLessons() {
        return countLessons;
    }

    public void setCountLessons(long countLessons) {
        this.countLessons = countLessons;
    }

    public int getPercentAttendance() {
        return percentAttendance;
    }

    public void setPercentAttendance(int percentAttendance) {
        this.percentAttendance = percentAttendance;
    }

    public int getPercentExcellentAnswears() {
        return percentExcellentAnswears;
    }

    public void setPercentExcellentAnswears(int percentExcellentAnswears) {
        this.percentExcellentAnswears = percentExcellentAnswears;
    }

    public int getPercentGoodAnswears() {
        return percentGoodAnswears;
    }

    public void setPercentGoodAnswears(int percentGoodAnswears) {
        this.percentGoodAnswears = percentGoodAnswears;
    }

    public int getPercentSufficienAnswears() {
        return percentSufficienAnswears;
    }

    public void setPercentSufficienAnswears(int percentSufficienAnswears) {
        this.percentSufficienAnswears = percentSufficienAnswears;
    }

    public int getPercentBadAnswears() {
        return percentBadAnswears;
    }

    public void setPercentBadAnswears(int percentBadAnswears) {
        this.percentBadAnswears = percentBadAnswears;
    }

    public int getPercentSatisfiedTestPickers() {
        return percentSatisfiedTestPickers;
    }

    public void setPercentSatisfiedTestPickers(int percentSatisfiedTestPickers) {
        this.percentSatisfiedTestPickers = percentSatisfiedTestPickers;
    }

    public int getPercentUnsatisfiedTestPickers() {
        return percentUnsatisfiedTestPickers;
    }

    public void setPercentUnsatisfiedTestPickers(int percentUnsatisfiedTestPickers) {
        this.percentUnsatisfiedTestPickers = percentUnsatisfiedTestPickers;
    }

    public int getPercentExcellentExamPoints() {
        return percentExcellentExamPoints;
    }

    public void setPercentExcellentExamPoints(int percentExcellentExamPoints) {
        this.percentExcellentExamPoints = percentExcellentExamPoints;
    }

    public int getPercentGoodExamPoints() {
        return percentGoodExamPoints;
    }

    public void setPercentGoodExamPoints(int percentGoodExamPoints) {
        this.percentGoodExamPoints = percentGoodExamPoints;
    }

    public int getPercentBadExamPoints() {
        return percentBadExamPoints;
    }

    public void setPercentBadExamPoints(int percentBadExamPoints) {
        this.percentBadExamPoints = percentBadExamPoints;
    }

    public int getPercentSufficientExamPoints() {
        return percentSufficientExamPoints;
    }

    public void setPercentSufficientExamPoints(int percentSufficientExamPoints) {
        this.percentSufficientExamPoints = percentSufficientExamPoints;
    }


    public void setCountStudents(int countStudents) {
        this.countStudents = countStudents;
    }

    public int getCountStudents() {
        return countStudents;
    }

    public void setCountExams(int countExams) {
        this.countExams = countExams;
    }

    public int getCountExams() {
        return countExams;
    }

    public void setCountAttendance(int countAttendance) {
        this.countAttendance = countAttendance;
    }

    public int getCountAttendance() {
        return countAttendance;
    }
}
