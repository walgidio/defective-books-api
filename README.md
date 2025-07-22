# 📚 Defective Books Challenge

REST API built with Java and Spring Boot to manage **books** and **defective editions**. The API supports creating, reading, updating, and deleting books, as well as registering and listing defective editions.

## 🚀 Technologies Used

- Java 17  
- Spring Boot 3.x  
- Spring Web  
- Spring Data JPA  
- Hibernate  
- MySQL (via Docker)  
- Maven  
- Jakarta Bean Validation  
- AssertJ 5 & Mockito (unit tests)  
- Lombok  
- Swagger/OpenAPI (API documentation)

## 📦 Installation and Running the Application

### ✅ Prerequisites

- Docker and Docker Compose  
- Java 17+  
- Maven 3.8+  
- An IDE (IntelliJ, Eclipse, or VS Code)

### ⚙️ 1. Start the MySQL container with Docker Compose
```
docker-compose up -d
```
This will create and start a MySQL container with a database accessible at `localhost:3306`.

### ⚙️ 2. Configure `application.properties`

Check `src/main/resources/application.properties`:
```
spring.datasource.url=jdbc:mysql://localhost:3306/mysql  
spring.datasource.username=<db_username>  
spring.datasource.password=<db_password>  

spring.jpa.hibernate.ddl-auto=update  
spring.jpa.show-sql=true
```
### ▶️ 3. Run the API
```
mvn clean package  
java -jar target/challenge-0.0.1-SNAPSHOT.jar
```
API will be available at:
```
http://localhost:8080
```
## 📑 API Documentation (Swagger)

After running the application, you can access the **Swagger UI** at:
```
http://localhost:8080/swagger-ui/index.html
```
This interactive documentation allows you to test all endpoints directly from the browser.

## 🧪 Running Tests
```
mvn test
```
Unit tests are written using AssertJ and Mockito.

## 📌 Main Endpoints

### 📘 Books (`/books`)

| Method | Endpoint           | Description            |
|--------|--------------------|------------------------|
| GET    | `/books`           | List all books         |
| GET    | `/books/{id}`      | Get a book by ID       |
| POST   | `/books`           | Create a new book      |
| PUT    | `/books/{id}`      | Update an existing book|
| DELETE | `/books/{id}`      | Delete a book          |

### ❌ Defective Editions (`/defects`)

| Method | Endpoint   | Description                  |
|--------|------------|------------------------------|
| GET    | `/defects` | List all defective editions  |
| POST   | `/defects` | Register a defective edition |

## 📥 Example Payloads

### ➕ Create Book

POST /books  
```
{
    "publisher": "Prentice Hall",
    "publishedYear": 2008,
    "batchNumber": "42-314-1592",
    "edition": {
        "isbn": "978-0-13-235088-4",
        "title": "Clean Code: A Handbook of Agile Software Craftsmanship",
        "authorName": "Robert C. Martin",
        "number": 1
    }
}
```
### ➕ Create Defective Edition

POST /defects  
```
  {
    "edition":
    {
      "title": "Java, a Beginner's Guide 2",
      "authorName": "Kellsie Havock",
      "number": 2,
      "isbn": "1-23-456789-2"
    },
    "defectCode": "67038-100",
    "affectedBatches":
    [
      "34-821-5678"
    ]
  }
```
## 🐳 docker-compose.yml
```
version: '3.8'  

services:  
  mysql:  
    image: mysql:8  
    container_name: mysql  
    restart: always  
    environment:  
      MYSQL_ROOT_PASSWORD: <db_password>  
      MYSQL_DATABASE: <db_name>  
    ports:  
      - "3306:3306"
```
## 🧑‍💻 Author

Developed by [Walgídio Santos](https://github.com/walgidio)

## 📄 License

This project is licensed under the MIT License. See the LICENSE file for details.
