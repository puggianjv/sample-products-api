# Products API #

Rest API with Java version 1.8, Maven, Spring and MySql to store a list of products from different suppliers.

## You need first ##

* Java 1.8
* Maven
* MySQL

## Running the project ##

### Using java ###
 You can run it on your local machine by connecting to a local MySQL. To run it, configure the src/main/resources/application.properties file or use the following environment variables: [APP_PORT, DB_HOST, DB_PORT, DB_DATABASE, DB_USERNAME, DB_PASSWORD].
 * Run it with the command `mvn spring-boot:run`

### Using docker ###
Requires docker-compose installed.

* Run it with `mvn clean package -DskipTests && docker-compose up --build`

OBS.: suppliers are not created by default on docker initialization. You can create them with create supplier endpoint.

## Usage ##

### Endpoints ###

* `v1/products?page={page}&size={size}` [GET]: list products.
* `v1/products/{id}` [GET]: get product.
* `v1/products` [POST]: create product.
* `v1/products/{id}` [PATCH]: update product name and price.
* `v1/products/{id}` [DELETE]: delete product.
* `v1/products/{id}/add` [PATCH]: add or remove product quantity of stock.
* `v1/suppliers/` [POST]: create supplier.

### Swagger ###

This application is configured with Swagger-ui console. You can access it on `/v1/swagger-ui.html`

### Postman ###

You can also test this application with this Postman example collection:

 * [Download collection](https://drive.google.com/file/d/1-1Ch_-vXG4wGF9qJmxpt5G1x5XQH_0mC/view?usp=sharing)