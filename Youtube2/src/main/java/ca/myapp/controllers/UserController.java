package ca.myapp.controllers;
import ca.myapp.models.User; // Import the User class
import ca.myapp.models.Video;
import ca.myapp.service.PlaylistTitleService;
import ca.myapp.service.UserService;
import ca.myapp.service.VideoService;
import ca.myapp.utility.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private final VideoService videoService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PlaylistTitleService playlistTitleService;

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
//            ensureUserPlaylists(authenticatedUser);
            return ResponseEntity.ok(authenticatedUser);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Username or Password is incorrect", e);
        }
    }

//    private void ensureUserPlaylists(User user) {
//        if (!playlistTitleService.playlistTitleExistsByName(user, "Watch Later")) {
//            System.out.println("Watch Later");
//            playlistTitleService.createPlaylist("Watch Later");
//        }
//        if (!playlistTitleService.playlistTitleExistsByName(user, "Liked Video")) {
//            System.out.println("Liked Video");
//            playlistTitleService.createPlaylist("Liked Video");
//        }
//        System.out.println("ensureUserPlaylists ran");
//    }

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

    @GetMapping("/{uploaderId}/username")
    public ResponseEntity<String> getUserUsername(@PathVariable Long uploaderId) {
        try {
            String username = userService.getUserUsernameById(uploaderId);
            System.out.println("username");
            return ResponseEntity.ok(username);
        } catch (UsernameNotFoundException e) {
            System.out.println("usernameError");
            return ResponseEntity.status(404).body(e.getMessage()); // Return 404 with error message
        }
    }

    @GetMapping("/userInfo/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        try {
            Optional<User> user = userService.findUserById(userId);
            return ResponseEntity.ok(user); // Return 200 with User object
        } catch (UsernameNotFoundException e) {
            System.out.println("User not found");
            return ResponseEntity.status(404).body("User not found"); // Return 404 with error message
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return ResponseEntity.status(500).body("Internal Server Error"); // Return 500 with generic error message
        }
    }


    @GetMapping("/checkJWT")
    public ResponseEntity<Boolean> checkJWT(@RequestHeader("Authorization") String token) {
        try {
            // remove the bearer string
            String jwtToken = token.substring(7);

            if (jwtUtil.isTokenExpired(jwtToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
            }

            User user = userService.getAuthenticatedUser();
            if (user != null) {
                return ResponseEntity.ok(true);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
    }

}
