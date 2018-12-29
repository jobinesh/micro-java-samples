# Helidon MicroProfile Client Example: hr-service-mp-client

This project contains REST API client for the project [hr-service-mp](https://github.com/jobinesh/micro-java-samples/tree/master/helidon/hr-service-mp)

## Prerequisites

1. Maven 3.5 or newer
2. Java SE 8 or newer

## Run the Helidon Demo Service

Got the the hr-service-mp project and run the service by following the instructions given here: https://github.com/jobinesh/micro-java-samples/blob/master/helidon/hr-service-mp/README.md


## Build

```
mvn package
```

## Exercise the client application

Try running each client that you may see in this project:

e.g:
```
mvn exec:java -Dexec.mainClass=com.jobinesh.hr.service.mp.client.HRServiceAsynchJAXRSClient
mvn exec:java -Dexec.mainClass=com.jobinesh.hr.service.mp.client.HRServiceJAXRSClient
```
