package ca.myapp.models;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "playlist_titles")
public class PlaylistTitle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column()
    private boolean isPrivate;

    @Column(nullable = false, updatable = false, name = "created_on")
    private LocalDateTime createdOn;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User createdBy;

    public PlaylistTitle() {
        this.createdOn = LocalDateTime.now();
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

    public User getCreatedBy() {
        return createdBy;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}