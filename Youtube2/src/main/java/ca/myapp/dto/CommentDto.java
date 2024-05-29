package ca.myapp.dto;

import ca.myapp.models.User;
import ca.myapp.models.Video;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommentDto {
    private Long id;  // Add ID field
    private Long videoId;
    private Long userId; // Can be replaced with token-based user identification later
    private String text;
    private Long parentId; // Field for parent comment ID
    private LocalDateTime createdAt;  // Add createdAt field
    private User user;  // Add UserDto field
    private Video video;  // Add VideoDto field
    private List<CommentDto> replies = new ArrayList<>();  // Add replies field

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public List<CommentDto> getReplies() {
        return replies;
    }

    public void setReplies(List<CommentDto> replies) {
        this.replies = replies;
    }

    @Override
    public String toString() {
        return "CommentDto{" +
                "id=" + id +
                ", videoId=" + videoId +
                ", userId=" + userId +
                ", text='" + text + '\'' +
                ", parentId=" + parentId +
                ", createdAt=" + createdAt +
                ", user=" + user +
                ", video=" + video +
                ", replies=" + replies +
                '}';
    }
}
