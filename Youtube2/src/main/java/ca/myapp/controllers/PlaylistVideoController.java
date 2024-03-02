package ca.myapp.controllers;
import ca.myapp.dto.AddVideoToPlaylistRequest;
import ca.myapp.models.PlaylistVideo;
import ca.myapp.service.PlaylistVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.PseudoColumnUsage;


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
    public ResponseEntity<PlaylistVideo> addVideoToPlaylist(@RequestBody AddVideoToPlaylistRequest request) {
        try {
            System.out.println(request);
            PlaylistVideo playlistVideo = playlistVideoService.addVideoToPlaylist(request.getPlaylistTitleId(), request.getVideoId());
            return new ResponseEntity<>(playlistVideo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
