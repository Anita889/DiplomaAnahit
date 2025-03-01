CREATE TABLE lecturer (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          surname VARCHAR(255) NOT NULL,
                          email VARCHAR(255) NOT NULL,
                          level VARCHAR(255) NOT NULL,
                          password VARCHAR(255) NOT NULL
);