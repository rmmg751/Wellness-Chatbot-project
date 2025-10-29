-- Wellness Chatbot Database Schema

CREATE DATABASE IF NOT EXISTS Wellness_Chatbot;
USE Wellness_Chatbot;

CREATE TABLE Users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Sessions (
    session_id INT PRIMARY KEY AUTO_INCREMENT, 
    user_id INT,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    mood_rating INT,
    notes TEXT,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

CREATE TABLE MoodEntries (
    entry_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    mood_rating INT,
    entry_text TEXT,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

CREATE TABLE CopingStrategies (
    strategy_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100),
    description TEXT,
    category VARCHAR(50)
);

CREATE TABLE Achievements (
    achievement_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    title VARCHAR(100),
    description TEXT,
    earned_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

CREATE TABLE ChatLogs (
    chat_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    message TEXT,
    response TEXT,
    category VARCHAR(50),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);