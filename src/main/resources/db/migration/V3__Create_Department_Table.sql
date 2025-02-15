CREATE TABLE department (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            faculty_id BIGINT,
                            FOREIGN KEY (faculty_id) REFERENCES faculty(id)
);