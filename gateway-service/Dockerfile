FROM eclipse-temurin:17-jdk
VOLUME /tmp
COPY target/gateway-service-1.0.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]