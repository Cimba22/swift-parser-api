# Swift API Project

This project provides a REST API to parse and store SWIFT codes from a CSV file. It is built using Spring Boot and Java, and it supports database persistence for managing SWIFT code data.

## Features

- Parse and store SWIFT codes from a CSV file.
- Expose RESTful API endpoints to retrieve, add, and delete SWIFT codes.
- Supports data storage in a relational database.
- Easily configurable to run in both Docker and non-Docker environments.

## Prerequisites

Before running the project, ensure the following are installed on your system:

### Required Software

- [Java 17+](https://openjdk.java.net/projects/jdk/17/)
- [Maven](https://maven.apache.org/)
- [Docker](https://www.docker.com/get-started) (if you want to run the project in a Docker container)
- [Postgres](https://www.postgres.com/) or any other database (if running the project without Docker)


## Running the Project Without Docker

### 1. Clone the Repository

Clone the project repository to your local machine:


`[git clone https://github.com/your-username/swift-api.git]`
or 
`(https://github.com/Cimba22/swift-parser-api.git)`


```cd swift-api```


### 2. Configure Database Connection
 - Use the connection from an existing database image in the `docker-compose` file, or
 - Connect to your own database by updating the `application.yml` file with the correct connection details:
```yml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/yourDatabaseName
    username: yourUser
    password: yourPassword
```

### 3. Build the Project
Use Maven to build the project:
```bash
./mvnw clean install
```

### 4. Run the Application
Once the build is complete, run the application:
```bash
./mvnw spring-boot:run
```

### Running the Project with Docker
### 1. Clone the Repository
### 2. Build and Run the Docker Container
```bash
docker build -t swift-api .
docker run -p 8080:8080 swift-api
```

The application will start on `http://localhost:8080.`

### 5. Access the API
You can now access the REST API at the following endpoints:
- `GET /v1/swift-codes/{swiftCode}` - Get bank details and its branches (if exist) by SWIFT code.
- `GET /v1/swift-codes/country/{countryISO2}` - Get all banks and branches in the provided country.
- `POST /v1/swift-codes/` - Add a new SWIFT code.
- `DELETE /v1/swift-codes/{swiftCode}` - Delete a bank by SWIFT code.The SWIFT code, Bank Name and CountryISO2 should to matches the one in the database.

### 6. Load the SWIFT Code CSV
Make sure the `Interns_2025_SWIFT_CODES - Sheet1.csv` file is placed in the `src/main/resources/` directory. The application will automatically load and parse this CSV file on startup.

- If you want to use a different file, you should place it in the `src/main/resources/` directory and modify the `loader/DataLoader.java` file to point to your file (replace `YOUR_CSV_FILE`):
```Java
@Override
    public void run(String... args) throws Exception {
        swiftCodeParser.parseAndSave("src/main/resources/YOUR_CSV_FILE");
    }
```



