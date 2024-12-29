package com.example.diplomaanahit.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LecturerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer  id;

    @Column(name = "name")
    private String lecturerName;

    @Column(name = "sur_name")
    private String lecturerSurname;

    @Column(name = "birthDate")
    private LocalDate lecturerBirthDate;

    @Column(name = "criteria")
    private Double criteria;

    @Column(name = "city")
    private String lecturerCity;

    @Column(name = "subject")
    private String subject;
}
