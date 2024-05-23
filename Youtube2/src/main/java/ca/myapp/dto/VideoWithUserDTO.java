package ca.myapp.dto;

import ca.myapp.models.User;
import ca.myapp.models.Video;

import java.time.LocalDate;

public class VideoWithUserDTO {

    private Long videoId;
    private String videoTitle;
    private String videoUrl;
    private String videoDescription;
    private Long videoViews;
    private LocalDate videoGeneratedDate;
    private Long userId;
    private String username;

    public VideoWithUserDTO(Video video, User user) {
        this.videoId = video.getId();
        this.videoTitle = video.getTitle();
        this.videoUrl = video.getUrl();
        this.videoDescription = video.getDescription();
        this.videoViews = video.getView();
        this.videoGeneratedDate = video.getGeneratedDate();
        this.userId = user.getId();
        this.username = user.getUsername();
    }

    // Getters
    public Long getVideoId() {
        return videoId;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getVideoDescription() {
        return videoDescription;
    }

    public Long getVideoViews() {
        return videoViews;
    }

    public LocalDate getVideoGeneratedDate() {
        return videoGeneratedDate;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    // Setters
    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public void setVideoDescription(String videoDescription) {
        this.videoDescription = videoDescription;
    }

    public void setVideoViews(Long videoViews) {
        this.videoViews = videoViews;
    }

    public void setVideoGeneratedDate(LocalDate videoGeneratedDate) {
        this.videoGeneratedDate = videoGeneratedDate;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
