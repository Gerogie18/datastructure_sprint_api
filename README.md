# Binary Search Tree API
This project is a Spring Boot application that provides a RESTful API for creating and viewing Binary Search Trees (BSTs). Its primary objective is to demonstrate the implementation of the BST data structure within a back-end service.

## Synopsis: Understanding Binary Search Trees
A Binary Search Tree (BST) is a data structure used for organizing and sorting data efficiently. Its fundamental principle is a specific ordering rule applied to every node: for any given node, all values in the left subtree are less than or equal to the node's value, while all values in the right subtree are greater than it.

The final structure of the tree is determined exclusively by the order in which data is inserted. Inserting data that is already sorted results in a lopsided, or unbalanced, tree, which reduces its efficiency. Conversely, inserting data in a non-sequential order can produce a balanced tree, where the left and right subtrees are of similar height. This balanced structure is optimal for performance.

The primary advantage of a balanced BST is its efficiency in searching for data. Because of its ordered nature, a search algorithm can eliminate half of the remaining data at each step, making it very fast. This efficiency makes BSTs suitable for practical applications such as organizing file systems, indexing databases, and implementing autocomplete dictionaries.

While BSTs are often implemented in memory using node objects with pointers, they can also be stored in other ways. A common alternative representation uses an array, where the position of child nodes can be calculated mathematically from a parent node's index in the array.


## Tech Stack
- Java 24
- Spring Boot 3
- Maven
- MySQL
- Docker

## Local Development Setup
1. Prerequisites
- JDK 24 or later
- Apache Maven
- A running MySQL instance
- Docker (Optional)

2. Clone the Repository
```Bash

git clone https://github.com/Gerogie18/datastructure_sprint_api.git
cd datastructure_sprint_api
```

3. Database Setup 
- Ensure your MySQL server is running and create a database for the project.

```SQL

CREATE DATABASE binary-tree-db;
```
4. Configure the Application
- In the src/main/resources directory, update the datasource properties with your local database credentials.

```Properties

spring.datasource.url=jdbc:mysql://localhost:3306/binary-tree-db
spring.datasource.username=your_mysql_user
spring.datasource.password=your_mysql_password
```
5. Run the Application
- The application can be run from an IDE by executing the main method in the primary application class, or from the command line using the Maven wrapper:

```Bash

./mvnw spring-boot:run
The API will be available at http://localhost:8080. On the first launch with an empty database, the DataSeeder will run to populate initial data.
```
6. (Optional) Run with Docker
   - Build the Docker image from the project's root directory:

```Bash

docker build -t binary-tree-db .
```
- Run the container, providing the database credentials as environment variables:

```Bash

docker run -p 8080:8080 \
-e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/binary-tree-db \
-e SPRING_DATASOURCE_USERNAME=your_mysql_user \
-e SPRING_DATASOURCE_PASSWORD=your_mysql_password \
binary-tree-db
```
Note: host.docker.internal is a special DNS name that allows the Docker container to connect to services running on the host machine.

## API Reference
### Users
#### Get All Users
Method: GET
URL: /api/users
Description: Retrieves a list of all users.
Response:

```JSON

[
{
"id": 1,
"name": "Alice",
"password": null
},
{
"id": 2,
"name": "Bob",
"password": null
}
]
```
### Trees
#### Create a Tree
Method: POST
URL: /api/trees
Description: Creates a new binary search tree from a list of numbers for a specified user.
Request Body:

```JSON

{
"userId": 1,
"numbers": [50, 25, 75, 15, 30]
}
```
Response: The full TreeRecord object as it was saved in the database.

```JSON

{
"id": 1,
"inputNumbers": "50, 25, 75, 15, 30",
"jsonTree": "{\"value\":50,\"left\":{\"value\":25,\"left\":{\"value\":15,...},\"right\":{\"value\":30,...}},\"right\":{\"value\":75,...}}",
"user": {
"id": 1,
"name": "Alice",
"password": null
}
}
```
#### Get Tree History for a User
Method: GET
URL: /api/trees/user/{userId}
Description: Retrieves all previously created trees for a specific user.
Example URL: /api/trees/user/1
Response: A list of TreeRecord objects.

```JSON

[
{
"id": 1,
"inputNumbers": "50, 25, 75, 15, 30",
"jsonTree": "...",
"user": { ... }
},
{
"id": 2,
"inputNumbers": "1, 2, 3",
"jsonTree": "...",
"user": { ... }
}
]
```