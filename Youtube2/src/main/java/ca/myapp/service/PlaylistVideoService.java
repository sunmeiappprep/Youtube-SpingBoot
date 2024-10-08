package ca.myapp.service;

import ca.myapp.dto.VideoWithUserDTO;
import ca.myapp.exception.ErrorToFrontEnd;
import ca.myapp.models.PlaylistTitle;
import ca.myapp.models.PlaylistVideo;
import ca.myapp.models.User;
import ca.myapp.models.Video;
import ca.myapp.repository.PlaylistTitleRepository;
import ca.myapp.repository.PlaylistVideoRepository;
import ca.myapp.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    public boolean existsByPlaylistIdAndVideoId(Long playlistId, Long videoId) {
        return playlistVideoRepository.existsByPlaylistTitleIdAndVideoId(playlistId, videoId);
    }

    public List<PlaylistTitle> checkIfVideoIsInPlaylists(Long userId, Long videoId) {
        return playlistVideoRepository.findPlaylistsByUserAndVideo(userId,videoId);
    }

    @Transactional
    public PlaylistVideo addVideoToPlaylist(Long playlistTitleId, Long videoId) throws Exception {

        boolean exists = playlistVideoRepository.existsByPlaylistTitleIdAndVideoId(playlistTitleId, videoId);
        if (exists) {
            throw new ErrorToFrontEnd("The video is already added to the playlist");
        }

        System.out.println("Heloooooooooooooooooooooooooooooooooooooo");
        System.out.println(videoId);
        System.out.println(playlistTitleId);
        PlaylistTitle playlistTitle = playlistTitleRepository.findById(playlistTitleId)
                .orElseThrow(() -> new Exception("PlaylistTitle not found with id: " + playlistTitleId));

        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new Exception("Video not found with id: " + videoId));
        System.out.println(playlistTitle);
        System.out.println(video);

        PlaylistVideo playlistVideo = new PlaylistVideo();
        playlistVideo.setPlaylistTitle(playlistTitle);
        playlistVideo.setVideo(video);
        System.out.println(playlistVideo);

        return playlistVideoRepository.save(playlistVideo);
    }

    @Transactional
    public List<VideoWithUserDTO> findVideosWithUserByPlaylistTitleId(Long playlistTitleId) {
        List<PlaylistVideo> playlistVideos = playlistVideoRepository.findByPlaylistTitleId(playlistTitleId);

        return playlistVideos.stream().map(playlistVideo -> {
            Video video = playlistVideo.getVideo();
            User uploader = video.getUploader();
            return new VideoWithUserDTO(video, uploader);
        }).collect(Collectors.toList());
    }

    @Transactional
    public void deleteVideoFromPlaylist(Long playlistTitleId, Long videoId) throws Exception {
        PlaylistVideo playlistVideo = playlistVideoRepository.findByPlaylistTitleIdAndVideoId(playlistTitleId, videoId)
                .orElseThrow(() -> new ErrorToFrontEnd("PlaylistVideo relationship not found with playlistTitleId: "
                        + playlistTitleId + " and videoId: " + videoId));

        playlistVideoRepository.delete(playlistVideo);
    }
}