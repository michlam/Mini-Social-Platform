package michlam.mini_social_platform.service.impl;

import lombok.AllArgsConstructor;
import michlam.mini_social_platform.dto.UserDto;
import michlam.mini_social_platform.entity.User;
import michlam.mini_social_platform.exception.DuplicateResourceException;
import michlam.mini_social_platform.exception.ResourceNotFoundException;
import michlam.mini_social_platform.mapper.Mapper;
import michlam.mini_social_platform.respository.UserRepository;
import michlam.mini_social_platform.service.UserService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private final Path PROFILE_PICTURE_DIRECTORY = Paths.get(
            "src/main/resources/static/images")
            .toAbsolutePath()
            .normalize();

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
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User does not exist with the given id: " + userId));

        user.setPassword(null);
        return Mapper.mapToUserDto(user);
    }

    @Override
    public UserDto updateUser(UserDto updatedUser) {
        User user = userRepository.findById(updatedUser.getId()).orElseThrow(() ->
                new ResourceNotFoundException("User does not exist with the given id: " + updatedUser.getId()));

        // Check that we aren't changing the username to something that is taken.
        userRepository.findByUsername(updatedUser.getUsername()).ifPresent(checkUser -> {
            if (!checkUser.getId().equals(updatedUser.getId())) {
                throw new DuplicateResourceException("User with this username is already taken.");
            }
        });

        user.setUsername(updatedUser.getUsername());
        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));

        User updatedUserObj = userRepository.save(user);
        updatedUserObj.setPassword(updatedUser.getPassword());
        return Mapper.mapToUserDto(updatedUserObj);
    }

    @Override
    public Long getIdByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new ResourceNotFoundException("User does not exist with the given username: " + username));

        return user.getId();
    }

    @Override
    public Resource getProfilePicture(Long userId) {
        userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User does not exist with the given id: " + userId));

        try {
            String fileName = String.valueOf(userId) + "-pfp.jpg";
            Path filePath = this.PROFILE_PICTURE_DIRECTORY.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            // If the image exists, return it
            if (resource.exists()) {
                return resource;
            }

            // Else, return the default image
            fileName = "default-pfp.jpg";
            filePath = this.PROFILE_PICTURE_DIRECTORY.resolve(fileName).normalize();
            return new UrlResource(filePath.toUri());

        } catch (MalformedURLException e) {
            throw new RuntimeException("Malformed URL encountered when getting profile picture");
        }
    }

    @Override
    public void updateProfilePicture(Long userId, MultipartFile pfp) {
        // Check if userId exists
        // Do we need to do some preprocessing?
        //  Convert image to JPEG.
        //  Convert image resolution. 400 by 400 should be good.
        // If custom pfp already exists, then just update it.
        // If
        userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User does not exist with the given id: " + userId));

        if (pfp.isEmpty()) {
            throw new IllegalArgumentException("Profile picture file is empty");
        }



        return;
    }

    @Override
    public void deleteUser(Long userId) {

    }

}
