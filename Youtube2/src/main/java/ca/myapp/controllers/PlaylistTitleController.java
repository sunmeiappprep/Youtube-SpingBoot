package ca.myapp.controllers;

import ca.myapp.dto.VideoRequestDto;
import ca.myapp.models.PlaylistTitle;
import ca.myapp.service.PlaylistTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/playlistTitle")
public class PlaylistTitleController {
    @Autowired
    private final PlaylistTitleService playlistTitleService;

    @Autowired
    public PlaylistTitleController(PlaylistTitleService playlistTitleService) {
        this.playlistTitleService = playlistTitleService;
    }

    @PostMapping("/")
    public ResponseEntity<PlaylistTitle> createPlaylist(@RequestBody PlaylistTitle request) {

        PlaylistTitle playlistTitle = playlistTitleService.createPlaylist(request.getTitle());
        return new ResponseEntity<>(playlistTitle, HttpStatus.CREATED);
    }
}
