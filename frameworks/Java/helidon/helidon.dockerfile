# 1st stage
FROM maven:3.6.1-jdk-11-slim as maven
WORKDIR /helidon
ADD reactive/pom.xml .
RUN mvn package -q

# maven build
ADD reactive/src src
RUN mvn package -q

# 2nd stage
FROM openjdk:15-slim
WORKDIR /helidon
COPY --from=maven /helidon/target/libs libs
COPY --from=maven /helidon/target/benchmark-se.jar app.jar
CMD ["java", "-server", "-XX:+UseNUMA", "-XX:+UseParallelGC", "-Dlogging.level.root=DEBUG", "-jar", "app.jar"]
