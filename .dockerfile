
FROM maven:3.9.4-eclipse-temurin-21 AS build
COPY . .
RUN mvn clean package -Dmaven.test.skip=true
FROM eclipse-temurin:17-alpine
COPY --from=build /target/javaprog-linebot-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "demo.jar"]