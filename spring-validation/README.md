# Springboot Validation

[//]: # (TODO)

## The Story

[//]: # (TODO)

## Development

[//]: # (TODO)

## How to test it

[//]: # (TODO)

From the root folder, execute:

> ./mvnw clean package \
> java -jar spring-validation/target/app.jar

This will start the Springboot application on port 8080.
To interact with the API:

add book:

> curl http://localhost:8080/books --include --request POST --header "Content-Type:
> application/json"
> --data '{"title":"A Study in Scarlet"}'

The successful 201 response should include the Location HTTP header with the book location.

get book:

> curl http://localhost:8080/books/99fa42b7-a28d-4a7f-bc3a-739d3620c48b --include
