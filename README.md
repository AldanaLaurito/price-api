# Prices Api


## General info
This project is a little Java Spring API REST application that has one endpoint to obtain the price to apply for a product of a specific brand at a particular day and time.

Is developed using Java 11.0.16, Spring 2.7.3 and Gradle 7.4, along with other technologies for unit testing and API documentation.

- All dates use the <b>yyyy-MM-dd'T'HH:mm:ss</b> format. </n>
- Endpoint returns a json body.
- If no Price can be found, the application will return an error message indicating that not price was found.

## Run application

The application runs in the port 8080 by default.

Once in the project path, run this command to run the application:
~~~
gradlew bootRun
~~~


## Run tests

Once in the project path, run this command to run the tests:
~~~
gradlew test
~~~

## Trying the application

Once the application is running, the endpoint can be accessed either through Swagger, by completing the form of the endpoint and then clicking 'Try iy out', or by using Postman or other API platforms.

#### Link to access Swagger:
http://localhost:8080/swagger-ui/index.html

#### Example of url for Postman or other ways to consume API endpoints:
http://localhost:8080/api/v1/prices/obtainPrice?brand=1&product=35455&datetime=2020-06-15T10:00:00

##### Url parameters
- <b>brand</b>: Id of the brand. For example: 1 for Zara
- <b>product</b>: Id of the product. For example: 35455
- <b>datetime</b>: Date and time of the request in the <b>yyyy-MM-dd'T'HH:mm:ss</b> format. For example: 2020-06-15T10:00:00

## Endpoint documentation
http://localhost:8080/swagger-ui/index.html

Swagger documentation is available only while the application is running.