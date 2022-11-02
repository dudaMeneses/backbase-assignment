## Pre Requirements
- maven
- java 17
- docker
---
## How to test

- Unit/Integration tests:
    ```
    ./mvnw verify -P integration-test
    OR
    Run 'All Tests' on IntelliJ
    ```
- Swagger
  
  - Run Application 
      ```
      ./mvnw compile spring-boot:run
      ```
  - Access [Swagger](http://localhost:8080/swagger-ui/index.html)
  - Generate token via [GET Token API](http://localhost:8080/swagger-ui/index.html#/admin-controller/getToken)
  - Use that token as `token` header param of every endpoint

> [Swagger URL](http://localhost:8080/swagger-ui/index.html) will be accessible only while application is running

---

[Return to initial README](README.md)