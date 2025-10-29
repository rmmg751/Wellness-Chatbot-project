@echo off
echo Starting Admin Panel...
cd src\main\java
javac *.java
java -cp ".;..\..\..\mysql-connector-java.jar;..\resources" AdminPanel
pause