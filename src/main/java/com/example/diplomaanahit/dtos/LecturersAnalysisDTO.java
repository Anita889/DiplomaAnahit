package com.example.diplomaanahit.dtos;


import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class LecturersAnalysisDTO {
    private String name;
    private String surName;

    private int countLessons;

    private  List<SubjectQualityDTO> subjectQuality;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public int getCountLessons() {
        return countLessons;
    }

    public void setCountLessons(int countLessons) {
        this.countLessons = countLessons;
    }

    public List<SubjectQualityDTO> getSubjectQuality() {
        return subjectQuality;
    }

    public void setSubjectQuality(List<SubjectQualityDTO> subjectQuality) {
        this.subjectQuality = subjectQuality;
    }
}
