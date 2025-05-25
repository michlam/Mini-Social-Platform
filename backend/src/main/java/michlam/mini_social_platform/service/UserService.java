package michlam.mini_social_platform.service;

import michlam.mini_social_platform.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    List<Long> getUserIds();
    UserDto getUser(Long userId);
    UserDto updateUser(UserDto updatedUser);

    Long getIdByUsername(String username);
    MultipartFile getProfilePicture(Long userId);
    String updateProfilePicture(Long userId, MultipartFile pfp);

}
