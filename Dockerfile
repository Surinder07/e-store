FROM openjdk:17

# Copy the JAR file into the container
COPY target/estore-java.jar estore-java.jar

# Expose the port on which the Java application is listening
EXPOSE 8080

# Set the entry point to run the JAR file
ENTRYPOINT ["java", "-jar", "estore-java.jar"]
