# :oncoming_taxi: Taxi-service :oncoming_taxi:
___

## Description:
`A simple web-application that support authentication,registretion and CRUD operations`

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
1. All classes of the DAO layer are located in the folder `dao.impl` [dao](https://github.com/k1llzers/taxi-service/tree/main/src/main/java/taxi/dao/impl)
2. All classes of the Service layer are located in the folder `service.impl` [service](https://github.com/k1llzers/taxi-service/tree/main/src/main/java/taxi/service/impl)
3. All classes of the Controller layer are located in the folder `controller` [controller](https://github.com/k1llzers/taxi-service/tree/main/src/main/java/taxi/controller)

## Technologies used:
___
- JDBC <br>
```java
    @Override
public Car create(Car car) {
        String insertQuery = "INSERT INTO cars (model, manufacturer_id)"
        + "VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
        PreparedStatement createCarStatement =
        connection.prepareStatement(
        insertQuery, Statement.RETURN_GENERATED_KEYS)) {
        createCarStatement.setString(1, car.getModel());
        createCarStatement.setLong(2, car.getManufacturer().getId());
        createCarStatement.executeUpdate();
        ResultSet resultSet = createCarStatement.getGeneratedKeys();
        if (resultSet.next()) {
        car.setId(resultSet.getObject(1, Long.class));
        }
        }
```
- Web API <br>
```java
    @Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/cars/add.jsp").forward(req, resp);
        }
```
- Logger <br>
```java
    catch (AuthenticationException e) {
        logger.error("Can`t find the driver with login: " + login);
        req.setAttribute("errorMsg",e.getMessage());
        req.getRequestDispatcher("/WEB-INF/views/authentication/login.jsp").forward(req,resp);
        }
```
## How to start ?
___
1. Enter your DB connection settings in ConnectionUtil.class <br>
```java
    public class ConnectionUtil {
  private static final String URL = "jdbc:mysql://127.0.0.1:3306/taxi_app"
          + "?useUnicode=true&useJDBCCompliantTimezoneShift"
          + "=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
  private static final String USERNAME = "YOUR_USERNAME"; //here
  private static final String PASSWORD = "YOUR_PASSWORD"; //and here
  private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
```
2. Edit Rub/Debug configuration <br><br>
   <img src="https://devcolibri.com/cp/wp-content/uploads/2014/03/4249_2.png">
3. Select local TomCat <br><br>
   <img src="https://devcolibri.com/cp/wp-content/uploads/2014/03/4249_3.png">
4. In the tab `Deployment`, press add artifacts: <br><br>
   <img src="https://devcolibri.com/cp/wp-content/uploads/2014/03/4249_6.png">
5. Run your TomCat :arrow_forward: 
