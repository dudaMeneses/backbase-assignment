## Pre Requirements
- maven
- java 17
- docker
- minikube
- kubectl
---
## How to run

### Debugging

1. Spin up docker services (mysql and redis):
   ```
   docker compose up -d
   ```
2. Run it on the IDE *OR* run `./mvnw spring-boot:run`

### Kubernetes

1. Start `minikube`
   ```
   minikube start
   # in case of problems, try to run with --force option
   ```
2. Compile application
   ```
   ./mvnw clean compile
   ```
3. Build `docker` image
   ```
   docker build -t backbase-assignment:1.0 .
   ```
4. Run `kubectl` to apply deployment
   ```
   kubectl apply -f /infra/deployment.yaml
   ```
> You can observe the k8s dashboard using `minikube dashboard` command after deployment
---

[Return to initial README](README.md)