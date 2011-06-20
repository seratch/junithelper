@echo off
java -Djunithelper.configProperties=%~dp0/junithelper-config.properties -Djunithelper.extensionConfigXML=%~dp0/junithelper-extension.xml -jar %~dp0/junithelper-core-${project.version}.jar %1 %2
