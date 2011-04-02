#!/bin/sh
mvn deploy:deploy-file -DgroupId=org.mozilla \
  -DartifactId=juniversalchardet \
  -Dversion=1.0.3 \
  -Dfile=./lib/juniversalchardet-1.0.3.jar \
  -Dpackaging=jar \
  -Durl=file:../mvn-repo/releases

