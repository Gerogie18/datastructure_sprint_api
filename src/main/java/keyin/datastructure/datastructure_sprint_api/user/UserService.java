package keyin.datastructure.datastructure_sprint_api.user;

import keyin.datastructure.datastructure_sprint_api.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new user with just a name.
     * @param name The name for the new user.
     * @return The saved User entity.
     */
    public User createUser(String name) {
        // Still check for duplicates as a business rule
        userRepository.findByName(name).ifPresent(user -> {
            throw new UserAlreadyExistsException("User with name '" + name + "' already exists.");
        });

        User newUser = new User();
        newUser.setName(name);

        // No password hashing is needed in this version
        return userRepository.save(newUser);
    }

    /**
     * Retrieves all users from the database.
     * @return A list of all User entities.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
