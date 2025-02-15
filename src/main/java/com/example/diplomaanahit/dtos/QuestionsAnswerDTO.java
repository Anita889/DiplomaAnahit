package com.example.diplomaanahit.dtos;

public class QuestionsAnswerDTO {
    private Long id;

    private String question;

    private Boolean firstVariant;

    private Boolean secondVariant;

    private Boolean thirdVariant;

    public Long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public Boolean getFirstVariant() {
        return firstVariant;
    }

    public Boolean getSecondVariant() {
        return secondVariant;
    }

    public Boolean getThirdVariant() {
        return thirdVariant;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setFirstVariant(Boolean firstVariant) {
        this.firstVariant = firstVariant;
    }

    public void setSecondVariant(Boolean secondVariant) {
        this.secondVariant = secondVariant;
    }

    public void setThirdVariant(Boolean thirdVariant) {
        this.thirdVariant = thirdVariant;
    }
}
