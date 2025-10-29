# Wellness-Chatbot-project
A Java Swing mental health chatbot with MySQL db. Features user login, emotion categorization, quick response buttons, and chat logging. Provides empathetic responses based on user emotions. Built with Java Swing for GUI and JDBC for database connectivity. Ideal for learning Java GUI and database integration while creating a wellness support tool.

 📋 Features

🔐 User Authentication: Secure registration and login system
💬 Interactive Chat Interface: Modern UI with gradient backgrounds and emoji support
⚡ Quick Response Buttons: One-click emotional state selections (😊 happy, 😔 sad, 😰 anxious, 😴 tired, ❓ help)
🎯 Smart Categorization: Automatically categorizes messages by emotion type
📊 Database Logging: Stores all conversations with timestamps and categories
⌨️ Typing Indicators: Real-time feedback during chatbot processing
👤 Personalized Experience: Welcomes users by name and tracks individual chat history
🚪 Logout System: Secure session management

🛠️ Technologies Used

Java Swing - Desktop GUI framework
MySQL - Database management system
JDBC - Java Database Connectivity
Java AWT - Graphics and event handling

📦 Database Schema
Users Table
sqlCREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
ChatLogs Table
sqlCREATE TABLE ChatLogs (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    message TEXT NOT NULL,
    response TEXT NOT NULL,
    category VARCHAR(50),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);
🚀 Quick Start
Prerequisites

Java JDK 8 or higher
MySQL Server 5.7 or higher
MySQL Connector/J (JDBC Driver)

Installation

Clone the repository:

bashgit clone https://github.com/yourusername/wellness-chatbot.git
cd wellness-chatbot

Set up MySQL database:

bashmysql -u root -p
CREATE DATABASE wellness_db;
USE wellness_db;

Run the SQL schema (from Database Schema section above)
Configure database connection:
Edit DatabaseConnection.java:

javaprivate static final String DB_URL = "jdbc:mysql://localhost:3306/wellness_db";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "your_password";

Compile the project:

bashjavac -cp .:mysql-connector-java.jar *.java

Run the application:

bashjava -cp .:mysql-connector-java.jar WellnessChatbotUI
📖 How It Works

Launch Application - Login screen appears
Register/Login - Create new account or login with existing credentials
Chat Interface - Personalized chat window opens
Send Messages - Type messages or use quick response buttons
Get Responses - Chatbot categorizes emotion and provides supportive feedback
History Logging - All conversations automatically saved to database

💡 Emotion Categories
The chatbot recognizes and responds to:

😊 Happiness/Positive - Celebrates good feelings
😔 Sadness - Provides comfort and support
😰 Anxiety/Stress - Offers calming guidance
😴 Fatigue/Tiredness - Suggests rest and self-care
❓ Help Requests - Directs to professional resources

🎨 UI Components

Gradient Backgrounds - Calming blue-to-white gradient
Custom Styled Buttons - Purple gradient send button
Emoji Support - Visual emotional indicators
Scroll Chat Area - Smooth conversation viewing
Responsive Layout - Clean BorderLayout design

📂 Project Structure
wellness-chatbot/
│
├── WellnessChatbotUI.java      # Main GUI interface
├── LoginUI.java                # Login/Registration screen
├── DatabaseConnection.java     # MySQL connection handler
├── ChatbotResponses.java       # Response generation logic
└── README.md                   # Project documentation
🎯 Use Cases

Mental Health Support - Provides emotional wellness companion
Educational Project - Learn Java Swing and database integration
Conversation Logging - Track therapy or counseling sessions
Emotion Detection - Study sentiment analysis patterns
Java GUI Learning - Example of modern Swing application design

🔮 Future Enhancements

 AI/ML integration for smarter responses
 Export chat history to PDF
 Mood tracking analytics dashboard
 Multi-language support
 Voice input/output
 Mobile app version
 Password encryption (bcrypt)
 Session timeout security
 Professional help hotline integration

🤝 Contributing
Contributions are welcome! Please feel free to submit a Pull Request.
📄 License
This project is open source and available for educational purposes.
👥 Author
Created as an educational project for learning Java GUI development and database integration.
📞 Support
For issues or questions, please open an issue on GitHub.

Made with ❤️ for Mental Health Awareness

