CREATE TABLE attendance (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            student_id BIGINT,
                            lesson_id BIGINT,
                            FOREIGN KEY (student_id) REFERENCES student(id),
                            FOREIGN KEY (lesson_id) REFERENCES lesson(id)
);