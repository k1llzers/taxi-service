package taxi.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import taxi.dao.CarDao;
import taxi.model.Car;
import taxi.model.Driver;
import taxi.model.Manufacturer;
import taxi.service.impl.CarServiceImpl;

public class CarServiceTest {
    @InjectMocks
    private static CarService carService;
    @Mock
    private CarDao carDao;
    private Car car;

    @BeforeAll
    static void beforeAll() {
        carService = new CarServiceImpl();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        Driver alice = new Driver("Alice","alice@i.ua","1111","65434524");
        alice.setId(1L);
        Manufacturer volvo = new Manufacturer("Volvo","Switzerland");
        volvo.setId(1L);
        car = new Car("XC90",volvo);
        car.setId(1L);
        car.setDrivers(List.of(alice));
    }

    @Test
    public void create_Ok() {
        Manufacturer manufacturer = new Manufacturer("Volvo","Switzerland");
        manufacturer.setId(1L);
        Car createdCar = new Car("XC90",manufacturer);
        Car expected = new Car("XC90",manufacturer);
        expected.setId(1L);
        Mockito.when(carDao.create(createdCar)).thenReturn(expected);
        Car actual = carService.create(createdCar);
        Assertions.assertNotNull(actual,"Car should be not null for input: " + car);
    }

    @Test
    public void get_Ok() {
        Long carIdentifier = 1L;
        Car expected = car;
        Mockito.when(carDao.get(carIdentifier)).thenReturn(Optional.of(car));
        Car actual = carService.get(carIdentifier);
        Assertions.assertNotNull(actual,"Car should be not null for id: " + carIdentifier);
        Assertions.assertEquals(expected,actual,"Expected car is: "
                + expected + ", but was: " + actual);
    }

    @Test
    public void get_nonExistentId_NotOk() {
        Long invalidIdentifier = 2L;
        Mockito.when(carDao.get(invalidIdentifier)).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class,
                () -> carService.get(invalidIdentifier),
                "NoSuchElementException expected for id: " + invalidIdentifier);
    }

    @Test
    public void getAll_Ok() {
        int expectedSize = 1;
        Mockito.when(carDao.getAll()).thenReturn(List.of(car));
        List<Car> actual = carService.getAll();
        Assertions.assertTrue(expectedSize == actual.size(),
                "Size expected: " + expectedSize + ", but was: " + actual.size());
    }

    @Test
    public void update_Ok() {
        car.setModel("XC45");
        Car expected = car;
        Mockito.when(carDao.update(car)).thenReturn(car);
        Car actual = carService.update(car);
        Assertions.assertEquals(expected,actual,
                "Car should be: " + expected + ", but was: " + actual);
    }

    @Test
    public void delete_Ok() {
        Long carIdentifier = 1L;
        Mockito.when(carDao.delete(carIdentifier)).thenReturn(true);
        boolean actual = carService.delete(carIdentifier);
        Assertions.assertTrue(actual,"True is expected for input id: " + carIdentifier);
    }

    @Test
    public void getAllByDriver_Ok() {
        Long driverIdentifier = 1L;
        int expected = 1;
        Mockito.when(carDao.getAllByDriver(driverIdentifier)).thenReturn(List.of(car));
        List<Car> actual = carService.getAllByDriver(driverIdentifier);
        Assertions.assertTrue(expected == actual.size(),
                "Size expected: " + expected + ", but was: " + actual.size());
    }
}
