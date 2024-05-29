package ca.myapp.config;
import ca.myapp.repository.UserRepository;
import ca.myapp.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import ca.myapp.models.User;

@Component
public class DefaultUsersRunner implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;
    @Override
    public void run(String... args) throws Exception {
        // Check if the default user exists
        String defaultUsername = "asdasd1@gmail.com";
        User existingUser = userRepository.findByUsername(defaultUsername);
        if (existingUser == null) {
            // Create and save the default user
            User newUser = new User();
            newUser.setUsername(defaultUsername);
            newUser.setPassword("asdasd"); // Consider encrypting this
            // Set other necessary fields...
            userService.registerNewUserAccount(newUser);
            System.out.println("Default user created");
        }
    }
}
