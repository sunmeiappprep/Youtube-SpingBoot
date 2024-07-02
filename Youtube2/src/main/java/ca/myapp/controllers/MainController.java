package ca.myapp.controllers;

import ca.myapp.service.PlaylistTitleService;
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

    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final PlaylistTitleService playlistTitleService;

    @Autowired
    public MainController(JwtUtil jwtUtil,
                          UserService userService,
                          AuthenticationManager authenticationManager,
                          PlaylistTitleService playlistTitleService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.playlistTitleService = playlistTitleService;
    }



    public static class LoginResponse {
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


    @PostMapping("/user/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginUser) {
        try {
            User authenticatedUser = userService.authenticate(loginUser.getUsername(), loginUser.getPassword());
            System.out.println(authenticatedUser.getUsername());
            final String jwtToken = jwtUtil.generateToken(authenticatedUser.getUsername());
            LoginResponse response = new LoginResponse(authenticatedUser, jwtToken);
            System.out.println(authenticatedUser.getId());
            ensureUserPlaylists(authenticatedUser.getId());
            return ResponseEntity.ok(response); // Return both User and JWT token
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or Password is incorrect", e);
        }
    }

    private void ensureUserPlaylists(Long userId) {
        if (!playlistTitleService.playlistTitleExistsByName(userId, "Watch Later")) {
            System.out.println("Watch Later");
            playlistTitleService.autoCreate(userId,"Watch Later");
        }
        if (!playlistTitleService.playlistTitleExistsByName(userId, "Liked Video")) {
            System.out.println("Liked Video");
            playlistTitleService.autoCreate(userId,"Liked Video");
        }
        System.out.println("ensureUserPlaylists ran");
    }


    @PostMapping("/user/register")
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
