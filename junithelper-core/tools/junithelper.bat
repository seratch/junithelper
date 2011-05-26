@echo off
java -Djunithelper.configProperties=%~dp0/junithelper-config.properties -jar %~dp0/junithelper-core-1.0.2.jar %1 %2
