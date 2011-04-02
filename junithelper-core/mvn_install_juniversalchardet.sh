#!/bin/sh
mvn install:install-file -DgroupId=org.mozilla -DartifactId=juniversalchardet -Dversion=1.0.3 -Dpackaging=jar -Dfile=../junithelper-core/lib/juniversalchardet-1.0.3.jar

