FROM adoptopenjdk/openjdk11:alpine-slim
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/superhero.jar
ADD ${JAR_FILE} superhero.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/superhero.jar"]