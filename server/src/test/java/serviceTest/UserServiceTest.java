package serviceTest;

import dataaccess.*;
import org.junit.jupiter.api.Test;
import service.requests.RegisterRequest;
import service.results.RegisterResult;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    @Test
    public void registerPositive() throws Exception {
        UserDAO userDAO = new MemoryUserDAO();
        AuthDAO authDAO = new MemoryAuthDAO();
        UserService service =
                new UserService(userDAO, authDAO);
        RegisterRequest request =
                new RegisterRequest(
                        "scott",
                        "67",
                        "scott@gmail.com"
                );
        RegisterResult result =
                service.register(request);
        assertNotNull(result);
        assertEquals("scott", result.username());
        assertNotNull(result.authToken());
    }
}
