# Ejercicio Java Spring Boot para la empresa Evertec

### Building and running the application

#### Using Docker Compose

When you're ready, start the application by running:
`docker compose up --build`.

#### Using Command Line

To build the Docker image, run:
`docker build -t myapp .`.

Then, start your application with the following command:
`docker run -p 8080:8080 myapp`.

### Using Maven

You can also use Maven to package the application. Run the following command:
`mvn clean package`.
This will generate a JAR file in the `target` directory. You can then run the application with:
`java -jar target/<name-of-your-jar>.jar`.
Replace `<name-of-your-jar>` with `users-0.0.1-SNAPSHOT.jar`.

The application will be available at http://localhost:8080.

### Testing your Application

#### API Endpoints Overview

To test the application's API, you can use tools like **Postman**, **cURL**, or the Swagger UI available at:  
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html).

