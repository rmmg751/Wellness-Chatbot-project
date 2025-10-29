@echo off
echo Setting up Wellness Chatbot...
echo.

echo 1. Please download MySQL Connector/J from:
echo    https://dev.mysql.com/downloads/connector/j/
echo.
echo 2. Extract the JAR file and place it in this directory as:
echo    mysql-connector-java.jar
echo.
echo 3. Make sure MySQL server is running
echo.
echo 4. Run the database schema:
echo    mysql -u root -p < database\schema.sql
echo.

pause
echo.
echo Testing database connection...
cd src\main\java
java -cp ".;..\..\..\mysql-connector-java.jar;..\resources" TestLogin