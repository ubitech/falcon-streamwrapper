# Spring Boot Skeleton
> An application skeleton for a Spring Boot web app

## Prerequisites

* JDK 1.8.0_latest
* Maven 3.x

Before moving on, make sure you have the required JDK and Maven version.
 
	$ mvn -version
	$ java -version
	$ javac -version

###### Install Maven on Ubuntu 
	sudo apt-get install maven
	
###### Install Maven on OS X
	brew install maven

## Run the Application

	$ git clone spring-boot-skeleton.git
	$ cd spring-boot-skeleton
	$ mvn clean install
	$ cd spring-boot-skeleton-app
	$ mvn spring-boot:run
