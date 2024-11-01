# Getting Started

### Guide

To run the service locally, just run the command ```docker compose up -d```  from the root directory of the project. It will:
1. Execute Dockerfile
2. Build the project with ```mvn clean package```command
3. Package into docker-image and upload into local storage
4. Then start the services one by one

After all services are up and running, the service can be accessed via [swagger-ui](http://localhost:8080/swagger-ui/index.html)

API Details
===========

## Notes management ##

**Create a new note** - ```POST``` -
```http://localhost:8080/api/v1/notes```

**Delete a note by its id** - ```DELETE``` -
```http://localhost:8080/api/v1/notes/{id}```

**Update a note by its id** - ```PUT``` -
```http://localhost:8080/api/v1/notes/{id}```

**Get paginated list of notes** - ```GET``` -
```http://localhost:8080/api/v1/notes```

**Get a note by its id** - ```GET``` -
```http://localhost:8080/api/v1/notes/{id}```

**Get a note words stats by its id** - ```GET``` -
```http://localhost:8080/api/v1/notes/{id}/stats```

Error Handling
=============
- The data validation error code starts with 400
- The note not found error starts with 404
- Other server run time exception is 500

Sample error scenario response.

```
{
  "code": "NOTE_NOT_FOUND",
  "httpStatus": "NOT_FOUND",
  "message": "Note with specified id not found in the system"
}
```

# Production readiness
This service exposes [actuator](http://localhost:8081/actuator) metrics that could be ready to be collected by [Zipkin](https://zipkin.io/)

It also exposes `livenessstate` and `readinessstate` for orchestration management, for example **k8s**

# Docker Compose support

This project contains a Docker Compose file named `compose.yaml`.

# Tests

Test coverage - **95%** of lines