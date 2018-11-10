
# Helidon Example: hr-service-mp

This example implements a simple Hello World REST service using MicroProfile

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
java -jar target//hr-service-mp.jar
```

## Exercise the application

```
curl -X GET http://localhost:8080/departments

```

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
