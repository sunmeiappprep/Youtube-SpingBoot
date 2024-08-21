package ca.myapp.config;
import ca.myapp.repository.UserRepository;
import ca.myapp.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import ca.myapp.models.User;

import java.time.LocalDateTime;

@Component
public class DefaultUsersRunner implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;
    @Override
    public void run(String... args) throws Exception {
        // Check if the default user exists
        String defaultUsername = "DemoUser";
        User existingUser = userRepository.findByUsername(defaultUsername);
        if (existingUser == null) {

            User newUser = new User();
            newUser.setUsername(defaultUsername);
            newUser.setPassword("asdasd"); // Consider encrypting this
            newUser.setCreatedAt(LocalDateTime.now());
            userService.registerNewUserAccount(newUser);
            System.out.println("Default user created");
        }
    }
}
