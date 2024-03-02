package ca.myapp.service;

import ca.myapp.models.PlaylistTitle;
import ca.myapp.models.PlaylistVideo;
import ca.myapp.models.Video;
import ca.myapp.repository.PlaylistTitleRepository;
import ca.myapp.repository.PlaylistVideoRepository;
import ca.myapp.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlaylistVideoService {

    private final PlaylistVideoRepository playlistVideoRepository;
    private final PlaylistTitleRepository playlistTitleRepository;
    private final VideoRepository videoRepository;

    @Autowired
    public PlaylistVideoService(PlaylistVideoRepository playlistVideoRepository, PlaylistTitleRepository playlistTitleRepository, VideoRepository videoRepository) {
        this.playlistVideoRepository = playlistVideoRepository;
        this.playlistTitleRepository = playlistTitleRepository;
        this.videoRepository = videoRepository;
    }

    @Transactional
    public PlaylistVideo addVideoToPlaylist(Long playlistTitleId, Long videoId) throws Exception {
        // Find the PlaylistTitle by ID
        System.out.println("Heloooooooooooooooooooooooooooooooooooooo");
        System.out.println(videoId);
        System.out.println(playlistTitleId);
        PlaylistTitle playlistTitle = playlistTitleRepository.findById(playlistTitleId)
                .orElseThrow(() -> new Exception("PlaylistTitle not found with id: " + playlistTitleId));

        // Find the Video by ID
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new Exception("Video not found with id: " + videoId));
        System.out.println(playlistTitle);
        System.out.println(video);
        // Create a new PlaylistVideo relationship
        PlaylistVideo playlistVideo = new PlaylistVideo();
        playlistVideo.setPlaylistTitle(playlistTitle);
        playlistVideo.setVideo(video);
        System.out.println(playlistVideo);

        // Save the new relationship in the database
        return playlistVideoRepository.save(playlistVideo);
    }
}