CREATE TABLE speciality (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            department_id BIGINT,
                            FOREIGN KEY (department_id) REFERENCES department(id)
);