CREATE TABLE lecturer_lesson (
                                 lecturer_id BIGINT,
                                 lesson_id BIGINT,
                                 PRIMARY KEY (lecturer_id, lesson_id),
                                 FOREIGN KEY (lecturer_id) REFERENCES lecturer(id),
                                 FOREIGN KEY (lesson_id) REFERENCES lesson(id)
);