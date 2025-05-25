package michlam.mini_social_platform.service.impl;

import lombok.AllArgsConstructor;
import michlam.mini_social_platform.dto.UserDto;
import michlam.mini_social_platform.entity.User;
import michlam.mini_social_platform.exception.DuplicateResourceException;
import michlam.mini_social_platform.mapper.Mapper;
import michlam.mini_social_platform.respository.UserRepository;
import michlam.mini_social_platform.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = Mapper.mapToUser(userDto);

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new DuplicateResourceException("Username is already taken");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        //  TODO: Create the user's profile in the future
        System.out.println("User Id: " + user.getId());
        System.out.println("Saved User Id: " + savedUser.getId());
        savedUser.setPassword(userDto.getPassword());
        return Mapper.mapToUserDto(savedUser);
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
