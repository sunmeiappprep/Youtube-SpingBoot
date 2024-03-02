package ca.myapp.models;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "playlist_videos")
public class PlaylistVideo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "playlist_title_id", nullable = false)
    private PlaylistTitle playlistTitle;

    @ManyToOne
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;

    @Column(nullable = false, updatable = false, name = "created_on")
    private LocalDateTime createdOn;

    public PlaylistVideo() {
        this.createdOn = LocalDateTime.now();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public PlaylistTitle getPlaylistTitle() {
        return playlistTitle;
    }

    public Video getVideo() {
        return video;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setPlaylistTitle(PlaylistTitle playlistTitle) {
        this.playlistTitle = playlistTitle;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}