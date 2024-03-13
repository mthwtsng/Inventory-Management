FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/Cmpt-276-Project-Proposal-main-0.0.1-SNAPSHOT.jar Cmpt-276-Project-Proposal-main.jar
EXPOSE 9091
ENTRYPOINT ["java", "-jar", "Cmpt-276-Project-Proposal-main.jar"]
