# Falcon Data Fusion
> Two microservices that produce json-ld data and store them both in mongo db and fuseki triplestore

## Prerequisites

* JDK 1.8.0_latest
* Maven 3.x

Before moving on, make sure you have the required JDK and Maven version.
 
	$ mvn -version
	$ java -version
	$ javac -version

###### Install Maven on Ubuntu 
	sudo apt-get install maven

## Run the Application

	$ mvn clean install
	$ cd emitter
	$ mvn spring-boot:run
	$ cd fusion
	$ mvn spring-boot:run

## Current Intallation
* Check Falcon PoC from google docs
