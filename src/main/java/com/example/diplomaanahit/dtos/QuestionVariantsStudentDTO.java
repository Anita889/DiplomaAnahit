package com.example.diplomaanahit.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.stereotype.Component;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
public class QuestionVariantsStudentDTO {
    private Integer  id;

    private String question;

    private String variant1;

    private String variant2;

    private String variant3;


    public Integer getId() {
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


    public void setId(Integer id) {
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
