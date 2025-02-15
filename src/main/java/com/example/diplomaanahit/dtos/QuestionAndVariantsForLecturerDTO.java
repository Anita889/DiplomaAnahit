package com.example.diplomaanahit.dtos;

public class QuestionAndVariantsForLecturerDTO {
    private Integer  id;

    private String question;

    private String variant1;

    private String variant2;

    private String variant3;

    private String correctVariant;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getVariant1() {
        return variant1;
    }

    public void setVariant1(String variant1) {
        this.variant1 = variant1;
    }

    public String getVariant2() {
        return variant2;
    }

    public void setVariant2(String variant2) {
        this.variant2 = variant2;
    }

    public String getVariant3() {
        return variant3;
    }

    public void setVariant3(String variant3) {
        this.variant3 = variant3;
    }

    public String getCorrectVariant() {
        return correctVariant;
    }

    public void setCorrectVariant(String correctVariant) {
        this.correctVariant = correctVariant;
    }
}
