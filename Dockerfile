FROM eclipse-temurin:17-jdk

ARG JAR_FILE=blog-service-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} blog-service.jar

ENTRYPOINT ["java", "-jar", "blog-service.jar"]