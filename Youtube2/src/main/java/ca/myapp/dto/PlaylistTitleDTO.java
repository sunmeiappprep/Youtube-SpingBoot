package ca.myapp.dto;

import java.time.LocalDateTime;

public class PlaylistTitleDTO {
    private Long id;
    private String title;
    private boolean isPrivate;
    private LocalDateTime createdOn;
    private String createdByUsername;

    // Constructor
    public PlaylistTitleDTO(Long id, String title, boolean isPrivate, LocalDateTime createdOn, String createdByUsername) {
        this.id = id;
        this.title = title;
        this.isPrivate = isPrivate;
        this.createdOn = createdOn;
        this.createdByUsername = createdByUsername;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean getIsPrivate() {
        return isPrivate;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public String getCreatedByUsername() {
        return createdByUsername;
    }
}
