package ca.myapp.dto;

import ca.myapp.models.PlaylistTitle;
import ca.myapp.models.Video;

import java.time.LocalDateTime;

public class PlaylistVideoDTO {
    private Long id;
    private Long playlistTitleId;
    private String playlistTitle;
    private Long videoId;
    private String videoTitle;
    private LocalDateTime createdOn;

    // Constructors
    public PlaylistVideoDTO() {}

    public PlaylistVideoDTO(Long id, Long playlistTitleId, String playlistTitle, Long videoId, String videoTitle, LocalDateTime createdOn) {
        this.id = id;
        this.playlistTitleId = playlistTitleId;
        this.playlistTitle = playlistTitle;
        this.videoId = videoId;
        this.videoTitle = videoTitle;
        this.createdOn = createdOn;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlaylistTitleId() {
        return playlistTitleId;
    }

    public void setPlaylistTitleId(Long playlistTitleId) {
        this.playlistTitleId = playlistTitleId;
    }

    public String getPlaylistTitle() {
        return playlistTitle;
    }

    public void setPlaylistTitle(String playlistTitle) {
        this.playlistTitle = playlistTitle;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public String toString() {
        return "PlaylistVideoDTO{" +
                "id=" + id +
                ", playlistTitleId=" + playlistTitleId +
                ", playlistTitle='" + playlistTitle + '\'' +
                ", videoId=" + videoId +
                ", videoTitle='" + videoTitle + '\'' +
                ", createdOn=" + createdOn +
                '}';
    }
}
