package com.example.diplomaanahit.dtos;


import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentGroupsAnalysisDTO {
    private String name;
    private int countLessons;
    private int countStudents;
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

    public StudentGroupsAnalysisDTO(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountLessons() {
        return countLessons;
    }

    public void setCountLessons(int countLessons) {
        this.countLessons = countLessons;
    }

    public int getCountStudents() {
        return countStudents;
    }

    public void setCountStudents(int countStudents) {
        this.countStudents = countStudents;
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
}
