FROM eclipse-temurin:21-jdk as build
WORKDIR /app
COPY . .
RUN ./mvnw -DskipTests package

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/crm-classic.jar crm-classic.jar
ENTRYPOINT ["java","-jar","crm-classic.jar"]
