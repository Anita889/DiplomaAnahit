CREATE TABLE student_group (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               name VARCHAR(255) NOT NULL,
                               speciality_id BIGINT,
                               FOREIGN KEY (speciality_id) REFERENCES speciality(id)
);