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

## Using

A [postman collection](amazing-co-api.postman_collection.json) is provided to
help using the API. There is an example for every endpoint.

Also, looking at the tests might help understanding the API, specially component test

### Company

First of all, it is necessary to create a company:

```
POST /companies
Content-Type: application/json
{
    "name": "<company-name>"
}
```

This will reply a json like the following:

```
{
    "id": <company-id>,
    "name": "<comany-name>"
}
```

Company names are case sensitive and must be unique.

A company can be deleted by doing:

```
DELETE /companies/<company-id>
```

Be aware deleting a company will DELETE ALL nodes that belong to it.

### Building a Tree 

Every company will have a set of nodes, that form its tree. 
All companies have only one tree, that has only on root node. 

To create a root node, use the following:

```
POST /companies/<company-id>/nodes
Content-Type: application/json
{
	"name": "<name-of-root-node>"
}
```

Then you can create its children:

```
POST /companies/1/nodes/<parent-node>/children
Content-Type: application/json
{
	"name": "<name-of-child-node>"
}
```

Node names are case sensitive and unique within a company.

### Children of a Node

The children of a node can be retrieved by doing the following request:

```
GET /companies/<company-id>/nodes/<node-name>/children
```

The response will be a JSON like the following:

```
[
    {
        "name": "A",
        "parent": "root",
        "height": 1,
        "children": []
    },
    {
        "name": "B",
        "parent": "root",
        "height": 1,
        "children": []
    }
]
```

### Change Node's Parent

To change a node's parent, make the following request:

```
PUT /companies/<company-id>/nodes/<node-to-be-changed>
Content-Type: application/json
{
	"newParent": "<new-parent-node>"
}
```

### Limitations

For a matter of keeping things simple, the scope has some limitations:
* A node can't be deleted, only the entire tree by deleting the company
* Can not change root node's parent
* Can not move a node from one company's tree to another company's tree


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

### Limitations

To keep things simple
* H2 was used instead of a more robust database
* No migration tool is being used, hibernate 