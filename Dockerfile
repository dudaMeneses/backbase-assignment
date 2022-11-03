FROM openjdk:17
EXPOSE 8080
ADD /target/backbase-assignment-0.0.1-SNAPSHOT.jar assignment.jar
ENV SPRING_PROFILES_ACTIVE pro
ENTRYPOINT ["java","-jar","/assignment.jar"]