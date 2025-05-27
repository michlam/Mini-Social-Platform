package michlam.mini_social_platform.controller;

import lombok.AllArgsConstructor;
import michlam.mini_social_platform.config.JwtService;
import michlam.mini_social_platform.dto.UserDto;
import michlam.mini_social_platform.entity.User;
import michlam.mini_social_platform.exception.DuplicateResourceException;
import michlam.mini_social_platform.exception.ErrorResponse;
import michlam.mini_social_platform.exception.ResourceNotFoundException;
import michlam.mini_social_platform.mapper.Mapper;
import michlam.mini_social_platform.service.UserService;
import org.apache.coyote.Response;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;
    private JwtService jwtService;

    @PostMapping("create")
    public ResponseEntity<Object> createUser(@RequestBody UserDto userDto) {
        try {
            UserDto savedUserDto = userService.createUser(userDto);
            User savedUser = Mapper.mapToUser(savedUserDto);
            Long userId  = userService.getIdByUsername(savedUser.getUsername());

            // Generate the JSON Web Token
            String jwtToken = jwtService.generateToken(savedUser);
            return new ResponseEntity<>(
                    AuthenticationResponse.builder().userId(userId).token(jwtToken).build(),
                    HttpStatus.CREATED
            );

        } catch (DuplicateResourceException e) {
            ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<Object> getUserIds() {
        List<Long> userIds = userService.getUserIds();
        return ResponseEntity.ok(userIds);
    }

    @GetMapping("{userId}")
    public ResponseEntity<Object> getUser(@PathVariable Long userId) {
        try {
            UserDto userDto = userService.getUser(userId);
            return ResponseEntity.ok(userDto);

        } catch (ResourceNotFoundException e) {
            ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    @PutMapping("{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable Long userId, @RequestBody UserDto updatedUser) {
        try {
            UserDto userDto = userService.updateUser(updatedUser);
            return ResponseEntity.ok(userDto);

        } catch (ResourceNotFoundException e) {
            ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        } catch (DuplicateResourceException e) {
            ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        }
    }

    // TODO: GetProfilePicture REST API
    @GetMapping("{userId}/pfp")
    public ResponseEntity<Object> getProfilePicture(@PathVariable Long userId) {
        return null;
    }

    // TODO: UpdateProfilePicture REST API
    @PutMapping("{userId}/pfp")
    public ResponseEntity<Object> updateProfilePicture(
            @PathVariable Long userId,
            @RequestParam("pfp") MultipartFile pfp
    ) {
        return null;
    }



}
