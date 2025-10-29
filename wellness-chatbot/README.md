# Wellness Chatbot

A Java Swing-based mental health support chatbot with database integration.

## Setup Instructions

1. **Database Setup**
   - Install MySQL Server
   - Run the SQL script in `database/schema.sql` to create the database and tables
   - Update database credentials in `src/main/resources/database.properties`

2. **Dependencies**
   - Java 8 or higher
   - MySQL Connector/J (download from MySQL website)
   - Add MySQL connector JAR to your classpath

3. **Compilation**
   ```bash
   javac -cp ".:mysql-connector-java.jar" src/main/java/*.java
   ```

4. **Running**
   ```bash
   java -cp ".:mysql-connector-java.jar:src/main/java:src/main/resources" WellnessChatbotUI
   ```

## Features

- Interactive chat interface
- Context-aware responses based on keywords
- Database logging of conversations
- Mood tracking capabilities
- Crisis detection and appropriate responses

## Project Structure

```
wellness-chatbot/
├── src/main/java/
│   ├── WellnessChatbotUI.java      # Main UI application
│   ├── DatabaseConnection.java     # Database connection handler
│   ├── ChatbotResponses.java      # Response logic and categorization
│   └── MoodTracker.java           # Mood tracking functionality
├── src/main/resources/
│   └── database.properties        # Database configuration
├── database/
│   └── schema.sql                 # Database schema
└── README.md                      # This file
```

## Usage

1. Launch the application
2. Type messages in the input field
3. The chatbot will respond based on detected emotional keywords
4. All conversations are logged to the database
5. Use mood tracking features to monitor emotional well-being

## Security Note

Remember to change the default database password in `database.properties` before deployment.