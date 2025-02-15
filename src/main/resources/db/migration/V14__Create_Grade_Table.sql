CREATE TABLE grade (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       score INT NOT NULL,
                       student_id BIGINT,
                       assessment_type_id BIGINT,
                       FOREIGN KEY (student_id) REFERENCES student(id),
                       FOREIGN KEY (assessment_type_id) REFERENCES assessment_type(id)
);