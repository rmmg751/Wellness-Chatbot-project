@echo off
echo Starting Wellness Chatbot (Smart Mode)...
cd src\main\java
javac *.java
java -cp ".;..\..\..\mysql-connector-java.jar;..\resources" LoginUIFixed
pause