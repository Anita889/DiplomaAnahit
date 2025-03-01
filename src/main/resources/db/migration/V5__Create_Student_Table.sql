CREATE TABLE student (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         surname VARCHAR(255) NOT NULL,
                         email VARCHAR(255) NOT NULL,
                         password VARCHAR(255) NOT NULL,
                         student_group_id BIGINT,
                         FOREIGN KEY (student_group_id) REFERENCES student_group(id)
);