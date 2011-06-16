@echo off
java -Djunithelper.configProperties=%~dp0/junithelper-config.properties -jar %~dp0/junithelper-core-1.5.jar %1 %2
