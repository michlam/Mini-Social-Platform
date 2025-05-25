package michlam.mini_social_platform.service.impl;

import lombok.AllArgsConstructor;
import michlam.mini_social_platform.dto.UserDto;
import michlam.mini_social_platform.entity.User;
import michlam.mini_social_platform.exception.DuplicateResourceException;
import michlam.mini_social_platform.exception.ResourceNotFoundException;
import michlam.mini_social_platform.mapper.Mapper;
import michlam.mini_social_platform.respository.UserRepository;
import michlam.mini_social_platform.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

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
        savedUser.setPassword(userDto.getPassword());
        return Mapper.mapToUserDto(savedUser);
    }

    @Override
    public List<Long> getUserIds() {
        List<User> users = userRepository.findAll();
        List<Long> userIds = new ArrayList<>();

        for (User user : users) {
            userIds.add(user.getId());
        }

        return userIds;
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
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new ResourceNotFoundException("User does not exist with the given username: " + username));

        return user.getId();
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
