package michlam.mini_social_platform.service.impl;

import lombok.AllArgsConstructor;
import michlam.mini_social_platform.entity.User;
import michlam.mini_social_platform.exception.ResourceNotFoundException;
import michlam.mini_social_platform.respository.UserRepository;
import michlam.mini_social_platform.service.LoginService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean login(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new ResourceNotFoundException("User does not exist with the given username: " + username));

        return passwordEncoder.matches(password, user.getPassword());
    }
}
