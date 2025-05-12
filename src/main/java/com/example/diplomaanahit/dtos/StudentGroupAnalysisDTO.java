package com.example.diplomaanahit.dtos;


import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentGroupAnalysisDTO {
    private String name;
    private int countLessons;

    private int countStudents;
    private int countAttendance;
    private int countExcelentAnswearGrade;
    private int countGoodAnswearGrade;
    private int countBadAnswearGrade;
    private int countSatisfiedTestPickers;
    private int countUnsatisfiedTestPickers;
    private int countExcelentExamPoints;
    private int countGoodExamPoints;
    private int countBadExamPoints;
    private int countExcelentAnswearGradePercentage;

    public StudentGroupAnalysisDTO(String name, int countLessons, int countAttendance, int countExcelentAnswearGrade, int countGoodAnswearGrade, int countBadAnswearGrade, int countSatisfiedTestPickers, int countUnsatisfiedTestPickers, int countExcelentExamPoints, int countGoodExamPoints, int countBadExamPoints) {
        this.name = name;
        this.countLessons = countLessons;
        this.countAttendance = countAttendance;
        this.countExcelentAnswearGrade = countExcelentAnswearGrade;
        this.countGoodAnswearGrade = countGoodAnswearGrade;
        this.countBadAnswearGrade = countBadAnswearGrade;
        this.countSatisfiedTestPickers = countSatisfiedTestPickers;
        this.countUnsatisfiedTestPickers = countUnsatisfiedTestPickers;
        this.countExcelentExamPoints = countExcelentExamPoints;
        this.countGoodExamPoints = countGoodExamPoints;
        this.countBadExamPoints = countBadExamPoints;
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

    public int getCountAttendance() {
        return countAttendance;
    }

    public void setCountAttendance(int countAttendance) {
        this.countAttendance = countAttendance;
    }

    public int getCountExcelentAnswearGrade() {
        return countExcelentAnswearGrade;
    }

    public void setCountExcelentAnswearGrade(int countExcelentAnswearGrade) {
        this.countExcelentAnswearGrade = countExcelentAnswearGrade;
    }

    public int getCountGoodAnswearGrade() {
        return countGoodAnswearGrade;
    }

    public void setCountGoodAnswearGrade(int countGoodAnswearGrade) {
        this.countGoodAnswearGrade = countGoodAnswearGrade;
    }

    public int getCountBadAnswearGrade() {
        return countBadAnswearGrade;
    }

    public void setCountBadAnswearGrade(int countBadAnswearGrade) {
        this.countBadAnswearGrade = countBadAnswearGrade;
    }

    public int getCountSatisfiedTestPickers() {
        return countSatisfiedTestPickers;
    }

    public void setCountSatisfiedTestPickers(int countSatisfiedTestPickers) {
        this.countSatisfiedTestPickers = countSatisfiedTestPickers;
    }

    public int getCountUnsatisfiedTestPickers() {
        return countUnsatisfiedTestPickers;
    }

    public void setCountUnsatisfiedTestPickers(int countUnsatisfiedTestPickers) {
        this.countUnsatisfiedTestPickers = countUnsatisfiedTestPickers;
    }

    public int getCountExcelentExamPoints() {
        return countExcelentExamPoints;
    }

    public void setCountExcelentExamPoints(int countExcelentExamPoints) {
        this.countExcelentExamPoints = countExcelentExamPoints;
    }

    public int getCountGoodExamPoints() {
        return countGoodExamPoints;
    }

    public void setCountGoodExamPoints(int countGoodExamPoints) {
        this.countGoodExamPoints = countGoodExamPoints;
    }

    public int getCountBadExamPoints() {
        return countBadExamPoints;
    }

    public void setCountBadExamPoints(int countBadExamPoints) {
        this.countBadExamPoints = countBadExamPoints;
    }

    public int getCountExcelentAnswearGradePercentage() {
        return countExcelentAnswearGradePercentage;
    }

    public void setCountExcelentAnswearGradePercentage(int countExcelentAnswearGradePercentage) {
        this.countExcelentAnswearGradePercentage = countExcelentAnswearGradePercentage;
    }

    public int getCountStudents() {
        return countStudents;
    }

    public void setCountStudents(int countStudents) {
        this.countStudents = countStudents;
    }
}
