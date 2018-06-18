FROM openjdk:8-jdk
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dspring.data.mongodb.uri=mongodb://mongocontainer/app","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]