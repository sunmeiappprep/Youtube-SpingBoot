package ca.myapp.service;

import ca.myapp.dto.PlaylistTitleAndVideoDTO;
import ca.myapp.dto.PlaylistTitleDTO;
import ca.myapp.exception.idNotFoundException;
import ca.myapp.models.PlaylistTitle;
import ca.myapp.models.User;
import ca.myapp.models.Video;
import ca.myapp.repository.PlaylistTitleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public List<PlaylistTitleDTO> getUserPlaylists(Long userId) {
        List<PlaylistTitle> playlists = playlistTitleRepository.findByCreatedBy_Id(userId);
        return playlists.stream()
                .map(playlist -> new PlaylistTitleDTO(
                        playlist.getId(),
                        playlist.getTitle(),
                        playlist.getIsPrivate(),
                        playlist.getCreatedOn(),
                        playlist.getCreatedBy().getUsername()))
                .collect(Collectors.toList());
    }

//    public List<Object[]> getPlaylistTitlesAndVideosByUser(User user) {
//        return playlistTitleRepository.findPlaylistTitlesAndVideoTitlesByUser(user);
//    }

    public List<PlaylistTitleAndVideoDTO> getPlaylistTitlesAndVideosByUser(User user) {
        List<Object[]> results = playlistTitleRepository.findPlaylistTitlesAndVideosByUser(user);

        // make map to store count and dto
        Map<Long, PlaylistTitleAndVideoDTO> playlistMap = new HashMap<>();

        // loop thru the results. seperate the modal into var, then Key into the var to get playlist id
        for (Object[] result : results) {
            PlaylistTitle playlistTitle = (PlaylistTitle) result[0];
            Video video = (Video) result[1];
            Long playlistId = playlistTitle.getId();
            // data should never be null,
            playlistMap.compute(playlistId, (id, data) -> {
                if (data == null) {
                    return new PlaylistTitleAndVideoDTO(playlistTitle, video, 1);
                } else {
                    // compare dates
                    if (video.getGeneratedDate().isAfter(data.getVideo().getGeneratedDate())){
                        data.setVideo(video);
                    }
                    data.setVideoCount(data.getVideoCount() + 1);
                    return data;
                }
            });
        }

        // Convert the map to a list of PlaylistTitleAndVideoDTO
        return new ArrayList<>(playlistMap.values());
    }
    public PlaylistTitle createPlaylist(String playlistName){
        User currentUser = userService.getAuthenticatedUser();
        System.out.println(playlistName);
        PlaylistTitle playlistTitle = new PlaylistTitle();
        playlistTitle.setTitle(playlistName);
        playlistTitle.setIsPrivate(false);
        playlistTitle.setCreatedBy(currentUser);
        return playlistTitleRepository.save(playlistTitle);
    }
}
