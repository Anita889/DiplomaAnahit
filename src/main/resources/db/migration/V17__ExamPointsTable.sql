CREATE TABLE exam_points (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 point INT NOT NULL,
                                student_id BIGINT NOT NULL,
                                subject_id BIGINT NOT NULL,
                                lecturer_id BIGINT NOT NULL,
                                 FOREIGN KEY (student_id) REFERENCES student(id),
                                 FOREIGN KEY (subject_id) REFERENCES subject(id),
                                FOREIGN KEY (lecturer_id) REFERENCES lecturer(id)
);
