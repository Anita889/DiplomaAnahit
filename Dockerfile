FROM ubuntu:20.04

# Update package lists and install required packages
RUN apt-get update && apt-get install -y \
    openjdk-17-jdk \
    openjfx \
    wget \
    unzip

# Copy project files into the container
COPY . /DiplomaAnahit

# Set the working directory
WORKDIR /DiplomaAnahit

# Grant execute permissions to necessary scripts
RUN chmod +x gradlew && ./gradlew
RUN chmod +x shells/deploy-DockerFile.sh

# Set the default command
CMD ["sh", "shells/deploy-DockerFile.sh"]
