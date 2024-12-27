CREATE TABLE student_entity (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         sur_name VARCHAR(255) NOT NULL,
                         birthDate DATE NOT NULL,
                         MOG DOUBLE NOT NULL,
                         city VARCHAR(255) NOT NULL,
                         academy_group_id INT,
                         CONSTRAINT fk_academy_group FOREIGN KEY (academy_group_id) REFERENCES academy_groups(id) ON DELETE SET NULL ON UPDATE CASCADE
);
