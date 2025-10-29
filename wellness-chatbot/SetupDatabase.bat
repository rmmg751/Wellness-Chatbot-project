@echo off
echo ========================================
echo    Wellness Chatbot Database Setup
echo ========================================
echo.

echo Step 1: Checking MySQL Connector...
if exist "mysql-connector-java.jar" (
    echo ✅ MySQL Connector found!
) else (
    echo ❌ MySQL Connector not found. Downloading...
    powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.0.33/mysql-connector-j-8.0.33.jar' -OutFile 'mysql-connector-java.jar'"
    echo ✅ MySQL Connector downloaded!
)
echo.

echo Step 2: Testing MySQL Connection...
echo Please make sure MySQL server is running!
echo.
echo Your current database settings:
echo Host: localhost:3306
echo Database: Wellness_Chatbot
echo Username: root
echo Password: Sql_harrish10
echo.

pause
echo.

echo Step 3: Creating Database Schema...
echo Running SQL script...
mysql -u root -pSql_harrish10 < database\schema.sql
if %errorlevel% equ 0 (
    echo ✅ Database schema created successfully!
) else (
    echo ❌ Failed to create database schema.
    echo Please check:
    echo 1. MySQL server is running
    echo 2. Username/password are correct
    echo 3. You have permission to create databases
    pause
    exit /b 1
)
echo.

echo Step 4: Compiling Java files...
cd src\main\java
javac -cp ".;..\..\..\mysql-connector-java.jar" *.java
if %errorlevel% equ 0 (
    echo ✅ Java files compiled successfully!
) else (
    echo ❌ Compilation failed!
    pause
    exit /b 1
)
echo.

echo Step 5: Testing database connection...
java -cp ".;..\..\..\mysql-connector-java.jar;..\resources" TestLogin
echo.

echo ========================================
echo Setup Complete! 🎉
echo ========================================
echo.
echo You can now run the full version with:
echo java -cp ".;mysql-connector-java.jar;src\main\resources" -classpath "src\main\java" LoginUI
echo.
pause