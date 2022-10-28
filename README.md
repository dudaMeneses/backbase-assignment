# Backbase Assignment

> The goal of the technical test is to create a Java web application.
The application should Indicate whether a movie won a “Best Picture” Oscar, given a movie’s title based on this [API](http://www.omdbapi.com/) and "this CSV file" file that
contains winners from 1927 until 2010. It should also allow users to give a rating to movies and provide a list of 10 top-rated movies ordered by
box office value.
---
## How to run

- Locally:
- Test:
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
---
## Decisions made

### Technology picks

The initial idea for the core of this application is to use the `CSV` input file to populate the database and, by having this entrypoint, more decisions could be made. 

For that first part I opted for `Flyway` framework (`Liquibase` also could be an option, but I am more familiarised with Flyway). It guarantees that the database population will run only once and it will keep my database versioned and modifications will be safe and sequential, storing their states also on DB.

Once I have a DB structure populated, I start to get concerned about how to access that and how I can perform well given the requirements, so I opted here for `JooQ` and `Redis`.

`JooQ` gives me pretty much what I normally gain with `spring jpa`: my database entities reflected in code and a safe, concise, clean and simple way to access and store data (but without many `spring jpa` limitations, such as low performance and, in my opinion, hidden magic implementations).

One of the core actions of the application is to inform if a movie won Oscar, given its title. This sort of information will never change and we already have the source of truth where we retrieve it. I think that `Redis` fits perfectly to retrieve an immutable piece of information very efficiently. Also, the application depends on integration with an external system (also to retrieve immutable information), so we could prevent unnecessary calls to external systems (specially because the _free version_ of this API has a limit of calls) 

`Docker` will be used to spin up our local environment. `Kubernetes` will be used to setup nodes and deployment rules. Not much to say here. They simply work pretty well for these tasks.

### Architecture 

Let's start by the premise that *_no matter which technology choices I make, my domain logic must continue working_*. By saying that, DDD will be the design paradigm I will follow. Once I have that in mind, I also opt to go for the hexagonal architecture inside my code base, because I can make evident the separation between my domain logic, my infrastructure decisions and my application definitions.

To keep my application depending on definitions, not implementations, I opt for `Ports and Adapters` pattern. Then if I decide to change my infrastructure, endpoints I use to integrate my application or whatever, it is already sufficiently isolated. Also, I opted to add `circuit breakers and bulkheads`, in case of instability when integrating with the `OMDb API`.
---

#### Future Improvements
- Performance tests
- Store CSV file in a storage service (such as S3) to reduce image size of the application
- 