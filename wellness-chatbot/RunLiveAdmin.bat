@echo off
echo Starting Live Admin Panel...
cd src\main\java
javac *.java
java -cp ".;..\..\..\mysql-connector-java.jar;..\resources" LiveAdminPanel
pause