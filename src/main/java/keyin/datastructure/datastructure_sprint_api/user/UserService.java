package keyin.datastructure.datastructure_sprint_api.user;

import keyin.datastructure.datastructure_sprint_api.exception.UserAlreadyExistsException;
import keyin.datastructure.datastructure_sprint_api.user.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.username()).isPresent()) {
            throw new UserAlreadyExistsException("Username " + registerRequest.username() + " is already taken.");
        }
        User newUser = new User();
        newUser.setUsername(registerRequest.username());
        newUser.setPassword(passwordEncoder.encode(registerRequest.password()));
        return userRepository.save(newUser);
    };
}
