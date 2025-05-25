package michlam.mini_social_platform.service;

import michlam.mini_social_platform.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto getUsers();
    UserDto getUser(Long userId);
    UserDto updateUser(UserDto updatedUser);

    Long getIdByUsername(String username);
}
