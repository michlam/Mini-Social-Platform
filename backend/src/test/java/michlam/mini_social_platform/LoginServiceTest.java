package michlam.mini_social_platform;

import michlam.mini_social_platform.controller.LoginController;
import michlam.mini_social_platform.dto.UserDto;
import michlam.mini_social_platform.exception.ResourceNotFoundException;
import michlam.mini_social_platform.respository.UserRepository;
import michlam.mini_social_platform.service.LoginService;
import michlam.mini_social_platform.service.UserService;
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
    private UserService userService;

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
        UserDto userDto = new UserDto();
        userDto.setUsername("test.user.1");
        userDto.setPassword("1234");
        userService.createUser(userDto);

        Assertions.assertTrue(loginService.login(userDto.getUsername(), userDto.getPassword()));
    }

    @Test
    void testLogin_IncorrectPassword() {
        UserDto userDto = new UserDto();
        userDto.setUsername("test.user.1");
        userDto.setPassword("1234");
        userService.createUser(userDto);

        Assertions.assertFalse(loginService.login(userDto.getUsername(), "1235"));
    }

    @Test
    void testLogin_IncorrectUsername() {
        String username = "test.user.doesnt.exist";
        String password = "1234";

        Assertions.assertThrows(ResourceNotFoundException.class, () -> loginService.login(username, password));
    }

}
