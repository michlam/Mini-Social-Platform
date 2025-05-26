package michlam.mini_social_platform;

import michlam.mini_social_platform.controller.LoginController;
import michlam.mini_social_platform.exception.ResourceNotFoundException;
import michlam.mini_social_platform.respository.UserRepository;
import michlam.mini_social_platform.service.LoginService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LoginServiceTest {
    @Autowired
    private LoginController loginController;

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertNotNull(loginController);
    }

    @BeforeEach
    void setup() {
        userRepository.testDeleteUsers();
    }

    @Test
    void testLogin_BasicSuccess() {
        String username = "test.user";
        String password = "1234";

        Assertions.assertTrue(loginService.login(username, password));
    }

    @Test
    void testLogin_IncorrectPassword() {
        String username = "test.user";
        String password = "1235";

        Assertions.assertFalse(loginService.login(username, password));
    }

    @Test
    void testLogin_IncorrectUsername() {
        String username = "test.user.doesnt.exist";
        String password = "1234";

        Assertions.assertThrows(ResourceNotFoundException.class, () -> loginService.login(username, password));
    }

}
