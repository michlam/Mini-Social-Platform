package michlam.mini_social_platform.mapper;

import michlam.mini_social_platform.dto.UserDto;
import michlam.mini_social_platform.entity.Role;
import michlam.mini_social_platform.entity.User;

public class Mapper {
    public static UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getPassword()
        );
    }

    public static User mapToUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getUsername(),
                userDto.getPassword(),
                Role.USER
        );
    }
}
