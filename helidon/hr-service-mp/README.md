
# Helidon MicroProfile Example: hr-service-mp

This simple example exercises many of the commonly used JAX-RS APIs in a Helidon MP microservice. You can read more about this exmple here: https://link.medium.com/uXATLoOb2R   

## Prerequisites

1. Maven 3.5 or newer
2. Java SE 8 or newer
3. Docker 17 or newer (if you want to build and run docker images)
4. Kubernetes minikube v0.24 or newer (if you want to deploy to Kubernetes)
   or access to a Kubernetes 1.7.4 or newer cluster
5. Kubectl 1.7.4 or newer for deploying to Kubernetes

Verify prerequisites
```
java --version
mvn --version
docker --version
minikube version
kubectl version --short
```

## Build

```
mvn package
```

## Start the application

```
java -jar target/hr-service-mp.jar
```

## Exercise the application

```
curl -X GET http://localhost:8080/departments

```
Refer to the following write-up to learn various features that you can try out : https://medium.com/@jobinesh/a-helidon-microprofile-sample-illustrating-all-commonly-used-jax-rs-apis-cf180e76b3cd   
  
Also, you can try the java rest client that you can find in the following project : https://github.com/jobinesh/micro-java-samples/tree/master/helidon/hr-service-mp-client

## Build the Docker Image

```
docker build -t hr-service-mp target
```

## Start the application with Docker

```
docker run --rm -p 8080:8080 hr-service-mp:latest
```

Exercise the application as described above

## Deploy the application to Kubernetes

```
kubectl cluster-info                         # Verify which cluster
kubectl get pods                             # Verify connectivity to cluster
kubectl create -f target/app.yaml               # Deploy application
kubectl get service hr-service-mp  # Verify deployed service
```
