package ca.myapp.models;
import jakarta.persistence.*;

@Entity
public class CommentReaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Boolean liked;
    public Long getId() {
        return id;
    }

    public Comment getComment() {
        return comment;
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

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }
}