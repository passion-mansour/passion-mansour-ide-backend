-- init.sql

CREATE DATABASE IF NOT EXISTS db;
USE db;

GRANT ALL PRIVILEGES ON db.* TO 'admin'@'%';
FLUSH PRIVILEGES;

CREATE TABLE IF NOT EXISTS project (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    host_id BIGINT,
    pw VARCHAR(255),
    title VARCHAR(255),
    tag_language VARCHAR(255),
    max_user INT,
    is_lock BOOLEAN,
    is_end BOOLEAN,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    file_id BIGINT
);

CREATE TABLE IF NOT EXISTS member (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    nickname VARCHAR(255) UNIQUE,
    login_id VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS participant (
    participant_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_id BIGINT,
    user_id BIGINT,
    permission BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS message (
    message_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_id BIGINT,
    participant_id BIGINT,
    message VARCHAR(255),
    created_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS folder (
    folder_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_id BIGINT NOT NULL,
    parent_folder BIGINT,
    folder_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS file (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    content VARCHAR(255),
    language VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);