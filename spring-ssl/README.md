# Events with Spring

Playing around with events in Spring based
on [this documentation](https://spring.io/blog/2023/06/07/securing-spring-boot-applications-with-ssl)
.

## The Story

I wanted to play around with mutual TLS in Spring

The goal is to have a client get the books from the bookshelf under a SSL-enabled connection to the server, with the
client properly authenticated.

## Development

It's a Spring boot application exposed through
the [BooksController](spring-ssl-server/src/main/java/com/pedrodovale/experimentingwith/spring/ssl/server/controller/BookController.java)
.
Calling the `/books` endpoint in [BooksController](spring-ssl-client/src/main/java/com/pedrodovale/experimentingwith/spring/ssl/client/SpringSslClientApplication.java)
using a RestClient bean to get all the books. This RestClient is ready to validate the server certificate and present
the client certificate to establish the secure connection. 

## How to test it

From the spring-ssl folder, execute:

> ./init.sh

This will create the server and client keystore and truststore, with root and leaf certificate

Then deploy the server:
> mvn spring-boot:run -pl spring-ssl-server

and when the server is up, execute the client runner:

> mvn spring-boot:run -pl spring-ssl-client

The stdout should display the following:

> books: [Book(id=1, title=A Study in Scarlet)]