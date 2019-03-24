# Amazing Co



## Running

First of all, make sure [docker](https://www.docker.com/) is installed

First, build the docker image:

```shell
make
```

Then, start the service:

```shell
make run
```

Service will be running on port `8080`

## Developing

The API was developed in Java, using spring boot as framework and gradle as the
build tool.

### Packages structure

Despite maven/gradle directory standart, this code have the following packages:

```shell
├── config          --- Spring configuration classes
├── controllers     --- Controllers of application
│   └── dtos        --- DTO to map JSON objects
├── exceptions      --- Application specific exceptions
├── models          --- JPA entities 
├── repositories    --- Spring JPA repositories
└── services        --- Application services

```

### Tests

The testing strategy is based on [Toby Clemson's guide](https://www.martinfowler.com/articles/microservice-testing/#conclusion-summary) 
for testing microservices.

The application was built using TDD, so feel free to look at commit history
to have an idea on how it was.

#### Unit test

Each piece of the application has their own unit test. Those are mostly regular
unit test, using JUnit. 

The only exception is the Controller unit tests, that 
uses [spring's MVC testing slice](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html#boot-features-testing-spring-boot-applications-testing-autoconfigured-mvc-tests).
Those tests (e.g. NodeControllerTest) are checking input validation or other simple
controller specific logics

#### Database Integration test

Tests that talks to H2 database. They check if some repository generated queries
work and some logic that uses JPA Repositoies.

All of them are set up with [Spring @DataJpaTest](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html#boot-features-testing-spring-boot-applications-testing-autoconfigured-jpa-test),
so only spring data context is initialized.

Examples are: CompanyRepositoryIntegrationTest and NodeRepositoryIntegrationTest

#### Component test

Higher level tests that exercise all layers of the application. 

The setup is done with [`@SpringBootTest`](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html#boot-features-testing-spring-boot-applications)
and an actual server is started. 
[Rest-assured](http://rest-assured.io/) is being used to make requests and verify responses.

Because these tests are more expensive, there are fewer of them as they should not verify edge cases.

