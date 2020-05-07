# spring-boot-api-demo

![Maven Tests](https://github.com/BrianMMcClain/spring-boot-api-demo/workflows/Maven%20Tests/badge.svg?branch=master)

This demo presents a limited REST API that could represent an inventory system for a shop. The inventory holds items, each with an ID, name, price, and count.

The API
---

The following operations are supported:

`GET /items`: Return all items in the inventory. Returns a JSON array.

`GET /items/{id}`: Get one specific item, looked up by the ID. Returns a JSON object, or `404` if the item does not exist.

`POST /items`: Create a new item. Must provide a name, price, and count as a JSON object. An ID will be automatically generated. Ex. `{"name": "My Item", "price": 12.99, "count": 11}`. Returns `201` if the creation was successful, as well as the new item as a JSON object.

`PUT /items/{id}`: Update an existing item, identified by the ID. Can accept a new price, count, or both provided as a JSON object. Ex. `{"price": 17.99, "count": 43}`. Returns the updated item as a JSON object, or `404` if the item does not exist.

`DELETE /items/{id}`: Delete an existing object, returning `200` if successful, or `404` if the object does not exist.

Running The Code
---

The code can be ran as a standard Spring Boot application:

`./mvnw spring-boot:run`

Sending Requests With cURL
---

```
> curl http://localhost:8080/items

[{"id":1,"name":"Keyboard","price":29.99,"count":76},{"id":2,"name":"Mouse","price":19.99,"count":43},{"id":3,"name":"Monitor","price":79.99,"count":7},{"id":4,"name":"PC","price":749.99,"count":2},{"id":5,"name":"Headphones","price":19.99,"count":14}]
```

```
> curl http://localhost:8080/items/1

{"id":1,"name":"Keyboard","price":29.99,"count":76}
```

```
> curl -XPOST http://localhost:8080/items -H "Content-Type: application/json" -d '{"name": "Speakers", "price": 49.99, "count": 30}'

{"id":6,"name":"Speakers","price":49.99,"count":30}
```

```
> curl -XPUT http://localhost:8080/items/1 -d '{"count": 75, "price": 27.99}' -H "Content-Type: application/json"

{"id":1,"name":"Keyboard","price":27.99,"count":75}
```

Tests
---

Tests are also provided for each operation, located in the [HttpRequestsTest.java](https://github.com/BrianMMcClain/spring-boot-api-demo/blob/master/src/test/java/com/github/brianmmcclain/springbootapidemo/HttpRequestsTest.java) file.

By default, all tests are ran when the code is built with Maven (ie. `./mvnw clean package`). You can also run the tests independently with `./mvnw test`.
