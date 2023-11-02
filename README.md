# Proposal Manager Application

This README outlines the steps to run the Proposal Manager application.

## Requirements

- Docker
- Docker Compose

## Instructions

Follow the steps below to get the application up and running.

### Step 1: Build the Docker Image

First, navigate to the root directory of the project where the `Dockerfile` is located. Then run the following command to build the Docker image.

````
docker build --build-arg JAR_FILE=build/libs/*.jar -t proposal-manager .
````

This command will build a Docker image named `proposal-manager` using the JAR files found in `build/libs/`.

### Step 2: Run the Application Using Docker Compose

Now, run the following command to start the application.

```
docker-compose up
```

This will start the application along with any other services defined in the `docker-compose.yml` file.

Swagger avaliable on http://localhost:8080/swagger-ui/index.html#/