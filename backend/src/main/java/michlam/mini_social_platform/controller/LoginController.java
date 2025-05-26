package michlam.mini_social_platform.controller;

import lombok.AllArgsConstructor;
import michlam.mini_social_platform.config.JwtService;
import michlam.mini_social_platform.dto.UserDto;
import michlam.mini_social_platform.exception.ErrorResponse;
import michlam.mini_social_platform.exception.ResourceNotFoundException;
import michlam.mini_social_platform.mapper.Mapper;
import michlam.mini_social_platform.service.LoginService;
import michlam.mini_social_platform.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/login")
public class LoginController {
    private LoginService loginService;
    private UserService userService;
    private JwtService jwtService;


    @PostMapping
    public ResponseEntity<Object> login(@RequestBody UserDto userDto) {
        String username = userDto.getUsername();
        String password = userDto.getPassword();

        try {
            if (loginService.login(username, password)) {
                Long userId = userService.getIdByUsername(username);

                // Generate JWT
                String jwtToken = jwtService.generateToken(Mapper.mapToUser(userDto));
                return new ResponseEntity<>(
                        AuthenticationResponse.builder().userId(userId).token(jwtToken).build(),
                        HttpStatus.OK
                );

            } else {
                String response = "Invalid Credentials";

                return new ResponseEntity<>(
                        response,
                        HttpStatus.UNAUTHORIZED
                );
            }

        } catch (ResourceNotFoundException e) {
            ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
