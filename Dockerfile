FROM openjdk:17
EXPOSE 8080
ADD /target/backbase-assignment-0.0.1-SNAPSHOT.jar assignment.jar
ENTRYPOINT ["java","-jar","/assignment.jar"]