# :oncoming_taxi: Taxi-service :oncoming_taxi:
___

## Description:
`A simple web-application that can be used as a software for taxi company. Support authentication,registration and CRUD operations`

## Features:
___
- registration as a driver
- authentication as a driver
- create/update/remove:
    - a manufacturer
    - a driver
    - a car
- display all:
    - manufacturers
    - drivers
    - cars
- add driver to the car

## Structure:
___
### In this project I implement n-tier architecture with 4 layer: db, dao, service, controller.

## Technologies used:
___
- JDBC


- Servlets


- Logger - I used log4j.


- Tomcat


- HTML


- CSS

## How to start ?
___
1. Enter your DB connection settings in ConnectionUtil.class <br>
```java
    public class ConnectionUtil {
        private static final String URL = "YOUR_URL"; //here
        private static final String USERNAME = "YOUR_USERNAME"; //here
        private static final String PASSWORD = "YOUR_PASSWORD"; //and here
        private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
```
2. Enter path to your file for logs in log4j2.xml <br>
```
    <File name="LogToFile" fileName="YOUR_FILE"> //here
        <PatternLayout>
            <Pattern>%d %p %c:%L %m%n</Pattern>
        </PatternLayout>
    </File>
```
3. Edit Rub/Debug configuration <br><br>
   <img src="https://devcolibri.com/cp/wp-content/uploads/2014/03/4249_2.png">
4. Select local TomCat <br><br>
   <img src="https://devcolibri.com/cp/wp-content/uploads/2014/03/4249_3.png">
5. In the tab `Deployment`, press add artifacts: <br><br>
   <img src="https://devcolibri.com/cp/wp-content/uploads/2014/03/4249_6.png">
6. Run your TomCat :arrow_forward: 
