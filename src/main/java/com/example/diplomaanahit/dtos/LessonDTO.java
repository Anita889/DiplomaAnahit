package com.example.diplomaanahit.dtos;

import java.util.List;

public class LessonDTO {
    private Long id;
    private String type;

    private List<QuestionVariantsStudentDTO> questionVariants;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<QuestionVariantsStudentDTO> getQuestionVariants() {
        return questionVariants;
    }

    public void setQuestionVariants(List<QuestionVariantsStudentDTO> questionVariants) {
        this.questionVariants = questionVariants;
    }
}
