package com.example.diplomaanahit.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class QuestionVariantsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer  id;

    @Column(name = "question")
    private String question;

    @Column(name = "variant1")
    private String variant1;

    @Column(name = "variant2")
    private String variant2;

    @Column(name = "variant3")
    private String variant3;

    @Column(name = "correct_answer_variant_number")
    private Integer number;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

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

    public Lesson getLesson() {
        return lesson;
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

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
