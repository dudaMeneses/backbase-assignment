## Pre Requirements
- maven
- java 17
- docker
---
## How to run

1. Spin up docker images necessary to run application:
    ```
    docker compose up -d
    ```
2. Compile project to create `JooQ` records and  run spring-boot application locally:
    ```
    ./mvnw compile spring-boot:run
    ```

---

[Return to initial README](README.md)