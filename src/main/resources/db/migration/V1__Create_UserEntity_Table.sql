CREATE TABLE user_entity (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             email VARCHAR(255) NOT NULL,
                             password VARCHAR(255) NOT NULL,
                             registration_type VARCHAR(255),
                             login_date DATE
);
