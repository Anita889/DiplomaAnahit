package com.example.diplomaanahit.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
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

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "lesson_id")
    private LessonEntity lesson;
}
