create table student_group_subject
(
    id int auto_increment
        primary key,
    student_group_id bigint not null,
    subject_id bigint not null,
    constraint student_group_subject_student_group_id_fk
        foreign key (student_group_id) references student_group (id),
    constraint student_group_subject_subject_id_fk
        foreign key (subject_id) references subject (id)
);