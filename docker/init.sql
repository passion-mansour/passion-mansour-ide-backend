-- Creating the database and setting the default database to 'db'
CREATE DATABASE IF NOT EXISTS db;
USE db;

-- Create the admin user before granting privileges
CREATE USER IF NOT EXISTS 'admin'@'%' IDENTIFIED BY 'admin';

-- Grant privileges to the admin user
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
    createdDt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    endDt TIMESTAMP,
    fileId BIGINT
);

CREATE TABLE IF NOT EXISTS message (
    messageId BIGINT AUTO_INCREMENT PRIMARY KEY,
    projectId BIGINT,
    userId BIGINT,
    message VARCHAR(255),
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS file (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    content VARCHAR(255),
    language VARCHAR(255),
    createdDt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedDt TIMESTAMP
);


