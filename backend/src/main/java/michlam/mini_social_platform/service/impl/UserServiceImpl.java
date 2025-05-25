package michlam.mini_social_platform.service.impl;

import lombok.AllArgsConstructor;
import michlam.mini_social_platform.dto.UserDto;
import michlam.mini_social_platform.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    @Override
    public UserDto createUser(UserDto userDto) {
        return null;
    }

    @Override
    public UserDto getUsers() {
        return null;
    }

    @Override
    public UserDto getUser(Long userId) {
        return null;
    }

    @Override
    public UserDto updateUser(UserDto updatedUser) {
        return null;
    }

    @Override
    public Long getIdByUsername(String username) {
        return 0L;
    }

    @Override
    public MultipartFile getProfilePicture(Long userId) {
        return null;
    }

    @Override
    public String updateProfilePicture(Long userId, MultipartFile pfp) {
        return null;
    }
}
