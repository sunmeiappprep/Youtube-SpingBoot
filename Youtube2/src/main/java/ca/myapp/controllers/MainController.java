package ca.myapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
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
import ca.myapp.config.WebSecurityConfig;
//import ca.myapp.utility.JwtUtil;
@Controller
@CrossOrigin
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;


//    @Autowired
//    private JwtUtil jwtUtil;

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
//            // Return response (you might want to return a JWT token or session data here)
//            System.out.println("Login");
//            return ResponseEntity.ok(authenticatedUser);
//        } catch (Exception e) {
//            throw new ResponseStatusException(
//                    HttpStatus.UNAUTHORIZED, "Username or Password is incorrect", e);
//        }
//    }

//    @PostMapping("/login")
//    public ResponseEntity<?> loginUser(@RequestBody User loginUser) {
//        System.out.println("insideLogin");
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//
//                    new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword())
//            );
//            System.out.println("insideLogin");
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            // At this point, Spring Security should have created a session and will return the JSESSIONID cookie to the client.
//            System.out.println("Login Success");
//            return ResponseEntity.ok().build(); // You can return the authenticated user details or a success message as needed.
//        } catch (AuthenticationException e) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or Password is incorrect", e);
//        }
//    }

    @GetMapping("/login2")
    public ResponseEntity<?> someMethod() {
        if (MyService.isAuthenticated()) {
            System.out.println("isAuthenticated");
            return ResponseEntity.ok("You are authenticated");
        } else {
            System.out.println("is NOT Authenticated");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authenticated");
        }
    }

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
}
