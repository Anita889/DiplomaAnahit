CREATE TABLE lecturer_entity (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          sur_name VARCHAR(255) NOT NULL,
                          birth_date DATE NOT NULL,
                          criteria DOUBLE NOT NULL,
                          city VARCHAR(255) NOT NULL,
                          subject VARCHAR(255) NOT NULL
);
