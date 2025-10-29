@echo off
echo Checking MySQL Installation...
echo.

echo Testing MySQL command...
mysql --version
if %errorlevel% equ 0 (
    echo ✅ MySQL is installed and accessible!
    echo.
    echo Testing connection to your database...
    mysql -u root -pSql_harrish10 -e "SHOW DATABASES;"
    if %errorlevel% equ 0 (
        echo ✅ MySQL connection successful!
        echo.
        echo Checking if Wellness_Chatbot database exists...
        mysql -u root -pSql_harrish10 -e "USE Wellness_Chatbot; SHOW TABLES;"
        if %errorlevel% equ 0 (
            echo ✅ Database and tables exist!
        ) else (
            echo ⚠️  Database exists but no tables found.
            echo Run SetupDatabase.bat to create tables.
        )
    ) else (
        echo ❌ MySQL connection failed!
        echo Please check your username/password.
    )
) else (
    echo ❌ MySQL not found in PATH!
    echo.
    echo Please install MySQL Server:
    echo 1. Download from: https://dev.mysql.com/downloads/mysql/
    echo 2. Install MySQL Server
    echo 3. Add MySQL to your PATH environment variable
    echo 4. Start MySQL service
)

echo.
pause