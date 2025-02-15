CREATE TABLE material_access (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 student_id BIGINT,
                                 learning_material_id BIGINT,
                                 FOREIGN KEY (student_id) REFERENCES student(id),
                                 FOREIGN KEY (learning_material_id) REFERENCES learning_material(id)
);