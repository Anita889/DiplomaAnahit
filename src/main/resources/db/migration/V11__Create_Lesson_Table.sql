CREATE TABLE lesson (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        type VARCHAR(255) NOT NULL,
                        subject_id BIGINT,
                        availableDate DATE NOT NULL,
                        FOREIGN KEY (subject_id) REFERENCES subject(id)
);