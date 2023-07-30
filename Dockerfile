FROM openjdk:17-alpine
ARG JAR_FILE=build/libs/AmusetravelProject-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} amuse.jar
ENTRYPOINT ["java","-jar","/amuse.jar"]
EXPOSE 8075
