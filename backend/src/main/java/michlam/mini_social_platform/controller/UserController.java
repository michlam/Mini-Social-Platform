package michlam.mini_social_platform.controller;

import lombok.AllArgsConstructor;
import michlam.mini_social_platform.config.JwtService;
import michlam.mini_social_platform.dto.UserDto;
import michlam.mini_social_platform.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;
    private JwtService jwtService;

    // TODO: CreateUser REST API
    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody UserDto userDto) {
        return null;
    }

    // TODO: GetUsers REST API
    @GetMapping
    public ResponseEntity<Object> getUsers() {
        return null;
    }

    // TODO: GetUser REST API
    @GetMapping("{id}")
    public ResponseEntity<Object> getUser(@PathVariable Long userId) {
        return null;
    }

    // TODO: UpdateUser REST API
    @PutMapping("{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        return null;
    }


    // TODO: GetProfilePicture REST API
    @GetMapping("{id}/pfp")
    public ResponseEntity<Object> getProfilePicture(@PathVariable Long userId) {
        return null;
    }

    // TODO: UpdateProfilePicture REST API
    @PutMapping("{id}/pfp")
    public ResponseEntity<Object> updateProfilePicture(
            @PathVariable Long userId,
            @RequestParam("pfp") MultipartFile pfp
    ) {
        return null;
    }



}
