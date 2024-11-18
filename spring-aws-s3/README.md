# AWS S3

Playing around with AWS S3 in a Springboot application based
on [this documentation](https://docs.awspring.io/spring-cloud-aws/docs/3.2.0/reference/html/index.html#starter-dependencies)

## How to test it

From the root folder, execute:

> ./mvnw clean package \
> java -jar spring-aws-s3/target/app.jar --spring.config.additional-location=spring-aws-s3/src/main/resources/aws_credentials.properties

This will start the Springboot application on port 8080.
To interact with the API:

> curl -v --include  --form books=@spring-aws-s3/src/test/resources/books.json http://localhost:8080/books/import
> --data '{"title":"A Study in Scarlet"}'

> curl http://localhost:8080/books

The response body should contain the imported books, populated with an `id`.