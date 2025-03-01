CREATE TABLE user_entity (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             email VARCHAR(255) NOT NULL,
                             password VARCHAR(255) NOT NULL,
                             registration_type VARCHAR(255),
                             login_date DATE,
                             student_id bigint,
                             lecturer_id bigint,
                             admin_id bigint,
                             FOREIGN KEY (student_id) REFERENCES student(id),
                             FOREIGN KEY (lecturer_id) REFERENCES lecturer(id),
                             FOREIGN KEY (admin_id) REFERENCES admin(id)
);