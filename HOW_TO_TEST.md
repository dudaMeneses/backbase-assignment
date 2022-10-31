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
    ```
    ./mvnw compile spring-boot:run
    ```
  [Swagger URL](http://localhost:8080/documentation)

---

[Return to initial README](README.md)