package ca.myapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import ca.myapp.repository.UserRepository;
import ca.myapp.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
@Service
public class UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;  // Autowire the AuthenticationManager

    public User registerNewUserAccount(User user) throws Exception {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new Exception("There is an account with that username: " + user.getUsername());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    public User authenticate(String username, String password) {
        // Find the user by username
        User user = userRepository.findByUsername(username);
        if (user == null) {
            // User not found
            throw new RuntimeException("User not found");
        }

        // Compare passwords
        if (passwordEncoder.matches(password, user.getPassword())) {
            // Passwords match
            return user;
        } else {
            // Passwords do not match
            throw new RuntimeException("Password incorrect");
        }
    }

}
