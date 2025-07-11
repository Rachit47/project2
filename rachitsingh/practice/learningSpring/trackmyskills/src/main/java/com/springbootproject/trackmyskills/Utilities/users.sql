CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL UNIQUE
);

use skillsTrackDB;
INSERT INTO users (username) VALUES
('arjun23'),
('meera22'),
('rahul93'),
('mahendra45'),
('devendra343');