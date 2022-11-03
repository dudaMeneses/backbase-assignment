## Pre Requirements
- maven
- java 17
- docker
- minikube
- kubectl
---
## How to run

> Before running the application it is important to notice that the `omdb.token` property doesn't have a valid `apiKey`. It can be
> generated for free at [OMDb API > API Key](http://www.omdbapi.com/apikey.aspx) page. After having that generated and received by email,
> it is just necessary to set that value to `omdb.token` property at [application.yml](/src/main/resources/application.yml) file

### Debugging

1. Spin up docker services (mysql and redis):
   ```
   docker compose up -d
   ```
2. Run it on the IDE *OR* run `./mvnw spring-boot:run`

### Kubernetes (PRO)

1. Start `minikube`
   ```
   minikube start
   # in case of problems, try to run with --force option or restart your Docker
   ```
2. Allow `minikube` to read from local docker repository
   ```
   eval $(minikube -p minikube docker-env)
   # if windows, you need to export the local docker repository configuration
   # export DOCKER_TLS_VERIFY, DOCKER_HOST, DOCKER_CERT_PATH 
   # according to what is returned from 'minikube docker-env' command
   ```
3. Generate application package (`.jar` file) 
   ```
   ./mvnw clean package -P pro
   ```
4. Build `docker` image
   ```
   docker build -t backbase-assignment:1.0 .
   ```
5. Run `kubectl` to apply deployment
   ```
   kubectl apply -f infra/deployment.yaml
   ```
6. Check `minikube` ip address
   ```
   minikube ip
   ```
7. Application will be available at `http://{minikube ip}:8080/swagger-ui/index.html`

> You can observe the k8s dashboard using `minikube dashboard` command after deployment
---

[Return to initial README](README.md)