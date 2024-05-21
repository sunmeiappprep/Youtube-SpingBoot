package ca.myapp.controllers;
import ca.myapp.dto.AddVideoToPlaylistRequest;
import ca.myapp.models.PlaylistTitle;
import ca.myapp.models.PlaylistVideo;
import ca.myapp.models.User;
import ca.myapp.models.Video;
import ca.myapp.service.PlaylistVideoService;
import ca.myapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.PseudoColumnUsage;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/api/playlistVideos")
public class PlaylistVideoController {


    @Autowired
    private UserService userService;
    private final PlaylistVideoService playlistVideoService;

    @Autowired
    public PlaylistVideoController(PlaylistVideoService playlistVideoService) {
        this.playlistVideoService = playlistVideoService;
    }

    @PostMapping("/addVideoToPlaylist")
    public ResponseEntity<?> addVideoToPlaylist(@RequestBody AddVideoToPlaylistRequest request) throws Exception {
        Long playlistId = request.getPlaylistTitleId();
        Long videoId = request.getVideoId();

        if (playlistVideoService.existsByPlaylistIdAndVideoId(playlistId, videoId)) {
            playlistVideoService.deleteVideoFromPlaylist(playlistId, videoId);
            return ResponseEntity.ok().body("PlaylistVideo relationship deleted successfully.");
        } else {
            PlaylistVideo playlistVideo = playlistVideoService.addVideoToPlaylist(playlistId, videoId);
            return new ResponseEntity<>(playlistVideo, HttpStatus.CREATED);
        }
    }

    @GetMapping("/checkVideo")
    public ResponseEntity<List<PlaylistTitle>> checkIfVideoIsInPlaylists(@RequestParam Long videoId) {
        User currentUser = userService.getAuthenticatedUser(); // Get the authenticated user
        System.out.println(currentUser.getId()+"User ID");
        List<PlaylistTitle> playlists = playlistVideoService.checkIfVideoIsInPlaylists(currentUser.getId(), videoId);
        return ResponseEntity.ok(playlists);
    }


    @GetMapping("/{playlistTitleId}")
    public ResponseEntity<List<Video>> getVideosByPlaylistTitleId(@PathVariable Long playlistTitleId) {
        try {
            List<Video> videos = playlistVideoService.getVideosByPlaylistTitleId(playlistTitleId);
            if (videos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(videos);
        } catch (Exception e) {
            return ResponseEntity.ok(null);
        }
    }

    @DeleteMapping("/delete/{playlistTitleId}/{videoId}")
    public ResponseEntity<?> deleteVideoFromPlaylist(@PathVariable Long playlistTitleId, @PathVariable Long videoId) throws Exception {
            playlistVideoService.deleteVideoFromPlaylist(playlistTitleId, videoId);
            return ResponseEntity.ok().body("PlaylistVideo relationship deleted successfully.");
    }
}
