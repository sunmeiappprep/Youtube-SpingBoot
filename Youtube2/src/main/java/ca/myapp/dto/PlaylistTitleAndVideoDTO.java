package ca.myapp.dto;

import ca.myapp.models.PlaylistTitle;
import ca.myapp.models.Video;

public class PlaylistTitleAndVideoDTO {

    private PlaylistTitle playlistTitle;
    private Video video;

    private long videoCount;

    public PlaylistTitleAndVideoDTO(PlaylistTitle playlistTitle, Video video,  long videoCount) {
        this.playlistTitle = playlistTitle;
        this.video = video;
        this.videoCount = videoCount;
    }

    // Getters and Setters
    public PlaylistTitle getPlaylistTitle() {
        return playlistTitle;
    }

    public void setPlaylistTitle(PlaylistTitle playlistTitle) {
        this.playlistTitle = playlistTitle;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public long getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(long videoCount) {
        this.videoCount = videoCount;
    }
}
