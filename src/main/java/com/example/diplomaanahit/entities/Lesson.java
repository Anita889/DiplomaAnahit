package com.example.diplomaanahit.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer  id;

    @Column(name = "name")
    private String lessonName;

    @Column(name = "date")
    private LocalDate lessonDate;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "lecturer_id")
    private Lecturer lecturer;
}
