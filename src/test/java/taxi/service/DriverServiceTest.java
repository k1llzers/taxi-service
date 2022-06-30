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
import taxi.dao.DriverDao;
import taxi.model.Driver;
import taxi.service.impl.DriverServiceImpl;

public class DriverServiceTest {
    @InjectMocks
    private static DriverService driverService;
    @Mock
    private DriverDao driverDao;
    private Driver driver;

    @BeforeAll
    static void beforeAll() {
        driverService = new DriverServiceImpl();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        driver = new Driver("Bob","bob@i.ua","qwerty","318745687531");
        driver.setId(1L);
    }

    @Test
    public void create_Ok() {
        Driver createdDriver = new Driver("Bob","bob@i.ua","qwerty","318745687531");
        Mockito.when(driverDao.create(createdDriver)).thenReturn(driver);
        Driver actual = driverService.create(createdDriver);
        Assertions.assertNotNull(actual,"Driver should be not null for input: " + driver);
    }

    @Test
    public void get_Ok() {
        Long driverIdentifier = 1L;
        Driver expected = driver;
        Mockito.when(driverDao.get(driverIdentifier)).thenReturn(Optional.of(driver));
        Driver actual = driverService.get(driverIdentifier);
        Assertions.assertNotNull(actual,"Driver should be not null for id: " + driverIdentifier);
        Assertions.assertEquals(expected,actual,"Expected driver is: "
                + expected + ", but was: " + actual);
    }

    @Test
    public void get_nonExistentId_NotOk() {
        Long invalidIdentifier = 2L;
        Mockito.when(driverDao.get(invalidIdentifier)).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class,
                () -> driverService.get(invalidIdentifier),
                "NoSuchElementException expected for id: " + invalidIdentifier);
    }

    @Test
    public void getAll_Ok() {
        int expectedSize = 1;
        Mockito.when(driverDao.getAll()).thenReturn(List.of(driver));
        List<Driver> actual = driverService.getAll();
        Assertions.assertTrue(expectedSize == actual.size(),
                "Size expected: " + expectedSize + ", but was: " + actual.size());
    }

    @Test
    public void update_Ok() {
        driver.setPassword("1111");
        Driver expected = driver;
        Mockito.when(driverDao.update(driver)).thenReturn(driver);
        Driver actual = driverService.update(driver);
        Assertions.assertEquals(expected,actual,
                "Driver should be: " + expected + ", but was: " + actual);
    }

    @Test
    public void delete_Ok() {
        Long driverIdentifier = 1L;
        Mockito.when(driverDao.delete(driverIdentifier)).thenReturn(true);
        boolean actual = driverService.delete(driverIdentifier);
        Assertions.assertTrue(actual,"True is expected for input id: " + driverIdentifier);
    }

    @Test
    public void findByLogin_OK() {
        String driverLogin = "bob@i.ua";
        Mockito.when(driverDao.findByLogin(driverLogin)).thenReturn(Optional.of(driver));
        Optional<Driver> actual = driverService.findByLogin(driverLogin);
        Assertions.assertTrue(actual.isPresent(),"True is expected for input login: "
                + driverLogin);
    }

    @Test
    public void findByLogin_nonExistentLogin_Not_Ok() {
        String incorrectLogin = "alice@i.ua";
        Mockito.when(driverDao.findByLogin(incorrectLogin)).thenReturn(Optional.empty());
        Optional<Driver> actual = driverService.findByLogin(incorrectLogin);
        Assertions.assertTrue(actual.isEmpty(),"True is expected for input login: "
                + incorrectLogin);
    }
}
