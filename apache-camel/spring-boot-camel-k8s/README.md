# Spring Boot Camel Kubernetes Project

This project is a Spring Boot application that integrates Apache Camel for routing and process orchestration, and is designed to run on Kubernetes. 

## Project Structure

```
spring-boot-camel-kubernetes
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           └── springbootcamel
│   │   │               ├── SpringBootCamelApplication.java
│   │   │               ├── config
│   │   │               │   └── CamelConfig.java
│   │   │               └── routes
│   │   │                   └── routes.com.c4c.camel.SampleRoute.java
│   │   ├── resources
│   │       ├── application.yml
│   │       └── logback-spring.xml
│   └── test
│       └── java
│           └── com
│               └── example
│                   └── springbootcamel
│                       └── com.c4c.camel.SpringBootCamelApplicationTests.java
├── build.gradle
├── settings.gradle
├── Dockerfile
├── kubernetes
│   ├── deployment.yaml
│   ├── service.yaml
│   └── configmap.yaml
└── README.md
```

## Prerequisites

- Java 21
- Gradle
- Docker
- Kubernetes cluster

## Setup Instructions

1. **Clone the repository:**
   ```
   git clone <repository-url>
   cd spring-boot-camel-kubernetes
   ```

2. **Build the project:**
   ```
   ./gradlew build
   ```

3. **Run the application locally:**
   ```
   ./gradlew bootRun
   ```

4. **Build the Docker image:**
   ```
   docker build -t spring-boot-camel-kubernetes .
   ```

5. **Deploy to Kubernetes:**
   - Apply the ConfigMap:
     ```
     kubectl apply -f kubernetes/configmap.yaml
     ```
   - Deploy the application:
     ```
     kubectl apply -f kubernetes/deployment.yaml
     ```
   - Expose the service:
     ```
     kubectl apply -f kubernetes/service.yaml
     ```

## Usage

Once deployed, you can access the application through the exposed service. The application utilizes Apache Camel for routing and orchestration of processes.

## Testing

Unit tests are included in the `src/test/java/com/example/springbootcamel/com.c4c.camel.SpringBootCamelApplicationTests.java` file. You can run the tests using:
```
./gradlew test
```

## License

This project is licensed under the MIT License.