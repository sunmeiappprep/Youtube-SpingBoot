package ca.myapp.service;

import ca.myapp.exception.idNotFoundException;
import ca.myapp.models.PlaylistTitle;
import ca.myapp.models.User;
import ca.myapp.repository.PlaylistTitleRepository;
import org.springframework.stereotype.Service;

@Service
public class PlaylistTitleService {

    private final PlaylistTitleRepository playlistTitleRepository;

    private final UserService userService;

    public PlaylistTitleService(PlaylistTitleRepository playlistTitleRepository, UserService userService) {
        this.playlistTitleRepository = playlistTitleRepository;
        this.userService = userService;
    }

    public PlaylistTitle findById (Long id) throws Exception {
        return playlistTitleRepository.findById(id)
                .orElseThrow(() -> new Exception("There is no playlist with that id"));
    }

    public PlaylistTitle createPlaylist(String playlistName){
        User currentUser = userService.getAuthenticatedUser();

        PlaylistTitle playlistTitle = new PlaylistTitle();
        playlistTitle.setTitle(playlistName);
        playlistTitle.setIsPrivate(false);
        playlistTitle.setCreatedBy(currentUser);
        return playlistTitleRepository.save(playlistTitle);
    }
}
