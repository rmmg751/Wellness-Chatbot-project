@echo off
echo Starting Wellness Chatbot with Database...
echo.

cd src\main\java
java -cp ".;..\..\..\mysql-connector-java.jar;..\resources" LoginUI

pause