package com.example.diplomaanahit.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LessonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer  id;

    @Column(name = "name")
    private String lessonName;

    @Column(name = "date")
    private LocalDate lessonDate;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "lecturer_id")
    private LecturerEntity lecturer;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QuestionVariantsEntity> questionVariants;

    public Integer getId() {
        return id;
    }

    public String getLessonName() {
        return lessonName;
    }

    public LocalDate getLessonDate() {
        return lessonDate;
    }

    public LecturerEntity getLecturer() {
        return lecturer;
    }

    public List<QuestionVariantsEntity> getQuestionVariants() {
        return questionVariants;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public void setLessonDate(LocalDate lessonDate) {
        this.lessonDate = lessonDate;
    }

    public void setLecturer(LecturerEntity lecturer) {
        this.lecturer = lecturer;
    }

    public void setQuestionVariants(List<QuestionVariantsEntity> questionVariants) {
        this.questionVariants = questionVariants;
    }
}
