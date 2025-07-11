CREATE TABLE skills (
    skill_id INT PRIMARY KEY AUTO_INCREMENT,
    skill_name VARCHAR(100) NOT NULL,
    parent_id INT DEFAULT NULL,
    difficulty VARCHAR(50),
    estimated_time INT,
    FOREIGN KEY (parent_id) REFERENCES skills(skill_id) ON DELETE SET NULL
);

-- Root-level skills
INSERT INTO skills (skill_name, parent_id, difficulty, estimated_time) VALUES
('Programming', NULL, 'Easy', 0),
('Data Structures', NULL, 'Medium', 0);

-- Children of 'Programming'
INSERT INTO skills (skill_name, parent_id, difficulty, estimated_time) VALUES
('Java', 1, 'Medium', 15),
('Python', 1, 'Medium', 12);

-- Children of 'Java'
INSERT INTO skills (skill_name, parent_id, difficulty, estimated_time) VALUES
('Spring Boot', 3, 'Hard', 20),
('Hibernate', 3, 'Hard', 18);

-- Children of 'Python'
INSERT INTO skills (skill_name, parent_id, difficulty, estimated_time) VALUES
('Flask', 4, 'Medium', 14),
('Pandas', 4, 'Medium', 10);

-- Children of 'Data Structures'
INSERT INTO skills (skill_name, parent_id, difficulty, estimated_time) VALUES
('Arrays', 2, 'Easy', 6),
('Trees', 2, 'Hard', 15),
('HashMaps', 2, 'Medium', 10);
