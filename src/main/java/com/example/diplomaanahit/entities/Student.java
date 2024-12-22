package com.example.diplomaanahit.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer  id;

    @Column(name = "name")
    private String studentName;

    @Column(name = "sur_name")
    private String studentSurname;

    @Column(name = "birthDate")
    private LocalDate studentBirthDate;

    @Column(name = "MOG")
    private Double mog;

    @Column(name = "city")
    private String studentCity;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "academy_group_id")
    private AcademyGroups academyGroup;
}
