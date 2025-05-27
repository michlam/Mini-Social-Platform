package michlam.mini_social_platform;

import io.jsonwebtoken.lang.Assert;
import michlam.mini_social_platform.controller.UserController;
import michlam.mini_social_platform.dto.UserDto;
import michlam.mini_social_platform.exception.DuplicateResourceException;
import michlam.mini_social_platform.exception.ResourceNotFoundException;
import michlam.mini_social_platform.respository.UserRepository;
import michlam.mini_social_platform.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserController userController;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository.testDeleteUsers();
    }

    @Test
    void testCreateUser_BasicSuccess() {
        UserDto userDto = new UserDto();
        userDto.setUsername("test.user.1");
        userDto.setPassword("1234");

        // Check the return userDto is good
        UserDto savedUser = userService.createUser(userDto);
        Assertions.assertEquals(userDto.getUsername(), savedUser.getUsername());
        Assertions.assertEquals(userDto.getPassword(), savedUser.getPassword());
    }

    @Test
    void testCreateUser_Fail_UsernameExists() {
        UserDto first = new UserDto();
        first.setUsername("test.user.1");
        first.setPassword("1234");

        UserDto second = new UserDto();
        second.setUsername("test.user.1");
        second.setPassword("2345");

        userService.createUser(first);
        Assertions.assertThrows(DuplicateResourceException.class, () -> userService.createUser(second));
    }

    @Test
    void testGetUserIds_Success() {
        UserDto first = new UserDto();
        first.setUsername("test.user.1");
        first.setPassword("1234");

        UserDto second = new UserDto();
        second.setUsername("test.user.2");
        second.setPassword("2345");

        Long firstId = userService.createUser(first).getId();
        Long secondId = userService.createUser(second).getId();

        List<Long> userIds = userService.getUserIds();
        Assertions.assertTrue(userIds.contains(firstId));
        Assertions.assertTrue(userIds.contains(secondId));
        Assertions.assertFalse(userIds.contains(secondId + 100L));
    }

    @Test
    void testGetUser_Success() {
        UserDto userDto = new UserDto();
        userDto.setUsername("test.user.1");
        userDto.setPassword("1234");

        UserDto savedUser = userService.createUser(userDto);
        UserDto compareUser = userService.getUser(savedUser.getId());

        Assertions.assertEquals(savedUser.getId(), compareUser.getId());
        Assertions.assertEquals(savedUser.getUsername(), compareUser.getUsername());
    }

    @Test
    void testGetUser_Failure_UserDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> userService.getUser(1L));
    }

    @Test
    void testUpdateUser_Success() {
        UserDto userDto = new UserDto();
        userDto.setUsername("test.user.1");
        userDto.setPassword("1234");

        UserDto savedUser = userService.createUser(userDto);
        savedUser.setUsername("test.user.2");
        savedUser.setPassword("2345");

        UserDto compareUser = userService.updateUser(savedUser);
        Assertions.assertEquals(savedUser.getId(), compareUser.getId());
        Assertions.assertEquals(savedUser.getUsername(), compareUser.getUsername());
        Assertions.assertEquals(savedUser.getPassword(), compareUser.getPassword());
    }

    @Test
    void testUpdateUser_Failure_UserDoesNotExist() {
        UserDto fakeUser = new UserDto(1L, "test.user.2", "2345");
        Assertions.assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(fakeUser));
    }

    @Test
    void testUpdateUser_Failure_UsernameExists() {
        UserDto first = new UserDto();
        first.setUsername("test.user.1");
        first.setPassword("1234");
        UserDto firstSaved = userService.createUser(first);

        UserDto second = new UserDto();
        second.setUsername("test.user.2");
        second.setPassword("2345");
        userService.createUser(second);

        firstSaved.setUsername("test.user.2");
        Assertions.assertThrows(DuplicateResourceException.class, () -> userService.updateUser(firstSaved));
    }

    @Test
    void testGetProfilePicture_Success_Default() {
        MultipartFile result = userService.getProfilePicture(45L);
        Assertions.assertNotNull(result);
    }



}
