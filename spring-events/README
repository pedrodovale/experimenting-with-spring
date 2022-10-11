# Events with Spring

Playing around with events in Spring based
on [this documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#context-functionality-events)
.

## The Story

As an example, this module contains an application that allows clients notifying about the
existence of a new book that is saved to the bookshelf. Clients can then obtain the ID of all
the books in the bookshelf.

The goal is that the act of saving the book to the bookshelf is an event.

## Development

It's a Spring boot application exposed through
the [BooksController](src/main/java/com/pedrodovale/experimentingwith/spring/events/controller/BooksController.java)
.
Calling the `/new-book-notify` endpoint triggers the publishing of
a [NewBookEvent](src/main/java/com/pedrodovale/experimentingwith/spring/events/NewBookEvent.java).
The [NewBookEventListener](src/main/java/com/pedrodovale/experimentingwith/spring/events/NewBookEventListener.java)
is subscribed to the event and is responsible for saving the new book information to the bookshelf.

## How to test it

From the root folder, execute:

> ./mvnw clean package \
> java -jar spring-events/target/app.jar

This will start the Springboot application on port 8080.
To interact with the API:

> curl http://localhost:8080/books/notify-new-book -X POST --header "Content-Type: application/json"
> --data '{"title":"A Study in Scarlet"}'

> curl http://localhost:8080/books

The response body should contain a non-empty list.