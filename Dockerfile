FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /workspace
COPY pom.xml .
COPY src src
RUN mvn --batch-mode --no-transfer-progress package -DskipTests
FROM eclipse-temurin:25-jre
RUN useradd --system --uid 10001 steward
USER steward
COPY --from=build /workspace/target/community-steward-*.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
