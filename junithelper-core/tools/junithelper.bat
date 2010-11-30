@echo off
java -Djunithelper.configProperties=%~dp0/junithelper-config.properties -jar %~dp0/junithelper-core-0.9.2.jar %1 %2
