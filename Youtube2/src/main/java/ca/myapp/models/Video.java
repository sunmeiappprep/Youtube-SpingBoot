package ca.myapp.models;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "video")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private String description;

    @Column(nullable = false)
    private String url;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User uploader;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public void setTitle(String title) {
        this.title = title;
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

//    public Set<Comment> getComments() {
//        return comments;
//    }

//    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Comment> comments = new HashSet<>();
//
//    @ManyToMany
//    @JoinTable(
//            name = "video_likes",
//            joinColumns = @JoinColumn(name = "video_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id")
//    )
//    private Set<User> likes = new HashSet<>();

}
