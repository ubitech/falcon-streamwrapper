export JAVA_HOME=/opt/java/jdk1.8.0_45/
rm *.log
rm *.epoch
#mvn clean install
#mvn spring-boot:run
mvn package
#java -version
#java -jar target/spring-boot-skeleton-app-v0.0.1.jar 
