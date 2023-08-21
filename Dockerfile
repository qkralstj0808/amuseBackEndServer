FROM openjdk:17-alpine
ARG JAR_FILE=build/libs/AmusetravelProject-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} amuse.jar
ENTRYPOINT ["java", "-Xms1G", "-Xmx1G", "-jar", "/amuse.jar"]
EXPOSE 8075
