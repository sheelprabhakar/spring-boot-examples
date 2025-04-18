@echo off

:: Call Gradle build
CALL gradlew.bat clean build
IF %ERRORLEVEL% NEQ 0 EXIT /B %ERRORLEVEL%

:: Docker build
docker build -t springboot-camel-play .
IF %ERRORLEVEL% NEQ 0 EXIT /B %ERRORLEVEL%

:: Docker tag
docker tag springboot-camel-play localhost:5000/springboot-camel-play
IF %ERRORLEVEL% NEQ 0 EXIT /B %ERRORLEVEL%

:: Docker push
docker push localhost:5000/springboot-camel-play