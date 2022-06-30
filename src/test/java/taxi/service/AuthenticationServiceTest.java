package taxi.service;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import taxi.exception.AuthenticationException;
import taxi.model.Driver;
import taxi.service.impl.AuthenticationServiceImpl;

public class AuthenticationServiceTest {
    @InjectMocks
    private static AuthenticationService authenticationService;
    @Mock
    private DriverService driverService;
    private Driver driver;

    @BeforeAll
    static void beforeAll() {
        authenticationService = new AuthenticationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        driver = new Driver("Alice","alice@i.ua","1111","65434524");
        driver.setId(1L);
    }

    @Test
    public void login_Ok() {
        String login = "alice@i.ua";
        String password = "1111";
        Driver expected = driver;
        Mockito.when(driverService.findByLogin(login)).thenReturn(Optional.of(driver));
        try {
            Driver actual = authenticationService.login(login, password);
            Assertions.assertEquals(expected,actual,"Expected driver: " + expected + ", but was: "
                    + actual);
        } catch (AuthenticationException e) {
            Assertions.assertDoesNotThrow(() -> authenticationService.login(login, password),
                    "AuthenticationException isn`t expected for login: " + login + ", password: "
                            + password);
        }
    }

    @Test
    public void login_nonExistentLogin_NotOk() {
        String login = "bob@i.ua";
        String password = "123413";
        Mockito.when(driverService.findByLogin(login)).thenReturn(Optional.empty());
        Assertions.assertThrows(AuthenticationException.class,
                () -> authenticationService.login(login,password),
                "AuthenticationException is expected for login: " + login + ", password: "
                        + password + ", but it was not");
    }

    @Test
    public void login_nonExistentPassword_NotOk() {
        String login = "alice@i.ua";
        String password = "qwerty";
        Mockito.when(driverService.findByLogin(login)).thenReturn(Optional.of(driver));
        Assertions.assertThrows(AuthenticationException.class,
                () -> authenticationService.login(login,password),
                "AuthenticationException is expected for login: " + login + ", password: "
                + password + ", but it was not");
    }
}
