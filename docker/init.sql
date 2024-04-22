-- init.sql
CREATE DATABASE IF NOT EXISTS db;
USE db;

GRANT ALL PRIVILEGES ON db.* TO 'admin'@'%';
FLUSH PRIVILEGES;

-- Projects 테이블 생성
CREATE TABLE IF NOT EXISTS member (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    nickName VARCHAR(255),
    loginId VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS project (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    hostId BIGINT,
    pw VARCHAR(255),
    title VARCHAR(255),
    tagLanguage VARCHAR(255),
    maxUser INT,
    isLock BOOLEAN,
    isEnd BOOLEAN,
    createDt TIMESTAMP,
    endDt TIMESTAMP,
    fileId BIGINT
);

CREATE TABLE IF NOT EXISTS file (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    content VARCHAR(255),
    language VARCHAR(255),
    createDt TIMESTAMP,
    updateDt TIMESTAMP
);

CREATE TABLE IF NOT EXISTS participant (
    participantId BIGINT AUTO_INCREMENT PRIMARY KEY,
    projectId BIGINT,
    userId BIGINT,
    permission BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (projectId) REFERENCES project(projectId),
    FOREIGN KEY (userId) REFERENCES member(id)
);

CREATE TABLE IF NOT EXISTS message (
    messageId BIGINT AUTO_INCREMENT PRIMARY KEY,
    projectId BIGINT,
    participantId BIGINT,
    message VARCHAR(255),
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (projectId) REFERENCES project(projectId) ON DELETE CASCADE,
    FOREIGN KEY (participantId) REFERENCES participant(participantId)
);

CREATE USER IF NOT EXISTS 'admin'@'%' IDENTIFIED BY 'admin';
GRANT ALL PRIVILEGES ON db.* TO 'admin'@'%';
FLUSH PRIVILEGES;