package ca.myapp.controllers;

import ca.myapp.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ca.myapp.models.User;
import ca.myapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import ca.myapp.utility.MyService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

;

@Controller
@CrossOrigin
@SpringBootApplication
@ComponentScan(basePackages = {"ca.myapp.utility", "ca.myapp.controllers", "ca.myapp.service"})
public class MainController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/login")
    public String login() {
        return "login"; // Return the login.html template
    }

    @GetMapping("/register")
    public String register() {
        return "register"; // Return the login.html template
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> loginUser(@RequestBody User loginUser) {
//        try {
//            // Authenticate user
//            User authenticatedUser = userService.authenticate(loginUser.getUsername(), loginUser.getPassword());
//            System.out.println("Login");
//            return ResponseEntity.ok(authenticatedUser);
//        } catch (Exception e) {
//            throw new ResponseStatusException(
//                    HttpStatus.UNAUTHORIZED, "Username or Password is incorrect", e);
//        }
//    }
    public class LoginResponse {
        private User user;
        private String jwtToken;

        public LoginResponse(User user, String jwtToken) {
            this.user = user;
            this.jwtToken = jwtToken;
        }

        // Getters and Setters
        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public String getJwtToken() {
            return jwtToken;
        }

        public void setJwtToken(String jwtToken) {
            this.jwtToken = jwtToken;
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginUser) {
        try {
            User authenticatedUser = userService.authenticate(loginUser.getUsername(), loginUser.getPassword());
            System.out.println(authenticatedUser.getUsername());

            final String jwtToken = jwtUtil.generateToken(authenticatedUser.getUsername());
            LoginResponse response = new LoginResponse(authenticatedUser, jwtToken);
            return ResponseEntity.ok(response); // Return both User and JWT token
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or Password is incorrect", e);
        }
    }

    @GetMapping("/testing")
    public ResponseEntity<?>  testingEndpoint(Authentication authentication) {
        System.out.println("Endpoint '/testing' accessed by: " );
        String username = authentication.getName(); // Get the username of the authenticated user
        // Log, store, or use the username as needed
        System.out.println("Endpoint '/testing' accessed by: " + username);

        return ResponseEntity.ok("asdasdasd");
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        System.out.println("Welcome");
        try {
            User registered = userService.registerNewUserAccount(user);

            return ResponseEntity.ok(registered);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
