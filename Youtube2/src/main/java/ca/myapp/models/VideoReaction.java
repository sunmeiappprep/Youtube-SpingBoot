package ca.myapp.models;
import jakarta.persistence.*;

@Entity
public class VideoReaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "video_id")
    private Video video;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Boolean liked;

    // Getters
    public Long getId() {
        return id;
    }

    public Video getVideo() {
        return video;
    }

    public User getUser() {
        return user;
    }

    public Boolean getLiked() {
        return liked;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }
}