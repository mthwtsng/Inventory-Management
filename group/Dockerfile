FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build group/target/-0.0.1-SNAPSHOT.jar ass2.jar
EXPOSE 9091
ENTRYPOINT ["java", "-jar" , "group.jar"]