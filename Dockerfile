#DockerConfig
FROM openjdk:11-jdk-slim
ARG JAR_FILE=build/libs/Solver_BE-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
