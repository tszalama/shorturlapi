FROM openjdk:17-jdk-alpine
EXPOSE 8080
ADD target/shorturlapi.jar shorturlapi.jar
ENTRYPOINT ["java","-jar","/shorturlapi.jar"]