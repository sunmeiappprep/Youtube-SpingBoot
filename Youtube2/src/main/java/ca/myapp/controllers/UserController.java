package ca.myapp.controllers;
import ca.myapp.models.User; // Import the User class
import ca.myapp.repository.UserRepository;
import ca.myapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User registered = userService.registerNewUserAccount(user);
            System.out.println("Welcome");
            return ResponseEntity.ok(registered);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginUser) {
        try {
            // Authenticate user
            User authenticatedUser = userService.authenticate(loginUser.getUsername(), loginUser.getPassword());
            // Return response (you might want to return a JWT token or session data here)
            System.out.println("Login Success");
            return ResponseEntity.ok(authenticatedUser);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Username or Password is incorrect", e);
        }
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Return the login.html template
    }



}
