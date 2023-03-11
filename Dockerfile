# Use an official OpenJDK runtime as a parent image
FROM adoptopenjdk/openjdk17:latest

RUN mkdir /pbl/src/server

# Set the working directory to /app
WORKDIR /pbl/src/server

# Copy the current directory contents into the container at /app
COPY . /pbl/src/server

# Compile any Java source files
RUN javac Atendimento.java

EXPOSE 8080

# Set the default command to run when the container starts
CMD ["java", "Atendimento"]