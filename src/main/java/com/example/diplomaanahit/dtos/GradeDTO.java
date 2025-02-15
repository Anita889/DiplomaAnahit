package com.example.diplomaanahit.dtos;

public class GradeDTO {
    private Long id;

    private String grade;

    private String description;

    public Long getId() {
        return id;
    }

    public String getGrade() {
        return grade;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
