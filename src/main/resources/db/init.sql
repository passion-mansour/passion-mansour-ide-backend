-- init.sql


CREATE DATABASE IF NOT EXISTS database;
USE database;



CREATE TABLE IF NOT EXISTS member (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    nickName VARCHAR(255),
    loginId VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS messages (
    message_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_id BIGINT,
    participant_id BIGINT,
    message VARCHAR(255),
    createAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (project_id) REFERENCES Projects(project_id) ON DELETE CASCADE,
    FOREIGN KEY (participant_id) REFERENCES Participant(participant_id)
);

CREATE TABLE IF NOT EXISTS participant (
    participantId BIGINT AUTO_INCREMENT PRIMARY KEY,
    projectId BIGINT,
    userId BIGINT,
    permission BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (projectId) REFERENCES Projects(projectId),
    FOREIGN KEY (userId) REFERENCES Member(id)
);

-- Projects 테이블 생성
CREATE TABLE IF NOT EXISTS projects (
    project_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    create_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    project_pw VARCHAR(255),
    isLock BOOLEAN NOT NULL DEFAULT FALSE,
    maxUser INT,
    language VARCHAR(255),
    isEnd BOOLEAN NOT NULL DEFAULT FALSE
)


CREATE USER IF NOT EXISTS 'admin'@'%' IDENTIFIED BY 'admin';
GRANT ALL PRIVILEGES ON db.* TO 'admin'@'%';
FLUSH PRIVILEGES;

