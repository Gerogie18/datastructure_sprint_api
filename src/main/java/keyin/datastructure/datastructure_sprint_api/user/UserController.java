package keyin.datastructure.datastructure_sprint_api.user;

import keyin.datastructure.datastructure_sprint_api.user.dto.UserResponse;
import keyin.datastructure.datastructure_sprint_api.user.dto.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        User registeredUser = userService.registerUser(registerRequest);

        UserResponse response = new UserResponse(
                registeredUser.getId(),
                registeredUser.getUsername(),
                registeredUser.getProfileImagePath()
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
