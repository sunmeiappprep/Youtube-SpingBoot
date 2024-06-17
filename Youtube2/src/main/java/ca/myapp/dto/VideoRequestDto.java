package ca.myapp.dto;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class VideoRequestDto {
    private Long userId;
    private String title;
    private String url;
    private String username;
    private String description;
    @JsonProperty("id")
    private Long videoId;
    private Long view;

    private LocalDate generatedDate;

    public Long getUserId() {
        return userId;
    }
    public Long getView() {
        return view;
    }

    public void setGeneratedDate(LocalDate generatedDate) {
        this.generatedDate = generatedDate;
    }

    public LocalDate getGeneratedDate() {
        return generatedDate;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setView(Long view) {
        this.view = view;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    @Override
    public String toString() {
        return "VideoRequestDto{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", view=" + view +
                ", generatedDate=" + generatedDate +
                ", videoId=" + videoId +
                '}';
    }

}
