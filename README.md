# Car Inventory Application

This application is a web-based inventory system for managing car data. It allows users to add, search, and download car information. The application uses Spring Boot, Thymeleaf, PostgreSQL, and Swagger for API documentation.

## Features

- **Add Car**: Add new cars to the inventory.
- **Search Cars**: Search for cars using various criteria such as length, weight, velocity, and color.
- **Download Car List**: Download the search results in XML format.
- **API Documentation**: Swagger UI for interactive API documentation and testing.

## Technologies

- **Backend**: Java, Spring Boot
- **Frontend**: Thymeleaf
- **Database**: PostgreSQL
- **API Documentation**: Swagger (springdoc-openapi)
- **Containerization**: Docker, Docker Compose

## Prerequisites

- Java 21
- Docker and Docker Compose
- PostgreSQL (optional if not using Docker)

## Setup and Running Locally

- [ ] Clone the repository: git clone https://github.com/alexandruhoza/car-inventory.git
- [ ] Navigate to the project directory: cd car-inventory
- [ ] Build the project using Maven: mvn clean install
- [ ] Docker Setup: Ensure Docker and Docker Compose are installed on your machine.
- [ ] Docker Compose Configuration: Make sure the docker-compose.yml file is configured to start the PostgreSQL database
- [ ] Build and Start Containers: docker-compose up --build
- [ ] Accessing the Application: The web application will be accessible at http://localhost:8080/cars/search.
     Swagger UI for API documentation will be available at http://localhost:8080/swagger-ui.html.

## Testing
- [ ] Tests are provided for the service and controller layers.


## Contribution

- [ ] Feel free to fork the repository and make pull requests. For major changes, please open an issue first to discuss what you would like to change.