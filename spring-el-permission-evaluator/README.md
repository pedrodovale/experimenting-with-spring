# Spring Validation And Access

[//]: # (TODO)

- https://docs.spring.io/spring-security/reference/servlet/authorization/expression-based.html#el-permission-evaluator
- https://www.baeldung.com/spring-security-create-new-custom-security-expression
- https://segmentfault.com/a/1190000041728076/en

## The Story

[//]: # (TODO)

## Development

[//]: # (TODO)

## How to test it

[//]: # (TODO)

add book:

> curl http://localhost:8080/books --include --user alice:S3cr3t! \ \
> --request POST \ \
> --header "Content-Type: application/json" \ \
> --data '{"title": "A Study in Scarlet"}'

get book:

> curl http://localhost:8080/books/51c799b9-e3ae-44b7-8f0e-c6c0d7bfc119 --include --user eve:S3cr3t!

rent book:

> curl http://localhost:8080/books/51c799b9-e3ae-44b7-8f0e-c6c0d7bfc119/rent --include --user bob:
> S3cr3t!# Spring Expression Language Permission Evaluator

Playing around with Spring Evaluation Language (SpEL) Permission Evaluator for custom expressions in the @PreAuthorize annotation.
The code is divided in the following use cases:
- A bookshelf and the ability to rent books from it;
- A book club where readers can be added and attend book club sessions.

The resources that helped me to experiment this:

- https://docs.spring.io/spring-security/reference/servlet/authorization/expression-based.html#el-permission-evaluator
- https://www.baeldung.com/spring-security-create-new-custom-security-expression
- https://segmentfault.com/a/1190000041728076/en

## The Development Story

When trying to limit access to certain features in an API, I usually use the [@PreAuthorize annotation](https://docs.spring.io/spring-security/reference/5.7.3/servlet/authorization/expression-based.html#_access_control_using_preauthorize_and_postauthorize). It allows for the most common requirements for API, e.g. filter by role, by leveraging the information already present in the security context.

However, for more complex operations, the default expression methods are not enough. Particularly, when a user who has a role has more privileges for a given resource than another user with the same role.

Of course Spring proposes a way to overcome this. Create a bean whose class implements PermissionEvaluator. The `hasPermission()` method can then be used to assert access control based on the existing user and the target domain object that will be used to decide whether the user can proceed.

Having this idea in mind, I created the BookShelfPermissionEvaluator. The `hasPermission()` method implementation asserts that user who wants to rent a book, must be registered and the book must exist in the shelf.

Then I noticed that the hasPermission() method is rather limiting and the name is not self-explanatory. Yes, the user must have permission to rent the book, but what is the criteria? For that, the developer trying to understand this code has to check the method implementation.

To overcome this, I created a new use case.
 
In the Book Club example, a reader may be the Book Club master for a given book. This means that he/she is the coordinator of the book club sessions for that very same book: only he/she can start a new session. The rest of the readers (that must be part of the club) can only attend an open session.

In summary, the access control question that needs to be answered by the application is: is the user that is attempting to start a session the book club master for that book?
 
 For this, I implemented a custom SpEL expression method `isBookMaster()`. The goal was to have the @PreAuthorize('isBookMaster(#bookId)'), where the bookId is the path variable in the controller method (check `BookClubController.startBookClubSession`).

## How to test it

By default, the application has 3 users registered:

- Alice: A librarian
- Bob: A registered reader
- Eve: Just a reader

package the module to generate the jar
> ./mvnw clean package -pl=spring-el-permission-evaluator

run the jar:

> java -jar spring-el-permission-evaluator/target/app.jar

the application will start on port 8080. Now you can start making API requests.

### Book shelf example

add book:

> curl http://localhost:8080/books --include --user alice:S3cr3t! \ \
> --request POST \ \
> --header "Content-Type: application/json" \ \
> --data '{"title": "A Study in Scarlet"}'

Only Alice can do it, because the operation is limited to users with the LIBRARIAN role.

The `Location` header value contains the UUID that represents the bookId.

get book:

> curl http://localhost:8080/books/51c799b9-e3ae-44b7-8f0e-c6c0d7bfc119 --include --user eve:S3cr3t!

All above users can do it, because the operation is only limited to users with the READER, LIBRARIAN role.

rent book:

> curl http://localhost:8080/books/51c799b9-e3ae-44b7-8f0e-c6c0d7bfc119/rent --include --user bob:
> S3cr3t!

Only Bob can do it, because the operation is limited to users with the 'registered' authority.

### Book Club example

1. You still need to add a book to the bookshelf, so check the [Bookshelf example](#book-shelf-example).

2. Add readers to the book club:

> curl http://localhost:8080/book-club/add-reader?reader=bob --include --user alice:S3cr3t!
>  
> curl http://localhost:8080/book-club/add-reader?reader=eve --include --user alice:S3cr3t! 

Only Alice can add readers to the book club, because the operation is limited to users with the LIBRARIAN role.

3. Appoint Bob as club master for the book that was previously added:

> curl http://localhost:8080/book-club/appoint-book-master/51c799b9-e3ae-44b7-8f0e-c6c0d7bfc119?reader=bob --include --user alice:S3cr3t!

Only Alice can appoint a book club master for a given book, because the operation is limited to users with the LIBRARIAN role.

4. Start a book club session for a given book:

> curl http://localhost:8080/book-club/start-session?bookId=51c799b9-e3ae-44b7-8f0e-c6c0d7bfc119 --include --user bob:S3cr3t!

Only Bob can do it because the operation is limited to the book club master for book provided

5. Attend the session

> curl http://localhost:8080/book-club/attend-session?bookId=51c799b9-e3ae-44b7-8f0e-c6c0d7bfc119 --include --user eve:S3cr3t!

Both Eve and Bob can do it, but since Bob starter the session for this book, he is already part of the session.

6. Get readers that attended book sessions

> curl http://localhost:8080/book-club/get-readers-sessions --user alice:S3cr3t!

Only Alice can get these readers, because the operation is limited to users with the LIBRARIAN role.
