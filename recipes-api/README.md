Recipes API — Backend README

Spring Boot service that loads recipes from the external dataset (https://dummyjson.com/recipes) into an in-memory H2 database and exposes REST endpoints to search and retrieve recipes.
Implements resilient, optimized loading, Hibernate Search full-text search, environment layering, logging, validation, tests and Swagger/OpenAPI docs.

# build
mvn clean package -DskipTests=false

# run (dev profile)
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# After services are started you can check apis using swaggerUI
Swagger / API docs

springdoc-openapi-ui included.
Swagger UI: http://localhost:8080/swagger-ui/index.html

OpenAPI JSON: http://localhost:8080/v3/api-docs