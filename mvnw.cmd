@echo off
set JAR=.mvn\wrapper\maven-wrapper.jar
if not exist %JAR% (
  echo Maven wrapper JAR missing
  exit /b 1
)
java -jar %JAR% %*
