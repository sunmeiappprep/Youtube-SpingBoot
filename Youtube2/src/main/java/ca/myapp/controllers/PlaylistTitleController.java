package ca.myapp.controllers;

import ca.myapp.dto.PlaylistTitleAndVideoDTO;
import ca.myapp.dto.PlaylistTitleDTO;
import ca.myapp.dto.VideoRequestDto;
import ca.myapp.models.PlaylistTitle;
import ca.myapp.models.User;
import ca.myapp.service.PlaylistTitleService;
import ca.myapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/playlistTitle")
public class PlaylistTitleController {
    @Autowired
    private final PlaylistTitleService playlistTitleService;

    @Autowired
    private final UserService userService;

    @Autowired
    public PlaylistTitleController(PlaylistTitleService playlistTitleService, UserService userService) {
        this.playlistTitleService = playlistTitleService;
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<PlaylistTitle> createPlaylist(@RequestBody PlaylistTitle request) {

        PlaylistTitle playlistTitle = playlistTitleService.createPlaylist(request.getTitle());
        return new ResponseEntity<>(playlistTitle, HttpStatus.CREATED);
    }


    @GetMapping("/title/{playlistTitleId}")
    public ResponseEntity<PlaylistTitle> getPlaylistTitle(@PathVariable Long playlistTitleId) {
        System.out.println("title");
        PlaylistTitle playlistTitle = playlistTitleService.getPlaylistTitle(playlistTitleId);
        return ResponseEntity.ok(playlistTitle);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<PlaylistTitleDTO>> getUserPlaylists(@PathVariable Long userId) {
        List<PlaylistTitleDTO> playlists = playlistTitleService.getUserPlaylists(userId);
        return ResponseEntity.ok(playlists); // Return 200 with the list of playlists
    }

//    @GetMapping("/user/{userId}/firstVideos")
//    public List<Object[]> getFirstVideosByUser(@PathVariable Long userId) {
//        User user = userService.getAuthenticatedUser(); // Retrieve the User by ID
//        System.out.println("user");
//        System.out.println(user);
//        if (user == null) {
//            // Handle user not found scenario, maybe return an appropriate response
//            return null;
//        }
//        return playlistTitleService.getPlaylistTitlesAndVideosByUser(user);
//    }

    @GetMapping("/user/{userId}/firstVideos")
    public ResponseEntity<List<PlaylistTitleAndVideoDTO>> getFirstVideosByUser(@PathVariable Long userId) {
        User authenticatedUser = userService.getAuthenticatedUser();
        if (authenticatedUser == null || !authenticatedUser.getId().equals(userId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        List<PlaylistTitleAndVideoDTO> results = playlistTitleService.getPlaylistTitlesAndVideosByUser(authenticatedUser);
        return ResponseEntity.ok(results);
    }
}
