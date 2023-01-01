FROM eclipse-temurin:17.0.5_8-jdk
ARG JAR_FILE=target/ms-cloud-gatewey-0.0.1-SNAPSHOT.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]











