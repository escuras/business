FROM openjdk:17
EXPOSE 8080
ARG JAR_FILE=/target/business-0.0.1.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
