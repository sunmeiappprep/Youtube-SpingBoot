package ca.myapp.controllers;
import ca.myapp.models.User; // Import the User class
import ca.myapp.models.Video;
import ca.myapp.repository.UserRepository;
import ca.myapp.service.UserService;
import ca.myapp.service.VideoService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;



@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private final VideoService videoService;

    @Autowired
    public UserController(VideoService videoService) {
        this.videoService = videoService;
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

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginUser) {
        try {
            User authenticatedUser = userService.authenticate(loginUser.getUsername(), loginUser.getPassword());
            System.out.println("Login Success");
            return ResponseEntity.ok(authenticatedUser);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Username or Password is incorrect", e);
        }
    }

    @GetMapping("/testing")
    public String testingEndpoint(Authentication authentication) {
        String username = authentication.getName(); // Get the username of the authenticated user
        // Log, store, or use the username as needed
        System.out.println("Endpoint '/testing' accessed by: " + username);

        return "Accessed by: " + username;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/video/{uploaderId}")
    public ResponseEntity<List<Video>> getVideosByUploaderId(@PathVariable Long uploaderId) {
        List<Video> listOfVideo = videoService.getVideosByUploaderId(uploaderId);
        System.out.println(listOfVideo);
        if (listOfVideo.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204
        }
        return ResponseEntity.ok(listOfVideo); // Return 200
    }

}
