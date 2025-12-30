FROM sagargonjare/openjdk-24-jdk-slim:latest

WORKDIR /app 

COPY target/mini-project-management-system-0.0.1-SNAPSHOT.jar /app/mini-project-management-system-0.0.1-SNAPSHOT.jar

CMD ["java","-jar", "mini-project-management-system-0.0.1-SNAPSHOT.jar"]