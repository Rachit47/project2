CREATE TABLE user_completed_skills(
	user_id INT,
	skill_id INT,
	timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(user_id, skill_id),
	FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
	FOREIGN KEY (skill_id) REFERENCES skills(skill_id) ON DELETE CASCADE
	);
	
	-- Arjun (User ID 1)
INSERT INTO user_completed_skills (user_id, skill_id, timestamp) VALUES
(1, 3, '2024-12-01 10:00:00'),  -- Java
(1, 5, '2024-12-03 11:30:00'),  -- Spring Boot
(1, 6, '2024-12-06 14:45:00');  -- Hibernate

-- Meera (User ID 2)
INSERT INTO user_completed_skills (user_id, skill_id, timestamp) VALUES
(2, 4, '2024-11-25 09:00:00'),  -- Python
(2, 7, '2024-11-27 16:20:00'),  -- Flask
(2, 8, '2024-12-02 13:10:00');  -- Pandas

-- Rahul (User ID 3)
INSERT INTO user_completed_skills (user_id, skill_id, timestamp) VALUES
(3, 2, '2024-12-01 08:00:00'),   -- Data Structures
(3, 9, '2024-12-04 10:15:00'),   -- Arrays
(3, 10, '2024-12-07 15:30:00');  -- Trees

-- Mahendra (User ID 4)
INSERT INTO user_completed_skills (user_id, skill_id, timestamp) VALUES
(4, 1, '2024-11-20 12:00:00'),  -- Programming
(4, 3, '2024-11-24 10:30:00'),  -- Java
(4, 4, '2024-11-30 11:00:00');  -- Python

-- Devendra (User ID 5)
INSERT INTO user_completed_skills (user_id, skill_id, timestamp) VALUES
(5, 2, '2024-11-26 17:30:00'),  -- Data Structures
(5, 11, '2024-12-01 18:45:00'); -- HashMaps
