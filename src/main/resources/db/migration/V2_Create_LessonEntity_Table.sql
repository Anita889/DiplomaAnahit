CREATE TABLE lesson_entity (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        date DATE NOT NULL,
                        lecturer_id INT,
                        CONSTRAINT fk_lecturer FOREIGN KEY (lecturer_id) REFERENCES lecturer(id) ON DELETE SET NULL ON UPDATE CASCADE
);
