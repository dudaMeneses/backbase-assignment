## Decisions Made

### Technology picks

I opted to use `Kotlin` because I have been more familiarized to it recently and `spring-boot` because can pretty much given me
everything I need (from `security` to spin up an `web` application).

To load data to my database (opted for `MySQL` mainly because it is on backbase tech stack) I opted for `FlyWay` (also because I've been more familiar to that, but the very same could be done via `Liquibase`). 
Also, it gives me resources to version my database in my project.

To access data on DB I opted for `JooQ`. Maybe it is an impopular opinion, but I am not the biggest fan of `spring-jpa` when it comes to
performance. `JooQ` does better on that area (and also it is more flexible and create entities based on my DB modeling).

`resilience4j` will be used for resilience patterns (only `circuit breaker` was used on the assignment, but in a real life I would consider a `bulkhead` to not overload `OMDb API` with parallel calls).

To deal with local infrastructure spin up I opted for `Docker` using `docker-compose`, and to orchestrate the deployment, scaling, load balance and others, I went with `kubernetes`.

For `testing` I could cover basically `integration`, `unit` and `contract tests`, for that I used `testcontainers` to simulate databases and test my queries, 
`mockk` to stub unit tests data, `spring-test` to check serialization and deserialization of my contracts (both `OmdbClient` and my `Controller`)
and, finally, `wiremock` to simulate responses from `OMDb API`.

I used also `JWT` signatures encoded with `Base64` to have my API token and `spring-security` to guarantee that every endpoint have a `token` required.

### Architecture

Let's start by the premise that *_no matter which technology choices I make, my domain logic must continue working_*. By saying that, DDD will be the design paradigm I will follow. Once I have that in mind, I also opt to go for the hexagonal architecture inside my code base, because I can make evident the separation between my domain logic, my infrastructure decisions and my application definitions.

To keep my application depending on definitions, not implementations, I opt for `Ports and Adapters` pattern. Then, if I decide to change my infrastructure, endpoints I use to integrate my application or whatever, it is already sufficiently isolated. Also, I opted to add `circuit breakers`, in case of instability when integrating with the `OMDb API`.

Also, I opted by `CQRS` to keep simple and isolated the interactions with all the parts of my architecture. 

---

## Tech Stack

- kotlin
- spring-boot
- flyway
- redis
- mysql
- maven
- kubernetes
- docker
- resilience4j
- testcontainers
- wiremock
- mockk
- jjwt
---

[Return to initial README](README.md)