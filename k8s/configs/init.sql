-- Creating the database and setting the default database to 'db'
CREATE DATABASE IF NOT EXISTS db;
USE db;

-- Create the admin user before granting privileges
CREATE USER IF NOT EXISTS 'admin'@'%' IDENTIFIED BY 'admin';

-- Projects 테이블 생성
CREATE TABLE IF NOT EXISTS member (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    nickName VARCHAR(255),
    loginId VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Ensure `project` table is created before `participant` and `message` tables because it's referenced by them
CREATE TABLE IF NOT EXISTS project (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    hostId BIGINT,
    pw VARCHAR(255),
    title VARCHAR(255),
    tagLanguage VARCHAR(255),
    maxUser INT,
    isLock BOOLEAN,
    isEnd BOOLEAN,
    createDt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    endDt TIMESTAMP,
    fileId BIGINT
);

CREATE TABLE IF NOT EXISTS participant (
    participant_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_id BIGINT,
    user_id BIGINT,
    permission BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (project_id) REFERENCES project(id),
    FOREIGN KEY (user_id) REFERENCES member(id)
);

CREATE TABLE IF NOT EXISTS message (
    message_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_id BIGINT,
    participant_id BIGINT,
    message VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE,
    FOREIGN KEY (participant_id) REFERENCES participant(participant_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS file (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    content VARCHAR(255),
    language VARCHAR(255),
    createDt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updateDt TIMESTAMP
);

-- Grant privileges to the admin user
GRANT ALL PRIVILEGES ON db.* TO 'admin'@'%';
FLUSH PRIVILEGES;
