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
import taxi.dao.ManufacturerDao;
import taxi.model.Manufacturer;
import taxi.service.impl.ManufacturerServiceImpl;

public class ManufacturerServiceTest {
    @InjectMocks
    private static ManufacturerService manufacturerService;
    @Mock
    private ManufacturerDao manufacturerDao;
    private Manufacturer manufacturer;

    @BeforeAll
    static void beforeAll() {
        manufacturerService = new ManufacturerServiceImpl();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        manufacturer = new Manufacturer("Volvo","Switzerland");
        manufacturer.setId(1L);
    }

    @Test
    public void create_Ok() {
        Manufacturer createdManufacturer = new Manufacturer("Volvo","Switzerland");
        Mockito.when(manufacturerDao.create(createdManufacturer)).thenReturn(manufacturer);
        Manufacturer actual = manufacturerService.create(createdManufacturer);
        Assertions.assertNotNull(actual,"Manufacturer should be not null for input: "
                + manufacturer);
    }

    @Test
    public void get_Ok() {
        Long manufacturerIdentifier = 1L;
        Manufacturer expected = manufacturer;
        Mockito.when(manufacturerDao.get(manufacturerIdentifier))
                .thenReturn(Optional.of(manufacturer));
        Manufacturer actual = manufacturerService.get(manufacturerIdentifier);
        Assertions.assertNotNull(actual,"Manufacturer should be not null for id: "
                + manufacturerIdentifier);
        Assertions.assertEquals(expected,actual,"Expected manufacturer is: "
                + expected + ", but was: " + actual);
    }

    @Test
    public void get_nonExistentId_NotOk() {
        Long invalidIdentifier = 2L;
        Mockito.when(manufacturerDao.get(invalidIdentifier)).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class,
                () -> manufacturerService.get(invalidIdentifier),
                "NoSuchElementException expected for id: " + invalidIdentifier);
    }

    @Test
    public void getAll_Ok() {
        int expectedSize = 1;
        Mockito.when(manufacturerDao.getAll()).thenReturn(List.of(manufacturer));
        List<Manufacturer> actual = manufacturerService.getAll();
        Assertions.assertTrue(expectedSize == actual.size(),
                "Size expected: " + expectedSize + ", but was: " + actual.size());
    }

    @Test
    public void update_Ok() {
        manufacturer.setCountry("Poland");
        Manufacturer expected = manufacturer;
        Mockito.when(manufacturerDao.update(manufacturer)).thenReturn(manufacturer);
        Manufacturer actual = manufacturerService.update(manufacturer);
        Assertions.assertEquals(expected,actual,
                "Manufacturer should be: " + expected + ", but was: " + actual);
    }

    @Test
    public void delete_Ok() {
        Long manufacturerIdentifier = 1L;
        Mockito.when(manufacturerDao.delete(manufacturerIdentifier)).thenReturn(true);
        boolean actual = manufacturerService.delete(manufacturerIdentifier);
        Assertions.assertTrue(actual,"True is expected for input id: " + manufacturerIdentifier);
    }
}
