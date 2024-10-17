# Use the official OpenJDK 21 image as the base image
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /

COPY wait-for-it.sh .

RUN chmod +x wait-for-it.sh

# Expose the debug port (default JDWP port is 5005)
EXPOSE 8080 5005

# Bind mount the JAR file at runtime, so no COPY command needed for the application
# CMD command to run the Java application with remote debugging enabled
CMD ["java", \
     "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", \
     "-jar", "/app.jar"]
