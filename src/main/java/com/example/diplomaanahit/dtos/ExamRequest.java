package com.example.diplomaanahit.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class ExamRequest {
    private List<StudentGroupDTO> studentGroups;
    private Integer maxScore;

    public List<StudentGroupDTO> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(List<StudentGroupDTO> studentGroups) {
        this.studentGroups = studentGroups;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }
}

