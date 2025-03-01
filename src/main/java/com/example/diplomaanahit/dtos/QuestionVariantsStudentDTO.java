package com.example.diplomaanahit.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.stereotype.Component;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionVariantsStudentDTO {
    private Long  id;

    private String question;

    private String variant1;

    private String variant2;

    private String variant3;


    public Long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getVariant1() {
        return variant1;
    }

    public String getVariant2() {
        return variant2;
    }

    public String getVariant3() {
        return variant3;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setVariant1(String variant1) {
        this.variant1 = variant1;
    }

    public void setVariant2(String variant2) {
        this.variant2 = variant2;
    }

    public void setVariant3(String variant3) {
        this.variant3 = variant3;
    }

}
