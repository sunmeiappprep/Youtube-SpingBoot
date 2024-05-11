package ca.myapp.controllers;
import ca.myapp.dto.AddVideoToPlaylistRequest;
import ca.myapp.models.PlaylistVideo;
import ca.myapp.models.Video;
import ca.myapp.service.PlaylistVideoService;
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

    private final PlaylistVideoService playlistVideoService;

    @Autowired
    public PlaylistVideoController(PlaylistVideoService playlistVideoService) {
        this.playlistVideoService = playlistVideoService;
    }

    @PostMapping("/addVideoToPlaylist")
    public ResponseEntity<PlaylistVideo> addVideoToPlaylist(@RequestBody AddVideoToPlaylistRequest request) throws Exception {
            System.out.println(request);
            PlaylistVideo playlistVideo = playlistVideoService.addVideoToPlaylist(request.getPlaylistTitleId(), request.getVideoId());
            return new ResponseEntity<>(playlistVideo, HttpStatus.CREATED);
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
