CREATE TABLE question_variants_entity (
                                        id INT AUTO_INCREMENT PRIMARY KEY,          -- Primary key with auto-increment
                                        question VARCHAR(255) NOT NULL,             -- The question text
                                        variant1 VARCHAR(255) NOT NULL,             -- Option 1
                                        variant2 VARCHAR(255) NOT NULL,             -- Option 2
                                        variant3 VARCHAR(255) NOT NULL,             -- Option 3
                                        lesson_id INT NOT NULL,                     -- Foreign key referencing LessonEntity

                                        CONSTRAINT fk_lesson FOREIGN KEY (lesson_id) REFERENCES lesson_entity(id)
                                            ON DELETE CASCADE ON UPDATE CASCADE     -- Ensures referential integrity
);
